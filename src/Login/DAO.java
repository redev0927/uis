package Login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Common.ReadProps;

// Login - DAO.java
// DB에 저장되어 있는 ID, PW의 값을 가져오며,
// 프로그램 내에서 사용할 수 있는 형식으로 저장

public class DAO {
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
	public ArrayList<LoginGS> getMembers() throws SQLException, Exception{
		Connection con = null;
		PreparedStatement pstmt = null;
		// "Statement pstmt = null;"에 비해 미리처리하기 때문에 빠르게 처리
		ResultSet rs = null;
		
		ArrayList<LoginGS> members = new ArrayList<LoginGS>();
		LoginGS member; // DB Table의 1개의 레코드(record : row)를 불러오기 위한 임시 변수
		
		con = getConnection();
		String sql = "SELECT * FROM STUDENT UNION SELECT * FROM PROFESSOR UNION SELECT ID, PW, NAME, NULL, NULL FROM STAFF";
		
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
					
			while(rs.next()){
				member = new LoginGS(); // 임시 생성
				member.setId(rs.getString("ID")); // id 필드값을 가져옴
				member.setPw(rs.getString("PW")); // pw 필드값을 가져옴
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