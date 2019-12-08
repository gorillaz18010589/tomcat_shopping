package com.beans;
//java.util.Collections.sort(List<Product> list, Comparator<? super Product> c) :排序(void方法) 
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Product implements Comparable<Product> { //實作Comparable
 private int id;
 private String name;
 private String price;
 private String category;
 private String featured;
 private String image;
 
 //取得屬性,設定屬性方法
 public int getId () {
	 return id;
 }
 public void setId(int id) {
	 this.id = id;
 }
 public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getFeatured() {
		return featured;
	}
	public void setFeatured(String featured) {
		this.featured = featured;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	//改寫toString方法,回傳proudct物件的屬性值
	@Override
		public String toString() {	
		return "Product [id=" + id + ", name=" + name + ", price=" + price + ", category=" + category + ", featured="
				+ featured + ", image=" + image + "]";
		}
	
//	(檢查購物車是否包含輸入的物件id,有的話回傳true)
	public boolean check(ArrayList<String> carlist, String id2) { //檢查(1.購物車,2.id2)
			for(String id : carlist) {
				if(id.equals(id2)) {//如果你尋訪的購物車裡面,有你已經放進購物車的東西時設定為true
					return true;
				}
			}
			return false;
	}
	

	
	//(刪除購物車項目) =>1輸入購物車,2.id號碼.會刪除購物車
	public ArrayList<String> remove(ArrayList<String> carlist, String id){
		for(String cid : carlist) {
			if(cid.equals(id)) { //如果尋訪裡面的購物車陣列有此id的話
				carlist.remove(cid); //刪除購物車
				break;
			}
		}
		return carlist;
	}
	
	//比較單一商品價差
	@Override
	public int compareTo(Product p) {
		return Integer.parseInt(this.price)  -Integer.parseInt(p.price) ;
	}
	
	//商品價格由高到低排序    
	//sort(List<Product> list, Comparator<? super Product> c)
	public ArrayList<Product>hightolow(ArrayList<Product>list){ //(1.list商品陣列,2.兩商品價差)
		 Collections.sort(list, new Temp()); //把List陣列的商品,照價格排序
		return list;
	}
	//商品價格由低到高排序
	public ArrayList<Product> lowtohigh(ArrayList<Product> list) {
		Collections.sort(list);
		return list;
	}
	//輸入兩個商品,算出價差實作Comparator<Product>
	class Temp implements Comparator<Product>{ //用Comparator<Product>強制要用Product類別帶入
		@Override //要兩個相比較所以用compare在overrider
		public int compare(Product o1, Product o2) { //比較(商品 o1, 商品 o2)
			return Integer.parseInt(o2.getPrice()) - Integer.parseInt(o1.getPrice());//商品2價格-商品1價格
		}
		
	}
	
	
}

