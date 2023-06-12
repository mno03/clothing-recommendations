package project;


import java.sql.*;
import java.util.*;

public class Login2DAO {
	
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private final String Login = "select * from profile where id = ? and password = ?";
	
	private Login2DAO() {}
	private static Login2DAO instance = new Login2DAO();
	
	public static Login2DAO getInstance() {
		return instance;
	}
	
	
	public int idPW(String id, String password) {
		con = DBConnect.getConnection();
		
		try {
			pstmt = con.prepareStatement(Login);

			
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return 1;
			}
			
		}catch(Exception e) {
			e.getStackTrace();
		}
		return -1;
	}
	
}
