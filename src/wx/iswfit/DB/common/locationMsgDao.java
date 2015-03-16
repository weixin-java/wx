package wx.iswfit.DB.common;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import wx.iswfit.DB.model.LocationMsg;

public class locationMsgDao implements BaseDao {

	ConMysqlDao csd;
	
	@Override
	public boolean insert(String sql) {
		// TODO Auto-generated method stub
		if(csd==null)
			csd = new ConMysqlDao();
		boolean r=false;
		try {
			csd.prepareConnection();
//			csd.con.setAutoCommit(false);
			csd.stmt = csd.con.createStatement();
			r = csd.stmt.execute(sql);
//			csd.con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			csd.rollback();
			e.printStackTrace();
		} finally {
			csd.close();
		}

		return r;
	}

	@Override
	public boolean delete(String sql) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getRowCount(String sql) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean alert(String sql) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List select(String sql) {
		List returnList=new ArrayList<LocationMsg>();
		if(csd==null)
			csd = new ConMysqlDao();
		try {
			csd.prepareConnection();
//			csd.con.setAutoCommit(false);
			csd.stmt = csd.con.createStatement();
			ResultSet rs = csd.stmt.executeQuery(sql);
//			csd.con.commit();
			while (rs.next()){
				LocationMsg bean=new LocationMsg();
				bean.setCreateTime(Integer.parseInt(rs.getString("CreateTime")));
				bean.setFromUserName(rs.getString("FromUserName"));
				bean.setToUserName(rs.getString("ToUserName"));
				bean.setId(Integer.parseInt(rs.getString("id")));
				bean.setX(rs.getString("x"));
				bean.setY(rs.getString("y"));
				bean.setMsgType(rs.getString("MsgType"));
				bean.setScale(Integer.parseInt(rs.getString("id")));
				bean.setLabel(rs.getString("Label"));
				bean.setMsgId(rs.getString("MsgId"));
				returnList.add(bean);
            }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			csd.rollback();
			e.printStackTrace();
		} finally {
			csd.close();
		}

		return returnList;
	}

}
