package wx.iswfit.tool;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class test {

	public String getRequest(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		String token = "hY005tX5eY5gByXX"; 
  
        Map<String, String[]> map = request.getParameterMap(); 
        String pram="";
        for (Entry<String, String[]> en : map.entrySet()) {  
        	pram+=(en.getKey() + "=" + en.getValue()[0]+"&");  
        }
        System.out.println(pram);
  
        String timestamp = map.get("timestamp")[0];  
        String nonce = map.get("nonce")[0];  
        String sha1 = "";  
        String[] ss = new String[] { token, timestamp, nonce };  
  
        Arrays.sort(ss);  
        for (String s : ss) {  
            sha1 += s;  
        }  
  
        sha1 = new SHA1().getDigestOfString(sha1.getBytes());  
  
        if (sha1.toLowerCase().equals(map.get("signature")[0])) {  
            
//        	response.getWriter().write(map.get("echostr")[0]);
        	
        	return map.get("echostr")[0];  
            
        }else{
        	return "false";
        }
	}
	
}
