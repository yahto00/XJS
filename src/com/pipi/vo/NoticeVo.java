package com.pipi.vo;
/**
 * 封装消息内容用于前台展示
 * @author xihashao-pc
 *
 */
public class NoticeVo {
	/**消息标题*/
	private String title;
	/**消息内容*/
	private String content;
	/**是否已读*/
	private boolean isRead;
	/**该通知对应的消息状态表的id*/
	public Integer noticeStateId;
	/**通知id */
	public Integer noticeId;
	 
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public boolean isRead() {
		return isRead;
	}
	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}
	public Integer getNoticeStateId() {
		return noticeStateId;
	}
	public void setNoticeStateId(Integer noticeStateId) {
		this.noticeStateId = noticeStateId;
	}
	public Integer getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(Integer noticeId) {
		this.noticeId = noticeId;
	}
	
	
	
	
}
