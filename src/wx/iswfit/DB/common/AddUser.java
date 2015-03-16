package wx.iswfit.DB.common;

import java.sql.SQLException;

public class AddUser {

	ConMysqlDao csd;
	// UserBean user = new UserBean();
	private String sql = "INSERT INTO userinfo(username,password) VALUE(?,?)";
	//private String sql = "INSERT INTO userinfo(username,password,userphone) VALUE(?,?,?)";

	//空构造方法
	public AddUser() {

	}

	public int add(UserBean user) {
		// this.user = user;
		csd = new ConMysqlDao();
		int i = 0;
		try {
			csd.prepareConnection();
			csd.con.setAutoCommit(false);
			csd.ps = csd.con.prepareStatement(sql);
			csd.ps.setString(1, user.getUsername());
			csd.ps.setString(2, user.getPassword());
			//csd.ps.setString(3, user.getUserphone());
			
			i = csd.ps.executeUpdate();
			csd.con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			csd.rollback();
			e.printStackTrace();
		} finally {
			csd.close();
		}

		return i;
	}
}
