package Professor;

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
import Professor.ProfDAO;
import Professor.ProfGS;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import javax.swing.DropMode;
import javax.swing.JPanel;
import javax.swing.JLayeredPane;

// Professor - WindowPROF.java
// 교수 권한이 있는 계정 사용자에게 보여지는 메인 창 구성

public class WindowPROF extends JFrame {
	private JTextArea tfUserName;
	private JTextArea tfNo;
	private JButton btnLogout;
	private JButton btnSettings;
	private JButton btnCheckStudent;
	private JButton btnAttendance;
	private JButton btnEditGrades;
	
	public WindowPROF() {
		setTitle("대학정보시스템(UIS)::교수");	// 화면 Title 지정
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
		tfNo.setBounds(100, 125, 37, 16);
		tfNo.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		tfNo.setForeground(Color.WHITE);
		tfNo.setBackground(new Color(1, 0, 140));
		tfNo.setEditable(false);
		getContentPane().add(tfNo);
		tfNo.setColumns(10);

		tfUserName = new JTextArea();
		tfUserName.setDropMode(DropMode.INSERT);
		tfUserName.setBounds(92, 144, 43, 24);
		tfUserName.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		tfUserName.setForeground(Color.WHITE);
		tfUserName.setBackground(new Color(1, 0, 140));
		tfUserName.setEditable(false);
		getContentPane().add(tfUserName);
		tfUserName.setColumns(10);
		
		btnCheckStudent = new JButton("");
		btnCheckStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				layeredPane.removeAll();
				JPanel info = new CheckStudent();
				info.setBounds(0, 0, 547, 465);
				layeredPane.add(info);
			}
		});
		btnCheckStudent.setIcon(new ImageIcon(".//Images//btnCheckStudent.jpg"));
		btnCheckStudent.setBounds(75, 220, 82, 27);
		btnCheckStudent.setBorderPainted(false);
		getContentPane().add(btnCheckStudent);
		
		btnAttendance = new JButton("");
		btnAttendance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				layeredPane.removeAll();
				JPanel info = new Attendance();
				info.setBounds(0, 0, 547, 465);
				layeredPane.add(info);
			}
		});
		btnAttendance.setIcon(new ImageIcon(".//Images//btnAttendance.jpg"));
		btnAttendance.setBounds(76, 260, 82, 27);
		btnAttendance.setBorderPainted(false);
		getContentPane().add(btnAttendance);
		
		btnEditGrades = new JButton("");
		btnEditGrades.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				layeredPane.removeAll();
				JPanel info = new EditGrades();
				info.setBounds(0, 0, 547, 465);
				layeredPane.add(info);
			}
		});
		btnEditGrades.setIcon(new ImageIcon(".//Images//btnEditGrades.jpg"));
		btnEditGrades.setBounds(77, 300, 82, 27);
		btnEditGrades.setBorderPainted(false);
		getContentPane().add(btnEditGrades);

		btnSettings = new JButton("");
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Test
				JPanel info = new Professor.Info();
				info.setBounds(0, 0, 547, 465);
				layeredPane.add(info);
			}
		});
		
				btnLogout = new JButton("");
				btnLogout.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						JOptionPane.showMessageDialog(null, "로그아웃 되었습니다.");
						dispose();	// 창 종료
						LoginGUI frame = new LoginGUI();
						frame.setLocationRelativeTo(null);
					}
				});
				
						JLabel lbBG = new JLabel("");
						lbBG.setBounds(0, 0, 754, 465);
						lbBG.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
						lbBG.setIcon(new ImageIcon(".//Images//WindowBG.png"));
						getContentPane().add(lbBG);
				btnLogout.setBorderPainted(false);	// 버튼 테두리 삭제
				btnLogout.setOpaque(false);	// 버튼 투명 설정
				btnLogout.setBounds(71, 173, 33, 33);
				getContentPane().add(btnLogout);
		btnSettings.setBorderPainted(false);	// 버튼 테두리 삭제
		btnSettings.setOpaque(false);	// 버튼 투명 설정
		btnSettings.setBounds(118, 173, 33, 33);
		getContentPane().add(btnSettings);

		Load();

		setVisible(true);
	}

	// 데이터베이스의 정보를 가져오기 위한 메소드(Load)
	public void Load() {
		ProfDAO dao = new ProfDAO();
		ArrayList<ProfGS> members = new ArrayList<ProfGS>();

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
			System.out.println("[ERROR]"+e.getMessage()); // 예외 메시지 (console) 인쇄
			e.printStackTrace();
		} catch(Exception e) {
			System.out.println("[ERROR]"+e.getMessage());
			e.printStackTrace();
		}
	}
}