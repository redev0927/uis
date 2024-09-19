package Professor;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import Login.ChangePW;
import Professor.ProfDAO;
import Professor.ProfGS;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.Color;

// Student - Info.java
// �л� ����������� ������ִ� JPanel

public class Info extends JPanel {
	// ����� ���� ȭ�鿡 ��µǴ� �׸� ���� TextArea ����
	private JTextArea UserID;
	private JTextArea UserDEPT;
	private JTextArea UserNAME;
	private JTextArea UserRRN;
	
	// ����� ���� ȭ�鿡 ǥ�õǴ� Title �� �̹��� ��
	private JLabel LbTitle;
	private JLabel lbBG;

	public Info() {
		setBackground(Color.WHITE);
		setLayout(null);
		
		lbBG = new JLabel("");
		lbBG.setIcon(new ImageIcon("./Images/Line.png"));	// (������)����θ� ���� �̹��� �ε�
		lbBG.setBounds(0, 0, 444, 59);
		add(lbBG);
		
		LbTitle = new JLabel("����� ����");
		LbTitle.setFont(new Font("���� ���", Font.BOLD, 20));
		LbTitle.setBounds(14, 23, 146, 24);
		add(LbTitle);
		
		JLabel LbID = new JLabel("�й�/������ȣ");
		LbID.setFont(new Font("���� ���", Font.BOLD, 16));
		LbID.setBounds(10, 71, 108, 18);
		add(LbID);
		
		UserID = new JTextArea();
		UserID.setBounds(119, 71, 116, 24);
		UserID.setEditable(false); // (setEditable)����� ���Ƿ�  ��µ� ���� ���� �Ұ�
		UserID.setColumns(10);	// �й��� ���� �ִ� �Է� ���� ���� ����
		add(UserID);
		
		JLabel LbDEPT = new JLabel("�а�");
		LbDEPT.setFont(new Font("���� ���", Font.BOLD, 16));
		LbDEPT.setBounds(10, 113, 44, 18);
		add(LbDEPT);
		
		UserDEPT = new JTextArea();
		UserDEPT.setBounds(54, 113, 181, 24);
		UserDEPT.setEditable(false);
		UserDEPT.setColumns(10);
		add(UserDEPT);
		
		JLabel LbName = new JLabel("����");
		LbName.setFont(new Font("���� ���", Font.BOLD, 16));
		LbName.setBounds(249, 71, 44, 18);
		add(LbName);
		
		UserNAME = new JTextArea();
		UserNAME.setBounds(292, 71, 116, 24);
		UserNAME.setEditable(false);
		UserNAME.setColumns(10);
		add(UserNAME);
		
		JLabel LbRRN = new JLabel("�ֹε�Ϲ�ȣ");
		LbRRN.setFont(new Font("���� ���", Font.BOLD, 16));
		LbRRN.setBounds(10, 161, 108, 18);
		add(LbRRN);
		
		UserRRN = new JTextArea();
		UserRRN.setBounds(119, 161, 289, 24);
		UserRRN.setEditable(false);
		UserRRN.setColumns(10);
		add(UserRRN);
		
		JButton btnChPw = new JButton("��й�ȣ ����");
		btnChPw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ChangePW frame = new ChangePW();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
		btnChPw.setFont(new Font("���� ���", Font.BOLD, 15));
		btnChPw.setBounds(315, 22, 129, 27);
		add(btnChPw);
		
		Load();	// ������ ���� �� ���� �ҷ����⸦ �ڵ� ����
		
		setVisible(true); // (setVisible)�г� ���
	}
	
	// ����� ������ �������� ��� �޼ҵ�
	public void Load() {
		ProfDAO dao = new ProfDAO();
		ArrayList<ProfGS> members = new ArrayList<ProfGS>();
		
		try {
			members = dao.getMembers();

		for(int i=0; i<members.size();i++) {
			members.get(i).getID();
			members.get(i).getName();
			members.get(i).getDept();
			members.get(i).getRRN();
		}
		
			String MyID = members.get(0).getID();
			String MyName = members.get(0).getName();
			String MyDept = members.get(0).getDept();
			String MyRRNFront = members.get(0).getRRN().substring(0,6);
			String MyRRNBack = members.get(0).getRRN().substring(6,13);
			
			UserID.append(MyID);
			UserNAME.append(MyName);
			UserDEPT.append(MyDept);
			UserRRN.append(MyRRNFront + "-" + MyRRNBack);
			
			
		} catch(SQLException e) {
			System.out.println("[ERROR]"+e.getMessage()); // ���� �޽��� (console) �μ�
			e.printStackTrace();
		} catch(Exception e) {
			System.out.println("[ERROR]"+e.getMessage());
			e.printStackTrace();
		}
	}
}
