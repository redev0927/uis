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
	
	private String[] Header = {"���ǹ�ȣ", "���Ǹ�", "��米��", "�������", "���"};
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
		
		JLabel lbTitle = new JLabel("������ȸ");
		lbTitle.setFont(new Font("���� ���", Font.BOLD, 18));
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
	
	// �����ͺ��̽��� ������ �������� ���� �޼ҵ�(Load)
	public void Load() {
		StuDAO dao = new StuDAO();
		ArrayList<StudentGS> members = new ArrayList<StudentGS>();
		Object record[] = new Object[5];

		model.setNumRows(0);	// �˻� �ø��� ���� ���̺� ��µ� ������ ����

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
			System.out.println("[ERROR]"+e.getMessage()); // ���� �޽��� (console) �μ�
			e.printStackTrace();
		} catch(Exception e) {
			System.out.println("[ERROR]"+e.getMessage());
			e.printStackTrace();
		}
	}

}
