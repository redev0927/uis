package Staff;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RegClass extends JPanel {
    public static JTextField tfExp;
    public static JTextField tfClassNumber;
    public static JTextField tfClassName;
    public static JTextField tfCount;
    public static String[] DeptNum = {"100", "101", "102", "103"};
    public static String Perm = null;
    public static JTextField tfProfessor;
    public static JTextField tfScore;
    /**
     * Create the panel.
     */
    public RegClass() {
        setBackground(Color.WHITE);
        setLayout(null);

        JLabel lbRegClass = new JLabel("강좌 등록");
        lbRegClass.setForeground(new Color(0, 0, 128));
        lbRegClass.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        lbRegClass.setBounds(36, 15, 82, 21);
        add(lbRegClass);

        JLabel lbClassNumber = new JLabel("강좌번호");
        lbClassNumber.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        lbClassNumber.setBounds(36, 51, 82, 21);
        add(lbClassNumber);

        JLabel lbClassName = new JLabel("강좌이름");
        lbClassName.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        lbClassName.setBounds(36, 87, 82, 21);
        add(lbClassName);

        JLabel lbProfessor = new JLabel("\uB2F4\uB2F9\uAD50\uC218 ID");
        lbProfessor.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        lbProfessor.setBounds(206, 48, 147, 21);
        add(lbProfessor);

        JLabel lbScore = new JLabel("학점수");
        lbScore.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        lbScore.setBounds(230, 118, 82, 21);
        add(lbScore);

        JLabel lbExp = new JLabel("설명");
        lbExp.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        lbExp.setBounds(36, 144, 82, 21);
        add(lbExp);

        tfExp = new JTextField();
        tfExp.setBounds(36, 168, 327, 82);
        add(tfExp);
        tfExp.setColumns(10);

        tfClassNumber = new JTextField();
        tfClassNumber.setBounds(114, 48, 78, 27);
        add(tfClassNumber);
        tfClassNumber.setColumns(10);

        tfClassName = new JTextField();
        tfClassName.setColumns(10);
        tfClassName.setBounds(112, 84, 251, 27);
        add(tfClassName);

        JButton btnRegClass = new JButton("등록");
        btnRegClass.setBackground(Color.WHITE);
        btnRegClass.setForeground(Color.WHITE);
        btnRegClass.setContentAreaFilled(false); // 1
        btnRegClass.setOpaque(true); // 2 //1,2를 선언해줘야 변경하기 버튼의 배경색
        btnRegClass.setBackground(new Color(0, 0, 139));
        btnRegClass.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                //Perm = cbScore.getSelectedItem().toString();
                try {
                    new Dao().setClass();//강좌등록으로 고쳐봄
                    JOptionPane.showMessageDialog(null, "등록이 완료되었습니다.");
                } catch (Exception e) {
                    System.out.println("[ERROR]"+e.getMessage());
                    e.printStackTrace();
                }
            }
        });
        btnRegClass.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        btnRegClass.setBounds(150, 256, 93, 29);
        add(btnRegClass);

        JLabel label = new JLabel("최대수강인원");
        label.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        label.setBounds(36, 118, 123, 21);
        add(label);

        tfCount = new JTextField();
        tfCount.setBounds(135, 115, 57, 27);
        add(tfCount);
        tfCount.setColumns(10);

        tfProfessor = new JTextField();
        tfProfessor.setBounds(291, 48, 72, 27);
        add(tfProfessor);
        tfProfessor.setColumns(10);

        tfScore = new JTextField();
        tfScore.setBounds(291, 115, 72, 27);
        add(tfScore);
        tfScore.setColumns(10);

    }
}
