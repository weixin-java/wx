package wx.iswfit.DB.common;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetAllUser {

	ConMysqlDao csd;
	// UserBean user = new UserBean();
	private String sql = "SELECT * FROM userinfo";

	public GetAllUser() {

	}

	public List<UserBean> allUser() {
		List<UserBean> users = new ArrayList<UserBean>();
		csd = new ConMysqlDao();
		try {
			csd.prepareConnection();
			csd.ps = csd.con.prepareStatement(sql);
			ResultSet rs = csd.ps.executeQuery();

			while (rs.next()) {
				UserBean user = new UserBean();
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setUserphone(rs.getString("userphone"));

				// ���ҵ��ļ�¼��ӽ�users������
				users.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			csd.close();
		}

		return users;
	}
}
