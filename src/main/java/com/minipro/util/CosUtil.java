package com.minipro.util;

import com.minipro.conf.Parameter;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.request.DelFileRequest;
import com.qcloud.cos.request.UploadFileRequest;
import com.qcloud.cos.sign.Credentials;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

public class CosUtil {



	final static  long appId= Parameter.APP_ID;
	final static String secretId= Parameter.SECRET_ID;
	final static String secretKey = Parameter.SECRET_KEY;

	static ClientConfig clientConfig ;
	static Credentials cred;
	static   COSClient cosClient;
	
	static {
		clientConfig= new ClientConfig();
		clientConfig.setRegion("gz");
		cred = new Credentials(appId, secretId, secretKey);
		cosClient = new COSClient(clientConfig, cred);
	}

	public static String upload(String bucketName,String filePath){
		File file = new File(filePath);
		String fileName = file.getName();
		String cosFileName = String.format("/%s",fileName);
        UploadFileRequest uploadFileRequest =
                new UploadFileRequest(bucketName, cosFileName, filePath);
        uploadFileRequest.setEnableShaDigest(false);
        String uploadFileRet = cosClient.uploadFile(uploadFileRequest);
        LogUtil.log("upload file ret:" + uploadFileRet);
		return uploadFileRet;
	} 
	
	public static String del(String accessUrl){
		int index =accessUrl.indexOf("//");
		int end=accessUrl.indexOf("-");
		String bucketName = accessUrl.substring(index+2, end);
		
		index=accessUrl.lastIndexOf("/");
		String cosFilePath=accessUrl.substring(index);
		DelFileRequest delFileRequest = new DelFileRequest(bucketName, cosFilePath);
        String delFileRet = cosClient.delFile(delFileRequest);
        LogUtil.log("del file ret:" + delFileRet);
        return delFileRet;
	}


//	public static String download()
}
