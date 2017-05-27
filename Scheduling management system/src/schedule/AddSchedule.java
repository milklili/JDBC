package schedule;


import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.*;

import core.JdbcConnection;
import core.JdbcDataView;



@SuppressWarnings("serial")
public class AddSchedule extends JFrame implements ActionListener    {
	JTextField textclass;
	JTextField textsid;
	JLabel label_sid;
	JLabel label_cname;
	JLabel label_cnum;
	JLabel label_sweek;
	JLabel label_eweek;
	JPanel panel1;
	JPanel panel2;
	JPanel panel3;
	JScrollPane jscrollpane;
	@SuppressWarnings("rawtypes")
	JComboBox coursename;
	@SuppressWarnings("rawtypes")
	JComboBox sweek;
	@SuppressWarnings("rawtypes")
	JComboBox eweek;
	JButton buttonadd;
	@SuppressWarnings("rawtypes")
	Vector h;
	ResultSetMetaData rsmd;
	public  String Grade="";;
	public  String Classs="";
	public  int Week;
	public  int Timeframe;
	public  boolean change=false;
	private JdbcDataView d;
	public AddSchedule(String x,String y,int z, int w,JdbcDataView c) {
		
		super();
	  								// 设置窗体可关闭－－－必须
		setTitle("添加排课");						// 设置窗体标题－－－必须
		setBounds(400, 200, 550, 300);		// 设置窗体位置和大小－－－必须
		Grade=x;
		Classs=y;
		Week=z;
		Timeframe=w;
		d=c;
		String sql="select cname from course where grade='"+Grade+"'";
		query( sql);
		System.out.println(Grade);
		//System.out.println(Week);
		init();
		setVisible(true);					
	}
	
	










	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void init(){
		//String grade,classes;
		//setLayout(new BorderLayout()); //设置布局管理器	
		//currow;
		//String[] c= new String [currow.size()] ;
		label_sid=new JLabel("输入排课号：");
		label_sid.setSize(150, 30);
		label_sid.setLocation(100, 10);
		label_cname=new JLabel("选择课程名：");
		label_cname.setSize(150, 30);
		label_cname.setLocation(100, 40); 
		label_cnum=new JLabel("输入教室号：");
		label_cnum.setSize(150, 30);
		label_cnum.setLocation(100, 80);
		label_sweek=new JLabel("选择开始周：");
		label_sweek.setSize(150, 30);
		label_sweek.setLocation(100, 120); 	
		label_eweek=new JLabel("选择结束周：");
		label_eweek.setSize(150, 30);
		label_eweek.setLocation(100, 160); 
		
		textsid =new JTextField(20);
		textsid.setSize(150, 30);
		textsid.setLocation(200, 10);
		coursename=new JComboBox(); 
		for(int i=0;i<h.size();i++){
		coursename.addItem(h.elementAt(i));
		}
		coursename.setSize(150, 30);
		coursename.setLocation(200,50); 
		textclass=new JTextField(20);
		textclass.setSize(150, 30);
		textclass.setLocation(200, 90);
		sweek=new JComboBox(); 
		sweek.addItem("1");
		sweek.addItem("2");
		sweek.addItem("9");
		sweek.addItem("10");
		sweek.addItem("11");
		sweek.setSize(150, 30);
		sweek.setLocation(200, 130); 	
		eweek=new JComboBox();
		eweek.addItem("9");
		eweek.addItem("10");
		eweek.addItem("15");
		eweek.addItem("16");
		eweek.addItem("17");
		eweek.addItem("18");
		eweek.addItem("19");
		eweek.setSize(150, 30);
		eweek.setLocation(200, 170); 
		
		buttonadd=new JButton("添加");
		buttonadd.addActionListener(this);
		buttonadd.setSize(80, 30);
		buttonadd.setLocation(400, 200); 
		
		panel1 = new JPanel();
		panel1.add(label_sid);
		panel1.add(textsid);
		panel1.add(label_cname);
		panel1.add(coursename);
		panel1.add(label_cnum);
		panel1.add(textclass);
		panel1.add(label_sweek);
		panel1.add(sweek);
		
		panel1.add(label_eweek);
		panel1.add(eweek);
		
		panel1.add(buttonadd);
		panel1.setLayout(null);
		add(panel1);
		//add(panel2, BorderLayout.CENTER);
		//add(panel3, BorderLayout.SOUTH);
		
		
	}

	//@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == buttonadd){
			String x,y,z,k,g;
			x=coursename.getSelectedItem().toString();
			String sql1="select cid from course where cname='"+x+"' and grade='"+Grade+"'";
			query(sql1);
			System.out.println(h.size());
			String cid=(String) h.elementAt(0);
			y=sweek.getSelectedItem().toString();
			z=eweek.getSelectedItem().toString();
			k=textclass.getText();
			g=textsid.getText();
			if(k.length()==0||g.length()==0||x.length()==0||y.length()==0||z.length()==0)
			{     JFrame newFrame=new JFrame();
				 JOptionPane.showMessageDialog(newFrame.getContentPane(),"请输入完整信息!", "系统信息", JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
		    int u = Integer.valueOf(y).intValue();
		    int v=  Integer.valueOf(z).intValue();
			String sql="insert into Schedule values('"+g+"','"+cid+"','"+Week+"','"+Timeframe+"','"+k+"','"+u+"','"+v+"','"+Classs+"')";
			update(sql);
			change=true;
			String sql2 = "select * from schedule s,course c where s.cid=c.cid and c.grade='"+Grade+"' and s.class='"+ Classs+"' and s.startweek<='"+Week+"' and s.endweek>='"+Week+"'";
			System.out.println(sql2);
			d.query(sql2);
		

			}
			
		}
		
	}
	
	private void query(String sql){
		ResultSet resultSet = JdbcConnection.executeQuery(sql);// 获取数据集
		try {
		displayResultSet(resultSet);
		} catch (SQLException e1) {
		e1.printStackTrace(); //在命令行打印异常信息
		}
		JdbcConnection.close();
		}
	
	private void update(String sql){
		int count = JdbcConnection.executeUpdate(sql);//
		System.out.println("受影响行数： " + count);
		JdbcConnection.close();
		
		}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void displayResultSet(ResultSet rs) throws SQLException {

		
		h= new Vector();
		try {
		rsmd = rs.getMetaData(); //获取数据集属性名
		
		while (rs.next()) {
		for (int i = 1; i <= rsmd.getColumnCount(); ++i) {
		h.addElement(rs.getString(i));
		}
		}
		
		} catch (SQLException e1) {
		e1.printStackTrace();
		}
		
		}


}

//f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);