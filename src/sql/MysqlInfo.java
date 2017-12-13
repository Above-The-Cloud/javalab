package sql;

import java.sql.*;

public class MysqlInfo {
	/**
	 * 
	 * @author 汪春雨
	 * @time 20171209
	 * 展示数据库的所有信息
	 * 
	 */
    // JDBC 驱动名及数据库 URL
    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    public static final String DB_URL = "jdbc:mysql://localhost:3306/Chess";
 
    // 数据库的用户名与密码，需要根据自己的设置
    public static final String USER = "root";
    public static final String PASS = "";
 
    public static void main(String[] args) {
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
            sql = "SELECT * FROM user_info";
            ResultSet rs = stmt.executeQuery(sql);
        
            // 展开结果集数据库
            while(rs.next()){
                // 通过字段检索
                int id  = rs.getInt("user_id");
                String name = rs.getString("user_name");
                String password = rs.getString("user_password");
                String num_win = rs.getString("num_win");
                String num_lose = rs.getString("num_lose");
                String num_peace = rs.getString("num_peace");
                //Date datetime = rs.getDate("submission_time")
    
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
}