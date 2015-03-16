<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>获取指定状态的所有商品</title>
    

  </head>
  
  <body>
    <form action="/selectAllGoods">
    	<table>
    		<tr>
    			<td>access_token</td>
    			<td><input type="text" size="50" name="access_token" value="g2tYFxq8YFSPnT8XX2Y8LxWdR61ax786Mm1UoCJTx1QRU3qhx09s6Y3BM-d0_DXEUA9cu4ly1YGSvFvqIyToTNgeC49FSOrc6InCYAT3X18"></td>
    		</tr>
    		<tr>
    			<td>oper</td>
    			<td><input type="text" size="50" name="oper" value="getbystatus"></td>
    		</tr>
    		<tr>
    			<td>status</td>
    			<td><textarea name="body" clos="80" rows="20" style="width: 360px; "></textarea></td>
    		</tr>
    		<tr>
    			<td></td>
    			<td><input type="submit"></td>
    		</tr>
    	</table>
    </form>
  </body>
</html>
