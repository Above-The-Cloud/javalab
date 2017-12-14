package window;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import chessBoard.ChessBoarder;
import defaultSet.DefaultSet;
import window.LabelEvent.ChessPieceClick;
import sql.*;

import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;

public class ChineseChessMainFrame extends JFrame {

	private JPanel contentPane;
	private JPanel Pane1;
	private JPanel Pane2;
	private JPanel Pane3;
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
	
	ChessBoarderCanvas MyCanvas;
	UserInfo[] userInfo = new UserInfo[2];


	public ChineseChessMainFrame(LoginFrame lf) {
		
		this.userInfo = lf.getUserInfo();
		//数据初始化
		DataInit();
		
		//设置图标
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(ChineseChessMainFrame.class.getResource("/imageLibary/black-jiang.png")));
		//设置标题
		this.setTitle("中国象棋");
		//设置窗口大小
		this.setBounds(0, 0, 1366, 768);
		//设置窗口不可改变大小
		this.setResizable(false);
		//设置默认关闭
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		BackGround.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ChineseChessMainFrame.class.getResource("/imageLibary/background.png"))));
		BackGround.setBounds(0, 0, 1366, 768);
		//添加背景图片的关键语句
		this.getLayeredPane().add(BackGround, new Integer(Integer.MIN_VALUE)); 

		
		//初始化4个JPanel
		Pane1 = new JPanel();
		Pane2 = new JPanel();
		Pane3 = new JPanel();
		
		//设置4个JPanel的位置和共同属性
		Pane1.setBounds(0, 0, 1366, 768);
		Pane1.setOpaque(false);
		Pane1.setVisible(true);
		Pane1.setLayout(null);
		Pane2.setBounds(0, 0, 1366, 768);
		Pane2.setOpaque(false);
		Pane2.setVisible(false);
		Pane2.setLayout(null);
		Pane3.setBounds(0, 0, 1366, 768);
		Pane3.setOpaque(false);
		Pane3.setVisible(false);
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
		MyCanvas.SendData(this.MyBoarder, Toolkit.getDefaultToolkit().getImage(ChineseChessMainFrame.class.getResource("/imageLibary/background.png")), DefaultSet.CanvasPosX, DefaultSet.CanvasPosY, DefaultSet.CanvasPosX+661, DefaultSet.CanvasPosY+728);
		MyCanvas.repaint();
		MyCanvas.addMouseListener(new ChessPieceClick());
		Pane1.add(MyCanvas);
		
		//对Pane1添加信息栏
		InfBoard = new InformationBoard();
		InfBoard.setBounds(1011, 50, 394, 481);
		Pane1.add(InfBoard);
		
		InfBoard.AddLog("红方执子");
		
		//添加重新开始按钮
		DiyButton AllReset = new DiyButton("Image\\ButtonAllReset(0).png","Image\\ButtonAllReset(1).png");
		AllReset.setBounds(780, 610, 326, 115);
		AllReset.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent arg0){
				DataInit();
				InfBoard.Clear();
				InfBoard.AddLog("红方执子");
				MyCanvas.SendWinner('无');
				MyCanvas.paintImmediately(0, 0, MyCanvas.getWidth(), MyCanvas.getHeight());
			}
		});
		Pane1.add(AllReset);
		
		//添加时间标签
		JLabel TimerLabel = new JLabel();
		TimerLabel.setBounds(1030, 570, 100, 50);
		TimerLabel.setFont(new Font("华文行楷",Font.CENTER_BASELINE,28));
		Pane1.add(TimerLabel);
		TimerThread MyTimerThread = new TimerThread(TimerLabel);
		MyTimerThread.start();
		
		
		//添加认输按钮
		DiyButton WantLose = new DiyButton("Image\\ButtonLose(0).png","Image\\ButtonLose(1).png");
		WantLose.setBounds(780, 360, 326, 115);
		WantLose.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent arg0){
				if(DoPlayer == '黑')MyCanvas.SendWinner('红');
				else MyCanvas.SendWinner('黑');
			}
		});
		Pane1.add(WantLose);
		
		//添加平局按钮
		DiyButton WantEqual = new DiyButton("Image\\ButtonEqual(0).png","Image\\ButtonEqual(1).png");
		WantEqual.setBounds(780, 230, 326, 115);
		WantEqual.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent arg0){
				MyCanvas.SendWinner('二');
			}
		});
		Pane1.add(WantEqual);
		
		//添加悔棋按钮
		DiyButton WantBack = new DiyButton("Image\\ButtonBack(0).png","Image\\ButtonBack(1).png");
		WantBack.setBounds(780, 100, 326, 115);
		WantBack.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent arg0){

			}
		});
		Pane1.add(WantBack);
		
	}
	
	/**基本数据初始化
	 * @author 汪春雨
	 * 时间：20171201
	 */
	public void DataInit(){
		MenuMode = 0;
		DoPlayer = '红';
		MyBoarder = new ChessBoarder();
		
		System.out.println(userInfo[0].getUserName() + "\t" + userInfo[0].getNumWin());
		System.out.println(userInfo[1].getUserName() + "\t" + userInfo[1].getNumWin());
	}

}
