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

	// ��¼�����GUI���  
	// JDBC �����������ݿ� URL
    static final String JDBC_DRIVER = MysqlInfo.JDBC_DRIVER;  
    static final String DB_URL = MysqlInfo.DB_URL;
 
    // ���ݿ�������������
    static final String USER = MysqlInfo.USER;
    static final String PASS = MysqlInfo.PASS;
    
    public UserInfo[] userInfo = new UserInfo[2]; 
    
    private JFrame jf = new JFrame("��¼");
    
    
    private JPanel jp1=new JPanel();  
    private JPanel jp2=new JPanel(); 
    
    private JPanel jp3=new JPanel();
    
    private JPanel jp4=new JPanel();  
    private JPanel jp5=new JPanel();
    private JPanel bottomButton=new JPanel();  
    private JButton signUpButton=new JButton("ע��"); 
    
    private JTextField userField1 = new JTextField(20);  
    private JPasswordField passField1 = new JPasswordField(20);
    
    private JTextField userField2 = new JTextField(20);  
    private JPasswordField passField2 = new JPasswordField(20);
    
    private JButton startButton = new JButton("��ʼ"); 
    
    public void init() throws Exception  
    {  
         
        // ��������  
    	
    	jf.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    	jf.addWindowListener(new WindowAdapter() {
    	public void windowClosing(WindowEvent e) {
    		jf.dispose();
    		cca.setVisible(true);
    	}
    	});
        Class.forName("com.mysql.jdbc.Driver");  
        // Ϊ��¼��ť����¼�������  
        startButton.addActionListener(e -> {  
            // ��¼�ɹ�����ʾ����¼�ɹ���  
            System.out.println(userField1.getText() +"\t"+String.valueOf(passField1.getPassword())+"\t"+validate(userField1.getText(), String.valueOf(passField1.getPassword())));  
            System.out.println(userField2.getText() +"\t"+String.valueOf(passField2.getPassword())+"\t"+validate(userField2.getText(), String.valueOf(passField2.getPassword()))); 
            
            if(userField1.getText().equals(userField2.getText()))
            {
            	JOptionPane.showMessageDialog(jf, "���1�����2������ͬ��"); 
            }
            else if (validate(userField1.getText(), String.valueOf(passField1.getPassword()))&&validate(userField2.getText(), String.valueOf(passField2.getPassword())))  
            {  
                JOptionPane.showMessageDialog(jf, "��¼�ɹ�");  
                
                jf.setVisible(false); 
                cca.setVisible(false);
                setUserInfo(userField1.getText(), userField2.getText());
                ChineseChessMainFrame ccmf = new ChineseChessMainFrame(this);
    			ccmf.setVisible(true);
                
            }  
            // ������ʾ����¼ʧ�ܡ�  
            else  
            {  	
            	if((!validate(userField1.getText(), String.valueOf(passField1.getPassword())))&&(!validate(userField2.getText(), String.valueOf(passField2.getPassword()))))
                	JOptionPane.showMessageDialog(jf, "��¼ʧ�ܣ����1,���2��Ϣ�������");  
            	else if(!validate(userField1.getText(), String.valueOf(passField1.getPassword())))
                	JOptionPane.showMessageDialog(jf, "��¼ʧ�ܣ����1��Ϣ�������");  
            	else if(!validate(userField2.getText(), String.valueOf(passField2.getPassword())))
                	JOptionPane.showMessageDialog(jf, "��¼ʧ�ܣ����2��Ϣ�������");  
            }  
        });  
        signUpButton.addActionListener(e -> {  
            // ע��
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
        class myWindowListener extends WindowAdapter{  
  
            @Override  
            public void windowClosing(WindowEvent e) {  
                  
                System.exit(0);  
            }  
              
        }  
        
        jp1.add(new Label("���1��"));  
        jp1.add(userField1);  
        jp2.add(new Label("����  ��"));  
        jp2.add(passField1); 
        jp3.add(new Label("    "));
        jp4.add(new Label("���2��"));  
        jp4.add(userField2);  
        jp5.add(new Label("����  ��"));  
        jp5.add(passField2);
        bottomButton.add(signUpButton);  
        bottomButton.add(startButton); 
        jf.setLayout(new GridLayout(6,1,1,1));
        jf.setLocationRelativeTo(null);
  
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
    
    //�ж����ݿ����Ƿ��и������������  
    private boolean validate(String userName, String userPass)  
    {  
        String sql="select *from user_info where user_name='"+userName+"' and user_password='"+userPass+"'";  
        try(  
        		Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
        		Statement pstmt = conn.createStatement();  
                ResultSet rs=pstmt.executeQuery(sql))  
          
        {   //�����ѯ��ResultSet���г���һ���ļ�¼�����¼�ɹ�  
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
