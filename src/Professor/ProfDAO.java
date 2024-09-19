package Professor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Common.ReadProps;
import Login.LoginGUI;
import Professor.ProfGS;

// Professor - ProfDAO.java
// 사용자 정보창에 필요한 정보(학번(교수번호), 이름, 학과, 주민등록번호)를 DB에서 가져오며,
//프로그램 내에서 사용할 수 있는 형식으로 저장

public class ProfDAO {
	// DB 연결을 위한 DB정보 불러오기
	ReadProps pr = new ReadProps();

	public String oracleDriver = pr.getDBName();
	public String oracleURL = pr.getDBUrl();
	public String oracleId = pr.getDBId();
	public String oraclePw = pr.getDBPw();

	// JDBC 연결 메소드
	public Connection getConnection() throws ClassNotFoundException, Exception {
		Connection con = null; // JDBC와의 연결 객체 초기화

		try {
			Class.forName(oracleDriver); // JDBC Driver 검색
			con = DriverManager.getConnection(oracleURL, oracleId, oraclePw); // JDBC URL

			// JDBC driver 누락 시 예외처리
		} catch (ClassNotFoundException e) {
			System.out.println("[ERROR]" + e.getMessage());
			e.printStackTrace();

			// 모든 예외상황 처리
		} catch (Exception e) {
			System.out.println("[ERROR]" + e.getMessage());
			e.printStackTrace();
		}

		return con;
	}

	// 사용자 정보를 가져오는 기능 메소드
	public ArrayList<ProfGS> getMembers() throws SQLException, Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		// "Statement pstmt = null;"에 비해 미리처리하기 때문에 빠르게 처리
		ResultSet rs = null;

		ArrayList<ProfGS> members = new ArrayList<ProfGS>();
		ProfGS member = null; // DB Table의 1개의 레코드(record : row)를 불러오기 위한 임시 변수

		con = getConnection();
		String sql = "SELECT PROFESSOR.ID, PROFESSOR.NAME, DEPT.DEPT_NAME, RRN FROM PROFESSOR, DEPT WHERE DEPT.ID=PROFESSOR.DEPT_ID AND PROFESSOR.ID LIKE '"
				+ LoginGUI.PassID + "'";

		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				member = new ProfGS(); // 임시 생성
				member.setID(rs.getString("ID")); // id 필드값을 가져옴
				member.setName(rs.getString("NAME")); // name 필드값을 가져옴
				member.setDept(rs.getString("DEPT_NAME")); // dept 필드값을 가져옴
				member.setRRN(rs.getString("RRN")); // RRN 필드값을 가져옴

