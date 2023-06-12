package project;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
public class RegisterFrame extends JFrame {
	CustomerDTO customerDTO;
	CustomerDAO customerDAO;
	JTextField idfld;
	JPasswordField pwfld;
	JPasswordField pwckfld;
	JTextField namefld;
	JTextField heightFld;
	JTextField sholderWidthFld;
	JTextField BustFld;
	JTextField bottomsFld;
	JTextField wcfeFld;
	int passwordCorrect = 1; // 비밀번호 일치 확인을 위한 변수
	public RegisterFrame() {
		// 프레임
		BackgroundPanel jf = new BackgroundPanel(); // 백그라운드 설정
		jf.setSize(800, 600);
		jf.setLayout(null);
		setTitle("회원가입");
		setContentPane(jf);
		// TITLE
		JLabel title = new JLabel("회원가입");
		title.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		title.setForeground(Color.white);
		title.setBounds(350, 40, 100, 80);
		// ID
		MyLabel id = new MyLabel("아이디"); // 일괄적인 폰트및 레이블 설정을 위해 mylabel클래스를 만듦
		id.setBounds(93, 150, 80, 30);
		// ID 텍스트필드
		idfld = new JTextField(30);
		idfld.setBounds(165, 150, 200, 30);
		idfld.setBorder(null);
		// 비밀번호
		MyLabel pw = new MyLabel("비밀번호");
		pw.setBounds(80, 200, 80, 30);
		pw.setBorder(null);
		// 비밀번호 텍스트필드
		pwfld = new JPasswordField(30);
		pwfld.setBounds(165, 200, 200, 30);
		pwfld.setBorder(null);
		// 비밀번호 확인
		MyLabel pwck = new MyLabel("비밀번호 확인");
		pwck.setBounds(44, 245, 105, 40);
		// 비번확인 텍스트필드
		pwckfld = new JPasswordField(30);
		pwckfld.setBounds(165, 250, 200, 30);
		pwckfld.setBorder(null);
		// 이름
		MyLabel name = new MyLabel("이름");
		name.setBounds(107, 300, 80, 30);
		namefld = new JTextField(30);
		namefld.setBounds(165, 300, 200, 30);
		namefld.setBorder(null);
		// 성별
		MyLabel mw = new MyLabel("성별");
		mw.setBounds(108, 350, 80, 30);
		ButtonGroup group = new ButtonGroup(); // 버튼 그룹 생성 -> 라디오 버튼을 그룹에 넣으면 하나만 선택해야함
		JRadioButton jrbtn = new JRadioButton("남");
		jrbtn.setBounds(185, 350, 90, 30);
		jrbtn.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		jrbtn.setForeground(Color.white);
		jrbtn.setOpaque(false);
		JRadioButton jrbtn2 = new JRadioButton("여");
		jrbtn2.setBounds(305, 350, 90, 30);
		jrbtn2.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		jrbtn2.setForeground(Color.white);
		jrbtn2.setOpaque(false);
		group.add(jrbtn);
		group.add(jrbtn2);
		// 키
		MyLabel height = new MyLabel("키");
		height.setBounds(490, 150, 80, 30);
		heightFld = new JTextField(15);
		heightFld.setBounds(535, 150, 100, 30);
		heightFld.setBorder(null);
		// 어깨너비
		MyLabel sholderWidth = new MyLabel("어깨너비");
		sholderWidth.setBounds(446, 200, 80, 30);
		sholderWidthFld = new JTextField(15);
		sholderWidthFld.setBounds(535, 200, 100, 30);
		sholderWidthFld.setText("0");
		sholderWidthFld.setBorder(null);
		// 가슴둘레
		MyLabel Bust = new MyLabel("가슴둘레");
		Bust.setBounds(446, 250, 80, 30);
		BustFld = new JTextField(15);
		BustFld.setBounds(535, 250, 100, 30);
		BustFld.setText("0");
		BustFld.setBorder(null);
		// 하의총장
		MyLabel bottoms = new MyLabel("하의총장");
		bottoms.setBounds(446, 300, 80, 30);
		bottomsFld = new JTextField(15);
		bottomsFld.setBounds(535, 300, 100, 30);
		bottomsFld.setText("0");
		bottomsFld.setBorder(null);
		// 허리둘레
		MyLabel wcfe = new MyLabel("허리둘레");
		wcfe.setBounds(446, 350, 80, 30);
		wcfeFld = new JTextField(15);
		wcfeFld.setBounds(535, 350, 100, 30);
		wcfeFld.setText("0");
		wcfeFld.setBorder(null);
		// 가입
		Color c = new Color(238, 238, 239); // 배경색 결정
		Color o = new Color(242, 84, 27);
		RoundedButton register = new RoundedButton("가입", o, c); // roundedButton클래스는 login2 파일에 선언되어있음.
		register.setBounds(170, 450, 280, 35);
		register.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		// 취소버튼
		RoundedButton cancel = new RoundedButton("취소", c, o);
		cancel.setBounds(490, 450, 80, 35);
		cancel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		// 타이틀 추가
		jf.add(title);
		// 아이디 추가
		jf.add(id);
		jf.add(idfld);
		// 비밀번호추가
		jf.add(pw);
		jf.add(pwfld);
		// 비밀번호확인 추가
		jf.add(pwck);
		jf.add(pwckfld);
		// 이름
		jf.add(name);
		jf.add(namefld);
		// 성별
		jf.add(mw);
		jf.add(jrbtn);
		jf.add(jrbtn2);
		// 키
		jf.add(height);
		jf.add(heightFld);
		// 어깨너비
		jf.add(sholderWidth);
		jf.add(sholderWidthFld);
		// 가슴둘레
		jf.add(Bust);
		jf.add(BustFld);
		// 하의총장
		jf.add(bottoms);
		jf.add(bottomsFld);
		// 허리둘레
		jf.add(wcfe);
		jf.add(wcfeFld);
		// 버튼 추가
		jf.add(register);
		jf.add(cancel);
		// 프레임
		setSize(800, 600);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { // 로그인 버튼 액션
				new Login2();
				dispose();
			}
		});
		register.addActionListener(new ActionListener() { // 회원가입 버튼 액션
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String sex; // 성별 입력
					if (jrbtn.isSelected()) {
						sex = "male";
					} else {
						sex = "female";
					}
					contentSet(sex); // 성별및 다른 신체 치수들 입력
					int result;
					if (passwordCorrect == 1) { // 비밀번호와 비밀번호 확인이ㅣ 일치하면 데이터 입력실행
						result = customerDAO.insertProfile(customerDTO);
					} else {
						result = -1;
					}
					if (result == 1) { // 1이 나왔다는것은 삽입이 잘 나옷것. 잘 안되면 음수값이 나옴.
						JOptionPane.showMessageDialog(null, "회원가입이 완료되었습니다.");
						new Login2();
						dispose();
					} else {
						JOptionPane.showMessageDialog(null, "회원가입에 실패하였습니다.");
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "값을 입력하세요.");
				}
			}
		});
	}
	public void contentSet(String sex) { // 입력받은 값들을 저장
		customerDTO = new CustomerDTO();
		String name, id;
		String password = "1234";
		double height, shoulder, chest, waist, leg;
		name = namefld.getText();
		id = idfld.getText();
		if ((pwfld.getText()).equals(pwckfld.getText())) { // 비밀번호와 비밀번호 확인이 서로 일치하는지 확인. 일치하지 않으면 -1반환
			password = pwfld.getText();
			passwordCorrect = 1;
		} else {
			JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다.");
			passwordCorrect = -1;
		}
		height = Double.parseDouble(heightFld.getText()); // String 값을 double로 변환
		shoulder = Double.parseDouble(sholderWidthFld.getText());
		chest = Double.parseDouble(BustFld.getText());
		waist = Double.parseDouble(bottomsFld.getText());
		leg = Double.parseDouble(wcfeFld.getText());
		customerDTO.setName(name);
		customerDTO.setId(id);
		customerDTO.setPassword(password);
		customerDTO.setHeight(height);
		customerDTO.setShoulder(shoulder);
		customerDTO.setChest(chest);
		customerDTO.setWaist(waist);
		customerDTO.setLeg(leg);
		customerDTO.setSex(sex);
	}
	class BackgroundPanel extends JPanel { // 패널에 백그라운드 삽입
		private ImageIcon backgroundImg = new ImageIcon("registerbackground.png");
		private Image img = backgroundImg.getImage();
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
		}
	}
	class MyLabel extends JLabel {
		public MyLabel(String text) {
			super(text);
			setFont(new Font("맑은 고딕", Font.BOLD, 15));
			setForeground(Color.white);
		}
	}
	public static void main(String[] args) {
		new RegisterFrame();
	}
}




