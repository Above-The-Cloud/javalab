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
 
    // ���ݿ�������������
    static final String USER = MysqlInfo.USER;
    static final String PASS = MysqlInfo.PASS;
    
	JFrame jf = new JFrame("ע��");
	JPanel jp1=new JPanel();  
    JPanel jp2=new JPanel();
    JPanel jp5=new JPanel();
    JPanel bottomButton=new JPanel();  
    JButton signUpButton=new JButton("ע��"); 
    
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
        jp1.add(new Label("���1��"));  
        jp1.add(userField1);  
        jp2.add(new Label("����  ��"));  
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
  //ע����Һ����� ��1�������ȼ�����ݿ����Ƿ�����Ӧ�����ݣ�����еĻ���ʾ"����Ҵ��ڣ���ֱ�ӵ�¼��"  
    private void cheak(String userName, String userPass) throws Exception  
    {      
        if (validate(userField1.getText()))  
        {  
            JOptionPane.showMessageDialog(jf, "���˺��Ѵ��ڣ�");  
        }  
  
        else  
        {  
            String sql="insert ignore into user_info(user_name, user_password, num_win, num_lose, num_peace, submission_time) values(?,?,0,0,0, CURRENT_DATE())";  
            Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
            PreparedStatement pstmt = conn.prepareStatement(sql);  
            pstmt.setString(1, userName);  
            pstmt.setString(2, userPass);  
            pstmt.executeUpdate();  
            JOptionPane.showMessageDialog(jf, "ע��ɹ����¼��������");  
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
   
}
