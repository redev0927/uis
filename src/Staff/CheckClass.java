package Staff;

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


public class CheckClass extends JPanel {
	public static JTable tbSearch;
	private String[] Header = {"���ǹ�ȣ", "���Ǹ�", "��米��","������","�ִ��ο�","����","��������", ""};//
	private DefaultTableModel model = new DefaultTableModel(null, Header){
        public boolean isCellEditable(int rowIndex, int mColIndex) {
        	int i=0;
            boolean state;
            
            if(mColIndex==7+(8*i)) {
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
	public CheckClass() {
		setBackground(Color.WHITE);
		setLayout(null);
		
		JLabel lbCheckClass = new JLabel("���� Ȯ��");
		lbCheckClass.setForeground(new Color(0, 0, 128));
		lbCheckClass.setBounds(17, 15, 82, 21);
		lbCheckClass.setFont(new Font("���� ���", Font.BOLD, 18));
		add(lbCheckClass);
		
		Load();
		
		tbSearch = new JTable(model);
		
		tbSearch.getColumnModel().getColumn(7).setCellRenderer(new TableCell());;
	    tbSearch.getColumnModel().getColumn(7).setCellEditor(new TableCell());;
		
		tbSearch.setBackground(Color.white);
		tbSearch.getTableHeader().setReorderingAllowed(false);
		tbSearch.getTableHeader().setResizingAllowed(false);
		JScrollPane scroll = new JScrollPane(tbSearch);
		scroll.setLocation(17, 51);
		scroll.setSize(449, 315);
		scroll.setPreferredSize(new Dimension(450, 250));
		add(scroll);
		
	}
	
	//
	   class TableCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer{

		      JButton Open;

		      public TableCell() {
		         // TODO Auto-generated constructor stub
		         Open= new JButton("����");
		         Open.setFont(new Font("���� ���", Font.BOLD, 10));

		         Open.addActionListener(e -> {
		            try {
		               new STFDAO().OpenClass();
		               JOptionPane.showMessageDialog(null, "���� ������ �Ϸ�Ǿ����ϴ�.");
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
		         return Open;
		      }

		      @Override
		      public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
		            int column) {
		         // TODO Auto-generated method stub
		         return Open;
		      }
		   }
	
	
	//���� ������ ��������  �޼ҵ�
	public void Load() {
		STFDAO dao = new STFDAO();
		ArrayList<STFGS> members = new ArrayList<STFGS>();
		Object record[] = new Object[7];
		
		model.setNumRows(0);
		
		try {
			members = dao.Search();

		for(int i=0; i<members.size();i++) {
			STFGS vo = members.get(i);
			record[0] = vo.getClassNumber();
			record[1] = vo.getClassName();
			record[2] = vo.getProfessor();
			record[3] = vo.getScore();
			record[4] = vo.getCount();
			record[5] = vo.getExp();
			record[6] = vo.getSTATE(); //��������
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
