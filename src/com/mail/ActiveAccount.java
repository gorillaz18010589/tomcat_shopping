package com.mail;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model.MyConnection;

/**
 * Servlet implementation class ActiveAccount
 */
@WebServlet("/ActiveAccount")
public class ActiveAccount extends HttpServlet {

	
	

   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//���H�H��ϥΪ̪�email��hash���Ѽ�,�վ㪬�A
		String email = request.getParameter("key1");
		String hash = request.getParameter("key2");
		
		Connection conn = MyConnection.getconnection();
		try {
			//1.�d��Email���ϥΪ̿�J��mail,�򲣥ͪ���,�Mactivie��0�����
		String sql ="SELECT email,hash,active FRON mailtable WHERE email = ? AND hash = ? AND active ='0'";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();	
			if(rs.next()) {
				//2.��E�����A���,activie�אּ1,email��hash���]��s
				String sql1="UPDATE mailtable SET active='1' where email = ? AND hash = ?";
				PreparedStatement pstmt1 = conn.prepareStatement(sql1);
				pstmt.setString(1, email);
				pstmt.setString(2, hash);
				int i = pstmt.executeUpdate();
				if(i==1) { //�p�G��s���\��n�J�b������
					response.sendRedirect("mail/Accountlogin.jsp");
				}
				else {
					response.sendRedirect("mail/mailindex.jsp");
				}
			}
		
		}catch (Exception e) {
			System.out.println("ActibeAccount���~" + e.toString());
		}
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
