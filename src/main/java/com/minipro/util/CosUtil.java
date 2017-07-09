package com.minipro.util;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.request.DelFileRequest;
import com.qcloud.cos.request.UploadFileRequest;
import com.qcloud.cos.sign.Credentials;

public class CosUtil {

	final static  long appId= 1253746863;
	final static String secretId= "AKID1S6261nshoqHxbzpCyx5Gwlh7x6aF9nm";
	final static String secretKey = "ns6CCi6MlVoXQCouBWIwSuQN6Cj7mhhe";
	final static String bucketName = "image";
	static ClientConfig clientConfig ;
	static Credentials cred;
	static   COSClient cosClient;
	
	static {
		clientConfig= new ClientConfig();
		clientConfig.setRegion("gz");
		cred = new Credentials(appId, secretId, secretKey);
		cosClient = new COSClient(clientConfig, cred);
	}

	
	public static void main(String[] args) {
		String result=upload("image","c:/test.png");
		Gson gson=new Gson();

		JSONObject obj =new JSONObject(result);
		JSONObject date=obj.getJSONObject("data");
		System.out.println(date.get("access_url"));
	}
	
	public static String upload(String bucketName,String filePath){
		String fileName=filePath.substring(filePath.lastIndexOf("/"));
        UploadFileRequest uploadFileRequest =
                new UploadFileRequest(bucketName, fileName, filePath);
        uploadFileRequest.setEnableShaDigest(false);
        String uploadFileRet = cosClient.uploadFile(uploadFileRequest);
        System.out.println("upload file ret:" + uploadFileRet);
        return uploadFileRet;
	} 
	
	public static String del(String accessUrl){
		int index =accessUrl.indexOf("//");
		int end=accessUrl.indexOf("-");
		String bucketName=accessUrl.substring(index+2, end);
		
		 index=accessUrl.lastIndexOf("/");
		String cosFilePath=accessUrl.substring(index);
		DelFileRequest delFileRequest = new DelFileRequest(bucketName, cosFilePath);
        String delFileRet = cosClient.delFile(delFileRequest);
        System.out.println("del file ret:" + delFileRet);
        return delFileRet;
	}
	
//	public static String download()
}
