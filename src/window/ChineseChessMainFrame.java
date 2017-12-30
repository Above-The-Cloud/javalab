package window;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import Audio.MP3;
import chessBoard.ChessBoarder;
import defaultSet.DefaultSet;
import window.LabelEvent.ChessPieceClick;
import sql.*;
import java.util.*;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.ImageObserver;

public class ChineseChessMainFrame extends JFrame {

	/*
	 * 
	 * 战局记录专用数据结构
	 * 
	 */
	public static ArrayList<MoveStep> record = new ArrayList<MoveStep>();
	public static void recordAdd(MoveStep ms)
	{
		record.add(ms);
	}
	private JPanel contentPane;
	private JPanel Pane1;
	public static HomePanel Pane2;
	public static HomePanel Pane3;
	private JPanel Pane4;
	static public InformationBoard InfBoard;
	
	/**
	 * 游戏模式
	 * 0：双人对决
	 * 1：人机对决
	 * 2：棋盘演示
	 * 3：退出游戏
	 */
	static public int MenuMode = 0;
	/**
	 * 执子方
	 * 红:红方
	 * 黑:黑方
	 * 无：无，不能下子
	 */
	static public char DoPlayer = '红';
	//棋盘数据
	static public ChessBoarder MyBoarder;

	
	public static ChessBoarderCanvas MyCanvas;
	public static UserInfo[] userInfo = new UserInfo[2];


	public ChineseChessMainFrame(LoginFrame lf) {
		
		this.userInfo = lf.getUserInfo();
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    	addWindowListener(new WindowAdapter() {
    	public void windowClosing(WindowEvent e) {
    		dispose();
    		lf.backToEntrance();
    	}
    	});
		//数据初始化
		DataInit();
		
		//设置图标
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(ChineseChessMainFrame.class.getResource("/imageLibary/black-jiang.png")));
		//设置标题
		this.setTitle("中国象棋");
		//设置窗口大小
		this.setBounds(0, 0, 1400, 870);
		//设置窗口不可改变大小
		this.setResizable(false);
		//设置默认关闭
		
		//设置窗口居中
		this.setLocationRelativeTo(null);
		
		//设置ContentPane属性
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		//不使用布局
		contentPane.setLayout(null);
		//设置ContentPane为透明
		contentPane.setOpaque(false);
		this.setContentPane(contentPane);
		
		//设置ContentPane上信息
		
