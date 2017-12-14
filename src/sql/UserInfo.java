package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class UserInfo {
	private int userId = 0;
	private String userName = null;
	private String userPassword = null;
	private int numWin = 0;
	private int numLose = 0;
	private int numPeace = 0;
	//private Date submissionDate = null;
	public void update(String s)
	{
		Connection conn;
		String sql;
		ResultSet rs;
		Statement pstmt;
		try {
			conn = DriverManager.getConnection(MysqlInfo.DB_URL,MysqlInfo.USER,MysqlInfo.PASS);
			pstmt = conn.createStatement();  
		
		
        //ResultSet rs=pstmt.executeQuery(sql));
			switch(s)
			{
			case "win":
				this.setNumWin(this.getNumWin()+1);
				sql = "update user_info set num_win=num_win+1 where user_id=" + userId + ";";
				int temp = pstmt.executeUpdate(sql);
				System.out.println("------------------"+ temp + "------------------------");
				break;
			case "lose":
				this.setNumLose(this.getNumLose()+1);
				sql = "update user_info set num_lose=num_win+1 where user_id=" + userId + ";";
				pstmt.executeUpdate(sql);
				break;
			case "peace":
				this.setNumPeace(this.getNumPeace()+1);
				sql = "update user_info set num_peace=num_win+1 where user_id=" + userId + ";";
				pstmt.executeUpdate(sql);
				break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public int getNumWin() {
		return numWin;
	}
	public void setNumWin(int numWin) {
		this.numWin = numWin;
	}
	public int getNumLose() {
		return numLose;
	}
	public void setNumLose(int numLose) {
		this.numLose = numLose;
	}
	public int getNumPeace() {
		return numPeace;
	}
	public void setNumPeace(int numPeace) {
		this.numPeace = numPeace;
	}
//	public Date getSubmissionDate() {
//		return submissionDate;
//	}
//	public void setSubmissionDate(Date submissionDate) {
//		this.submissionDate = submissionDate;
//	}
	
	
}
