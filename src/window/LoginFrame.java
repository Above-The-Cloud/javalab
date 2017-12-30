package window;

import java.awt.*; 
 
import java.awt.event.*;  
import javax.swing.*;  
import java.util.*;  
import java.io.*;  
import java.sql.*;  
import sql.*;
public class LoginFrame {
	
	public ChineseChessApp cca =  null;
	
	 public LoginFrame(ChineseChessApp cca) {
		super();
		userInfo[0] = new UserInfo();
		userInfo[1] = new UserInfo();
		this.cca = cca;
	}

	// 登录界面的GUI组件  
	// JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = MysqlInfo.JDBC_DRIVER;  
    static final String DB_URL = MysqlInfo.DB_URL;
 
    // 数据库的玩家名与密码
    static final String USER = MysqlInfo.USER;
    static final String PASS = MysqlInfo.PASS;
    
    public UserInfo[] userInfo = new UserInfo[2]; 
    
    private JFrame jf = new JFrame("登录");
    
    
    private JPanel jp1=new JPanel();  
    private JPanel jp2=new JPanel(); 
    
    private JPanel jp3=new JPanel();
    
    private JPanel jp4=new JPanel();  
    private JPanel jp5=new JPanel();
    private JButton signUpButton=new JButton(new ImageIcon("Image\\register(1).png")); 
   // private DiyButton signUpButton=new DiyButton("Image\\register(1).png","Image\\register(2).png"); 
    private JTextField userField1 = new JTextField(20);  
    private JPasswordField passField1 = new JPasswordField(20);
    
    private JTextField userField2 = new JTextField(20);  
    private JPasswordField passField2 = new JPasswordField(20);
    
