package core;
     import java.sql.Connection;
     import java.sql.DriverManager;
     import java.sql.ResultSet;
     import java.sql.SQLException;
     import java.sql.Statement;

public class JdbcConnection {
	//���ݿ���������
	private static String DRIVER_NAME =
	"com.microsoft.sqlserver.jdbc.SQLServerDriver";
	//���ݿ����ӵ�ַ����ʵ������������Ϊsales
	private static String DATABASE_URL
	="jdbc:sqlserver://localhost:1433;DatabaseName=CourseManagement";
	//���ݿ��û�����
	private static String DATABASE_USERNAME = "aa";
	//���ݿ�����
	private static String DATABASE_PASSWORD = "123";
	//���ݿ�����
	private static Connection connection;
	//SQL�������
	private static Statement statement;
	//���ݲ�ѯ���
	private static ResultSet resultSet;
	//��ȡ���ݿ�����
	public static Connection getJdbcConnection() throws SQLException
	{
	if (connection == null || connection.isClosed())
	{
	try
	{
	//������������
	Class.forName(DRIVER_NAME);
	//��ȡ���ݿ�����
	connection = DriverManager.getConnection(DATABASE_URL,
	DATABASE_USERNAME, DATABASE_PASSWORD);
	} catch (ClassNotFoundException e)
	{
	System.err.println("װ�� JDBC��������ʧ�ܡ� " );
	e.printStackTrace();
	}
	catch (SQLException e)
	{
	System.err.println( "�޷��������ݿ�" );
	e.printStackTrace();
	} }
	return connection;
	}
	//��ѯ����
	public static ResultSet executeQuery(String sql) {
	try
	{
	statement = getJdbcConnection().createStatement();
	resultSet = statement.executeQuery(sql);
	}
	catch (SQLException e)
	{
	System.out.println(e);
	}
	return resultSet;
	}
	// update����
	public static int executeUpdate(String sql)
	{
	int count = 0; //��Ӱ����������
	try
	{
	statement = getJdbcConnection().createStatement();
	count = statement.executeUpdate(sql);
	}
	catch (SQLException e)
	{
	System.out.println(e);
	}
	return count;
	}
	//�ر����ݿ�
	public static void close()
	{
	if (resultSet != null)
	{
	try
	{
	resultSet.close();
	}
	catch (SQLException e)
	{
	e.printStackTrace();
	}
	}
	if (statement != null)
	{
	try
	{
	statement.close();
	}
	catch (SQLException e)
	{
	e.printStackTrace();
	} }
	if (connection != null)
	{
	try
	{
	connection.close();
	} catch (SQLException e)
	{ e
	.printStackTrace();
	}
	}
	}
}
