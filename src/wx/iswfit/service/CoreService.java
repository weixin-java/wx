package wx.iswfit.service;

import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import wx.iswfit.MssageBean.resp.TextMessage;
import wx.iswfit.sys.PARAMETER;
import wx.iswfit.tool.MessageUtil;

/**
 * 核心服务类
 * 
 * @author huamry
 * @date 2015-02-20
 */
public class CoreService {
 /**
  * 处理微信发来的请求
  * 
  * @param request
  * @return
  */
 public static String processRequest(HttpServletRequest request) {
  String respMessage = null;
  try {
   // 默认返回的文本消息内容
   String respContent = "请求处理异常，请稍候尝试！";
   // xml请求解析
//   //处理接收消息    
//   ServletInputStream in = request.getInputStream();  
//   //将POST流转换为XStream对象  
//   XStream xs = new XStream(new DomDriver());
//   //将指定节点下的xml节点数据映射为对象  
//   xs.alias("xml", InputMessage.class);  
//   //将流转换为字符串  
//   StringBuilder xmlMsg = new StringBuilder();  
//   byte[] b = new byte[4096];  
//   for (int n; (n = in.read(b)) != -1;) {  
//       xmlMsg.append(new String(b, 0, n, "UTF-8"));  
//   }
   
   SAXReader reader = new SAXReader();
   Document document = null;
   document = reader.read(request.getInputStream()); 
   document.setXMLEncoding("UTF-8");
   String xmlString=document.asXML();
   System.out.println("getXML============================="+xmlString);
   Map<String, String> requestMap = MessageUtil.parseXml(xmlString);
   // 发送方帐号（open_id）
   String fromUserName = requestMap.get("FromUserName");
   // 公众帐号
   String toUserName = requestMap.get("ToUserName");
   // 消息类型
   String msgType = requestMap.get("MsgType");
   // 回复文本消息
   TextMessage textMessage = new TextMessage();
   textMessage.setToUserName(fromUserName);
   textMessage.setFromUserName(toUserName);
   textMessage.setCreateTime(new Date().getTime());
   textMessage.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_TEXT);
   textMessage.setFuncFlag(0);
   // 文本消息
   if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
//    respContent = "您发送的是文本消息！";
//    textMessage.setContent(respContent);
//    respMessage = textMessage.toXMLString();
	   Text server=new Text();
	   respMessage=server.getReturn(requestMap);
   }
   // 图片消息
   else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
    respContent = "您发送的是图片消息！";
    textMessage.setContent(respContent);
    respMessage = textMessage.toXMLString();
   }
   // 地理位置消息
   else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
    respContent = "您所在位置已纪录：［"+requestMap.get("Label").toString()+"］，现在可以使用语音查询地图了！";
    Location server=new Location();
    server.InsertIntoDB(requestMap);
    textMessage.setContent(respContent);
    respMessage = textMessage.toXMLString();
   }
   // 链接消息
   else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
    respContent = "您发送的是链接消息！";
   }
   // 音频消息
   else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
    respContent = "您发送的是音频消息！内容为：［"+requestMap.get("Recognition")+"］";
//    textMessage.setContent(respContent);
    Location server=new Location();
    
    respMessage = server.searchLocation(requestMap);
   }
   // 事件推送
   else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
    // 事件类型
    String eventType = requestMap.get("Event");
    // 订阅
    if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
     textMessage.setContent(PARAMETER.GUAN_ZHU);
     respMessage = textMessage.toXMLString();
    }
    if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
        if("EVERYDAYHOTEST".equals(requestMap.get("EventKey"))){
        	requestMap.put("MsgType", "text");
        	requestMap.put("Content", "2,2");
        	requestMap.put("MsgId", "afabafaseuifbgreugwugweu-feg");
        	Text server=new Text();
     	   	respMessage=server.getReturn(requestMap);
        }
    }
//    // 取消订阅
//    else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
//     // TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
//    }
//    // 自定义菜单点击事件
//    else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
//     // TODO 自定义菜单权没有开放，暂不处理该类消息
//    }
   }
  } catch (Exception e) {
   e.printStackTrace();
  }
  return respMessage;
 }
}
