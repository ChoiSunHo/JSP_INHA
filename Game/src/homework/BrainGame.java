package homework;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

public class BrainGame extends JFrame implements ItemListener, ActionListener, MouseListener { /**
 * 
 */
	private static final long serialVersionUID = 5085646369979517666L; // main_~~ : 메인화면,  bs_~~ : 이삭 게임 프로그램, brc_~~ : 선호 게임 프로그램, is_~~~ : 명진 게임 프로그램

	public static enum Game { // 버튼찾기게임, 순서기억게임, 순간시게임
		ButtonSearch, ButtonRememberClick, InstantaneousSee
	};
	
	public enum Level {
		EASY, NORMAL, HARD, EXPERT
	}

	Level nowLevel = Level.NORMAL;
	Level playedLevel; // ------------------------------------------ 현재 레벨 (게임종료시  playedLevel = nowLevel; 코드 작성바람)
	
	Game nowGame = Game.ButtonSearch;
	Game playedGame; // ------------------------------------------ 현재 게임 (게임종료시  playedGame = nowGame; 코드 작성바람)
	
	Connection conn;
	Statement stmt; 
	String sql;// ------------------------------------------- DB
	
	private JLabel main_1st_p; // 메인 최상위 컨테이너
	private JPanel main_center_p; // 메인 중앙 컨테이너
	private JButton main_start_btn; // 게임 Start 버튼 ---------------------------------- 하단에 함수 추가바람
	private JTextField main_gameTitleTF, main_gameScoreTF; // 타이틀, 스코어 text
	private ButtonGroup main_gameSet_rg; // 게임 버튼그룹
	private JRadioButton main_game_bs_rbtn; // ------------------------------------------------- 버튼찾기게임
	private JRadioButton main_game_brc_rbtn; // ------------------------------------------------- 순서기억게임
	private JRadioButton main_game_is_rbtn; // ------------------------------------------------- 순간시게임
	private JPanel main_game_rbtn_p; // 게임 컨테이너
	private ButtonGroup main_levelset_rg; // 레벨 버튼그룹 
	private JRadioButton main_ez_rbtn;
	private JRadioButton main_nm_rbtn;
	private JRadioButton main_hd_rbtn;
	private JRadioButton main_ex_rbtn;
	private JPanel main_level_rbtn_p; // 난이도 컨테이너
	private Timer main_t; // 3 2 1 카운터
	private int main_cnt; // 카운터 시간 체크

	// ---------------------------------------------------------------------------------------------------------------

	private final String bs_data_condition[] = { "글자색과 글자가 일치하는 버튼 클릭", "배경색과 글자가 일치하는 버튼 클릭" }; // 문제
	private final String bs_data_name[] = { "빨강", "주황", "노랑", "초록", "파랑", "보라", "하양", "검정" }; // 색이름

	private final Color[] bs_nowColor = { Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE,
			new Color(120, 43, 144), Color.WHITE, Color.BLACK }; // 색

	private Random bs_random; // 게임 랜덤생성

	private JPanel bs_gamePan; // 게임 최상위 컨테이너
	private JPanel bs_btnsPan; // 게임 버튼 컨테이너
	private JPanel bs_bottomPan; // 게임 하단 컨테이너
	private JPanel bs_bottom_centerPan; // 게임 하단 중앙 컨테이너
	private JTextField bs_gameNameTF, bs_questionTF, bs_scoreTF; // 게임명, 문제, 점수 text
	private JTextField bs_comboTF; // combo text

	private ArrayList<JButton> bs_arr_btn; // 버튼 객체 리스트
	private JProgressBar bs_time_progress; // 남은시간 화면출력
	private int bs_cnt = 3000; // 남은시간
	private int bs_cntMax = 3000; // 이 게임 최대시간
	private int bs_score = 0; // 점수
	private int bs_combo_score = 0; // 콤보
	private int bs_combo_time = 0; // 콤보 유효시간
	private int bs_true_answer_pos = 0; // 정답위치
	private int bs_btn_count = 0; // 버튼 개수
	private String bs_question; // 현재 질문

	private Timer bs_timecheck; // 게임 진행시간
	private Thread bs_gameThread; // 실제 게임 쓰레드	
	private boolean bs_making; // 버튼 요소 생성중
	private boolean bs_isLive = false; // 게임 쓰레드 생존여부
	private boolean bs_isEnd = false; // 게임 종료 여부
	private String userName = "";
	private JButton main_scoreList;


