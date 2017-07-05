package com.minipro.entity;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class Test {
	
	
	public static void main(String[] args) {
		Gson gson=new Gson();
		List<Image> images=new ArrayList<Image>();
		Album album=new Album();
		Image image1=new Image();
		image1.setUrl("11111");
		images.add(image1);
		Image image2=new Image();
		image2.setUrl("11111");
		images.add(image2);
		Image image3=new Image();
		image3.setUrl("11111");
		images.add(image3);
		Image image4=new Image();
		image4.setUrl("11111");
		images.add(image4);
		album.setImages(images);
		String s=gson.toJson(album);
		System.out.println(s);
		
		 album =(Album) gson.fromJson(s, Album.class);
		
		System.out.println(album);
	}

}
