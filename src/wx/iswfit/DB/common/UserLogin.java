package wx.iswfit.DB.common;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserLogin {

	ConMysqlDao csd;
	// UserBean user = new UserBean();
	private String sql = "SELECT * FROM userinfo WHERE username=?";

	public UserLogin() {

	}

	public boolean login(UserBean user) {
		boolean flag = false;
		csd = new ConMysqlDao();
		try {
			csd.prepareConnection();
			csd.ps = csd.con.prepareStatement(sql);
			csd.ps.setString(1, user.getUsername());
			// csd.ps.setString(2, user.getPassword());
			ResultSet rs = csd.ps.executeQuery();
			while (rs.next()) {
				flag = user.getPassword().equals(rs.getString("password"));
			}
			// flag = csd.ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			csd.close();
		}

		return flag;
	}
}
