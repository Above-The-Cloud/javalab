package window;
import sql.*;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableCellRenderer;

import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class RankFrame {
	 static final String JDBC_DRIVER = MysqlInfo.JDBC_DRIVER;  
	    static final String DB_URL = MysqlInfo.DB_URL;
	 
	    // 数据库的用户名与密码
	    static final String USER = MysqlInfo.USER;
	    static final String PASS = MysqlInfo.PASS;
	    
	    
	//private Jframe jf=null;
	JPanel panel = new JPanel();
	JTable table;
	JScrollPane jscrollpane = new JScrollPane();

	Object[] columnNames = new Object[]{"排名","用户名","胜","败","平","胜率"};//列名
	Object[][] rowData = new Object[50][6];//行数，列数
	int index=0;
	
	public RankFrame() {
		super();
		this.getUserInfo();
		JFrame jf = new JFrame("排行榜");
		table = new JTable(rowData,columnNames);
		jscrollpane.setBounds(200, 100, 550, 650);
		jscrollpane.setViewportView(table);//这句很重要
		table.setRowHeight(30); 
		/**
		 * 字居中显示设置
		*/
		DefaultTableCellRenderer r = new DefaultTableCellRenderer();    
		r.setHorizontalAlignment(JLabel.CENTER);   
		table.setDefaultRenderer(Object.class,r);
		panel.add(jscrollpane);
		jf.add(panel);
		jf.setSize(500, 500);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		jf.setLocationRelativeTo(null);
	}
	
	public void getUserInfo()
	{

        Connection conn = null;
        Statement stmt = null;
        try{
        	MysqlQuery mq = new MysqlQuery();
        	String s = mq.signUp("cqj", "123321");
        	System.out.println(s);
            // 注册 JDBC 驱动
            Class.forName("com.mysql.jdbc.Driver");
        
            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
        
            // 执行查询
            System.out.println(" 实例化Statement对...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT user_id,user_name,user_password,num_win,num_lose,num_peace,num_win*1000/(num_win+num_lose+num_peace) as rank FROM user_info order by rank desc;";
            ResultSet rs = stmt.executeQuery(sql);
        
            // 展开结果集数据库
            while(rs.next()){
                // 通过字段检索
                int id  = rs.getInt("user_id");
                String name = rs.getString("user_name");
                String password = rs.getString("user_password");
                int num_win = rs.getInt("num_win");
                int num_lose = rs.getInt("num_lose");
                int num_peace = rs.getInt("num_peace");
                //Date datetime = rs.getDate("submission_time")
                rowData[index][0] = toString().valueOf(index+1);
                rowData[index][1] = name;
                rowData[index][2] = num_win;
                rowData[index][3] = num_lose;
                rowData[index][4] = num_peace;
                rowData[index][5] = String.format("%.2f", num_win*100.0/(num_win + num_lose + num_peace)) + "%";
                index++;
                // 输出数据
                System.out.print("id: " + id);
                System.out.print(", name: " + name);
                System.out.print(", password: " + password);
                System.out.print(", win: " + num_win);
                System.out.print(", lose: " + num_lose);
                System.out.print(", peace: " + num_peace);
                System.out.print("\n");
            }
            
            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }// 什么都不做
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        System.out.println("Goodbye!");
    
	}
//	public static void main(String[] args)
//	{
//		RankFrame r = new RankFrame();
//	}
	
}
