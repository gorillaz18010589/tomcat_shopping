package com.mail;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;

import com.beans.RegisterBean;
import com.model.RegeisterDao;


@WebServlet("/RegeisterServlet")
public class RegeisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page = request.getParameter("page");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.取得會員輸入的參數
				String fname = request.getParameter("fname");
				String lname = request.getParameter("lname");
				String email = request.getParameter("email");
				String pword = request.getParameter("pword");
				String newPword = DigestUtils.md5Hex(pword); //把密碼加密處理
				
				
				//2.設定啟動亂數瑪
				String hash ;
				Random random = new Random();
				random.nextInt(999999);
				hash = DigestUtils.md5Hex("" +random);
				
				
				//3.把加碼密碼跟亂數code放置user上
				RegisterBean user = new RegisterBean();
				user.setFName(fname);
				user.setLname(lname);
				user.setEmail(email);
				user.setPword(newPword);
				user.setHash(hash);//設定啟動瑪
				
				System.out.println("有近來");
				RegeisterDao regdao = new RegeisterDao(); 
				String str;
					try {
						str = regdao.RegeisterUser(user);//把user資料交給DAO存放進資料庫哩,並且寄一封信,如果裡面有含成功的字樣
						if(str.equals("success")) {
							response.sendRedirect("mail/verify.jsp");//轉到登入成功頁面
						}else {
							response.sendRedirect("mail/index.jsp");
						}
					} catch (SQLException e) {
						System.out.println("servlet有錯誤" + e.toString());
					}
					
				}
	}


