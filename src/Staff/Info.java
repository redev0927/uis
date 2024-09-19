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
// 담당자 사용자정보를 출력해주는 JPanel

public class Info extends JPanel {
	// 사용자 정보 화면에 출력되는 항목에 대한 TextArea 생성
	private JTextArea UserID;
	private JTextArea UserNAME;
	
	// 사용자 정보 화면에 표시되는 Title 및 이미지 라벨
	private JLabel LbTitle;
	private JLabel lbBG;

	public Info() {
		setBackground(Color.WHITE);
		setLayout(null);
		
		lbBG = new JLabel("");
		lbBG.setIcon(new ImageIcon("./Images/Line.png"));	// (안정성)상대경로를 통한 이미지 로딩
		lbBG.setBounds(0, 0, 444, 59);
		add(lbBG);
		
		LbTitle = new JLabel("사용자 정보");
		LbTitle.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		LbTitle.setBounds(14, 23, 146, 24);
		add(LbTitle);
		
		JLabel LbID = new JLabel("직원번호");
		LbID.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		LbID.setBounds(10, 71, 108, 18);
		add(LbID);
		
		UserID = new JTextArea();
		UserID.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		UserID.setBounds(119, 71, 116, 24);
		UserID.setEditable(false); // (setEditable)사용자 임의로 Textfield 내용 수정 불가
		UserID.setColumns(10);	// 학번에 대해 최대 입력 글자 수를 제한
		add(UserID);
		
		JLabel LbName = new JLabel("성명");
		LbName.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		LbName.setBounds(249, 71, 44, 18);
		add(LbName);
		
		UserNAME = new JTextArea();
		UserNAME.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		UserNAME.setBounds(292, 71, 116, 24);
		UserNAME.setEditable(false);
		UserNAME.setColumns(10);
		add(UserNAME);
		
		JButton btnChPw = new JButton("비밀번호 변경");
		btnChPw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ChangePW frame = new ChangePW();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
		btnChPw.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		btnChPw.setBounds(315, 22, 129, 27);
		add(btnChPw);
		
		Load();	// 생성자 실행 시 정보 불러오기를 자동 실행
		
		setVisible(true); // (setVisible)패널 출력
	}
	
	// 데이터베이스의 정보를 가져오기 위한 메소드(Load)
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
			System.out.println("[ERROR]"+e.getMessage()); // 예외 메시지 (console) 인쇄
			e.printStackTrace();
		} catch(Exception e) {
			System.out.println("[ERROR]"+e.getMessage());
			e.printStackTrace();
		}
	}
}
