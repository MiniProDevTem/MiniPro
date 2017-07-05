package com.minipro.entity;

import java.io.Serializable;
import java.util.List;

public class Album implements Serializable{
	
	private List<Image> images;

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}
	

}
