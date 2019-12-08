package com.controller;
//�Ȥ��O�޲z�Ҧ�_Controller
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import com.beans.Product;
import com.model.DB;


@WebServlet("/AdminController")
public class AdminController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//A.�p�G�@�}�lpage�O��,�������n�J�|��,�ɤJ������login
			String page = request.getParameter("page");
			if(page == null) {
				System.out.println("page�����ѼƨS���,��J�n�J����");
				request.getRequestDispatcher("admin/login.jsp").forward(request, response);
			}else {
				doPost(request, response);
				System.out.println("dopost");
			}
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//B.�n�J���w�b��.�K�X,�p�G���\�����index����,���Ѫ����~��b�n�J����
		String page = request.getParameter("page"); //���opage�Ѽ�
		//1.���o�ϥΪ̿�J���b��,�K�X�Ѽ�
		if(page.equals("admin-login-form")) {
			String username = request.getParameter("username");//���o�ϥΪ̿�J���b���Ѽ�
			String password = request.getParameter("password");//���o�ϥΪ̿�J���K�X�Ѽ�
		//2.�ϥΪ̿�J���b���K�X,�����ҳB�z,�p�G�b�K���T,�ɤJindex����,�p�G���Ѫ��ܳ]�m��ܿ��~�T���Ѽ�,��ϥΪ̱b���Ѽ�,�~��ɤJ�n�J���������
			if(username.equals("zdrgnkoiu") && password.equals("pink236545")) {
				System.out.println("�b���K�X���T:"+username + "�ɤJindex����" );
				request.getRequestDispatcher("admin/index.jsp").forward(request, response);
			}else {
				request.setAttribute("msg", "�b���αK�X���~�Э��s��J");//�]�w���~�T���ǻ���login����
				request.setAttribute("username", username);
				System.out.println("�n�J����");
				request.getRequestDispatcher("admin/login.jsp").forward(request, response);
			}
		}
		//C.�R���ӫ~��Ʈw
		if(page.equals("delete")) {
		//1.//���o�I�諸�ӫ~��Ʈwid�Ѽ�,�R�����w�ӫ~����Ʈw
			String id = request.getParameter("id");//���o�I�諸�ӫ~��Ʈwid�Ѽ�
			DB db = new DB(); //�I�s��Ʈw�������
			try {
				db.deletProduct(id); //�R���ӫ~��Ʈw(���wid�ӫ~)
				System.out.println("�R���F�@���ӫ~���:" + id);
			} catch (SQLException e) {
			
			}
			JOptionPane.showMessageDialog(null, "���\�q��Ʈw�R���F" + id +"�ӫ~", "info", JOptionPane.INFORMATION_MESSAGE );
			request.getRequestDispatcher("admin/index.jsp").forward(request, response);//�R�����~��Aindex����
		}
		
		//D.�I��Home�s����admin/index.jsp����
		if(page.equals("index")) {
			request.getRequestDispatcher("admin/index.jsp").forward(request, response);
			System.out.println("�i�JHome�D��");
		}
		
		//E.�I��addProduct�s����admin/addProduct.jsp
		if(page.equals("addproduct")) {
			request.getRequestDispatcher("admin/addProduct.jsp").forward(request, response);
			System.out.println("�i�JaddProduct(�W�[�ӫ~)����");
		}
		
		//F."���o�s��ӫ~��id�Ѽ�",�åB�s�X"���ӫ~����T�M��"�X��,�öǻ���edit_product�����h���s��to_G�B�z
		if(page.equals("edit")) {
			String id =  request.getParameter("id");
			DB db = new DB();
			Product p = null; //�s�X�ӫ~����@�}�l����
			try {
				p = db.fetchProduct(id); //����wid�s����쪺�ӫ~,�եX�ӫ~��T,�æs�Jp
				System.out.println("�n�s�誺�ӫ~id�O:" + id );
			} catch (SQLException e) {
				
			}
			request.setAttribute("p", p);//����w���s��ӫ~��TP,�Ǩ�s�譶�����B�z
			System.out.println("���b�੹�s�譶���h���B�z...");
			request.getRequestDispatcher("admin/editProduct.jsp").forward(request, response);
		}
		
		//G."�s��ӫ~���e"��s��Ʈw
		if(page.equals("edit_product")) {
			//1.���o�A�n�ק諸�ӫ~�Ѽ�
			String id = request.getParameter("id");//���o�ӫ~��id�Ѽ�,updateProduct��k�ݭn��id�ӬݬO�ק���Ӱӫ~,�ҥH����
			String name = request.getParameter("name");
			String price = request.getParameter("price");
			String category = request.getParameter("category"); //���o�ק�᪺�����Ѽ�
			String featured = request.getParameter("featured"); //���o�ק�᪺���˰Ѽ�
			//2.�s�XProduct�������,�]�w�s���ȶi�h
			Product p = new Product();
			p.setId(Integer.parseInt(id));  
			p.setName(name);
			p.setPrice(price);
			p.setCategory(category);
			p.setFeatured(featured);
			
			//3.��s�s��᪺�ӫ~���,��s���Ʈw
			DB db =  new DB();
			try {
				db.updateProduct(p); //��s��Ʈw(�ӫ~�}�C)
				System.out.println(id + ":" + name + "�ӫ~�w��s���\");
			} catch (SQLException e) {
				System.out.println("�ӫ~��s����");
			}
//			JOptionPane.showMessageDialog(null, "�ӫ~�w��s!!", "info", JOptionPane.INFORMATION_MESSAGE); �o�y�|�d����
			System.out.println("�^��index.jsp����");
			request.getRequestDispatcher("admin/index.jsp").forward(request, response);
		}
		
		//H.�s�W�ӫ~
		if(page.equals("add_product")) {
			//1.���o��J���ӫ~��T�Ѽ�
			String name = request.getParameter("name");
			String price = request.getParameter("price");
			String category = request.getParameter("category");
			String featured = request.getParameter("featured");
			String image = request.getParameter("image");
			
			//2.��s�W���ӫ~��T�Ѽ�,�]�w�W�hProdcut
			Product p = new Product();
			p.setName(name);
			p.setPrice(price);
			p.setCategory(category);
			p.setFeatured(featured);
			p.setImage("img/"+image);//"img/(�Ҧ�m���U)+�A�s�W���o���Ϥ��W�r���|"
			
			//3.��s�W�n���ӫ~p,�s�W���Ʈw
			DB db  = new DB();
			try {
				db.addProduct(p);
				System.out.println("�s�W�ӫ~���\:" + name);
			} catch (SQLException e) {
				System.out.println("�s�W�ӫ~����" +e.toString());
			}
			System.out.println("�ɤJindex.jsp����");
			request.getRequestDispatcher("admin/index.jsp").forward(request, response);
		}
	}

}
