package Student;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class SignUpClass extends JPanel {
	public static JTable tbSignupClass;

	private String[] Header = {"강의번호", "강의명", "담당교수", "학점", "설명", " "};
	private DefaultTableModel model = new DefaultTableModel(null, Header) {
        public boolean isCellEditable(int rowIndex, int mColIndex) {
        	int i=0;
        	boolean state;
        	
        	if(mColIndex==5+(6*i)) {
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
	public SignUpClass() {
		setBackground(Color.WHITE);
		setLayout(null);

		JLabel lbTitle = new JLabel("수강신청");
		lbTitle.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		lbTitle.setBounds(17, 40, 82, 21);
		add(lbTitle);

		tbSignupClass = new JTable(model);

		tbSignupClass.getColumnModel().getColumn(5).setCellRenderer(new TableCell());;
		tbSignupClass.getColumnModel().getColumn(5).setCellEditor(new TableCell());;

		tbSignupClass.setBackground(Color.white);
		tbSignupClass.getTableHeader().setReorderingAllowed(false);
		tbSignupClass.getTableHeader().setResizingAllowed(false);
		JScrollPane scroll = new JScrollPane(tbSignupClass);
		scroll.setLocation(17, 70);
		scroll.setSize(480, 150);
		scroll.setPreferredSize(new Dimension(500, 250));
		add(scroll);
		
		Load();
	}

	class TableCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer{

		JButton SignUp;

		public TableCell() {
			// TODO Auto-generated constructor stub
			SignUp = new JButton("수강신청");
			SignUp.setFont(new Font("맑은 고딕", Font.BOLD, 10));

			SignUp.addActionListener(e -> {
				try {
					new StuDAO().submitClass();
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
			return SignUp;
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			// TODO Auto-generated method stub
			return SignUp;
		}
	}
	
	// 데이터베이스의 정보를 가져오기 위한 메소드(Load)
	public void Load() {
		StuDAO dao = new StuDAO();
		ArrayList<StudentGS> members = new ArrayList<StudentGS>();
		Object record[] = new Object[5];

		model.setNumRows(0);	// 검색 시마다 기존 테이블에 출력된 내용을 삭제

		try {				
			members = dao.OpenedClass();

			for(int i=0; i<members.size();i++) {
				StudentGS data = members.get(i);
				record[0] = data.getID();
				record[1] = data.getCouName();
				record[2] = data.getName();
				record[3] = data.getCredit();
				record[4] = data.getIntro();
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
