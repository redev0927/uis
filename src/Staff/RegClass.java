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
		
		JLabel lbRegClass = new JLabel("°­ÁÂ µî·Ï");
		lbRegClass.setForeground(new Color(0, 0, 128));
		lbRegClass.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 18));
		lbRegClass.setBounds(36, 15, 82, 21);
		add(lbRegClass);
		
		JLabel lbClassNumber = new JLabel("°­ÁÂ¹øÈ£");
		lbClassNumber.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 15));
		lbClassNumber.setBounds(36, 51, 82, 21);
		add(lbClassNumber);
		
		JLabel lbClassName = new JLabel("°­ÁÂÀÌ¸§");
		lbClassName.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 15));
		lbClassName.setBounds(36, 87, 82, 21);
		add(lbClassName);
		
		JLabel lbProfessor = new JLabel("\uB2F4\uB2F9\uAD50\uC218 ID");
		lbProfessor.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 15));
		lbProfessor.setBounds(206, 48, 147, 21);
		add(lbProfessor);
		
		JLabel lbScore = new JLabel("ÇÐÁ¡¼ö");
		lbScore.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 15));
		lbScore.setBounds(230, 118, 82, 21);
		add(lbScore);
		
		JLabel lbExp = new JLabel("¼³¸í");
		lbExp.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 15));
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
		
		JButton btnRegClass = new JButton("µî·Ï");
		btnRegClass.setBackground(Color.WHITE);
		btnRegClass.setForeground(Color.WHITE);
		btnRegClass.setContentAreaFilled(false); // 1
		btnRegClass.setOpaque(true); // 2 //1,2¸¦ ¼±¾ðÇØÁà¾ß º¯°æÇÏ±â ¹öÆ°ÀÇ ¹è°æ»ö
		btnRegClass.setBackground(new Color(0, 0, 139));
		btnRegClass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Perm = cbScore.getSelectedItem().toString();
				try {
					new STFDAO().setClass();//°­ÁÂµî·ÏÀ¸·Î °íÃÄº½
					JOptionPane.showMessageDialog(null, "µî·ÏÀÌ ¿Ï·áµÇ¾ú½À´Ï´Ù.");
				} catch (Exception e) {
					System.out.println("[ERROR]"+e.getMessage());
					e.printStackTrace();
				}
			}
		});
		btnRegClass.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 15));
		btnRegClass.setBounds(150, 256, 93, 29);
		add(btnRegClass);
		
		JLabel label = new JLabel("ÃÖ´ë¼ö°­ÀÎ¿ø");
		label.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 15));
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
