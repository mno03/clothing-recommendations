package project;


import java.sql.*;
import java.util.*;

public class SearchDAO {
	
	public Vector getProfile() {
		Vector data = new Vector();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DBConnect.getConnection();
			String sql = "select * from profile order by name asc";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String name = rs.getString("name");
				String id = rs.getString("id");
				String password = rs.getString("password");
				String gender = rs.getString("sex");
				double height = rs.getDouble("height");
				double shoulder = rs.getDouble("shoulder");
				double chest = rs.getDouble("chest");
				double waist = rs.getDouble("waist");
				double leg = rs.getDouble("leg");
				
				Vector row = new Vector();
				row.add(name);
				row.add(id);
				row.add(password);
				row.add(gender);
				row.add(height);
				row.add(shoulder);
				row.add(chest);
				row.add(waist);
				row.add(leg);
				data.add(row);
			}
					
		}catch(Exception e) {
			e.getStackTrace();
		}finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(con != null) {
					con.close();
				}
			}catch(Exception e) {
				
			}
		}
		return data;
	}
	
	public int insertProfile(SearchDTO dto) {
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
			pstmt.setString(4, dto.getGender());
			pstmt.setDouble(5, dto.getHeight());
			pstmt.setDouble(6, dto.getShoulder());
			pstmt.setDouble(7, dto.getChest());
			pstmt.setDouble(8, dto.getWaist());
			pstmt.setDouble(9, dto.getLeg());
			
			result = pstmt.executeUpdate();
			
		}catch(Exception e) {
			System.out.println("삽입 오류");
		}finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(con != null) {
					con.close();
				}
			}catch(Exception e){
				
			}
		}
		return result;
		
	}
	
	public int deleteProfile(SearchDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			con = DBConnect.getConnection();
			String sql = "delete from profile where name=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getName());
//			pstmt.setString(2, dto.getId());
//			pstmt.setString(3, dto.getGender());
			result = pstmt.executeUpdate();
			
		}catch (Exception e) {
			
		}finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(con != null) {
					con.close();
				}
			}catch(Exception e) {
				
			}
		}
		return result;
	}
	
	public int updateProfile(SearchDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			con = DBConnect.getConnection();
			String sql = "update profile set name=?,password=?,sex=?,height=?,shoulder=?,chest=?,waist=?,leg=? where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getPassword());
			pstmt.setString(3, dto.getGender());
			pstmt.setDouble(4, dto.getHeight());
			pstmt.setDouble(5, dto.getShoulder());
			pstmt.setDouble(6, dto.getChest());
			pstmt.setDouble(7, dto.getWaist());
			pstmt.setDouble(8, dto.getLeg());
			pstmt.setString(9, dto.getId());
			
			result = pstmt.executeUpdate();
			
		}catch(Exception e) {
			System.out.println(e);
		}finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(con != null) {
					con.close();
				}
			}catch(Exception e) {
				
			}
		}
		return result;
	}


}


