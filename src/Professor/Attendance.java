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
	private String[] Header = { "�й�", "�̸�", "���" };
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

		JLabel lbTitle = new JLabel("�⼮�� ��ȸ");
		lbTitle.setForeground(new Color(0, 0, 128));
		lbTitle.setFont(new Font("���� ���", Font.BOLD, 18));
		lbTitle.setBounds(17, 15, 121, 21);
		add(lbTitle);

		JLabel lbLectName = new JLabel("�������� ����");
		lbLectName.setForeground(Color.DARK_GRAY);
		lbLectName.setFont(new Font("���� ���", Font.BOLD, 15));
		lbLectName.setBounds(27, 48, 111, 18);
		add(lbLectName);
		
		try {
			Select=new ProfDAO().IntoCombo();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		cbLect = new JComboBox(Select);
		//�׸��߰�
		cbLect.setBounds(130, 47, 200, 24);
		add(cbLect);

		JButton btnRun = new JButton("��ȸ");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Load(); // ������������
			}
		});
		btnRun.setBackground(new Color(0, 0, 128));
		btnRun.setForeground(new Color(255, 255, 255));
		btnRun.setContentAreaFilled(false); // 1
		btnRun.setOpaque(true); // 2 //1,2�� ��������� �����ϱ� ��ư�� ����
		btnRun.setBackground(new Color(0, 0, 139));
		btnRun.setFont(new Font("���� ���", Font.BOLD, 15));
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

	// �����ͺ��̽��� ������ �������� ���� �޼ҵ�(Load)
	public void Load() {
		ProfDAO dao = new ProfDAO();
		ArrayList<ProfGS> members = new ArrayList<ProfGS>();
		Object record[] = new Object[4];

		model.setNumRows(0); // �˻� �ø��� ���� ���̺� ��µ� ������ ����

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
			System.out.println("[ERROR]" + e.getMessage()); // ���� �޽��� (console) �μ�
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("[ERROR]" + e.getMessage());
			e.printStackTrace();
		}
	}
}
