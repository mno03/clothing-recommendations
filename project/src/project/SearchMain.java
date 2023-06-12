package project;


import java.sql.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class SearchMain extends JFrame implements ActionListener, MouseListener {

	JFrame main;
	JTable table;
	JScrollPane scroll;
	DefaultTableModel model;
	Vector data, col;
	JLabel id, name, password, gender, height, shoulder, chest, waist, leg;
	JButton add, delete, change, exit;
	JTextField idfld, namefld, passfld, genderfld, heightfld, sholfld, chestfld, waistfld, legfld;
	SearchDAO searchDAO;
	SearchDTO searchDTO;
	Font ft = new Font("맑은 고딕", Font.BOLD, 15);

	public SearchMain() {
		searchDAO = new SearchDAO();

		main = new JFrame();
		main.setLayout(null);
		main.setSize(800, 600);

		// 버튼생성
		main.add(add = new JButton("등록"));
		add.setBounds(80, 465, 130, 40);
		add.addActionListener(this);
		add.setFont(ft);

		main.add(delete = new JButton("삭제"));
		delete.setBounds(245, 465, 130, 40);
		delete.addActionListener(this);
		delete.setFont(ft);

		main.add(change = new JButton("수정"));
		change.setBounds(405, 465, 130, 40);
		change.addActionListener(this);
		change.setFont(ft);

		main.add(exit = new JButton("나가기"));
		exit.setBounds(570, 465, 130, 40);
		exit.addActionListener(this);
		exit.setFont(ft);

		// 이름
		main.add(name = new JLabel("이름"));
		name.setBounds(53, 323, 80, 30);
		name.setFont(ft);

		main.add(namefld = new JTextField(20));
		namefld.setBounds(108, 323, 130, 30);
		namefld.setFont(ft);

		// 아이디
		main.add(id = new JLabel("아이디"));
		id.setBounds(287, 323, 80, 30);
		id.setFont(ft);

		main.add(idfld = new JTextField(20));
		idfld.setBounds(358, 323, 130, 30);
		idfld.setFont(ft);

		// 비밀번호
		main.add(password = new JLabel("비밀번호"));
		password.setBounds(534, 323, 80, 30);
		password.setFont(ft);

		main.add(passfld = new JTextField(20));
		passfld.setBounds(614, 323, 130, 30);
		passfld.setFont(ft);
		
		// 성별
		main.add(gender = new JLabel("성별"));
		gender.setBounds(52, 363, 80, 30);
		gender.setFont(ft);

		main.add(genderfld = new JTextField(20));
		genderfld.setBounds(108, 363, 130, 30);
		genderfld.setFont(ft);

		// 키
		main.add(height = new JLabel("키"));
		height.setBounds(317, 363, 80, 30);
		height.setFont(ft);

		main.add(heightfld = new JTextField(20));
		heightfld.setBounds(358, 363, 130, 30);
		heightfld.setFont(ft);

		// 어깨너비
		main.add(shoulder = new JLabel("어깨너비"));
		shoulder.setBounds(533, 363, 80, 30);
		shoulder.setFont(ft);

		main.add(sholfld = new JTextField(20));
		sholfld.setBounds(614, 363, 130, 30);
		sholfld.setFont(ft);

		// 가슴둘레
		main.add(chest = new JLabel("가슴둘레"));
		chest.setBounds(23, 403, 80, 30);
		chest.setFont(ft);

		main.add(chestfld = new JTextField(20));
		chestfld.setBounds(108, 405, 130, 30);
		chestfld.setFont(ft);

		// 허리둘레
		main.add(waist = new JLabel("허리둘레"));
		waist.setBounds(272, 403, 80, 30);
		waist.setFont(ft);

		main.add(waistfld = new JTextField(20));
		waistfld.setBounds(358, 405, 130, 30);
		waistfld.setFont(ft);

		// 다리총장
		main.add(leg = new JLabel("다리총장"));
		leg.setBounds(533, 403, 80, 30);
		leg.setFont(ft);

		main.add(legfld = new JTextField(20));
		legfld.setBounds(613, 405, 130, 30);
		legfld.setFont(ft);

		// 테이블 목록 추가
		col = new Vector();
		col.add("이름");
		col.add("아이디");
		col.add("비밀번호");
		col.add("성별");
		col.add("키");
		col.add("어깨너비");
		col.add("가슴둘레");
		col.add("허리둘레");
		col.add("다리총장");

		model = new DefaultTableModel(searchDAO.getProfile(), col) {
			public boolean isCellEditable(int row, int colum) {
				return false;
			}
		};

		table = new JTable(model);
		table.addMouseListener(this);
		scroll = new JScrollPane(table);
		JTableSet();
		main.add(scroll);
		scroll.setBounds(0, 0, 800, 295);

		main.setVisible(true);
		main.setResizable(false);
		main.setDefaultCloseOperation(EXIT_ON_CLOSE);
		main.setLocationRelativeTo(null);
	}

	public void JTableSet() {
		// 이동과 길이 조절 여러개 선택 되는 것을 방지하는 모듈
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

		// 컬럼 정렬에 필요한 메서드
		DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
		celAlignCenter.setHorizontalAlignment(JLabel.CENTER);

		DefaultTableCellRenderer celAlignRight = new DefaultTableCellRenderer();
		celAlignRight.setHorizontalAlignment(JLabel.RIGHT);

		DefaultTableCellRenderer celAlignLeft = new DefaultTableCellRenderer();
		celAlignLeft.setHorizontalAlignment(JLabel.LEFT);

		// 컬럼별 사이즈 조절 & 정렬
		table.getColumnModel().getColumn(0).setPreferredWidth(10);
		table.getColumnModel().getColumn(0).setCellRenderer(celAlignCenter);

		table.getColumnModel().getColumn(1).setPreferredWidth(10);
		table.getColumnModel().getColumn(1).setCellRenderer(celAlignCenter);

		table.getColumnModel().getColumn(2).setPreferredWidth(10);
		table.getColumnModel().getColumn(2).setCellRenderer(celAlignCenter);

		table.getColumnModel().getColumn(3).setPreferredWidth(10);
		table.getColumnModel().getColumn(3).setCellRenderer(celAlignCenter);

		table.getColumnModel().getColumn(4).setPreferredWidth(10);
		table.getColumnModel().getColumn(4).setCellRenderer(celAlignCenter);

		table.getColumnModel().getColumn(5).setPreferredWidth(10);
		table.getColumnModel().getColumn(5).setCellRenderer(celAlignCenter);

		table.getColumnModel().getColumn(6).setPreferredWidth(10);
		table.getColumnModel().getColumn(6).setCellRenderer(celAlignCenter);

		table.getColumnModel().getColumn(7).setPreferredWidth(10);
		table.getColumnModel().getColumn(7).setCellRenderer(celAlignCenter);

		table.getColumnModel().getColumn(8).setPreferredWidth(10);
		table.getColumnModel().getColumn(8).setCellRenderer(celAlignCenter);

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int rowIndex = table.getSelectedRow();

		namefld.setText(table.getValueAt(rowIndex, 0) + "");
		idfld.setText(table.getValueAt(rowIndex, 1) + "");
		passfld.setText(table.getValueAt(rowIndex, 2) + "");
		genderfld.setText(table.getValueAt(rowIndex, 3) + "");
		heightfld.setText(table.getValueAt(rowIndex, 4) + "");
		sholfld.setText(table.getValueAt(rowIndex, 5) + "");
		chestfld.setText(table.getValueAt(rowIndex, 6) + "");
		waistfld.setText(table.getValueAt(rowIndex, 7) + "");
		legfld.setText(table.getValueAt(rowIndex, 8) + "");

	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String ButtonFlag = e.getActionCommand();

		if (ButtonFlag.equals("등록")) {
			try {
				contentSet();
				int result = searchDAO.insertProfile(searchDTO);
				int msg = JOptionPane.showConfirmDialog(null, "등록하시겠습니까?", "Change", JOptionPane.OK_CANCEL_OPTION);
				if (msg == 0) {
					if (result == 1) {
						JOptionPane.showMessageDialog(this, "추가되었습니다");
						jTableRefresh();
						contentClear();
					} else {
						JOptionPane.showMessageDialog(this, "실패하였습니다");
					}
				}
			} catch (Exception ex) {

			}
		} else if (ButtonFlag.equals("삭제")) {
			try {
				contentSet();
				int result = searchDAO.deleteProfile(searchDTO);
				int msg = JOptionPane.showConfirmDialog(null, "삭제하시겠습니까?", "Change", JOptionPane.OK_CANCEL_OPTION);
				if (msg == 0) {
					if (result == 1) {
						JOptionPane.showMessageDialog(this, "삭제되었습니다");
						jTableRefresh();
						contentSet();
						contentClear();
					} else {
						JOptionPane.showMessageDialog(this, "삭제 실패하였습니다");
					}
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "값을 확인해주세요");
			}
		} else if (ButtonFlag.equals("수정")) {
			try {
				contentSet();
				int result = searchDAO.updateProfile(searchDTO);
				int msg = JOptionPane.showConfirmDialog(null, "수정하시겠습니까?", "Change", JOptionPane.OK_CANCEL_OPTION);

				if (msg == 0) {
					if (result == 1) {
						JOptionPane.showMessageDialog(this, "수정 완료");
						jTableRefresh();
						contentSet();
						contentClear();
						namefld.setFocusable(true);
					}
				}

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "수정 실패");
				JOptionPane.showMessageDialog(this, "값을 확인해주세요");
			}
		} else if (ButtonFlag.equals("나가기")) {
			int result = JOptionPane.showConfirmDialog(null, "나가시겠습니까?", "Exit", JOptionPane.OK_CANCEL_OPTION);
			if (result == 0) {
				new Login2();
				main.dispose();
			}
		}

	}

	public void jTableRefresh() {
		// 테이블 내용 갱신 메서드
		DefaultTableModel model = new DefaultTableModel(searchDAO.getProfile(), col) {

			public boolean isCellEditable(int row, int colum) {
				return false;
			}
		};
		table.setModel(model);
		JTableSet();
	}

	public void contentClear() {
		namefld.setText("");
		idfld.setText("");
		passfld.setText("");
		genderfld.setText("");
		heightfld.setText("");
		sholfld.setText("");
		chestfld.setText("");
		waistfld.setText("");
		legfld.setText("");
	}

	public void contentSet() {
		searchDTO = new SearchDTO();
		String name = namefld.getText();
		String id = idfld.getText();
		String pw = passfld.getText();
		String gender = genderfld.getText();
		double height = Double.parseDouble(heightfld.getText());
		double shoulder = Double.parseDouble(sholfld.getText());
		double chest = Double.parseDouble(chestfld.getText());
		double waist = Double.parseDouble(waistfld.getText());
		double leg = Double.parseDouble(legfld.getText());

		searchDTO.setName(name);
		searchDTO.setId(id);
		searchDTO.setPassword(pw);
		searchDTO.setGender(gender);
		searchDTO.setHeight(height);
		searchDTO.setShoulder(shoulder);
		searchDTO.setChest(chest);
		searchDTO.setWaist(waist);
		searchDTO.setLeg(leg);
	}

	public static void main(String[] args) {
		new SearchMain();

	}

}

