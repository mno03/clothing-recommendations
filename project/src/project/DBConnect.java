package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


//db를 연결하는 클래스.
public class DBConnect {
	public static Connection getConnection() { // 연결해서 커넥션 객체를 반환하는 함수다. static으로 만들었다.
		Connection con=null; // =null 이게 initialize다. 그냥 선언만 하면 con을 생성하는 게 try안에 들어가 있어서 return쪽에 빨간줄. 이게 나은 듯..
		String url = "jdbc:mysql://localhost:3306/project";
		String id = "root";
		String pw = "1234";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url,id,pw);
			System.out.println("접속 성공");
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("접속 실패");
		} finally {
			
		}
		return con;
	}
	
	// 닫아주는 메서드. 커넥션과 스테이트먼트를 받아서 그게 null이 아니라면 닫는다.
	public static void close(Connection con, Statement stmt) {
		try {
			if(con!=null) { // con이 null이 아니라면, 즉 con이 연결돼있다면.
				con.close();
			}
			if(stmt!=null) { // stmt도 null이 아니라면.
				stmt.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// select를 하게 되는 경우 ResultSet이 생기니깐, 비슷하게 rs도 닫아주는 메서드 하나 더 만들어주고.
	public static void close(ResultSet rs, Connection con, Statement stmt) {
		try {
			if(con!=null) { // con이 null이 아니라면, 즉 con이 연결돼있다면.
				con.close();
			}
			if(stmt!=null) { // stmt도 null이 아니라면.
				stmt.close();
			}
			if(rs!=null) { // rs도 null이 아니라면.
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}