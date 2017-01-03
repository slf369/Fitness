package eg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

public class JdbcUtils {
	
	private static String user = "qc_dev";
	private static String password = "712$%^jxj0";
	private static String dbName = "quancheng_project";
	private static  String url = "jdbc:mysql://db003.qc.com:3306/"+dbName+"?user="+user+"&password="+password+"&useUnicode=true&characterEncoding=gb2312";
	
	private static DataSource dataSource = null;
	
	/**
	 * ��������
	 */
	static{
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}catch(Exception e){
			System.out.println("Exception:"+e.getMessage()+"");
			throw new ExceptionInInitializerError(e);
		}
	}
	
	private JdbcUtils(){
	}
	
	/**
	 * ��ȡ����
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException{
		return DriverManager.getConnection(url);
	}
	
	public static DataSource getDataSource(){
		return dataSource;
	}
	
	/**
	 * �ͷ���Դ
	 * @param rs
	 * @param st
	 * @param conn
	 */
	public static void free(ResultSet rs,Statement st,Connection conn){
		try{
			if(rs != null){
				rs.close();
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try{
				if(st != null){
					st.close();
				}
			}catch(SQLException e){
				e.printStackTrace();
			}finally{
				try{
					if(conn != null){
						conn.close();
					}
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
		}
			
	}

}