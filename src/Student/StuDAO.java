package Student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Common.ReadProps;
import Login.LoginGUI;

// Student - StuDAO.java
// ����� ����â�� �ʿ��� ����(�й�(������ȣ), �̸�, �а�, �ֹε�Ϲ�ȣ)�� DB���� ��������,
//���α׷� ������ ����� �� �ִ� �������� ����

public class StuDAO {
	// DB ������ ���� DB���� �ҷ�����
	ReadProps pr = new ReadProps();

	public String oracleDriver=pr.getDBName();
	public String oracleURL=pr.getDBUrl();
	public String oracleId=pr.getDBId();
	public String oraclePw=pr.getDBPw();

	// JDBC ���� �޼ҵ�
	public Connection getConnection() throws ClassNotFoundException, Exception{
		Connection con = null; // JDBC���� ���� ��ü �ʱ�ȭ

		try{
			Class.forName(oracleDriver); // JDBC Driver �˻�
			con = DriverManager.getConnection(oracleURL,oracleId,oraclePw); // JDBC URL

			// JDBC driver ���� �� ����ó��
		} catch(ClassNotFoundException e){
			System.out.println("[ERROR]"+e.getMessage());
			e.printStackTrace();

			// ��� ���ܻ�Ȳ ó��
		} catch(Exception e){
			System.out.println("[ERROR]"+e.getMessage());
			e.printStackTrace();
		}

		return con;
	}

	// ����� ������ �������� ��� �޼ҵ�
	public ArrayList<StudentGS> getMembers() throws SQLException, Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		ArrayList<StudentGS> members = new ArrayList<StudentGS>();
		StudentGS member;

