package Prof;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class ChkStudent extends JPanel {
    private JTable tbCheckStudent;
    private String[] Header = {"아이디", "이름", "소속학과"};
    private String [] Select = {};
    private DefaultTableModel model = new DefaultTableModel(null, Header){
        public boolean isCellEditable(int rowIndex, int mColIndex) {
            return false;
        }
    };

    public static String Lect = null;
    public static JComboBox cbLect;

    /**
     * Create the panel.
     */
    public ChkStudent() {
        setBackground(Color.WHITE);
        setLayout(null);

        JLabel lbTitle = new JLabel("학생 명단 조회");
        lbTitle.setForeground(new Color(0, 0, 128));
        lbTitle.setBounds(17, 15, 131, 21);
        lbTitle.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        add(lbTitle);

        JLabel lbLectName = new JLabel("강의 선택");
        lbLectName.setForeground(Color.DARK_GRAY);
        lbLectName.setBounds(17, 54, 82, 21);
        lbLectName.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        add(lbLectName);


        try {
            Select=new Dao().IntoCombo();
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        cbLect = new JComboBox(Select);
        cbLect.setBounds(103, 51, 243, 27);
        add(cbLect);

        JButton btnRun = new JButton("조회");
        btnRun.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Load(); //정보가져오기
            }
        });
        btnRun.setBackground(new Color(0, 0, 128));
        btnRun.setForeground(Color.WHITE);
        btnRun.setContentAreaFilled(false); // 1
        btnRun.setOpaque(true); // 2 //1,2를 선언해줘야 변경하기 버튼의 배경색
        btnRun.setBackground(new Color(0, 0, 139));

        btnRun.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        btnRun.setBounds(353, 50, 65, 29);
        add(btnRun);

        tbCheckStudent = new JTable(model);
        tbCheckStudent.setBounds(17, 89, 401, 175);
        tbCheckStudent.getTableHeader().setReorderingAllowed(false);
        tbCheckStudent.getTableHeader().setResizingAllowed(false);
        JScrollPane scroll = new JScrollPane(tbCheckStudent);
        scroll.setLocation(17, 91);
        scroll.setSize(401, 194);
        scroll.setPreferredSize(new Dimension(470, 250));
        add(scroll);


    }

    // 데이터베이스의 정보를 가져오기 위한 메소드(Load)
    public void Load() {
        Dao dao = new Dao();
        ArrayList<GetterSetter> members = new ArrayList<GetterSetter>();
        Object record[] = new Object[4];

        model.setNumRows(0);	// 검색 시마다 기존 테이블에 출력된 내용을 삭제

        try {
            members = dao.resultChkStu();

            for(int i=0; i<members.size();i++) {
                GetterSetter data = members.get(i);
                record[0] = data.getID();
                record[1] = data.getName();
                record[2] = data.getDept();
                model.addRow(record);
            }

        } catch(SQLException e) {
            System.out.println("[ERROR]"+e.getMessage()); // 예외 메시지 (console) 인쇄
            e.printStackTrace();
        } catch(Exception e) {
            System.out.println("[ERROR]"+e.getMessage());
            e.printStackTrace();
        }
    }
}
