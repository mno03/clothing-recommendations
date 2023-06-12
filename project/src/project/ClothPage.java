package project;

import java.awt.Color;
import java.awt.Container;
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
class ClothPageFrame3 extends JFrame {
	private int countx = 0; // 이미지 가로 갯수 카운트
	private int county = 0; // 이미지 세로 갯수 카운트
	private int x = 500; // 첫 이미지버튼 x좌표
	private int y = 80; // 첫 이미지버튼 y좌표
	int x_position = 1386; // 처음 header x위치.


	JunhwanItemDAO dao = new JunhwanItemDAO();
	Table clothPageTable;


	String id = "", brandName = "", transparentBrandUrl = "";


	public ClothPageFrame3(String[] args) {
		try {
			id = args[0];
			brandName = args[1];
			transparentBrandUrl = args[2];
		} catch (Exception e) {
			System.out.println("args를 다 받지 못했습니다.");
		}
		BackgroundPanel c = new BackgroundPanel();
		setContentPane(c);
		c.setLayout(null);
		HeadPanel header = new HeadPanel();
		BackHeadPanel backHeadPanel = new BackHeadPanel();
		CenterPanel body = new CenterPanel();
		c.add(header);
		c.add(backHeadPanel);
		JScrollPane scrollPane = new JScrollPane(body);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(0, 0, 1385, 763); // 15 작아야한다.
		scrollPane.setViewportView(body);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16); // scroll speed
		// scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
		scrollPane.setBorder(null);
		scrollPane.setOpaque(false);
		c.add(scrollPane);
		c.setBackground(Color.gray);
		setSize(1400, 800);
		setTitle("의류 상세보기");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		
		Thread2 th = new Thread2(header, backHeadPanel);
		th.start(); // 스레드 시작
		
	}
	class BackgroundPanel extends JPanel { // 백그라운드 패널
		public BackgroundPanel() {
			setBorder(null);
			setLayout(null);
		}
	}
	class HeadPanel extends JPanel {
				
		public HeadPanel() {
			setLayout(null);
			setBounds(x_position, 0, 400, 762);
			setOpaque(false);
			// setBackground(Color.gray);
			// 뒤로가기 버튼.
			ImageIcon originicon = new ImageIcon("backbutton.png"); // 뒤로 가기 이미지.
			Image img = originicon.getImage();
			Image chngimg = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH); // 이미지 크기조절
			ImageIcon icon = new ImageIcon(chngimg);
			JButton back = new JButton(icon);
			back.setOpaque(false);
			back.setContentAreaFilled(false);
			back.setBorder(null);
			back.setBounds(40, 680, 30, 30);
			back.addMouseListener(new MouseAdapter() { // 뒤로가기 마우스 리스너.
				@Override
				public void mouseReleased(MouseEvent e) {
					String[] args = { id };
					BrandSelect.main(args);
					dispose();
				}
			});
			// 브랜드 이미지.
			JLabel BrandImage = new JLabel();
			try {
				ImageIcon originbricon = new ImageIcon(transparentBrandUrl); // 넘겨받은 브랜드 투명이미지 주소 삽입.
				Image brimg = originbricon.getImage();
				Image chngbrimg = brimg.getScaledInstance(250, 250, Image.SCALE_SMOOTH); // 이미지 크기조절
				ImageIcon bricon = new ImageIcon(chngbrimg);
				BrandImage.setIcon(bricon);
			} catch (Exception e) {
				System.out.println("넘겨받은 브랜드 이미지 경로가 없습니다.");
			}
			BrandImage.setBounds(50, 230, 250, 250);
			// 브랜드 이름.
			try {
				JLabel brand = new JLabel(brandName); // header부분 JLabel에 넘겨받은 브랜드 이름 삽입.
				brand.setBounds(25, 500, 300, 60);
				brand.setVerticalAlignment(SwingConstants.BOTTOM); // 브랜드명은 아래로 정렬 중인 상태.
				brand.setHorizontalAlignment(SwingConstants.CENTER); // 가운데 정렬 중.
				brand.setFont(new Font("Gothic", Font.BOLD, 30));
				brand.setForeground(Color.white);
				add(brand);
			} catch (Exception e2) {
				System.out.println("넘겨받은 브랜드 이름이 없습니다.");
			}
			add(back);
			add(BrandImage);
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
	class CenterPanel extends JPanel {
		int countx = 0; // 이미지 가로 갯수 카운트
		int county = 0; // 이미지 세로 갯수 카운트
		public CenterPanel() {
			setLayout(null);
//			setBounds(0, 100, 1400, 677);
			setBorder(null);
			setFont(new Font("맑은 고딕", Font.LAYOUT_LEFT_TO_RIGHT, 20));
			try {// 넘겨받은 브랜드 이름으로 의류 이름과 사진경로 테이블 얻어오기.
				clothPageTable = dao.getClothPageTable(brandName);
				for (int i = 0; i < clothPageTable.size(); i++) {
					String name = clothPageTable.getString(i, "이름"); // i번 줄의 의류 이름.
					String url = "images\\"+name.trim()+"1.jpg"; // 의류 사진 경로.
					
					File clothImg = new File(url);
					
					if(clothImg.exists()) {
						add(clothButton(name, url));
					} else {System.out.println("파일 경로에 파일이 없습니다 : "+url);}

				}
			} catch (Exception e) {
				System.out.println("넘겨받은 브랜드 이름이 없습니다.");
			}
			setPreferredSize(new Dimension(1400, 80 + 290 * (county))); // 150은 clothButton메서드의 y값을 줘야함.
			setResizable(false);
			setOpaque(false);
		}
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			ImageIcon icon = new ImageIcon("brandbackground.png");
			Image img = icon.getImage();
			g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
		}
	
		public JButton clothButton(String name, String url) { // 버튼 삽입 메소
			ImageIcon originicon = new ImageIcon(url); // 이미지 삽입
			Image img = originicon.getImage();
			Image chngimg = img.getScaledInstance(200, 240, Image.SCALE_SMOOTH); // 이미지 크기조절
			ImageIcon icon = new ImageIcon(chngimg);
			JButton cloth = new JButton(icon);
			cloth.addActionListener(new ActionListener() { // 의류 버튼에 액션을 등록.
				@Override
				public void actionPerformed(ActionEvent e) { // 버튼을 누르면.
					int index = clothPageTable.searchIndex("이름", name);
					String[] args = { id, brandName, transparentBrandUrl, clothPageTable.getString(index, "대분류"), name, url }; // 그 이름과 경로를 다음 화면에 전달한다.
					ClothSize.main(args);
					dispose();
				}
			});
			cloth.setBounds(x + 290 * countx, y + 290 * county, 200, 240); // x,y에 버튼 삽입, 200,240사이즈의 버튼
			countx++;
			if (countx == 3) { // 가로 이미지 갯수가 3개가 되면
				county++; // 다음 줄로 넘어감
				countx = 0; // 그 다음 다시 가로이미지 갯수 0으로 초기화
			}
			return cloth; // JButton cloth 반환
		}
	}
	//스레드 클래스.
	class Thread2 extends Thread {  
		HeadPanel header;
		BackHeadPanel backHeadPanel;
			
		// HeadPanel, BackHeadPanel 객체를 받아서 초기화하는 스레드 생성자.
		Thread2(HeadPanel header, BackHeadPanel backHeadPanel) {  
			this.header = header;
			this.backHeadPanel = backHeadPanel;
		}
				
		// 스레드 시작 시 실행되는 것. 0.001초에 한번씩 goLeft를 호출한다.
		public void run() {            
			while (true) {
				try {
					sleep(1);
					backHeadPanel.goLeft();
					header.goLeft();
				} catch (InterruptedException e) {
					return;
				}
			}
		}
	}
}
//	String[] args = {id, brand.getBrandName(),transparentBrandUrl};
public class ClothPage {
	public static void main(String[] args) {
		new ClothPageFrame3(args);
	}
}




