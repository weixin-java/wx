package wx.iswfit.MssageBean.resp;

public class TextMessage extends BaseMessage {
	// 消息内容
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
	
	public String toXMLString(){
		return "<xml>"+
				"<ToUserName><![CDATA["+this.getToUserName()+"]]></ToUserName>"+
				"<FromUserName><![CDATA["+this.getFromUserName()+"]]></FromUserName>"+
				"<CreateTime>"+this.getCreateTime()+"</CreateTime>"+
				"<MsgType><![CDATA["+this.getMsgType()+"]]></MsgType>"+
				"<Content><![CDATA["+this.getContent()+"]]></Content>"+
				"</xml>";
	}
}