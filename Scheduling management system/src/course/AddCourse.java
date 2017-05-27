package course;

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
public class AddCourse extends JFrame implements ActionListener    {
	JTextField textcid;
	JLabel label_cid;
	JLabel label_coursename;
	JLabel label_teacherid;
	JLabel label_grade;
	JLabel label_time;
	JPanel panel1;
	JPanel panel2;
	JPanel panel3;
	JScrollPane jscrollpane;
	JTextField textcoursename;
	JTextField texttid;
	JTextField texttime;
	@SuppressWarnings("rawtypes")
	JComboBox grade;
	//JComboBox eweek;
	JButton buttonadd;
	//private ShowCourse d;
	public AddCourse() {
		super();
	  								// ���ô���ɹرգ���������
		setTitle("��ӿγ�");						// ���ô�����⣭��������
		setBounds(400, 200, 550, 300);		// ���ô���λ�úʹ�С����������
		// d=s;
		init();
		setVisible(true);					
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void init(){
		//String grade,classes;
		//setLayout(new BorderLayout()); //���ò��ֹ�����	
		label_cid=new JLabel("����γ̺ţ�");
		label_cid.setSize(150, 30);
		label_cid.setLocation(100, 40); 
		label_coursename=new JLabel("����γ�����");
		label_coursename.setSize(150, 30);
		label_coursename.setLocation(100, 80);
		label_teacherid=new JLabel("�����ʦ�ţ�");
		label_teacherid.setSize(150, 30);
		label_teacherid.setLocation(100, 120); 	
		label_grade=new JLabel("����ѧ�ڣ�");
		label_grade.setSize(150, 30);
		label_grade.setLocation(100, 160); 
		label_time=new JLabel("����ʱ����");
		label_time.setSize(150, 30);
		label_time.setLocation(100, 200); 
		
		
		textcid=new JTextField(); 
		textcid.setSize(150, 30);
		textcid.setLocation(200, 40); 
		textcoursename=new JTextField();
		textcoursename.setSize(150, 30);
		textcoursename.setLocation(200, 80);
		texttid=new JTextField();
		texttid.setSize(150, 30);
		texttid.setLocation(200, 120); 	
		grade=new JComboBox();
		grade.addItem("��һ��");
		grade.addItem("��һ��");
		grade.addItem("�����"); 
		grade.addItem("�����"); 
		grade.addItem("������");
		grade.addItem("������");
		grade.addItem("������");
		grade.addItem("������");
		grade.setSize(150, 30);
		grade.setLocation(200, 160); 
		texttime=new JTextField();
		texttime.setSize(150, 30);
		texttime.setLocation(200,200); 	
		buttonadd=new JButton("���");
		buttonadd.addActionListener(this);
		buttonadd.setSize(80, 30);
		buttonadd.setLocation(400, 200); 
		
		panel1 = new JPanel();
		panel1.add(label_cid);
		panel1.add(textcid);
		panel1.add(label_coursename);
		panel1.add(textcoursename);
		panel1.add(label_teacherid);
		panel1.add(texttid);
		panel1.add(label_grade);
		panel1.add(grade);
		panel1.add(label_time);
		panel1.add(texttime);
		
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
			String a,b,c,d,m,f;
			a=textcid.getText();
			b=textcoursename.getText();
			c=texttid.getText();
			d=grade.getSelectedItem().toString();
			m="�������";
			f=texttime.getText();
			if(a.length()==0||b.length()==0||c.length()==0||d.length()==0||f.length()==0)
			{     JFrame newFrame=new JFrame();
				 JOptionPane.showMessageDialog(newFrame.getContentPane(),"������������Ϣ!", "ϵͳ��Ϣ", JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				String sql="insert into Course values('"+a+"','"+b+"','"+c+"','"+d+"','"+m+"','"+f+"')";
				update(sql);
			}
			
		}
		
	}
	private void update(String sql){
		int count = JdbcConnection.executeUpdate(sql);//
		System.out.println("��Ӱ�������� " + count);
		JdbcConnection.close();
		
		}
	


}

//f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);