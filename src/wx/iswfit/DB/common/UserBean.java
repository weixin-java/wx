package wx.iswfit.DB.common;

public class UserBean {

	private Integer id;
	private String username;
	private String password;
	private String userphone;

	// �յĹ��췽��
	public UserBean() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserphone() {
		return userphone;
	}

	public void setUserphone(String userphone) {
		this.userphone = userphone;
	}

}
