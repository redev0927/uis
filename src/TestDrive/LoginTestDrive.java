package TestDrive;

import java.awt.EventQueue;
import Login.LoginGUI;

// LoginTestDrive
// Login화면 이후 작업이 정상적인지 확인

public class LoginTestDrive {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					System.out.println("[ERROR]로그인 화면 생성 실패");
				}
			}
		});
	}

}
