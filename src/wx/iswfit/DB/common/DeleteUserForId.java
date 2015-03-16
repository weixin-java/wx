package wx.iswfit.DB.common;

import java.sql.SQLException;

public class DeleteUserForId {

	ConMysqlDao csd;
	private String sql = "DELETE FROM userinfo WHERE id=?";

	public DeleteUserForId() {

	}

	public int deleteForId(UserBean user) {
		int i = 0;
		csd = new ConMysqlDao();
		try {
			// ��ȡ�û���Ӧid
			GetIdFoName getid = new GetIdFoName();
			int id = getid.getId(user);

			csd.prepareConnection();
			csd.con.setAutoCommit(false);
			csd.ps = csd.con.prepareStatement(sql);
			csd.ps.setInt(1, id);
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
