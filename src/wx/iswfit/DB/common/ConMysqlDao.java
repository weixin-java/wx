package wx.iswfit.DB.common;





import java.sql.*;

public class ConMysqlDao {
	static {
		// 加载驱动程序
		try {
			Class.forName("com.mysql.jdbc.Driver");
            System.out.println("成功加载MySQL驱动！");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("驱动加载失败");
		}
	}
	// 数据库链接字符串
	private String url = "jdbc:mysql://localhost:3306/wx?characterEncoding=utf8";
	// 用户名
	private String userName = "root";
	// 密码
	private String passWord = "root";
	// 连接对象
	public Connection con = null;
	// 语句对象
	public PreparedStatement ps = null;
	
	public Statement stmt=null;

	// 数据库链接方法
	public void prepareConnection() {
		try {
			if (con == null || con.isClosed()) {
				con = DriverManager.getConnection(url, userName, passWord);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("链接异常:" + e.getMessage());
		}
	}

	// 关闭方法
	public void close() {
		try {
			if (ps != null) {
				ps.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("关闭链接异常:" + e.getMessage());
		}
	}

	// 操作回滚
	public void rollback() {
		try {
			con.rollback();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("回滚失败:" + e.getMessage());
		}
	}

}
