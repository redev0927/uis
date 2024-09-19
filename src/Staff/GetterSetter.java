package Staff;

//사용자 정보화면에서 필요한 id, 사용자명, 학과, 주민등록번호 값을 set/get하는 클래스

public class GetterSetter {
    private String ID;
    private String Name;
    private String Dept;
    private String RRN;
    private String STATE;
    //
    private String ClassName;
    private String ClassNumber;
    private String Count;
    private String Exp;
    private String Score;
    private String Professor;

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
    public String getSTATE() {
        return STATE;
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
    public void setSTATE(String STATE) {
        this.STATE = STATE;
    }
    ////////////////////////////////////////////
    public String getClassName() {
        return ClassName;
    }
    public String getClassNumber() {
        return ClassNumber;
    }
    public String getCount() {
        return Count;
    }
    public String getExp() {
        return Exp;
    }
    public String getScore() {
        return Score;
    }
    public String getProfessor() {
        return Professor;
    }

    public void setClassName(String ClassName) {
        this.ClassName = ClassName;
    }
    public void setClassNumber(String ClassNumber) {
        this.ClassNumber = ClassNumber;
    }
    public void setCount(String Count) {
        this.Count = Count;
    }
    public void setExp(String Exp) {
        this.Exp = Exp;
    }
    public void setScore(String Score) {
        this.Score = Score;
    }
    public void setProfessor(String Professor) {
        this.Professor = Professor;
    }
}
