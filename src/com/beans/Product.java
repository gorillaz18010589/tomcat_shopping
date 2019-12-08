package com.beans;
//java.util.Collections.sort(List<Product> list, Comparator<? super Product> c) :�Ƨ�(void��k) 
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Product implements Comparable<Product> { //��@Comparable
 private int id;
 private String name;
 private String price;
 private String category;
 private String featured;
 private String image;
 
 //���o�ݩ�,�]�w�ݩʤ�k
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
	
	//��gtoString��k,�^��proudct�����ݩʭ�
	@Override
		public String toString() {	
		return "Product [id=" + id + ", name=" + name + ", price=" + price + ", category=" + category + ", featured="
				+ featured + ", image=" + image + "]";
		}
	
//	(�ˬd�ʪ����O�_�]�t��J������id,�����ܦ^��true)
	public boolean check(ArrayList<String> carlist, String id2) { //�ˬd(1.�ʪ���,2.id2)
			for(String id : carlist) {
				if(id.equals(id2)) {//�p�G�A�M�X���ʪ����̭�,���A�w�g��i�ʪ������F��ɳ]�w��true
					return true;
				}
			}
			return false;
	}
	

	
	//(�R���ʪ�������) =>1��J�ʪ���,2.id���X.�|�R���ʪ���
	public ArrayList<String> remove(ArrayList<String> carlist, String id){
		for(String cid : carlist) {
			if(cid.equals(id)) { //�p�G�M�X�̭����ʪ����}�C����id����
				carlist.remove(cid); //�R���ʪ���
				break;
			}
		}
		return carlist;
	}
	
	//�����@�ӫ~���t
	@Override
	public int compareTo(Product p) {
		return Integer.parseInt(this.price)  -Integer.parseInt(p.price) ;
	}
	
	//�ӫ~����Ѱ���C�Ƨ�    
	//sort(List<Product> list, Comparator<? super Product> c)
	public ArrayList<Product>hightolow(ArrayList<Product>list){ //(1.list�ӫ~�}�C,2.��ӫ~���t)
		 Collections.sort(list, new Temp()); //��List�}�C���ӫ~,�ӻ���Ƨ�
		return list;
	}
	//�ӫ~����ѧC�찪�Ƨ�
	public ArrayList<Product> lowtohigh(ArrayList<Product> list) {
		Collections.sort(list);
		return list;
	}
	//��J��Ӱӫ~,��X���t��@Comparator<Product>
	class Temp implements Comparator<Product>{ //��Comparator<Product>�j��n��Product���O�a�J
		@Override //�n��Ӭۤ���ҥH��compare�boverrider
		public int compare(Product o1, Product o2) { //���(�ӫ~ o1, �ӫ~ o2)
			return Integer.parseInt(o2.getPrice()) - Integer.parseInt(o1.getPrice());//�ӫ~2����-�ӫ~1����
		}
		
	}
	
	
}

