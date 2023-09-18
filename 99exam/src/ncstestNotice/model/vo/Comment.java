package ncstestNotice.model.vo;

import java.sql.Date;

// 2022.2.16(수) 14h45
public class Comment {
	
	private int no; // NO	NUMBER	No		1
	private String commentContents; // COMMENTCONTENTS	VARCHAR2(4000 BYTE)	No		2
	private Date writeDate; // WRITEDATE	DATE	Yes	"sysdate"	3
	
	public Comment() {
		super();
	}

	public Comment(int no, String commentContents, Date writeDate) {
		super();
		this.no = no;
		this.commentContents = commentContents;
		this.writeDate = writeDate;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getCommentContents() {
		return commentContents;
	}

	public void setCommentContents(String commentContents) {
		this.commentContents = commentContents;
	}

	public Date getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(Date writeDate) {
		this.writeDate = writeDate;
	}

	@Override
	public String toString() {
		return "Comment [no=" + no + ", commentContents=" + commentContents + ", writeDate=" + writeDate + "]";
	}
	
}
