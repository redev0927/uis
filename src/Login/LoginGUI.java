package Login;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Professor.WindowPROF;
import Staff.WindowSTFG;
import Staff.WindowSTFH;
import Student.WindowSTU;

// Login - LoginGUI.java
// ����ڿ��� �������� Login ȭ�鱸��

public class LoginGUI extends JFrame {
	private JPanel contentPane;
	public static String PassID;
	public static String PassPW;

	private static JTextField FieldID;
	private static JPasswordField FieldPW;

	public LoginGUI() {
		setTitle("���������ý���(UIS)::Login"); // ȭ�� Title ����
		setResizable(false); // ȭ�� ũ�� ���� �Ұ�
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ȭ�� ���� �� ���μ����� ����
		setBounds(100, 100, 510, 340);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setVisible(true);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 504, 305);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel LabelPW = new JLabel("Password");
		LabelPW.setBounds(160, 158, 76, 19);
		panel.add(LabelPW);
		LabelPW.setForeground(Color.DARK_GRAY);
		LabelPW.setFont(new Font("���� ���", Font.BOLD, 13));

		JLabel LabelTitle = new JLabel("���������ý���(UIS)");
		LabelTitle.setBounds(160, 66, 191, 32);
		panel.add(LabelTitle);
		LabelTitle.setForeground(new Color(25, 25, 112));
		LabelTitle.setFont(new Font("���� ���", Font.BOLD, 20));

		JLabel LabelID = new JLabel("ID");
		LabelID.setBounds(161, 127, 22, 23);
		panel.add(LabelID);
		LabelID.setForeground(Color.DARK_GRAY);
		LabelID.setFont(new Font("���� ���", Font.BOLD, 13));

		FieldID = new JTextField();

		FieldID.setBounds(221, 129, 130, 19);
		panel.add(FieldID);
		FieldID.setColumns(10);

		FieldPW = new JPasswordField();
		FieldPW.setBounds(221, 158, 130, 19);
		panel.add(FieldPW);
		FieldPW.setColumns(10);

		JButton BtnLogin = new JButton("�α���");
		BtnLogin.setBounds(160, 192, 191, 32);
		panel.add(BtnLogin);
		BtnLogin.setBackground(new Color(25, 25, 112));
		BtnLogin.setForeground(new Color(255, 255, 255));
		BtnLogin.setContentAreaFilled(false); // 1
		BtnLogin.setOpaque(true); // 2 //1,2�� ��������� �����ϱ� ��ư�� ����
		BtnLogin.setBackground(new Color(0, 0, 139));

		BtnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new DAO(); // �����͸� �ҷ���
				Check(); // ���� ��������� Ȯ��
			}
		});

		BtnLogin.setFont(new Font("���� ���", Font.BOLD, 14));

		JLabel label = new JLabel("");
		label.setBounds(-3, 0, 510, 305);
		label.setIcon(new ImageIcon("./Images/Login_sc.jpg"));
		panel.add(label);
	}

	// ��ϵ� �������� Ȯ���ϴ� ����� �����ϴ� �޼ҵ�
	public void Check() {
		HashMap<String, String> map = new HashMap<String, String>();
		DAO dao = new DAO();
		ArrayList<LoginGS> members = new ArrayList<LoginGS>();

		try {
			members = dao.getMembers();

			for (int i = 0; i < members.size(); i++)
				// DB���� �ҷ��� �ڷῡ�� id, pw ���� ����
				map.put(members.get(i).getId(), members.get(i).getPw());

			while (true) {
				String id = LoginGUI.FieldID.getText();

				@SuppressWarnings("deprecation")
				String password = LoginGUI.FieldPW.getText();

				if (!map.containsKey(id)) {
					JOptionPane.showMessageDialog(null, "�Է��Ͻ� id�� �������� �ʽ��ϴ�. �ٽ� �Է����ּ���.", "�α��� ����",
							JOptionPane.ERROR_MESSAGE);
					break;
				}

				else if (!(map.get(id)).equals(password)) {
					JOptionPane.showMessageDialog(null, "��й�ȣ�� ��ġ���� �ʽ��ϴ�. �ٽ� �Է����ּ���.", "�α��� ����",
							JOptionPane.ERROR_MESSAGE);
					break;
				}

				else {
					setVisible(false);

					PassID = id;
					PassPW = password;

					// ����� ���޺��� �ش�Ǵ� â���� �̵�
					if (id.substring(0, 1).equals("S"))
						new WindowSTU();
					else if (id.substring(0, 1).equals("P"))
						new WindowPROF();
					else if (id.substring(0, 1).equals("H"))
						new WindowSTFH();
					else if (id.substring(0, 1).equals("G"))
						new WindowSTFG();

					break;
				}

			}
			// ���� ó��
		} catch (SQLException e) {
			System.out.println("[ERROR]" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("[ERROR]" + e.getMessage());
			e.printStackTrace();
		}
	}
}
