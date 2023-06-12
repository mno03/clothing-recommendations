package project;

import java.io.File;

public class test {
	public static void main(String[] args) {
		String[] all_column = { "번호", "이름", "경로", "대분류", "중분류", "브랜드", "성별", "색상", "사이즈", "상의총장", "어깨너비", "가슴단면",
				"소매길이", "하의총장", "허리단면", "엉덩이단면", "허벅지단면", "밑위", "밑단단면" };

//		// 특정 테이블에서 (컬럼=값)인 조건에 해당하는 아이템들의 모든 속성을 로딩하는 메서드.
//		public Vector selectItem(String table, String column, String value) {
//			table="item";
//			Vector item = sql(all_column.length, "select", "번호", "이름", "경로", "대분류", "중분류", "브랜드", "성별", "색상", "사이즈", "상의총장", "어깨너비", "가슴단면", "소매길이",
//					"하의총장", "허리단면", "엉덩이단면", "허벅지단면", "밑위", "밑단단면", "from "+table+" where "+column+"="+value+";");
//			return item;
//		}

		JunhwanItemDAO dao = new JunhwanItemDAO();
//		update item set 번호=null where 번호=-1.1; 

//		select 번호, 상의총장 from item where 브랜드='아디다스';
//		dao.sql("update item set 번호=null where 번호=-1.1;");

//		String table = "profile";
//		String name = "홍길동";
//		dao.sql("delete from "+table+" where 이름='"+name+"';");

//		Table item = DAO.sql(2,"select", "속성", "단위", "from unit;");
//		Table item = DAO.sql(4, "select", "번호", "이름", "경로", "대분류", "from item where 브랜드='아디다스';");

//		Table item = DAO.sql(2, "select", "총장","총장", "from item;");
//		Table item = DAO.sql(2, "select", "id", "password", "from profile where id='junan8733' and password='134';");
		dao.updateNull();

//		HashMap i = (HashMap) item.get(0);
//		Vector brand = dao.selectBrandList();
//		String k = (String) i.get("경로");
//		System.out.println(item);
//		System.out.println(item.getString(0, "단위").equals(""));
//		int index = item.searchIndex("이름", "우알롱");
//		String k = item.getString(index, "경로");

//		File k = new File("C:\\java\\files\\project\\images\\Collar Sweats1.png");
//		
//		System.out.println(k.exists());

	}
}
