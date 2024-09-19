package Prof;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class Grade extends JPanel {
    public static JTable tbEditGrades;
    private String[] Header = { "학번", "이름", "등급" };
    private String [] Select = {};
    private DefaultTableModel model = new DefaultTableModel(null, Header){ //학점빼고 수정불가
        public boolean isCellEditable(int rowIndex, int mColIndex) {
            int i=0;
            boolean state;

            if(mColIndex==2+(3*i)) {
                state = true;
                i++;
            }

            else {
                state = false;
                i++;
            }

            return state;
        }
    };


    public static String Lect = null;
    public static String ID = null;
    public static String Name = null;
    public static String Score = null;

    public static JComboBox cbLect;

    /**
     * Create the panel.
     */
    public Grade() {
        setBackground(Color.WHITE);
        setLayout(null);

        JLabel lbTitle = new JLabel("성적 입력");
        lbTitle.setBounds(17, 15, 121, 21);
        lbTitle.setForeground(new Color(0, 0, 128));
        lbTitle.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        add(lbTitle);

        JLabel lbLectName = new JLabel("진행중인 강의");
        lbLectName.setBounds(17, 55, 111, 18);
        lbLectName.setForeground(Color.DARK_GRAY);
        lbLectName.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        add(lbLectName);


        try {
            Select=new Dao().IntoCombo();
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        cbLect = new JComboBox(Select);
        cbLect.setBounds(119, 52, 174, 24);

        add(cbLect);

        JButton btnRun = new JButton("조회");
        btnRun.setBounds(298, 53, 65, 23);
        btnRun.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Load(); // 정보가져오기
            }
        });
        btnRun.setForeground(new Color(255, 255, 255));
        btnRun.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        btnRun.setContentAreaFilled(false); // 1
        btnRun.setOpaque(true); // 2 //1,2를 선언해줘야 변경하기 버튼의 배경색
        btnRun.setBackground(new Color(0, 0, 139));
        add(btnRun);

        tbEditGrades = new JTable(model);
        tbEditGrades.setBounds(17, 89, 401, 175);
        tbEditGrades.getTableHeader().setReorderingAllowed(false);
        tbEditGrades.getTableHeader().setResizingAllowed(false);
        JScrollPane scroll = new JScrollPane(tbEditGrades);
        scroll.setBounds(17, 82, 416, 203);
        scroll.setPreferredSize(new Dimension(470, 250));
        add(scroll);

        JButton btnEdit = new JButton("수정");
        btnEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 선택한 열 값 가져오기
                int selectedRow = tbEditGrades.getSelectedRow();
                selectedRow = tbEditGrades.convertRowIndexToModel(selectedRow);
                ID = (String)tbEditGrades.getModel().getValueAt(selectedRow, 0);
                Name = (String)tbEditGrades.getModel().getValueAt(selectedRow, 1);
                Score = (String)tbEditGrades.getModel().getValueAt(selectedRow, 2);


                try {
                    new Dao().EditGrades();
                    JOptionPane.showMessageDialog(null, "수정되었습니다.");
                } catch (Exception e1) {
                    System.out.println("[ERROR]"+e1.getMessage());
                    e1.printStackTrace();
                }
            }
        });
        btnEdit.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        btnEdit.setBackground(new Color(0, 0, 128));
        btnEdit.setForeground(Color.WHITE);
        btnEdit.setContentAreaFilled(false); // 1
        btnEdit.setOpaque(true); // 2 //1,2를 선언해줘야 변경하기 버튼의 배경색
        btnEdit.setBackground(new Color(0, 0, 139));
        btnEdit.setBounds(366, 53, 65, 23);
        add(btnEdit);

    }

    // 데이터베이스의 정보를 가져오기 위한 메소드(Load)
    public void Load() {
        Dao dao = new Dao();
        ArrayList<GetterSetter> members = new ArrayList<GetterSetter>();
        Object record[] = new Object[3];

        model.setNumRows(0); // 검색 시마다 기존 테이블에 출력된 내용을 삭제

        try {
            members = dao.resultEdit();

            for (int i = 0; i < members.size(); i++) {
                GetterSetter data = members.get(i);
                record[0] = data.getID();
                record[1] = data.getName();
                record[2] = data.getScore();

                model.addRow(record);
            }

        } catch (SQLException e) {
            System.out.println("[ERROR]" + e.getMessage()); // 예외 메시지 (console) 인쇄
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("[ERROR]" + e.getMessage());
            e.printStackTrace();
        }
    }
}
