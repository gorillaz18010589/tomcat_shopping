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
		//A.案Home主業時顯示"推薦商品"清單跟購物車,其他的話走post看各自顯示
		//1.如果有拿到page參數是空或者有含index頁面,抓取資料庫商品資料,存在list
		String page = request.getParameter("page");//抓到隱藏的page參數
		if(page == null||page.equals("index")) {//如果page沒抓到,或者是有案home(index)
			//抓取資料"Product清單"
			DB db = new DB();
			try {
				list = db.fetchProList();//抓取資料"Product清單",把資料庫值抓出來,再把值設置在Product的list李
			}catch (Exception e) {
				System.out.println("抓取購物車資料失敗"+ e.toString());
			}
			 session = request.getSession(); //取得session
			 session.setAttribute("cartlist", cartlist);//抓到的購物車參數存到購物車的session上,傳到index頁面
			 session.setAttribute("list", list);//把資料庫的商品資料參數上釘上去,傳到index頁面
			 
				request.getRequestDispatcher("index.jsp").forward(request, response);
				System.out.println("顯示商品走doGet,點選home時");
		}else {
			doPost(request, response);
			System.out.println("顯示商品走doPost,看你點哪個分頁有哪條");
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page = request.getParameter("page");//抓到隱藏的page參數
		//B.如果點選登入,取得登入login參數,就跳轉到login頁面
		if(page.equals("login"))
			request.getRequestDispatcher("login.jsp").forward(request,response);
			System.out.println("login");
		
		//如果點選註冊,取得註冊sign-up參數,就跳轉到sign-up頁面
		if(page.equals("sign-up")) {
			request.getRequestDispatcher("signup.jsp").forward(request, response);
			System.out.println("到註冊頁面");
		}
		
		//C.連接Singup.jsp註冊頁面的,value="sign-up-form(註冊畫面)
		if(page.equals("sign-up-form")) { //如果註冊頁面的參數value有sign-up-form,做以下事情
			
		//1.抓取sign-up-form,使用者輸入註冊資訊的參數
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String address = request.getParameter("address");
			String username = request.getParameter("username");
			String password_1 = request.getParameter("password_1");//第一次使用者輸入的密碼
			String password_2 = request.getParameter("password_2");//第二次輸入確認的密碼是否一致
				
		//2.如果密碼跟確認密碼一致時,把使用者註冊的值,用set方法設定上去到user上去
			if(password_1.equals(password_2)) {
				User user = new User();
				user.setName(name);
				user.setEmail(email);
				user.setAddress(address);
				user.setUsername(username);
				user.setPassword(password_1);
		//3.把註冊好設值的user資料,存入user資料庫
				DB db = new DB();
				try {
					db.addUser(user); 
				}catch (Exception e) {
					System.out.println("會員新註冊寫入資料庫失敗" + e.toString());
				}
		//4.註冊會員寫入資料庫後,把使用者姓名資料設值,且回報成功創建訊息給對方,並導入登入頁面	login.jsp	
				request.setAttribute("username", username);//把使用這註冊號的名字,傳遞給signup去做呈現
				request.setAttribute("msg","激活信件已記致您的信箱,請激活");//註冊成功訊息,傳遞給signup去做呈現
				System.out.println("會員註冊成功:" +name);
				request.getRequestDispatcher("login.jsp").forward(request, response);//註冊成功,直接導入登入頁面
		//5.如果輸入密碼跟確認密碼不一致時,給予提醒訊息,把參數傳給原頁面,讓他打的資料可以不用重新,重複註冊誒面
			}else {
				request.setAttribute("msg", "請確認您輸入的密碼一致");
				request.setAttribute("name", name);
				request.setAttribute("address", address);
				request.setAttribute("email", email);
				request.setAttribute("username", username);
				System.out.println("會員註冊失敗");
				request.getRequestDispatcher("signup.jsp").forward(request, response);
		
			}
		}
		//D.如果login.jsp頁面含有login-form的話,做檢查帳密,登入成功跳index.jsp頁面
		if(page.equals("login-form")) {
			
		//1.取得login.jsp使用者登入的帳號密碼參數
			String username = request.getParameter("username");//取得使用者登入帳號
			String password = request.getParameter("password");
			User user = new User();
			DB db = new DB();//叫出資料庫物件,使用它來檢查帳密
			boolean status = false;//因為檢查帳密所以預設一開始布林直為false	
			
		//2.檢查帳號是否正確,如果正確值是true,不正確的話值是false
			try {
				status = db.checkUser(username, password);
			} catch (SQLException e) {
				System.out.println("驗證使用者帳密出現錯誤:" +e.toString());
			}
			
		//3.如果帳密設正確:置session,且把使用者的資料抓出來,設置user上,抓取使用者清單
			if(status) {
				session = request.getSession();
				session.setAttribute("session", session); //設置session到index.jsp
				try {
					userList = db.fetchUserlist();//抓取資料庫使用者清單出來,並設置在userlist上
				} catch (SQLException e) {
					System.out.println("db.fetchUserlist()出現錯誤:" +e.toString());
				}
				
		//4把你輸入的帳密使用者:名字,地址,emaill,帳號,sesssion值傳到index.jsp頁面
				session.setAttribute("name", user.fetchname(userList, username));//設置參數為("name",fetchname抓取這個帳號人的名字("使用者清單",使用者帳號)
				session.setAttribute("address", user.fetchadd(userList, username));
				session.setAttribute("email", user.fetchemail(userList, username));
				session.setAttribute("username",username);
				System.out.println("登入成功");
				request.getRequestDispatcher("home.jsp").forward(request, response);//把參數帶到index.jsp頁面	
			}else {//如果帳密錯誤,把錯誤訊息,username,繼續待到登入頁面重登
				request.setAttribute("msg", "帳號或密碼錯誤");
				request.setAttribute("username", username);
				System.out.println("登入失敗");
				request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}
		//E.登出logout,把購物車清空,把session關掉
		if(page.equals("logout")) {
			//1.清掉使用者登入的session
			session = request.getSession(); //取得session
			session.invalidate();//清空使用者登入的session
			//2.清掉使用者的購物車,並且把空的購物車session跟沒有設置
			cartlist.clear();//清空購物車list陣列裡的資料
			session = request.getSession(); //取得新的session
			session.setAttribute("cartlist", cartlist);//把清空的購物車session傳到index頁面
			 session.setAttribute("list", list);
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
		//F.把商品list依照頁面不同送到不同頁面顯示
//		mobiles(手機),laptops(筆電),clothing(服飾),home-decor(家具裝飾),all-products(全部商品)
		if(page.equals("mobiles")||page.equals("laptops")||page.equals("clothing")||page.equals("home-decor")||page.equals("all-products")) {
		//1.把商品清單顯示出來,抓取資料庫商品,
			DB db = new DB();
			Product p = new Product();
			
			db.doConnent();
			try {
				list = db.fetchProList();//抓取資料庫的商品清單,並掛上去商品物件上
			} catch (SQLException e) {
				System.out.println("F.分類頁面商品清單抓取出現問題:" + e.toString());
			}	
		//2.把這個資料庫取得的Productlist,存到參數帶到這5個頁面去呈現
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
		//G.引導到購物車頁面
		if(page.equals("showcart")) {
			System.out.println("進入購物車列表");
			request.getRequestDispatcher("cart.jsp").forward(request, response);
		}
	
		//H.新增購物車按鈕,如果有重複警告重複,如果沒重複list陣列add(id),購物車新增一格商品id,新增玩續留你原本畫面
		if(page.equals("addtocart")) {
			//1.id參數要用於檢驗是否重複且顯示在購物車上,2.action參數用於Low to high,從url取得
			String id= request.getParameter("id"); //取得id參數,準備要驗證購物車是否重複
			String action = request.getParameter("action");//取得action參數,準備帶到low_to_highit去處理
			//2.驗證購物車是否重複,如果重複顯示重複訊息,如果沒重複購物車List.add(id).購物車陣列新增一筆資料
			Product p = new Product();
			boolean check = p.check(cartlist, id);//購物車有重複顯示true,沒重複false
			if(check) {
//				JOptionPane.showMessageDialog(null, "此商品已經被加入購物車", "Info", JOptionPane.INFORMATION_MESSAGE);
				System.out.println("商品已被加入購物車"+id);
			}else {
				cartlist.add(id);//在購物車陣列新增一筆(這個商品id是由add購物車頁面取得的參數)
//				JOptionPane.showMessageDialog(null, "成功加入購物車", "Info", JOptionPane.INFORMATION_MESSAGE);
				System.out.println("成功加入購物車:" +id);
			}
			//3.把id參數跟action帶到各自頁面去做,購物車+1呈現,action是從新增購物車按鈕取得而來
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
		//I.結帳成功頁面
			if(page.equals("success")) {
			request.getRequestDispatcher("success.jsp").forward(request, response);
			System.out.println("進入結帳成功頁面");
			/*session = request.getSession();
			 cartlist.clear();
			 session.setAttribute("cartlist", cartlist);*/
		}
		//J.刪除購物車的項目,需要id參數好刪除商品
		 if(page.equals("remove")) {
		//1.抓到刪除商品的id參數,把使用者點選的刪除商品,從購物中車中刪除
			 String id = request.getParameter("id");
			 Product p = new Product();
			cartlist = p.remove(cartlist, id);//刪除購物車裡面的ID商品
		//2.刪除消費者指定的購物車商品後,把刪除後的購物車參數傳回cart頁面去作呈現	
			session = request.getSession();
			session.setAttribute("cartlist", cartlist);//刪除商品後的購物車參數
			System.out.println("刪除購物車項目成功:" + id);
			request.getRequestDispatcher("cart.jsp").forward(request, response);//再回原頁面顯示
		 }
		 //K.價格排序
		 	if(page.equals("price-sort")) {
		 		//1.取得action可以帶入到頁面,取得sort存到price是低到高跟高到低的選項參數
		 		String action = request.getParameter("action");//取得要分頁面的參數
		 		String price = request.getParameter("sort");//取得low-to-high,high-to-low的參數
		 		//2.如果有含low-to-high執行,價格由低到高的方法,反之執行high-to-lo
		 		Product p = new Product();
		 		if(price.equals("low-to-high")) {
			 		list = p.lowtohigh(list);//商品價格由低到高排序
			 		session.setAttribute("list", list);
			 		System.out.println("使用者按下low-to-high");
			 	}else {
			 		list = p.hightolow(list);//商品價格由高到低排序
			 		session.setAttribute("list", list);//把排序好的list清單參數帶到各分頁去
			 		System.out.println("使用者按下hightolow");
		 		}
		 		//3.如果分頁有含這些頁面,分別把list價格排序參數帶到頁面
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
		 //L.搜尋商品
		 	if(page.equals("search_product")) {
		 		String name = request.getParameter("name");
		 		DB db = new DB();
		 		Product p = new Product();
		 		db.doConnent();
		 		try {
		 			list = db.searchProduct(name); //搜尋商品
				} catch (SQLException e) {
					System.out.println("搜尋商品失敗.." + e.toString());
				}
		 			request.setAttribute("list", list);//把搜尋後得商翉內容傳到search.jsp頁面
			 		System.out.println("把搜尋的商品資料" + list +"傳到search.jsp頁面");
			 		request.getRequestDispatcher("search.jsp").forward(request, response); 		
		 	}
		 	//M.送出信件後收到三餐數
		 	//抓到寄信後使用者的email跟hash瑪參數,調整狀態
		 		if(page.equals("mymail")) {
				String email = request.getParameter("key1");
				String hash = request.getParameter("key2");
				System.out.println("參數有近來");
				System.out.println("有包含mymail");
						DB db = new DB();
						db.doConnent();			
						try {
						int	i = db.changeActive(email, hash);
							if(i==1) { //如果更新成功到登入帳號頁面
								System.out.println("激活狀態" +email);
								request.getRequestDispatcher("login.jsp").forward(request, response);
							}
							else {
								System.out.println("激活失敗" + email);
								request.getRequestDispatcher("signup.jsp").forward(request, response);
							}
						} catch (SQLException e) {
							System.out.println("ActibeAccount錯誤" + e.toString());
						}
						db.dbClose();
				}	
		 		//N.點選商品加入到我的最愛
				if(page.equals("intoProduct")) {
					String id = request.getParameter("id");
					System.out.println("有抓到id參數:" + id);
					DB db = new DB();
					db.doConnent();
					try {
						list =db.intoProduct(id);
						System.out.println("成功進入指定商品:" + id);
					}catch (Exception e) {
						System.out.println("進入商品失敗" + e.toString());
					}
					request.setAttribute("list", list);
					System.out.println("導入頁面中" +list +"到intoProduct");
					request.getRequestDispatcher("favorite.jsp").forward(request, response);
				}
			}
			
	}	


