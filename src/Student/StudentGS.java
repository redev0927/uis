package Student;

//Student - StudentGS.java
//����� ����ȭ�鿡�� �ʿ��� id, ����ڸ�, �а�, �ֹε�Ϲ�ȣ ���� set/get�ϴ� Ŭ����

public class StudentGS {
	private String ID;
	private String Name;
	private String Dept;
	private String RRN;
	private String Credit;
	private String CouName;
	private String Score;
	private String Intro;
	
	public String getID() {
		return ID;
	}
	public String getName() {
		return Name;
	}
	public String getDept() {
		return Dept;
	}
	public String getRRN() {
		return RRN;
	}
	public String getCredit() {
		return Credit;
	}	
	public String getCouName() {
		return CouName;
	}
	public String getScore() {
		return Score;
	}
	public String getIntro() {
		return Intro;
	}
	
	public void setID(String ID) {
		this.ID = ID;
	}
	public void setName(String Name) {
		this.Name = Name;
	}
	public void setDept(String Dept) {
		this.Dept = Dept;
	}
	public void setRRN(String RRN) {
		this.RRN = RRN;
	}
	public void setCredit(String Credit) {
		this.Credit = Credit;
	}
	public void setCouName(String CouName) {
		this.CouName = CouName;
	}
	public void setScore(String Score) {
		this.Score = Score;
	}
	public void setIntro(String Intro) {
		this.Intro = Intro;
	}
}
