package Staff;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class BillPrint extends JPanel {
	public static JTextField tfName;
	private JTable tbSearch;

	private String[] Header = {"아이디", "이름", "청구금액", "담당자"};
	private DefaultTableModel model = new DefaultTableModel(null, Header){
		public boolean isCellEditable(int rowIndex, int mColIndex) {
			return false;
		}
	};

	public static String Perm = null;

	public static String ID = null;
	public static String Name = null;
	public static String Dept = null;
	public static String RRN = null;

	/**
	 * Create the panel.
	 */
	public BillPrint() {
		setBackground(Color.WHITE);
		setLayout(null);

		JLabel label = new JLabel("청구서 발급");
		label.setBounds(17, 15, 157, 21);
		label.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		add(label);

		JLabel lbPerm = new JLabel("직급");
		lbPerm.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		lbPerm.setBounds(28, 40, 40, 21);
		add(lbPerm);

		JLabel lbName = new JLabel("이름");
		lbName.setBounds(177, 40, 40, 21);
		lbName.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		add(lbName);

		tfName = new JTextField();
		tfName.setBounds(216, 40, 99, 21);
		add(tfName);
		tfName.setColumns(10);

		tbSearch = new JTable(model);
		tbSearch.setBackground(Color.white);
		tbSearch.getTableHeader().setReorderingAllowed(false);
		tbSearch.getTableHeader().setResizingAllowed(false);
		JScrollPane scroll = new JScrollPane(tbSearch);
		scroll.setLocation(17, 70);
		scroll.setSize(385, 150);
		scroll.setPreferredSize(new Dimension(470, 250));
		add(scroll);

		JComboBox cbPerm = new JComboBox();
		cbPerm.addItem("학생");
		cbPerm.setSelectedItem(null);
		cbPerm.setBounds(64, 40, 99, 24);
		add(cbPerm);

		JButton btnPrint = new JButton("발급");
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "청구서가 발급되었습니다.");
			}
		});

		JButton btnSearch = new JButton("검색");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Load();
			}
		});
		btnSearch.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		btnSearch.setBounds(329, 37, 71, 27);
		add(btnSearch);
		btnPrint.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		btnPrint.setBounds(153, 237, 105, 27);
		add(btnPrint);
	}

	// 데이터베이스의 정보를 가져오기 위한 메소드(Load)
	public void Load() {
		STFDAO dao = new STFDAO();
		ArrayList<STFGS> members = new ArrayList<STFGS>();
		Object record[] = new Object[4];

		model.setNumRows(0);	// 검색 시마다 기존 테이블에 출력된 내용을 삭제

		try {				
			members = dao.resultBillSearch();

			for(int i=0; i<members.size();i++) {
				STFGS data = members.get(i);
				record[0] = data.getID();
				record[1] = data.getName();
				record[2] = data.getDept();
				record[3] = data.getRRN();
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