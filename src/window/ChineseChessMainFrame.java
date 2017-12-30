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
	 * ս�ּ�¼ר�����ݽṹ
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
	 * ��Ϸģʽ
	 * 0��˫�˶Ծ�
	 * 1���˻��Ծ�
	 * 2��������ʾ
	 * 3���˳���Ϸ
	 */
	static public int MenuMode = 0;
	/**
	 * ִ�ӷ�
	 * ��:�췽
	 * ��:�ڷ�
	 * �ޣ��ޣ���������
	 */
	static public char DoPlayer = '��';
	//��������
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
		//���ݳ�ʼ��
		DataInit();
		
		//����ͼ��
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(ChineseChessMainFrame.class.getResource("/imageLibary/black-jiang.png")));
		//���ñ���
		this.setTitle("�й�����");
		//���ô��ڴ�С
		this.setBounds(0, 0, 1400, 870);
		//���ô��ڲ��ɸı��С
		this.setResizable(false);
		//����Ĭ�Ϲر�
		
		//���ô��ھ���
		this.setLocationRelativeTo(null);
		
		//����ContentPane����
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		//��ʹ�ò���
		contentPane.setLayout(null);
		//����ContentPaneΪ͸��
		contentPane.setOpaque(false);
		this.setContentPane(contentPane);
		
		//����ContentPane����Ϣ
		
		//��ӱ���ͼƬ
		JLabel BackGround = new JLabel("");
		BackGround.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ChineseChessMainFrame.class.getResource("/imageLibary/bgd.jpg"))));
		BackGround.setBounds(0, 0, 1400, 870);
		//��ӱ���ͼƬ�Ĺؼ����
		this.getLayeredPane().add(BackGround, new Integer(Integer.MIN_VALUE)); 

		//��ʼ��4��JPanel
		Pane1 = new JPanel();
		Pane2 = new HomePanel();
		Pane3 = new HomePanel();
		
		//����4��JPanel��λ�ú͹�ͬ����
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

		
		//��4��Pane��ӽ�ContentPanel
		contentPane.add(Pane1);
		contentPane.add(Pane2);
		contentPane.add(Pane3);

		
		//��Pane1���Canvas����������
		MyCanvas = new ChessBoarderCanvas();
		//����Canvasλ�úʹ�С
		MyCanvas.setBounds(DefaultSet.CanvasPosX, DefaultSet.CanvasPosY, 661, 728);
		//ΪCanvas��������
		//MyCanvas.SendData(this.MyBoarder, Toolkit.getDefaultToolkit().getImage(ChineseChessMainFrame.class.getResource("/imageLibary/bgd.jpg")), DefaultSet.CanvasPosX, DefaultSet.CanvasPosY, DefaultSet.CanvasPosX+661, DefaultSet.CanvasPosY+728);
		MyCanvas.repaint();
		MyCanvas.addMouseListener(new ChessPieceClick(userInfo));
		Pane1.add(MyCanvas);
		MyCanvas.setOpaque(false);
		//��Pane1�����Ϣ��
		InfBoard = new InformationBoard();
		InfBoard.setBounds(1100, 50, 394, 481);
		//Pane1.add(InfBoard);
		
		InfBoard.AddLog("�췽ִ��");
		//����û���Ϣ��1
		JLabel user1name = new JLabel("�췽:"+userInfo[0].getUserName());
		user1name.setBounds(161,30,300,40);
		user1name.setFont(new Font("��������",Font.CENTER_BASELINE,30));
		Pane2.add(user1name);
		JLabel user1win = new JLabel("Ӯ��"+userInfo[0].getNumWin()+"��");
		user1win.setFont(new Font("�����п�",Font.CENTER_BASELINE,25));
		user1win.setBounds(161,70,300,40);
		Pane2.add(user1win);
		JLabel user1lose = new JLabel("�䣺"+userInfo[0].getNumLose()+"��");
		user1lose.setFont(new Font("�����п�",Font.CENTER_BASELINE,25));
		user1lose.setBounds(161,110,300,40);
		Pane2.add(user1lose);
		JLabel user1peace = new JLabel("ƽ��"+userInfo[0].getNumPeace()+"��");
		user1peace.setFont(new Font("�����п�",Font.CENTER_BASELINE,25));
		user1peace.setBounds(161,150,300,40);
		Pane2.add(user1peace);
		JLabel user1rate = new JLabel("ʤ�ʣ�"+String.format("%.1f", (userInfo[0].getNumWin()*100.0/(userInfo[0].getNumWin()+userInfo[0].getNumLose()+userInfo[0].getNumPeace())))+"%");
		user1rate.setFont(new Font("�����п�",Font.CENTER_BASELINE,25));
		user1rate.setBounds(161,190,300,40);
		Pane2.add(user1rate);
		
		//����û���Ϣ��2
		JLabel user2name = new JLabel("�ڷ�:"+userInfo[1].getUserName());
		user2name.setBounds(161,30,300,40);
		user2name.setFont(new Font("��������",Font.CENTER_BASELINE,30));
		Pane3.add(user2name);
		JLabel user2win = new JLabel("Ӯ��"+userInfo[1].getNumWin()+"��");
		user2win.setFont(new Font("�����п�",Font.CENTER_BASELINE,25));
		user2win.setBounds(161,70,300,40);
		Pane3.add(user2win);
		JLabel user2lose = new JLabel("�䣺"+userInfo[1].getNumLose()+"��");
		user2lose.setFont(new Font("�����п�",Font.CENTER_BASELINE,25));
		user2lose.setBounds(161,110,300,40);
		Pane3.add(user2lose);
		JLabel user2peace = new JLabel("ƽ��"+userInfo[1].getNumPeace()+"��");
		user2peace.setFont(new Font("�����п�",Font.CENTER_BASELINE,25));
		user2peace.setBounds(161,150,300,40);
		Pane3.add(user2peace);
		JLabel user2rate = new JLabel("ʤ�ʣ�"+String.format("%.1f", (userInfo[1].getNumWin()*100.0/(userInfo[1].getNumWin()+userInfo[1].getNumLose()+userInfo[1].getNumPeace())))+"%");
		user2rate.setFont(new Font("�����п�",Font.CENTER_BASELINE,25));
		user2rate.setBounds(161,190,300,40);
		Pane3.add(user2rate);
		
		//������¿�ʼ��ť
		DiyButton AllReset = new DiyButton("Image\\ButtonAllReset(0).png","Image\\ButtonAllReset(1).png");
		AllReset.setBounds(1150, 600, 326, 115);
		MyCanvas.add(AllReset);
		AllReset.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent arg0){
				DataInit();
				InfBoard.Clear();
				InfBoard.AddLog("�췽ִ��");
				MyCanvas.SendWinner('��', userInfo);
				record.clear();
				ChessPieceClick.SwitchDoPlayer();
				MyCanvas.paintImmediately(0, 0, MyCanvas.getWidth(), MyCanvas.getHeight());
			}
		});
		Pane1.add(AllReset);
		
		//���ʱ���ǩ
		JLabel TimerLabel1 = new JLabel();
		TimerLabel1.setBounds(1000, 590, 100, 50);
		TimerLabel1.setFont(new Font("�����п�",Font.CENTER_BASELINE,28));
		Pane1.add(TimerLabel1);
		JLabel TimerLabel12 = new JLabel();
		TimerLabel12.setBounds(950, 290, 100, 50);
		TimerLabel12.setFont(new Font("�����п�",Font.CENTER_BASELINE,28));
		Pane1.add(TimerLabel12);
		TimerThread MyTimerThread = new TimerThread(TimerLabel1, TimerLabel12);
		MyTimerThread.start();
		
		
		//������䰴ť
		DiyButton WantLose = new DiyButton("Image\\ButtonLose(0).png","Image\\ButtonLose(1).png");
		WantLose.setBounds(45, 710, 326, 115);
		WantLose.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent arg0){
				if(DoPlayer == '��')
				{
					MyCanvas.SendWinner('��', userInfo);
					DoPlayer = '��';
				}
					
				else 
				{
					MyCanvas.SendWinner('��', userInfo);
					DoPlayer = '��';
				}
				MyCanvas.repaint();
				MyCanvas.paintImmediately(0, 0, MyCanvas.getWidth(), MyCanvas.getHeight());
				System.out.println("repaint done");
				ChineseChessMainFrame.reDisplay();
			}
		});
		Pane1.add(WantLose);
		
		//���ƽ�ְ�ť
		DiyButton WantEqual = new DiyButton("Image\\ButtonEqual(0).png","Image\\ButtonEqual(1).png");
		WantEqual.setBounds(345, 710, 326, 115);
		WantEqual.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent arg0){
				MyCanvas.SendWinner('��', userInfo);
				DoPlayer = '��';
				MyCanvas.repaint();
				MyCanvas.paintImmediately(0, 0, MyCanvas.getWidth(), MyCanvas.getHeight());
				System.out.println("repaint done");
				ChineseChessMainFrame.reDisplay();
			}
		});
		Pane1.add(WantEqual);
		
		//��ӻ��尴ť
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
						JOptionPane.showMessageDialog(ChineseChessMainFrame.this, "����ɹ���");
					}
					else
					{
						JOptionPane.showMessageDialog(ChineseChessMainFrame.this, "����ʧ�ܣ�");
					}
			}
		});
		Pane1.add(WantBack);
		
	}
	
	/**�������ݳ�ʼ��
	 * @author ������
	 * ʱ�䣺20171201
	 */
	public static void DataInit(){
		MenuMode = 0;
		DoPlayer = '��';
		MyBoarder = new ChessBoarder();
		
		System.out.println(userInfo[0].getUserName() + "\t" + userInfo[0].getNumWin());
		System.out.println(userInfo[1].getUserName() + "\t" + userInfo[1].getNumWin());
	}
	/*
	 * ս���ز�
	 * 
	 */
	public static void reDisplay()
	{
		
		int n=JOptionPane.showConfirmDialog(null, "�Ƿ�ս���ز���","����",JOptionPane.YES_NO_CANCEL_OPTION);
		if(n==0)  //ս���ز�
		{
			DataInit();
			InfBoard.Clear();
			MyCanvas.SendWinner('��', userInfo);
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
			int t = JOptionPane.showConfirmDialog(null, "�Ƿ�Ҫ���¿�ʼ", "�ز�����",JOptionPane.YES_NO_CANCEL_OPTION);
			if(t==0)
			{
				DataInit();
				InfBoard.Clear();
				InfBoard.AddLog("�췽ִ��");
				MyCanvas.SendWinner('��', userInfo);
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
