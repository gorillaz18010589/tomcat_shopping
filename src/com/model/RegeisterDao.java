package com.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.beans.RegisterBean;
import com.mail.SendingEmail;

public class RegeisterDao {
	private Connection conn ;
	public String RegeisterUser(RegisterBean user) throws SQLException {
		//1.把使用者輸入的資料加入到資料庫
		String fname = user.getFName();
		String lname = user.getLname();
		String email = user.getEmail();
		String pword = user.getPword();
		String hash = user.getHash();
		
		Connection conn = MyConnection.getconnection();//連接資料庫
		try {
		//1.得到的user資料新加入進去
		String sql ="INSERT INTO mailtable (fname,lname,email,pword,hash) VALUES(?,?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1,fname);
		pstmt.setString(2,lname);
		pstmt.setString(3,email);
		pstmt.setString(4,pword);
		pstmt.setString(5,hash);
		
		
		//2.如果資料庫有近來,就寄信到使用者的email,跟hash瑪
		int i  = pstmt.executeUpdate();
		if(i != 0) { //如果資料庫有近來不等於0,代表有人註冊就季信
			SendingEmail se = new SendingEmail(email, hash);//寄信順便把參數帶到狀態那邊處理
			se.sendMail();
			return "success";
		}
		}catch (Exception e) {
			System.out.println();
		}
		return "Error";
	}
}
