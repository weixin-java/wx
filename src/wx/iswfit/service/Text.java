package wx.iswfit.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import wx.iswfit.DB.common.locationMsgDao;
import wx.iswfit.DB.model.LocationMsg;
import wx.iswfit.MssageBean.resp.Article;
import wx.iswfit.MssageBean.resp.NewsMessage;
import wx.iswfit.MssageBean.resp.TextMessage;
import wx.iswfit.sys.PARAMETER;
import wx.iswfit.tool.BaiDuMap;
import wx.iswfit.tool.MessageUtil;

public class Text {

	public String getReturn(Map map) throws UnsupportedEncodingException {
		String returnString=null;
		String FromUserName=map.get("FromUserName").toString();
		String ToUserName=map.get("ToUserName").toString();
		String CreateTime=map.get("CreateTime").toString();
		String MsgType=map.get("MsgType").toString();
		String MsgId=map.get("MsgId").toString();
		String Content=map.get("Content").toString();
		String [] contentArray=Content.split(",");
		if(contentArray.length>1){
			if("0".equals(contentArray[0])){
				TextMessage bean = new TextMessage();
				bean.setToUserName(FromUserName);
				bean.setFromUserName(ToUserName);
				bean.setCreateTime(Integer.parseInt(CreateTime));
				bean.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_TEXT);
				bean.setFuncFlag(0);
				String ask=URLEncoder.encode(contentArray[1],"UTF-8");
				String answer=getAnswer(ask);
				bean.setContent(answer);
				returnString=bean.toXMLString();
			}else if("1".equals(contentArray[0])){
				String Recognition=URLEncoder.encode(contentArray[1],"UTF-8");
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
					int page_size=10;
					int page_num=0;
					BaiDuMap bdMapTool=new BaiDuMap();
					if(contentArray.length>2)
						page_size=Integer.parseInt(contentArray[2]);
					if(contentArray.length>3){
						page_num=Integer.parseInt(contentArray[3])-1;
						page_num=(page_num<0)?0:page_num;
					}
					String baiduMapApiUrl="http://api.map.baidu.com/place/v2/search?ak=9sbxq8yh6x0uXXBuNw8BgP77&output=json&query="+Recognition+"&page_size="+page_size+"&page_num="+page_num+"&scope=1&location="+x+","+y+"&radius=2000";
					System.out.println("BaiDuURL======"+baiduMapApiUrl);
					String jsonStr=bdMapTool.getJsonString(baiduMapApiUrl);
					System.out.println("baiduJSON_Str====="+jsonStr);
					//解析json字符串
					List resultList=bdMapTool.json2ListMap(jsonStr);
					if(resultList!=null&resultList.size()>0){
//						System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
						NewsMessage bean=new NewsMessage();
						bean.setFromUserName(ToUserName);
						bean.setToUserName(FromUserName);
						bean.setCreateTime(Integer.parseInt(CreateTime));
						bean.setMsgType("news");
						bean.setArticleCount(resultList.size());
						List<Article> artList=new ArrayList<Article>();
						for (int i = 0; i < resultList.size(); i++) {

//							System.out.println("bbbbbbbbbbbbbbb"+i);
							Map tempmap=(Map)resultList.get(i);
							Article at=new Article();
							at.setDescription(tempmap.get("name").toString());
							at.setPicUrl("http://api.map.baidu.com/panorama?width=512&height=256&location="+tempmap.get("lng").toString()+","+tempmap.get("lat").toString()+"&fov=180&ak=9sbxq8yh6x0uXXBuNw8BgP77");
//							at.setPicUrl("http://api.map.baidu.com/panorama?width=512&height=256&location=116.313393,40.04778&fov=180&ak=E4805d16520de693a3fe707cdc962045");
							at.setTitle(tempmap.get("address").toString());
							at.setUrl("http://api.map.baidu.com/place/detail?uid="+tempmap.get("uid").toString()+"&output=html");
							artList.add(at);
						}
						bean.setArticles(artList);
						returnString=bean.toXMLString();
//						System.out.println(returnString);
					}
				}
			}else if("2".equals(contentArray[0])){
				String QMURL="http://testapi.iqingmei.com/qmserver/dispatcher?m=AttitudeEveryHotList&client_id=4f1496eab372faf1d3a03450f125e97d&format=json";
				BaiDuMap bdMapTool=new BaiDuMap();
				String jsonQM=bdMapTool.getJsonString(QMURL);
				List resultList=bdMapTool.json2QMMap(jsonQM);
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
					at.setDescription(tempmap.get("Description").toString());
					at.setTitle(tempmap.get("Title").toString());
					at.setPicUrl(tempmap.get("PicUrl").toString());
					at.setUrl(tempmap.get("Url").toString());
					artList.add(at);
				}
				bean.setArticles(artList);
				returnString=bean.toXMLString();
			}
		}else{
			TextMessage bean = new TextMessage();
			bean.setToUserName(FromUserName);
			bean.setFromUserName(ToUserName);
			bean.setCreateTime(Integer.parseInt(CreateTime));
			bean.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_TEXT);
			bean.setFuncFlag(0);
			bean.setContent(PARAMETER.DEFAULT_TEXT);
			returnString=bean.toXMLString();
		}
		
		return returnString;
	}
	public String getAnswer(String ask) throws UnsupportedEncodingException{
		String answer=ask;
		if(ask!=null){
			if(ask.indexOf(URLEncoder.encode("你好","UTF-8"))>-1){
				answer="你好啊～亲";
			}else if(ask.indexOf(URLEncoder.encode("谁","UTF-8"))>-1){
				answer="我是谁，你是谁，重要吗？";
			}else if(ask.indexOf(URLEncoder.encode("漂亮","UTF-8"))>-1){
				answer="谢谢！";
			}else if(ask.indexOf(URLEncoder.encode("吗","UTF-8"))>-1){
				answer="您的意思我没有明白！";
			}else if(ask.indexOf(URLEncoder.encode("！","UTF-8"))>-1){
				answer="您的意思我没有明白！";
			}else {
				answer="您的意思我没有明白！";
			}
		}
		
		return answer;
	}
}
