package Professor;

//Professor - ProfGS.java
//����� ����ȭ�鿡�� �ʿ��� id, ����ڸ�, �а�, �ֹε�Ϲ�ȣ ���� set/get�ϴ� Ŭ����

public class ProfGS {
	private String ID;
	private String Name;
	private String Dept;
	private String RRN;
	private String Score;
	
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
	public String getScore() {
		return Score;
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
	public void setScore(String Score) {
		this.Score = Score;
	}
}
