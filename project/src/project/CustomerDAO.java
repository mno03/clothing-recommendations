package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
public class CustomerDAO {
	
	private CustomerDAO() {}
	private static CustomerDAO instance = new CustomerDAO();
	
	public static CustomerDAO getInstance() {
		return instance;
	}
	
	public static int insertProfile(CustomerDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			con = DBConnect.getConnection();
			String sql = "insert into profile values(?,?,?,?,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getId());
			pstmt.setString(3, dto.getPassword());
			pstmt.setString(4, dto.getSex());
			pstmt.setDouble(5, dto.getHeight());
			pstmt.setDouble(6, dto.getShoulder());
			pstmt.setDouble(7, dto.getChest());
			pstmt.setDouble(8, dto.getWaist());
			pstmt.setDouble(9, dto.getLeg());
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("삽입오류");
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				System.out.println(e);
				System.out.println("닫기 오류");
			}
		}
		return result;
	}
	
	public int idPass(String id, String password) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DBConnect.getConnection();
			String sql = "select * from profile where id=? and password=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return 1;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}


