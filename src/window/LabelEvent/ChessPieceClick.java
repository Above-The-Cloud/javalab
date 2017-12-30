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
 * @author ������
 *ʱ�䣺20171129
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
			//����������⣬���ε����Ч
			return;
		}
		else{
			if (ChineseChessMainFrame.MyBoarder.p1 == null){
			//��ѡ������
				System.out.println("��ѡ������");
				ChineseChessMainFrame.MyBoarder.p1 = new Point(x,y);
				//System.out.println(ChineseChessMainFrame.MyBoarder.p1);
			}
			else{
				//��ѡ������
				System.out.println("��ѡ������");
				if (ChineseChessMainFrame.MyBoarder.MyPieces[ChineseChessMainFrame.MyBoarder.p1.y][ChineseChessMainFrame.MyBoarder.p1.x] == null){
					//ѡ������Ϊ��
					System.out.println("ѡ������Ϊ��");
					ChineseChessMainFrame.MyBoarder.p1 = new Point(x,y);
				}
				else{
					//ѡ�����Ӳ�Ϊ��
					System.out.println("ѡ�����Ӳ�Ϊ��");
					if (ChineseChessMainFrame.MyBoarder.MyPieces[ChineseChessMainFrame.MyBoarder.p1.y][ChineseChessMainFrame.MyBoarder.p1.x].name.charAt(0) != ChineseChessMainFrame.DoPlayer){
						//ѡ�е��ǷǱ�������
						System.out.println("ѡ�е��ǷǱ�������");
						ChineseChessMainFrame.MyBoarder.p1 = new Point(x,y);
					}
					else{
						//ѡ�е��Ǳ�������
						System.out.println("ѡ�е��Ǳ�������");
						if (ChineseChessMainFrame.MyBoarder.MyPieces[y][x] == null){
							//�ڶ���ѡ��Ϊ��
							System.out.println("�ڶ���ѡ��Ϊ��");
							ChineseChessMainFrame.MyBoarder.p2 = new Point(x,y);
							if (ChineseChessMainFrame.MyBoarder.PieceMove(ChineseChessMainFrame.MyBoarder.p1, ChineseChessMainFrame.MyBoarder.p2) == true){
								//���ӿ����ƶ�
								DoPieceSound.play();
								t = new JLabel("");
								t.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ChineseChessMainFrame.class.getResource("/imageLibary/"+ChineseChessMainFrame.MyBoarder.MyPieces[y][x].name.charAt(1)+".jpg"))));
								t.setBounds(0, 0, 1400, 800);
								ChineseChessMainFrame.MyCanvas.add(t);
								ChineseChessMainFrame.MyCanvas.repaint();
								jiangjun = ChineseChessMainFrame.MyBoarder.jiangjun();
								MoveStep ms = new MoveStep(ChineseChessMainFrame.MyBoarder.p1, ChineseChessMainFrame.MyBoarder.p2);
								ChineseChessMainFrame.recordAdd(ms);
								System.out.println("���ӿ����ƶ�");
								char Winner = ChineseChessMainFrame.MyBoarder.Winner();
								if (Winner == '��'){
									WinSound.play();
									((ChessBoarderCanvas)arg0.getSource()).SendWinner('��', userInfo);
									ChineseChessMainFrame.InfBoard.AddLog("�췽���ʤ��!");
									redisplay = true;
								}
								else if (Winner == '��'){
									WinSound.play();
									((ChessBoarderCanvas)arg0.getSource()).SendWinner('��', userInfo);
									ChineseChessMainFrame.InfBoard.AddLog("�ڷ����ʤ��!");
									redisplay = true;
								}
								else{
									ChineseChessMainFrame.MyBoarder.p1 = null;
									ChineseChessMainFrame.MyBoarder.p2 = null;
									SwitchDoPlayer();
								}
								
							}
							else{
								//���Ӳ����ƶ�
								System.out.println("���Ӳ����ƶ�");
								ChineseChessMainFrame.MyBoarder.p1 = new Point(x,y);
								ChineseChessMainFrame.MyBoarder.p2 = null;
							}
						}
						else{
							//�ڶ���ѡ�зǿ�
							System.out.println("�ڶ���ѡ��Ϊ��");
							if (ChineseChessMainFrame.MyBoarder.MyPieces[y][x].name.charAt(0) == ChineseChessMainFrame.DoPlayer){
								//�ڶ���ѡ�еĻ��Ǳ�������
								System.out.println("�ڶ���ѡ�еĻ��Ǳ�������");
								ChineseChessMainFrame.MyBoarder.p1 = new Point(x,y);
							}
							else{
								//�ڶ���ѡ�е��ǵз�����
								System.out.println("�ڶ���ѡ�е��ǵз�����");
								ChineseChessMainFrame.MyBoarder.p2 = new Point(x,y);
								if (ChineseChessMainFrame.MyBoarder.PieceEat(ChineseChessMainFrame.MyBoarder.p1, ChineseChessMainFrame.MyBoarder.p2) == true){
									//���ӿ��Գ�
									DoPieceSound.play();
									t = new JLabel("");
									t.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ChineseChessMainFrame.class.getResource("/imageLibary/"+ChineseChessMainFrame.MyBoarder.MyPieces[y][x].name.charAt(1)+".jpg"))));
									t.setBounds(0, 0, 1400, 800);
									ChineseChessMainFrame.MyCanvas.add(t);
									ChineseChessMainFrame.MyCanvas.repaint();
									System.out.println("���ӿ��Գ�");
									jiangjun = ChineseChessMainFrame.MyBoarder.jiangjun();
									MoveStep ms = new MoveStep(ChineseChessMainFrame.MyBoarder.p1, ChineseChessMainFrame.MyBoarder.p2);
									ChineseChessMainFrame.recordAdd(ms);
									char Winner = ChineseChessMainFrame.MyBoarder.Winner();
									if (Winner == '��'){
										WinSound.play();
										((ChessBoarderCanvas)arg0.getSource()).SendWinner('��', userInfo);
										ChineseChessMainFrame.InfBoard.AddLog("�췽���ʤ��!");
										redisplay = true;
									}
									else if (Winner == '��'){
										WinSound.play();
										((ChessBoarderCanvas)arg0.getSource()).SendWinner('��', userInfo);
										ChineseChessMainFrame.InfBoard.AddLog("�ڷ����ʤ��!");
										redisplay = true;
									}
									else{
										ChineseChessMainFrame.MyBoarder.p1 = null;
										ChineseChessMainFrame.MyBoarder.p2 = null;
										SwitchDoPlayer();
									}
								}
								else{
									//���Ӳ��ܳ�
									System.out.println("���Ӳ��ܳ�");
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
			JOptionPane.showMessageDialog(null, "������"); 
			jiangjun = false;
		}
		if(redisplay)
		{
			ChineseChessMainFrame.reDisplay();
			redisplay = false;
		}
	}
	
	/**
	 * ��������ִ�з�
	 * @author ������
	 * ʱ�䣺20171202
	 */
	static public void SwitchDoPlayer(){
		if (ChineseChessMainFrame.DoPlayer == '��'){
			ChineseChessMainFrame.DoPlayer = '��';
			ChineseChessMainFrame.InfBoard.AddLog("�ڷ�ִ��");
			System.out.println("�ֵ��ڷ�");
		}
		else if (ChineseChessMainFrame.DoPlayer == '��'){
			ChineseChessMainFrame.DoPlayer = '��';
			ChineseChessMainFrame.InfBoard.AddLog("�췽ִ��");
			System.out.println("�ֵ��췽");
		}
		else{
			//ʲô������
		}
	}
}
