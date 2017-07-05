<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script src="http://localhost:8080/MiniPro/js/jquery-1.11.3.min.js" ></script>
</head>
<body>

<center>
<h1>测试是否存在</h1>
<input type="text" id="openId"><br>
<button onclick="testIsExit()">点击测试</button>
<br>
<br>
<hr>
<h1>测试添加用户</h1>
	openId:<input type="text" id="fopenId"><br>
	name:<input type="text" id="fname"><br>
	place:<input type="text" id="fplace"><br>
	birthday:<input type="text" id="fbirthday"><br>
	sex:<input type="text" id="fsex"><br>
	location:<input type="text" id="flocation"><br>
	qq:<input type="text" id="fqq"><br>
<button id="clickMe">点击添加</button>
</center>
<br>
<br>
<br>
<hr>
<center>
<h1>标记用户</h1>
uuid:<input type="text" id="muuid"><br>
	ouuid:<input type="text" id="mouuid"><br>
	isLike:<input type="text" id="misLike"><br>
	<button id="mark">标记用户</button></center>


<br>
<br>
<hr>
<center>
<h1>获取联系人</h1>
uuid：<input type="text" id="cuuid"><br>
<button onclick="getContact()">点击测试</button></center>

<br>
<br>
<hr>
<center>
<h1>获得用户详情</h1>
uuid：<input type="text" id="cduuid"><br>
<button onclick="getContactDetail()">点击测试</button></center>
<br>
<br>
<hr>
<center>
<h1>上传图片</h1>
uuid:<input type="text" id="imuuid"><br>
url:<input type="text" id="imUrl"><br>
<button id="upload">上传图片</button></center>
<br>
<br>
<hr>
<center>
<h1>删除图片</h1>
uuid:<input type="text" id="dimuuid"><br>
url:<input type="text" id="dimUrl"><br>
<button id="delete">删除图片</button></center>
<br>
<br>
<hr>
<center>
<h1>获得推荐人列表</h1>
uudi:<input type="text" id="comuuid" ><br>
sex:<input type="text" id="comsex"><br>
ageMin:<input type="text" id="comageMin"><br>
ageMax:<input type="text" id="comageMax"><br>

<button id="recommend">点击获得</button></center>

<br>
<br>
<hr>
<center>
<h1>添加游戏记录</h1>
uudi:<input type="text" id="giuudi" ><br>
heroId:<input type="text" id="giheroId"><br>
userTime:<input type="text" id="giuserTime"><br>
winRate:<input type="text" id="giwinRate"><br>
<button id="giButton">添加</button></center>

<br>
<br>
<hr>
<center>
<h1>添加英雄</h1>
hname:<input type="text" id="hname" ><br>
location:<input type="text" id="location"><br>
<button id="addHero">添加</button></center>

<br>
<br>
<hr>
<center>
<h1>添加游戏记录</h1>
uuid:<input type="text" id="gifuuid" ><br>
hid:<input type="text" id="gifhid"><br>
timeUser:<input type="text" id="giftimeUser"><br>
winRate:<input type="text" id="gifwinRate"><br>
<button id="addGameInform">添加</button></center>

</body>
<script>

function testIsExit(){
	var openID=$("#openId").val();
	$.get("http://localhost:8080/MiniPro/user/isExit?openId="+openID,
			function(json){
		alert(JSON.stringify(json));
	});
}

$("#clickMe").click(function() {
	var json = {
		"openId" : $("#fopenId").val(),
		"name" : $("#fname").val(),
		"place" : $("#fplace").val(),
		"birthday" : $("#fbirthday").val(),
		"sex" : $("#fsex").val(),
		"location" : $("#flocation").val(),
		"qq" : $("#fqq").val()
	};
	$.post("http://localhost:8080/MiniPro/user/create", {
		data : JSON.stringify(json)
	}, function(json) {
		alert(JSON.stringify(json));
	});

});

$("#mark").click(function() {
	var json = {
		"uuid" : $("#muuid").val(),
		"ouuid" : $("#mouuid").val(),
		"isLike" : $("#misLike").val()
	};
	$.post("http://localhost:8080/MiniPro/user/mark", {
		data : JSON.stringify(json)
	}, function(json) {
		alert(JSON.stringify(json));
	});

});
 
function getContact(){
	var openID=$("#cuuid").val();
	$.get("http://localhost:8080/MiniPro/user/getContacts?uuid="+openID,
			function(json){
		alert(JSON.stringify(json));
	});
}
function getContactDetail(){
	var openID=$("#cduuid").val();
	$.get("http://localhost:8080/MiniPro/user/contactDetail?uuid="+openID,
			function(json){
		alert(JSON.stringify(json));
	});
}

$("#upload").click(function() {
	var json = {
		"uuid" : $("#imuuid").val(),
		"imageUrl" : $("#imUrl").val()
	};
	alert(JSON.stringify(json));
	$.post("http://localhost:8080/MiniPro/user/uploadImage", {
		data : JSON.stringify(json)
	}, function(json) {
		alert(JSON.stringify(json));
	});

});

$("#delete").click(function() {
	var json = {
		"uuid" : $("#dimuuid").val(),
		"imageUrl" : $("#dimUrl").val()
	};
	alert(JSON.stringify(json));
	$.post("http://localhost:8080/MiniPro/user/delImage", {
		data : JSON.stringify(json)
	}, function(json) {
		alert(JSON.stringify(json));
	});

});

$("#recommend").click(function() {
	var json = {
		
		"uuid":$("#comuuid").val()
		
	};
	alert(JSON.stringify(json));
	$.post("http://localhost:8080/MiniPro/user/recommend", {
		data : JSON.stringify(json)
	}, function(json) {
		alert(JSON.stringify(json));
	});

});

$("#giButton").click(function() {
	var json = {
		"uuid":$("#giuudi").val(),
		"hid":$("#giheroId").val(),	
		"timeUser" : $("#giuserTime").val(),
		"winRate" : $("#giwinRate").val(),
		
	};
	$.post("http://localhost:8080/MiniPro/user/addGInform", {
		data : JSON.stringify(json)
	}, function(json) {
		alert(JSON.stringify(json));
	});

});

$("#addHero").click(function() {
	var json = {
		"hname":$("#hname").val(),
		"location":$("#location").val()
	};
	$.post("http://localhost:8080/MiniPro/date/addHero", {
		data : JSON.stringify(json)
	}, function(json) {
		alert(JSON.stringify(json));
	});

});
$("#addGameInform").click(function() {
	var json = {
		"uuid":$("#gifuuid").val(),
		"hid":$("#gifhid").val(),
		"timeUser":$("#giftimeUser").val(),
		"winRate":$("#gifwinRate").val()
	};
	$.post("http://localhost:8080/MiniPro/date/addGameInform", {
		data : JSON.stringify(json)
	}, function(json) {
		alert(JSON.stringify(json));
	});

});

</script>
</html>