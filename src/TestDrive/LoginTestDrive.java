package TestDrive;

import java.awt.EventQueue;
import Login.LoginGUI;

// LoginTestDrive
// Loginȭ�� ���� �۾��� ���������� Ȯ��

public class LoginTestDrive {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					System.out.println("[ERROR]�α��� ȭ�� ���� ����");
				}
			}
		});
	}

}
