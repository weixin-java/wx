package wx.iswfit.DB.common;

import java.util.List;

public interface BaseDao {
	//插入
	public boolean insert(String sql);
	
	//删除
	public boolean delete(String sql);
	
	//统计
	public int getRowCount(String sql);
	
	//修改
	public boolean alert(String sql);
	
	//查询
	public List select(String sql);
}
