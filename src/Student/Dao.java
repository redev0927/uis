package Student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Database.ReadProp;
import Login.Graphic;
import Staff.RegClass;

// 사용자 정보창에 필요한 정보(학번(교수번호), 이름, 학과, 주민등록번호)를 DB에서 가져오며,
//프로그램 내에서 사용할 수 있는 형식으로 저장

public class Dao {
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
        ResultSet rs = null;

        ArrayList<GetterSetter> members = new ArrayList<GetterSetter>();
        GetterSetter member;

        con = getConnection();
        String sql = "SELECT STUDENT.ID, STUDENT.NAME, DEPT.DEPT_NAME, RRN FROM STUDENT, DEPT WHERE DEPT.ID=STUDENT.DEPT_ID AND STUDENT.ID LIKE '" + Graphic.PassID + "'";

        try {
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while(rs.next()){
                member = new GetterSetter(); // 임시 생성
                member.setID(rs.getString("ID")); // id 필드값을 가져옴
                member.setName(rs.getString("NAME")); // name 필드값을 가져옴
                member.setDept(rs.getString("DEPT_NAME")); // dept 필드값을 가져옴
                member.setRRN(rs.getString("RRN")); // RRN 필드값을 가져옴
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

    // 신청가능한 강의목록를 가져오는 기능 메소드
    public ArrayList<GetterSetter> OpenedClass() throws SQLException, Exception{
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;

        ArrayList<GetterSetter> members = new ArrayList<GetterSetter>();
        GetterSetter member=null;

        con = getConnection();

        sql = "SELECT COURSES.ID, COURSES.NAME, PROFESSOR.NAME as " + "ProfName" + ", COURSES.CREDIT, COURSES.INTRO FROM COURSES, PROFESSOR WHERE COURSES.STATE=1 AND courses.prof_id=PROFESSOR.ID ORDER BY ID ASC";

        try {
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while(rs.next()){
                member = new GetterSetter(); // 임시 생성

                member.setID(rs.getString("ID")); // id 필드값을 가져옴
                member.setCouName(rs.getString("NAME")); // 강의 테이블-name 필드값을 가져옴
                member.setName(rs.getString("ProfName")); // 교수 테이블-name 필드값을 가져옴
                member.setCredit(rs.getString("CREDIT")); // 강의 테이블-Credit 필드값을 가져옴
                member.setIntro(rs.getString("INTRO")); // 강의 테이블-Credit 필드값을 가져옴

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

    // 사용자 수강 현황을 가져오는 기능 메소드
    public ArrayList<GetterSetter> resultTakes() throws SQLException, Exception{
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;

        ArrayList<GetterSetter> members = new ArrayList<GetterSetter>();
        GetterSetter member=null;

        con = getConnection();

        sql = "SELECT TAKES.COU_ID, COURSES.NAME, PROFESSOR.NAME as " + "ProfName" + ", COURSES.CREDIT FROM COURSES, PROFESSOR, TAKES WHERE COURSES.ID=TAKES.COU_ID AND courses.prof_id=PROFESSOR.ID AND TAKES.STU_ID = '" + Graphic.PassID + "' ORDER BY COU_ID ASC";

        try {
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while(rs.next()){
                member = new GetterSetter(); // 임시 생성

                member.setID(rs.getString("COU_ID")); // id 필드값을 가져옴
                member.setCouName(rs.getString("NAME")); // 강의 테이블-name 필드값을 가져옴
                member.setName(rs.getString("ProfName")); // 교수 테이블-name 필드값을 가져옴
                member.setCredit(rs.getString("CREDIT")); // 강의 테이블-Credit 필드값을 가져옴

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

    // 수강신청을 위한 기능 메소드
    public void submitClass() throws SQLException, Exception{
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = null;

        con = getConnection();

        ArrayList<GetterSetter> members = new ArrayList<GetterSetter>();
        members = resultTakes();
        Object record[] = new Object[5];

        int SubmitCredit = 0;
        int cntCredit = 0;

        while(true) {
            for(int i=0; i<members.size();i++) {
                GetterSetter data = members.get(i);
                record[i] = data.getCredit();
                SubmitCredit = Integer.parseInt(record[i].toString());
                cntCredit = cntCredit + SubmitCredit;
            }

            SubmitCredit = Integer.parseInt(AttendClass.tbSignupClass.getValueAt(AttendClass.tbSignupClass.getSelectedRow(), 3).toString());
            cntCredit = cntCredit + SubmitCredit;

            if(cntCredit>18) {
                JOptionPane.showMessageDialog(null, "신청가능한 학점을 초과하였습니다.", "Error", JOptionPane.ERROR_MESSAGE);
                break;
            }

            else {
                sql = "INSERT INTO TAKES VALUES('" + Graphic.PassID + "', '" + AttendClass.tbSignupClass.getValueAt(AttendClass.tbSignupClass.getSelectedRow(), 0) + "', '" + "-" + "')";

                try {
                    pstmt = con.prepareStatement(sql);
                    rs = pstmt.executeQuery();

                    JOptionPane.showMessageDialog(null, "수강신청이 완료되었습니다.");

                    rs.close();
                    pstmt.close();
                    con.close();

                    break;
                } catch(SQLException e) {
                    System.out.println("[ERROR]"+e.getMessage());
                    e.printStackTrace();

                } catch(Exception e) {
                    System.out.println("[ERROR]"+e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }




    // 수강취소를 위한 기능 메소드
    public void CancelClass() throws SQLException, Exception{
        Connection con = null;

        PreparedStatement pstmt = null;

        ResultSet rsSelect = null;
        ResultSet rsDelete = null;

        String sq1Select = null;
        String sq1Delete = null;

        GetterSetter Result = new GetterSetter(); // 임시 생성

        con = getConnection();

        sq1Select = "SELECT COURSES.ID FROM COURSES, TAKES WHERE COURSES.NAME='" + ChkClass.tbchkSignupClass.getValueAt(ChkClass.tbchkSignupClass.getSelectedRow(), 1) + "' AND TAKES.COU_ID=COURSES.ID AND TAKES.STU_ID='" + Graphic.PassID + "'";

        try {
            pstmt = con.prepareStatement(sq1Select);
            rsSelect = pstmt.executeQuery();


            while(rsSelect.next()){
                Result.setID(rsSelect.getString("ID")); // id 필드값을 가져옴
            }

            String ResultSelect = Result.getID();

            sq1Delete = "DELETE FROM TAKES WHERE COU_ID='"+ResultSelect+"' AND TAKES.STU_ID='"+Graphic.PassID+"'";

            pstmt = con.prepareStatement(sq1Delete);
            rsDelete = pstmt.executeQuery();

            rsDelete.close();
            rsSelect.close();

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

    // 성적조회 기능 메소드
    public ArrayList<GetterSetter> resultGrades() throws SQLException, Exception{
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;

        ArrayList<GetterSetter> members = new ArrayList<GetterSetter>();
        GetterSetter member=null;

        con = getConnection();

        sql = "SELECT TAKES.COU_ID, COURSES.NAME, PROFESSOR.NAME AS " + "ProfName" + ", COURSES.CREDIT, TAKES.SCORE FROM COURSES, PROFESSOR, TAKES WHERE COURSES.ID=TAKES.COU_ID AND courses.prof_id=PROFESSOR.ID AND TAKES.STU_ID = '" + Graphic.PassID + "' ORDER BY COU_ID ASC";

        try {
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while(rs.next()){
                member = new GetterSetter(); // 임시 생성

                member.setID(rs.getString("COU_ID")); // id 필드값을 가져옴
                member.setCouName(rs.getString("NAME")); // 강의 테이블-name 필드값을 가져옴
                member.setName(rs.getString("ProfName")); // 교수 테이블-name 필드값을 가져옴
                member.setCredit(rs.getString("CREDIT")); // 강의 테이블-Credit 필드값을 가져옴
                member.setScore(rs.getString("SCORE")); // Score 필드값을 가져옴

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
}
