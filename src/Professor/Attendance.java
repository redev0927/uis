package Professor;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class Attendance extends JPanel {
	private JTable tbAttendance;
	private String[] Header = { "학번", "이름", "등급" };
	private String [] Select = {};
	private DefaultTableModel model = new DefaultTableModel(null, Header){
        public boolean isCellEditable(int rowIndex, int mColIndex) {
        	return false;
        }
	};
	
	public static JComboBox cbLect;

	/**
	 * Create the panel.
	 */
	public Attendance() {
		setBackground(Color.WHITE);
		setLayout(null);

		JLabel lbTitle = new JLabel("출석부 조회");
		lbTitle.setForeground(new Color(0, 0, 128));
		lbTitle.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		lbTitle.setBounds(17, 15, 121, 21);
		add(lbTitle);

		JLabel lbLectName = new JLabel("진행중인 강의");
		lbLectName.setForeground(Color.DARK_GRAY);
		lbLectName.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		lbLectName.setBounds(27, 48, 111, 18);
		add(lbLectName);
		
		try {
			Select=new ProfDAO().IntoCombo();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		cbLect = new JComboBox(Select);
		//항목추가
		cbLect.setBounds(130, 47, 200, 24);
		add(cbLect);

		JButton btnRun = new JButton("조회");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Load(); // 정보가져오기
			}
		});
		btnRun.setBackground(new Color(0, 0, 128));
		btnRun.setForeground(new Color(255, 255, 255));
		btnRun.setContentAreaFilled(false); // 1
		btnRun.setOpaque(true); // 2 //1,2를 선언해줘야 변경하기 버튼의 배경색
		btnRun.setBackground(new Color(0, 0, 139));
		btnRun.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		btnRun.setBounds(334, 47, 83, 24);
		add(btnRun);

		tbAttendance = new JTable(model);
		tbAttendance.setBounds(17, 89, 401, 175);
		tbAttendance.getTableHeader().setReorderingAllowed(false);
		tbAttendance.getTableHeader().setResizingAllowed(false);
		JScrollPane scroll = new JScrollPane(tbAttendance);
		scroll.setLocation(17, 81);
		scroll.setSize(401, 204);
		scroll.setPreferredSize(new Dimension(470, 250));
		add(scroll);
	}

	// 데이터베이스의 정보를 가져오기 위한 메소드(Load)
	public void Load() {
		ProfDAO dao = new ProfDAO();
		ArrayList<ProfGS> members = new ArrayList<ProfGS>();
		Object record[] = new Object[4];

		model.setNumRows(0); // 검색 시마다 기존 테이블에 출력된 내용을 삭제

		try {
			members = dao.resultAtt();

			for (int i = 0; i < members.size(); i++) {
				ProfGS data = members.get(i);
				record[0] = data.getID();
				record[1] = data.getName();
				record[2] = data.getScore();
				model.addRow(record);
			}

		} catch (SQLException e) {
			System.out.println("[ERROR]" + e.getMessage()); // 예외 메시지 (console) 인쇄
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("[ERROR]" + e.getMessage());
			e.printStackTrace();
		}
	}
}
