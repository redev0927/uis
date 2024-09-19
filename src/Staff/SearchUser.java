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

public class SearchUser extends JPanel {
	public static JTextField tfName;
	private JTable tbSearch;

	private String[] Header = {"���̵�", "�̸�", "�Ҽ��а�", "�ֹε�Ϲ�ȣ"};
	private DefaultTableModel model = new DefaultTableModel(null, Header){
        public boolean isCellEditable(int rowIndex, int mColIndex) {
        	int i=0;
        	boolean state;
        	
        	if(mColIndex==(4*i)) {
        		state = false;
        		i++;
        	}
        	
        	else {
        		state = true;
        		i++;
        	}
        	
        	return state;
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
public SearchUser() {
	setBackground(Color.WHITE);
	setLayout(null);

	JLabel label = new JLabel("����� �˻�/����");
	label.setBounds(17, 15, 157, 21);
	label.setFont(new Font("���� ���", Font.BOLD, 18));
	add(label);

	JLabel lbIntro = new JLabel("<html><center>�Ҽ��а� ���� �� �Ʒ� �а� �ڵ�� �Է����ּ���.</center>100:�����а� 101:�����а� 102:���ڰ��а� 103:ȭ�а��а� 104:�װ����ְ��а�</html>");
	lbIntro.setFont(new Font("���� ���", Font.BOLD, 12));
	lbIntro.setForeground(new Color(0, 0, 128));
	lbIntro.setBounds(0, 223, 450, 42);
	add(lbIntro);

	JLabel lbPerm = new JLabel("����");
	lbPerm.setFont(new Font("���� ���", Font.BOLD, 15));
	lbPerm.setBounds(28, 40, 40, 21);
	add(lbPerm);

	JLabel lbName = new JLabel("�̸�");
	lbName.setBounds(177, 40, 40, 21);
	lbName.setFont(new Font("���� ���", Font.BOLD, 15));
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
	cbPerm.addItem("����");
	cbPerm.addItem("�л�");
	cbPerm.setSelectedItem(null);
	cbPerm.setBounds(64, 40, 99, 24);
	add(cbPerm);

	JButton btnEdit = new JButton("����");
	btnEdit.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			// ������ �� �� ��������
			int selectedRow = tbSearch.getSelectedRow();
			selectedRow = tbSearch.convertRowIndexToModel(selectedRow);
			ID = (String)tbSearch.getModel().getValueAt(selectedRow, 0);
			Name = (String)tbSearch.getModel().getValueAt(selectedRow, 1);
			Dept = (String)tbSearch.getModel().getValueAt(selectedRow, 2);
			RRN = (String)tbSearch.getModel().getValueAt(selectedRow, 3);

			try {
				new STFDAO().EditUser();
				JOptionPane.showMessageDialog(null, "�����Ǿ����ϴ�.");
			} catch (Exception e1) {
				System.out.println("[ERROR]"+e1.getMessage());
				e1.printStackTrace();
			}
		}
	});

	JButton btnSearch = new JButton("�˻�");
	btnSearch.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			Perm = cbPerm.getSelectedItem().toString();
			Load();
		}
	});
	btnSearch.setFont(new Font("���� ���", Font.BOLD, 15));
	btnSearch.setBounds(329, 37, 71, 27);
	add(btnSearch);
	btnEdit.setFont(new Font("���� ���", Font.BOLD, 15));
	btnEdit.setBounds(91, 270, 105, 27);
	add(btnEdit);

	JButton btnDel = new JButton("����");
	btnDel.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			try {
				new STFDAO().DelUser();
				model.setNumRows(0);
				JOptionPane.showMessageDialog(null, "�����Ǿ����ϴ�.");
			} catch (Exception e) {
				System.out.println("[ERROR]"+e.getMessage());
				e.printStackTrace();
			}
		}
	});
	btnDel.setFont(new Font("���� ���", Font.BOLD, 15));
	btnDel.setBounds(210, 270, 105, 27);
	add(btnDel);
}

// �����ͺ��̽��� ������ �������� ���� �޼ҵ�(Load)
public void Load() {
	STFDAO dao = new STFDAO();
	ArrayList<STFGS> members = new ArrayList<STFGS>();
	Object record[] = new Object[4];

	model.setNumRows(0);	// �˻� �ø��� ���� ���̺� ��µ� ������ ����

	try {				
		members = dao.resultSearch();

		for(int i=0; i<members.size();i++) {
			STFGS data = members.get(i);
			record[0] = data.getID();
			record[1] = data.getName();
			record[2] = data.getDept();
			record[3] = data.getRRN();
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
