package com.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;

import com.beans.Product;
import com.beans.User;
import com.mail.SendingEmail;

//資料庫api
public class DB {
	private String username ="root";
	private String password="root";
	private String dbName="myproject"; //庫名
	private String url ="jdbc:mysql://localhost:3305/" + dbName;
	private String driver ="com.mysql.jdbc.Driver"; //驅動程式
	
	private Connection con ; //驅動經理人
	private ArrayList<Product> list = new ArrayList<Product>(); //宣告商品清單
	private ArrayList<User> userList = new ArrayList<User>();//宣告使用者清單
	

	
	//"資料庫連線"
	public void doConnent() {
		try {
		Class.forName(driver);
		con = DriverManager.getConnection(url,username,password);//取得驅動經理人
		}catch (Exception e) {
			System.out.println("doConnent出現問題:"+e.toString());
		}
	}
	
	//"資料庫關閉"
	public void dbClose() {
		try {
		con.close();
		}catch (Exception e) {
			System.out.println("dbClose出現文提:" + e.toString());
		}
	}
	
	//"新增使用者資料"入資料庫
	public void addUser(User user)  { 
		String sql = "INSERT INTO user (name,address,email,username,password,hash) VALUES(?,?,?,?,?,?)";
		doConnent();//連線方法
		try {
		PreparedStatement pstmt =con.prepareStatement(sql);

		//把使用者輸的資料取得
		String name = user.getName();
		String address = user.getAddress();
		String email = user.getEmail();
		String username = user.getUsername();
		String password = user.getPassword();
	
		//產生hash CODE亂數瑪
		String hash ;
		Random random = new Random();
		random.nextInt(999999);
		hash = DigestUtils.md5Hex("" +random);
	
		//把使用者註冊資料新增到資料庫
		pstmt.setString(1, name);
		pstmt.setString(2, address);
		pstmt.setString(3, email);
		pstmt.setString(4, username);
		pstmt.setString(5, password);
		pstmt.setString(6, hash);
	
		int i  = pstmt.executeUpdate();
		if(i != 0) { //如果資料庫有近來不等於0,代表有人註冊就季信
			SendingEmail se = new SendingEmail(email, hash);//寄信順便把參數帶到狀態那邊處理
			se.sendMail();//季確認信
			dbClose();
		}
//		pstmt.executeUpdate();//開始執行
		System.out.println("註冊一筆會員資料成功" + email);
		
		}catch (Exception e) {
			System.out.println("新增使用者資料庫錯誤:" + e.toString());
		}
	}
	
	//"檢查使用者帳號密碼"是否正確,true為真,false為假
	public boolean checkUser(String username ,String password) throws SQLException {
		doConnent();//連線
		int count = 0;//比數一開始為0
		String sql = "Select * from user where username = ? and password = ?"; //查詢username跟password 
		PreparedStatement pstmt =  con.prepareStatement(sql);	
		pstmt.setString(1, username);
		pstmt.setString(2, password);
		
		ResultSet rs = pstmt.executeQuery(); //把查詢帳號跟密碼的結果存在rs
		while(rs.next()) {//當rs還有下一筆的時候
			count = 1; //進去等於1代表true
		}
		dbClose();
		
		if(count == 0) {//沒進來代表帳密為假
			return false; 
		}
		return true;
	}
	
