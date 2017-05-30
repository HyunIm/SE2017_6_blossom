/**
 * @title : SortList.java
 * @author : 황은선 (201511077@sangmyung.kr)
 * @version : 1.0.0.
 * @since : 2017 - 05 - 29
 * @brief : 리스트 정렬 코드
 * ------------------------------
 * @history : 코드 초안
 	author		version		date
 	황은선	    1.0.0.		2017-05-29
 * ------------------------------
 */

package se.smu;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.sql.*;
import se.smu.ListDB;

public class SortList {
	public static void main(String[] args) {
			try {
				// 기본 변수 선언
				Connection conn = null;
				String sql;
				Statement st = null;
				PreparedStatement pst = null;
				ResultSet rs = null;

				// DB연동
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?useSSL=false", "root", "0000");

				// 사용할 DB설정, 회원정보에서 ID와 Passward불러오기
				st = conn.createStatement();
				sql = "USE ListDB";
				st.execute(sql);
				
				/*주의: 정렬된 내용이 DB저장이 아니라 출력물에만 나타남*/
				
				if(/*항목명 정렬버튼을 한번클릭했을경우*/)
				{
					sql="SELECT ListName, DeadLine, Importance, FinishDay, Finish"
					   +"FROM ListInfo"
					   +"ORDER BY ListName asc";
				}
				else if(/*마감기한 정렬버튼을 한번클릭했을경우*/)
				{
					sql="SELECT ListName, DeadLine, Importance, FinishDay, Finish"
					   +"FROM ListInfo"
					   +"ORDER BY DeadLine asc";
				}
				else if(/*실제마감일 정렬버튼을 한번클릭했을경우*/)
				{
					sql="SELECT ListName, DeadLine, Importance, FinishDay, Finish"
					   +"FROM ListInfo"
					   +"ORDER BY FinishDay asc";
				}
				else if(/*완료여부 정렬버튼을 한번클릭했을경우*/)
				{
					sql="SELECT ListName, DeadLine, Importance, FinishDay, Finish"
					   +"FROM ListInfo"
					   +"ORDER BY Finish asc";
				}
				
				st.execute(sql);

				
				rs = st.executeQuery("select ID,Passward from MemberInfo");

				// 실행창 닫기
				rs.close();
				st.close();

			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}

		}

}