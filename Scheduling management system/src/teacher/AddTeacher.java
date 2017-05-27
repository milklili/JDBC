package teacher;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import core.JdbcConnection;


@SuppressWarnings("serial")
public class AddTeacher extends JFrame implements ActionListener    {
	JTextField texttid;
	JLabel label_tid;
	JLabel label_teachername;
	JLabel label_sex;
	JLabel label_degree;
	JLabel label_tmajor;
	JPanel panel1;
	JPanel panel2;
	JPanel panel3;
	JScrollPane jscrollpane;
	JTextField textteachername;
	JTextField texttmajor;
	JTextField textdegree;
	@SuppressWarnings("rawtypes")
	JComboBox sex;
	//JComboBox eweek;
	JButton buttonadd;
	public AddTeacher() {
		super();
	  								// 设置窗体可关闭－－－必须
		setTitle("添加教师");						// 设置窗体标题－－－必须
		setBounds(400, 200, 550, 300);		// 设置窗体位置和大小－－－必须
		init();
		setVisible(true);					
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void init(){
		//String grade,classes;
		//setLayout(new BorderLayout()); //设置布局管理器	
		label_tid=new JLabel("输入教师号：");
		label_tid.setSize(150, 30);
		label_tid.setLocation(100, 40); 
		label_teachername=new JLabel("输入教师名：");
		label_teachername.setSize(150, 30);
		label_teachername.setLocation(100, 80);
		label_sex=new JLabel("选择性别：");
		label_sex.setSize(150, 30);
		label_sex.setLocation(100, 120); 	
		label_degree=new JLabel("输入学位：");
		label_degree.setSize(150, 30);
		label_degree.setLocation(100, 160); 
		label_tmajor=new JLabel("输入专业方向：");
		label_tmajor.setSize(150, 30);
		label_tmajor.setLocation(100, 200); 
		
		
		texttid=new JTextField(); 
		texttid.setSize(150, 30);
		texttid.setLocation(200, 40); 
		textteachername=new JTextField();
		textteachername.setSize(150, 30);
		textteachername.setLocation(200, 80);
		sex=new JComboBox();
		sex.setSize(150, 30);
		sex.setLocation(200, 120); 
		sex.addItem("男");
		sex.addItem("女");
		textdegree=new JTextField();
		textdegree.setSize(150, 30);
		textdegree.setLocation(200, 160); 
		texttmajor=new JTextField();
		texttmajor.setSize(150, 30);
		texttmajor.setLocation(200,200); 	
		buttonadd=new JButton("添加");
		buttonadd.addActionListener(this);
		buttonadd.setSize(80, 30);
		buttonadd.setLocation(400, 200); 
		
		panel1 = new JPanel();
		panel1.add(label_tid);
		panel1.add(texttid);
		panel1.add(label_teachername);
		panel1.add(textteachername);
		panel1.add(label_sex);
		panel1.add(sex);
		panel1.add(label_degree);
		panel1.add(textdegree);
		panel1.add(label_tmajor);
		panel1.add(texttmajor);
		panel1.add(buttonadd);
		panel1.setLayout(null);
		add(panel1);
		//add(panel2, BorderLayout.CENTER);
		//add(panel3, BorderLayout.SOUTH);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == buttonadd){
			String a,b,c,d,m;
			a=texttid.getText();
			b=textteachername.getText();
			c=sex.getSelectedItem().toString();
			d=textdegree.getText();
			m=textdegree.getText();
			if(a.length()==0||b.length()==0||c.length()==0||d.length()==0||m.length()==0)
			{     JFrame newFrame=new JFrame();
				 JOptionPane.showMessageDialog(newFrame.getContentPane(),"请输入完整信息!", "系统信息", JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				String sql="insert into teacher values('"+a+"','"+b+"','"+c+"','"+d+"','"+m+"')";
				update(sql);
			}
			
		}
		
	}
	private void update(String sql){
		int count = JdbcConnection.executeUpdate(sql);//
		System.out.println("受影响行数： " + count);
		JdbcConnection.close();
		
		}
	


}

