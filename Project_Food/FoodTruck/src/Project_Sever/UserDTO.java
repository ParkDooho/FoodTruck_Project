package Project_Sever;

/**
 * UserDTO Class
 * @author Location_based_services Server
 *
 */

public class UserDTO {
	private String userID;
	private String userPW;
	private String userName;
	private String userTel;
	private String userNum;
	
	/*
	 * UserDTO() 생성자 : UserDTO 클래스 기본생성자
	 */	
	
	public UserDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserDTO(String userID, String userPW, String userName, String userTel, String userNum) {
		super();
		this.userID = userID;
		this.userPW = userPW;
		this.userName = userName;
		this.userTel = userTel;
		this.userNum = userNum;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserPW() {
		return userPW;
	}

	public void setUserPW(String userPW) {
		this.userPW = userPW;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	public String getUserNum() {
		return userNum;
	}

	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}

	@Override
	public String toString() {
		return "UserDTO [userID=" + userID + ", userPW=" + userPW + ", userName=" + userName + ", userTel=" + userTel
				+ ", userNum=" + userNum + "]";
	}

	
	
	
	
}
