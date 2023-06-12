package project;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

class ClothSize extends JFrame {
	JunhwanItemDAO dao = new JunhwanItemDAO();
	Table user;
	Table chosenCloth;
	Table unit;
	
	
	String id = "", brandName = "", transparentBrandUrl = "", category="", clothName = "", clothUrl = "";

	public ClothSize(String[] args) {
		try {
			id = args[0];
			brandName = args[1];
			transparentBrandUrl = args[2];
			category = args[3];
			clothName = args[4];
			clothUrl = args[5];

		} catch (Exception e) {
			System.out.println("args를 다 받지 못했습니다.");
		}

		setLayout(null);

		HeaderPanel header = new HeaderPanel();
		BodyPanel body = new BodyPanel();
		
		add(header);
		add(body);
		
		setTitle("사이즈 추천");
		setSize(1400, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(null);
		setResizable(false);

	}
	
	class HeaderPanel extends JPanel{
		public HeaderPanel() {
			setLayout(null);
			setBounds(0, 0, 1400, 120);
			setBackground(new Color(220, 220, 220));
						
			// 뒤로 가기 버튼.
			ImageIcon originicon = new ImageIcon("backbutton.png"); // 뒤로 가기 이미지.
			Image img = originicon.getImage();
			Image chngimg = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH); //이미지 크기조절
			ImageIcon icon = new ImageIcon(chngimg);
			JButton back = new JButton(icon);
			back.setBounds(1305, 20 , 30, 30);
			back.addMouseListener(new MouseAdapter() { // 뒤로가기 마우스 리스너.
				@Override
				public void mouseReleased(MouseEvent e) {
					String[] args={id, brandName, transparentBrandUrl};
					ClothPage.main(args);
					dispose();
				}
			});
			
			// 브랜드 이미지.
			JLabel BrandImage = new JLabel("");	
			try {
				ImageIcon originbricon = new ImageIcon(transparentBrandUrl); // 넘겨받은 브랜드 투명이미지 주소.
				Image brimg = originbricon.getImage();
				Image chngbrimg = brimg.getScaledInstance(100, 100, Image.SCALE_SMOOTH); //이미지 크기조절
				ImageIcon bricon = new ImageIcon(chngbrimg);
				BrandImage.setIcon(bricon);
			} catch (Exception e) {
				System.out.println("넘겨받은 브랜드 이미지 경로가 없습니다.");
				System.out.println(e);
			} 
			BrandImage.setBounds(70, 10, 100, 100);	
			
			// 브랜드 이름.
			try {
				JLabel brand = new JLabel(brandName); // header부분 JLabel에 브랜드 이름 삽입.
				brand.setBounds(200, 40, 1000, 60);
				brand.setVerticalAlignment(SwingConstants.BOTTOM); // 글씨가 아래로 정렬 중인 상태.
				brand.setFont(new Font("Gothic", Font.BOLD, 50));
				add(brand);
			} catch (Exception e2) {
				System.out.println("넘겨받은 브랜드 이름이 없습니다.");
				System.out.println(e2);
			} 
			add(back);
			add(BrandImage);
		}
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			ImageIcon icon = new ImageIcon("clothsizeheader.png");
			Image img = icon.getImage();
			g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
		}
	}
	
	class BodyPanel extends JPanel{
		int y=0;
		JLabel imgLabel = new JLabel();
		
		public BodyPanel() {
			
			setLayout(null);
			
			setBounds(0, 0, 1400, 800);
			setBackground(Color.gray);
			
			ProfilePanel profile = new ProfilePanel();
			ClothPanel property = new ClothPanel();
			// "Profile" 라벨.
			JLabel info = new JLabel("Profile");
			info.setBounds(100, 150, 340, 100);
			info.setFont(new Font("Serif", Font.PLAIN, 50));
			info.setHorizontalAlignment(SwingConstants.LEFT);
			info.setVerticalAlignment(SwingConstants.BOTTOM);
			info.setForeground(Color.white);
			
			// "추천사이즈" 라벨.
			JLabel size1 = new JLabel("추천 사이즈");
			size1.setBounds(980, 180, 130, 20);
			size1.setFont(new Font("Gothic", Font.BOLD, 17));
			size1.setHorizontalAlignment(SwingConstants.LEFT);
			size1.setForeground(Color.white);
			add(size1);
			
			
			// 옷 성별이 user와 다르면 알려주기.
			String notify="";
			try {
				if(chosenCloth.getString(0, "성별")==null||chosenCloth.getString(0, "성별").equals("")) notify=""; // 가져온 옷이 없으면 표시안함.
				else if(user.getString(0, "sex").equals("female")&&!chosenCloth.getString(0,"성별").contains("여")&&!chosenCloth.getString(0,"성별").contains("공용")) notify="(남성복입니다)";
				else if(user.getString(0, "sex").equals("male")&&!chosenCloth.getString(0,"성별").contains("남")&&!chosenCloth.getString(0,"성별").contains("공용")) notify="(여성복입니다)";
				else notify="";
			} catch (Exception e) {
				System.out.println("추천받은 옷이 없거나 옷의 성별값이 없습니다.");
			}
						
			// 성별 알람 라벨.
			JLabel notifyLabel = new JLabel(notify);
			notifyLabel.setBounds(1140, 180, 170, 20);
			notifyLabel.setFont(new Font("Gothic", Font.BOLD, 17));
			notifyLabel.setHorizontalAlignment(SwingConstants.LEFT);
			notifyLabel.setForeground(Color.white);
			add(notifyLabel);
			
			// 사이즈 추천 라벨.
			try {
				if(chosenCloth.getString(0, "사이즈")==null||chosenCloth.getString(0, "사이즈").equals("")) {
					JLabel size1t = new JLabel("없음"); // 추천 사이즈 가져와서 라벨에 넣기.
					size1t.setBounds(1080, 210, 220, 80);
					size1t.setFont(new Font("Gothic", Font.BOLD, 60));
					size1t.setForeground(Color.white);
					size1t.setHorizontalAlignment(SwingConstants.CENTER);
					add(size1t);
				} else {
					JLabel size1t = new JLabel(chosenCloth.getString(0, "사이즈")); // 추천 사이즈 가져와서 라벨에 넣기.
					size1t.setBounds(1080, 210, 220, 80);
					size1t.setFont(new Font("Gothic", Font.BOLD, 80));
					size1t.setForeground(Color.white);
					size1t.setHorizontalAlignment(SwingConstants.CENTER);
					add(size1t);
				}
				
			} catch (Exception e) {
				System.out.println("가져온 옷 사이즈 정보가 정확하지 않습니다.");
				System.out.println(e);
			}
						
			// 중앙에 대형 사진.
			ImageIcon icon = new ImageIcon(clothUrl);
			Image img = icon.getImage();
			Image updateImg = img.getScaledInstance(350, 420, Image.SCALE_SMOOTH);
			ImageIcon updateIcon = new ImageIcon(updateImg);
			imgLabel.setIcon(updateIcon);
			imgLabel.setBounds(503, 200, 350, 420);
			
			// 작은 이미지 버튼들 넣기.
			
			y=0;
			for(int i=1;i<=5;i++) {
				String url=clothUrl.replace("1.", i+".");
				File imageFile = new File(url);
				if(!imageFile.exists()) continue;  // 경로에 파일이 없으면 버튼을 만들지 않는다.
//				if(urllist[i]==null) continue;  // 받은 경로가 없거나, 
//				if(urllist[i].equals("")) continue; // 공스트링이어도 버튼을 만들지 않는다.
				
				ImageIcon icon1 = new ImageIcon(url);
				Image image = icon1.getImage();
				Image scaledImage = image.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
				ImageIcon scaledIcon = new ImageIcon(scaledImage);
				JButton clothButton = new JButton(scaledIcon);

				clothButton.setBounds(858, 200+y, 50, 50);
				y+=55;
							
				clothButton.addMouseListener(new MyMouseListener(url));
				add(clothButton);
			}
			// 옷 이름 라벨.
			JTextPane cloth_name = new JTextPane();
			cloth_name.setBounds(490, 630, 470, 90);
			cloth_name.setText(clothName);
			cloth_name.setFont(new Font("Gothic", Font.BOLD, 30));
			cloth_name.setForeground(Color.white);
			cloth_name.setBackground(new Color(0, 0, 0, 0));
			cloth_name.setEditable(false);
			cloth_name.setOpaque(false);
			
			
			add(info);
			add(profile);
			add(property);
			add(imgLabel);
			add(cloth_name);
		}
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			ImageIcon icon = new ImageIcon("clothsizebackground.png");
			Image img = icon.getImage();
			g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
		}
		class MyMouseListener extends MouseAdapter {
			String url="";
			public MyMouseListener(String url) {
				super();
				this.url=url;
			}
						
			public void mouseEntered(MouseEvent e) {

				ImageIcon icon = new ImageIcon(url);
				Image img = icon.getImage();
				Image updateImg = img.getScaledInstance(350, 420, Image.SCALE_SMOOTH);
				ImageIcon updateIcon = new ImageIcon(updateImg);
				imgLabel.setIcon(updateIcon);
	
			}
			public void mouseExited(MouseEvent e) {

				ImageIcon icon = new ImageIcon(clothUrl);
				Image img = icon.getImage();
				Image updateImg = img.getScaledInstance(350, 420, Image.SCALE_SMOOTH);
				ImageIcon updateIcon = new ImageIcon(updateImg);
				imgLabel.setIcon(updateIcon);
	
			}
		}	
	}
	
	class ProfilePanel extends JPanel{
		public ProfilePanel() {
			
			user=dao.getProfile(id); // 사용자 정보 db 가져오기.
			unit=dao.getUnit(); // 단위 db 가져오기.
			
			setLayout(null);
			
			setBounds(80, 255, 350, 450);			
			setBackground(new Color(2, 115, 94));
			
			// 사용자 정보 띄우기.
			try {
				String[] top = {"name","sex","height","shoulder","chest"};
				String[] bottom = {"name","sex","height","waist","leg"};
				String[] choose= {};
				
				switch(category) {
				case "상의": {choose=top; break;}
				case "하의": {choose=bottom; break;}
				default: System.out.println("대분류가 정확하지 않습니다.");
				}				
				
				TextPanel.refresh();
				int start_y=20;
				for(String i: choose) {					
					String j="";
					String k="";
					switch(i) {
					case "name": {j="이름"; k=user.getString(0,i); break;}
					case "sex": {j="성별"; k=user.getString(0,i); break;}
					case "height": {j="총장";
						if(category.equals("상의")) k= String.format("%4.1f", Double.parseDouble(user.getString(0,i))*0.36); // 상체 길이.
						else if (category.equals("하의")) k= String.format("%4.1f", Double.parseDouble(user.getString(0,i))*0.50); // 하체 길이.
						else k=user.getString(0,i);
						start_y+=50;
						break;}
					case "shoulder": {j="어깨너비"; k=user.getString(0,i); break;}
					case "chest": {j="가슴둘레"; k=user.getString(0,i); break;}
					case "waist": {j="허리둘레"; k=user.getString(0,i); break;}
					case "leg": {j="다리길이"; k=user.getString(0,i); break;}
					default: j= i; k=user.getString(0,i);
					}	
					
					add(new TextPanel(40, start_y, j, k+" "+unit.getString(unit.searchIndex("속성", i), "단위")));
				}
				} catch (Exception e) {
					System.out.println("사용자 정보을 가져오지 못했거나, 사용자 정보를 불러오는 중에 에러가 있습니다.");
					System.out.println(e);
				}
									
		}
	}

	class ClothPanel extends JPanel {
			
		public ClothPanel() { 
			
			chosenCloth = dao.getCloth(id, category, clothName); // 그 옷의 정보 가져오기.
			
			setLayout(null);
			
			setBounds(980, 305, 330, 400);
			setBackground(new Color(2, 115, 94));
			
			// 가져온 옷 사이즈가 없으면,
			if(chosenCloth.getString(0, "사이즈")==null||chosenCloth.getString(0, "사이즈").equals("")) {
				// 옷 이름 라벨.
				JTextPane nothing = new JTextPane();
				nothing.setContentType("text/html");
				nothing.setBounds(25, 45, 320, 380);
				String style = "#sorry{"            // 원하는 위치에서 줄바꿈을 하기위해 html을 넣었다. <br> OTL..
						+ "font: 23px \"맑은 고딕\";"
						+ "font-weight: bold;"
						+ "color:white;"
						+ "}";
				nothing.setText("<html><head><style>"+style+"</style></head><body><span id=sorry>죄송합니다.<br><br>이 옷에 대해서는<br>추천드릴 사이즈가<br>없습니다.</span></body></html>");
				nothing.setFont(new Font("Gothic", Font.BOLD, 30));
				nothing.setForeground(Color.white);
				nothing.setBackground(new Color(0, 0, 0, 0));
				nothing.setEditable(false);
				nothing.setOpaque(false);
				add(nothing);
				
			} else { // 가져온 옷 사이즈가 있으면,
				CategoryPanel categoryPanel = new CategoryPanel(category, chosenCloth.getString(0, "중분류"));
				add(categoryPanel);
				
				// 추천한 아이템 정보 띄우기.
				try {
				String[] top = {"성별","색상","상의총장","어깨너비","가슴단면","소매길이"};
				String[] bottom = {"성별","색상","하의총장","허리단면","엉덩이단면","허벅지단면","밑위","밑단단면"};
				String[] choose= {};
				
				switch(category) {
				case "상의": {choose=top; break;}
				case "하의": {choose=bottom; break;}
				default: System.out.println("대분류가 정확하지 않습니다.");
				}
				
				TextPanel.refresh();
				int start_y=45;
				for(String i: choose) {
					String j="";
					if(i.equals("상의총장")||i.equals("하의총장")) {j="총장"; start_y+=50;}
					else j=(String) i;
										
					add(new TextPanel(25, start_y, j, chosenCloth.getString(0,i).trim()+" "+unit.getString(unit.searchIndex("속성", i), "단위")));
				}
				} catch (Exception e) {
					System.out.println("옷을 가져오지 못했거나, 옷 정보를 불러오는 중에 에러가 있습니다.");
					System.out.println(e);
				}
			}
			
		}		
	}
	// 카테고리 패널.
	class CategoryPanel extends JPanel{
		public CategoryPanel(String category1, String category2) {
			setLayout(null);
			
			int height = 45;          // 이 패널의 높이.
			int font_size = 20;       // 이 패널 라벨들의 글자 크기.
			int category1_width = 85; // 속성 라벨의 폭.
			int category2_width = 230;
			int margin_x = 2;        // :와 value 사이의 마진.
			setBounds(0, 0, 350, height);
			
			
			JLabel category1Label = new JLabel(category1+" > "); // 속성 라벨.
			category1Label.setFont(new Font("Gothic", Font.BOLD, font_size)); // 글자는 현재 Gothic, BOLD.
			category1Label.setBounds(0, 0, category1_width, height);
			category1Label.setHorizontalAlignment(SwingConstants.RIGHT);
			category1Label.setForeground(Color.white);
						
			JLabel category2Label = new JLabel(category2); // 속성값+단위 라벨.
			category2Label.setFont(new Font("Gothic", Font.BOLD, font_size));
			category2Label.setBounds(category1_width+margin_x, 0, category2_width, height);
			category2Label.setHorizontalAlignment(SwingConstants.LEFT);
			category2Label.setForeground(Color.white);
			
			setOpaque(false); // 패널이 투명해진다.
			
			add(category1Label);
			add(category2Label);
		}
	}
	
	// 정보 한줄씩 띄우는 패널 클래스.
	static class TextPanel extends JPanel{ // index값을 저장해서 쓰려고 static클래스가 되었다.
		
		static int start_x=0; // static 시작 가로 기본값.
		static int start_y=0; // static 시작 세로 기본값.
		static int index=0; // static 인덱스 처음값.
		
		// Profile 패널과 Property 패널 모두에서 쓰려면 가끔씩 start_x, y와 index를 0으로 초기화해줄 필요가 있다.
		public static void refresh() { 
			start_x = 0;
			start_y = 0;			
			index=0;
		}
		
		// Text패널의 시작 가로, 세로 위치와 넣으려는 속성, (속성값+단위)를 받아서 패널을 생성하는 생성자.
		public TextPanel(int start_x, int start_y, String property, String value_unit) {
			this.start_x=start_x; // start_height을 받은 시작 높이값으로 만든다.
			this.start_y=start_y; // start_height을 받은 시작 높이값으로 만든다.
			
			int property_width = 120; // 속성 라벨의 폭.
			int margin_x = 20;	      // :와 value 사이의 마진.
			int value_width = 260;    // 속성값 라벨의 폭.
			
			int height = 50;          // 이 패널의 높이.
			int font_size = 23;       // 이 패널 라벨들의 글자 크기.
						
			int margin_y = 0;           // 다음 패널과의 마진.
			
						
			setLayout(null);
			setBounds(0, start_y+(height+margin_y)*index, 350, height);
			index++;
			
			setOpaque(false); // 패널이 투명해진다.
						
			JLabel propertyLabel = new JLabel(property); // 속성 라벨.
			propertyLabel.setFont(new Font("Gothic", Font.BOLD, font_size)); // 글자는 현재 Gothic, BOLD.
			propertyLabel.setBounds(start_x, 0, property_width, height);
			propertyLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			propertyLabel.setForeground(Color.white);
						
			JLabel valueLabel = new JLabel(value_unit); // 속성값+단위 라벨.
			valueLabel.setFont(new Font("Gothic", Font.BOLD, font_size));
			valueLabel.setBounds(start_x+property_width+margin_x, 0, value_width, height);
			valueLabel.setHorizontalAlignment(SwingConstants.LEFT);
			valueLabel.setForeground(Color.white);
			
			add(propertyLabel);
			add(valueLabel);
		}
	}

	//      String[] args = {id, brandName, transparentBrandUrl, category, name, url};
	public static void main(String[] args) {
		new ClothSize(args);
	}

}