		con = getConnection();
		String sql = "SELECT STUDENT.ID, STUDENT.NAME, DEPT.DEPT_NAME, RRN FROM STUDENT, DEPT WHERE DEPT.ID=STUDENT.DEPT_ID AND STUDENT.ID LIKE '" + LoginGUI.PassID + "'";

		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while(rs.next()){
				member = new StudentGS(); // �ӽ� ����
				member.setID(rs.getString("ID")); // id �ʵ尪�� ������
				member.setName(rs.getString("NAME")); // name �ʵ尪�� ������
				member.setDept(rs.getString("DEPT_NAME")); // dept �ʵ尪�� ������
				member.setRRN(rs.getString("RRN")); // RRN �ʵ尪�� ������
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

	// ��û������ ���Ǹ�ϸ� �������� ��� �޼ҵ�
	public ArrayList<StudentGS> OpenedClass() throws SQLException, Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;

		ArrayList<StudentGS> members = new ArrayList<StudentGS>();
		StudentGS member=null;

		con = getConnection();

		sql = "SELECT COURSES.ID, COURSES.NAME, PROFESSOR.NAME as " + "ProfName" + ", COURSES.CREDIT, COURSES.INTRO FROM COURSES, PROFESSOR WHERE COURSES.STATE=1 AND courses.prof_id=PROFESSOR.ID ORDER BY ID ASC";

		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while(rs.next()){
				member = new StudentGS(); // �ӽ� ����

				member.setID(rs.getString("ID")); // id �ʵ尪�� ������
				member.setCouName(rs.getString("NAME")); // ���� ���̺�-name �ʵ尪�� ������
				member.setName(rs.getString("ProfName")); // ���� ���̺�-name �ʵ尪�� ������
				member.setCredit(rs.getString("CREDIT")); // ���� ���̺�-Credit �ʵ尪�� ������
				member.setIntro(rs.getString("INTRO")); // ���� ���̺�-Credit �ʵ尪�� ������

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

	// ����� ���� ��Ȳ�� �������� ��� �޼ҵ�
	public ArrayList<StudentGS> resultTakes() throws SQLException, Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;

		ArrayList<StudentGS> members = new ArrayList<StudentGS>();
		StudentGS member=null;

		con = getConnection();

		sql = "SELECT TAKES.COU_ID, COURSES.NAME, PROFESSOR.NAME as " + "ProfName" + ", COURSES.CREDIT FROM COURSES, PROFESSOR, TAKES WHERE COURSES.ID=TAKES.COU_ID AND courses.prof_id=PROFESSOR.ID AND TAKES.STU_ID = '" + LoginGUI.PassID + "' ORDER BY COU_ID ASC";

		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while(rs.next()){
				member = new StudentGS(); // �ӽ� ����

				member.setID(rs.getString("COU_ID")); // id �ʵ尪�� ������
				member.setCouName(rs.getString("NAME")); // ���� ���̺�-name �ʵ尪�� ������
				member.setName(rs.getString("ProfName")); // ���� ���̺�-name �ʵ尪�� ������
				member.setCredit(rs.getString("CREDIT")); // ���� ���̺�-Credit �ʵ尪�� ������

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

	// ������û�� ���� ��� �޼ҵ�
	public void submitClass() throws SQLException, Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = null;

		con = getConnection();

		ArrayList<StudentGS> members = new ArrayList<StudentGS>();
		members = resultTakes();
		Object record[] = new Object[5];

		int SubmitCredit = 0;
		int cntCredit = 0;

		while(true) {
			for(int i=0; i<members.size();i++) {
				StudentGS data = members.get(i);
				record[i] = data.getCredit();
				SubmitCredit = Integer.parseInt(record[i].toString());
				cntCredit = cntCredit + SubmitCredit;
			}

			SubmitCredit = Integer.parseInt(SignUpClass.tbSignupClass.getValueAt(SignUpClass.tbSignupClass.getSelectedRow(), 3).toString());
			cntCredit = cntCredit + SubmitCredit;

			if(cntCredit>18) {
				JOptionPane.showMessageDialog(null, "��û������ ������ �ʰ��Ͽ����ϴ�.", "Error", JOptionPane.ERROR_MESSAGE);
				break;
			}

			else {
				sql = "INSERT INTO TAKES VALUES('" + LoginGUI.PassID + "', '" + SignUpClass.tbSignupClass.getValueAt(SignUpClass.tbSignupClass.getSelectedRow(), 0) + "', '" + "-" + "')";

				try {					
					pstmt = con.prepareStatement(sql);
					rs = pstmt.executeQuery();

					JOptionPane.showMessageDialog(null, "������û�� �Ϸ�Ǿ����ϴ�.");

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




	// ������Ҹ� ���� ��� �޼ҵ�
	public void CancelClass() throws SQLException, Exception{
		Connection con = null;

		PreparedStatement pstmt = null;

		ResultSet rsSelect = null;
		ResultSet rsDelete = null;

		String sq1Select = null;
		String sq1Delete = null;

		StudentGS Result = new StudentGS(); // �ӽ� ����

		con = getConnection();

		sq1Select = "SELECT COURSES.ID FROM COURSES, TAKES WHERE COURSES.NAME='" + CheckSignUpClass.tbchkSignupClass.getValueAt(CheckSignUpClass.tbchkSignupClass.getSelectedRow(), 1) + "' AND TAKES.COU_ID=COURSES.ID AND TAKES.STU_ID='" + LoginGUI.PassID + "'";

		try {
			pstmt = con.prepareStatement(sq1Select);
			rsSelect = pstmt.executeQuery();


			while(rsSelect.next()){
				Result.setID(rsSelect.getString("ID")); // id �ʵ尪�� ������
			}

			String ResultSelect = Result.getID();

			sq1Delete = "DELETE FROM TAKES WHERE COU_ID='"+ResultSelect+"' AND TAKES.STU_ID='"+LoginGUI.PassID+"'";

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

	// ������ȸ ��� �޼ҵ�
	public ArrayList<StudentGS> resultGrades() throws SQLException, Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;

		ArrayList<StudentGS> members = new ArrayList<StudentGS>();
		StudentGS member=null;

		con = getConnection();

		sql = "SELECT TAKES.COU_ID, COURSES.NAME, PROFESSOR.NAME AS " + "ProfName" + ", COURSES.CREDIT, TAKES.SCORE FROM COURSES, PROFESSOR, TAKES WHERE COURSES.ID=TAKES.COU_ID AND courses.prof_id=PROFESSOR.ID AND TAKES.STU_ID = '" + LoginGUI.PassID + "' ORDER BY COU_ID ASC";

		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while(rs.next()){
				member = new StudentGS(); // �ӽ� ����

				member.setID(rs.getString("COU_ID")); // id �ʵ尪�� ������
				member.setCouName(rs.getString("NAME")); // ���� ���̺�-name �ʵ尪�� ������
				member.setName(rs.getString("ProfName")); // ���� ���̺�-name �ʵ尪�� ������
				member.setCredit(rs.getString("CREDIT")); // ���� ���̺�-Credit �ʵ尪�� ������
				member.setScore(rs.getString("SCORE")); // Score �ʵ尪�� ������

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