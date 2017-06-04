/**
 * @title : TodoList.java
 * @author : 임현 (201511054@sangmyung.kr)
 * @version : 1.5.1.
 * @since : 2017 - 05 - 31
 * @brief : To do List
 * ------------------------------
 * @history
 	author		version		date		brief
 	임현			1.0.0.		2017-06-01	초안 작성 (InsertList 구현)
 	임현			1.0.1.		2017-06-02	Checkbox false 추가
 	임현			1.1.0.		2017-06-02	DeleteList 연동
 	임현			1.2.0.		2017-06-03	ModifyList 연동
 	임현			1.3.0.		2017-06-03	InsertCourInfo 연동
 	임현			1.3.1.		2017-06-03	ShowTable 함수 추가
 	황은선		1.4.0.		2017-06-04	ShowTable 함수 완성
 	임현			1.4.1.		2017-06-04	데이터베이스 연동 수정
 	임현			1.4.2.		2017-06-04	데이터베이스 연동 수정
 	임현			1.4.3.		2017-06-04	InsertCour 함수 추가
 	황은선		1.4.4.		2017-06-04	InsertCour 함수 수정 시도
 	임현			1.4.5.		2017-06-04	InsertCour 함수 삭제
 	임현			1.4.6.		2017-06-04	UI 변수명 변경
 	임현			1.4.7.		2017-06-04	CourName, Hide 추가
 	임현			1.5.0.		2017-06-04	CourName, Hide 테이블 추가
 	임현			1.5.1		2017-06-05	테이블 잔오류 해결
 * ------------------------------
 */

package se.smu;

import se.smu.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class TodoList extends JFrame {
	
	// 변수 선언
	JButton jb1, jb2, jb3, jb4;
	JTextField jt4, listnameTextField, deadlineTextField, finishdayTextField;
	JTable jtb;
	JCheckBox jck1, jck2, jck3;
	
	static String CourName;
	static String ListName;
	static String DeadLine;
	static String FinishDay;
	static String Finish;
	static String Importance;
	static String Hide;
	
	String ModiListName;
	String ModiDeadLine;
	String ModiFinishDay;
	String ModiFinish;
	String ModiImportance;
	
	String DeleteListName;
	
	/**
	 * @title : ShowTable
	 * @author : 황은선
	 * @brief : Table을 갱신해주는 함수
	 */
	public void ShowTable () {
		// 기본 변수 선언
		String row[]={"  B l o s s o m", "과목명", "To do List", "마감 기한", "실제 마감일", "완료 여부", "중요 여부", "숨김 여부"};
		Connection conn = null;
		String sql;
		String str=null;
		Statement st = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		DefaultTableModel model = new DefaultTableModel(row, 0);

		try{
			// DB연동
			Class.forName(DataBaseConn.forName);
			conn = DriverManager.getConnection(DataBaseConn.URL, DataBaseConn.ID, DataBaseConn.PASSWORD);

			// 사용할 DB설정, 리스트 정보 불러오기
			st = conn.createStatement();
			sql = "USE ListDB";
			st.execute(sql);
			rs = st.executeQuery("select * from Listinfo");
			
			while(rs.next())
			{
				for(int i = 1; i < 8; i++)
				{
					row[i]=rs.getString(i);
				}
				model.addRow(row);
			}
			rs.close();
			st.close();
		}
		catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
		
		// 테이블 첫번째 값 해결 가능 시 해결 요망
		JTable jtb = new JTable(model);
		jtb.setLocation(10, 10);;
		jtb.setSize(700, 400);
		JScrollPane js = new JScrollPane(jtb);
		js.setSize(700, 400);
		add(js);
	}
		
	TodoList() {
		setTitle("To do List");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);

		JPanel jp1 = new JPanel();
		JPanel jp2 = new JPanel();
		JPanel jp3 = new JPanel();

		jp1.setLocation(740, 50);
		jp1.setSize(400, 30);
		jp1.setLayout(new GridLayout(1, 4));
		JButton jb1 = new JButton("추가");
		JButton jb2 = new JButton("삭제");
		JButton jb3 = new JButton("변경");
		JButton jb4 = new JButton("과목 추가");
		jp1.add(jb1);
		jp1.add(jb2);
		jp1.add(jb3);
		jp1.add(jb4);
		add(jp1);
		
		jp2.setLocation(740, 150);
		jp2.setSize(90, 180);
		jp2.setLayout(new GridLayout(7, 1));
		jp2.add(new JLabel("과목 명"));
		jp2.add(new JLabel("To do 항목 명"));
		jp2.add(new JLabel("마감기한"));
		jp2.add(new JLabel("실제 마감일"));
		jp2.add(new JLabel("완료 여부"));
		jp2.add(new JLabel("중요 여부"));
		jp2.add(new JLabel("숨김 여부"));
		add(jp2);

		jp3.setLocation(860, 150);
		jp3.setSize(200, 180);
		jp3.setLayout(new GridLayout(7, 1));
		JCheckBox jck1 = new JCheckBox(" 완료");
		JCheckBox jck2 = new JCheckBox(" 중요");
		JCheckBox jck3 = new JCheckBox(" 숨김");
		JTextField jt4 = new JTextField();
		JTextField listnameTextField = new JTextField();
		JTextField deadlineTextField = new JTextField();
		JTextField finishdayTextField = new JTextField();
		jp3.add(jt4);
		jp3.add(listnameTextField);
		jp3.add(deadlineTextField);
		jp3.add(finishdayTextField);
		jp3.add(jck1);
		jp3.add(jck2);
		jp3.add(jck3);
		add(jp3);
		
		ShowTable();

		setSize(1200, 400);
		setVisible(true);
		
		jb1.addActionListener(new ActionListener() { // InsertList.java 참고
			public void actionPerformed(ActionEvent e) {
				CourName = jt4.getText();
				ListName = listnameTextField.getText();
				DeadLine = deadlineTextField.getText();
				FinishDay = finishdayTextField.getText();
				if (jck1.isSelected()) 	Finish = "O"; // 데이터베이스에 boolean값이 가능할 경우 할 필요 없음
				else Finish = "X";
				if (jck2.isSelected()) 	Importance = "O";
				else Importance = "X";
				if (jck3.isSelected()) 	Hide= "O";
				else Hide = "X";
				
				ListDB listdb = new ListDB();
				listdb.ListTable(CourName, ListName, DeadLine, FinishDay, Finish, Importance, Hide);
				
				ShowTable();
			}
		});
		
		jb2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeleteListName = listnameTextField.getText(); // java.sql.SQLException: Operation not allowed after ResultSet closed 오류
				new DeleteList(DeleteListName);
				
				ShowTable();
			}
		});
		
		jb3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CourName = jt4.getText();
				ModiListName = listnameTextField.getText();
				ModiDeadLine = deadlineTextField.getText();
				ModiFinishDay = finishdayTextField.getText();
				if (jck1.isSelected()) 	ModiFinish = "O"; // 데이터베이스에 boolean값이 가능할 경우 할 필요 없음
				else ModiFinish = "X";
				if (jck2.isSelected()) 	ModiImportance = "O";
				else ModiImportance = "X";
				if (jck3.isSelected()) 	Hide= "O";
				else Hide = "X";
				
				// java.sql.SQLException: Operation not allowed after ResultSet closed 오류
				new ModifyList(CourName, ModiListName, ModiDeadLine, ModiFinishDay, ModiFinish, ModiImportance, Hide);		
				
				ShowTable();
			}
		});
		
		jb4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new InsertCourInfo();
			}
		});
	}
	
	public static void main(String[] args) {
		new TodoList();
	}
}