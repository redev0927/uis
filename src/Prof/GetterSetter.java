package Prof;

//사용자 정보화면에서 필요한 id, 사용자명, 학과, 주민등록번호 값을 set/get하는 클래스

public class GetterSetter {
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