		//添加背景图片
		JLabel BackGround = new JLabel("");
		BackGround.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ChineseChessMainFrame.class.getResource("/imageLibary/bgd.jpg"))));
		BackGround.setBounds(0, 0, 1400, 870);
		//添加背景图片的关键语句
		this.getLayeredPane().add(BackGround, new Integer(Integer.MIN_VALUE)); 

		//初始化4个JPanel
		Pane1 = new JPanel();
		Pane2 = new HomePanel();
		Pane3 = new HomePanel();
		
		//设置4个JPanel的位置和共同属性
		Pane1.setBounds(0, 0, 1400, 870);
		Pane1.setOpaque(false);
		Pane1.setVisible(true);
		Pane1.setLayout(null);
		Pane2.setBounds(800,370,400,322);
		Pane2.setOpaque(false);
		Pane2.setVisible(true);
		Pane2.setLayout(null);
		Pane3.setBounds(750,70,400,322);
		Pane3.setOpaque(false);
		Pane3.setVisible(true);
		Pane3.setLayout(null);

		
		//把4个Pane添加进ContentPanel
		contentPane.add(Pane1);
		contentPane.add(Pane2);
		contentPane.add(Pane3);

		
		//对Pane1添加Canvas来绘制棋盘
		MyCanvas = new ChessBoarderCanvas();
		//设置Canvas位置和大小
		MyCanvas.setBounds(DefaultSet.CanvasPosX, DefaultSet.CanvasPosY, 661, 728);
		//为Canvas传递数据
		//MyCanvas.SendData(this.MyBoarder, Toolkit.getDefaultToolkit().getImage(ChineseChessMainFrame.class.getResource("/imageLibary/bgd.jpg")), DefaultSet.CanvasPosX, DefaultSet.CanvasPosY, DefaultSet.CanvasPosX+661, DefaultSet.CanvasPosY+728);
		MyCanvas.repaint();
		MyCanvas.addMouseListener(new ChessPieceClick(userInfo));
		Pane1.add(MyCanvas);
		MyCanvas.setOpaque(false);
		//对Pane1添加信息栏
		InfBoard = new InformationBoard();
		InfBoard.setBounds(1100, 50, 394, 481);
		//Pane1.add(InfBoard);
		
		InfBoard.AddLog("红方执子");
		//添加用户信息栏1
		JLabel user1name = new JLabel("红方:"+userInfo[0].getUserName());
		user1name.setBounds(161,30,300,40);
		user1name.setFont(new Font("华文隶书",Font.CENTER_BASELINE,30));
		Pane2.add(user1name);
		JLabel user1win = new JLabel("赢："+userInfo[0].getNumWin()+"局");
		user1win.setFont(new Font("华文行楷",Font.CENTER_BASELINE,25));
		user1win.setBounds(161,70,300,40);
		Pane2.add(user1win);
		JLabel user1lose = new JLabel("输："+userInfo[0].getNumLose()+"局");
		user1lose.setFont(new Font("华文行楷",Font.CENTER_BASELINE,25));
		user1lose.setBounds(161,110,300,40);
		Pane2.add(user1lose);
		JLabel user1peace = new JLabel("平："+userInfo[0].getNumPeace()+"局");
		user1peace.setFont(new Font("华文行楷",Font.CENTER_BASELINE,25));
		user1peace.setBounds(161,150,300,40);
		Pane2.add(user1peace);
		JLabel user1rate = new JLabel("胜率："+String.format("%.1f", (userInfo[0].getNumWin()*100.0/(userInfo[0].getNumWin()+userInfo[0].getNumLose()+userInfo[0].getNumPeace())))+"%");
		user1rate.setFont(new Font("华文行楷",Font.CENTER_BASELINE,25));
		user1rate.setBounds(161,190,300,40);
		Pane2.add(user1rate);
		
		//添加用户信息栏2
		JLabel user2name = new JLabel("黑方:"+userInfo[1].getUserName());
		user2name.setBounds(161,30,300,40);
		user2name.setFont(new Font("华文隶书",Font.CENTER_BASELINE,30));
		Pane3.add(user2name);
		JLabel user2win = new JLabel("赢："+userInfo[1].getNumWin()+"局");
		user2win.setFont(new Font("华文行楷",Font.CENTER_BASELINE,25));
		user2win.setBounds(161,70,300,40);
		Pane3.add(user2win);
		JLabel user2lose = new JLabel("输："+userInfo[1].getNumLose()+"局");
		user2lose.setFont(new Font("华文行楷",Font.CENTER_BASELINE,25));
		user2lose.setBounds(161,110,300,40);
		Pane3.add(user2lose);
		JLabel user2peace = new JLabel("平："+userInfo[1].getNumPeace()+"局");
		user2peace.setFont(new Font("华文行楷",Font.CENTER_BASELINE,25));
		user2peace.setBounds(161,150,300,40);
		Pane3.add(user2peace);
		JLabel user2rate = new JLabel("胜率："+String.format("%.1f", (userInfo[1].getNumWin()*100.0/(userInfo[1].getNumWin()+userInfo[1].getNumLose()+userInfo[1].getNumPeace())))+"%");
		user2rate.setFont(new Font("华文行楷",Font.CENTER_BASELINE,25));
		user2rate.setBounds(161,190,300,40);
		Pane3.add(user2rate);
		
		//添加重新开始按钮
		DiyButton AllReset = new DiyButton("Image\\ButtonAllReset(0).png","Image\\ButtonAllReset(1).png");
		AllReset.setBounds(1150, 600, 326, 115);
		MyCanvas.add(AllReset);
		AllReset.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent arg0){
				DataInit();
				InfBoard.Clear();
				InfBoard.AddLog("红方执子");
				MyCanvas.SendWinner('无', userInfo);
				record.clear();
				ChessPieceClick.SwitchDoPlayer();
				MyCanvas.paintImmediately(0, 0, MyCanvas.getWidth(), MyCanvas.getHeight());
			}
		});
		Pane1.add(AllReset);
		
		//添加时间标签
		JLabel TimerLabel1 = new JLabel();
		TimerLabel1.setBounds(1000, 590, 100, 50);
		TimerLabel1.setFont(new Font("华文行楷",Font.CENTER_BASELINE,28));
		Pane1.add(TimerLabel1);
		JLabel TimerLabel12 = new JLabel();
		TimerLabel12.setBounds(950, 290, 100, 50);
		TimerLabel12.setFont(new Font("华文行楷",Font.CENTER_BASELINE,28));
		Pane1.add(TimerLabel12);
		TimerThread MyTimerThread = new TimerThread(TimerLabel1, TimerLabel12);
		MyTimerThread.start();
		
		
		//添加认输按钮
		DiyButton WantLose = new DiyButton("Image\\ButtonLose(0).png","Image\\ButtonLose(1).png");
		WantLose.setBounds(45, 710, 326, 115);
		WantLose.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent arg0){
				if(DoPlayer == '黑')
				{
					MyCanvas.SendWinner('红', userInfo);
					DoPlayer = '无';
				}
					
				else 
				{
					MyCanvas.SendWinner('黑', userInfo);
					DoPlayer = '无';
				}
				MyCanvas.repaint();
				MyCanvas.paintImmediately(0, 0, MyCanvas.getWidth(), MyCanvas.getHeight());
				System.out.println("repaint done");
				ChineseChessMainFrame.reDisplay();
			}
		});
		Pane1.add(WantLose);
		
		//添加平局按钮
		DiyButton WantEqual = new DiyButton("Image\\ButtonEqual(0).png","Image\\ButtonEqual(1).png");
		WantEqual.setBounds(345, 710, 326, 115);
		WantEqual.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent arg0){
				MyCanvas.SendWinner('二', userInfo);
				DoPlayer = '无';
				MyCanvas.repaint();
				MyCanvas.paintImmediately(0, 0, MyCanvas.getWidth(), MyCanvas.getHeight());
				System.out.println("repaint done");
				ChineseChessMainFrame.reDisplay();
			}
		});
		Pane1.add(WantEqual);
		
		//添加悔棋按钮
		DiyButton WantBack = new DiyButton("Image\\ButtonBack(0).png","Image\\ButtonBack(1).png");
		WantBack.setBounds(645, 710, 326, 115);
		WantBack.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent arg0){
					if(ChineseChessMainFrame.MyBoarder.prestep())
					{
						ChessPieceClick.SwitchDoPlayer();
						MyCanvas.repaint();
						MyCanvas.paintImmediately(0, 0, MyCanvas.getWidth(), MyCanvas.getHeight());
						System.out.println("repaint done");
						JOptionPane.showMessageDialog(ChineseChessMainFrame.this, "悔棋成功！");
					}
					else
					{
						JOptionPane.showMessageDialog(ChineseChessMainFrame.this, "悔棋失败！");
					}
			}
		});
		Pane1.add(WantBack);
		
	}
	
	/**基本数据初始化
	 * @author 汪春雨
	 * 时间：20171201
	 */
	public static void DataInit(){
		MenuMode = 0;
		DoPlayer = '红';
		MyBoarder = new ChessBoarder();
		
		System.out.println(userInfo[0].getUserName() + "\t" + userInfo[0].getNumWin());
		System.out.println(userInfo[1].getUserName() + "\t" + userInfo[1].getNumWin());
	}
	/*
	 * 战局重播
	 * 
	 */
	public static void reDisplay()
	{
		
		int n=JOptionPane.showConfirmDialog(null, "是否战局重播？","标题",JOptionPane.YES_NO_CANCEL_OPTION);
		if(n==0)  //战局重播
		{
			DataInit();
			InfBoard.Clear();
			MyCanvas.SendWinner('无', userInfo);
			MyCanvas.paintImmediately(0, 0, MyCanvas.getWidth(), MyCanvas.getHeight());
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(MoveStep step : record)
			{
				ChineseChessMainFrame.MyBoarder.MyPieces[step.des.y][step.des.x] = ChineseChessMainFrame.MyBoarder.MyPieces[step.src.y][step.src.x];
				ChineseChessMainFrame.MyBoarder.MyPieces[step.src.y][step.src.x] = null;
				MP3 DoPieceSound = new MP3(ChineseChessMainFrame.class.getResource("/music/dopiece.wav").getPath().substring(1),false);
				MyCanvas.repaint();
				MyCanvas.paintImmediately(0, 0, MyCanvas.getWidth(), MyCanvas.getHeight());
				System.out.println("repaint done");
				DoPieceSound.play();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			int t = JOptionPane.showConfirmDialog(null, "是否要重新开始", "重播结束",JOptionPane.YES_NO_CANCEL_OPTION);
			if(t==0)
			{
				DataInit();
				InfBoard.Clear();
				InfBoard.AddLog("红方执子");
				MyCanvas.SendWinner('无', userInfo);
				MyCanvas.paintImmediately(0, 0, MyCanvas.getWidth(), MyCanvas.getHeight());
				record.clear();
			}
			else{
				
			}
			
		}
		else
		{
			
			return ;
		}
	}
}
