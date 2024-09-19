package Login;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class ChangePW extends JFrame {

	private JPanel contentPane;

	private static JPasswordField passNow;
	private static JPasswordField passNew;
	private static JPasswordField passCheck;
	public static final String pattern = "^[A-Za-z[0-9]]{10,20}$"; // ����, ����

	/**
	 * Create the frame.
	 */
	public ChangePW() {
		setTitle("��й�ȣ ����");	// ȭ�� Title ����
		setResizable(false);	// ȭ�� ũ�� ���� �Ұ�
		setBounds(100, 100, 435, 285);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
				JLabel label = new JLabel("* 7�ڸ��� �����ڿ� ���ڸ� �Է�");
				label.setFont(new Font("���� ��� Semilight", Font.PLAIN, 9));
				label.setForeground(new Color(0, 0, 205));
				label.setBounds(180, 133, 183, 21);
				getContentPane().add(label);

		JPanel pnChPw = new JPanel();
		pnChPw.setBounds(0, 0, 432, 253);
		contentPane.add(pnChPw);

		pnChPw.setBackground(Color.WHITE);
		pnChPw.setLayout(null);

		// �α��� ��й�ȣ ���� ����
		JLabel ChangePW = new JLabel("��й�ȣ ����");
		ChangePW.setFont(new Font("���� ���", Font.BOLD, 22));
		ChangePW.setForeground(new Color(0, 0, 128));
		ChangePW.setBounds(145, 15, 145, 38);
		pnChPw.add(ChangePW);

		// ���� ��й�ȣ
		JLabel PWNow = new JLabel("���� ��й�ȣ");
		PWNow.setFont(new Font("���� ���", Font.BOLD, 13));
		PWNow.setForeground(Color.DARK_GRAY);
		PWNow.setBounds(92, 84, 119, 21);
		pnChPw.add(PWNow);

		// �ű� ��й�ȣ
		JLabel PWNew = new JLabel("�ű� ��й�ȣ");
		PWNew.setForeground(Color.DARK_GRAY);
		PWNew.setFont(new Font("���� ���", Font.BOLD, 13));
		PWNew.setBounds(92, 116, 119, 21);
		pnChPw.add(PWNew);

		// ��й�ȣ Ȯ��
		JLabel PWCheck = new JLabel("��й�ȣ Ȯ��");
		PWCheck.setFont(new Font("���� ���", Font.BOLD, 13));
		PWCheck.setForeground(Color.DARK_GRAY);
		PWCheck.setBounds(92, 152, 119, 21);
		pnChPw.add(PWCheck);

		// Ȯ�ι�ư
		JButton btnOK = new JButton("�����ϱ�");
		btnOK.setBounds(174, 188, 82, 21);
		pnChPw.add(btnOK);
		btnOK.setContentAreaFilled(false); // 1
		btnOK.setOpaque(true); // 2 //1,2�� ��������� �����ϱ� ��ư�� ����
		btnOK.setBackground(new Color(0, 0, 139));
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CheckPW();
			}
		});
		btnOK.setFont(new Font("���� ���", Font.BOLD, 12));
		btnOK.setForeground(Color.WHITE);

		// ���� ��й�ȣ �Է�ĭ
		passNow = new JPasswordField();
		passNow.setBounds(180, 84, 145, 18);
		pnChPw.add(passNow);

		// ���ο� ��й�ȣ �Է�ĭ
		passNew = new JPasswordField();
		passNew.setBounds(180, 118, 145, 18);
		pnChPw.add(passNew);

		// ��й�ȣ Ȯ�� �Է�ĭ
		passCheck = new JPasswordField();
		passCheck.setBounds(180, 153, 145, 18);
		pnChPw.add(passCheck);
	}

	public void CheckPW(){
		String passBefore = passNow.getText();
		String passAfter = passNew.getText();
		String passChk = passCheck.getText();

		while(true) {
			if(!LoginGUI.PassPW.equals(passBefore)) {
				JOptionPane.showMessageDialog(null, "���� ��й�ȣ�� �ٽ� �Է����ּ���.", "����", JOptionPane.ERROR_MESSAGE);
				break;
			}

			else {
				// �ű� ��й�ȣ�� ���� ��й�ȣ�� ������ ����
				if (passBefore.equals(passAfter)) {
					// ������ �޽���â
					JOptionPane.showMessageDialog(null, "�ű� ��й�ȣ�� ���� ��й�ȣ�� ��ġ�մϴ�.", "����", JOptionPane.ERROR_MESSAGE);
					break; // JOptionPane
				}

				else {
					// �ű� ��й�ȣ�� ��й�ȣ Ȯ���� ������
					if (!passAfter.equals(passChk)) {
						JOptionPane.showMessageDialog(null, "�ű� ��й�ȣ�� ��й�ȣ Ȯ���� ��ġ���� �ʽ��ϴ�", "����",
								JOptionPane.ERROR_MESSAGE);
						break;
					}

					else {
						// 7�ڸ� ������ ���� ��������
						boolean mch = Pattern.matches("(^[A-Za-z0-9]{7}$)", passAfter);
						if (mch == false) {
							JOptionPane.showMessageDialog(null, "�ű� ��й�ȣ�� 7�ڸ��� ������, ���ڸ� �Է� �����մϴ�.", "����",
									JOptionPane.ERROR_MESSAGE);
							break;
						}
						else {
							try {
								new DAO().PWUpdate(passAfter);
								JOptionPane.showMessageDialog(null, "��й�ȣ ������ �Ϸ�Ǿ����ϴ�.");
								dispose();	// â ����
								break;
							} catch (Exception e) {
								System.out.println("[ERROR]" + e.getMessage());
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}

}
