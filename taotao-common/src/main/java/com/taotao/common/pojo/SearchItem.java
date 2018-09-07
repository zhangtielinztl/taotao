package com.taotao.common.pojo;

import java.io.Serializable;

public class SearchItem implements Serializable {
	private String id;
	private String title;
	private  String sellPoint;
	private long price;
	private String image;
	private String category_name;
	private String itemDesc;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}



	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	public String getSellPoint() {
		return sellPoint;
	}

	public void setSellPoint(String sellPoint) {
		this.sellPoint = sellPoint;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	@Override
	public String toString() {
		return "SearchItem{" +
				"id='" + id + '\'' +
				", title='" + title + '\'' +
				", sellPoint='" + sellPoint + '\'' +
				", price=" + price +
				", image='" + image + '\'' +
				", category_name='" + category_name + '\'' +
				", itemDesc='" + itemDesc + '\'' +
				'}';
	}

	public String [] getImages(){
		if(image!=null&&!"".equals(image)){
			String[] strings =image.split(",");
			return strings;
		}
		return null;
	}

}
