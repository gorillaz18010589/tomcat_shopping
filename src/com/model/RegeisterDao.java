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
		//1.��ϥΪ̿�J����ƥ[�J���Ʈw
		String fname = user.getFName();
		String lname = user.getLname();
		String email = user.getEmail();
		String pword = user.getPword();
		String hash = user.getHash();
		
		Connection conn = MyConnection.getconnection();//�s����Ʈw
		try {
		//1.�o�쪺user��Ʒs�[�J�i�h
		String sql ="INSERT INTO mailtable (fname,lname,email,pword,hash) VALUES(?,?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1,fname);
		pstmt.setString(2,lname);
		pstmt.setString(3,email);
		pstmt.setString(4,pword);
		pstmt.setString(5,hash);
		
		
		//2.�p�G��Ʈw�����,�N�H�H��ϥΪ̪�email,��hash��
		int i  = pstmt.executeUpdate();
		if(i != 0) { //�p�G��Ʈw����Ӥ�����0,�N���H���U�N�u�H
			SendingEmail se = new SendingEmail(email, hash);//�H�H���K��ѼƱa�쪬�A����B�z
			se.sendMail();
			return "success";
		}
		}catch (Exception e) {
			System.out.println();
		}
		return "Error";
	}
}
