package core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.awt.event.KeyAdapter;
//import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import data.User;


@SuppressWarnings("serial")
public class Login extends JFrame {
	private JdbcDataView main;

	private class ResetAction implements ActionListener {
		public void actionPerformed(final ActionEvent e){
			username.setText("");
			password.setText("");
			
		}
	}
	class LoginAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			// TODO Auto-generated method stub
			String z=new String(username.getText()); 
			String s=new String(password.getPassword()); 
			String t="111";
			if((z.equals(t)==true)&(s.equals(t)==true)){
			   main.setVisible(true);// 设置可见
			   Login.this.setVisible(false);
			} 
			
			else if(username.getText().length() == 0||s.length() == 0){
					JOptionPane.showMessageDialog(null, "密码和用户名不能为空");
					username.setText("");
					password.setText("");
			}		
			else {
				JOptionPane.showMessageDialog(null, "密码错误或用户不存在");
				username.setText("");
				password.setText("");
			}
			
			
	    }
		
	}
	
	private JPasswordField password;
	private JTextField username;
	private JButton login;
	private JButton reset;
	@SuppressWarnings("unused")
	private static User user;

	public Login(JdbcDataView c) {
		super();
		main=c;
		final BorderLayout borderLayout = new BorderLayout();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		borderLayout.setVgap(10);
		getContentPane().setLayout(borderLayout);
		setTitle("课程管理系统登录");
		setBounds(800, 400,400, 194);

		final JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(new EmptyBorder(0, 0, 0, 0));
		getContentPane().add(panel);

		final JPanel panel_2 = new JPanel();
		final GridLayout gridLayout = new GridLayout(0, 2);
		gridLayout.setHgap(5);
		gridLayout.setVgap(20);
		panel_2.setLayout(gridLayout);
		panel.add(panel_2);

		final JLabel label = new JLabel();
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setPreferredSize(new Dimension(0, 0));
		label.setMinimumSize(new Dimension(0, 0));
		panel_2.add(label);
		label.setText("用  户  名：");

		username = new JTextField(20);
		username.setPreferredSize(new Dimension(0, 0));
		panel_2.add(username);

		final JLabel label_1 = new JLabel();
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(label_1);
		label_1.setText("密      码：");

		password = new JPasswordField(20);
		/*
		password.addKeyListener(new KeyAdapter() {
			public void keyPressed(final KeyEvent e) {
				if (e.getKeyCode() == 10)
					login.doClick();
			}
		});
		*/
		panel_2.add(password);
		final JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.SOUTH);
		login=new JButton();
		login.addActionListener(new LoginAction());	
		login.setText("登录");
		panel_1.add(login);
		reset=new JButton();
		reset.addActionListener(new ResetAction());		
		reset.setText("重置");
		panel_1.add(reset);
		final JLabel tupianLabel = new JLabel();
		 String path = "pic/1.jpg";  
		ImageIcon loginIcon= new ImageIcon(path); 
		tupianLabel.setIcon(loginIcon);
		tupianLabel.setOpaque(true);
		tupianLabel.setBackground(Color.GREEN);
		tupianLabel.setPreferredSize(new Dimension(260, 60));
		panel.add(tupianLabel, BorderLayout.NORTH);
		setVisible(true);
		setResizable(false);

	}
//	public static User getUser() {
//		return user;
//	}
//	public static void setUser(User user) {
//		BookLoginIFrame.user = user;
//	}

}
