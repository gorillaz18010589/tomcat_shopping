package com.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import com.beans.Product;
import com.beans.User;
import com.model.DB;
import com.model.MyConnection;


@WebServlet("/Controller")
public class Controller extends HttpServlet {
	ArrayList<Product> list = new ArrayList<>();
	static ArrayList<String> cartlist = new ArrayList<>();
	ArrayList<User> userList = new ArrayList<>();
	HttpSession session;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//A.��Home�D�~�����"���˰ӫ~"�M����ʪ���,��L���ܨ�post�ݦU�����
		//1.�p�G������page�ѼƬO�ũΪ̦��tindex����,�����Ʈw�ӫ~���,�s�blist
		String page = request.getParameter("page");//������ê�page�Ѽ�
		if(page == null||page.equals("index")) {//�p�Gpage�S���,�Ϊ̬O����home(index)
			//������"Product�M��"
			DB db = new DB();
			try {
				list = db.fetchProList();//������"Product�M��",���Ʈw�ȧ�X��,�A��ȳ]�m�bProduct��list��
			}catch (Exception e) {
				System.out.println("����ʪ�����ƥ���"+ e.toString());
			}
			 session = request.getSession(); //���osession
			 session.setAttribute("cartlist", cartlist);//��쪺�ʪ����ѼƦs���ʪ�����session�W,�Ǩ�index����
			 session.setAttribute("list", list);//���Ʈw���ӫ~��ưѼƤW�v�W�h,�Ǩ�index����
			 
				request.getRequestDispatcher("index.jsp").forward(request, response);
				System.out.println("��ܰӫ~��doGet,�I��home��");
		}else {
			doPost(request, response);
			System.out.println("��ܰӫ~��doPost,�ݧA�I���Ӥ���������");
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page = request.getParameter("page");//������ê�page�Ѽ�
		//B.�p�G�I��n�J,���o�n�Jlogin�Ѽ�,�N�����login����
		if(page.equals("login"))
			request.getRequestDispatcher("login.jsp").forward(request,response);
			System.out.println("login");
		
		//�p�G�I����U,���o���Usign-up�Ѽ�,�N�����sign-up����
		if(page.equals("sign-up")) {
			request.getRequestDispatcher("signup.jsp").forward(request, response);
			System.out.println("����U����");
		}
		
		//C.�s��Singup.jsp���U������,value="sign-up-form(���U�e��)
		if(page.equals("sign-up-form")) { //�p�G���U�������Ѽ�value��sign-up-form,���H�U�Ʊ�
			
		//1.���sign-up-form,�ϥΪ̿�J���U��T���Ѽ�
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String address = request.getParameter("address");
			String username = request.getParameter("username");
			String password_1 = request.getParameter("password_1");//�Ĥ@���ϥΪ̿�J���K�X
			String password_2 = request.getParameter("password_2");//�ĤG����J�T�{���K�X�O�_�@�P
				
		//2.�p�G�K�X��T�{�K�X�@�P��,��ϥΪ̵��U����,��set��k�]�w�W�h��user�W�h
			if(password_1.equals(password_2)) {
				User user = new User();
				user.setName(name);
				user.setEmail(email);
				user.setAddress(address);
				user.setUsername(username);
				user.setPassword(password_1);
		//3.����U�n�]�Ȫ�user���,�s�Juser��Ʈw
				DB db = new DB();
				try {
					db.addUser(user); 
				}catch (Exception e) {
					System.out.println("�|���s���U�g�J��Ʈw����" + e.toString());
				}
		//4.���U�|���g�J��Ʈw��,��ϥΪ̩m�W��Ƴ]��,�B�^�����\�ЫذT�������,�þɤJ�n�J����	login.jsp	
				request.setAttribute("username", username);//��ϥγo���U�����W�r,�ǻ���signup�h���e�{
				request.setAttribute("msg","�E���H��w�O�P�z���H�c,�пE��");//���U���\�T��,�ǻ���signup�h���e�{
				System.out.println("�|�����U���\:" +name);
				request.getRequestDispatcher("login.jsp").forward(request, response);//���U���\,�����ɤJ�n�J����
		//5.�p�G��J�K�X��T�{�K�X���@�P��,���������T��,��Ѽƶǵ��쭶��,���L������ƥi�H���έ��s,���Ƶ��U�M��
			}else {
				request.setAttribute("msg", "�нT�{�z��J���K�X�@�P");
				request.setAttribute("name", name);
				request.setAttribute("address", address);
				request.setAttribute("email", email);
				request.setAttribute("username", username);
				System.out.println("�|�����U����");
				request.getRequestDispatcher("signup.jsp").forward(request, response);
		
			}
		}
		//D.�p�Glogin.jsp�����t��login-form����,���ˬd�b�K,�n�J���\��index.jsp����
		if(page.equals("login-form")) {
			
		//1.���ologin.jsp�ϥΪ̵n�J���b���K�X�Ѽ�
			String username = request.getParameter("username");//���o�ϥΪ̵n�J�b��
			String password = request.getParameter("password");
			User user = new User();
			DB db = new DB();//�s�X��Ʈw����,�ϥΥ����ˬd�b�K
			boolean status = false;//�]���ˬd�b�K�ҥH�w�]�@�}�l���L����false	
			
		//2.�ˬd�b���O�_���T,�p�G���T�ȬOtrue,�����T���ܭȬOfalse
			try {
				status = db.checkUser(username, password);
			} catch (SQLException e) {
				System.out.println("���ҨϥΪ̱b�K�X�{���~:" +e.toString());
			}
			
		//3.�p�G�b�K�]���T:�msession,�B��ϥΪ̪���Ƨ�X��,�]�muser�W,����ϥΪ̲M��
			if(status) {
				session = request.getSession();
				session.setAttribute("session", session); //�]�msession��index.jsp
				try {
					userList = db.fetchUserlist();//�����Ʈw�ϥΪ̲M��X��,�ó]�m�buserlist�W
				} catch (SQLException e) {
					System.out.println("db.fetchUserlist()�X�{���~:" +e.toString());
				}
				
		//4��A��J���b�K�ϥΪ�:�W�r,�a�},emaill,�b��,sesssion�ȶǨ�index.jsp����
				session.setAttribute("name", user.fetchname(userList, username));//�]�m�ѼƬ�("name",fetchname����o�ӱb���H���W�r("�ϥΪ̲M��",�ϥΪ̱b��)
				session.setAttribute("address", user.fetchadd(userList, username));
				session.setAttribute("email", user.fetchemail(userList, username));
				session.setAttribute("username",username);
				System.out.println("�n�J���\");
				request.getRequestDispatcher("home.jsp").forward(request, response);//��ѼƱa��index.jsp����	
			}else {//�p�G�b�K���~,����~�T��,username,�~��ݨ�n�J�������n
				request.setAttribute("msg", "�b���αK�X���~");
				request.setAttribute("username", username);
				System.out.println("�n�J����");
				request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}
		//E.�n�Xlogout,���ʪ����M��,��session����
		if(page.equals("logout")) {
			//1.�M���ϥΪ̵n�J��session
			session = request.getSession(); //���osession
			session.invalidate();//�M�ŨϥΪ̵n�J��session
			//2.�M���ϥΪ̪��ʪ���,�åB��Ū��ʪ���session��S���]�m
			cartlist.clear();//�M���ʪ���list�}�C�̪����
			session = request.getSession(); //���o�s��session
			session.setAttribute("cartlist", cartlist);//��M�Ū��ʪ���session�Ǩ�index����
			 session.setAttribute("list", list);
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
		//F.��ӫ~list�̷ӭ������P�e�줣�P�������
//		mobiles(���),laptops(���q),clothing(�A��),home-decor(�a��˹�),all-products(�����ӫ~)
		if(page.equals("mobiles")||page.equals("laptops")||page.equals("clothing")||page.equals("home-decor")||page.equals("all-products")) {
		//1.��ӫ~�M����ܥX��,�����Ʈw�ӫ~,
			DB db = new DB();
			Product p = new Product();
			
			db.doConnent();
			try {
				list = db.fetchProList();//�����Ʈw���ӫ~�M��,�ñ��W�h�ӫ~����W
			} catch (SQLException e) {
				System.out.println("F.���������ӫ~�M�����X�{���D:" + e.toString());
			}	
		//2.��o�Ӹ�Ʈw���o��Productlist,�s��ѼƱa��o5�ӭ����h�e�{
			request.setAttribute("list", list);
			
			if(page.equals("mobiles")) {
				request.getRequestDispatcher("mobiles.jsp").forward(request, response);
			}
			if(page.equals("laptops")) {
				request.getRequestDispatcher("laptops.jsp").forward(request, response);
			}
			if(page.equals("clothing")) {
				request.getRequestDispatcher("clothing.jsp").forward(request, response);
			}
			if(page.equals("home-decor")) {
				request.getRequestDispatcher("home-decor.jsp").forward(request, response);
			}
			if(page.equals("all-products")) {
				request.getRequestDispatcher("all-products.jsp").forward(request, response);
			}
			
		}
		//G.�޾ɨ��ʪ�������
		if(page.equals("showcart")) {
			System.out.println("�i�J�ʪ����C��");
			request.getRequestDispatcher("cart.jsp").forward(request, response);
		}
	
		//H.�s�W�ʪ������s,�p�G������ĵ�i����,�p�G�S����list�}�Cadd(id),�ʪ����s�W�@��ӫ~id,�s�W����d�A�쥻�e��
		if(page.equals("addtocart")) {
			//1.id�Ѽƭn�Ω�����O�_���ƥB��ܦb�ʪ����W,2.action�ѼƥΩ�Low to high,�qurl���o
			String id= request.getParameter("id"); //���oid�Ѽ�,�ǳƭn�����ʪ����O�_����
			String action = request.getParameter("action");//���oaction�Ѽ�,�ǳƱa��low_to_highit�h�B�z
			//2.�����ʪ����O�_����,�p�G������ܭ��ưT��,�p�G�S�����ʪ���List.add(id).�ʪ����}�C�s�W�@�����
			Product p = new Product();
			boolean check = p.check(cartlist, id);//�ʪ������������true,�S����false
			if(check) {
//				JOptionPane.showMessageDialog(null, "���ӫ~�w�g�Q�[�J�ʪ���", "Info", JOptionPane.INFORMATION_MESSAGE);
				System.out.println("�ӫ~�w�Q�[�J�ʪ���"+id);
			}else {
				cartlist.add(id);//�b�ʪ����}�C�s�W�@��(�o�Ӱӫ~id�O��add�ʪ����������o���Ѽ�)
//				JOptionPane.showMessageDialog(null, "���\�[�J�ʪ���", "Info", JOptionPane.INFORMATION_MESSAGE);
				System.out.println("���\�[�J�ʪ���:" +id);
			}
			//3.��id�ѼƸ�action�a��U�ۭ����h��,�ʪ���+1�e�{,action�O�q�s�W�ʪ������s���o�Ө�
			if(action.equals("index"))
				request.getRequestDispatcher("index.jsp").forward(request, response);
			if(action.equals("allproducts"))
				request.getRequestDispatcher("all-products.jsp").forward(request, response);
			if(action.equals("clothing"))
				request.getRequestDispatcher("clothing.jsp").forward(request, response);
			if(action.equals("home-decor"))
				request.getRequestDispatcher("home-decor.jsp").forward(request, response);
			if(action.equals("laptops"))
				request.getRequestDispatcher("laptops.jsp").forward(request, response);
			if(action.equals("mobiles"))
				request.getRequestDispatcher("mobiles.jsp").forward(request, response);
		}
		//I.���b���\����
			if(page.equals("success")) {
			request.getRequestDispatcher("success.jsp").forward(request, response);
			System.out.println("�i�J���b���\����");
			/*session = request.getSession();
			 cartlist.clear();
			 session.setAttribute("cartlist", cartlist);*/
		}
		//J.�R���ʪ���������,�ݭnid�ѼƦn�R���ӫ~
		 if(page.equals("remove")) {
		//1.���R���ӫ~��id�Ѽ�,��ϥΪ��I�諸�R���ӫ~,�q�ʪ��������R��
			 String id = request.getParameter("id");
			 Product p = new Product();
			cartlist = p.remove(cartlist, id);//�R���ʪ����̭���ID�ӫ~
		//2.�R�����O�̫��w���ʪ����ӫ~��,��R���᪺�ʪ����ѼƶǦ^cart�����h�@�e�{	
			session = request.getSession();
			session.setAttribute("cartlist", cartlist);//�R���ӫ~�᪺�ʪ����Ѽ�
			System.out.println("�R���ʪ������ئ��\:" + id);
			request.getRequestDispatcher("cart.jsp").forward(request, response);//�A�^�쭶�����
		 }
		 //K.����Ƨ�
		 	if(page.equals("price-sort")) {
		 		//1.���oaction�i�H�a�J�쭶��,���osort�s��price�O�C�찪�򰪨�C���ﶵ�Ѽ�
		 		String action = request.getParameter("action");//���o�n���������Ѽ�
		 		String price = request.getParameter("sort");//���olow-to-high,high-to-low���Ѽ�
		 		//2.�p�G���tlow-to-high����,����ѧC�찪����k,�Ϥ�����high-to-lo
		 		Product p = new Product();
		 		if(price.equals("low-to-high")) {
			 		list = p.lowtohigh(list);//�ӫ~����ѧC�찪�Ƨ�
			 		session.setAttribute("list", list);
			 		System.out.println("�ϥΪ̫��Ulow-to-high");
			 	}else {
			 		list = p.hightolow(list);//�ӫ~����Ѱ���C�Ƨ�
			 		session.setAttribute("list", list);//��ƧǦn��list�M��ѼƱa��U�����h
			 		System.out.println("�ϥΪ̫��Uhightolow");
		 		}
		 		//3.�p�G�������t�o�ǭ���,���O��list����ƧǰѼƱa�쭶��
		 		if(action.equals("index"))
					request.getRequestDispatcher("index.jsp").forward(request, response);
				if(action.equals("all-products"))
					request.getRequestDispatcher("all-products.jsp").forward(request, response);
				if(action.equals("mobiles"))
					request.getRequestDispatcher("mobiles.jsp").forward(request, response);
				if(action.equals("laptops"))
					request.getRequestDispatcher("laptops.jsp").forward(request, response);
				if(action.equals("clothing"))
					request.getRequestDispatcher("clothing.jsp").forward(request, response);
				if(action.equals("home-decor"))
					request.getRequestDispatcher("home-decor.jsp").forward(request, response);
		 	}
		 //L.�j�M�ӫ~
		 	if(page.equals("search_product")) {
		 		String name = request.getParameter("name");
		 		DB db = new DB();
		 		Product p = new Product();
		 		db.doConnent();
		 		try {
		 			list = db.searchProduct(name); //�j�M�ӫ~
				} catch (SQLException e) {
					System.out.println("�j�M�ӫ~����.." + e.toString());
				}
		 			request.setAttribute("list", list);//��j�M��o�������e�Ǩ�search.jsp����
			 		System.out.println("��j�M���ӫ~���" + list +"�Ǩ�search.jsp����");
			 		request.getRequestDispatcher("search.jsp").forward(request, response); 		
		 	}
		 	//M.�e�X�H��᦬��T�\��
		 	//���H�H��ϥΪ̪�email��hash���Ѽ�,�վ㪬�A
		 		if(page.equals("mymail")) {
				String email = request.getParameter("key1");
				String hash = request.getParameter("key2");
				System.out.println("�ѼƦ����");
				System.out.println("���]�tmymail");
						DB db = new DB();
						db.doConnent();			
						try {
						int	i = db.changeActive(email, hash);
							if(i==1) { //�p�G��s���\��n�J�b������
								System.out.println("�E�����A" +email);
								request.getRequestDispatcher("login.jsp").forward(request, response);
							}
							else {
								System.out.println("�E������" + email);
								request.getRequestDispatcher("signup.jsp").forward(request, response);
							}
						} catch (SQLException e) {
							System.out.println("ActibeAccount���~" + e.toString());
						}
						db.dbClose();
				}	
		 		//N.�I��ӫ~�[�J��ڪ��̷R
				if(page.equals("intoProduct")) {
					String id = request.getParameter("id");
					System.out.println("�����id�Ѽ�:" + id);
					DB db = new DB();
					db.doConnent();
					try {
						list =db.intoProduct(id);
						System.out.println("���\�i�J���w�ӫ~:" + id);
					}catch (Exception e) {
						System.out.println("�i�J�ӫ~����" + e.toString());
					}
					request.setAttribute("list", list);
					System.out.println("�ɤJ������" +list +"��intoProduct");
					request.getRequestDispatcher("favorite.jsp").forward(request, response);
				}
			}
			
	}	


