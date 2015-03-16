package wx.iswfit.tool;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONTokener;

public class BaiDuMap {
	//根据百度url获取json
		public String getJsonString(String urlstr){
			String json = null;
			StringBuffer sb = new StringBuffer();
			try {
				URL url = new URL(urlstr);
				InputStreamReader isr = new InputStreamReader(url.openStream());
				char[] buffer = new char[1024];
				int len = 0;
				while((len=isr.read(buffer))!=-1){
					sb.append(buffer,0,len);
				}
				
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			json = sb.toString();
//			System.out.println(json);
			
			return json;
		}
		
		//解析百度地图的json数据
		public List json2ListMap(String json){
			List<Map> returnList=null;
			JSONObject article = JSONObject.fromObject(json);
//			JSONObject article = (JSONObject) jsonParser.nextValue();
			int status = Integer.parseInt(article.getString("status"));
			if(status==0){
				returnList=new ArrayList<Map>();
				String results = article.getString("results");

				JSONArray jsonArr = JSONArray.fromObject(results);
	            for (int i = 0; i < jsonArr.size(); i++) {
	                String name = jsonArr.getJSONObject(i).getString("name");
	                String address = jsonArr.getJSONObject(i).getString("address");
//	                String street_id = jsonArr.getJSONObject(i).getString("street_id");
	                String uid = jsonArr.getJSONObject(i).getString("uid");
	                
	                JSONTokener jsonLocation = new JSONTokener(jsonArr.getJSONObject(i).getString("location"));
	        		JSONObject locationXY = (JSONObject) jsonLocation.nextValue();
	        		
	    			String lat = locationXY.getString("lat");
	    			String lng = locationXY.getString("lng");
	    			
	    			Map<String,String> retMap=new HashMap<String,String>();
	    			retMap.put("name", name);
	    			retMap.put("address", address);
//	    			retMap.put("street_id", street_id);
	    			retMap.put("uid", uid);
	    			retMap.put("lat", lat);
	    			retMap.put("lng", lng);
	    			returnList.add(retMap);
	            }
				
			}
			
			return returnList;
		}
		
		public List json2QMMap(String json){
			List<Map> returnList=null;
			JSONObject article = JSONObject.fromObject(json);

			String success = article.getString("success");
			
			JSONObject successObj = JSONObject.fromObject(success);
			
			int status = Integer.parseInt(successObj.getString("status"));
			String pic_server=successObj.getString("pic_server");
			if(status==0){
				returnList=new ArrayList<Map>();
				String items = successObj.getString("items");
				
				JSONObject itemsObj = JSONObject.fromObject(items);

				String item = itemsObj.getString("item");
				
				JSONArray jsonArr = JSONArray.fromObject(item);
	            for (int i = 0; i < jsonArr.size(); i++) {
//	                JSONTokener item = new JSONTokener(jsonArr.getJSONObject(i).getString("item"));
//	        		JSONObject itemsingle = (JSONObject) item.nextValue();
//	        		
//	    			String news = itemsingle.getString("news");
	            	String news = jsonArr.getJSONObject(i).getString("news");
	    			JSONObject newsObj = JSONObject.fromObject(news);

//	    			String school = itemsingle.getString("school");
	    			String school = jsonArr.getJSONObject(i).getString("school");
	    			JSONObject schoolObj = JSONObject.fromObject(school);
	    			

	    			String Title=newsObj.getString("news_title")+"["+schoolObj.getString("school_name")+" 提供]";
	    			String Description=newsObj.getString("publish_time_text");
	    			String PicUrl="";
	    			try{
	    				PicUrl=pic_server+newsObj.getString("news_pic1");
	    			}catch (Exception e){
	    				
	    			}
	    			String Url=pic_server+newsObj.getString("news_content");
	    			Map<String,String> retMap=new HashMap<String,String>();
	    			retMap.put("Title", Title);
	    			retMap.put("Description", Description);
	    			retMap.put("PicUrl", PicUrl);
	    			retMap.put("Url", Url);
	    			returnList.add(retMap);
	            }
				
			}
			
			return returnList;
		}
}
