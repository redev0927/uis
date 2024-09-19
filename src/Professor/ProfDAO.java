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
// ����� ����â�� �ʿ��� ����(�й�(������ȣ), �̸�, �а�, �ֹε�Ϲ�ȣ)�� DB���� ��������,
//���α׷� ������ ����� �� �ִ� �������� ����

public class ProfDAO {
	// DB ������ ���� DB���� �ҷ�����
	ReadProps pr = new ReadProps();

	public String oracleDriver = pr.getDBName();
	public String oracleURL = pr.getDBUrl();
	public String oracleId = pr.getDBId();
	public String oraclePw = pr.getDBPw();

	// JDBC ���� �޼ҵ�
	public Connection getConnection() throws ClassNotFoundException, Exception {
		Connection con = null; // JDBC���� ���� ��ü �ʱ�ȭ

		try {
			Class.forName(oracleDriver); // JDBC Driver �˻�
			con = DriverManager.getConnection(oracleURL, oracleId, oraclePw); // JDBC URL

			// JDBC driver ���� �� ����ó��
		} catch (ClassNotFoundException e) {
			System.out.println("[ERROR]" + e.getMessage());
			e.printStackTrace();

			// ��� ���ܻ�Ȳ ó��
		} catch (Exception e) {
			System.out.println("[ERROR]" + e.getMessage());
			e.printStackTrace();
		}

		return con;
	}

	// ����� ������ �������� ��� �޼ҵ�
	public ArrayList<ProfGS> getMembers() throws SQLException, Exception {
		Connection con = null;
		PreparedStatement pstmt = null;
		// "Statement pstmt = null;"�� ���� �̸�ó���ϱ� ������ ������ ó��
		ResultSet rs = null;

		ArrayList<ProfGS> members = new ArrayList<ProfGS>();
		ProfGS member = null; // DB Table�� 1���� ���ڵ�(record : row)�� �ҷ����� ���� �ӽ� ����

		con = getConnection();
		String sql = "SELECT PROFESSOR.ID, PROFESSOR.NAME, DEPT.DEPT_NAME, RRN FROM PROFESSOR, DEPT WHERE DEPT.ID=PROFESSOR.DEPT_ID AND PROFESSOR.ID LIKE '"
				+ LoginGUI.PassID + "'";

		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				member = new ProfGS(); // �ӽ� ����
				member.setID(rs.getString("ID")); // id �ʵ尪�� ������
				member.setName(rs.getString("NAME")); // name �ʵ尪�� ������
				member.setDept(rs.getString("DEPT_NAME")); // dept �ʵ尪�� ������
				member.setRRN(rs.getString("RRN")); // RRN �ʵ尪�� ������

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

			Lect.add("���Ǹ� ������ �ּ���.");

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

	// �л������ȸ �˻������ �������� ��� �޼ҵ� (�й�, �̸�, �Ҽ��а�)
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
				member = new ProfGS(); // �ӽ� ����
				member.setID(rs.getString("STU_ID")); // id �ʵ尪�� ������
				member.setName(rs.getString("NAME")); // name �ʵ尪�� ������
				member.setDept(rs.getString("DEPT_NAME")); // DEPT �ʵ尪�� ������

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

	// �⼮����ȸ �˻������ �������� ��� �޼ҵ� (�й�, �̸�, �������)
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
				member = new ProfGS(); // �ӽ� ����
				member.setID(rs.getString("STU_ID")); // id �ʵ尪�� ������
				member.setName(rs.getString("NAME")); // name �ʵ尪�� ������
				member.setScore(rs.getString("SCORE")); // SCORE �ʵ尪�� ������

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

	// �����Է½� ��ȸ ����� �������� ��� �޼ҵ� (�й�, �̸�, �������)
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
				member = new ProfGS(); // �ӽ� ����
				member.setID(rs.getString("STU_ID")); // id �ʵ尪�� ������
				member.setName(rs.getString("NAME")); // name �ʵ尪�� ������
				member.setScore(rs.getString("SCORE")); // SCORE �ʵ尪�� ������

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

	// ������� ������ ���� ��� �޼ҵ�

	public void EditGrades() throws SQLException, Exception{
	      Connection con = null;
	   
	      PreparedStatement pstmt = null;
	      
	      ResultSet rsSelect = null;
	      ResultSet rsUpdate = null;

	      String sqlSelect = null;
	      String sqlUpdate = null;
	      
	      ProfGS Result = new ProfGS(); // �ӽ� ����

	      con = getConnection();

	      sqlSelect = "SELECT COURSES.ID FROM COURSES WHERE COURSES.NAME='"
	    		  		+ Professor.EditGrades.cbLect.getSelectedItem().toString() + "'";   

	      try {
	         pstmt = con.prepareStatement(sqlSelect);
	         rsSelect = pstmt.executeQuery();
	         

	         while(rsSelect.next()){
	            Result.setID(rsSelect.getString("ID")); // id �ʵ尪�� ������
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