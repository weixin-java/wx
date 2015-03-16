package wx.iswfit.DB.common;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GetIdFoName {

	public GetIdFoName() {

	}

	ConMysqlDao csd;
	private String sql = "SELECT * FROM userinfo WHERE username=?";

	public int getId(UserBean user) {
		int i = 0;
		csd = new ConMysqlDao();
		try {
			csd.prepareConnection();
			csd.ps = csd.con.prepareStatement(sql);
			csd.ps.setString(1, user.getUsername());
			ResultSet rs = csd.ps.executeQuery();
			while (rs.next()) {
				i = rs.getInt("id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			csd.close();
		}

		return i;
	}
}