	//抓取資料"Product清單",把資料庫值抓出來,再把值設置在Product上
	public ArrayList<Product> fetchProList() throws SQLException{
		doConnent();//連線
		String sql = "SELECT * FROM product"; //查商品表
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		//取得商品資料庫欄位的資料
		while(rs.next()) {
		int id = rs.getInt("id");
		String name = rs.getString("name");//取得商品的資料庫name欄位存到name
		String price= rs.getString("price");
		String category= rs.getString("category");
		String featured= rs.getString("featured");
		String image= rs.getString("image");
		//產生商品物件實體,設置從資料庫取得的資料
		Product p = new Product(); 
		 p.setId(id);
		 p.setName(name);
		 p.setPrice(price);
		 p.setCategory(category);
		 p.setFeatured(featured);
		 p.setImage(image);
		 //把資料庫的資料掛到list上
		 list.add(p);
		 p = null;
		}
		dbClose();
		return list;//回傳商品購物車清單
	}
	//抓取資料庫"使用者(user)清單",把資料庫欄位抓出來,再把值設置在userlist上
	public ArrayList<User> fetchUserlist() throws SQLException {
		//1.連線查詢資料庫user表
		doConnent();
		String sql = "Select * from user"; 
		PreparedStatement st = con.prepareStatement(sql);
		ResultSet rs = st.executeQuery();
		
		//2.取得user資料個欄位的值
		while(rs.next()) {
			String address = rs.getString("address");
			String user = rs.getString("username");
			String email = rs.getString("email");
			String name = rs.getString("name");
			int id = rs.getInt("id");
			String password = rs.getString("password");
		
		//3.產生使用者物件,把資料庫個欄位的值設定上去為屬性
			User u = new User();
			u.setAddress(address);
			u.setEmail(email);
			u.setId(id);
			u.setName(name);
			u.setUsername(user);
			u.setPassword(password);
		//4.把預先寫好的使用者陣列,把使用者表取得的資料掛上去
			userList.add(u);
			u=null;			
		}
		dbClose();
		return userList;//回傳使用者清單
	}
	//輸入id"刪除商品"
	public void deletProduct(String id) throws SQLException {
		doConnent();
		String sql ="DELETE FROM product WHERE id = ?"; //刪除從Product裡的id欄位刪除 = "你輸入的id號碼" 
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, id);
		pstmt.executeUpdate();
		dbClose();			
	}
	
	//輸入id"查詢此id商品內容"
	public Product fetchProduct(String id) throws SQLException {
		doConnent();
		//1.查詢product表 => id欄位 = 你輸入的id號碼
		String sql="SELECT * FROM product WHERE id = ? ";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1,id);
		ResultSet rs = pstmt.executeQuery();
		Product p = new Product();
		
		//2.把你查詢的資料,用Product把資料設置上去
		while(rs.next()) {
			p.setId(rs.getInt("id"));
			p.setName(rs.getString("name"));
			p.setPrice(rs.getString("price"));
			p.setCategory(rs.getString("category"));
			p.setFeatured(rs.getString("featured"));
			p.setImage(rs.getString("image"));
		}
		dbClose();
		return p;
	}

	//"更新商品"  UPDATE "表格名" SET "欄位1" = [新值]  WHERE "條件";
	public void updateProduct(Product p) throws SQLException {//這個商品的id號碼等於多少,就會去修改這些資料
		doConnent();
		String sql = "update product set name=?,price=?,category=?,featured=? where id=?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, p.getName());
		st.setString(2, p.getPrice());
		st.setString(3, p.getCategory());
		st.setString(4, p.getFeatured());
		st.setInt(5, p.getId());//取得你丟的Product,取得裡面的id,把取得的id在id欄位改寫
		st.executeUpdate();
		dbClose();
	}

	//"新增商品"
	public void addProduct(Product p) throws SQLException {
		doConnent();
		//1.新增Product表 =>(5個欄位值為,你輸入好的Product p物件)
		String sql ="Insert into product(name,price,category,featured,image) values(?,?,?,?,?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		//2.取得你輸入好的Product物件,取得你設定的屬性,然後把這些值新增到資料庫上面
		pstmt.setString(1, p.getName());
		pstmt.setString(2, p.getPrice());
		pstmt.setString(3, p.getCategory());
		pstmt.setString(4, p.getFeatured());
		pstmt.setString(5, p.getImage());
		
		pstmt.executeUpdate();
		dbClose();
	}
	//搜尋商品名字(name),調出資料庫資料
	public ArrayList<Product> searchProduct(String name) throws SQLException {
		doConnent();
		Product p = new Product();
		String sql = "SELECT * FROM product WHERE name = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, name);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			name = rs.getString("name");
			String	price = rs.getString("price");
			String	category = rs.getString("category");
			String	featured = rs.getString("featured");
			String  image = rs.getString("image");
			int id = rs.getInt("id");
			
			p.setId(id);
			p.setName(name);
			p.setPrice(price);
			p.setCategory(category);
			p.setFeatured(featured);
			p.setImage(image);
			list.add(p);
		}
			dbClose();
			return list;
	}
	
	//新增到我的最愛
		public ArrayList<Product> intoProduct(String id) throws SQLException {
			doConnent();
			Product p = new Product();
			String sql = "SELECT * FROM product WHERE id = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				String name = rs.getString("name");
				String	price = rs.getString("price");
				String	category = rs.getString("category");
				String	featured = rs.getString("featured");
				String  image = rs.getString("image");
				int id1 = Integer.parseInt(rs.getString("id")) ;
				
				p.setId(id1);
				p.setName(name);
				p.setPrice(price);
				p.setCategory(category);
				p.setFeatured(featured);
				p.setImage(image);
				list.add(p);
			}
				dbClose();
				return list;
		}
	
		//1.查詢Email欄位使用者輸入的mail,跟產生的瑪,和activie為0的資料
	public int changeActive(String email, String hash)throws SQLException {	
			int i = 0;
			doConnent();
			String sql ="SELECT email,hash,active FROM user WHERE email = ? AND hash = ? AND active ='0'";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, hash);
			ResultSet rs = pstmt.executeQuery();	
				if(rs.next()) {
					//2.把激活狀態更改,activie改為1,email跟hash瑪也更新
					String sql1="UPDATE user SET active='1' where email = ? AND hash = ?";
					PreparedStatement pstmt1 = con.prepareStatement(sql1);
					pstmt1.setString(1, email);
					pstmt1.setString(2, hash);
					System.out.println("查詢email跟hash有近來");
					 i = pstmt1.executeUpdate();		
				}else {
					System.out.println("沒進來就還是0");
				}
			System.out.println("有入進資料給你1");
			return i =1; 
			
		}
	}


