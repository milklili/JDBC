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
JLabel label=null;//��ʾ�û�ѡȡ���֮��
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
    //table.setCellSelectionEnabled(true);//ʹ�ñ���ѡȡ����cellΪ��λ,����������Ϊ��λ.����û��д����,����ѡȡ�������ʱ������Ϊ��λ.
    selectionMode=table.getSelectionModel();//ȡ��table��ListSelectionModel.
    selectionMode.addListSelectionListener(this);
    JScrollPane s=new JScrollPane(table);
    
    JPanel panel=new JPanel();
    addButton=new JButton("���ӿγ���Ϣ");
    panel.add(addButton);
    addButton.addActionListener(this);
    alterButton=new JButton("�޸Ŀγ���Ϣ");
    panel.add(alterButton);
    alterButton.addActionListener(this);
    deleteButton=new JButton("ɾ���γ���Ϣ");  
    panel.add(deleteButton);
    deleteButton.addActionListener(this);
    fresh=new JButton("ˢ��"); 
    panel.add(fresh);
    fresh.addActionListener(this);
   
    label=new JLabel("��ѡȡ:");
   
    Container contentPane=f.getContentPane();
    contentPane.add(panel,BorderLayout.NORTH);
    contentPane.add(s,BorderLayout.CENTER);
    contentPane.add(label,BorderLayout.SOUTH);

    f.setTitle("�γ���Ϣ");
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
/*����ť�¼�,����ListSelectionModel�����������setSelectionMode()���������ñ��ѡȡģʽ.*/
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
			 JOptionPane.showMessageDialog(newFrame.getContentPane(),"��ѡ��һ��!!", "ϵͳ��Ϣ", JOptionPane.INFORMATION_MESSAGE);
		}
    	else{
    	int row=table.getSelectedRow();//����ѡ�����е��������ڼ���
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
			JOptionPane.showMessageDialog(newFrame.getContentPane(),"��ѡ��һ��!", "ϵͳ��Ϣ", JOptionPane.INFORMATION_MESSAGE);
    	}
    	else{
    		int row=table.getSelectedRow();//����ѡ�����е��������ڼ���
        	//int column=table.getSelectedColumn();//����ѡ�����е��������ڼ�
        	String currentvalue="";
        	currentvalue=(String)table.getValueAt(row,0);
    	    int option = JOptionPane.showConfirmDialog(null,"��Ϣɾ�����޷���ԭ���Ƿ�ɾ����", "ɾ����Ϣ", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE, null);
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

/*���û�ѡȡ�������ʱ�ᴥ��ListSelectionEvent,����ʵ��ListSelectionListener������������һ�¼�.ListSelectionListener����ֻ����һ������,�Ǿ���valueChanged().*/  
public void valueChanged(ListSelectionEvent el){
    String tempString="";
//JTable��getSelectedRows()��getSelectedColumns()�����᷵����ѡȡ���cell��index Array����.
    int[] rows=table.getSelectedRows();
    int[] columns=table.getSelectedColumns();
//JTable��getValueAt()�����᷵��ĳ�е�cell����,����ֵ��Object��������,�������Ҫ����ת��String��������.
    for (int i=0;i<rows.length;i++){
      for (int j=0;j<columns.length;j++)
        tempString = tempString+" "+(String)table.getValueAt(rows[i], columns[j]);
    }
    label.setText("��ѡȡ:"+tempString);
}


private void query(String sql){
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


@SuppressWarnings("unchecked")
private void displayResultSet(ResultSet rs) throws SQLException {
rows = new Vector<Vector<String>>();
try {
rsmd = rs.getMetaData(); //��ȡ���ݼ�������
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
String[] y={"���","�γ���","�ڿ���ʦ���","�ڿ���ʦ","ѧ��","רҵ","��ʱ"};
for(int i=0;i<7;i++){
name.addElement(y[i]);
}
table=new JTable(rows,name);
table.revalidate();//Jtableˢ��



}
/*
public static void main(String[] args){
    new Showteacher();
}
*/
}