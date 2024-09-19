package Login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Database.ReadProp;

// Login - DAO.java
// DB에 저장되어 있는 ID, PW의 값을 가져오며,
// 프로그램 내에서 사용할 수 있는 형식으로 저장

public class DAO {
    // DB 연결을 위한 DB정보 불러오기
    ReadProp pr = new ReadProp();

    public String oracleDriver=pr.getDBName();
    public String oracleURL=pr.getDBUrl();
    public String oracleId=pr.getDBId();
    public String oraclePw=pr.getDBPw();

    // JDBC 연결 메소드
    public Connection getConnection() throws ClassNotFoundException, Exception{
        Connection con = null; // JDBC와의 연결 객체 초기화

        try{
            Class.forName(oracleDriver); // JDBC Driver 검색
            con = DriverManager.getConnection(oracleURL,oracleId,oraclePw); // JDBC URL

            // JDBC driver 누락 시 예외처리
        } catch(ClassNotFoundException e){
            System.out.println("[ERROR]"+e.getMessage());
            e.printStackTrace();

            // 모든 예외상황 처리
        } catch(Exception e){
            System.out.println("[ERROR]"+e.getMessage());
            e.printStackTrace();
        }

        return con;
    }

    // 사용자 정보를 가져오는 기능 메소드
    public ArrayList<GetterSetter> getMembers() throws SQLException, Exception{
        Connection con = null;
        PreparedStatement pstmt = null;
        // "Statement pstmt = null;"에 비해 미리처리하기 때문에 빠르게 처리
        ResultSet rs = null;

        ArrayList<GetterSetter> members = new ArrayList<GetterSetter>();
        GetterSetter member; // DB Table의 1개의 레코드(record : row)를 불러오기 위한 임시 변수

        con = getConnection();
        String sql = "SELECT * FROM student UNION SELECT * FROM professor UNION SELECT staffid, staffpw, staffname, NULL, NULL FROM staff";

        try {
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while(rs.next()){
                member = new GetterSetter(); // 임시 생성
                member.setId(rs.getString("studentid")); // id 필드값을 가져옴
                member.setPw(rs.getString("studentpw")); // pw 필드값을 가져옴
                members.add(member);
            }

            rs.close();
            pstmt.close();
            con.close();

        } catch(SQLException e) {
            System.out.println("[ERROR]"+e.getMessage());
            e.printStackTrace();

        } catch(Exception e) {
            System.out.println("[ERROR]"+e.getMessage());
            e.printStackTrace();
        }

        return members;
    }

    // 비밀번호 변경 기능 메소드
    public void PWUpdate(String pass_after) throws SQLException, Exception{
        Connection con = null;
        PreparedStatement pstmt = null;
//        ResultSet rs = null;
        String sql = null;

        con = getConnection();

        if(Graphic.PassID.substring(0, 1).equals("S"))
            sql = "UPDATE student SET studentpw='" + pass_after + "' WHERE studentid='" + Graphic.PassID + "'";
        else if(Graphic.PassID.substring(0, 1).equals("P"))
            sql = "UPDATE professor SET professorpw='" + pass_after + "' WHERE professorid='" + Graphic.PassID + "'";
        else
            sql = "UPDATE staff SET staffpw='" + pass_after + "' WHERE staffid='" + Graphic.PassID + "'";

        try {
            pstmt = con.prepareStatement(sql);
            boolean rs = pstmt.execute();

//            rs.close();
            pstmt.close();
            con.close();

        } catch(SQLException e) {
            System.out.println("[ERROR]"+e.getMessage());
            e.printStackTrace();

        } catch(Exception e) {
            System.out.println("[ERROR]"+e.getMessage());
            e.printStackTrace();
        }
    }
}
