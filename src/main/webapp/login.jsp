<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<meta content='width=330, height=400, initial-scale=1' name='viewport' />
<link rel="shortcut icon" href="image/logo.ico" type="image/x-icon"/> 
<title>水产养殖</title>
<link href="ligerUI/ligerUI/skins/Aqua/css/ligerui-dialog.css" rel="stylesheet" type="text/css" />
<link href="ligerUI/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />	
<script src="ligerUI/jquery/jquery-1.6.4.js" type="text/javascript"></script>
<script src="ligerUI/jquery-validation/jquery.form.js" type="text/javascript"></script> 
<script src="ligerUI/jquery-validation/jquery.validate.js" type="text/javascript"></script> 
<script src="ligerUI/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
<script src="ligerUI/jquery-validation/messages_cn.js" type="text/javascript"></script> 
<script type="text/javascript" src="ligerUI/ligerUI/js/ligerui.all.js"></script> 
<style type="text/css">
/*reset*/
body, div, dl, dt, dd, ul, ol, li, h1, h2, h3, h4, h5, h6, PRe, form, fieldset, input, textarea, p, blockquote, th, td, span {
	margin:0;
	padding:0;
}
table {
	border-collapse:collapse;
	border-spacing:0;
}
fieldset, img {
	border:0;
}
address, caption, cite, code, dfn, em, strong, th, var {
	font-style:normal;
	font-weight:normal;
}
ol, ul {
	list-style:none;
}
caption, th {
	text-align:left;
}
h1, h2, h3, h4, h5, h6 {
	font-size:100%;
	font-weight:normal;
}
/*new*/
body {
	background:url(image/bg.jpg) no-repeat center top;
	
}
.content {
    font-family:"Microsoft YaHei",微软雅黑,"Microsoft JhengHei",华文细黑,STHeiti,MingLiu;
	width:280px;
	height:235px;
	position:relative;
	left:57.5%;
	top:300px;
}
.content li {
	margin-bottom:15px;
}
.content i {
	width:29px;
	height:25px;
	background:url(image/ico.png) no-repeat left top;
	display:inline-block;
	vertical-align:-13%;
}
.content input {
	padding:0 0 0 25px;
	width:258px;
	height:35px;
	border:1px solid #bcbbc0;
	border-radius:5px;
	background:url(image/ico.png) no-repeat 0 -33px;
}
.content .pass{ background-position:0 -69px;}
.content .btn {
    font-family:"Microsoft YaHei",微软雅黑,"Microsoft JhengHei",华文细黑,STHeiti,MingLiu;
	padding:0;
	width:285px;
	border:none;
	background:#31aafa;
	height:40px;
	color:#FFF;
	cursor:pointer;
}
.content .btn:hover {
	background:#51b9fd;
}

</style>
<script type="text/javascript">

	if(top.window !== window){
	    top.location = window.location;
	}
	$(function(){
		$("#userPassword").bind("keydown", function(e) {
			// 密码框的回车事件
			if (e.keyCode == 13) { 
				$("#submitB").trigger("click");
			}
		});
		$("#submitB").bind("click",function(){
			if (!login()) {
				return false;
			}
			$.ajax({
				type:"POST",
				async: false,
				url:"login!login",
				data:$("#form1").formSerialize(),
				dataType:"text",
				beforeSend:function(){
				   $.ligerDialog.waitting('正在登录,请稍候...');
				},
				success:function(result, textStatus){
					if(result == 'LOGINOK' )
			        	window.location.href= "page/index.jsp";
			        else if(result=='LOGINSTOP')
			        	$.ligerDialog.error('用户已经停用');
			        else	
			          $.ligerDialog.error('账号或密码出现错误');
				},
				error: function(XMLHttpRequest,textStatus){
					 $.ligerDialog.error('操作出现异常');
				},
				complete:function(){
				   setTimeout(function() {
						$.ligerDialog.closeWaitting();
				   }, 1000);
	    		}
			});
		});
	});
	// 登录方法
	function login() {
		var flag = true;
		var account = document.getElementById("userCode").value.trim();
		var pwd = document.getElementById("userPassword").value.trim();
		
		with (document.getElementById("RequiredFieldValidator3").style) {
			if (account == "") {
				visibility = "visible";
				flag = false;
			} else 
				visibility = "hidden";
		}
	
		with (document.getElementById("RequiredFieldValidator4").style) {
			if (pwd == "") {
				visibility = "visible";
				flag = false;
			} else 
				visibility = "hidden";
		}
	
		return flag;
	}
</script>
</head>

<body>
<div class="content">
  <ul>
  <li><i></i><span>水产养殖系统</span></li>
  <form id="form1" name="form1" method="post">
   <ul>
    <li>
     <div style="display:inline;float:left;width: 500px;height: 50px;"><input id="userCode" name="e.userCode"  value="admin" type="text" /><span id="RequiredFieldValidator3" style="VISIBILITY: hidden; COLOR: red;">请输入登录名</span></div>
    </li>
    <li>
     <div style="display:inline;float:left;width: 500px;height: 50px;"><input id="userPassword"  type=password name="e.userPassword" value="1"/><span id="RequiredFieldValidator4" style="VISIBILITY: hidden; COLOR: red">请输入密码</span></div>
    </li>
    <li>
      <input type="button" id="submitB" class="btn" value="登录"/>
    </li>
    </ul>
  </form>
</div>
</body>
</html>
