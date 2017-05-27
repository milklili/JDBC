package data;

import java.util.Date;

public class User {
	//初始化给一些初始值
	private int UserID = -1;
	private String UserName = "";
	private String Pwd = "111111";
	// @SuppressWarnings("deprecation")
	@SuppressWarnings({ "unused", "deprecation" })
	private Date date = new Date(1900, 1, 1, 1, 1, 1);
	public User() {
	}

	public User(String name, String password, Date day) {
		UserName = name;
		Pwd = password;
		date = day;
	}

	public void setUserName(String name) {
		UserName = name;
	}

	public String getUserName() {
		return UserName;
	}

	public void setPassword(String pwd) {
		Pwd = pwd;
	}

	public String getPassword() {
		return Pwd;
	}
	
	public int getUserID(){
		return UserID;
	}
	
}
