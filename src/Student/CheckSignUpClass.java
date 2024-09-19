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

public class CheckSignUpClass extends JPanel {
	public static JTable tbchkSignupClass;
	private String[] Header = {"���ǹ�ȣ", "���Ǹ�", "��米��", "����", " "};
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
	public CheckSignUpClass() {
		setBackground(Color.WHITE);
		setLayout(null);
		
		JLabel lbTitle = new JLabel("����������ȸ");
		lbTitle.setFont(new Font("���� ���", Font.BOLD, 18));
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
			Cancel = new JButton("�������");
			
			Cancel.addActionListener(e -> {
				try {
					new StuDAO().CancelClass();
					JOptionPane.showMessageDialog(null, "������û�� ��� �Ǿ����ϴ�.");
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
	
	// �����ͺ��̽��� ������ �������� ���� �޼ҵ�(Load)
	public void Load() {
		StuDAO dao = new StuDAO();
		ArrayList<StudentGS> members = new ArrayList<StudentGS>();
		Object record[] = new Object[4];

		model.setNumRows(0);	// �˻� �ø��� ���� ���̺� ��µ� ������ ����

		try {				
			members = dao.resultTakes();

			for(int i=0; i<members.size();i++) {
				StudentGS data = members.get(i);
				record[0] = data.getID();
				record[1] = data.getCouName();
				record[2] = data.getName();
				record[3] = data.getCredit();
				model.addRow(record);
			}

		} catch(SQLException e) {
			System.out.println("[ERROR]"+e.getMessage()); // ���� �޽��� (console) �μ�
			e.printStackTrace();
		} catch(Exception e) {
			System.out.println("[ERROR]"+e.getMessage());
			e.printStackTrace();
		}
	}
}
