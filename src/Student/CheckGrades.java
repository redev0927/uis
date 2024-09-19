package Student;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class CheckGrades extends JPanel {
	private JTable tbGrades;
	
	private String[] Header = {"강의번호", "강의명", "담당교수", "취득학점", "등급"};
	private DefaultTableModel model = new DefaultTableModel(null, Header){
        public boolean isCellEditable(int rowIndex, int mColIndex) {
        	return false;
        }
	};

	/**
	 * Create the panel.
	 */
	public CheckGrades() {
		setBackground(Color.WHITE);
		setLayout(null);
		
		JLabel lbTitle = new JLabel("성적조회");
		lbTitle.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		lbTitle.setBounds(17, 40, 82, 21);
		add(lbTitle);
		
		tbGrades = new JTable(model);
		tbGrades.setBackground(Color.white);
		tbGrades.getTableHeader().setReorderingAllowed(false);
		tbGrades.getTableHeader().setResizingAllowed(false);
		JScrollPane scroll = new JScrollPane(tbGrades);
		scroll.setLocation(17, 70);
		scroll.setSize(385, 150);
		scroll.setPreferredSize(new Dimension(470, 250));
		add(scroll);
		
		Load();
	}
	
	// 데이터베이스의 정보를 가져오기 위한 메소드(Load)
	public void Load() {
		StuDAO dao = new StuDAO();
		ArrayList<StudentGS> members = new ArrayList<StudentGS>();
		Object record[] = new Object[5];

		model.setNumRows(0);	// 검색 시마다 기존 테이블에 출력된 내용을 삭제

		try {				
			members = dao.resultGrades();

			for(int i=0; i<members.size();i++) {
				StudentGS data = members.get(i);
				record[0] = data.getID();
				record[1] = data.getCouName();
				record[2] = data.getName();
				record[3] = data.getCredit();
				record[4] = data.getScore();
				model.addRow(record);
			}

		} catch(SQLException e) {
			System.out.println("[ERROR]"+e.getMessage()); // 예외 메시지 (console) 인쇄
			e.printStackTrace();
		} catch(Exception e) {
			System.out.println("[ERROR]"+e.getMessage());
			e.printStackTrace();
		}
	}

}
