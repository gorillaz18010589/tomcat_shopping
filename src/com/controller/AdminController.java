package com.controller;
//客戶後臺管理模式_Controller
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
		//A.如果一開始page是空,必須先登入會員,導入頁面到login
			String page = request.getParameter("page");
			if(page == null) {
				System.out.println("page頁面參數沒抓到,轉入登入頁面");
				request.getRequestDispatcher("admin/login.jsp").forward(request, response);
			}else {
				doPost(request, response);
				System.out.println("dopost");
			}
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//B.登入指定帳號.密碼,如果成功跳轉到index頁面,失敗的話繼續在登入頁面
		String page = request.getParameter("page"); //取得page參數
		//1.取得使用者輸入的帳號,密碼參數
		if(page.equals("admin-login-form")) {
			String username = request.getParameter("username");//取得使用者輸入的帳號參數
			String password = request.getParameter("password");//取得使用者輸入的密碼參數
		//2.使用者輸入的帳號密碼,做驗證處理,如果帳密正確,導入index頁面,如果失敗的話設置顯示錯誤訊息參數,跟使用者帳號參數,繼續導入登入頁面做顯示
			if(username.equals("zdrgnkoiu") && password.equals("pink236545")) {
				System.out.println("帳號密碼正確:"+username + "導入index頁面" );
				request.getRequestDispatcher("admin/index.jsp").forward(request, response);
			}else {
				request.setAttribute("msg", "帳號或密碼錯誤請重新輸入");//設定錯誤訊息傳遞到login頁面
				request.setAttribute("username", username);
				System.out.println("登入失敗");
				request.getRequestDispatcher("admin/login.jsp").forward(request, response);
			}
		}
		//C.刪除商品資料庫
		if(page.equals("delete")) {
		//1.//取得點選的商品資料庫id參數,刪除指定商品的資料庫
			String id = request.getParameter("id");//取得點選的商品資料庫id參數
			DB db = new DB(); //呼叫資料庫物件實體
			try {
				db.deletProduct(id); //刪除商品資料庫(指定id商品)
				System.out.println("刪除了一筆商品資料:" + id);
			} catch (SQLException e) {
			
			}
			JOptionPane.showMessageDialog(null, "成功從資料庫刪除了" + id +"商品", "info", JOptionPane.INFORMATION_MESSAGE );
			request.getRequestDispatcher("admin/index.jsp").forward(request, response);//刪除後繼續再index頁面
		}
		
		//D.點選Home連接到admin/index.jsp頁面
		if(page.equals("index")) {
			request.getRequestDispatcher("admin/index.jsp").forward(request, response);
			System.out.println("進入Home主頁");
		}
		
		//E.點選addProduct連接到admin/addProduct.jsp
		if(page.equals("addproduct")) {
			request.getRequestDispatcher("admin/addProduct.jsp").forward(request, response);
			System.out.println("進入addProduct(增加商品)頁面");
		}
		
		//F."取得編輯商品的id參數",並且叫出"此商品的資訊清單"出來,並傳遞給edit_product頁面去做編輯to_G處理
		if(page.equals("edit")) {
			String id =  request.getParameter("id");
			DB db = new DB();
			Product p = null; //叫出商品物件一開始為空
			try {
				p = db.fetchProduct(id); //把指定id編號抓到的商品,調出商品資訊,並存入p
				System.out.println("要編輯的商品id是:" + id );
			} catch (SQLException e) {
				
			}
			request.setAttribute("p", p);//把指定的編輯商品資訊P,傳到編輯頁面做處理
			System.out.println("正在轉往編輯頁面去做處理...");
			request.getRequestDispatcher("admin/editProduct.jsp").forward(request, response);
		}
		
		//G."編輯商品內容"更新資料庫
		if(page.equals("edit_product")) {
			//1.取得你要修改的商品參數
			String id = request.getParameter("id");//取得商品的id參數,updateProduct方法需要此id來看是修改哪個商品,所以必須
			String name = request.getParameter("name");
			String price = request.getParameter("price");
			String category = request.getParameter("category"); //取得修改後的分類參數
			String featured = request.getParameter("featured"); //取得修改後的推薦參數
			//2.叫出Product物件實體,設定新的值進去
			Product p = new Product();
			p.setId(Integer.parseInt(id));  
			p.setName(name);
			p.setPrice(price);
			p.setCategory(category);
			p.setFeatured(featured);
			
			//3.更新編輯後的商品資料,更新到資料庫
			DB db =  new DB();
			try {
				db.updateProduct(p); //更新資料庫(商品陣列)
				System.out.println(id + ":" + name + "商品已更新成功");
			} catch (SQLException e) {
				System.out.println("商品更新失敗");
			}
//			JOptionPane.showMessageDialog(null, "商品已更新!!", "info", JOptionPane.INFORMATION_MESSAGE); 這句會卡網頁
			System.out.println("回到index.jsp頁面");
			request.getRequestDispatcher("admin/index.jsp").forward(request, response);
		}
		
		//H.新增商品
		if(page.equals("add_product")) {
			//1.取得輸入的商品資訊參數
			String name = request.getParameter("name");
			String price = request.getParameter("price");
			String category = request.getParameter("category");
			String featured = request.getParameter("featured");
			String image = request.getParameter("image");
			
			//2.把新增的商品資訊參數,設定上去Prodcut
			Product p = new Product();
			p.setName(name);
			p.setPrice(price);
			p.setCategory(category);
			p.setFeatured(featured);
			p.setImage("img/"+image);//"img/(黨位置底下)+你新增取得的圖片名字路徑"
			
			//3.把新增好的商品p,新增到資料庫
			DB db  = new DB();
			try {
				db.addProduct(p);
				System.out.println("新增商品成功:" + name);
			} catch (SQLException e) {
				System.out.println("新增商品失敗" +e.toString());
			}
			System.out.println("導入index.jsp頁面");
			request.getRequestDispatcher("admin/index.jsp").forward(request, response);
		}
	}

}
