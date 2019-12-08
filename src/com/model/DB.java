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

//��Ʈwapi
public class DB {
	private String username ="root";
	private String password="root";
	private String dbName="myproject"; //�w�W
	private String url ="jdbc:mysql://localhost:3305/" + dbName;
	private String driver ="com.mysql.jdbc.Driver"; //�X�ʵ{��
	
	private Connection con ; //�X�ʸg�z�H
	private ArrayList<Product> list = new ArrayList<Product>(); //�ŧi�ӫ~�M��
	private ArrayList<User> userList = new ArrayList<User>();//�ŧi�ϥΪ̲M��
	

	
	//"��Ʈw�s�u"
	public void doConnent() {
		try {
		Class.forName(driver);
		con = DriverManager.getConnection(url,username,password);//���o�X�ʸg�z�H
		}catch (Exception e) {
			System.out.println("doConnent�X�{���D:"+e.toString());
		}
	}
	
	//"��Ʈw����"
	public void dbClose() {
		try {
		con.close();
		}catch (Exception e) {
			System.out.println("dbClose�X�{�崣:" + e.toString());
		}
	}
	
	//"�s�W�ϥΪ̸��"�J��Ʈw
	public void addUser(User user)  { 
		String sql = "INSERT INTO user (name,address,email,username,password,hash) VALUES(?,?,?,?,?,?)";
		doConnent();//�s�u��k
		try {
		PreparedStatement pstmt =con.prepareStatement(sql);

		//��ϥΪ̿骺��ƨ��o
		String name = user.getName();
		String address = user.getAddress();
		String email = user.getEmail();
		String username = user.getUsername();
		String password = user.getPassword();
	
		//����hash CODE�üƺ�
		String hash ;
		Random random = new Random();
		random.nextInt(999999);
		hash = DigestUtils.md5Hex("" +random);
	
		//��ϥΪ̵��U��Ʒs�W���Ʈw
		pstmt.setString(1, name);
		pstmt.setString(2, address);
		pstmt.setString(3, email);
		pstmt.setString(4, username);
		pstmt.setString(5, password);
		pstmt.setString(6, hash);
	
		int i  = pstmt.executeUpdate();
		if(i != 0) { //�p�G��Ʈw����Ӥ�����0,�N���H���U�N�u�H
			SendingEmail se = new SendingEmail(email, hash);//�H�H���K��ѼƱa�쪬�A����B�z
			se.sendMail();//�u�T�{�H
			dbClose();
		}
//		pstmt.executeUpdate();//�}�l����
		System.out.println("���U�@���|����Ʀ��\" + email);
		
		}catch (Exception e) {
			System.out.println("�s�W�ϥΪ̸�Ʈw���~:" + e.toString());
		}
	}
	
	//"�ˬd�ϥΪ̱b���K�X"�O�_���T,true���u,false����
	public boolean checkUser(String username ,String password) throws SQLException {
		doConnent();//�s�u
		int count = 0;//��Ƥ@�}�l��0
		String sql = "Select * from user where username = ? and password = ?"; //�d��username��password 
		PreparedStatement pstmt =  con.prepareStatement(sql);	
		pstmt.setString(1, username);
		pstmt.setString(2, password);
		
		ResultSet rs = pstmt.executeQuery(); //��d�߱b����K�X�����G�s�brs
		while(rs.next()) {//��rs�٦��U�@�����ɭ�
			count = 1; //�i�h����1�N��true
		}
		dbClose();
		
		if(count == 0) {//�S�i�ӥN��b�K����
			return false; 
		}
		return true;
	}
	
	//������"Product�M��",���Ʈw�ȧ�X��,�A��ȳ]�m�bProduct�W
	public ArrayList<Product> fetchProList() throws SQLException{
		doConnent();//�s�u
		String sql = "SELECT * FROM product"; //�d�ӫ~��
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		//���o�ӫ~��Ʈw��쪺���
		while(rs.next()) {
		int id = rs.getInt("id");
		String name = rs.getString("name");//���o�ӫ~����Ʈwname���s��name
		String price= rs.getString("price");
		String category= rs.getString("category");
		String featured= rs.getString("featured");
		String image= rs.getString("image");
		//���Ͱӫ~�������,�]�m�q��Ʈw���o�����
		Product p = new Product(); 
		 p.setId(id);
		 p.setName(name);
		 p.setPrice(price);
		 p.setCategory(category);
		 p.setFeatured(featured);
		 p.setImage(image);
		 //���Ʈw����Ʊ���list�W
		 list.add(p);
		 p = null;
		}
		dbClose();
		return list;//�^�ǰӫ~�ʪ����M��
	}
	//�����Ʈw"�ϥΪ�(user)�M��",���Ʈw����X��,�A��ȳ]�m�buserlist�W
	public ArrayList<User> fetchUserlist() throws SQLException {
		//1.�s�u�d�߸�Ʈwuser��
		doConnent();
		String sql = "Select * from user"; 
		PreparedStatement st = con.prepareStatement(sql);
		ResultSet rs = st.executeQuery();
		
		//2.���ouser��ƭ���쪺��
		while(rs.next()) {
			String address = rs.getString("address");
			String user = rs.getString("username");
			String email = rs.getString("email");
			String name = rs.getString("name");
			int id = rs.getInt("id");
			String password = rs.getString("password");
		
		//3.���ͨϥΪ̪���,���Ʈw����쪺�ȳ]�w�W�h���ݩ�
			User u = new User();
			u.setAddress(address);
			u.setEmail(email);
			u.setId(id);
			u.setName(name);
			u.setUsername(user);
			u.setPassword(password);
		//4.��w���g�n���ϥΪ̰}�C,��ϥΪ̪���o����Ʊ��W�h
			userList.add(u);
			u=null;			
		}
		dbClose();
		return userList;//�^�ǨϥΪ̲M��
	}
	//��Jid"�R���ӫ~"
	public void deletProduct(String id) throws SQLException {
		doConnent();
		String sql ="DELETE FROM product WHERE id = ?"; //�R���qProduct�̪�id���R�� = "�A��J��id���X" 
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, id);
		pstmt.executeUpdate();
		dbClose();			
	}
	
