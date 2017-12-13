package sql;

import java.util.Date;

public class UserInfo {
	private int userId = 0;
	private String userName = null;
	private String userPassword = null;
	private int numWin = 0;
	private int numLose = 0;
	private int numPeace = 0;
	//private Date submissionDate = null;
	
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