    private JButton startButton = new JButton(new ImageIcon("Image\\start(1).png")); 
    //private DiyButton startButton = new DiyButton("Image\\start(1).png","Image\\start(2).png");
    public void init() throws Exception  
    {  
         
        // 加载驱动  
    	
    	jf.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    	jf.addWindowListener(new WindowAdapter() {
    	public void windowClosing(WindowEvent e) {
    		jf.dispose();
    		cca.setVisible(true);
    	}
    	});
        Class.forName("com.mysql.jdbc.Driver");  
        // 为登录按钮添加事件监听器  
        startButton.addActionListener(e -> {  
            // 登录成功则显示“登录成功”  
            System.out.println(userField1.getText() +"\t"+String.valueOf(passField1.getPassword())+"\t"+validate(userField1.getText(), String.valueOf(passField1.getPassword())));  
            System.out.println(userField2.getText() +"\t"+String.valueOf(passField2.getPassword())+"\t"+validate(userField2.getText(), String.valueOf(passField2.getPassword()))); 
            
            if(userField1.getText().equals(userField2.getText()))
            {
            	JOptionPane.showMessageDialog(jf, "玩家1和玩家2不可相同！"); 
            }
            else if (validate(userField1.getText(), String.valueOf(passField1.getPassword()))&&validate(userField2.getText(), String.valueOf(passField2.getPassword())))  
            {  
              
				LoadingFrame loadframe = new LoadingFrame();
                jf.setVisible(false); 
                cca.setVisible(false);
				loadframe.setVisible(true);
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				loadframe.dispose();

                setUserInfo(userField1.getText(), userField2.getText());
                ChineseChessMainFrame ccmf = new ChineseChessMainFrame(this);
    			ccmf.setVisible(true);  
                
            }  
            // 否则显示“登录失败”  
            else  
            {  	
            	if((!validate(userField1.getText(), String.valueOf(passField1.getPassword())))&&(!validate(userField2.getText(), String.valueOf(passField2.getPassword()))))
                	JOptionPane.showMessageDialog(jf, "登录失败，玩家1,玩家2信息输入错误");  
            	else if(!validate(userField1.getText(), String.valueOf(passField1.getPassword())))
                	JOptionPane.showMessageDialog(jf, "登录失败，玩家1信息输入错误");  
            	else if(!validate(userField2.getText(), String.valueOf(passField2.getPassword())))
                	JOptionPane.showMessageDialog(jf, "登录失败，玩家2信息输入错误");  
            }  
        }); 
        signUpButton.addActionListener(e -> {  
            // 注册
            System.out.println(userField1.getText() +"\t"+String.valueOf(passField1.getPassword())+"\t"+validate(userField1.getText(), String.valueOf(passField1.getPassword())));  
            try {  
            	
            	//jf.setVisible(false); 
            	new SignUpFrame().init();
                //jf.setVisible(true); 
                
            } catch (Exception e1) {  
                // TODO Auto-generated catch block  
                e1.printStackTrace();  
            }  
            
        });  



		jf.setIconImage(Toolkit.getDefaultToolkit().getImage(ChineseChessMainFrame.class.getResource("/imageLibary/black-jiang.png")));
		jf.setBounds(0, 0, 1366, 768);
		jf.setLayout(null);
		jf.setResizable(false);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setLocationRelativeTo(null); 
		addBackgroundImage("image//login_bgp.jpg");
		
		JPanel contentPane = (JPanel)jf.getContentPane();
        userField1.setFont(new Font("宋体", Font.PLAIN, 30));
        userField1.setForeground(Color.gray);
        userField1.setBounds(486, 103, 457, 60);
        userField1.setBorder(null);
        userField1.setOpaque(false);
        userField1.setText("红方用户名");
        userField1.setToolTipText("红方 用户名"); 
        
        userField1.addMouseListener(new MouseAdapter() {
        	   @Override
        	   public void mouseEntered(MouseEvent e) {
        		   if("红方用户名".equalsIgnoreCase(userField1.getText())){
        			   userField1.setText("");
        		    }
        	   }
        	   
        	   public void mouseExited(MouseEvent arg0){
   				//设置图片为未选中状态
        		   	if("".equals(userField1.getText().trim())){
        		   		userField1.setText("红方用户名");
        		    }
   				}
        	   
        });
        userField1.addFocusListener(new FocusListener (){
        	@Override
        	public void focusGained(FocusEvent e) {
        		if("红方用户名".equalsIgnoreCase(userField1.getText())){
        			userField1.setText("");
        		}

        	  }

        	        @Override
        	 public void focusLost(FocusEvent e) {
                 if("".equals(userField1.getText()) ){
                	 userField1.setText("红方用户名");
       	          }
        	  }

          });
        
        contentPane.add(userField1);  
        
        
        passField1.setBounds(486, 188, 457, 60);
        passField1.setFont(new Font("宋体", Font.PLAIN, 30));
        passField1.setForeground(Color.gray);
        passField1.setBorder(null);
        passField1.setOpaque(false);
        passField1.setToolTipText("红方密码");  
        passField1.setEchoChar((char)0);
        passField1.setText("红方密码");
        passField1.addMouseListener(new MouseAdapter() {
     	   @Override
     	   public void mouseEntered(MouseEvent e) {
     		   if("红方密码".equalsIgnoreCase(passField1.getText())){
     			  passField1.setText("");
     			  passField1.setEchoChar('*');
     		    }
     	   }
     	   
     	   public void mouseExited(MouseEvent arg0){
				//设置图片为未选中状态
     		   	if("".equals(passField1.getText().trim())){
     		   		passField1.setEchoChar((char)0);
     		   		passField1.setText("红方密码");
     		    }
			}
     });
        
      passField1.addFocusListener(new FocusListener (){
        	@Override
        	public void focusGained(FocusEvent e) {
        		if("红方密码".equalsIgnoreCase(passField1.getText())){
       			  passField1.setText("");
       			  passField1.setEchoChar('*');
       		    }

        	  }

        	        @Override
        	 public void focusLost(FocusEvent e) {
                 if("".equals(passField1.getText()) ){
                	 passField1.setEchoChar((char)0);
      		   		 passField1.setText("红方密码");
       	          }
        	  }

          });
        
        contentPane.add(passField1);  
        
        userField2.setFont(new Font("宋体", Font.PLAIN, 30));
        userField2.setForeground(Color.gray);
        userField2.setText("黑方用户名");
        userField2.setBounds(486, 303, 457, 60);
        userField2.setBorder(null);
        userField2.setOpaque(false);
        userField2.setToolTipText("黑方 用户名"); 
        userField2.addMouseListener(new MouseAdapter() {
     	   @Override
     	   public void mouseEntered(MouseEvent e) {
     		   if("黑方用户名".equalsIgnoreCase(userField2.getText())){
     			  userField2.setText("");
     		    }
     	   }
     	   
     	   public void mouseExited(MouseEvent arg0){
				//设置图片为未选中状态
     		   	if("".equals(userField2.getText().trim())){
     		   		userField2.setText("黑方用户名");
     		    }
			}
        });
        
      userField2.addFocusListener(new FocusListener (){
        	@Override
        	public void focusGained(FocusEvent e) {
        		if("黑方用户名".equalsIgnoreCase(userField2.getText())){
        			userField2.setText("");
        		}

        	  }

        	        @Override
        	 public void focusLost(FocusEvent e) {
                 if("".equals(userField2.getText()) ){
                	 userField2.setText("黑方用户名");
       	          }
        	  }

          });
        contentPane.add(userField2);
        
        passField2.setBounds(486, 388, 457, 60);
        passField2.setFont(new Font("宋体", Font.PLAIN, 30));
        passField2.setForeground(Color.gray);
        passField2.setBorder(null);
        passField2.setOpaque(false);
        passField2.setToolTipText("黑方密码");  
        passField2.setEchoChar((char)0);
        passField2.setText("黑方密码");
        passField2.addMouseListener(new MouseAdapter() {
      	   @Override
      	   public void mouseEntered(MouseEvent e) {
      		   if("黑方密码".equalsIgnoreCase(passField2.getText())){
      			   passField2.setText("");
      			   passField2.setEchoChar('*');
      		    }
      	   }
      	   
      	   public void mouseExited(MouseEvent arg0){
 				//设置图片为未选中状态
      		   	if("".equals(passField2.getText().trim())){
      		   		passField2.setEchoChar((char)0);
      		   		passField2.setText("黑方密码");
      		    }
 			}
      });
        
        passField2.addFocusListener(new FocusListener (){
        	@Override
        	public void focusGained(FocusEvent e) {
        		if("黑方密码".equalsIgnoreCase(passField2.getText())){
       			  passField2.setText("");
       			  passField2.setEchoChar('*');
       		    }

        	  }

        	        @Override
        	 public void focusLost(FocusEvent e) {
                 if("".equals(passField2.getText()) ){
                	 passField2.setEchoChar((char)0);
      		   		 passField2.setText("黑方密码");
       	          }
        	  }

          });
        contentPane.add(passField2);  

        signUpButton.setBounds(486, 550, 244, 86);
        signUpButton.setRolloverIcon(new ImageIcon("Image\\register(2).png"));  
        signUpButton.setPreferredSize(new Dimension(200,62));
        signUpButton.setContentAreaFilled(false);
        signUpButton.setOpaque(false);
        signUpButton.setBorderPainted(false);
        signUpButton.setFocusPainted(false);     
        
        
        startButton.setBounds(686, 550, 244, 86);
        startButton.setRolloverIcon(new ImageIcon("Image\\start(2).png"));  
        startButton.setPreferredSize(new Dimension(200,62));
        startButton.setContentAreaFilled(false);
        startButton.setOpaque(false);
        startButton.setBorderPainted(false);
        startButton.setFocusPainted(false);
        
        contentPane.add(signUpButton);  
        contentPane.add(startButton);
        
        jf.setVisible(true); 
        
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
	    jf.getLayeredPane().add(background,new Integer(Integer.MIN_VALUE));  
	    //获取窗体的第一层板对象  
	    JPanel contentPanel = (JPanel)jf.getContentPane();  
	    //设置第一层面板为透明  
	    contentPanel.setOpaque(false);  
	     
	}		

