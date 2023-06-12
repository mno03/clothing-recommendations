package project;

import java.sql.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JunhwanItemDAO {
	
	// 브랜드 선택창 : brand 테이블에서 브랜드 이름과 브랜드 사진 경로, transparent사진 경로를 Table로 가져오는 메서드.
	public Table getBrandTable() {
		System.out.println("getBrandTable()");
		Table brandTable = sql(3, "select", "이름","경로", "transparent경로", "from brand;");
		return brandTable;
	}
	
	
	// 의류 선택창 : item 테이블에서 아이템 이름과 사진 경로, 대분류를 Table로 가져오는 메서드.
	public Table getClothPageTable(String brandName) {
		System.out.println("getClothPageTable(String brandName)");
		Table clothPageTable = sql(2, "select distinct", "이름", "대분류", "from item where 브랜드='"+brandName+"';");
		return clothPageTable;
	}
	
	// 의류 상세 페이지창 :
	public Table getProfile(String id) {
		System.out.println("getProfile(String id)");
		Table profile=sql(7,"select", "name","sex","height","shoulder","chest","waist","leg", "from profile where id='"+id+"';");
		return profile;
	}

	// 옷 가져오기. 옷이 상의인지 하의인지 찾아서 그 옷의 다른 사진들 경로와 정보를 담은 Table을 반환한다.
	public Table getCloth(String id, String category, String clothName) {
		
		switch(category) {
		case "상의":
			System.out.println("상의 : getCloth(String category, String clothName, String clothUrl)");
			Table top=sql(10,"select", "번호", "대분류", "중분류", "성별", "사이즈", "색상", "상의총장", "어깨너비", "가슴단면", "소매길이",
					"from item where 이름='"+clothName+"' "
							+ "and not (어깨너비<(select shoulder from profile where id='"+id+"') "
									+ "or 상의총장>(select height*0.36 from profile where id='"+id+"')+12) "
											+ "order by 어깨너비 limit 1;");
			return top;
		case "하의":
			System.out.println("하의 : getCloth(String category, String clothName, String clothUrl)");
			Table bottom=sql(12,"select", "번호", "대분류", "중분류", "성별", "사이즈", "색상", "하의총장", "허리단면", "엉덩이단면", "허벅지단면", "밑위", "밑단단면",
					"from item where 이름='"+clothName+"' "
							+ "and not (허리단면*2<(select waist from profile where id='"+id+"') "
									+ "or 하의총장>(select height*0.51 from profile where id='"+id+"')+20) "
											+ "order by 허리단면 limit 1;");
			return bottom;
		default:
			Table item = new Table();
			System.out.println("대분류가 다릅니다."); return item;
		}
		
	}	
	
	// DB에서 단위 Table 가져오기.
	public Table getUnit() {
		System.out.println("getUnit()");
		Table unit=sql(2,"select", "속성","단위","from unit;");
		return unit;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////

	// -1.1공란을 Null로 바꿔주는 메서드.
	String[] all_column = { "번호", "이름", "대분류", "중분류", "브랜드", "성별", "색상", "사이즈", "상의총장", "어깨너비", "가슴단면", "소매길이",
			"하의총장", "허리단면", "엉덩이단면", "허벅지단면", "밑위", "밑단단면" };
	public void updateNull() {
		for (String i : all_column) {
			try {
				sql("update item set " + i + "=null where " + i + "=-1.1");
			} catch (Exception e) {
				System.out.println(e);
				continue;
			}
			
		}
	}
	// select문을 제외한 sql문을 서버로 날려주는 메서드.
	
//	사용 예시.
	
//	ItemDAO dao = new ItemDAO();
	
//	String table = "profile";
	
//	String name = "홍길동";
	
//	dao.sql("delete from "+table+" where 이름='"+name+"';");
	
//	=> profile 테이블에서 이름이 홍길동인 것들을 찾아 삭제한다.
	
	public static void sql(String... s) {
		Connection con = null;
		PreparedStatement pstmt = null;

		int ret = 0;
		try {

			con = DBConnect.getConnection(); // db 연결.

			String sql = "";
			for (String i : s)
				sql += i + " ";
			
			if(sql.contains("\\")) sql= sql.replace("\\", "\\\\"); // sql에 경로 입력시 제대로 입력해주기 위한 변환.
			
			System.out.println("명령문 : "+sql);
			pstmt = con.prepareStatement(sql);

			ret = pstmt.executeUpdate(); // 실행! 성공 1, 실패면 0이나 작은 값.
			if (ret < 1) {
				System.out.println("수행 오류.");
			} else {
				System.out.println("명령이 수행되었습니다.");
			}

		} catch (Exception e) {
			System.out.println("수행 오류.");
			e.printStackTrace();
		} finally {
			DBConnect.close(con, pstmt); // 다 닫아주는 메서드 호출.
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// select문을 서버로 날려주는 메서드. **첫번째 파라미터로 가져오는 컬럼 종류의 개수를 알려줘야한다.
	
//	사용 예시.
	
//	ItemDAO dao = new ItemDAO();
	
//	Vector item = dao.sql(4, "select", "번호", "이름", "경로", "대분류", "from item where 브랜드='아디다스';");
	
//	=> item 테이블에서 브랜드가 아디다스인 것들의 번호, 이름, 경로, 대분류 (총 4가지 속성)을 가져와서
	
//	   row를 가진 Table 객체로 반환한다.
	
	public static Table sql(int num, String... s) {
		Connection con = null;
		PreparedStatement pstmt = null;
		Table result = new Table();
		ResultSet rs = null;

		try {

			con = DBConnect.getConnection(); // db 연결.

			String sql = "";
			sql += s[0] + " ";
			if (num >= 2) {
				for (int k = 1; k < num; k++) {
					sql += s[k] + ", ";
				}
			}

			for (int n = num; n < s.length; n++) {
				sql += s[n] + " ";
			}
			if(sql.contains("\\")) sql= sql.replace("\\", "\\\\"); // sql에 경로 입력시 제대로 입력해주기 위한 변환.
			
			System.out.println("명령문 : "+sql);
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery(); // 실행! 성공 1, 실패면 0이나 작은 값.
			
			while (rs.next()) {
				HashMap row = new HashMap();

				for (int j = 1; j < num + 1; j++) {
//					if(!(rs.getString(s[j])==null))	row.put( s[j] , rs.getString(s[j]).trim());
//					else // 불러올 때의 값 그대로여야 그 값을 이용해 다시 sql을 조회할 수 있다. trim안함.
						row.put( s[j] , rs.getString(s[j]));
				}

				result.add(row);
			}

		} catch (Exception e) {
			System.out.println("수행 오류.");
			e.printStackTrace();
		} finally {
			DBConnect.close(con, pstmt); // 다 닫아주는 메서드 호출.
		}
		System.out.println(result);
		System.out.println();
		return result;
	}
}

	///////////////////////////////////////////////////////////////////////////////////////////////////////

// Table 클래스. (메서드 조금 가진 것 말고는 벡터랑 같음.)
class Table extends Vector{
	
	// row를 가진 Table 알맹이에 접근하는 메서드들.
	
//	사용 예시.
	
//	sql문을 통해 받아온 Table item.
	
//	String property = item.getString(0, "이름");
	
//	=> 받아온 item Table의 인덱스 0번째 row에 속한 "이름" 속성값을 String property로 정한다.	
	
	// 데이터형이 스트링인 것.
	public String getString(int rowNumber, String property) {
		try {
			HashMap row = (HashMap) get(rowNumber);
			String item = (String) row.get(property);
			return item;
		} catch (Exception e) {System.out.println("인덱스가 없거나 컬럼명이 잘못되었습니다.");return "";}
	}

	// 데이터형이 더블인 것.
	public double getDouble(int rowNumber, String property) {
		try {
			HashMap row = (HashMap) get(rowNumber);
			double item = Double.parseDouble((String) row.get(property));
			return item;
		} catch (Exception e) {System.out.println("인덱스가 없거나 컬럼명이 잘못되었습니다.");return -1.0;}
	}

	// 데이터형이 int인 것.
	public int getInt(int rowNumber, String property) {
		try {
			HashMap row = (HashMap) get(rowNumber);
			int item = Integer.parseInt((String) row.get(property));
			return item;
		} catch (Exception e) {System.out.println("인덱스가 없거나 컬럼명이 잘못되었습니다.");return -1;}
	}
	
	// 특정 (컬럼=값)인 줄의 인덱스 번호를 찾아주는 메서드.
	public int searchIndex(String WhereColumn, String WhereValue) {
		for(int i=0;i<size();i++) {
			if(WhereValue.equals(getString(i, WhereColumn))) return i;
		} System.out.println("조건에 맞는 인덱스가 없습니다."); return -1;
	}
	public int searchIndex(String WhereColumn, double WhereValue) {
		for(int i=0;i<size();i++) {
			if(WhereValue == getDouble(i, WhereColumn)) return i;
		} System.out.println("조건에 맞는 인덱스가 없습니다."); return -1;
	}
	public int searchIndex(String WhereColumn, int WhereValue) {
		for(int i=0;i<size();i++) {
			if(WhereValue == getInt(i, WhereColumn)) return i;
		} System.out.println("조건에 맞는 인덱스가 없습니다."); return -1;
	}
		
	public Table() {super();}
	public Table(Collection c) {super(c);}
	public Table(int initialCapacity, int capacityIncrement) {super(initialCapacity, capacityIncrement);}
	public Table(int initialCapacity) {super(initialCapacity);}
	
}

