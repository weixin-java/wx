package wx.iswfit.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import wx.iswfit.DB.common.locationMsgDao;
import wx.iswfit.DB.model.LocationMsg;
import wx.iswfit.MssageBean.resp.Article;
import wx.iswfit.MssageBean.resp.NewsMessage;
import wx.iswfit.MssageBean.resp.TextMessage;
import wx.iswfit.tool.BaiDuMap;
import wx.iswfit.tool.MessageUtil;

public class Location {
	
	public void InsertIntoDB(Map map) throws UnsupportedEncodingException{
		if(map!=null&&map.size()>0){
			String FromUserName=map.get("FromUserName").toString();
			String ToUserName=map.get("ToUserName").toString();
			String CreateTime=map.get("CreateTime").toString();
			String x=map.get("Location_X").toString();
			String y=map.get("Location_Y").toString();
			String MsgType=map.get("MsgType").toString();
			String Scale=map.get("Scale").toString();
			String Label=map.get("Label").toString();
			String MsgId=map.get("MsgId").toString();
			String sqlStr="insert into LocationMsg values(null,'"+ToUserName+"','"+FromUserName+"',"+CreateTime+",'"+MsgType+"','"+x+"','"+y+"',"+Scale+",'"+Label+"','"+MsgId+"')";
			System.out.println("SQL======"+sqlStr);
			locationMsgDao dao=new locationMsgDao();
			
			dao.insert(sqlStr);
		
		}
	}

	public String searchLocation(Map map) throws UnsupportedEncodingException {
		String returnString=null;
		String FromUserName=map.get("FromUserName").toString();
		String ToUserName=map.get("ToUserName").toString();
		String CreateTime=map.get("CreateTime").toString();
		String MsgType=map.get("MsgType").toString();
		String MsgId=map.get("MsgId").toString();
		String Format=map.get("Format").toString();
		String Recognition=map.get("Recognition").toString();
		Recognition=URLEncoder.encode(Recognition,"UTF-8");
		//查询最后一次提交的地理信息
		String sqlStr="select * from LocationMsg where FromUserName='"+FromUserName+"' order by CreateTime desc limit 0,1";
		locationMsgDao dao=new locationMsgDao();
		List locationList=dao.select(sqlStr);
		String x;
		String y;
		
		if(locationList!=null&&locationList.size()==1){
			LocationMsg msg=(LocationMsg)locationList.get(0);
			x=msg.getX();
			y=msg.getY();
			BaiDuMap bdMapTool=new BaiDuMap();
			String baiduMapApiUrl="http://api.map.baidu.com/place/v2/search?ak=9sbxq8yh6x0uXXBuNw8BgP77&output=json&query="+Recognition+"&page_size=10&page_num=0&scope=1&location="+x+","+y+"&radius=2000";
			System.out.println("BaiDuURL======"+baiduMapApiUrl);
			String jsonStr=bdMapTool.getJsonString(baiduMapApiUrl);
			System.out.println("baiduJSON_Str====="+jsonStr);
			//解析json字符串
			List resultList=bdMapTool.json2ListMap(jsonStr);
			if(resultList!=null&resultList.size()>0){
//				System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
				NewsMessage bean=new NewsMessage();
				bean.setFromUserName(ToUserName);
				bean.setToUserName(FromUserName);
				bean.setCreateTime(Integer.parseInt(CreateTime));
				bean.setMsgType("news");
				bean.setArticleCount(resultList.size());
				List<Article> artList=new ArrayList<Article>();
				for (int i = 0; i < resultList.size(); i++) {

//					System.out.println("bbbbbbbbbbbbbbb"+i);
					Map tempmap=(Map)resultList.get(i);
					Article at=new Article();
					at.setDescription(tempmap.get("name").toString());
					at.setPicUrl("http://api.map.baidu.com/panorama?width=512&height=256&location="+tempmap.get("lng").toString()+","+tempmap.get("lat").toString()+"&fov=180&ak=9sbxq8yh6x0uXXBuNw8BgP77");
//					at.setPicUrl("http://api.map.baidu.com/panorama?width=512&height=256&location=116.313393,40.04778&fov=180&ak=E4805d16520de693a3fe707cdc962045");
					at.setTitle(tempmap.get("address").toString());
					at.setUrl("http://api.map.baidu.com/place/detail?uid="+tempmap.get("uid").toString()+"&output=html");
					artList.add(at);
				}
				bean.setArticles(artList);
				returnString=bean.toXMLString();
//				System.out.println(returnString);
			}
		}else{
			TextMessage bean = new TextMessage();
			bean.setToUserName(FromUserName);
			bean.setFromUserName(ToUserName);
			bean.setCreateTime(Integer.parseInt(CreateTime));
			bean.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_TEXT);
			bean.setFuncFlag(0);
			bean.setContent("您尚未发送位置信息，请先使用微信位置功能发送您的坐标，然后再使用语音或文字检索！");
			returnString=bean.toXMLString();
		}
		
		return returnString;
	}
	
	
}
