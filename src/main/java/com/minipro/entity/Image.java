package com.minipro.entity;

import java.io.Serializable;

public class Image implements Serializable,Comparable<Image>{
	
    private static final long serialVersionUID = -6014241320070993952L;
    private String url;
    private long timestamp;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	@Override
	public int compareTo(Image o) {
		if(timestamp<o.getTimestamp()){
			return -1;
		}else if(timestamp==o.getTimestamp()){
			return 0;
		}else{
			return 1;
		}
	}
}
