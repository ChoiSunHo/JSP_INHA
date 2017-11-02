package homework;

import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;

import homework.BrainGame.Game;

public class BrainGameScore extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7386976518145368109L;

	private JPanel p;
	private JTextField gameName;
	private JTable table;
	private String game_txt;
	private String[] title = {"유저명","점수","난이도","날짜","순위"};		// 최상단 타이틀
	private String[] lists = {"유저명","점수","난이도","날짜","순위"};		// 열 추가
	private DefaultTableModel defmodel = new DefaultTableModel(title, 0);

	//DB 관련

	Connection conn;
	Statement stmt;
	ResultSet rs;
	String gameString;
	int rank = 1;


	public BrainGameScore(Game nowGame, String nowLevel) {
		setTitle("점수판");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		switch(nowGame.name()){
			case("ButtonSearch"):
				gameString = "버튼 찾기";
				break;
				
			case("ButtonRememberClick"):
				gameString = "순서 기억";
				break;
			case("InstantaneousSee"):
				gameString = "순간 시";
				break;
			
		}

		p = new JPanel();
		gameName = new JTextField(gameString + " Game Ranking");
		gameName.setHorizontalAlignment(JTextField.CENTER);
		gameName.setEnabled(false);
		gameName.setEditable(false);
		gameName.setDisabledTextColor(Color.ORANGE);
		gameName.setFont(new Font("견고딕", Font.BOLD, 25));
		gameName.setBorder(null);

		table = new JTable(defmodel);
		table.setEnabled(false);
		defmodel.addRow(lists); // TODO 이런식으로 사용 ( 한줄은 미리 체워놔야됨 )

		////////////////////////////////////////////////////////////////////////


		try {
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/gameDB?useSSL=false&user=root&password=##ace9715");
			stmt = conn.createStatement();
			
			// 난이도가 없는 순간시의 경우
			if(nowGame.name().equals("InstantaneousSee")){
				
				rs = stmt.executeQuery("select * from score where gametype = '" + nowGame + "' order by score desc, date asc limit 10");
				
				while(rs.next())
				{
					lists[0] = rs.getString(1);
					lists[1] = rs.getString(2);
					lists[2] = "-";
					lists[3] = rs.getString(4);
					lists[4] = String.valueOf(rank);
					
					defmodel.addRow(lists);
					rank++;
				}
				
			// 나머지 게임의 경우
			}else{
				
				rs = stmt.executeQuery("select * from score where gametype = '"+ nowGame + "' and level = '" +
						nowLevel + "' order by score desc, date asc limit 10");
				
				while(rs.next())
				{
					lists[0] = rs.getString(1);
					lists[1] = rs.getString(2);
					lists[2] = rs.getString(3);
					lists[3] = rs.getString(4);
					lists[4] = String.valueOf(rank);

					defmodel.addRow(lists);
					rank++;
				}
			}
			
			rs.close();
			stmt.close();
			conn.close();

		} catch(SQLException se) {
			se.printStackTrace();
		}

		/////////////////////////////////////////////////////////////////////////
		// lists = 쿼리 결과값... [인터넷 검색]

		p.add(gameName);
		p.add(table);

		add(p);

		setLocation(700,300);
		setSize(400,500);
		setResizable(false);
		setVisible(true);
	}
}
