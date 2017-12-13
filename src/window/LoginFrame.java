package window;

import java.awt.*;  
import java.awt.event.*;  
import javax.swing.*;  
import java.util.*;  
import java.io.*;  
import java.sql.*;  
import sql.*;
public class LoginFrame {
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
    private JPanel bottomButton=new JPanel();  
    private JButton signUpButton=new JButton("注册"); 
    
    private JTextField userField1 = new JTextField(20);  
    private JTextField passField1 = new JTextField(20);
    
    private JTextField userField2 = new JTextField(20);  
    private JTextField passField2 = new JTextField(20);
    
    private JButton startButton = new JButton("开始"); 
    
    public void init() throws Exception  
    {  
         
        // 加载驱动  
    	
        Class.forName("com.mysql.jdbc.Driver");  
        // 为登录按钮添加事件监听器  
        startButton.addActionListener(e -> {  
            // 登录成功则显示“登录成功”  
            System.out.println(userField1.getText() +"\t"+passField1.getText()+"\t"+validate(userField1.getText(), passField1.getText()));  
            System.out.println(userField2.getText() +"\t"+passField2.getText()+"\t"+validate(userField2.getText(), passField2.getText()));  
            if (validate(userField1.getText(), passField1.getText())&&validate(userField2.getText(), passField2.getText()))  
            {  
                JOptionPane.showMessageDialog(jf, "登录成功");  
                
                jf.setVisible(false); 
                
                setUserInfo(userField1.getText(), userField2.getText());
                
            }  
            // 否则显示“登录失败”  
            else  
            {  	
            	if((!validate(userField1.getText(), passField1.getText()))&&(!validate(userField2.getText(), passField2.getText())))
                	JOptionPane.showMessageDialog(jf, "登录失败，玩家1,玩家2信息输入错误");  
            	else if(!validate(userField1.getText(), passField1.getText()))
                	JOptionPane.showMessageDialog(jf, "登录失败，玩家1信息输入错误");  
            	else if(!validate(userField2.getText(), passField2.getText()))
                	JOptionPane.showMessageDialog(jf, "登录失败，玩家2信息输入错误");  
            }  
        });  
        signUpButton.addActionListener(e -> {  
            // 注册
            System.out.println(userField1.getText() +"\t"+passField1.getText()+"\t"+validate(userField1.getText(), passField1.getText()));  
            try {  
            	
            	//jf.setVisible(false); 
            	new SignUpFrame().init();
                //jf.setVisible(true); 
                
            } catch (Exception e1) {  
                // TODO Auto-generated catch block  
                e1.printStackTrace();  
            }  
            
        });  
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
        jp3.add(new Label("    "));
        jp4.add(new Label("玩家2："));  
        jp4.add(userField2);  
        jp5.add(new Label("密码  ："));  
        jp5.add(passField2);
        bottomButton.add(signUpButton);  
        bottomButton.add(startButton); 
        jf.setLayout(new GridLayout(6,1,1,1));
        
  
        jf.add(jp1);  
        jf.add(jp2);
        jf.add(jp3);
        jf.add(jp4);
        jf.add(jp5);
        jf.add(bottomButton);  
        jf.pack();  
        jf.setVisible(true);  
        jf.setLocation(600, 400);
        
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
    	return userInfo;
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
    
    //for test
    public static void main(String[] args) throws Exception  
    {  
    	LoginFrame lf = new LoginFrame();
        lf.init();
        
        
    } 
}
