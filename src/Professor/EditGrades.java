package Professor;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

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

public class EditGrades extends JPanel {
	public static JTable tbEditGrades;
	private String[] Header = { "�й�", "�̸�", "���" };
	private String [] Select = {};
	private DefaultTableModel model = new DefaultTableModel(null, Header){ //�������� �����Ұ�
		public boolean isCellEditable(int rowIndex, int mColIndex) {
			int i=0;
			boolean state;

			if(mColIndex==2+(3*i)) {
				state = true;
				i++;
			}

			else {
				state = false;
				i++;
			}

			return state;
		}
	};


	public static String Lect = null;
	public static String ID = null;
	public static String Name = null;
	public static String Score = null;

	public static JComboBox cbLect;

	/**
	 * Create the panel.
	 */
	public EditGrades() {
		setBackground(Color.WHITE);
		setLayout(null);

		JLabel lbTitle = new JLabel("���� �Է�");
		lbTitle.setBounds(17, 15, 121, 21);
		lbTitle.setForeground(new Color(0, 0, 128));
		lbTitle.setFont(new Font("���� ���", Font.BOLD, 18));
		add(lbTitle);

		JLabel lbLectName = new JLabel("�������� ����");
		lbLectName.setBounds(17, 55, 111, 18);
		lbLectName.setForeground(Color.DARK_GRAY);
		lbLectName.setFont(new Font("���� ���", Font.BOLD, 15));
		add(lbLectName);


		try {
			Select=new ProfDAO().IntoCombo();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		cbLect = new JComboBox(Select);
		cbLect.setBounds(119, 52, 174, 24);

		add(cbLect);

		JButton btnRun = new JButton("��ȸ");
		btnRun.setBounds(298, 53, 65, 23);
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Load(); // ������������
			}
		});
		btnRun.setForeground(new Color(255, 255, 255));
		btnRun.setFont(new Font("���� ���", Font.BOLD, 15));
		btnRun.setContentAreaFilled(false); // 1
		btnRun.setOpaque(true); // 2 //1,2�� ��������� �����ϱ� ��ư�� ����
		btnRun.setBackground(new Color(0, 0, 139));
		add(btnRun);

		tbEditGrades = new JTable(model);
		tbEditGrades.setBounds(17, 89, 401, 175);
		tbEditGrades.getTableHeader().setReorderingAllowed(false);
		tbEditGrades.getTableHeader().setResizingAllowed(false);
		JScrollPane scroll = new JScrollPane(tbEditGrades);
		scroll.setBounds(17, 82, 416, 203);
		scroll.setPreferredSize(new Dimension(470, 250));
		add(scroll);

		JButton btnEdit = new JButton("����");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ������ �� �� ��������
				int selectedRow = tbEditGrades.getSelectedRow();
				selectedRow = tbEditGrades.convertRowIndexToModel(selectedRow);
				ID = (String)tbEditGrades.getModel().getValueAt(selectedRow, 0);
				Name = (String)tbEditGrades.getModel().getValueAt(selectedRow, 1);
				Score = (String)tbEditGrades.getModel().getValueAt(selectedRow, 2);


				try {
					new ProfDAO().EditGrades();
					JOptionPane.showMessageDialog(null, "�����Ǿ����ϴ�.");
				} catch (Exception e1) {
					System.out.println("[ERROR]"+e1.getMessage());
					e1.printStackTrace();
				}
			}
		});
		btnEdit.setFont(new Font("���� ���", Font.BOLD, 15));
		btnEdit.setBackground(new Color(0, 0, 128));
		btnEdit.setForeground(Color.WHITE);
		btnEdit.setContentAreaFilled(false); // 1
		btnEdit.setOpaque(true); // 2 //1,2�� ��������� �����ϱ� ��ư�� ����
		btnEdit.setBackground(new Color(0, 0, 139));
		btnEdit.setBounds(366, 53, 65, 23);
		add(btnEdit);

	}

	// �����ͺ��̽��� ������ �������� ���� �޼ҵ�(Load)
	public void Load() {
		ProfDAO dao = new ProfDAO();
		ArrayList<ProfGS> members = new ArrayList<ProfGS>();
		Object record[] = new Object[3];

		model.setNumRows(0); // �˻� �ø��� ���� ���̺� ��µ� ������ ����

		try {
			members = dao.resultEdit();

			for (int i = 0; i < members.size(); i++) {
				ProfGS data = members.get(i);
				record[0] = data.getID();
				record[1] = data.getName();
				record[2] = data.getScore();

				model.addRow(record);
			}

		} catch (SQLException e) {
			System.out.println("[ERROR]" + e.getMessage()); // ���� �޽��� (console) �μ�
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("[ERROR]" + e.getMessage());
			e.printStackTrace();
		}
	}
}
