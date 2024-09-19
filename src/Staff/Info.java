package Staff;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import Login.ChangePW;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.Color;

// Staff - Info.java
// ����� ����������� ������ִ� JPanel

public class Info extends JPanel {
	// ����� ���� ȭ�鿡 ��µǴ� �׸� ���� TextArea ����
	private JTextArea UserID;
	private JTextArea UserNAME;
	
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
		
		JLabel LbID = new JLabel("������ȣ");
		LbID.setFont(new Font("���� ���", Font.BOLD, 16));
		LbID.setBounds(10, 71, 108, 18);
		add(LbID);
		
		UserID = new JTextArea();
		UserID.setFont(new Font("���� ���", Font.PLAIN, 13));
		UserID.setBounds(119, 71, 116, 24);
		UserID.setEditable(false); // (setEditable)����� ���Ƿ� Textfield ���� ���� �Ұ�
		UserID.setColumns(10);	// �й��� ���� �ִ� �Է� ���� ���� ����
		add(UserID);
		
		JLabel LbName = new JLabel("����");
		LbName.setFont(new Font("���� ���", Font.BOLD, 16));
		LbName.setBounds(249, 71, 44, 18);
		add(LbName);
		
		UserNAME = new JTextArea();
		UserNAME.setFont(new Font("���� ���", Font.PLAIN, 13));
		UserNAME.setBounds(292, 71, 116, 24);
		UserNAME.setEditable(false);
		UserNAME.setColumns(10);
		add(UserNAME);
		
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
	
	// �����ͺ��̽��� ������ �������� ���� �޼ҵ�(Load)
	public void Load() {
		STFDAO dao = new STFDAO();
		ArrayList<STFGS> members = new ArrayList<STFGS>();
		
		try {
			members = dao.getMembers();

		for(int i=0; i<members.size();i++) {
			members.get(i).getID();
			members.get(i).getName();
		}
		
			String MyID = members.get(0).getID();
			String MyName = members.get(0).getName();
			
			UserID.append(MyID);
			UserNAME.append(MyName);
			
			
		} catch(SQLException e) {
			System.out.println("[ERROR]"+e.getMessage()); // ���� �޽��� (console) �μ�
			e.printStackTrace();
		} catch(Exception e) {
			System.out.println("[ERROR]"+e.getMessage());
			e.printStackTrace();
		}
	}
}
