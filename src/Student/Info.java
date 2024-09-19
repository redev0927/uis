package Student;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import Login.Password;

import javax.swing.JLabel;
import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

// 학생 사용자정보를 출력해주는 JPanel

public class Info extends JPanel {
    // 사용자 정보 화면에 출력되는 항목에 대한 TextArea 생성
    private JTextArea UserID;
    private JTextArea UserDEPT;
    private JTextArea UserNAME;
    private JTextArea UserRRN;

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

        JLabel LbID = new JLabel("학번/교수번호");
        LbID.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        LbID.setBounds(10, 71, 108, 18);
        add(LbID);

        UserID = new JTextArea();
        UserID.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        UserID.setBounds(119, 71, 116, 24);
        UserID.setEditable(false); // (setEditable)사용자 임의로 Textfield 내용 수정 불가
        UserID.setColumns(10);	// 학번에 대해 최대 입력 글자 수를 제한
        add(UserID);

        JLabel LbDEPT = new JLabel("학과");
        LbDEPT.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        LbDEPT.setBounds(10, 113, 44, 18);
        add(LbDEPT);

        UserDEPT = new JTextArea();
        UserDEPT.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        UserDEPT.setBounds(54, 113, 181, 24);
        UserDEPT.setEditable(false);
        UserDEPT.setColumns(10);
        add(UserDEPT);

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

        JLabel LbRRN = new JLabel("주민등록번호");
        LbRRN.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        LbRRN.setBounds(10, 161, 108, 18);
        add(LbRRN);

        UserRRN = new JTextArea();
        UserRRN.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        UserRRN.setBounds(119, 161, 289, 24);
        UserRRN.setEditable(false);
        UserRRN.setColumns(10);
        add(UserRRN);

        JButton btnChPw = new JButton("비밀번호 변경");
        btnChPw.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                Password frame = new Password();
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
        Dao dao = new Dao();
        ArrayList<GetterSetter> members = new ArrayList<GetterSetter>();

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
            System.out.println("[ERROR]"+e.getMessage()); // 예외 메시지 (console) 인쇄
            e.printStackTrace();
        } catch(Exception e) {
            System.out.println("[ERROR]"+e.getMessage());
            e.printStackTrace();
        }
    }
}
