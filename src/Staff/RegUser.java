package Staff;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

//사용자 정보를 등록하는 JPanel

public class RegUser extends JPanel {
    public static JTextField UserName;
    public static JTextField UserID;
    public static JTextField UserRRN;
    public static String[] DeptNum = {"100", "101", "102", "103", "104", "105"};
    public static String Perm = null;
    public static int Dept;
    public static String saveDeptNum;

    public RegUser() {
        setBackground(Color.WHITE);
        setLayout(null);

        JLabel lbTitle = new JLabel("사용자 등록");
        lbTitle.setBounds(14, 2, 96, 25);
        lbTitle.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        add(lbTitle);

        JLabel lbBG = new JLabel("");
        lbBG.setBounds(0, -20, 436, 59);
        lbBG.setIcon(new ImageIcon("./Images/Line.png"));
        add(lbBG);

        JLabel lbPer = new JLabel("직급");
        lbPer.setBounds(14, 59, 37, 18);
        lbPer.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        add(lbPer);

        JComboBox listPerm = new JComboBox();
        listPerm.addItem("교수");
        listPerm.addItem("학생");
        listPerm.setSelectedItem(null);
        listPerm.setBounds(48, 57, 116, 25);
        add(listPerm);

        JLabel lbName = new JLabel("성명");
        lbName.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        lbName.setBounds(14, 105, 42, 18);
        add(lbName);

        UserName = new JTextField();
        UserName.setBounds(48, 104, 116, 24);
        add(UserName);
        UserName.setColumns(10);

        JLabel lbDept = new JLabel("학과");
        lbDept.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        lbDept.setBounds(188, 57, 37, 18);
        add(lbDept);

        JComboBox listDept = new JComboBox();
        listDept.addItem("기계공학과");
        listDept.addItem("전산학과");
        listDept.addItem("전자공학과");
        listDept.addItem("화학공학과");
        listDept.addItem("항공우주공학과");
        listDept.setSelectedItem(null);
        listDept.setBounds(243, 57, 171, 25);
        add(listDept);

        JLabel lbID = new JLabel("아이디");
        lbID.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        lbID.setBounds(188, 106, 62, 18);
        add(lbID);

        UserID = new JTextField();
        UserID.setBounds(243, 105, 116, 24);
        add(UserID);
        UserID.setColumns(10);

        JButton buttReg = new JButton("등록");
        buttReg.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                // 버튼 이벤트 확인
                Perm = listPerm.getSelectedItem().toString();
                Dept = listDept.getSelectedIndex();
                saveDeptNum = DeptNum[Dept];

                System.out.println(Perm);
                System.out.println(Dept);
                System.out.println(saveDeptNum);

                try {
                    new Dao().setUser();
                    JOptionPane.showMessageDialog(null, "등록이 완료되었습니다.");
                } catch (Exception e) {
                    System.out.println("[ERROR]"+e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        buttReg.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        buttReg.setBounds(343, 2, 71, 27);
        add(buttReg);

        JLabel lbRRN = new JLabel("주민등록번호");
        lbRRN.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        lbRRN.setBounds(14, 153, 96, 18);
        add(lbRRN);

        UserRRN = new JTextField();
        UserRRN.setBounds(124, 152, 290, 24);
        add(UserRRN);
        UserRRN.setColumns(10);
    }
}
