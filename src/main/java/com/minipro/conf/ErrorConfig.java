package com.minipro.conf;

/**
 * Created by chuckulu on 2017/7/4.
 */
public enum ErrorConfig {
        
	 NOTFOUND(10), 
     INVALIDPARAM(11), 
     NOTAUTHORIZATION(20), 
     SERVERERROR(21),
     USERNOTEXIT(31),
	 INVALPARAM(30);
     private int errorCode;
     
     private ErrorConfig(int errorCode){
    	 this.errorCode=errorCode;
     }
     
     private ErrorConfig(){ 
    	 
     }

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
     
	
}
