package window.LabelEvent;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Audio.MP3;
import defaultSet.DefaultSet;
import window.ChessBoarderCanvas;
import window.ChineseChessMainFrame;
import window.MoveStep;
import sql.*;

/**
 * 
 * @author 汪春雨
 *时间：20171129
 */
public class ChessPieceClick extends MouseAdapter {
	UserInfo[] userInfo = new UserInfo[2];
	public boolean jiangjun = false;
	public boolean redisplay = false;
	JLabel t;
	public ChessPieceClick(UserInfo[] userInfo)
	{
		this.userInfo = userInfo;
	}
	@Override
	public void mouseClicked(MouseEvent arg0){
		MP3 DoPieceSound = new MP3(ChineseChessMainFrame.class.getResource("/music/dopiece.wav").getPath().substring(1),false);
		MP3 WinSound = new MP3(ChineseChessMainFrame.class.getResource("/music/win.wav").getPath().substring(1),false);
		int x,y;
		x = arg0.getX();
		y = arg0.getY();
		x = (x - DefaultSet.ChessBoarderXX) / DefaultSet.ChessBoarderPP;
		y = (y - DefaultSet.ChessBoarderYY) / DefaultSet.ChessBoarderPP;
		System.out.println(x + "," +  y);
		if (x < 0 || x > 8 || y < 0 || y > 9){
			//点击在棋盘外，本次点击无效
			return;
		}
		else{
			if (ChineseChessMainFrame.MyBoarder.p1 == null){
			//无选中棋子
				System.out.println("无选中棋子");
				ChineseChessMainFrame.MyBoarder.p1 = new Point(x,y);
				//System.out.println(ChineseChessMainFrame.MyBoarder.p1);
			}
			else{
				//有选中棋子
				System.out.println("有选中棋子");
				if (ChineseChessMainFrame.MyBoarder.MyPieces[ChineseChessMainFrame.MyBoarder.p1.y][ChineseChessMainFrame.MyBoarder.p1.x] == null){
					//选中棋子为空
					System.out.println("选中棋子为空");
					ChineseChessMainFrame.MyBoarder.p1 = new Point(x,y);
				}
				else{
					//选中棋子不为空
					System.out.println("选中棋子不为空");
					if (ChineseChessMainFrame.MyBoarder.MyPieces[ChineseChessMainFrame.MyBoarder.p1.y][ChineseChessMainFrame.MyBoarder.p1.x].name.charAt(0) != ChineseChessMainFrame.DoPlayer){
						//选中的是非本方棋子
						System.out.println("选中的是非本方棋子");
						ChineseChessMainFrame.MyBoarder.p1 = new Point(x,y);
					}
					else{
						//选中的是本方棋子
						System.out.println("选中的是本方棋子");
						if (ChineseChessMainFrame.MyBoarder.MyPieces[y][x] == null){
							//第二次选中为空
							System.out.println("第二次选中为空");
							ChineseChessMainFrame.MyBoarder.p2 = new Point(x,y);
							if (ChineseChessMainFrame.MyBoarder.PieceMove(ChineseChessMainFrame.MyBoarder.p1, ChineseChessMainFrame.MyBoarder.p2) == true){
								//棋子可以移动
								DoPieceSound.play();
								t = new JLabel("");
								t.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ChineseChessMainFrame.class.getResource("/imageLibary/"+ChineseChessMainFrame.MyBoarder.MyPieces[y][x].name.charAt(1)+".jpg"))));
								t.setBounds(0, 0, 1400, 800);
								ChineseChessMainFrame.MyCanvas.add(t);
								ChineseChessMainFrame.MyCanvas.repaint();
								jiangjun = ChineseChessMainFrame.MyBoarder.jiangjun();
								MoveStep ms = new MoveStep(ChineseChessMainFrame.MyBoarder.p1, ChineseChessMainFrame.MyBoarder.p2);
								ChineseChessMainFrame.recordAdd(ms);
								System.out.println("棋子可以移动");
								char Winner = ChineseChessMainFrame.MyBoarder.Winner();
								if (Winner == '红'){
									WinSound.play();
									((ChessBoarderCanvas)arg0.getSource()).SendWinner('红', userInfo);
									ChineseChessMainFrame.InfBoard.AddLog("红方获得胜利!");
									redisplay = true;
								}
								else if (Winner == '黑'){
									WinSound.play();
									((ChessBoarderCanvas)arg0.getSource()).SendWinner('黑', userInfo);
									ChineseChessMainFrame.InfBoard.AddLog("黑方获得胜利!");
									redisplay = true;
								}
								else{
									ChineseChessMainFrame.MyBoarder.p1 = null;
									ChineseChessMainFrame.MyBoarder.p2 = null;
									SwitchDoPlayer();
								}
								
							}
							else{
								//棋子不能移动
								System.out.println("棋子不能移动");
								ChineseChessMainFrame.MyBoarder.p1 = new Point(x,y);
								ChineseChessMainFrame.MyBoarder.p2 = null;
							}
						}
						else{
							//第二次选中非空
							System.out.println("第二次选中为空");
							if (ChineseChessMainFrame.MyBoarder.MyPieces[y][x].name.charAt(0) == ChineseChessMainFrame.DoPlayer){
								//第二次选中的还是本方棋子
								System.out.println("第二次选中的还是本方棋子");
								ChineseChessMainFrame.MyBoarder.p1 = new Point(x,y);
							}
							else{
								//第二次选中的是敌方棋子
								System.out.println("第二次选中的是敌方棋子");
								ChineseChessMainFrame.MyBoarder.p2 = new Point(x,y);
								if (ChineseChessMainFrame.MyBoarder.PieceEat(ChineseChessMainFrame.MyBoarder.p1, ChineseChessMainFrame.MyBoarder.p2) == true){
									//棋子可以吃
									DoPieceSound.play();
									t = new JLabel("");
									t.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ChineseChessMainFrame.class.getResource("/imageLibary/"+ChineseChessMainFrame.MyBoarder.MyPieces[y][x].name.charAt(1)+".jpg"))));
									t.setBounds(0, 0, 1400, 800);
									ChineseChessMainFrame.MyCanvas.add(t);
									ChineseChessMainFrame.MyCanvas.repaint();
									System.out.println("棋子可以吃");
									jiangjun = ChineseChessMainFrame.MyBoarder.jiangjun();
									MoveStep ms = new MoveStep(ChineseChessMainFrame.MyBoarder.p1, ChineseChessMainFrame.MyBoarder.p2);
									ChineseChessMainFrame.recordAdd(ms);
									char Winner = ChineseChessMainFrame.MyBoarder.Winner();
									if (Winner == '红'){
										WinSound.play();
										((ChessBoarderCanvas)arg0.getSource()).SendWinner('红', userInfo);
										ChineseChessMainFrame.InfBoard.AddLog("红方获得胜利!");
										redisplay = true;
									}
									else if (Winner == '黑'){
										WinSound.play();
										((ChessBoarderCanvas)arg0.getSource()).SendWinner('黑', userInfo);
										ChineseChessMainFrame.InfBoard.AddLog("黑方获得胜利!");
										redisplay = true;
									}
									else{
										ChineseChessMainFrame.MyBoarder.p1 = null;
										ChineseChessMainFrame.MyBoarder.p2 = null;
										SwitchDoPlayer();
									}
								}
								else{
									//棋子不能吃
									System.out.println("棋子不能吃");
									ChineseChessMainFrame.MyBoarder.p1 = new Point(x,y);
									ChineseChessMainFrame.MyBoarder.p2 = null;
								}
							}
						}
					}
				}
			}
		}
		((ChessBoarderCanvas)arg0.getSource()).repaint();
		((ChessBoarderCanvas)arg0.getSource()).paintImmediately(0, 0, ((ChessBoarderCanvas)arg0.getSource()).getWidth(), ((ChessBoarderCanvas)arg0.getSource()).getHeight());
		try {
			Thread.sleep(800);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(t!=null)
			{ChineseChessMainFrame.MyCanvas.remove(t);
			ChineseChessMainFrame.MyCanvas.repaint();}
		System.out.println("repaint done");
		if(jiangjun)
		{
			JOptionPane.showMessageDialog(null, "将军！"); 
			jiangjun = false;
		}
		if(redisplay)
		{
			ChineseChessMainFrame.reDisplay();
			redisplay = false;
		}
	}
	
	/**
	 * 交换棋子执行方
	 * @author 汪春雨
	 * 时间：20171202
	 */
	static public void SwitchDoPlayer(){
		if (ChineseChessMainFrame.DoPlayer == '红'){
			ChineseChessMainFrame.DoPlayer = '黑';
			ChineseChessMainFrame.InfBoard.AddLog("黑方执子");
			System.out.println("轮到黑方");
		}
		else if (ChineseChessMainFrame.DoPlayer == '黑'){
			ChineseChessMainFrame.DoPlayer = '红';
			ChineseChessMainFrame.InfBoard.AddLog("红方执子");
			System.out.println("轮到红方");
		}
		else{
			//什么都不做
		}
	}
}
