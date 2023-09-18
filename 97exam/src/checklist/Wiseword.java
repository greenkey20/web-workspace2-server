package checklist;

//2022.3.17(목) 11h10
public class Wiseword {

	private int seq; // SEQ	NUMBER	No
	private String sentence; // SENTENCE	VARCHAR2(4000 BYTE)	No
	private String writer; // WRITER	VARCHAR2(500 BYTE)	No
	
	public Wiseword() {
		super();
	}
	
	public Wiseword(int seq, String sentence, String writer) {
		super();
		this.seq = seq;
		this.sentence = sentence;
		this.writer = writer;
	}

	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getSentence() {
		return sentence;
	}
	public void setSentence(String sentence) {
		this.sentence = sentence;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}

	@Override
	public String toString() {
		return "Wiseword [seq=" + seq + ", sentence=" + sentence + ", writer=" + writer + "]";
	}
	
}
