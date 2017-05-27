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
//�̳�JFrame��
//public static String grades,weeks,classess;
@SuppressWarnings("unused")
public  class JdbcDataView extends JFrame  implements ActionListener  {
	private static final long serialVersionUID = 1L;
	
	public static String sg;
	public static String sc;
	public static int w;
	public static int tf;
	
	JMenuBar bar;// �˵���
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
	// ��ʼ��
	public JdbcDataView()
	{
	// ���ô���λ�úʹ�С��ǰ���������ֱ��ʾ����λ�õĺ�����������꣬�����������ֱ��ʾ���ڴ�С�Ŀ�Ⱥ͸߶ȡ�
	setBounds(500, 100, 850, 550);
	//setVisible(true);// ���ÿɼ�
	//first=f;
	//AttributeSetUtilities.setWindowOpaque( first, false);
	//����ͼƬ��·���������·�����߾���·��������ͼƬ����"java��Ŀ��"���ļ��£�  
	 String path = "pic/1.jpg";  
     // ����ͼƬ  
     ImageIcon background = new ImageIcon(path);  
     // �ѱ���ͼƬ��ʾ��һ����ǩ����  
     JLabel label = new JLabel(background);  
     // �ѱ�ǩ�Ĵ�Сλ������ΪͼƬ�պ�����������  
     label.setBounds(0, 0, this.getWidth(), this.getHeight());  
     // �����ݴ���ת��ΪJPanel���������÷���setOpaque()��ʹ���ݴ���͸��  
     JPanel imagePanel = (JPanel) this.getContentPane();  
     imagePanel.setOpaque(false);  
     // �ѱ���ͼƬ��ӵ��ֲ㴰�����ײ���Ϊ����  
     this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));  
     init();
	 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //��رհ�ťʱ�˳�  
	
	
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void init() {
	setTitle("�γ̹���ϵͳ");// ���ñ���
	setLayout(new BorderLayout()); //���ò��ֹ�����	
	// �˵����Ͳ˵�
	bar = new JMenuBar();
	menuTeacher = new JMenu("��ʦ��Ϣ");
	menuTeacher.addActionListener(this); // ���¼�
	menuCourse  = new JMenu("�γ���Ϣ");
	menuCourse.addActionListener(this); // ���¼�
	menuAction  = new JMenu("�޸Ŀα�");
	bar.add(menuTeacher); //�������ӵ�������
	bar.add(menuCourse);
	bar.add(menuAction);
	setJMenuBar(bar);
	// �Ӳ˵�
	SeachTheacher =new JMenuItem("�鿴��ʦ��Ϣ");
	SeachTheacher.addActionListener(this); // ���¼�
	menuTeacher.add(SeachTheacher);
	SeachCourse=new JMenuItem("�鿴�γ���Ϣ");
	SeachCourse.addActionListener(this); // ���¼�
	menuCourse.add(SeachCourse);
	ActionAdd = new JMenuItem("��ӿγ�");
	ActionAdd.addActionListener(this); // ���¼�
	menuAction.add(ActionAdd);
	//ActionAlter= new JMenuItem("�޸Ŀγ�");
	//ActionAlter.addActionListener(this); // ���¼�
	//menuAction.add(ActionAlter);
	ActionDelete = new JMenuItem("ɾ���γ�");
	ActionDelete.addActionListener(this); // ���¼�
	menuAction.add(ActionDelete);
	
	// ɾ�����ݰ�ť�������
	text = new JTextField(16);
	buttonschedule=new JButton("�鿴�α�");
	label_grade = new JLabel("ѡ���꼶��");
	label_week = new JLabel("ѡ���ܣ�");
	label_classes = new JLabel("ѡ��༶��");
	grade=new JComboBox(); 
	grade.addItem("��һ��");
	grade.addItem("��һ��");
	grade.addItem("�����"); 
	grade.addItem("�����"); 
	grade.addItem("������");
	grade.addItem("������");
	grade.addItem("������");
	grade.addItem("������");
	week=new JComboBox();
	week.addItem("1");
	week.addItem("2");
	week.addItem("3");
	week.addItem("4");
	week.addItem("5");
	week.addItem("6");
	classes=new JComboBox(); 
	classes.addItem("1,2��");
	classes.addItem("3,4��");
	classes.addItem("5,6��");
	classes.addItem("���");
	panel = new JPanel();/*{
		public void paintComponent(Graphics g){
			  try{
			    g.drawImage(ImageIO.read(new    File("C:\\Users\\K550\\Desktop\\classsystem\\1.jpg")),0 ,0,this.getWidth(),this.getHeight(),this);
			   }catch(IOException e){}
	    }
	};*/
	panel.setOpaque(false);    //��panel����Ϊ͸��
	//this.setContentPane(panel);
	panel.add(label_grade); //��label�ؼ���ӵ������
	panel.add(grade);//��ComboBox()�ؼ��ӵ������ȥ
	panel.add(label_week);
	panel.add(week);
	panel.add(label_classes);
	panel.add(classes);
	panel.add(buttonschedule);
	//button.addActionListener(this); //Ϊ��ťע�������
	buttonschedule.addActionListener(this);
	add(panel, BorderLayout.SOUTH);
	
	Object[] Week={"","����һ","���ڶ�","������","������","������","������","������"};
	Object[][]TimeFrame={{"��һ��","","","","","","",""},
			             {"�ڶ���","","","","","","",""},
			             {"������","","","","","","",""},
			             {"���Ľ�","","","","","","",""},
			             {"�����","","","","","","",""},
			             {"������","","","","","","",""},
			            };
	table = new JTable(TimeFrame, Week) ;
			/*
	table = new JTable(TimeFrame,Week) { // ����jtable�ĵ�Ԫ��Ϊ͸����
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
	table.setOpaque(false);//���������Ϊ͸��       
	add(table, BorderLayout.CENTER);
	table.setVisible(true);
	table.setRowHeight(70);
	table.setCellSelectionEnabled(true);//ʹ�ñ���ѡȡ����cellΪ��λ,����������Ϊ��λ.����û��д����,����ѡȡ�������ʱ������Ϊ��λ.
	//this.getContentPane().add(lab, -1); // jframe�����Ǵ��壬���ܷ����κ��������getContentPane()�����õ�frame��Ĭ��������壬��lab�������У�-1��ʾ���������²�
	//this.getContentPane().add(table, 0); // 0��ʾ�����������
	DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();  //���ñ���ÿһ��Ϊ͸��
	renderer.setOpaque(false);//render��Ԫ�������  
	for(int i = 0 ; i < Week.length ; i ++)  
	{  
	    table.getColumn(Week[i]).setCellRenderer(renderer);  
	}  
	JScrollPane scroller = new JScrollPane(table);
	scroller.setOpaque(false);//����������Ϊ͸��
	scroller.getViewport().setOpaque(false);  
	add( scroller, BorderLayout.CENTER );
	Container c = this.getContentPane();
	((JPanel) c).setOpaque(false); // �������Ϊ͸����
	c.remove(1);
	c.add( scroller, BorderLayout.CENTER );
	//ˢ��Table
	c.validate();
	
	}
	//���������¼�ʱ��ִ�еķ���
	public void actionPerformed(ActionEvent e) {
		
	// ��������ѯ�����̰�ť
		
	if (e.getSource() == ActionAdd ) {
		
	
		if(table.getSelectedRow()==-1)
		{
			JFrame newFrame =new JFrame() ;
			JOptionPane.showMessageDialog(newFrame,"��ѡ��ʱ�Σ�", "ϵͳ��Ϣ", JOptionPane.INFORMATION_MESSAGE);
		}
		else
		{
			grades=  grade.getSelectedItem().toString();
			weeks=week.getSelectedItem().toString();
		    n = Integer.valueOf(weeks).intValue();
		    classess=classes.getSelectedItem().toString();
			int row=table.getSelectedRow();//����ѡ�����е��������ڼ���
			int column=table.getSelectedColumn();//����ѡ�����е��������ڼ�
			String currentvalue="";
			currentvalue=(String)table.getValueAt(row,column);
		row=row+1;
		w=column;
		tf=row;
		
		  AddSchedule a=new AddSchedule(sg,sc,w,tf,first);
		 
		}
		
	}
	
	// ������ɾ���γ̰�ť
	if(e.getSource() == ActionDelete){
		if(table.getSelectedRow()==-1)
		{
			JFrame newFrame =new JFrame() ;
			JOptionPane.showMessageDialog(newFrame,"��ѡ��һ��ʱ�Σ�", "ϵͳ��Ϣ", JOptionPane.INFORMATION_MESSAGE);
		}
		else{
			grades=  grade.getSelectedItem().toString();
			weeks=week.getSelectedItem().toString();
		    n = Integer.valueOf(weeks).intValue();
		    classess=classes.getSelectedItem().toString();
	int row=table.getSelectedRow();//����ѡ�����е��������ڼ���
	int column=table.getSelectedColumn();//����ѡ�����е��������ڼ�
	String currentvalue="";
	currentvalue=(String)table.getValueAt(row,column);
	if(currentvalue.length()==0){
		JFrame newFrame =new JFrame() ;
		JOptionPane.showMessageDialog(newFrame,"��ѡ��һ����Чʱ�Σ�", "ϵͳ��Ϣ", JOptionPane.INFORMATION_MESSAGE);
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
	//JTable��getSelectedRows()��getSelectedColumns()�����᷵����ѡȡ���cell��index Array����.
	    int row=table.getSelectedRow();
	    int column=table.getSelectedColumn();
	//JTable��getValueAt()�����᷵��ĳ�е�cell����,����ֵ��Object��������,�������Ҫ����ת��String��������.
	    
	        tempString = tempString+" "+(String)table.getValueAt(row, column);
	    
	    tabelelabel.setText("��ѡȡ:"+tempString);
	}
	}
	*/
	public void query(String sql){
	ResultSet resultSet = JdbcConnection.executeQuery(sql);// ��ȡ���ݼ�
	try {
	displayResultSet(resultSet);
	} catch (SQLException e1) {
	e1.printStackTrace(); //�������д�ӡ�쳣��Ϣ
	}
	JdbcConnection.close();
	}
	
	private void update(String sql){
	int count = JdbcConnection.executeUpdate(sql);//
	System.out.println("��Ӱ�������� " + count);
	JdbcConnection.close();
	
	}
	
	//@SuppressWarnings({ "unchecked", "rawtypes" }) //�������Ʊ���������������Ϣ��
	//@SuppressWarnings({ "rawtypes", "unchecked" })
	

	private void displayResultSet(ResultSet rs) throws SQLException {

	String[] Week={"","����һ","���ڶ�","������","������","������","������","������"};
	Object[][]searchresults=new Object[6][8];
	try {
	
		String[] a={"��һ��","�ڶ���","������","���Ľ�","�����","������"};
		for(int i=0;i<6;i++){
		searchresults[i][0]=a[i];
	    }
		while (rs.next()){
			int x=rs.getInt("timeframe")-1;
			int y=rs.getInt("week");
			searchresults[x][y]=rs.getString("cname");
		}	
	table = new JTable(searchresults, Week);
	table.setOpaque(false);//���������Ϊ͸��       
	table.setCellSelectionEnabled(true);//ʹ�ñ���ѡȡ����cellΪ��λ,����������Ϊ��λ.����û��д����,����ѡȡ�������ʱ������Ϊ��λ.
	DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();  //���ñ���ÿһ��Ϊ͸��
	renderer.setOpaque(false);//render��Ԫ�������  
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
	DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();  //���ñ���ÿһ��Ϊ͸��
	renderer.setOpaque(false);//render��Ԫ�������  
	for(int i = 0 ; i < Week.length ; i ++)  
	{  
	    table.getColumn(Week[i]).setCellRenderer(renderer);  
	}  
	JScrollPane scroller = new JScrollPane(table);
	scroller.setOpaque(false);//����������Ϊ͸��
	scroller.getViewport().setOpaque(false);  
	Container c = getContentPane();
	c.remove(1);
	c.add( scroller, BorderLayout.CENTER );
	((JPanel) c).setOpaque(false); // �������Ϊ͸����
	//ˢ��Table
	c.validate();
	}
	
	public static void main(String[] args) {
	
		  first= new  JdbcDataView();
		  first.setVisible(false);// ���ò��ɼ�
		  new Login(first); 
		
		 //
	}
	
}