    //判断数据库中是否有该玩家名和密码  
    private boolean validate(String userName, String userPass)  
    {  
        String sql="select *from user_info where user_name='"+userName+"' and user_password='"+userPass+"'";  
        try(  
        		Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
        		Statement pstmt = conn.createStatement();  
                ResultSet rs=pstmt.executeQuery(sql))  
          
        {   //如果查询的ResultSet里有超过一条的记录，则登录成功  
                if (rs.next())  
                {  
                    return true;  
                }  
              
        }  
        catch(Exception e)  
        {  
            e.printStackTrace();  
        }  
        return false;  
    }  
    public UserInfo[] getUserInfo()
    {
    	return this.userInfo;
    }
    public void setUserInfo(String user1Name, String user2Name){
    	
    	
    	String sql1="select *from user_info where user_name='"+user1Name+"'";  
    	String sql2="select *from user_info where user_name='"+user2Name+"'";
        try{ 
        		Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
        		Statement pstmt = conn.createStatement();  
                ResultSet rs=pstmt.executeQuery(sql1);  
           
                if (rs.next())  
                {  
                    userInfo[0].setUserId(rs.getInt("user_id"));
                    userInfo[0].setUserName(rs.getString("user_name"));
                    userInfo[0].setUserPassword(rs.getString("user_password"));
                    userInfo[0].setNumWin(rs.getInt("num_win"));
                    userInfo[0].setNumLose(rs.getInt("num_lose"));
                    userInfo[0].setNumPeace(rs.getInt("num_peace"));
                }  
               rs=pstmt.executeQuery(sql2); 
               if (rs.next())  
               {  
                   userInfo[1].setUserId(rs.getInt("user_id"));
                   userInfo[1].setUserName(rs.getString("user_name"));
                   userInfo[1].setUserPassword(rs.getString("user_password"));
                   userInfo[1].setNumWin(rs.getInt("num_win"));
                   userInfo[1].setNumLose(rs.getInt("num_lose"));
                   userInfo[1].setNumPeace(rs.getInt("num_peace"));
               }  
                
              
        }  
       
        catch(Exception e)  
        {  
            e.printStackTrace();  
        }  
    }
    public void backToEntrance()
    {
    	this.jf.dispose();
    	cca.setVisible(true);
    }
    //for test
//    public static void main(String[] args) throws Exception  
//    {  
//    	LoginFrame lf = new LoginFrame();
//        lf.init();
//        
//        
//    } 
}


