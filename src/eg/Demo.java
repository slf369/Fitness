package eg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Demo {
	
	public static void main(String[] args) throws Exception{
		//���Դ��룺
		test();
		//��׼�淶���룺
		template();
	}
	
	//ģ�����
	public static void template(){
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			//�������
			st = conn.createStatement();
			//ִ�����
			rs = st.executeQuery("select * from user");
			//������
			while(rs.next()){
				System.out.println(rs.getObject(1) + "\t" + rs.getObject(2) + "\t" + rs.getObject(3) + "\t");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			JdbcUtils.free(rs, st, conn);
		}
	}
	
	//����
	static void test() throws Exception{
//		//ע������
//		DriverManager.registerDriver(new com.mysql.jdbc.Driver());
//		//ͨ��ϵͳ������ע������
//		System.setProperty("jdbc.drivers","");
//		//��̬��������
//		Class.forName("com.mysql.jdbc.Driver");
		
		//��������
		String url = "jdbc:mysql://db003.qc.com:3306/quancheng_product";
		String userName = "qc_dev";
		String password = "712$%^jxj0";
		Connection conn = DriverManager.getConnection(url,userName,password);
		
		//�������
		Statement st = conn.createStatement();
		
		//ִ�����
		ResultSet rs = st.executeQuery("select * from product_type");
		
		//������
		while(rs.next()){
			System.out.println(rs.getObject(1) + "\t" + rs.getObject(2) + "\t" + rs.getObject(3) + "\t");
		}
		
		//�ͷ���Դ
		rs.close();
		st.close();
		conn.close();
	}

}