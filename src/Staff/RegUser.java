package Staff;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

//Staff - RegUser.java
//����� ������ ����ϴ� JPanel

public class RegUser extends JPanel {

	public static JTextField UserName;
	public static JTextField UserID;
	public static JTextField UserRRN;
	public static String[] DeptNum = {"100", "101", "102", "103", "104"};
	public static String Perm = null;
	public static int Dept;
	public static String saveDeptNum;

	public RegUser() {
		setBackground(Color.WHITE);
		setLayout(null);
		
		JLabel lbTitle = new JLabel("����� ���");
		lbTitle.setBounds(14, 2, 96, 25);
		lbTitle.setFont(new Font("���� ���", Font.BOLD, 18));
		add(lbTitle);
		
		JLabel lbBG = new JLabel("");
		lbBG.setBounds(0, -20, 436, 59);
		lbBG.setIcon(new ImageIcon("./Images/Line.png"));
		add(lbBG);
		
		JLabel lbPer = new JLabel("����");
		lbPer.setBounds(14, 59, 37, 18);
		lbPer.setFont(new Font("���� ���", Font.BOLD, 15));
		add(lbPer);
		
		JComboBox listPerm = new JComboBox();
		listPerm.addItem("����");
		listPerm.addItem("�л�");
		listPerm.setSelectedItem(null);
		listPerm.setBounds(48, 57, 116, 25);
		add(listPerm);
		
		JLabel lbName = new JLabel("����");
		lbName.setFont(new Font("���� ���", Font.BOLD, 15));
		lbName.setBounds(14, 105, 42, 18);
		add(lbName);
		
		UserName = new JTextField();
		UserName.setBounds(48, 104, 116, 24);
		add(UserName);
		UserName.setColumns(10);
		
		JLabel lbDept = new JLabel("�а�");
		lbDept.setFont(new Font("���� ���", Font.BOLD, 15));
		lbDept.setBounds(188, 57, 37, 18);
		add(lbDept);
		
		JComboBox listDept = new JComboBox();
		listDept.addItem("�����а�");
		listDept.addItem("�����а�");
		listDept.addItem("���ڰ��а�");
		listDept.addItem("ȭ�а��а�");
		listDept.addItem("�װ����ְ��а�");
		listDept.setSelectedItem(null);
		listDept.setBounds(243, 57, 171, 25);
		add(listDept);
		
		JLabel lbID = new JLabel("���̵�");
		lbID.setFont(new Font("���� ���", Font.BOLD, 15));
		lbID.setBounds(188, 106, 62, 18);
		add(lbID);
		
		UserID = new JTextField();
		UserID.setBounds(243, 105, 116, 24);
		add(UserID);
		UserID.setColumns(10);

		JButton buttReg = new JButton("���");
		buttReg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// ��ư �̺�Ʈ Ȯ��
				Perm = listPerm.getSelectedItem().toString();
				Dept = listDept.getSelectedIndex();
				saveDeptNum = DeptNum[Dept];
				
				try {
					new STFDAO().setUser();
					JOptionPane.showMessageDialog(null, "����� �Ϸ�Ǿ����ϴ�.");
				} catch (Exception e) {
					System.out.println("[ERROR]"+e.getMessage());
					e.printStackTrace();
				}
			}
		});
		buttReg.setFont(new Font("���� ���", Font.BOLD, 15));
		buttReg.setBounds(343, 2, 71, 27);
		add(buttReg);
		
		JLabel lbRRN = new JLabel("�ֹε�Ϲ�ȣ");
		lbRRN.setFont(new Font("���� ���", Font.BOLD, 15));
		lbRRN.setBounds(14, 153, 96, 18);
		add(lbRRN);
		
		UserRRN = new JTextField();
		UserRRN.setBounds(124, 152, 290, 24);
		add(UserRRN);
		UserRRN.setColumns(10);
	}
}
