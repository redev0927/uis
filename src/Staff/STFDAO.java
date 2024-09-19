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
// ����� ����â�� �ʿ��� ����(�й�(������ȣ), �̸�, �а�, �ֹε�Ϲ�ȣ)�� DB���� ��������,
//���α׷� ������ ����� �� �ִ� �������� ����

public class STFDAO {
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
				member = new STFGS(); // �ӽ� ����
				member.setID(rs.getString("ID")); // id �ʵ尪�� ������
				member.setName(rs.getString("NAME")); // name �ʵ尪�� ������

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

	// ����� ����� ���� ��� �޼ҵ�
	public void setUser() throws SQLException, Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = null;

		con = getConnection();

		if(RegUser.Perm.equals("�л�"))
			sql = "INSERT INTO STUDENT VALUES ('" + RegUser.UserID.getText() + "', '"
					+ RegUser.UserRRN.getText().substring(6, 13) + "', '" + RegUser.UserName.getText()
					+ "', '" + RegUser.UserRRN.getText() + "', '" + RegUser.saveDeptNum + "')";
		else if(RegUser.Perm.equals("����"))
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

	// ����� �˻������ �������� ��� �޼ҵ�
	public ArrayList<STFGS> resultSearch() throws SQLException, Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;

		ArrayList<STFGS> members = new ArrayList<STFGS>();
		STFGS member=null;

		con = getConnection();

		if(SearchUser.Perm.equals("�л�"))
			sql = "SELECT STUDENT.ID, STUDENT.NAME, DEPT.DEPT_NAME, RRN FROM STUDENT, DEPT WHERE DEPT.ID=STUDENT.DEPT_ID AND STUDENT.NAME LIKE '" + SearchUser.tfName.getText() + "' ORDER BY ID ASC";
		else if(SearchUser.Perm.equals("����"))
			sql = "SELECT PROFESSOR.ID, PROFESSOR.NAME, DEPT.DEPT_NAME, RRN FROM PROFESSOR, DEPT WHERE DEPT.ID=PROFESSOR.DEPT_ID AND PROFESSOR.NAME LIKE '" + SearchUser.tfName.getText() + "' ORDER BY ID ASC";

		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while(rs.next()){
				member = new STFGS(); // �ӽ� ����
				member.setID(rs.getString("ID")); // id �ʵ尪�� ������
				member.setName(rs.getString("NAME")); // name �ʵ尪�� ������
				member.setDept(rs.getString("DEPT_NAME")); // DEPT �ʵ尪�� ������
				member.setRRN(rs.getString("RRN")); // RRN ���ڸ� �ʵ尪�� ������

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

	// ����� ������ ���� ��� �޼ҵ�
	public void DelUser() throws SQLException, Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = null;

		con = getConnection();

		if(SearchUser.Perm.equals("�л�"))
			sql = "DELETE FROM STUDENT WHERE NAME = '" +SearchUser.tfName.getText()+ "'";
		else if(SearchUser.Perm.equals("����"))
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

	// ����� ������ ���� ��� �޼ҵ�
	public void EditUser() throws SQLException, Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = null;

		con = getConnection();

		if(SearchUser.Perm.equals("�л�"))
			sql = "UPDATE STUDENT SET ID='" + SearchUser.ID + "', NAME='"+SearchUser.Name+"', DEPT_ID='" + SearchUser.Dept
			+"', RRN='"+SearchUser.RRN+"' WHERE NAME = '" +SearchUser.tfName.getText()+ "'";
		else if(SearchUser.Perm.equals("����"))
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

	//���
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

	//���¸� �˻������ �������� ��� �޼ҵ�
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
				member = new STFGS(); // �ӽ� ����
				member.setClassNumber(rs.getString("ID")); // �ʵ尪�� ������
				member.setClassName(rs.getString("NAME")); // name �ʵ尪�� ������
				member.setProfessor(rs.getString("PROF_ID")); // RRN ���ڸ� �ʵ尪�� ������
				member.setScore(rs.getString("CREDIT")); // RRN ���ڸ� �ʵ尪�� ������
				member.setCount(rs.getString("MAX")); // DEPT �ʵ尪�� ������
				member.setExp(rs.getString("INTRO")); // RRN ���ڸ� �ʵ尪�� ������
				member.setSTATE(rs.getString("STATE")); //STATE �ʵ尪�� ������

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

	// ���ǰ����� ���� ��� �޼ҵ�
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

	// û���� �߱� ��� ��ȸ����� �������� ��� �޼ҵ�
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
				member = new STFGS(); // �ӽ� ����
				member.setID(rs.getString("ID")); // id �ʵ尪�� ������
				member.setName(rs.getString("NAME")); // name �ʵ尪�� ������
				member.setDept(Bill); // DEPT �ʵ尪�� ������
				member.setRRN(LoginGUI.PassID); // RRN ���ڸ� �ʵ尪�� ������

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