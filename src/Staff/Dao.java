package Staff;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Database.ReadProp;
import Login.Graphic;

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
        GetterSetter member=null;

        con = getConnection();
        String sql = "SELECT * FROM staff WHERE staffid LIKE '" + Graphic.PassID + "'";

        try {
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while(rs.next()){
                member = new GetterSetter(); // 임시 생성
                member.setID(rs.getString("staffid")); // id 필드값을 가져옴
                member.setName(rs.getString("staffname")); // name 필드값을 가져옴

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

    // 사용자 등록을 위한 기능 메소드
    public void setUser() throws SQLException, Exception{
        Connection con = null;
        PreparedStatement pstmt = null;
//        ResultSet rs = null;

        String sql = null;

        con = getConnection();

        if(RegUser.Perm.equals("학생")) {
            sql = "INSERT INTO student VALUES ('" + RegUser.UserID.getText() + "', '"
                    + RegUser.UserRRN.getText().substring(6, 13) + "', '" + RegUser.UserName.getText()
                    + "', " + RegUser.UserRRN.getText() + ", " + RegUser.saveDeptNum + ")";
            System.out.println(sql);
        }
        else if(RegUser.Perm.equals("교수")) {
            sql = "INSERT INTO professor VALUES ('" + RegUser.UserID.getText() + "', '"
                    + RegUser.UserRRN.getText().substring(6, 13) + "', '" + RegUser.UserName.getText()
                    + "', " + RegUser.UserRRN.getText() + ", " + RegUser.saveDeptNum + ")";
            System.out.println(sql);
        }

        try {
            pstmt = con.prepareStatement(sql);

            // 240831 - UPDATE, INSERT, DELETE문을 사용할 때 executeUpdate()
            // 240831 - SELECT문을 사용할 때 executeQuery()
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

    // 사용자 검색결과를 가져오는 기능 메소드
    public ArrayList<GetterSetter> resultSearch() throws SQLException, Exception{
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;

        ArrayList<GetterSetter> members = new ArrayList<GetterSetter>();
        GetterSetter member=null;

        con = getConnection();

        if(SearchUser.Perm.equals("학생")) {
            sql = "SELECT studentid, studentname, deptname, studentnum FROM student, dept WHERE dept.deptnum=student.dept_deptnum AND student.studentname LIKE '" + SearchUser.tfName.getText() + "' ORDER BY student.studentid ASC";
            try {
                pstmt = con.prepareStatement(sql);
                rs = pstmt.executeQuery();

                while(rs.next()){
                    member = new GetterSetter(); // 임시 생성
                    member.setID(rs.getString("studentid")); // id 필드값을 가져옴
                    member.setName(rs.getString("studentname")); // name 필드값을 가져옴
                    member.setDept(rs.getString("deptname")); // DEPT 필드값을 가져옴
                    member.setRRN(rs.getString("studentnum")); // RRN 앞자리 필드값을 가져옴

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
        }
        else if(SearchUser.Perm.equals("교수")) {
            sql = "SELECT professorid, professorname, deptname, professornum FROM professor, dept WHERE dept.deptnum=professor.dept_deptnum AND professorname LIKE '" + SearchUser.tfName.getText() + "' ORDER BY professor.professorid ASC";
            try {
                pstmt = con.prepareStatement(sql);
                rs = pstmt.executeQuery();

                while(rs.next()){
                    member = new GetterSetter(); // 임시 생성
                    member.setID(rs.getString("professorid")); // id 필드값을 가져옴
                    member.setName(rs.getString("professorname")); // name 필드값을 가져옴
                    member.setDept(rs.getString("deptname")); // DEPT 필드값을 가져옴
                    member.setRRN(rs.getString("professornum")); // RRN 앞자리 필드값을 가져옴

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
        }

        return members;
    }

    // 사용자 삭제를 위한 기능 메소드
    public void DelUser() throws SQLException, Exception{
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = null;

        con = getConnection();

        if(SearchUser.Perm.equals("학생"))
            sql = "DELETE FROM student WHERE studentname = '" +SearchUser.tfName.getText()+ "'";
        else if(SearchUser.Perm.equals("교수"))
            sql = "DELETE FROM professor WHERE professorname = '" +SearchUser.tfName.getText()+ "'";

        try {
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

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
    }

    // 사용자 수정을 위한 기능 메소드
    public void EditUser() throws SQLException, Exception{
        Connection con = null;
        PreparedStatement pstmt = null;
//        ResultSet rs = null;

        String sql = null;

        con = getConnection();

        if(SearchUser.Perm.equals("학생"))
            sql = "UPDATE student SET studentid='" + SearchUser.ID + "', studentname='"+SearchUser.Name+"', dept_deptnum='" + SearchUser.Dept
                    +"', studentnum='"+SearchUser.RRN+"' WHERE studentname = '" +SearchUser.tfName.getText()+ "'";
        else if(SearchUser.Perm.equals("교수"))
            sql = "UPDATE professor SET professorid='" + SearchUser.ID + "', professorname='"+SearchUser.Name+"', dept_deptnum='" + SearchUser.Dept
                    +"', professornum='"+SearchUser.RRN+"' WHERE professorname = '" +SearchUser.tfName.getText()+ "'";

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

    //등록
    public void setClass() throws SQLException, Exception{
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = null;

        con = getConnection();

        sql = "INSERT INTO lecture VALUES ('" + Integer.parseInt(RegClass.tfClassNumber.getText()) + "', '"
                + RegClass.tfClassName.getText() + "', '" + RegClass.tfScore.getText()
                + "', '"+RegClass.tfProfessor.getText() +"', '"+ Integer.parseInt(RegClass.tfCount.getText())+ "', '" + RegClass.tfExp.getText() +"', 0)";


        try {
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

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
    }

    //강좌명 검색결과를 가져오는 기능 메소드
    public ArrayList<GetterSetter> Search() throws SQLException, Exception{
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        String sql2 = null;

        ArrayList<GetterSetter> members = new ArrayList<GetterSetter>();
        GetterSetter member=null;

        con = getConnection();


        sql2 = "SELECT COURSES.ID, COURSES.NAME, COURSES.PROF_ID, COURSES.CREDIT, COURSES.MAX, COURSES.INTRO ,STATE.NAME AS " + "STATE" + " FROM COURSES, STATE WHERE COURSES.STATE = STATE.ID";

        try {
            pstmt = con.prepareStatement(sql2);
            rs = pstmt.executeQuery();

            while(rs.next()){
                member = new GetterSetter(); // 임시 생성
                member.setClassNumber(rs.getString("ID")); // 필드값을 가져옴
                member.setClassName(rs.getString("NAME")); // name 필드값을 가져옴
                member.setProfessor(rs.getString("PROF_ID")); // RRN 앞자리 필드값을 가져옴
                member.setScore(rs.getString("CREDIT")); // RRN 앞자리 필드값을 가져옴
                member.setCount(rs.getString("MAX")); // DEPT 필드값을 가져옴
                member.setExp(rs.getString("INTRO")); // RRN 앞자리 필드값을 가져옴
                member.setSTATE(rs.getString("STATE")); //STATE 필드값을 가져옴

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

    // 강의개설을 위한 기능 메소드
    public void OpenClass() throws SQLException, Exception{
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = null;

        con = getConnection();

        sql = "UPDATE COURSES SET STATE = '1' WHERE NAME='" + ChkClass.tbSearch.getValueAt(ChkClass.tbSearch.getSelectedRow(), 1) + "'";

        try {
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

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
    }

    // 청구서 발급 대상 조회결과를 가져오는 기능 메소드
    public ArrayList<GetterSetter> resultBillSearch() throws SQLException, Exception{
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        String Bill = "3,720,000";

        ArrayList<GetterSetter> members = new ArrayList<GetterSetter>();
        GetterSetter member=null;

        con = getConnection();

        sql = "SELECT studentid, studentname FROM student WHERE studentname LIKE '" + Billing.tfName.getText() + "' ORDER BY studentid ASC";

        try {
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while(rs.next()){
                member = new GetterSetter(); // 임시 생성
                member.setID(rs.getString("studentid")); // id 필드값을 가져옴
                member.setName(rs.getString("studentname")); // name 필드값을 가져옴
                member.setDept(Bill); // DEPT 필드값을 가져옴
                member.setRRN(Graphic.PassID); // RRN 앞자리 필드값을 가져옴

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
