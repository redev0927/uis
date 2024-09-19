package Login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Common.ReadProps;

// Login - DAO.java
// DB�� ����Ǿ� �ִ� ID, PW�� ���� ��������,
// ���α׷� ������ ����� �� �ִ� �������� ����

public class DAO {
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
	public ArrayList<LoginGS> getMembers() throws SQLException, Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		// "Statement pstmt = null;"�� ���� �̸�ó���ϱ� ������ ������ ó��
		ResultSet rs = null;
		
		ArrayList<LoginGS> members = new ArrayList<LoginGS>();
		LoginGS member; // DB Table�� 1���� ���ڵ�(record : row)�� �ҷ����� ���� �ӽ� ����
		
		con = getConnection();
		String sql = "SELECT * FROM STUDENT UNION SELECT * FROM PROFESSOR UNION SELECT ID, PW, NAME, NULL, NULL FROM STAFF";
		
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
					
			while(rs.next()){
				member = new LoginGS(); // �ӽ� ����
				member.setId(rs.getString("ID")); // id �ʵ尪�� ������
				member.setPw(rs.getString("PW")); // pw �ʵ尪�� ������
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
	
		// ��й�ȣ ���� ��� �޼ҵ�
		public void PWUpdate(String pass_after) throws SQLException, Exception{
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;

			con = getConnection();
			
			if(LoginGUI.PassID.substring(0, 1).equals("S"))
				sql = "UPDATE STUDENT SET PW='" + pass_after + "' WHERE ID='" + LoginGUI.PassID + "'";
			else if(LoginGUI.PassID.substring(0, 1).equals("P"))
				sql = "UPDATE PROFESSOR SET PW='" + pass_after + "' WHERE ID='" + LoginGUI.PassID + "'";
			else
				sql = "UPDATE STAFF SET PW='" + pass_after + "' WHERE ID='" + LoginGUI.PassID + "'";
			
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
}