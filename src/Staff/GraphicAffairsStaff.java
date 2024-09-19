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

import Login.Graphic;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;

import javax.swing.DropMode;
import javax.swing.JPanel;
import javax.swing.JLayeredPane;

// 학사 담당자 권한이 있는 계정 사용자에게 보여지는 메인 창 구성

public class GraphicAffairsStaff extends JFrame {
    private JTextArea tfUserName;
    private JTextArea tfNo;
    private JButton btnLogout;
    private JButton btnSettings;

    private JButton btnRegUser;
    private JButton btnEditUser;

    public GraphicAffairsStaff() {
        setTitle("대학정보시스템(UIS)::직원");	// 화면 Title 지정
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
        tfNo.setFont(new Font("맑은 고딕", Font.BOLD, 12));
        tfNo.setForeground(Color.WHITE);
        tfNo.setBackground(new Color(1, 0, 140));
        tfNo.setEditable(false);
        getContentPane().add(tfNo);
        tfNo.setColumns(10);

        tfUserName = new JTextArea();
        tfUserName.setDropMode(DropMode.INSERT);
        tfUserName.setBounds(80, 144, 80, 24);
        tfUserName.setFont(new Font("맑은 고딕", Font.BOLD, 15));
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
                JOptionPane.showMessageDialog(null, "로그아웃 되었습니다.");
                dispose();	// 창 종료
                Graphic frame = new Graphic();
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

        btnSettings = new JButton("");
        btnSettings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Test
                JPanel info = new Staff.Info();
                info.setBounds(0, 0, 547, 465);
                layeredPane.add(info);
            }
        });
        btnSettings.setBorderPainted(false);	// 버튼 테두리 삭제
        btnSettings.setOpaque(false);	// 버튼 투명 설정
        btnSettings.setBounds(118, 173, 33, 33);
        getContentPane().add(btnSettings);

        Load();

        setVisible(true);
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
