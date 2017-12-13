package window;

import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
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
    JButton signUpButton=new JButton("注册"); 
    
    JTextField userField1 = new JTextField(20);  
    JPasswordField passField1 = new JPasswordField(20);
    public void init(){
    	class myWindowListener extends WindowAdapter{  
  		  
            @Override  
            public void windowClosing(WindowEvent e) {  
                  
                System.exit(0);  
            }  
              
        }
    	
    	jf.addWindowListener(new myWindowListener());  
        jp1.add(new Label("玩家1："));  
        jp1.add(userField1);  
        jp2.add(new Label("密码  ："));  
        jp2.add(passField1); 
        bottomButton.add(signUpButton);  
        jf.setLayout(new GridLayout(4,1,1,1));
        jf.add(jp1);  
        jf.add(jp2);
        jf.add(jp5);
        jf.add(bottomButton);  
        jf.pack();  
        jf.setVisible(true);  
        jf.setLocation(600, 400);
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
   
}
