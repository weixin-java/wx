package wx.iswfit.tool;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class MessageUtil {
	
	public static final String REQ_MESSAGE_TYPE_TEXT ="text";

	public static final String REQ_MESSAGE_TYPE_IMAGE ="image";

	public static final String REQ_MESSAGE_TYPE_LOCATION ="location";

	public static final String REQ_MESSAGE_TYPE_LINK ="link";

	public static final String REQ_MESSAGE_TYPE_VOICE ="voice";
	
	public static final String REQ_MESSAGE_TYPE_EVENT ="event";
	
	public static final String EVENT_TYPE_SUBSCRIBE ="subscribe";
	
	public static final String EVENT_TYPE_CLICK ="CLICK";
	
	
	public static Map parseXml(String strXml){
	    Map<String,String> map = null;
	    try {
	      if (strXml.length() <= 0 || strXml == null)
	        return null;
	       
	      // 将字符串转化为XML文档对象
	      Document document = DocumentHelper.parseText(strXml);
	      // 获得文档的根节点
	      Element root = document.getRootElement();
	      // 遍历根节点下所有子节点
	      Iterator<?> iter = root.elementIterator();
	      
	      // 遍历所有结点
	      map = new HashMap<String,String>();
	      //利用反射机制，调用set方法
	      
	      while(iter.hasNext()){
	        Element ele = (Element)iter.next();
//	        //获取set方法中的参数字段（实体类的属性）
//	        Field field = c.getDeclaredField(ele.getName());
//	        //获取set方法，field.getType())获取它的参数数据类型
//	        Method method = c.getDeclaredMethod("set"+ele.getName(), field.getType());
//	        //调用set方法
//	        method.invoke(msg, ele.getText());
	        map.put(ele.getName(), ele.getText());
	      }
	    } catch (Exception e) {
	      // TODO: handle exception
	      System.out.println("xml 格式异常: "+ strXml);
	      e.printStackTrace();
	    }
	    return map;
	  }
	
}
