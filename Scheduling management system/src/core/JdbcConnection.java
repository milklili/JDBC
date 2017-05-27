package core;
     import java.sql.Connection;
     import java.sql.DriverManager;
     import java.sql.ResultSet;
     import java.sql.SQLException;
     import java.sql.Statement;

public class JdbcConnection {
	//数据库驱动名称
	private static String DRIVER_NAME =
	"com.microsoft.sqlserver.jdbc.SQLServerDriver";
	//数据库连接地址；本实例中数据名称为sales
	private static String DATABASE_URL
	="jdbc:sqlserver://localhost:1433;DatabaseName=CourseManagement";
	//数据库用户名称
	private static String DATABASE_USERNAME = "aa";
	//数据库密码
	private static String DATABASE_PASSWORD = "123";
	//数据库连接
	private static Connection connection;
	//SQL命令对象
	private static Statement statement;
	//数据查询结果
	private static ResultSet resultSet;
	//获取数据库连接
	public static Connection getJdbcConnection() throws SQLException
	{
	if (connection == null || connection.isClosed())
	{
	try
	{
	//加载驱动程序
	Class.forName(DRIVER_NAME);
	//获取数据库连接
	connection = DriverManager.getConnection(DATABASE_URL,
	DATABASE_USERNAME, DATABASE_PASSWORD);
	} catch (ClassNotFoundException e)
	{
	System.err.println("装载 JDBC驱动程序失败。 " );
	e.printStackTrace();
	}
	catch (SQLException e)
	{
	System.err.println( "无法连接数据库" );
	e.printStackTrace();
	} }
	return connection;
	}
	//查询操作
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
	// update操作
	public static int executeUpdate(String sql)
	{
	int count = 0; //受影响数据行数
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
	//关闭数据库
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
