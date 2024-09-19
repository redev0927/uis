package Student;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;

public class ChkClass extends JPanel {
    public static JTable tbchkSignupClass;
    private String[] Header = {"강의번호", "강의명", "담당교수", "학점", " "};
    private DefaultTableModel model = new DefaultTableModel(null, Header){
        public boolean isCellEditable(int rowIndex, int mColIndex) {
            int i=0;
            boolean state;

            if(mColIndex==4+(5*i)) {
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

    /**
     * Create the panel.
     */
    public ChkClass() {
        setBackground(Color.WHITE);
        setLayout(null);

        JLabel lbTitle = new JLabel("수강내역조회");
        lbTitle.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        lbTitle.setBounds(17, 40, 136, 21);
        add(lbTitle);

        tbchkSignupClass = new JTable(model);

        tbchkSignupClass.getColumnModel().getColumn(4).setCellRenderer(new TableCell());;
        tbchkSignupClass.getColumnModel().getColumn(4).setCellEditor(new TableCell());;

        tbchkSignupClass.setBackground(Color.white);
        tbchkSignupClass.getTableHeader().setReorderingAllowed(false);
        tbchkSignupClass.getTableHeader().setResizingAllowed(false);
        JScrollPane scroll = new JScrollPane(tbchkSignupClass);
        scroll.setLocation(17, 70);
        scroll.setSize(450, 150);
        scroll.setPreferredSize(new Dimension(470, 250));
        add(scroll);

        Load();
    }

    class TableCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer{

        JButton Cancel;

        public TableCell() {
            // TODO Auto-generated constructor stub
            Cancel = new JButton("수강취소");

            Cancel.addActionListener(e -> {
                try {
                    new Dao().CancelClass();
                    JOptionPane.showMessageDialog(null, "수강신청이 취소 되었습니다.");
                } catch (Exception e1) {
                    System.out.println("[ERROR]"+e1.getMessage());
                    e1.printStackTrace();
                }
            });

        }

        @Override
        public Object getCellEditorValue() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            // TODO Auto-generated method stub
            return Cancel;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
                                                     int column) {
            // TODO Auto-generated method stub
            return Cancel;
        }

    }

    // 데이터베이스의 정보를 가져오기 위한 메소드(Load)
    public void Load() {
        Dao dao = new Dao();
        ArrayList<GetterSetter> members = new ArrayList<GetterSetter>();
        Object record[] = new Object[4];

        model.setNumRows(0);	// 검색 시마다 기존 테이블에 출력된 내용을 삭제

        try {
            members = dao.resultTakes();

            for(int i=0; i<members.size();i++) {
                GetterSetter data = members.get(i);
                record[0] = data.getID();
                record[1] = data.getCouName();
                record[2] = data.getName();
                record[3] = data.getCredit();
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
