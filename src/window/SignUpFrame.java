
package window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import sql.MysqlInfo;

public class SignUpFrame {
	static final String JDBC_DRIVER = MysqlInfo.JDBC_DRIVER;  
    static final String DB_URL = MysqlInfo.DB_URL;
 
    // 数据库的玩家名与密码
    static final String USER = MysqlInfo.USER;
    static final String PASS = MysqlInfo.PASS;
    
	JFrame jf = new JFrame("注册");
	JPanel jp1=new JPanel();  
    JPanel jp2=new JPanel();
    JPanel jp5=new JPanel();
    JPanel bottomButton=new JPanel();  
    JButton signUpButton=new JButton(new ImageIcon("Image\\register(1).png")); 
    
    JTextField userField1 = new JTextField(20);  
    JPasswordField passField1 = new JPasswordField(20);
    public void init(){
    	class myWindowListener extends WindowAdapter{  
  		  
            @Override  
            public void windowClosing(WindowEvent e) {  
                  
                System.exit(0);  
            }  
              
        }
    	
		jf.setIconImage(Toolkit.getDefaultToolkit().getImage(ChineseChessMainFrame.class.getResource("/imageLibary/black-jiang.png")));
		jf.setLayout(null);
		jf.setBounds(0, 0, 762, 482);
		System.out.println("sign up");
		jf.setResizable(false);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setLocationRelativeTo(null); 
		addBackgroundImage("image//register_bgp.jpg");
    	
    	jf.addWindowListener(new myWindowListener());  
    	JPanel contentPane = (JPanel)jf.getContentPane();
    	contentPane.add(signUpButton);
    	jf.setVisible(true); 
    	
        
        userField1.setBounds(175, 90, 457, 60);
        userField1.setFont(new Font("宋体", Font.PLAIN, 30));
        userField1.setForeground(Color.gray);
        userField1.setBorder(null);
        userField1.setOpaque(false);
        userField1.setText("用户名");
        userField1.setToolTipText("用户名"); 
        
        userField1.addMouseListener(new MouseAdapter() {
        	   @Override
        	   public void mouseEntered(MouseEvent e) {
        		   if("用户名".equalsIgnoreCase(userField1.getText())){
        			   userField1.setText("");
        		    }
        	   }
        	   
        	   public void mouseExited(MouseEvent arg0){
   				//设置图片为未选中状态
        		   	if("".equals(userField1.getText().trim())){
        		   		userField1.setText("用户名");
        		    }
   				}
        	   
        });
        userField1.addFocusListener(new FocusListener (){
        	@Override
        	public void focusGained(FocusEvent e) {
        		if("用户名".equalsIgnoreCase(userField1.getText())){
        			userField1.setText("");
        		}

        	  }

        	        @Override
        	 public void focusLost(FocusEvent e) {
                 if("".equals(userField1.getText()) ){
                	 userField1.setText("用户名");
       	          }
        	  }

          });
    	contentPane.add(userField1);  
    	
        passField1.setBounds(175, 194, 457, 60);
        passField1.setFont(new Font("宋体", Font.PLAIN, 30));
        passField1.setForeground(Color.gray);
        passField1.setBorder(null);
        passField1.setOpaque(false);
        passField1.setToolTipText("密码");  
        passField1.setEchoChar((char)0);
        passField1.setText("密码");
        passField1.addMouseListener(new MouseAdapter() {
     	   @Override
     	   public void mouseEntered(MouseEvent e) {
     		   if("密码".equalsIgnoreCase(passField1.getText())){
     			  passField1.setText("");
     			  passField1.setEchoChar('*');
     		    }
     	   }
     	   
     	   public void mouseExited(MouseEvent arg0){
				//设置图片为未选中状态
     		   	if("".equals(passField1.getText().trim())){
     		   		passField1.setEchoChar((char)0);
     		   		passField1.setText("密码");
     		    }
			}
     });
        
        passField1.addFocusListener(new FocusListener (){
        	@Override
        	public void focusGained(FocusEvent e) {
        		if("密码".equalsIgnoreCase(passField1.getText())){
       			  passField1.setText("");
       			  passField1.setEchoChar('*');
       		    }

        	  }

        	        @Override
        	 public void focusLost(FocusEvent e) {
                 if("".equals(passField1.getText()) ){
                	 passField1.setEchoChar((char)0);
      		   		 passField1.setText("密码");
       	          }
        	  }

          });
        contentPane.add( passField1);  
    	
        signUpButton.setBounds(230, 310, 244, 60);
        signUpButton.setRolloverIcon(new ImageIcon("Image\\register(2).png"));  
        signUpButton.setPreferredSize(new Dimension(200,62));
        signUpButton.setContentAreaFilled(false);
        signUpButton.setOpaque(false);
        signUpButton.setBorderPainted(false);
        signUpButton.setFocusPainted(false);     
    	contentPane.add(signUpButton); 
    	
        signUpButton.addActionListener(e -> {  
        	System.out.println(userField1.getText() +"\t"+String.valueOf(passField1.getPassword())+"\t");  
        	try {
				cheak(userField1.getText(), String.valueOf(passField1.getPassword()));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	});
    }
  //注册玩家和密码 （1）首先先检查数据库中是否有相应的数据，如果有的话提示"该玩家存在，请直接登录。"  
    private void cheak(String userName, String userPass) throws Exception  
    {      
        if (validate(userField1.getText()))  
        {  
            JOptionPane.showMessageDialog(jf, "该账号已存在！");  
        }  
  
        else  
        {  
            String sql="insert ignore into user_info(user_name, user_password, num_win, num_lose, num_peace, submission_time) values(?,?,0,0,0, CURRENT_DATE())";  
            Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
            PreparedStatement pstmt = conn.prepareStatement(sql);  
            pstmt.setString(1, userName);  
            pstmt.setString(2, userPass);  
            pstmt.executeUpdate();  
            JOptionPane.showMessageDialog(jf, "注册成功请登录。。。。");  
            jf.setVisible(false);
        }  
              
          
    }  
        private boolean validate(String userName)  
        {  
            String sql="select *from user_info where user_name='"+userName+"'";  
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
   
}
