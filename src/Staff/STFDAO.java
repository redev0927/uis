package Staff;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Common.ReadProps;
import Login.LoginGUI;

// Staff - STFDAO.java
// 사용자 정보창에 필요한 정보(학번(교수번호), 이름, 학과, 주민등록번호)를 DB에서 가져오며,
//프로그램 내에서 사용할 수 있는 형식으로 저장

public class STFDAO {
	// DB 연결을 위한 DB정보 불러오기
	ReadProps pr = new ReadProps();

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
	public ArrayList<STFGS> getMembers() throws SQLException, Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		ArrayList<STFGS> members = new ArrayList<STFGS>();
		STFGS member=null;

		con = getConnection();
		String sql = "SELECT * FROM STAFF WHERE ID LIKE '" + LoginGUI.PassID + "'";

		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while(rs.next()){
				member = new STFGS(); // 임시 생성
				member.setID(rs.getString("ID")); // id 필드값을 가져옴
				member.setName(rs.getString("NAME")); // name 필드값을 가져옴

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
		ResultSet rs = null;

		String sql = null;

		con = getConnection();

		if(RegUser.Perm.equals("학생"))
			sql = "INSERT INTO STUDENT VALUES ('" + RegUser.UserID.getText() + "', '"
					+ RegUser.UserRRN.getText().substring(6, 13) + "', '" + RegUser.UserName.getText()
					+ "', '" + RegUser.UserRRN.getText() + "', '" + RegUser.saveDeptNum + "')";
		else if(RegUser.Perm.equals("교수"))
			sql = "INSERT INTO PROFESSOR VALUES ('" + RegUser.UserID.getText() + "', '"
					+ RegUser.UserRRN.getText().substring(6, 13) + "', '" + RegUser.UserName.getText()
					+ "', '" + RegUser.UserRRN.getText() + "', '" + RegUser.saveDeptNum + "')";

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

	// 사용자 검색결과를 가져오는 기능 메소드
	public ArrayList<STFGS> resultSearch() throws SQLException, Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;

		ArrayList<STFGS> members = new ArrayList<STFGS>();
		STFGS member=null;

		con = getConnection();

		if(SearchUser.Perm.equals("학생"))
			sql = "SELECT STUDENT.ID, STUDENT.NAME, DEPT.DEPT_NAME, RRN FROM STUDENT, DEPT WHERE DEPT.ID=STUDENT.DEPT_ID AND STUDENT.NAME LIKE '" + SearchUser.tfName.getText() + "' ORDER BY ID ASC";
		else if(SearchUser.Perm.equals("교수"))
			sql = "SELECT PROFESSOR.ID, PROFESSOR.NAME, DEPT.DEPT_NAME, RRN FROM PROFESSOR, DEPT WHERE DEPT.ID=PROFESSOR.DEPT_ID AND PROFESSOR.NAME LIKE '" + SearchUser.tfName.getText() + "' ORDER BY ID ASC";

		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while(rs.next()){
				member = new STFGS(); // 임시 생성
				member.setID(rs.getString("ID")); // id 필드값을 가져옴
				member.setName(rs.getString("NAME")); // name 필드값을 가져옴
				member.setDept(rs.getString("DEPT_NAME")); // DEPT 필드값을 가져옴
				member.setRRN(rs.getString("RRN")); // RRN 앞자리 필드값을 가져옴

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

	// 사용자 삭제를 위한 기능 메소드
	public void DelUser() throws SQLException, Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = null;

		con = getConnection();

		if(SearchUser.Perm.equals("학생"))
			sql = "DELETE FROM STUDENT WHERE NAME = '" +SearchUser.tfName.getText()+ "'";
		else if(SearchUser.Perm.equals("교수"))
			sql = "DELETE FROM PROFESSOR WHERE NAME = '" +SearchUser.tfName.getText()+ "'";

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
		ResultSet rs = null;

		String sql = null;

		con = getConnection();

		if(SearchUser.Perm.equals("학생"))
			sql = "UPDATE STUDENT SET ID='" + SearchUser.ID + "', NAME='"+SearchUser.Name+"', DEPT_ID='" + SearchUser.Dept
			+"', RRN='"+SearchUser.RRN+"' WHERE NAME = '" +SearchUser.tfName.getText()+ "'";
		else if(SearchUser.Perm.equals("교수"))
			sql = "UPDATE PROFESSOR SET ID='" + SearchUser.ID + "', NAME='"+SearchUser.Name+"', DEPT_ID='" + SearchUser.Dept
			+"', RRN='"+SearchUser.RRN+"' WHERE NAME = '" +SearchUser.tfName.getText()+ "'";

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

	//등록
	public void setClass() throws SQLException, Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = null;

		con = getConnection();

		sql = "INSERT INTO COURSES VALUES ('" + Integer.parseInt(RegClass.tfClassNumber.getText()) + "', '"
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
	public ArrayList<STFGS> Search() throws SQLException, Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sql2 = null;

		ArrayList<STFGS> members = new ArrayList<STFGS>();
		STFGS member=null;

		con = getConnection();


		sql2 = "SELECT COURSES.ID, COURSES.NAME, COURSES.PROF_ID, COURSES.CREDIT, COURSES.MAX, COURSES.INTRO ,STATE.NAME AS " + "STATE" + " FROM COURSES, STATE WHERE COURSES.STATE = STATE.ID";

		try {
			pstmt = con.prepareStatement(sql2);
			rs = pstmt.executeQuery();

			while(rs.next()){
				member = new STFGS(); // 임시 생성
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

		sql = "UPDATE COURSES SET STATE = '1' WHERE NAME='" + CheckClass.tbSearch.getValueAt(CheckClass.tbSearch.getSelectedRow(), 1) + "'";

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
	public ArrayList<STFGS> resultBillSearch() throws SQLException, Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String Bill = "3,720,000";

		ArrayList<STFGS> members = new ArrayList<STFGS>();
		STFGS member=null;

		con = getConnection();

		sql = "SELECT STUDENT.ID, STUDENT.NAME FROM STUDENT WHERE STUDENT.NAME LIKE '" + BillPrint.tfName.getText() + "' ORDER BY ID ASC";

		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while(rs.next()){
				member = new STFGS(); // 임시 생성
				member.setID(rs.getString("ID")); // id 필드값을 가져옴
				member.setName(rs.getString("NAME")); // name 필드값을 가져옴
				member.setDept(Bill); // DEPT 필드값을 가져옴
				member.setRRN(LoginGUI.PassID); // RRN 앞자리 필드값을 가져옴

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