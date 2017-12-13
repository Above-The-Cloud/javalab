package window;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Audio.MP3;
import chessBoard.ChessBoarder;

public class ChineseChessApp extends JFrame 
	implements ActionListener, MouseListener
	
{
	/**
	 * 入口类
	 * 
	 * 游戏模式
	 * 1：双人对决
	 * 2：人机对决
	 * 3：排行榜
	 * 4.关于
	 * 5：退出游戏
	 */
	private JLabel Menu1, Menu2, Menu3, Menu4, Menu5;
	
	public ChineseChessApp()
	{
		setIconImage(Toolkit.getDefaultToolkit().getImage(ChineseChessMainFrame.class.getResource("/imageLibary/black-jiang.png")));
		setTitle("中国象棋");
		setBounds(0, 0, 1366, 768);
		setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null); 
		addBackgroundImage("imageLibary\\background.png");
		
		JPanel contentPane = (JPanel)this.getContentPane();
		
		Menu1 = new JLabel("");
		Menu1.setOpaque(false);
		Menu2 = new JLabel("");
		Menu2.setOpaque(false);
		Menu3 = new JLabel("");
		Menu3.setOpaque(false);
		Menu4 = new JLabel("");
		Menu4.setOpaque(false);
		Menu5 = new JLabel("");
		Menu5.setOpaque(false);
		
		Menu1.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image\\Menu1_0.png")));
		Menu1.addMouseListener(this);		
		Menu1.setBounds(460, 160,294,62);
		contentPane.add(Menu1);
		
		Menu2.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image\\Menu2_0.png")));
		Menu2.addMouseListener(this);		
		Menu2.setBounds(460, 260,294,62);
		contentPane.add(Menu2);
		
		Menu3.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image\\Menu3_0.png")));
		Menu3.addMouseListener(this);		
		Menu3.setBounds(460, 360,294,62);
		contentPane.add(Menu3);
		
		Menu4.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image\\Menu4_0.png")));
		Menu4.addMouseListener(this);		
		Menu4.setBounds(460, 460,294,62);
		contentPane.add(Menu4);
		
		Menu5.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image\\Menu5_0.png")));
		Menu5.addMouseListener(this);		
		Menu5.setBounds(460, 560,294,62);
		contentPane.add(Menu5);
	}
	
	public void addBackgroundImage(String s)
	{  
	    //实例化一个ImageIcon图标类的对象  
	    ImageIcon image = new ImageIcon(s);  
	    //实例化一个标签类的对象  
	    JLabel background = new JLabel(image);   
	    //设置标签显示的位置和大小  
	    background.setBounds(0,0,image.getIconWidth(),image.getIconHeight());  
	    //将标签添加到窗体的第二层面板上  
	    getLayeredPane().add(background,new Integer(Integer.MIN_VALUE));  
	    //获取窗体的第一层板对象  
	    JPanel contentPanel = (JPanel)this.getContentPane();  
	    //设置第一层面板为透明  
	    contentPanel.setOpaque(false);  
	     
	}		

	public void actionPerformed(ActionEvent evt)
	{
		
	}
	
    public void mouseClicked(MouseEvent evt) 
    {// 单击鼠标时执行的操作  
    	Object source = evt.getSource();
 	   	if(source == Menu1) {
 	   		LoginFrame lf = new LoginFrame(this);
 	   		try {
				lf.init();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	//this.setVisible(false);
			
			//MP3 BgMusic = new MP3(ChineseChessMainFrame.class.getResource("music\\bgm.mp3").getPath().substring(1));
			//BgMusic.play();    
 	   	}
 	   	if(source == Menu4) {
 	   		AboutDialog dialog = new AboutDialog(this);
 	   		dialog.setVisible(true);
 	   		dialog.setBounds(420,420,565,430);
 	   	}
 	   if(source == Menu5) {
 		   System.exit(0);
 	   }
 	   
 	   	
    }  
  
    public void mouseEntered(MouseEvent evt) 
    {// 鼠标进入组件时执行的操作  
 	   	Object source = evt.getSource();
 	   	if(source == Menu1) {
 	   		Menu1.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image\\Menu1_1.png")));
 	   	}
 	   	if(source == Menu2) {
 	   		Menu2.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image\\Menu2_1.png")));
 	   	}
 	   	if(source == Menu3) {
 	   		Menu3.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image\\Menu3_1.png")));
 	   	}
 	   	if(source == Menu4) {
 	   		Menu4.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image\\Menu4_1.png")));
 	   	}
 	   	if(source == Menu5) {
 	   		Menu5.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image\\Menu5_1.png")));
 	   	}
 	   	
 	 }  
  
    public void mouseExited(MouseEvent evt) 
    {// 鼠标离开组件时执行的操作  
 	   	Object source = evt.getSource();
 	   	if(source == Menu1) {
 	   		Menu1.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image\\Menu1_0.png")));
 	   	}
 	   	if(source == Menu2) {
 	   		Menu2.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image\\Menu2_0.png")));
 	   	}
 	   	if(source == Menu3) {
 	   		Menu3.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image\\Menu3_0.png")));
 	   	}
 	   	if(source == Menu4) {
 	   		Menu4.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image\\Menu4_0.png")));
 	   	}
 	   	if(source == Menu5) {
 	   		Menu5.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image\\Menu5_0.png")));
 	   	}

    }    
  
    public void mousePressed(MouseEvent evt) 
    {// 鼠标在组件上按下时执行的操作  
  
    }  
  
    public void mouseReleased(MouseEvent evt) 
    {// 鼠标释放时执行的操作  
    }  	
    
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					ChineseChessApp frame = new ChineseChessApp();
					frame.setVisible(true);
					MP3 BgMusic = new MP3(ChineseChessMainFrame.class.getResource("/music/bgm.mp3").getPath().substring(1));
					BgMusic.play();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}


