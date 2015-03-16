package wx.iswfit.DB.common;

import java.sql.SQLException;

public class UpdateUserForId {

	public UpdateUserForId() {

	}

	ConMysqlDao csd;
	private String sql = "UPDATE userinfo SET username=?,password=? WHERE id=?";

	// private String sql = "UPDATE userinfo SET username=?,password=?,userphone=? WHERE id=?";

	public int updateForId(UserBean user) {
		int i = 0;
		csd = new ConMysqlDao();
		try {
			// ��ȡ�û���Ӧid
			GetIdFoName getid = new GetIdFoName();
			int id = getid.getId(user);

			csd.prepareConnection();
			csd.con.setAutoCommit(false);
			csd.ps = csd.con.prepareStatement(sql);
			csd.ps.setString(1, user.getUsername());
			csd.ps.setString(2, user.getPassword());
			// csd.ps.setString(3,user.getUserphone());
			csd.ps.setInt(3, id);
			i = csd.ps.executeUpdate();
			// System.out.println(i);
			csd.con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			csd.rollback();
			e.printStackTrace();
			System.out.println("�û������ڻ��������");
		} finally {
			csd.close();
		}
		return i;
	}

}