	//��Jid"�d�ߦ�id�ӫ~���e"
	public Product fetchProduct(String id) throws SQLException {
		doConnent();
		//1.�d��product�� => id��� = �A��J��id���X
		String sql="SELECT * FROM product WHERE id = ? ";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1,id);
		ResultSet rs = pstmt.executeQuery();
		Product p = new Product();
		
		//2.��A�d�ߪ����,��Product���Ƴ]�m�W�h
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

	//"��s�ӫ~"  UPDATE "���W" SET "���1" = [�s��]  WHERE "����";
	public void updateProduct(Product p) throws SQLException {//�o�Ӱӫ~��id���X����h��,�N�|�h�ק�o�Ǹ��
		doConnent();
		String sql = "update product set name=?,price=?,category=?,featured=? where id=?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, p.getName());
		st.setString(2, p.getPrice());
		st.setString(3, p.getCategory());
		st.setString(4, p.getFeatured());
		st.setInt(5, p.getId());//���o�A�᪺Product,���o�̭���id,����o��id�bid����g
		st.executeUpdate();
		dbClose();
	}

	//"�s�W�ӫ~"
	public void addProduct(Product p) throws SQLException {
		doConnent();
		//1.�s�WProduct�� =>(5�����Ȭ�,�A��J�n��Product p����)
		String sql ="Insert into product(name,price,category,featured,image) values(?,?,?,?,?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		//2.���o�A��J�n��Product����,���o�A�]�w���ݩ�,�M���o�ǭȷs�W���Ʈw�W��
		pstmt.setString(1, p.getName());
		pstmt.setString(2, p.getPrice());
		pstmt.setString(3, p.getCategory());
		pstmt.setString(4, p.getFeatured());
		pstmt.setString(5, p.getImage());
		
		pstmt.executeUpdate();
		dbClose();
	}
	//�j�M�ӫ~�W�r(name),�եX��Ʈw���
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
	
	//�s�W��ڪ��̷R
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
	
		//1.�d��Email���ϥΪ̿�J��mail,�򲣥ͪ���,�Mactivie��0�����
	public int changeActive(String email, String hash)throws SQLException {	
			int i = 0;
			doConnent();
			String sql ="SELECT email,hash,active FROM user WHERE email = ? AND hash = ? AND active ='0'";
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, hash);
			ResultSet rs = pstmt.executeQuery();	
				if(rs.next()) {
					//2.��E�����A���,activie�אּ1,email��hash���]��s
					String sql1="UPDATE user SET active='1' where email = ? AND hash = ?";
					PreparedStatement pstmt1 = con.prepareStatement(sql1);
					pstmt1.setString(1, email);
					pstmt1.setString(2, hash);
					System.out.println("�d��email��hash�����");
					 i = pstmt1.executeUpdate();		
				}else {
					System.out.println("�S�i�ӴN�٬O0");
				}
			System.out.println("���J�i��Ƶ��A1");
			return i =1; 
			
		}
	}


