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

public class Password extends JFrame {

    private JPanel contentPane;

    private static JPasswordField passNow;
    private static JPasswordField passNew;
    private static JPasswordField passCheck;
    public static final String pattern = "^[A-Za-z[0-9]]{10,20}$"; // 영문, 숫자

    /**
     * Create the frame.
     */
    public Password() {
        setTitle("비밀번호 변경");	// 화면 Title 지정
        setResizable(false);	// 화면 크기 조정 불가
        setBounds(100, 100, 435, 285);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel label = new JLabel("* 7자리의 영문자와 숫자만 입력");
        label.setFont(new Font("맑은 고딕 Semilight", Font.PLAIN, 9));
        label.setForeground(new Color(0, 0, 205));
        label.setBounds(180, 133, 183, 21);
        getContentPane().add(label);

        JPanel pnChPw = new JPanel();
        pnChPw.setBounds(0, 0, 432, 253);
        contentPane.add(pnChPw);

        pnChPw.setBackground(Color.WHITE);
        pnChPw.setLayout(null);

        // 로그인 비밀번호 변경 제목
        JLabel ChangePW = new JLabel("비밀번호 변경");
        ChangePW.setFont(new Font("맑은 고딕", Font.BOLD, 22));
        ChangePW.setForeground(new Color(0, 0, 128));
        ChangePW.setBounds(145, 15, 145, 38);
        pnChPw.add(ChangePW);

        // 기존 비밀번호
        JLabel PWNow = new JLabel("기존 비밀번호");
        PWNow.setFont(new Font("한컴 고딕", Font.BOLD, 13));
        PWNow.setForeground(Color.DARK_GRAY);
        PWNow.setBounds(92, 84, 119, 21);
        pnChPw.add(PWNow);

        // 신규 비밀번호
        JLabel PWNew = new JLabel("신규 비밀번호");
        PWNew.setForeground(Color.DARK_GRAY);
        PWNew.setFont(new Font("한컴 고딕", Font.BOLD, 13));
        PWNew.setBounds(92, 116, 119, 21);
        pnChPw.add(PWNew);

        // 비밀번호 확인
        JLabel PWCheck = new JLabel("비밀번호 확인");
        PWCheck.setFont(new Font("한컴 고딕", Font.BOLD, 13));
        PWCheck.setForeground(Color.DARK_GRAY);
        PWCheck.setBounds(92, 152, 119, 21);
        pnChPw.add(PWCheck);

        // 확인버튼
        JButton btnOK = new JButton("변경하기");
        btnOK.setBounds(174, 188, 82, 21);
        pnChPw.add(btnOK);
        btnOK.setContentAreaFilled(false); // 1
        btnOK.setOpaque(true); // 2 //1,2를 선언해줘야 변경하기 버튼의 배경색
        btnOK.setBackground(new Color(0, 0, 139));
        btnOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                CheckPW();
            }
        });
        btnOK.setFont(new Font("한컴 고딕", Font.BOLD, 12));
        btnOK.setForeground(Color.WHITE);

        // 기존 비밀번호 입력칸
        passNow = new JPasswordField();
        passNow.setBounds(180, 84, 145, 18);
        pnChPw.add(passNow);

        // 새로운 비밀번호 입력칸
        passNew = new JPasswordField();
        passNew.setBounds(180, 118, 145, 18);
        pnChPw.add(passNew);

        // 비밀번호 확인 입력칸
        passCheck = new JPasswordField();
        passCheck.setBounds(180, 153, 145, 18);
        pnChPw.add(passCheck);
    }

    public void CheckPW(){
        String passBefore = passNow.getText();
        String passAfter = passNew.getText();
        String passChk = passCheck.getText();

        while(true) {
            if(!Graphic.PassPW.equals(passBefore)) {
                JOptionPane.showMessageDialog(null, "기존 비밀번호를 다시 입력해주세요.", "오류", JOptionPane.ERROR_MESSAGE);
                break;
            }

            else {
                // 신규 비밀번호와 기존 비밀번호가 같으면 오류
                if (passBefore.equals(passAfter)) {
                    // 같으면 메시지창
                    JOptionPane.showMessageDialog(null, "신규 비밀번호와 기존 비밀번호가 일치합니다.", "오류", JOptionPane.ERROR_MESSAGE);
                    break; // JOptionPane
                }

                else {
                    // 신규 비밀번호와 비밀번호 확인이 같은지
                    if (!passAfter.equals(passChk)) {
                        JOptionPane.showMessageDialog(null, "신규 비밀번호와 비밀번호 확인이 일치하지 않습니다", "오류",
                                JOptionPane.ERROR_MESSAGE);
                        break;
                    }

                    else {
                        // 7자리 영문자 숫자 조합인지
                        boolean mch = Pattern.matches("(^[A-Za-z0-9]{7}$)", passAfter);
                        if (mch == false) {
                            JOptionPane.showMessageDialog(null, "신규 비밀번호는 7자리의 영문자, 숫자만 입력 가능합니다.", "오류",
                                    JOptionPane.ERROR_MESSAGE);
                            break;
                        }
                        else {
                            try {
                                new DAO().PWUpdate(passAfter);
                                JOptionPane.showMessageDialog(null, "비밀번호 변경이 완료되었습니다.");
                                dispose();	// 창 종료
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
