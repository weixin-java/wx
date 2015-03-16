<%@ page language="java" import="java.util.*,
wx.iswfit.tool.*" pageEncoding="UTF-8"%>

<%
test t=new test();
String v=t.getRequest(request, response);

System.out.println("############################微信回调接口："+v);

response.getWriter().write(v);

%>
