package com.minipro.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.minipro.util.LogUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.minipro.authentication.AccessRequired;
import com.minipro.entity.JSONResult;
import com.minipro.service.UserService;
import com.minipro.service.param.UserServiceParam;
import com.minipro.util.CosUtil;

@Controller
@RequestMapping("/resource")
public class ResourceController extends AbstractController{
	@Autowired
	private UserService userService;

	private static final Logger logger = LoggerFactory.getLogger(CosUtil.class);
	
	@RequestMapping("/voiceUpload/{uuid}")
	@ResponseBody
	public String voiceUpload(@PathVariable("uuid")String uuid,@RequestParam(value="file",required=false)MultipartFile file,HttpServletRequest request){
		JSONResult rst=new JSONResult();
		
		rst.fail();
		
		String path = request.getSession().getServletContext().getRealPath("/temp");
		 String fileName=file.getOriginalFilename();  
         File targetFile=new File(path,uuid+"_voice_"+fileName);  

         if(!targetFile.exists()){  
               targetFile.mkdirs();  
         }  
         try {
			file.transferTo(targetFile);
			String result=CosUtil.upload("voice", targetFile.getAbsolutePath());
			JSONObject obj =new JSONObject(result);
			String code =obj.getString("code");
			if(!code.equals("0")){
				rst.fail("上传失败！");
				return rst.toJson();
			}
			
			JSONObject data = obj.getJSONObject("data");
			UserServiceParam.HeadOrVoiceParam p =new UserServiceParam.HeadOrVoiceParam();
			p.setUuid(uuid);
			p.setUploadUrl(data.getString("access_url"));
			p.setType(1);
			JSONResult rs=userService.addHearOrVoice(p);
			if(rs.getMessage().equals("fail")){
				rst.fail("上传失败！");
				return rst.toJson();
			}
			targetFile.delete();
         } catch (IllegalStateException e) {
        	 e.printStackTrace();
        	 rst.fail();
        	 return rst.toJson();
		} catch (IOException e) {
			e.printStackTrace();
			rst.fail();
       	 	return rst.toJson();
		} 
		 rst.success();
		 return rst.toJson();
	}
	
	
	@RequestMapping("/headUpload/{uuid}")
	@ResponseBody
	public String headUpload(@PathVariable("uuid")String uuid,@RequestParam(value="file",required=false)MultipartFile file,HttpServletRequest request){
		JSONResult rst=new JSONResult();
		
		rst.fail();
		
		String path = request.getSession().getServletContext().getRealPath("/temp");
		 String fileName=file.getOriginalFilename();  
         File targetFile=new File(path,uuid+"_head_"+fileName);  

         if(!targetFile.exists()){  
               targetFile.mkdirs();  
         }  
         try {
			file.transferTo(targetFile);
			String result=CosUtil.upload("head", targetFile.getAbsolutePath());
			JSONObject obj =new JSONObject(result);
			String code =obj.getString("code");
			if(!code.equals("0")){
				rst.fail("上传失败！");
				return rst.toJson();
			}
			
			JSONObject date=obj.getJSONObject("data");
			UserServiceParam.HeadOrVoiceParam p =new UserServiceParam.HeadOrVoiceParam();
			p.setUuid(uuid);
			p.setUploadUrl(date.getString("access_url"));
			p.setType(0);
			JSONResult rs=userService.addHearOrVoice(p);
			if(rs.getMessage().equals("fail")){
				rst.fail("上传失败！");
				return rst.toJson();
			}
			targetFile.delete();
         } catch (IllegalStateException e) {
        	 e.printStackTrace();
        	 rst.fail();
        	 return rst.toJson();
		} catch (IOException e) {
			e.printStackTrace();
			rst.fail();
       	 	return rst.toJson();
		} 
		 rst.success();
		 return rst.toJson();
	}
	
	
	@RequestMapping("/imageUpload/{uuid}")
	@ResponseBody
	public String imageUpload(@PathVariable("uuid")String uuid,@RequestParam(value="file",required=false)MultipartFile file,HttpServletRequest request){
		JSONResult rst=new JSONResult();
		LogUtil.log("uuid:"+uuid);
		
		LogUtil.log(uuid);
		rst.fail();
		String path = request.getSession().getServletContext().getRealPath("/temp");
		 String fileName=file.getOriginalFilename();  
         File targetFile=new File(path,uuid+"_image_"+fileName);  

         if(!targetFile.exists()){  
               targetFile.mkdirs();  
         }  
         LogUtil.log("保存成功");
         try {
			file.transferTo(targetFile);
			String result=CosUtil.upload("image", targetFile.getAbsolutePath());
			JSONObject obj =new JSONObject(result);
			String code =obj.getString("code");
			if(!code.equals("0")){
				rst.fail("上传失败！");
				return rst.toJson();
			}
			
			JSONObject date=obj.getJSONObject("data");
			UserServiceParam.UploadImageParam imageParam =new UserServiceParam.UploadImageParam();
			imageParam.setImageUrl(date.getString("access_url"));
			imageParam.setUuid(uuid);
			JSONResult rs=userService.uploadImage(imageParam);
			if(rs.getMessage().equals("fail")){
				rst.fail("上传失败！");
				return rst.toJson();
			}
			targetFile.delete();
         } catch (IllegalStateException e) {
        	 e.printStackTrace();
        	 rst.fail();
        	 return rst.toJson();
		} catch (IOException e) {
			e.printStackTrace();
			rst.fail();
       	 	return rst.toJson();
		} 
		 rst.success();
		 return rst.toJson();

	}
	
	@RequestMapping("/imageDelete/{uuid}/{imageUrl}")
	@ResponseBody
    @AccessRequired
	public String delImage(@PathVariable("uuid")String uuid,@PathVariable("imageUrl")String imageUrl){
		String data="{\"uuid\":"+uuid+",\"imageUrl\":"+imageUrl+"}";
		return invokeService("user","delImage",data);
	}
	
}