				members.add(member);
			}

			rs.close();
			pstmt.close();
			con.close();

		} catch (SQLException e) {
			System.out.println("[ERROR]" + e.getMessage());
			e.printStackTrace();

		} catch (Exception e) {
			System.out.println("[ERROR]" + e.getMessage());
			e.printStackTrace();
		}

		return members;
	}

	public String[] IntoCombo() throws SQLException, Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;

		ArrayList<String> Lect = new ArrayList<>();
		String[] LectArray = {};

		con = getConnection();

		sql = "SELECT NAME FROM COURSES WHERE PROF_ID = '" + LoginGUI.PassID + "' AND STATE='1'";

		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			Lect.add("강의를 선택해 주세요.");

			while (rs.next()) {
				Lect.add(rs.getString("NAME"));
			}

			LectArray = new String[Lect.size()];
			int size = 0;

			for (String temp : Lect) {
				LectArray[size++] = temp;
			}

			rs.close();
			pstmt.close();
			con.close();

		} catch (SQLException e) {
			System.out.println("[ERROR]" + e.getMessage());
			e.printStackTrace();

		} catch (Exception e) {
			System.out.println("[ERROR]" + e.getMessage());
			e.printStackTrace();
		}

		return LectArray;
	}

	// 학생명단조회 검색결과를 가져오는 기능 메소드 (학번, 이름, 소속학과)
	public ArrayList<ProfGS> resultChkStu() throws SQLException, Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;

		ArrayList<ProfGS> members = new ArrayList<ProfGS>();
		ProfGS member = null;

		con = getConnection();

		sql = "SELECT TAKES.STU_ID, STUDENT.NAME, DEPT.DEPT_NAME FROM TAKES, STUDENT, DEPT, COURSES WHERE STUDENT.ID=TAKES.STU_ID AND DEPT.ID=STUDENT.DEPT_ID AND COURSES.ID=TAKES.COU_ID AND COURSES.NAME= '"
				+ Professor.CheckStudent.cbLect.getSelectedItem().toString() + "'";

		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				member = new ProfGS(); // 임시 생성
				member.setID(rs.getString("STU_ID")); // id 필드값을 가져옴
				member.setName(rs.getString("NAME")); // name 필드값을 가져옴
				member.setDept(rs.getString("DEPT_NAME")); // DEPT 필드값을 가져옴

				members.add(member);

			}

			rs.close();
			pstmt.close();
			con.close();

		} catch (SQLException e) {
			System.out.println("[ERROR]" + e.getMessage());
			e.printStackTrace();

		} catch (Exception e) {
			System.out.println("[ERROR]" + e.getMessage());
			e.printStackTrace();
		}

		return members;
	}

	// 출석부조회 검색결과를 가져오는 기능 메소드 (학번, 이름, 취득학점)
	public ArrayList<ProfGS> resultAtt() throws SQLException, Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;

		ArrayList<ProfGS> members = new ArrayList<ProfGS>();
		ProfGS member = null;

		con = getConnection();

		sql = "SELECT TAKES.STU_ID, STUDENT.NAME, TAKES.SCORE FROM TAKES, STUDENT, COURSES WHERE STUDENT.ID=TAKES.STU_ID AND TAKES.COU_ID = COURSES.ID AND COURSES.NAME= '"
				+ Professor.Attendance.cbLect.getSelectedItem().toString() + "' ORDER BY STU_ID ASC";

		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				member = new ProfGS(); // 임시 생성
				member.setID(rs.getString("STU_ID")); // id 필드값을 가져옴
				member.setName(rs.getString("NAME")); // name 필드값을 가져옴
				member.setScore(rs.getString("SCORE")); // SCORE 필드값을 가져옴

				members.add(member);

			}

			rs.close();
			pstmt.close();
			con.close();

		} catch (SQLException e) {
			System.out.println("[ERROR]" + e.getMessage());
			e.printStackTrace();

		} catch (Exception e) {
			System.out.println("[ERROR]" + e.getMessage());
			e.printStackTrace();
		}

		return members;
	}

	// 성적입력시 조회 결과를 가져오는 기능 메소드 (학번, 이름, 취득학점)
	public ArrayList<ProfGS> resultEdit() throws SQLException, Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;

		ArrayList<ProfGS> members = new ArrayList<ProfGS>();
		ProfGS member = null;

		con = getConnection();

		sql = "SELECT TAKES.STU_ID, STUDENT.NAME, TAKES.SCORE FROM TAKES, STUDENT, COURSES WHERE STUDENT.ID=TAKES.STU_ID AND TAKES.COU_ID = COURSES.ID AND COURSES.NAME= '"
				+ Professor.EditGrades.cbLect.getSelectedItem().toString() + "' ORDER BY STU_ID ASC";

		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				member = new ProfGS(); // 임시 생성
				member.setID(rs.getString("STU_ID")); // id 필드값을 가져옴
				member.setName(rs.getString("NAME")); // name 필드값을 가져옴
				member.setScore(rs.getString("SCORE")); // SCORE 필드값을 가져옴

				members.add(member);

			}

			rs.close();
			pstmt.close();
			con.close();

		} catch (SQLException e) {
			System.out.println("[ERROR]" + e.getMessage());
			e.printStackTrace();

		} catch (Exception e) {
			System.out.println("[ERROR]" + e.getMessage());
			e.printStackTrace();
		}

		return members;
	}

	// 취득학점 수정을 위한 기능 메소드

	public void EditGrades() throws SQLException, Exception{
	      Connection con = null;
	   
	      PreparedStatement pstmt = null;
	      
	      ResultSet rsSelect = null;
	      ResultSet rsUpdate = null;

	      String sqlSelect = null;
	      String sqlUpdate = null;
	      
	      ProfGS Result = new ProfGS(); // 임시 생성

	      con = getConnection();

	      sqlSelect = "SELECT COURSES.ID FROM COURSES WHERE COURSES.NAME='"
	    		  		+ Professor.EditGrades.cbLect.getSelectedItem().toString() + "'";   

	      try {
	         pstmt = con.prepareStatement(sqlSelect);
	         rsSelect = pstmt.executeQuery();
	         

	         while(rsSelect.next()){
	            Result.setID(rsSelect.getString("ID")); // id 필드값을 가져옴
	         }
	         
	         String ResultSelect = Result.getID();
	         
	         sqlUpdate = "UPDATE TAKES SET SCORE='" + EditGrades.tbEditGrades.getValueAt(EditGrades.tbEditGrades.getSelectedRow(), 2)
	                 + "' WHERE STU_ID='" + EditGrades.tbEditGrades.getValueAt(EditGrades.tbEditGrades.getSelectedRow(), 0) + "' AND COU_ID='" + ResultSelect + "'";

	         pstmt = con.prepareStatement(sqlUpdate);
	         rsUpdate = pstmt.executeQuery();

	         rsUpdate.close();
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

}