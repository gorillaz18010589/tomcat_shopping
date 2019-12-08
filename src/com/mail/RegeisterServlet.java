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
		//1.���o�|����J���Ѽ�
				String fname = request.getParameter("fname");
				String lname = request.getParameter("lname");
				String email = request.getParameter("email");
				String pword = request.getParameter("pword");
				String newPword = DigestUtils.md5Hex(pword); //��K�X�[�K�B�z
				
				
				//2.�]�w�Ұʶüƺ�
				String hash ;
				Random random = new Random();
				random.nextInt(999999);
				hash = DigestUtils.md5Hex("" +random);
				
				
				//3.��[�X�K�X��ü�code��muser�W
				RegisterBean user = new RegisterBean();
				user.setFName(fname);
				user.setLname(lname);
				user.setEmail(email);
				user.setPword(newPword);
				user.setHash(hash);//�]�w�Ұʺ�
				
				System.out.println("�����");
				RegeisterDao regdao = new RegeisterDao(); 
				String str;
					try {
						str = regdao.RegeisterUser(user);//��user��ƥ浹DAO�s��i��Ʈw��,�åB�H�@�ʫH,�p�G�̭����t���\���r��
						if(str.equals("success")) {
							response.sendRedirect("mail/verify.jsp");//���n�J���\����
						}else {
							response.sendRedirect("mail/index.jsp");
						}
					} catch (SQLException e) {
						System.out.println("servlet�����~" + e.toString());
					}
					
				}
	}


