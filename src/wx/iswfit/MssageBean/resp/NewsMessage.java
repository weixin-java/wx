package wx.iswfit.MssageBean.resp;

import java.util.List;

public class NewsMessage extends BaseMessage {
	// 图文消息个数，限制为10条以内
	private int ArticleCount;
	// 多条图文消息信息，默认第一个item为大图
	private List<Article> Articles;

	public int getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}

	public List<Article> getArticles() {
		return Articles;
	}

	public void setArticles(List<Article> articles) {
		Articles = articles;
	}
	
	public String toXMLString(){
		StringBuffer retunStBf=new StringBuffer("<xml>");
		
		retunStBf.append("<ToUserName><![CDATA["+this.getToUserName()+"]]></ToUserName>");
		retunStBf.append("<FromUserName><![CDATA["+this.getFromUserName()+"]]></FromUserName>");
		retunStBf.append("<CreateTime>"+this.getCreateTime()+"</CreateTime>");
		retunStBf.append("<MsgType><![CDATA[news]]></MsgType>");
		retunStBf.append("<ArticleCount>"+Articles.size()+"</ArticleCount>");

		retunStBf.append("<Articles>");
		for (int i = 0; i < Articles.size(); i++) {
			Article at=(Article)Articles.get(i);
			retunStBf.append("<item>");
			retunStBf.append(at.toXMLString());
			retunStBf.append("</item>");
		}
		retunStBf.append("</Articles>");
		
		retunStBf.append("</xml>");
		return retunStBf.toString();
	}
}