	public BrainGame() {
		setTitle("브레인 게임");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(500, 300);

		bs_init();

		main_1st_p = new JLabel("",SwingConstants.CENTER);
		main_center_p = new JPanel();
		main_center_p.setLayout(new BoxLayout(main_center_p, BoxLayout.Y_AXIS));
		main_center_p.setOpaque(false);

		main_gameTitleTF = new JTextField("Brain Game");
		main_gameTitleTF.setFont(new Font("궁서", Font.BOLD, 30));
		main_gameTitleTF.setEditable(false);
		main_gameTitleTF.setOpaque(false);
		main_gameTitleTF.setForeground(Color.MAGENTA);
		main_gameTitleTF.setBorder(null);
		main_gameTitleTF.setHorizontalAlignment(JTextField.CENTER);

		main_gameScoreTF = new JTextField();
		main_gameScoreTF.setEditable(false);
		main_gameScoreTF.setOpaque(false);
		main_gameScoreTF.setBorder(null);
		main_gameScoreTF.setForeground(Color.YELLOW);
		main_gameScoreTF.setFont(new Font("견고딕", Font.BOLD, 30));
		main_gameScoreTF.setHorizontalAlignment(JTextField.CENTER);

		main_1st_p.setLayout(new BorderLayout(100,100));

		try {
			main_1st_p.setIcon(new ImageIcon(imageResize(ImageIO.read(new File("images/main_background.jpg")), 700, 455)));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		main_start_btn = new JButton("Start");
		main_start_btn.setAlignmentX(Component.CENTER_ALIGNMENT);
		main_start_btn.setPreferredSize(new Dimension(280, 50));
		main_start_btn.setMaximumSize(new Dimension(280, 50));
		main_start_btn.setFont(new Font("굴림", Font.BOLD, 20));
		main_t = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				main_cnt -= 1000;
				if(main_cnt == 3000) drawing_count("images/three.png");
				else if(main_cnt == 2000) drawing_count("images/two.png");
				else if(main_cnt == 1000) drawing_count("images/one.png");				
				else if(main_cnt <= 0){
					main_1st_p.removeAll();
					revalidate();
					repaint();
					try {
						main_1st_p.setIcon(new ImageIcon(imageResize(ImageIO.read(new File("images/main_background.jpg")), 700, 455)));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					main_t.stop();
					switch(nowGame){ // ----------------------------------------------------------------- ((게임함수 추가부분)) 
					case ButtonSearch: // 버튼 찾기 게임
						bs_startGame();
						break;
					case ButtonRememberClick: // TODO 순서기억 게임
						brc_startGame();
						break;
					case InstantaneousSee: // TODO 순간시 게임
						is_startGame();
						break;
					default:
						break;
					}
				}
			}		
		});
		main_start_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				main_t.start();
				main_1st_p.removeAll();
				main_cnt = 4000;
				revalidate();
				repaint();
			}
		});
		main_levelset_rg = new ButtonGroup(); ////////////////

		main_ez_rbtn = new JRadioButton("EASY");
		main_nm_rbtn = new JRadioButton("NORMAL", true);
		main_hd_rbtn = new JRadioButton("HARD");
		main_ex_rbtn = new JRadioButton("EXPERT");

		main_levelset_rg.add(main_ez_rbtn);
		main_levelset_rg.add(main_nm_rbtn);
		main_levelset_rg.add(main_hd_rbtn);
		main_levelset_rg.add(main_ex_rbtn);

		main_ez_rbtn.setBackground(new Color(0, 230, 0));
		main_nm_rbtn.setBackground(new Color(50, 150, 130));
		main_hd_rbtn.setBackground(new Color(250, 0, 0));
		main_ex_rbtn.setBackground(new Color(150, 50, 150));

		main_ez_rbtn.setForeground(Color.BLACK);
		main_nm_rbtn.setForeground(Color.YELLOW);
		main_hd_rbtn.setForeground(Color.BLACK);
		main_ex_rbtn.setForeground(Color.BLACK);

		main_level_rbtn_p = new JPanel(new FlowLayout());
		main_level_rbtn_p.setOpaque(false);

		main_level_rbtn_p.add(main_ez_rbtn);
		main_level_rbtn_p.add(main_nm_rbtn);
		main_level_rbtn_p.add(main_hd_rbtn);
		main_level_rbtn_p.add(main_ex_rbtn);

		main_ez_rbtn.addItemListener(this);
		main_nm_rbtn.addItemListener(this);
		main_hd_rbtn.addItemListener(this);
		main_ex_rbtn.addItemListener(this);

		main_gameSet_rg = new ButtonGroup(); //////////////

		main_game_bs_rbtn = new JRadioButton("버튼찾기",true);
		main_game_brc_rbtn = new JRadioButton("순서기억");
		main_game_is_rbtn = new JRadioButton("순간시");

		main_game_bs_rbtn.setBackground(Color.LIGHT_GRAY);
		main_game_brc_rbtn.setBackground(Color.LIGHT_GRAY);
		main_game_is_rbtn.setBackground(Color.LIGHT_GRAY);

		main_game_bs_rbtn.setForeground(Color.YELLOW);
		main_game_brc_rbtn.setForeground(Color.BLACK);
		main_game_is_rbtn.setForeground(Color.BLACK);

		main_game_bs_rbtn.addItemListener(this);
		main_game_brc_rbtn.addItemListener(this);
		main_game_is_rbtn.addItemListener(this);

		main_game_rbtn_p = new JPanel(new FlowLayout());
		main_game_rbtn_p.setOpaque(false);

		main_scoreList = new JButton("점수판");
		main_scoreList.setPreferredSize(new Dimension(80, 25));
		main_scoreList.addActionListener(new ActionListener() { // XXX 점수판
			
			@Override
			public void actionPerformed(ActionEvent e) {
						new BrainGameScore(nowGame, nowLevel.name());
			}
		});
		
		main_gameSet_rg.add(main_game_bs_rbtn);
		main_gameSet_rg.add(main_game_brc_rbtn);
		main_gameSet_rg.add(main_game_is_rbtn);	

		main_game_rbtn_p.add(main_game_bs_rbtn);
		main_game_rbtn_p.add(main_game_brc_rbtn);
		main_game_rbtn_p.add(main_game_is_rbtn);	
		main_game_rbtn_p.add(main_scoreList);
		
		main_center_p.add(main_gameTitleTF);
		main_center_p.add(main_gameScoreTF);
		main_center_p.add(main_game_rbtn_p);
		main_center_p.add(main_level_rbtn_p);
		main_center_p.add(main_start_btn);

		main_1st_p.add(main_center_p, BorderLayout.CENTER);

		add(main_1st_p);


		setSize(700, 500);
		setMinimumSize(new Dimension(700, 500));
		setVisible(true);
		main_start_btn.requestFocus();
	}

	private void bs_init() {
		bs_random = new Random();

		bs_gamePan = new JPanel();
		bs_gamePan.setLayout(new BoxLayout(bs_gamePan, BoxLayout.Y_AXIS));
		bs_btnsPan = new JPanel(new GridLayout(2, 5, 30, 30));
		bs_bottomPan = new JPanel(new BorderLayout());
		bs_gameNameTF = new JTextField();
		bs_questionTF = new JTextField();
		bs_arr_btn = new ArrayList<JButton>();
		bs_time_progress = new JProgressBar(SwingConstants.HORIZONTAL, 0, 100);
		bs_time_progress.setPreferredSize(new Dimension(300, bs_time_progress.getMinimumSize().height));

		bs_scoreTF = new JTextField();

		bs_bottom_centerPan = new JPanel(new BorderLayout());
		bs_comboTF = new JTextField();
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.DESELECTED)
			return;

		if (main_ez_rbtn.isSelected()) {
			nowLevel = Level.EASY;

			main_ez_rbtn.setForeground(Color.YELLOW);
			main_nm_rbtn.setForeground(Color.BLACK);
			main_hd_rbtn.setForeground(Color.BLACK);
			main_ex_rbtn.setForeground(Color.BLACK);
		} else if (main_nm_rbtn.isSelected()) {
			nowLevel = Level.NORMAL;

			main_ez_rbtn.setForeground(Color.BLACK);
			main_nm_rbtn.setForeground(Color.YELLOW);
			main_hd_rbtn.setForeground(Color.BLACK);
			main_ex_rbtn.setForeground(Color.BLACK);
		} else if (main_hd_rbtn.isSelected()) {
			nowLevel = Level.HARD;

			main_ez_rbtn.setForeground(Color.BLACK);
			main_nm_rbtn.setForeground(Color.BLACK);
			main_hd_rbtn.setForeground(Color.YELLOW);
			main_ex_rbtn.setForeground(Color.BLACK);
		} else if (main_ex_rbtn.isSelected()) {
			nowLevel = Level.EXPERT;

			main_ez_rbtn.setForeground(Color.BLACK);
			main_nm_rbtn.setForeground(Color.BLACK);
			main_hd_rbtn.setForeground(Color.BLACK);
			main_ex_rbtn.setForeground(Color.YELLOW);
		} 
		if(main_game_bs_rbtn.isSelected()){
			nowGame = Game.ButtonSearch;

			main_game_bs_rbtn.setForeground(Color.YELLOW);
			main_game_brc_rbtn.setForeground(Color.BLACK);
			main_game_is_rbtn.setForeground(Color.BLACK);
			
			main_ez_rbtn.setEnabled(true);
			main_nm_rbtn.setEnabled(true);
			main_hd_rbtn.setEnabled(true);
			main_ex_rbtn.setEnabled(true);
		} else if(main_game_brc_rbtn.isSelected()){
			nowGame = Game.ButtonRememberClick;

			main_game_bs_rbtn.setForeground(Color.BLACK);
			main_game_brc_rbtn.setForeground(Color.YELLOW);
			main_game_is_rbtn.setForeground(Color.BLACK);
			
			main_ez_rbtn.setEnabled(true);
			main_nm_rbtn.setEnabled(true);
			main_hd_rbtn.setEnabled(true);
			main_ex_rbtn.setEnabled(true);
		} else if(main_game_is_rbtn.isSelected()){
			nowGame = Game.InstantaneousSee;

			main_game_bs_rbtn.setForeground(Color.BLACK);
			main_game_brc_rbtn.setForeground(Color.BLACK);
			main_game_is_rbtn.setForeground(Color.YELLOW);
			
			main_ez_rbtn.setEnabled(false);
			main_nm_rbtn.setEnabled(false);
			main_hd_rbtn.setEnabled(false);
			main_ex_rbtn.setEnabled(false);
		}

		revalidate();
		repaint();
	}

	private BufferedImage imageResize(BufferedImage img, int newW, int newH) { // 이미지 리사이즈
		Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
		BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = dimg.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();

		return dimg;
	}  

	private void drawing_count(String path) { // 카운트 이미지출력
		try {
			BufferedImage myPicture = ImageIO.read(new File(path));
			myPicture = imageResize(myPicture, 328, 445);
			main_1st_p.setIcon(new ImageIcon(myPicture));
			revalidate();
			repaint();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void gameResult(int score) { // XXX 게임 종료 화면
		getContentPane().removeAll();
		main_1st_p.add(main_center_p);
		getContentPane().add(main_1st_p); // 삭제하고 다시 메인화면으로 변경

		playedLevel = nowLevel; // 플레이한 레벨
		playedGame = nowGame; // 플레이한 게임
		
		int result = JOptionPane.showConfirmDialog(null, "Score : "+score+"점 기록을 저장하시겠습니까?","기록저장 여부",JOptionPane.OK_CANCEL_OPTION);
		
		if(result == JOptionPane.OK_OPTION){
			userName = JOptionPane.showInputDialog("Score : "+score+"점 { [이름]을 적고 [확인]버튼 클릭 }",userName); 
			
			if(userName == null || userName.replace(" ","").equals("")){
				userName = "NoName";
			}

			/**
			 *  @values
			 *  game_txt 게임명
			 *  level_txt 난이도
			 *  score 점수
			 *  userName 유저명
			 *  
			 *  추가사항 : 날짜 (SYSDATE) 등
			 */
			// TODO 여기에 DB INSERT 구문 Query 추가바람
			// insert into score values('홍길동', 40, 'EASY', curdate(), 'ButtonSearch');
			
			try {
				conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/gameDB?useSSL=false&user=root&password=##ace9715");
				stmt = conn.createStatement();
				
				sql = "insert into score values( '" + userName + 
	                    "', " + score + ", '" + playedLevel + "', curdate(), '" + playedGame + "')";
	            
	            stmt.executeUpdate(sql); //쿼리문 전송
	            
	            stmt.close();
	            conn.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		// main_gameScoreTF.setText("SCORE : "+score + "점 UserName : "+userName); // TODO 플레이한 점수 & 이름 메인화면에 표시
		
		if(is_restrat == true){
		// 취소 버튼 누를 시 task를 다시 재정의 하여 동작하게 한다. InstantaneousSee 게임
		@SuppressWarnings("unused")
		java.util.Timer is_text_t = new java.util.Timer();
		@SuppressWarnings("unused")
		java.util.TimerTask is_task = new java.util.TimerTask() {   		// 원하는 시간에 수행되도록 한다.
			@Override
			public void run() {
				is_startT();
				is_gameT.setVisible(false);
			}
		};
		// 점수 밑 게임 시간 정지
		is_score = 0;
		is_score_l = new JLabel("점수 : " + is_score + "점 ");
		is_mTimer.cancel();
		is_restrat = false;
		}
		

		revalidate();
		repaint();
	}

	private void bs_startGame() {
		remove(main_1st_p);
		bs_score = 0;
		bs_combo_score = 0;

		bs_gamePan.add(bs_gameNameTF);
		bs_gamePan.add(bs_questionTF);
		bs_gamePan.add(bs_btnsPan);
		bs_gamePan.add(bs_bottomPan);
		add(bs_gamePan);

		bs_gameNameTF.setText("Brain Game");
		bs_gameNameTF.setHorizontalAlignment(JTextField.CENTER);
		bs_gameNameTF.setEditable(false);
		bs_gameNameTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, bs_gameNameTF.getMinimumSize().height));
		bs_gameNameTF.setFont(new Font("굴림", Font.BOLD, 12));

		bs_questionTF.setHorizontalAlignment(JTextField.CENTER);
		bs_questionTF.setEditable(false);
		bs_questionTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, bs_questionTF.getMinimumSize().height));
		bs_questionTF.setFont(new Font("굴림", Font.BOLD, 25));

		bs_scoreTF.setText("점수 : 0");
		bs_scoreTF.setHorizontalAlignment(JTextField.LEFT);
		bs_scoreTF.setEditable(false);

		bs_bottomPan.add(bs_scoreTF, BorderLayout.WEST);
		bs_bottomPan.add(bs_bottom_centerPan, BorderLayout.CENTER);
		bs_bottomPan.add(bs_time_progress, BorderLayout.EAST);
		bs_bottomPan.setMaximumSize(new Dimension(Integer.MAX_VALUE, bs_questionTF.getMinimumSize().height));

		bs_comboTF.setText("0 COMBO");
		bs_comboTF.setEditable(false);
		bs_comboTF.setMaximumSize(new Dimension(Integer.MAX_VALUE, bs_gameNameTF.getMinimumSize().height));
		bs_comboTF.setForeground(Color.MAGENTA);
		bs_comboTF.setFont(new Font("굴림", Font.BOLD, 12));

		bs_bottom_centerPan.add(bs_comboTF, BorderLayout.EAST);

		switch (nowLevel) {
		case EASY:
			bs_btn_count = 4;
			bs_cnt = 90000;
			break;
		case NORMAL:
			bs_btn_count = 6;
			bs_cnt = 80000;
			break;
		case HARD:
			bs_btn_count = 10;
			bs_cnt = 70000;
			break;
		case EXPERT:
			bs_btn_count = 30;
			bs_cnt = 60000;
		default:
			break;
		}
		bs_cntMax = bs_cnt;
		bs_time_progress.setMaximum(bs_cntMax);
		if (nowLevel != Level.EXPERT)
			bs_btnsPan.setLayout(new GridLayout((int) bs_btn_count / 2, 2, 10, 10));
		else
			bs_btnsPan.setLayout(new GridLayout(5, 6, 10, 10));

		bs_timecheck = new Timer(100, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				bs_cnt -= 100;

				if (bs_cnt > bs_cntMax)
					bs_cnt = bs_cntMax;
				bs_time_progress.setValue(bs_cnt);
				if (bs_cnt <= 0) {
					bs_isEnd = true;
					bs_gameThread.interrupt();

					gameResult(bs_score);

					bs_timecheck.stop();
				}
			}
		});

		bs_gameThread = new Thread(new Runnable() {

			@Override
			public void run() {

				synchronized (bs_gameThread) {
					while (bs_isLive) {
						try {
							Thread.sleep(1); // 이걸 하면 빠르게 맞춰도 문제가 발생하지 않음
						} catch (InterruptedException e1) {
						}
						bs_arr_btn.clear();
						bs_btnsPan.removeAll();
						bs_question = bs_data_condition[bs_random.nextInt(bs_data_condition.length)];
						bs_questionTF.setText(bs_question);

						bs_true_answer_pos = bs_random.nextInt(bs_btn_count);

						for (int i = 0; i < bs_btn_count; i++) { // 게임 데이터 생성
							int name = bs_random.nextInt(bs_data_name.length);
							int bgColor = bs_random.nextInt(bs_nowColor.length);
							int fgColor = bs_random.nextInt(bs_nowColor.length);
							bs_making = true;

							if (bs_question.equals(bs_data_condition[0])) { // 글자색==글자?
								while (bs_making) {
									if (name == fgColor) {
										if (bs_true_answer_pos == i && fgColor != bgColor) {
											bs_making = false;
										} else {
											name = bs_random.nextInt(bs_data_name.length);
											bgColor = bs_random.nextInt(bs_nowColor.length);
											fgColor = bs_random.nextInt(bs_nowColor.length);
										}
									} else {
										if (bs_true_answer_pos != i && fgColor != bgColor) {
											bs_making = false;
										} else {
											name = bs_random.nextInt(bs_data_name.length);
											bgColor = bs_random.nextInt(bs_nowColor.length);
											fgColor = bs_random.nextInt(bs_nowColor.length);
										}
									}
								}
							} else if (bs_question.equals(bs_data_condition[1])) { // 배경색==글자?
								while (bs_making) {
									if (name == bgColor) {
										if (bs_true_answer_pos == i && fgColor != bgColor) {
											bs_making = false;
										} else {
											name = bs_random.nextInt(bs_data_name.length);
											bgColor = bs_random.nextInt(bs_nowColor.length);
											fgColor = bs_random.nextInt(bs_nowColor.length);
										}
									} else {
										if (bs_true_answer_pos != i && fgColor != bgColor) {
											bs_making = false;
										} else {
											name = bs_random.nextInt(bs_data_name.length);
											bgColor = bs_random.nextInt(bs_nowColor.length);
											fgColor = bs_random.nextInt(bs_nowColor.length);
										}
									}
								}
							}

							JButton make_btn = new JButton(bs_data_name[name]);
							make_btn.setBackground(bs_nowColor[bgColor]);
							make_btn.setForeground(bs_nowColor[fgColor]);
							make_btn.setFont(new Font("굴림", Font.BOLD, 25));
							make_btn.addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent e) {
									JButton btn = (JButton) e.getSource();
									String color = btn.getText().toString();

									int pos = 0;

									for (int i = 0; i < bs_data_name.length; i++) {
										if (color.equals(bs_data_name[i])) {
											pos = i;
											break;
										}
									}

									if (bs_question.equals(bs_data_condition[0])) {
										if (btn.getForeground() == bs_nowColor[pos]) {
											if (bs_combo_time - bs_cnt <= 5000)
												bs_combo_score++;
											else
												bs_combo_score = 1;

											if (bs_combo_score == 0)
												bs_combo_score = 1;

											bs_score += 10 * bs_combo_score;

											if (bs_cnt + 3000 > bs_cntMax)
												bs_cnt = bs_cntMax;
											else
												bs_cnt += 3000;

											bs_comboTF.setText(bs_combo_score+" COMBO");
											bs_gameThread.interrupt();
										} else {
											bs_cnt -= 5000;
											bs_combo_score = 0;
											bs_comboTF.setText(bs_combo_score+" COMBO");
										}
									} else {
										if (btn.getBackground() == bs_nowColor[pos]) {
											if (bs_combo_time - bs_cnt <= 5000)
												bs_combo_score++;
											else
												bs_combo_score = 1;

											if (bs_combo_score == 0)
												bs_combo_score = 1;

											bs_score += 10 * bs_combo_score;

											if (bs_cnt + 3000 > bs_cntMax)
												bs_cnt = bs_cntMax;
											else
												bs_cnt += 3000;

											bs_comboTF.setText(bs_combo_score+" COMBO");
											bs_gameThread.interrupt();
										} else {
											bs_cnt -= 5000;
											bs_combo_score = 0;
											bs_comboTF.setText(bs_combo_score+" COMBO");
										}
									}
									bs_scoreTF.setText("점수 : " + bs_score);
								}
							});
							bs_arr_btn.add(make_btn);
							bs_btnsPan.add(make_btn);
						}
						bs_combo_time = bs_cnt;
						validate();
						repaint();
						try {
							bs_gameThread.wait();
						} catch (InterruptedException e) {

							if (bs_isEnd) {
								bs_isEnd = false;
								return;
							} else {
								bs_gameThread.notify();
							}
						}
					}
				}
			}
		});

		bs_isLive = true;
		bs_gameThread.start();
		bs_timecheck.start();
	}
	///////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////


	//변수 정리


	private JPanel brc_mainPan; // 최상위 컨테이너
	private JPanel brc_southPan; // 하단 컨테이너

	private JPanel brc_btnPan; // 버튼 컨테이너

	private JLabel brc_name; // 순서기억게임
	private JLabel brc_info; // 난이도, 점수, 연속, 기회

	private JButton brc_btn[]; // 24 개의 버튼
	
	private int brc_score = 0; // 점수
	private int brc_count = 0; // 연속
	private int brc_life = 3; // 기회
	
	private Color brc_back = new Color(10,178,255); //배경 색깔



	private boolean brc_next = true; // 문제를 맞추거나 틀렸을때 새로운 문제를 불러오기 위한 변수 
	private int[] brc_btnQuestion; // 맞춰야하는 버튼 배열의 인덱스를 담을 변수
	private int brc_order = 0; // 플레이어가 맞춰야할 brc_btnQuestion 인덱스
	
	private Thread brc_gameThread;
	private boolean brc_isLive = false;

	/////////////////////////////////////////////////////////////////////////////////////

	private void brc_startGame() {
		remove(main_1st_p); // 이전 컨테이너 제거

		brc_mainPan = new JPanel(new BorderLayout());

		brc_southPan = new JPanel(new GridLayout(2,1));
		brc_southPan.setBackground(brc_back);

		brc_btnPan = new JPanel(new GridLayout(4,6,5,5));
		brc_btnPan.setBackground(brc_back);

		brc_name = new JLabel("순서 기억 게임");
		brc_info = new JLabel("난이도 : " + nowLevel + "  점수 : " +
				brc_score + "  연속 : " + brc_count + "  기회 : " + brc_life);
		brc_name.setFont(new Font("HY견고딕", Font.BOLD, 20));
		brc_info.setFont(new Font("HY견고딕", Font.BOLD, 20));

		brc_btn = new JButton[24];

		for (int i = 0 ; i < brc_btn.length ; i++){ // 24개의 버튼 생성
			brc_btn[i] = new JButton(new ImageIcon("images/cloud.png"));
			brc_btn[i].setBorderPainted(false); // 외곽선 없애기
			brc_btn[i].setFocusPainted(false); // 선택 되었을 때 생기는 테두리 없애기
			brc_btn[i].setBackground(Color.WHITE);
			brc_btnPan.add(brc_btn[i]);
			brc_btn[i].addActionListener(this);
		}

		brc_southPan.add(brc_name);
		brc_southPan.add(brc_info);
		brc_mainPan.add(brc_btnPan, BorderLayout.CENTER);
		brc_mainPan.add(brc_southPan, BorderLayout.SOUTH);
		brc_name.setHorizontalAlignment(JLabel.CENTER);
		brc_info.setHorizontalAlignment(JLabel.CENTER);
		add(brc_mainPan);

		
		// 난이도에 따라 외워야하는 버튼 갯수 설정
		
		switch (nowLevel) {
		case EASY:
			brc_btnQuestion = new int[4];
			break;
		case NORMAL:
			brc_btnQuestion = new int[6];
			break;
		case HARD:
			brc_btnQuestion = new int[8];
			break;
		case EXPERT:
			brc_btnQuestion = new int[10];
		default:
			break;
		}

	/////////////////////////////////////////////////////////////////////////////////

		brc_gameThread = new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (brc_gameThread) {			
					while(brc_isLive){
						try {
							Thread.sleep(1);
						} catch (InterruptedException e2) {
							return;
						}
		
						if ( brc_next ){
							brc_order = 0; //맞춰야할 버튼배열 인덱스 초기화
		
							// 버튼 색깔 초기화
							for (int i = 0 ; i < brc_btn.length ; i++){
								brc_btn[i].setBackground(Color.WHITE);
							}
		
							for( int i = 0 ; i < brc_btnQuestion.length ; i++ ){ // 눌러야할 버튼 랜덤으로 생성
								brc_btnQuestion[i] = ( (int)(Math.random() * 100) ) % 24; // 0~23까지 랜덤값을 대입
		
								for( int j = 0; j < i ; j++ ){ // 중복 방지
									while( brc_btnQuestion[j] == brc_btnQuestion[i] ){
										brc_btnQuestion[i] = ( (int)(Math.random() * 100) ) % 24;
									}
								}
							}
							
							
							try { // 1초 여유 시간
								Thread.sleep(1000);
							} catch (InterruptedException e1) {
								return;
							}
		
		
		
							for (int i = 0 ; i < brc_btnQuestion.length ; i++){ // 눌러야 할 버튼 보여주기
								
								brc_btn[brc_btnQuestion[i]].setBackground(Color.BLACK);
		
								try {
									Thread.sleep(800);
								} catch (InterruptedException e) {
									return;
								}
		
								brc_btn[brc_btnQuestion[i]].setBackground(Color.WHITE);
							}
		
							brc_next = false;
						}
					}
				}
			}
		});

	brc_isLive = true;
	brc_gameThread.start();

	}



	/////////////////////////////////////////////////////////////////////////
	
	private JLabel is_jl;
	private JPanel is_game; 					// 문제가 나오는 창
	private JTextField is_tf;  						// 입력
	private JLabel is_gameT,is_subtitle, is_level;
	private JButton is_jb;
	private JLabel is_score_l, is_timer_t; 		  // 점수와 시간 라벨
	private int is_score = 0;
	private int is_FirstS = 800; 					// 1 단계
	private int is_SecS = 500; 						// 2 단계
	private int is_ThrS = 250;						// 3 단계

	private boolean is_restrat = false;			// 게임 초기화면 복귀 시 다른 게임에 방해 안하도록 설정
	
	private java.util.Timer is_mTimer;      // 게임 전체 시간 명
	private TimerTask is_stT;
	
	private int is_mTime = 40;         				 // 게임 전체 시간

	private java.util.Timer is_st;			// 문제로 나오게 되는 문장들의 시간을 정한다.
	private TimerTask is_sts;
	
	private boolean is_isPlaying = false;

	public void is_startT(){           // 전체 게임 시간 계산
		is_stT = new TimerTask() {

			@Override
			public void run() {
				is_mTime--;
				for (int i = 0; i < is_mTime; i++) {
					is_timer_t.setText("남은 시간 : " + is_mTime + "초");
				}
				if(is_mTime ==0){
					is_timer_t.setText("TIME OVER!!");
					is_isPlaying = false;
					is_text_t.cancel();
					is_text_t = null;
					is_endG();
				}
			}
		};
		is_mTimer.schedule(is_stT, 1000,1000);
	}
	
	int is_len=4;			

	ArrayList<String> is_gaText = new ArrayList<>();   	//  level1. 문제
	ArrayList<String> is_gaText2 = new ArrayList<>();	//  level2. 문제
	ArrayList<String> is_gaText3 = new ArrayList<>();    //  level3. 문제
	
	
	int is_val =0;                       
	String is_rval;
	Random is_ran = new Random();   // 게임 텍스트 랜덤으로 출력하기 위해



	private void is_startGame(){     //게임 레이아웃
		remove(main_1st_p);
		is_mTime = 40;
		
		is_st = new java.util.Timer();
		is_mTimer  = new java.util.Timer();
		is_text_t  = new java.util.Timer();

		LineBorder is_back_sh = new LineBorder(Color.RED);   // 가장자리 테두리


		is_timer_t = new JLabel("남은 시간 : " + is_mTime + "초 ");      //시간 레이아웃
		is_timer_t.setFont(new Font("견고딕", Font.BOLD, 25));
		is_timer_t.setHorizontalAlignment(SwingConstants.RIGHT);
		is_timer_t.setBorder(is_back_sh);
		is_timer_t.setOpaque(true);   // 라벨의 기본 배경은 투명이기에 설정
		is_timer_t.setBackground(new Color(212,40,250));
		is_timer_t.setForeground(new Color(255,255,255));
		is_timer_t.setSize(50, 40);


		is_score_l = new JLabel("점수 : " + is_score + "점 ");     //점수 레이아웃
		is_score_l.setFont(new Font("궁서", Font.BOLD, 25));
		is_score_l.setHorizontalAlignment(SwingConstants.LEFT);
		is_score_l.setBorder(is_back_sh);
		is_score_l.setOpaque(true);
		is_score_l.setBackground(Color.ORANGE);
		is_score_l.setSize(50, 40);
		
		is_gaText.add("마이크");   // 1단계
		is_gaText.add("낙석");
		is_gaText.add("무대");
		is_gaText.add("다람쥐");
		is_gaText.add("달걀");
		is_gaText.add("의자");
		is_gaText.add("생물");
		is_gaText.add("나뭇잎");
		is_gaText.add("별자리");
		is_gaText.add("개구리");
		is_gaText.add("토끼");
		is_gaText.add("새우");
		is_gaText.add("노트북");

		is_gaText2.add("눈 가리고 아웅");   // 2단계
		is_gaText2.add("가는 날이 장날이다");
		is_gaText2.add("가뭄에 콩 나듯");
		is_gaText2.add("공든 탑이 무너지랴");
		is_gaText2.add("금강산도 식후경");
		is_gaText2.add("간에 기별도 안 간다");
		is_gaText2.add("갈수록 태산");
		is_gaText2.add("세월이 약이다");
		is_gaText2.add("소귀에 경 읽기");
		is_gaText2.add("우물 안의 개구리");
		is_gaText2.add("수박 겉 핥기");
		is_gaText2.add("설마가 사람 잡는다");
		is_gaText2.add("티끌 모아 태산");
		
		is_gaText3.add("@@66");    // 3단계
		is_gaText3.add("*456*");
		is_gaText3.add("54867");
		is_gaText3.add("1010235");
		is_gaText3.add("!2017!");
		is_gaText3.add("2486");
		is_gaText3.add("54823");
		is_gaText3.add("###1");
		is_gaText3.add(">><<");
		is_gaText3.add("54869");
		is_gaText3.add("245 85");
		is_gaText3.add("48576");
		is_gaText3.add("1 1 1 1");
		
		is_jl = new JLabel();
		try {
			is_jl.setIcon(new ImageIcon(imageResize(ImageIO.read(new File("images/back_ground.png")), 700, 666)));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		is_jl.setLayout(new FlowLayout());
		is_game = new JPanel();
		is_tf = new JTextField(20);  												 // 글자 입력
		is_gameT = new JLabel(" 순간시 게임");  									 //문제가 나오는 라벨
		is_subtitle = new JLabel("* 클릭시 게임을 시작합니다."); 				 // 간단한 설명문
		is_jb = new JButton("확인");
		is_jb.setSize(30, 30);
		is_level = new JLabel("Level_1");
				
		
		
		
		LineBorder is_sh = new LineBorder(new Color(250,255,96));				// 가장자리 테두리 색상
		Font is_msg = new Font("HY견고딕", Font.ROMAN_BASELINE, 25); 		 // 문장 폰트 설정
		
		Font is_level_l = new Font("Serif", Font.ITALIC, 25);    						// 레벨 폰트 설정
		
		
		// 게임창 레이아웃
		is_game.setBackground(new Color(25,230,255));
		is_game.setBorder(is_sh);
		is_game.setPreferredSize(new Dimension(500, 360));  						//layout에서 크기 설정하기 위해 사용
		is_game.setLayout(new BorderLayout(10,10));
		is_gameT.setHorizontalAlignment(SwingConstants.CENTER);				 //텍스트 정렬
		is_gameT.setFont(is_msg);
		
		
		is_subtitle.setForeground(Color.RED);					// 안내창 레이아웃
		
		
		is_level.setOpaque(true);										// 레벨 레이아웃
		is_level.setBackground(new Color(10,220,210));
		is_level.setFont(is_level_l);
		is_level.setForeground(new Color(120,10,255));
		is_level.setHorizontalAlignment(SwingConstants.CENTER);
		is_level.setHorizontalAlignment(JLabel.CENTER);
		is_level.setBorder(is_sh);		

		is_game.addMouseListener(this);
		is_jb.addActionListener(this);
		is_tf.addActionListener(this);
		
		add(is_jl);
		is_jl.add(is_score_l);
		is_jl.add(is_timer_t);
		is_game.add(is_gameT, "Center");
		is_game.add(is_subtitle, "South");
		is_jl.add(is_level);
		is_jl.add(is_game);
		is_jl.add(is_tf);
		is_jl.add(is_jb);
		
	}
	
	java.util.Timer is_text_t; 				 // 게임 문장 시간 타이머
	
		
	@Override
	public void mouseClicked(MouseEvent e) { // 여기서 에러가 많이 발생했었다. 유형은 이미 timetask가 돌고있다는것 또는 schedule이 되어있거나 cancel되었다는것 또는 그외..
		
		if(!is_isPlaying){
			final java.util.TimerTask is_task = new java.util.TimerTask() {   		// 원하는 시간에 수행되도록 한다.
				@Override
				public void run() {
					is_gameT.setVisible(false);
				}
			};
			is_sts = new TimerTask() {
				@Override
				public void run() {
						is_st.cancel();
						is_st = null;
						is_isPlaying = true;
						is_startT();
						is_game.remove(is_subtitle);
						is_setting();
						is_text_t.schedule(is_task, is_FirstS);     // 1단게 시간에 맞추어 문장이 사라진다.
						
				}
			};
			is_st.schedule(is_sts,1500,1000);
		}
	}


	public void is_setting(){     						 // 1단계 문제
		is_val = is_ran.nextInt(is_gaText.size());
		is_rval = is_gaText.get(is_val);
		is_gameT.setText(is_rval);
		is_game.setBackground(Color.YELLOW);
		is_gameT.setForeground(Color.BLUE);
		is_tf.setText("");
		is_tf.requestFocus();
		is_gameT.setVisible(true);
	}
	
	public void is_setting2(){  							// 2단계 문제
		is_val = is_ran.nextInt(is_gaText2.size());
		is_rval = is_gaText2.get(is_val);
		is_gameT.setText(is_rval);
		is_level.setText("Level_2");
		is_tf.setText("");
		is_tf.requestFocus();
		is_gameT.setVisible(true);
	}
	
	public void is_setting3(){								// 3단게 문제
		is_val = is_ran.nextInt(is_gaText3.size());
		is_rval = is_gaText3.get(is_val);
		is_gameT.setText(is_rval);
		is_level.setText("Level_3");
		is_tf.setText("");
		is_tf.requestFocus();
		is_gameT.setVisible(true);
	}

	public void is_pass_score(){
		is_score = is_score + 10;
		is_score_l.setText("점수 : " + is_score + "점 ");
	}
	public void is_fail_score(){
		is_score = is_score - 10;
		if(is_score < 0) is_score = 0;
		is_score_l.setText("점수 : " + is_score + "점 ");
	}
	public void is_endG(){   // 게임 종료시 확인 밑 닫기 창 
		is_restrat = true;
		gameResult(is_score);
	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
	@Override
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}

	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void actionPerformed(ActionEvent e) {
		if(!brc_next){
			for(int i = 0 ; i < brc_btn.length ; i++){
				if(e.getSource().equals(brc_btn[i])){ // 24개의 버튼과 비교
					brc_btn[i].setBackground(Color.BLACK); // 눌린 버튼 색깔 변경
	
					if(brc_btnQuestion[brc_order] == i){ // 정답 판별
						brc_order++; //맞춰야할 버튼배열 인덱스 + 1
						if(brc_order == brc_btnQuestion.length){ // 정답을 전부 맞추면
							JOptionPane.showMessageDialog(brc_mainPan, "정답입니다");
							brc_score += 10;
							brc_count += 1;
							
							brc_info.setText("난이도 : " + nowLevel + "  점수 : " +
									brc_score + "  연속 : " + brc_count + "  기회 : " + brc_life);
							
							brc_next = true; // 문제 출제 스레드 실행
						}
						brc_order = brc_order % brc_btnQuestion.length;
	
					}else{ // 정답이 아니면
						JOptionPane.showMessageDialog(brc_mainPan, "오답입니다");
						brc_count = 0;
						brc_life -= 1;
						if ( brc_life == 0 ){ // 기회를 다 써버리면
							gameResult(brc_score);///// XXX 게임 종료시 점수전송
							brc_score = 0;
							brc_count = 0;
							brc_life = 3;
							brc_isLive = false;
							brc_gameThread.interrupt();
						}
						brc_info.setText("난이도 : " + nowLevel + "  점수 : " +
								brc_score + "  연속 : " + brc_count + "  기회 : " + brc_life);
						
						brc_next = true;
					}
				}
			}
		}
		if(e.getSource().equals(is_jb)){							// 버튼을 클릭시 이벤트 발생
			TimerTask is_task2 = new TimerTask() {  		 // 버튼을 클릭시 문장이 시간

				@Override
				public void run() {
					is_gameT.setVisible(false);
				}
			};

			if(is_gameT.getText().equals(is_tf.getText())){   
				if(is_score<50){
					is_setting();
					is_text_t.schedule(is_task2, is_FirstS);
				}else if(is_score >= 120){
					is_setting3();
					is_text_t.schedule(is_task2, is_ThrS);
				}else if(is_score >= 50){
					is_setting2();
					is_text_t.schedule(is_task2, is_SecS);
				}
				is_game.setBackground(Color.YELLOW);  //배경
				is_gameT.setForeground(Color.BLUE);    //글자색
				is_pass_score();
				is_text_t.schedule(is_task2, is_FirstS);

			}
			else if(is_gameT.getText() != is_tf.getText()){
				
				if(is_score<50){
					is_setting();
					is_text_t.schedule(is_task2, is_FirstS);
				}else if(is_score >= 120){
					is_setting3();
					is_text_t.schedule(is_task2, is_ThrS);
				}else if(is_score >= 50){
					is_setting2();
					is_text_t.schedule(is_task2, is_SecS);
				}
				is_game.setBackground(Color.RED);
				is_gameT.setForeground(Color.YELLOW);
				is_fail_score();
			}
		}
		else if(e.getSource().equals(is_tf)){              //텍스트 필드에서 이벤트 발생
			TimerTask is_task3 = new TimerTask() {
				@Override
				public void run() {
					is_gameT.setVisible(false);
				}
			};


			if(is_gameT.getText().equals(is_tf.getText())){
				is_game.setBackground(Color.YELLOW);   //배경
				is_gameT.setForeground(Color.BLUE);
				if(is_score< 50){
					is_setting();
					is_text_t.schedule(is_task3, is_FirstS);	
				}else if(is_score >= 120){
					is_setting3();
					is_text_t.schedule(is_task3, is_ThrS);
				}else if(is_score >= 50){
					is_setting2();
					is_text_t.schedule(is_task3, is_SecS);	
				}
				is_pass_score();

			}
			else if(is_gameT.getText() != is_tf.getText()){
				
				if(is_score< 50){
					is_setting();
					is_text_t.schedule(is_task3, is_FirstS);
				}else if(is_score >= 120){
					is_setting3();
					is_text_t.schedule(is_task3, is_ThrS);
				}else if(is_score >= 50){
					is_setting2();
					is_text_t.schedule(is_task3, is_SecS);
				}
				is_game.setBackground(Color.RED);			//배경
				is_gameT.setForeground(Color.YELLOW);     // 라벨 글자색
				is_fail_score();


			}
		}
	}

	////////////////////////////////////////////////////////////////////////////////
	public static void main(String[] args) {
		new BrainGame();
	}


}
