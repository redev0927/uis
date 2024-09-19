package Staff;

import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

import Login.LoginGUI;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;

import javax.swing.DropMode;
import javax.swing.JPanel;
import javax.swing.JLayeredPane;

// Professor - WindowSTFH.java
// �л� ����� ������ �ִ� ���� ����ڿ��� �������� ���� â ����

public class WindowSTFH extends JFrame {
	private JTextArea tfUserName;
	private JTextArea tfNo;
	private JButton btnLogout;
	private JButton btnSettings;
	
	private JButton btnRegUser;
	private JButton btnEditUser;
	
	public WindowSTFH() {
		setTitle("���������ý���(UIS)::����");	// ȭ�� Title ����
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 760, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(242, 0, 512, 465);
		getContentPane().add(layeredPane);

		tfNo = new JTextArea();
		tfNo.setDropMode(DropMode.INSERT);
		tfNo.setBounds(102, 125, 39, 16);
		tfNo.setFont(new Font("���� ���", Font.BOLD, 12));
		tfNo.setForeground(Color.WHITE);
		tfNo.setBackground(new Color(1, 0, 140));
		tfNo.setEditable(false);
		getContentPane().add(tfNo);
		tfNo.setColumns(10);

		tfUserName = new JTextArea();
		tfUserName.setDropMode(DropMode.INSERT);
		tfUserName.setBounds(80, 144, 80, 24);
		tfUserName.setFont(new Font("���� ���", Font.BOLD, 15));
		tfUserName.setForeground(Color.WHITE);
		tfUserName.setBackground(new Color(1, 0, 140));
		tfUserName.setEditable(false);
		getContentPane().add(tfUserName);
		tfUserName.setColumns(10);
		
		btnRegUser = new JButton("");
		btnRegUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				layeredPane.removeAll();
				JPanel info = new RegUser();
				info.setBounds(0, 0, 547, 465);
				layeredPane.add(info);
			}
		});
		btnRegUser.setIcon(new ImageIcon(".//Images//btnRegUser.jpg"));
		btnRegUser.setBounds(74, 220, 82, 27);
		btnRegUser.setBorderPainted(false);
		getContentPane().add(btnRegUser);

		btnEditUser = new JButton("");
		btnEditUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				layeredPane.removeAll();
				JPanel info = new SearchUser();
				info.setBounds(0, 0, 547, 465);
				layeredPane.add(info);
			}
		});
		btnEditUser.setIcon(new ImageIcon(".//Images//btnEditUser.jpg"));
		btnEditUser.setBounds(74, 260, 84, 27);
		btnEditUser.setBorderPainted(false);
		getContentPane().add(btnEditUser);

		btnLogout = new JButton("");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "�α׾ƿ� �Ǿ����ϴ�.");
				dispose();	// â ����
				LoginGUI frame = new LoginGUI();
				frame.setLocationRelativeTo(null);
			}
		});

		JLabel lbBG = new JLabel("");
		lbBG.setBounds(0, 0, 754, 465);
		lbBG.setFont(new Font("���� ���", Font.PLAIN, 15));
		lbBG.setIcon(new ImageIcon(".//Images//WindowBG.png"));
		getContentPane().add(lbBG);
		btnLogout.setBorderPainted(false);	// ��ư �׵θ� ����
		btnLogout.setOpaque(false);	// ��ư ���� ����
		btnLogout.setBounds(71, 173, 33, 33);
		getContentPane().add(btnLogout);

		btnSettings = new JButton("");
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Test
				JPanel info = new Staff.Info();
				info.setBounds(0, 0, 547, 465);
				layeredPane.add(info);
			}
		});
		btnSettings.setBorderPainted(false);	// ��ư �׵θ� ����
		btnSettings.setOpaque(false);	// ��ư ���� ����
		btnSettings.setBounds(118, 173, 33, 33);
		getContentPane().add(btnSettings);

		Load();

		setVisible(true);
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

			tfNo.append(MyID);
			tfUserName.append(MyName);	
		} catch(SQLException e) {
			System.out.println("[ERROR]"+e.getMessage()); // ���� �޽��� (console) �μ�
			e.printStackTrace();
		} catch(Exception e) {
			System.out.println("[ERROR]"+e.getMessage());
			e.printStackTrace();
		}
	}
}