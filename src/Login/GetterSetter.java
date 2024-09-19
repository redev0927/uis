package Login;

// Login시 필요한 id, pw 값을 set/get하는 클래스

public class GetterSetter {
    private String id;
    private String pw;

    public String getId() {
        return id;
    }
    public String getPw() {
        return pw;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setPw(String pw) {
        this.pw = pw;
    }
}
