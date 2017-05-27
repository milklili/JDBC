package core;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import course.ShowCourse;
import schedule.AddSchedule;
import teacher.Showteacher;

import javax.imageio.ImageIO;
import javax.print.attribute.AttributeSetUtilities;
import javax.swing.*;
//继承JFrame类
//public static String grades,weeks,classess;
@SuppressWarnings("unused")
public  class JdbcDataView extends JFrame  implements ActionListener  {
	private static final long serialVersionUID = 1L;
	
	public static String sg;
	public static String sc;
	public static int w;
	public static int tf;
	
	JMenuBar bar;// 菜单条
	JMenu menuTeacher, menuCourse, menuAction;
	JMenuItem SeachTheacher,SeachCourse,ActionAdd, ActionAlter, ActionDelete;
	//JMenuItem addAgents, addCustomers, addOrders, addProducts;
	JTextField text;
	JButton buttondelete;
	JButton buttonadd;
	JButton buttonschedule;
	@SuppressWarnings("rawtypes")
    JComboBox grade;
	@SuppressWarnings("rawtypes")
	JComboBox week;
	@SuppressWarnings("rawtypes")
    JComboBox classes;
	
	JLabel label_grade;
	JLabel label_week;
	JLabel label_classes;
	JLabel tabelelabel;
	JPanel panel;
	JScrollPane jscrollpane;
	JTable table=null;
	public String grades,weeks,classess;
	public int n;
	static JdbcDataView first;
	ListSelectionModel selectionMode=null;
	DefaultTableModel dtm;
	ResultSetMetaData rsmd;
	// 初始化
	public JdbcDataView()
	{
	// 设置窗口位置和大小，前两个参数分别表示窗口位置的横坐标和纵坐标，后两个参数分别表示窗口大小的宽度和高度。
	setBounds(500, 100, 850, 550);
	//setVisible(true);// 设置可见
	//first=f;
	//AttributeSetUtilities.setWindowOpaque( first, false);
	//背景图片的路径。（相对路径或者绝对路径。本例图片放于"java项目名"的文件下）  
	 String path = "pic/1.jpg";  
     // 背景图片  
     ImageIcon background = new ImageIcon(path);  
     // 把背景图片显示在一个标签里面  
     JLabel label = new JLabel(background);  
     // 把标签的大小位置设置为图片刚好填充整个面板  
     label.setBounds(0, 0, this.getWidth(), this.getHeight());  
     // 把内容窗格转化为JPanel，否则不能用方法setOpaque()来使内容窗格透明  
     JPanel imagePanel = (JPanel) this.getContentPane();  
     imagePanel.setOpaque(false);  
     // 把背景图片添加到分层窗格的最底层作为背景  
     this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));  
     init();
	 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //点关闭按钮时退出  
	
	
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void init() {
	setTitle("课程管理系统");// 设置标题
	setLayout(new BorderLayout()); //设置布局管理器	
	// 菜单条和菜单
	bar = new JMenuBar();
	menuTeacher = new JMenu("教师信息");
	menuTeacher.addActionListener(this); // 绑定事件
	menuCourse  = new JMenu("课程信息");
	menuCourse.addActionListener(this); // 绑定事件
	menuAction  = new JMenu("修改课表");
	bar.add(menuTeacher); //将组件添加到窗口中
	bar.add(menuCourse);
	bar.add(menuAction);
	setJMenuBar(bar);
	// 子菜单
	SeachTheacher =new JMenuItem("查看教师信息");
	SeachTheacher.addActionListener(this); // 绑定事件
	menuTeacher.add(SeachTheacher);
	SeachCourse=new JMenuItem("查看课程信息");
	SeachCourse.addActionListener(this); // 绑定事件
	menuCourse.add(SeachCourse);
	ActionAdd = new JMenuItem("添加课程");
	ActionAdd.addActionListener(this); // 绑定事件
	menuAction.add(ActionAdd);
	//ActionAlter= new JMenuItem("修改课程");
	//ActionAlter.addActionListener(this); // 绑定事件
	//menuAction.add(ActionAlter);
	ActionDelete = new JMenuItem("删除课程");
	ActionDelete.addActionListener(this); // 绑定事件
	menuAction.add(ActionDelete);
	
	// 删除数据按钮、输入框
	text = new JTextField(16);
	buttonschedule=new JButton("查看课表");
	label_grade = new JLabel("选择年级：");
	label_week = new JLabel("选择周：");
	label_classes = new JLabel("选择班级：");
	grade=new JComboBox(); 
	grade.addItem("大一上");
	grade.addItem("大一下");
	grade.addItem("大二上"); 
	grade.addItem("大二下"); 
	grade.addItem("大三上");
	grade.addItem("大三下");
	grade.addItem("大四上");
	grade.addItem("大四下");
	week=new JComboBox();
	week.addItem("1");
	week.addItem("2");
	week.addItem("3");
	week.addItem("4");
	week.addItem("5");
	week.addItem("6");
	classes=new JComboBox(); 
	classes.addItem("1,2班");
	classes.addItem("3,4班");
	classes.addItem("5,6班");
	classes.addItem("混合");
	panel = new JPanel();/*{
		public void paintComponent(Graphics g){
			  try{
			    g.drawImage(ImageIO.read(new    File("C:\\Users\\K550\\Desktop\\classsystem\\1.jpg")),0 ,0,this.getWidth(),this.getHeight(),this);
			   }catch(IOException e){}
	    }
	};*/
	panel.setOpaque(false);    //将panel设置为透明
	//this.setContentPane(panel);
	panel.add(label_grade); //将label控件添加到面板中
	panel.add(grade);//将ComboBox()控件加到面板中去
	panel.add(label_week);
	panel.add(week);
	panel.add(label_classes);
	panel.add(classes);
	panel.add(buttonschedule);
	//button.addActionListener(this); //为按钮注册监听器
	buttonschedule.addActionListener(this);
	add(panel, BorderLayout.SOUTH);
	
	Object[] Week={"","星期一","星期二","星期三","星期四","星期五","星期六","星期天"};
	Object[][]TimeFrame={{"第一节","","","","","","",""},
			             {"第二节","","","","","","",""},
			             {"第三节","","","","","","",""},
			             {"第四节","","","","","","",""},
			             {"第五节","","","","","","",""},
			             {"第六节","","","","","","",""},
			            };
	table = new JTable(TimeFrame, Week) ;
			/*
	table = new JTable(TimeFrame,Week) { // 设置jtable的单元格为透明的
		public Component prepareRenderer(TableCellRenderer renderer,
				 int row, int column) {
				 Component c = super.prepareRenderer(renderer, row, column);
				 if (c instanceof JComponent) {
				 ((JComponent) c).setOpaque(false);
				 }
				 return c;
				 }
				 };
				 */
	table.setOpaque(false);//将表格设置为透明       
	add(table, BorderLayout.CENTER);
	table.setVisible(true);
	table.setRowHeight(70);
	table.setCellSelectionEnabled(true);//使得表格的选取是以cell为单位,而不是以列为单位.若你没有写此行,则在选取表格数据时以整列为单位.
	//this.getContentPane().add(lab, -1); // jframe本身是窗体，不能放置任何组件，用getContentPane()方法得到frame的默认内容面板，将lab放入其中，-1表示放入面板的下层
	//this.getContentPane().add(table, 0); // 0表示放在面板的最顶层
	DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();  //设置表格的每一格为透明
	renderer.setOpaque(false);//render单元格的属性  
	for(int i = 0 ; i < Week.length ; i ++)  
	{  
	    table.getColumn(Week[i]).setCellRenderer(renderer);  
	}  
	JScrollPane scroller = new JScrollPane(table);
	scroller.setOpaque(false);//将该容器设为透明
	scroller.getViewport().setOpaque(false);  
	add( scroller, BorderLayout.CENTER );
	Container c = this.getContentPane();
	((JPanel) c).setOpaque(false); // 设置面板为透明的
	c.remove(1);
	c.add( scroller, BorderLayout.CENTER );
	//刷新Table
	c.validate();
	
	}
	//触发动作事件时，执行的方法
	public void actionPerformed(ActionEvent e) {
		
	// 如果点击查询代理商按钮
		
	if (e.getSource() == ActionAdd ) {
		
	
		if(table.getSelectedRow()==-1)
		{
			JFrame newFrame =new JFrame() ;
			JOptionPane.showMessageDialog(newFrame,"请选择时段！", "系统信息", JOptionPane.INFORMATION_MESSAGE);
		}
		else
		{
			grades=  grade.getSelectedItem().toString();
			weeks=week.getSelectedItem().toString();
		    n = Integer.valueOf(weeks).intValue();
		    classess=classes.getSelectedItem().toString();
			int row=table.getSelectedRow();//返回选定的行的索引，第几行
			int column=table.getSelectedColumn();//返回选定的列的索引，第几
			String currentvalue="";
			currentvalue=(String)table.getValueAt(row,column);
		row=row+1;
		w=column;
		tf=row;
		
		  AddSchedule a=new AddSchedule(sg,sc,w,tf,first);
		 
		}
		
	}
	
	// 如果点击删除课程按钮
	if(e.getSource() == ActionDelete){
		if(table.getSelectedRow()==-1)
		{
			JFrame newFrame =new JFrame() ;
			JOptionPane.showMessageDialog(newFrame,"请选择一个时段！", "系统信息", JOptionPane.INFORMATION_MESSAGE);
		}
		else{
			grades=  grade.getSelectedItem().toString();
			weeks=week.getSelectedItem().toString();
		    n = Integer.valueOf(weeks).intValue();
		    classess=classes.getSelectedItem().toString();
	int row=table.getSelectedRow();//返回选定的行的索引，第几行
	int column=table.getSelectedColumn();//返回选定的列的索引，第几
	String currentvalue="";
	currentvalue=(String)table.getValueAt(row,column);
	if(currentvalue.length()==0){
		JFrame newFrame =new JFrame() ;
		JOptionPane.showMessageDialog(newFrame,"请选择一个有效时段！", "系统信息", JOptionPane.INFORMATION_MESSAGE);
	}
	else{
	row=row+1;
	String classess;
	classess=classes.getSelectedItem().toString();
	String sql = "delete from schedule where sid in(select sid from schedule s,course c where s.cid=c.cid and c.cname='"+currentvalue+"' and s.week='"+column+"' and s.timeframe='"+row+"' and s.class='"+classess+"')";
	update(sql);
	String sql2 = "select * from schedule s,course c where s.cid=c.cid and c.grade='"+grades+"' and s.class='"+classess+"' and s.startweek<='"+n+"' and s.endweek>='"+n+"'";
	System.out.println(sql2);
	query(sql2);
	}
		}
	}
	
	if(e.getSource() == SeachTheacher){
		 System.out.println("1111");
		new Showteacher();

	}
	if(e.getSource() == SeachCourse){
		System.out.println("1111");
		new ShowCourse();

	}
	
	if(e.getSource() == buttonschedule){
		//String grades,weeks,classess;
		grades=  grade.getSelectedItem().toString();
		weeks=week.getSelectedItem().toString();
	    n = Integer.valueOf(weeks).intValue();
	    classess=classes.getSelectedItem().toString();
	    sg=grades;
	    sc=classess;
	    String sql = "select * from schedule s,course c where s.cid=c.cid and c.grade='"+grades+"' and s.class='"+classess+"' and s.startweek<='"+n+"' and s.endweek>='"+n+"'";
	    System.out.println(sql);
		query(sql);
		
		
	}
    }
    /*
	class clicktableActionListener implements ListSelectionListener{
	public void valueChanged(ListSelectionEvent el){
	    String tempString="";
	//JTable的getSelectedRows()与getSelectedColumns()方法会返回已选取表格cell的index Array数据.
	    int row=table.getSelectedRow();
	    int column=table.getSelectedColumn();
	//JTable的getValueAt()方法会返回某行的cell数据,返回值是Object数据类型,因此我们要自行转成String数据类型.
	    
	        tempString = tempString+" "+(String)table.getValueAt(row, column);
	    
	    tabelelabel.setText("你选取:"+tempString);
	}
	}
	*/
	public void query(String sql){
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
	
	//@SuppressWarnings({ "unchecked", "rawtypes" }) //用于抑制编译器产生警告信息。
	//@SuppressWarnings({ "rawtypes", "unchecked" })
	

	private void displayResultSet(ResultSet rs) throws SQLException {

	String[] Week={"","星期一","星期二","星期三","星期四","星期五","星期六","星期天"};
	Object[][]searchresults=new Object[6][8];
	try {
	
		String[] a={"第一节","第二节","第三节","第四节","第五节","第六节"};
		for(int i=0;i<6;i++){
		searchresults[i][0]=a[i];
	    }
		while (rs.next()){
			int x=rs.getInt("timeframe")-1;
			int y=rs.getInt("week");
			searchresults[x][y]=rs.getString("cname");
		}	
	table = new JTable(searchresults, Week);
	table.setOpaque(false);//将表格设置为透明       
	table.setCellSelectionEnabled(true);//使得表格的选取是以cell为单位,而不是以列为单位.若你没有写此行,则在选取表格数据时以整列为单位.
	DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();  //设置表格的每一格为透明
	renderer.setOpaque(false);//render单元格的属性  
	for(int i = 0 ; i < Week.length ; i ++)  
	{  
	    table.getColumn(Week[i]).setCellRenderer(renderer);  
	}  
	add(table, BorderLayout.CENTER);
	table.setVisible(true);
	table.setRowHeight(70);
	} catch (SQLException e1) {
	e1.printStackTrace();
	}
	DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();  //设置表格的每一格为透明
	renderer.setOpaque(false);//render单元格的属性  
	for(int i = 0 ; i < Week.length ; i ++)  
	{  
	    table.getColumn(Week[i]).setCellRenderer(renderer);  
	}  
	JScrollPane scroller = new JScrollPane(table);
	scroller.setOpaque(false);//将该容器设为透明
	scroller.getViewport().setOpaque(false);  
	Container c = getContentPane();
	c.remove(1);
	c.add( scroller, BorderLayout.CENTER );
	((JPanel) c).setOpaque(false); // 设置面板为透明的
	//刷新Table
	c.validate();
	}
	
	public static void main(String[] args) {
	
		  first= new  JdbcDataView();
		  first.setVisible(false);// 设置不可见
		  new Login(first); 
		
		 //
	}
	
}
