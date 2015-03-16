package wx.iswfit.servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wx.iswfit.service.CoreService;
import wx.iswfit.sys.PARAMETER;
import wx.iswfit.tool.SHA1;

/** 
 * 核心请求处理类 
 *  
 * @author huarmy
 * @date 2015-02-18 
 */  
public class API extends HttpServlet {
	 private static final long serialVersionUID = 4440739483644821986L;  
  
    /** 
     * 确认请求来自微信服务器 
     */  
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
    	String token = PARAMETER.token;
    	  
        Map<String, String[]> map = request.getParameterMap(); 
        String pram="";
        for (Entry<String, String[]> en : map.entrySet()) {  
        	pram+=(en.getKey() + "=" + en.getValue()[0]+"&");  
        }
        System.out.println("requestParameter＝＝＝＝＝＝＝＝"+pram);
  
        String timestamp = (map.get("timestamp")==null||map.get("timestamp")[0]==null)?"":map.get("timestamp")[0];  
        String nonce = (map.get("nonce")==null||map.get("nonce")[0]==null)?"":map.get("nonce")[0];  
        String sha1 = "";  
        String[] ss = new String[] { token, timestamp, nonce };  
  
        Arrays.sort(ss);  
        for (String s : ss) {  
            sha1 += s;  
        }  
  
        sha1 = new SHA1().getDigestOfString(sha1.getBytes());  
  
        if (sha1.toLowerCase().equals((map.get("signature")==null||map.get("signature")[0]==null)?"":map.get("signature")[0])) {  
            
        	System.out.println("result＝＝＝＝＝＝＝＝TRUE");
        	String ret="";
    		ret=CoreService.processRequest(request);
//        	if(map!=null&&map.get("echostr")!=null&&map.size()>0&&map.get("echostr")[0]!=null){
//    			//验证成功
//    			ret=map.get("echostr")[0];
//    		}
    		System.out.println("ret==="+ret);
    		response.setContentType("text/html;charset=UTF-8");
        	response.getWriter().write(ret); 
            
        }
    }  
  
    /** 
     * 处理微信服务器发来的消息 
     */  
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
        // TODO 消息的接收、处理、响应  
    	doGet(request,response);
    }  
  
}  