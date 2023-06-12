package project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

public class BrandSelect extends JFrame {
	
	JButton search;
	JTextField brand_tosearch;
	
	JunhwanItemDAO dao = new JunhwanItemDAO();
	Table brandTable;
	String id="";
	
	int x_position = 1386; // 처음 x위치.
	
	public BrandSelect(String[] args) {
		try {
			id=args[0];
		} catch (Exception e) {
			System.out.println("아이디를 받지 못했습니다.");
		}
		JPanel c = new JPanel();
		setContentPane(c);
				
		c.setLayout(null);
		
		HeadPanel header = new HeadPanel();
		BackHeadPanel backHeadPanel = new BackHeadPanel();
		CenterPanel centerPanel = new CenterPanel();
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(0, 0, 1385, 763); // 패널보다 가로는 15쯤, 세로는 35쯤 작아야한다.
		scrollPane.setViewportView(centerPanel);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);   // 스크롤바 속도
		scrollPane.setBorder(null);
		scrollPane.setOpaque(false);
		c.add(header);
		c.add(backHeadPanel);		
		c.add(scrollPane);
				
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("브랜드 선택창");
		setSize(1400,800); 
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		
		Thread1 th = new Thread1(header, backHeadPanel);
		th.start(); // 스레드 시작
	}
	class BackgroundPanel extends JPanel { // 백그라운드 패널
		public BackgroundPanel() {
			setBorder(null);
			setLayout(null);
		}
	}
	class HeadPanel extends JPanel{
		
		
		public HeadPanel() {
			
			setLayout(null);
			setBounds(x_position, 0, 400, 762);
			setOpaque(false);
			
//			setBackground(Color.gray);
						
			JLabel text = new JLabel("Brands");
			text.setFont(new Font("Bell MT", Font.PLAIN, 90));
			text.setBounds(50, 280, 300, 100);
			text.setForeground(Color.white);
			
			JTextField brand_tosearch = new JTextField(50);
			brand_tosearch.setBounds(40, 550, 255, 30);
			brand_tosearch.setBorder(null);
			brand_tosearch.setBackground(new Color(220,230,240));
			
			ImageIcon icon = new ImageIcon("searchbutton.png");
			JButton search = new JButton(icon);
			search.setBorder(null);
			search.setOpaque(false);
			search.setBounds(310, 550, 30, 30);
						
			add(text);
			add(brand_tosearch);
			add(search);
			
			setResizable(false);
			
		}
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			ImageIcon icon = new ImageIcon("headpanel1.png");
			Image img = icon.getImage();
			g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
		}
		boolean flag=true;
		// x위치가 0이면 기다려라. x위치를 조금 줄이고, 스레드를 깨운다.
		synchronized void goLeft() { 
			if (x_position == 0) {
				try {
					wait(); flag=!flag;
				} catch (InterruptedException e) {
					return;
				}
			}
			if(flag) {
				x_position-=11; // 21만큼 x위치를 왼쪽으로 이동시킨다.
				setBounds(x_position,0,400,762);
				notify();
			}
		}
		
	}
	class BackHeadPanel extends JPanel { // headpanel 뒤에있는 또다른 패널. 별 기능은 없음. 디자인적 요소
		
		
		public BackHeadPanel() {
			setLayout(null);
			setBounds(x_position+10, 0, 400, 762);
			setOpaque(false);
			// setBackground(Color.orange);
			setResizable(false);
		}
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Color color = new Color(242, 84, 27);
			g.setColor(color);
			g.fillRoundRect(0, 0, getWidth(), getHeight(), 80, 80);
		}
		boolean flag=true;
		// x위치가 0이면 기다려라. x위치를 조금 줄이고, 스레드를 깨운다.
		synchronized void goLeft() { 
			if (x_position == 0) {
				try {
					wait(); flag=!flag;
				} catch (InterruptedException e) {
					return;
				}
			}
			if(flag) {
				x_position-=11; // 21만큼 x위치를 왼쪽으로 이동시킨다.
				setBounds(x_position,0,400,762);
				notify();
			}
		}
	}
	class CenterPanel extends JPanel{
		
		int countx = 0; //이미지 가로 갯수 카운트
		int county = 0; // 이미지 세로 갯수 카운트

		public CenterPanel() {
			
			setLayout(null);			
//			setBounds(0, 100, 1400, 677);
			setBorder(null);
			
//			setBackground(new Color(220, 220, 220));
			setFont(new Font("맑은 고딕", Font.LAYOUT_LEFT_TO_RIGHT, 20));
			
			brandTable = dao.getBrandTable(); // 브랜드 이름, 경로, transparent경로.
						
			for(int i=0; i<brandTable.size();i++) {
				String brandName = brandTable.getString(i, "이름");
				String brandUrl = brandTable.getString(i, "경로");
				File brandImg = new File(brandUrl);
				
				if(brandImg.exists()) {
					add(brand(brandName, brandUrl));
				} else {System.out.println("파일 경로에 파일이 없습니다 : "+brandUrl);}
			}
			
			setPreferredSize(new Dimension(1400, 80+290*(county))); // 80은 brand메서드의 y값을 줘야함.
			setResizable(false);
			setOpaque(false);
		}
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			ImageIcon icon = new ImageIcon("brandbackground.png");
			Image img = icon.getImage();
			g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
		}
		public JButton brand(String brandName, String brandUrl) { // 버튼 삽입 메소드
			
			int x = 500; 	//첫 이미지버튼 x좌표
			int y = 80;	//첫 이미지버튼 y좌표
			
			ImageIcon icon = new ImageIcon(brandUrl);
		    Image image = icon.getImage();
			Image scaledImage = image.getScaledInstance(200, 240, Image.SCALE_SMOOTH);
		    ImageIcon scaledIcon = new ImageIcon(scaledImage);
		    JButton brand = new JButton(scaledIcon);
		    JLabel cover = new JLabel(brandName, SwingConstants.CENTER);//마우스를 버튼위로 올리면 visible true가 되면서 브랜드 이름이 나옴
			cover.setVerticalAlignment(SwingConstants.BOTTOM);
			cover.setForeground(Color.black);
			cover.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
			cover.setVisible(false);
			brand.setLayout(new BorderLayout());
			brand.add(cover, BorderLayout.SOUTH);
			
		    brand.addActionListener(new ActionListener() { // 버튼에 액션을 등록.
				@Override
				public void actionPerformed(ActionEvent e) {  // 버튼을 누르면.
					int index = brandTable.searchIndex("이름",brandName);
					String transparentBrandUrl= brandTable.getString(index,"transparent경로");
					String[] args = {id, brandName,transparentBrandUrl};
					ClothPage.main(args);
					dispose();
				}
				
			});
		    
		    brand.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					cover.setVisible(true);
				}
				@Override
				public void mouseExited(MouseEvent e) {
					cover.setVisible(false);
				}
				
			 });
		    
			brand.setBounds(x+290*countx, y+290*county, 200, 240); // x,y에 버튼 삽입, 200,240사이즈의 버튼
			cover.setBounds(x+290*countx, y+290*county, 200, 240);
			countx++;
			if(countx == 3) { 		//가로 이미지 갯수가 3개가 되면
				county++;			//다음 줄로 넘어감
				countx = 0;			//그 다음 다시 가로이미지 갯수 0으로 초기화
			}
			return brand; // JButton cloth 반환
		}		
	}
	//스레드 클래스.
	class Thread1 extends Thread {  
		HeadPanel headPanel;
		BackHeadPanel backHeadPanel;
			
		// HeadPanel 객체를 받아서 초기화하는 스레드 생성자.
		Thread1(HeadPanel headPanel, BackHeadPanel backHeadPanel) {  
			this.headPanel = headPanel;
			this.backHeadPanel = backHeadPanel;			
		}
					
		// 스레드 시작 시 실행되는 것. 0.001초에 한번씩 goLeft를 호출한다.
		public void run() {            
			while (true) {
				try {
					sleep(1);
					backHeadPanel.goLeft();
					headPanel.goLeft();
				} catch (InterruptedException e) {
					return;
				}
			}
		}
	}
//	String[] args = {id};
	public static void main(String[] args) {
		new BrandSelect(args);
	}
	
}
