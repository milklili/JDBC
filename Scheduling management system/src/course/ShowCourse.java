package course;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import core.JdbcConnection;

public class ShowCourse implements ActionListener,ListSelectionListener{
JTable table=null;
ListSelectionModel selectionMode=null;
JLabel label=null;//显示用户选取表格之用
@SuppressWarnings("rawtypes")
Vector rows;
@SuppressWarnings("rawtypes")
Vector currow;
JButton addButton;
JButton alterButton;
JButton deleteButton;
JButton fresh;
ResultSetMetaData rsmd;


public ShowCourse(){
    JFrame f=new JFrame();
    String sql = "select * from cinformation";
	query(sql);
	
    table.setPreferredScrollableViewportSize(new Dimension(400,80));
    //table.setCellSelectionEnabled(true);//使得表格的选取是以cell为单位,而不是以列为单位.若你没有写此行,则在选取表格数据时以整列为单位.
    selectionMode=table.getSelectionModel();//取得table的ListSelectionModel.
    selectionMode.addListSelectionListener(this);
    JScrollPane s=new JScrollPane(table);
    
    JPanel panel=new JPanel();
    addButton=new JButton("增加课程信息");
    panel.add(addButton);
    addButton.addActionListener(this);
    alterButton=new JButton("修改课程信息");
    panel.add(alterButton);
    alterButton.addActionListener(this);
    deleteButton=new JButton("删除课程信息");  
    panel.add(deleteButton);
    deleteButton.addActionListener(this);
    fresh=new JButton("刷新"); 
    panel.add(fresh);
    fresh.addActionListener(this);
   
    label=new JLabel("你选取:");
   
    Container contentPane=f.getContentPane();
    contentPane.add(panel,BorderLayout.NORTH);
    contentPane.add(s,BorderLayout.CENTER);
    contentPane.add(label,BorderLayout.SOUTH);

    f.setTitle("课程信息");
    f.pack();
    f.setBounds(400, 200, 800, 300);
    f.setVisible(true);
    f.addWindowListener(new WindowAdapter() {
                        public void windowClosing(WindowEvent e) {
                         // System.exit(0);
                        	f.dispose();
                        }
                      });
}
/*处理按钮事件,利用ListSelectionModel界面所定义的setSelectionMode()方法来设置表格选取模式.*/
public void actionPerformed(ActionEvent e){
    if (e.getSource() == addButton){
      //selectionMode.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    	System.out.println("1111");
    	new AddCourse();
    }
    if (e.getSource() == alterButton){
      //selectionMode.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    	System.out.println("1111");
    	if(table.getSelectedRow()==-1)
		{     JFrame newFrame=new JFrame();
			 JOptionPane.showMessageDialog(newFrame.getContentPane(),"请选中一行!!", "系统信息", JOptionPane.INFORMATION_MESSAGE);
		}
    	else{
    	int row=table.getSelectedRow();//返回选定的行的索引，第几行
        String x="",y="",w="",u="";
        u=(String)table.getValueAt(row,2);	
    	x=(String)table.getValueAt(row,0);
    	y=(String)table.getValueAt(row,1);
    	//String z=(String)currow.elementAt(0);
    	//int z=rows.size();
    	//System.out.println(z);
    	w=(String)table.getValueAt(row,6);
        new AlterCourse(x,y,u,w);
    	}
    }
    if (e.getSource() == deleteButton){
      //selectionMode.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    	if(table.getSelectedRow()==-1){
    		JFrame newFrame=new JFrame();
			JOptionPane.showMessageDialog(newFrame.getContentPane(),"请选中一行!", "系统信息", JOptionPane.INFORMATION_MESSAGE);
    	}
    	else{
    		int row=table.getSelectedRow();//返回选定的行的索引，第几行
        	//int column=table.getSelectedColumn();//返回选定的列的索引，第几
        	String currentvalue="";
        	currentvalue=(String)table.getValueAt(row,0);
    	    int option = JOptionPane.showConfirmDialog(null,"信息删除后无法复原，是否删除？", "删除信息", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE, null);
    		     switch (option) {
    		     case JOptionPane.YES_OPTION: {
    		    	 String sql = "delete from course where cid='"+currentvalue+"'";
    		     	 update(sql);
    		     	 String sql2="select * from course";
    		     	 query(sql2);
    		      break;
    		     }
    		     case JOptionPane.NO_OPTION:{
    		    	 //setDefaultCloseOperation(JOptionPane.DISPOSE_ON_CLOSE);
    		    	 break;
    		    	 }

    		     }
        }

    }
    
    if (e.getSource() == fresh){
    	String sql = "select * from cinformation";
    	query(sql);
    }
    
    
   
}

/*当用户选取表格数据时会触发ListSelectionEvent,我们实现ListSelectionListener界面来处理这一事件.ListSelectionListener界面只定义一个方法,那就是valueChanged().*/  
public void valueChanged(ListSelectionEvent el){
    String tempString="";
//JTable的getSelectedRows()与getSelectedColumns()方法会返回已选取表格cell的index Array数据.
    int[] rows=table.getSelectedRows();
    int[] columns=table.getSelectedColumns();
//JTable的getValueAt()方法会返回某行的cell数据,返回值是Object数据类型,因此我们要自行转成String数据类型.
    for (int i=0;i<rows.length;i++){
      for (int j=0;j<columns.length;j++)
        tempString = tempString+" "+(String)table.getValueAt(rows[i], columns[j]);
    }
    label.setText("你选取:"+tempString);
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


@SuppressWarnings("unchecked")
private void displayResultSet(ResultSet rs) throws SQLException {
rows = new Vector<Vector<String>>();
try {
rsmd = rs.getMetaData(); //获取数据集属性名
while (rs.next()) {
currow = new Vector<String>();
for (int i = 1; i <= rsmd.getColumnCount(); ++i) {
currow.addElement(rs.getString(i));
}
rows.addElement(currow);
}
/*
table = new JTable(rows, colum);
add(table, BorderLayout.CENTER);
table.setVisible(true);
table.setRowHeight(50);
*/
} catch (SQLException e1) {
e1.printStackTrace();
}
@SuppressWarnings("rawtypes")
Vector name= new Vector();
String[] y={"编号","课程名","授课老师编号","授课老师","学期","专业","课时"};
for(int i=0;i<7;i++){
name.addElement(y[i]);
}
table=new JTable(rows,name);
table.revalidate();//Jtable刷新



}
/*
public static void main(String[] args){
    new Showteacher();
}
*/
}