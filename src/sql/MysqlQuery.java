package sql;

import java.sql.*;

public class MysqlQuery {
	/**
	 * @author 汪春雨
	 * @time 20171207
	 * 数据库查询调用
	 * 
	 * 
	 */
	// JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = MysqlInfo.JDBC_DRIVER;  
    static final String DB_URL = MysqlInfo.DB_URL;
 
    // 数据库的用户名与密码
    static final String USER = MysqlInfo.USER;
    static final String PASS = MysqlInfo.PASS;
    
    public MysqlInfo mysql_Query(String sql)
    {
    	MysqlInfo mi = new MysqlInfo();
    	Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try{
            // 注册 JDBC 驱动
            Class.forName("com.mysql.jdbc.Driver");
        
            // 打开链接
            //System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
        
            // 执行查询
            //System.out.println(" 实例化Statement对...");
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        
            
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
        return mi;
    }
    
    public String signUp(String userName, String userPassword)
    {
    	String rt = "ok";
    	Connection conn = null;
    	PreparedStatement ptmt = null;
        ResultSet rs = null;
        try{
            // 注册 JDBC 驱动
            Class.forName("com.mysql.jdbc.Driver");
        
            // 打开链接
            //System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            String presql = "SELECT * FROM user_info "
            		+" WHERE user_name=?;";
            ptmt = conn.prepareStatement(presql); //预编译SQL，减少sql执行
            ptmt.setString(1, userName);
            rs = ptmt.executeQuery();
            if(!rs.next())
            {
            	String sql = "INSERT INTO user_info"
                		+"(user_name, user_password, num_win, num_lose, num_peace, submission_time) "
                		+"VALUES "
                		+"(?, ?, 0, 0, 0, CURRENT_DATE());";
                // 执行查询
                //System.out.println(" 实例化Statement对...");
                ptmt = conn.prepareStatement(sql); //预编译SQL，减少sql执行
                ptmt.setString(1, userName);
                ptmt.setString(2, userPassword);
                
                ptmt.executeUpdate();
                
            }
            else rt = "error";
            
            
            // 完成后关闭
            rs.close();
            ptmt.close();
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
                if(ptmt!=null) ptmt.close();
            }catch(SQLException se2){
            }// 什么都不做
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
    	return rt;
    }
    
    
}
