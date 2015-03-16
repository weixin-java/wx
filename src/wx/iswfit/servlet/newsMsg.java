package wx.iswfit.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class newsMsg extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String access_token="g2tYFxq8YFSPnT8XX2Y8LxWdR61ax786Mm1UoCJTx1QRU3qhx09s6Y3BM-d0_DXEUA9cu4ly1YGSvFvqIyToTNgeC49FSOrc6InCYAT3X18";
		if(request.getParameter("access_token")!=null)
			access_token=request.getParameter("access_token");
		String body="{\"status\":0}";
		if(request.getParameter("body")!=null)
			body=request.getParameter("body");
		
		String oper="getbystatus";
		if(request.getParameter("oper")!=null)
			oper=request.getParameter("oper");
		try {
			URL url = new URL("https://api.weixin.qq.com/cgi-bin/media/"+oper+"?access_token="+access_token);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "text/xml");
			connection.connect();

			// 发送域信息
			OutputStreamWriter out = new OutputStreamWriter(
					connection.getOutputStream(), "UTF-8");
			out.write(body);
			out.flush();
			out.close();

			System.out.println("send ok");

			int code = connection.getResponseCode();
			System.out.println("code " + code);
			System.out.println(connection.getResponseMessage());
			
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write(code); 

			// 读取响应内容
			String sCurrentLine = "";
			String sTotalString = "";
			if (code == 200) {
				// 获取返回数据
				java.io.InputStream is = connection.getInputStream();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is));
				while ((sCurrentLine = reader.readLine()) != null)
					if (sCurrentLine.length() > 0)
						sTotalString = sTotalString + sCurrentLine.trim();
			} else {
				sTotalString = "远程服务器连接失败,错误代码:" + code;

			}
			System.out.println("response:" + sTotalString);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}

}
