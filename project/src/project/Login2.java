package project;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Login2 extends JFrame {
	JTextField txtID;
	JPasswordField txtPass;

	public Login2() {
		MyPanel panel = new MyPanel(); // 그래픽을 이용한 MyPanel 클래스생성. JPanel 상속받음
		panel.setLayout(null);
		JLabel text = new JLabel("Login");
		text.setFont(new Font("휴먼엑스포", Font.BOLD, 35));
		text.setForeground(Color.white);
		text.setHorizontalAlignment(SwingConstants.CENTER);
		text.setBounds(137, 65, 300, 45);
		// panel 생성
		JLabel label = new JLabel("I D");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setFont(new Font("휴먼엑스포", Font.PLAIN, 20));
		label.setForeground(Color.white);
		label.setBounds(50, 140, 150, 25);
		// ID를 입력할 TextField 생성, 10글자 작성 가능
		txtID = new JTextField(10);
		txtID.setBounds(270, 140, 150, 20);
		txtID.setFont(new Font("Gothic", Font.PLAIN, 15));
		txtID.setOpaque(false); // 택스트필드 바탕색 없앰
		txtID.setBorder(null); // 텍스트필드 테두리 없앰
		// 비밀번호 입력 안내 텍스트 생성
		JLabel password = new JLabel("PW");
		password.setHorizontalAlignment(SwingConstants.RIGHT);
		password.setFont(new Font("휴먼엑스포", Font.BOLD, 20));
		password.setForeground(Color.white);
		password.setBounds(51, 180, 150, 20);
		// 암호처럼 textField에 쳤을 때 우리 눈에 암호화되어서 보이는 textField
		// textField지만 암호화됨
		// 비밀번호는 10자리 까지만 입력할 수 있음
		txtPass = new JPasswordField(10);
		txtPass.setBounds(270, 178, 150, 20);
		txtPass.setFont(new Font("Gothic", Font.BOLD, 15));
		txtPass.setOpaque(false);
		txtPass.setBorder(null);
		// 버튼 추가
		Color c = new Color(238, 238, 239); // 배경색 결정
		Color o = new Color(242, 84, 27); // 글자색 결정
		RoundedButton logbtn = new RoundedButton("LogIn", c, o);
		logbtn.setBounds(270, 230, 195, 30);
		logbtn.setBorder(null);
		// 버튼 추가
		RoundedButton mbtn = new RoundedButton("회원가입", o, c);
		mbtn.setBounds(150, 230, 100, 30);
		panel.add(text);
		// panel에 ID 텍스트인 label 추가
		panel.add(label);
		// panel에 아이디를 입력받을 txtID 추가
		panel.add(txtID);
		// panel에 PassWord 텍스트인 label 추가
		panel.add(password);
		// panel에 비밀번호를 입력받을 txtPass 추가
		panel.add(txtPass);
		// panel 맨 마지막에 LogIn 버튼 추가
		panel.add(logbtn);
		panel.add(mbtn);
		add(panel);
		// logbtn에 기능 추가
		logbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				check();
				String id = txtID.getText();
				String password = txtPass.getText();

				CustomerDAO cdao = CustomerDAO.getInstance();

				int result = cdao.idPass(id, password);
				if (result == 1) {
					JOptionPane.showMessageDialog(null, "로그인 완료");
					dispose();
					if (id.equals("관리자")) {
						new SearchMain();
					} else {
						String[] args = { id };
						BrandSelect.main(args);
					}
				} else {
					JOptionPane.showMessageDialog(null, "로그인 실패");
				}
			}
		});
		// mbtn에 기능 추가
		mbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new RegisterFrame();
				dispose();
			}
		});
		// JFrame에 이 모든 것들을 담은 panel을 추가하기
		setTitle("로그인");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 350);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}

	class MyPanel extends JPanel {
		private ImageIcon backgroundImg = new ImageIcon("loginbackground.png");
		private Image img = backgroundImg.getImage();

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
		}
	}

	// 둥근 버튼을 위한 클래스
	public int check() {
		int status = 0;
		CustomerDTO dto = new CustomerDTO();
		if ((txtID.getText()).equals(dto.getId())) {
			if ((txtPass.getText()).equals(dto.getPassword())) {
			}
		}
		return status;
	}

	public static void main(String[] args) {
		new Login2();
	}
}

class RoundedButton extends JButton { // 라운드 버튼 클래스
	private Color backColor;
	private Color fontColor;

	public RoundedButton() {
		super();
		decorate();
	}

	public RoundedButton(String text, Color backColor, Color fontColor) {
		super(text);
		decorate();
		this.backColor = backColor;
		this.fontColor = fontColor;
	}

	public RoundedButton(Action action) {
		super(action);
		decorate();
	}

	public RoundedButton(Icon icon) {
		super(icon);
		decorate();
	}

	public RoundedButton(String text, Icon icon) {
		super(text, icon);
		decorate();
	}

	protected void decorate() {
		setBorderPainted(false);
		setOpaque(false);
	}

	@Override
	protected void paintComponent(Graphics g) {
		int width = getWidth();
		int height = getHeight();
		Graphics2D graphics = (Graphics2D) g;
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if (getModel().isArmed()) {
			graphics.setColor(backColor.brighter()); // 클릭했을시 색이 밝아짐
		} else if (getModel().isRollover()) {
			graphics.setColor(backColor.darker()); // 마우스가 올려졌을때 어두워짐
		} else {
			graphics.setColor(backColor); // 평상시: 설정색
		}
		graphics.fillRoundRect(0, 0, width, height, 30, 30); // (0,0,widht,height,r,r)
		FontMetrics fontMetrics = graphics.getFontMetrics();
		Rectangle stringBounds = fontMetrics.getStringBounds(this.getText(), graphics).getBounds();
		int textX = (width - stringBounds.width) / 2;
		int textY = (height - stringBounds.height) / 2 + fontMetrics.getAscent();
		graphics.setColor(fontColor);
		graphics.setFont(getFont());
		graphics.drawString(getText(), textX, textY);
		graphics.dispose();
		super.paintComponent(g);
	}
}
