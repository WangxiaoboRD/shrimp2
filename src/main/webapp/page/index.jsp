<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	response.setDateHeader("Expires", 0);
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<meta content='width=330, height=400, initial-scale=1' name='viewport' />
	<link rel="shortcut icon" href="../image/logo.ico" type="image/x-icon"/> 
	<title>${initParam.title}</title>
	<link href="../ligerUI/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="../ligerUI/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />	
    <script src="../ligerUI/jquery/jquery-1.6.4.js" type="text/javascript"></script>
	<script src="../ligerUI/jquery-validation/jquery.form.js" type="text/javascript"></script> 
	<script src="../ligerUI/jquery-validation/jquery.validate.js" type="text/javascript"></script> 
    <script src="../ligerUI/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
    <script src="../ligerUI/jquery-validation/messages_cn.js" type="text/javascript"></script> 
	<script type="text/javascript" src="../ligerUI/ligerUI/js/ligerui.all.js"></script> 
    <script type="text/javascript">
    	var tab = null;
        var accordion = null;
        var tree = null;

        function KeepSessionAlive() {
            $.post("users!getReflus");
        }
        setInterval(KeepSessionAlive, 1000*60*20);
    	$(function (){
    		
    		//设置布局的样式
    		$("#mainLayout").ligerLayout({
    			leftWidth:190,bottomHeight:30,
    			height:'100%',width:'100%',
    			allowTopResize:false,
    			allowBottomResize:false,
    			onHeightChanged:heightChanged,space:2	});
    		//此处也是关键点	
    		var height = $(".l-layout-center").height();
    		
    		$("#framecenter").ligerTab({ height: height });
    		
    		$("#mainAccordion").ligerAccordion({ height: height - 24, speed: null });
    		
    		$(".l-link").hover(function(){
                $(this).addClass("l-link-over");
            }, function(){
                $(this).removeClass("l-link-over");
            });
            
            $("#mainTree").ligerTree({
				url:'user_auth_value!indexMenu',
                idFieldName :'id',
                parentIDFieldName:'parentId',
                nodeWidth :100,
                textFieldName:'itemName',//显示的菜单树名称
                attribute: ['id', 'url','name'],
                attributeMapping:["id","url",'itemName'],//添加到菜单html>li标记上的属性映射值
				checkbox:false,
				btnClickToToggleOnly: false,
				isExpand: 1,
                onSelect: function (node){
        	        if (!node.data.url) return;
	                var tabid = $(node.target).attr("tabid");
                	if (!tabid){
                		tabid = new Date().getTime();
                		$(node.target).attr("tabid", tabid);
                	}
                	addTab(tabid, node.data['itemName'], node.data.url);
        		 }
        });
            //定义各种组件的管理对象
            tab = $("#framecenter").ligerGetTabManager();
            accordion = $("#mainAccordion").ligerGetAccordionManager();
            tree = $("#mainTree").ligerGetTreeManager();
            
    		$("#pageloading").hide();

    		 // 获得系统当前日期
            var date = new Date();
            var weekDay = ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];
            var dateStr = "当前日期：" + date.getFullYear() + "年" + (date.getMonth() + 1) + "月" + date.getDate() + "日\t"+ weekDay[date.getDay()];
            $("#currentDate").text(dateStr);
    	});
    	
    	function heightChanged(options){
    		if(tab){ //添加高度补差
               tab.addHeight(options.diff);
            }
            if(accordion && options.middleHeight - 24 > 0){
              accordion.setHeight(options.middleHeight - 24);
            }
    	}
    	function addTab(tabid,text, url){ 
        	tab.addTabItem({ tabid : tabid,text: text, url: url});
        } 

		// 密码修改
    	function modifyPwd() {
    		$.ligerDialog.open({ 
   				title:'密码修改',
   				url: 'users_modifypwd.jsp', 
   				height: 200,
   				width: 350, 
   				buttons: [ 
   					{ text: '确定', onclick: function(item, dialog) {
   						var data = dialog.frame.onSave();
      	   		    	if(data!=null){
              	   		    if (data == 1) {
              	   		   		$.ligerDialog.warn('新密码与确认新密码输入不一致，请重新输入！');
              	   		   		return;
                      	    }
              	   		    
      	   		    		$.ligerui.ligerAjax({
		       					url:"users!modifyPwd",
		       					dataType:"text",
		       					data:data,
		       					success:function(_data,textStatus){
		       						if(_data == 'MODIFYOK'){
	       								dialog.close();
	       								$.ligerDialog.confirm('密码修改成功！是否进行重新登录？', function(flag) {
	       					            	if (flag) {
	       					            		location.href="../login.jsp";
	       					                }
	       					        	});
		       						}
		       					},
		       					error: function(XMLHttpRequest,textStatus){
		       						$.ligerDialog.error('操作出现异常');
		       					}
		       				});		
      	   	    		}	
      	   			}}, 
   					{ text: '取消', onclick: function(item, dialog) {
      	   				dialog.close();
  	   				}}
  	   			] 
   			});
        }

		// 退出系统
        function exitSys() {
        	$.ligerDialog.confirm('确定要退出系统吗？', function(flag) {
            	if (flag) {
            		location.href="../login.jsp";
                }
        	});
        }
        /**
        //下载说明书
        function downLoadManual(){
        	$.ligerDialog.confirm('确定要下载说明书吗？', function(flag) {
            	if (flag) {
            		 $.download($('form'), 'breed_detail!downloadTemplate');
                }
        	});
        
        }
        */
    </script>
    <style type="text/css">
    	body,html{padding:0px; margin:0;}
    	html{overflow:visible;}
    	body{overflow:hidden;}
    	.l-layout-top{background:#102A49;}
    	.l-layout-bottom{background:#E5EDEF;text-align:center;}
    	.l-link{ display:block; line-height:22px; height:22px; padding-left:20px;border:1px solid white; margin:4px;}
    	.l-link-over{ background:#FFEEAC; border:1px solid #DB9F00;}
    	
    	.toplinks{float:right;line-height:20px;height:20px;position: absolute;right: 5px;top: 5px; color:#bbd4ef;}
		.toplinks,.toplinks a{font-family:Arial,sans-serif;font-size:12px;}
		.toplinks a{margin:0 3px;text-decoration:none;}
		.toplinks a:hover{text-decoration:underline;}
    </style>
</head>

<body>
	<div id="pageloading"></div>
	<div id="mainLayout">
		<div position="top">	
			<div style="height:52px;background:url(../image/top.jpg) repeat-x;">
				<div class="toplinks">
					  <!--  <a style="text-decoration:none;color:#333333;" href="javascript:downLoadManual()"><img src="../image/header_down.jpg" width="14" height="12" style="position: absolute;top: 3px;right: 280px;"/><font color="#FFFFFF">下载说明书</font></a> | &nbsp;&nbsp;&nbsp;&nbsp;-->
					  <a href="javascript:modifyPwd()" style="text-decoration:none;color:#333333;">
					  <img src="../image/header_edit.png" width="16" height="15" style="position: absolute;top: 4px;right: 185px;"/><font color="#FFFFFF">密码修改</font></a> | &nbsp;&nbsp;&nbsp;&nbsp;
					  <a style="text-decoration:none;color:#333333;" href="#"><img src="../image/header_help.png" width="16" height="16" style="position: absolute;top: 3px;right: 106px;"/><font color="#FFFFFF">帮助</font></a> |&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
					  <a style="text-decoration:none;color:#333333;" href="javascript:exitSys()"><img src="../image/header_exit.png" width="16" height="16" style="position: absolute;top: 3px;right: 53px;"/><font color="#FFFFFF">退出系统</font></a>			
				</div>
				<div style="float:right;line-height:20px;height:20px;position: absolute;right: 220px;top: 25px;">
					<span id="currentDate" style="color:#FFFFFF;font-family:Arial,sans-serif;font-size:12px;"></span>
				</div>
			</div>
		</div>
		<div position="left"  title="系统菜单" id="mainAccordion"> 
			<div title="功能列表" class="l-scroll"><!--  此处作者非常聪明写一个 ul>li>ul>li的递归就可以搞定树的生成     -->
				 <ul id="mainTree" style="margin-top:3px;">
				 </ul>
			</div>
		</div>
		<div position="center" id="framecenter" >
			<div tabid="home" title="我的主页" style="height:300px;z-index: 99999999999999999;" >
               <iframe frameborder="0" name="home" id="home" src="welcome.jsp"></iframe>
            </div> 
		</div>
		<div position="bottom" style="height:39px;backgroundColor:#EAF1FB" >
			 <div align="center" style="display: inline;position: absolute;top: 5px">2016正大集团-中东南十省区.All Rights Reserved.</div>
			 <div style="display: inline;float: right;position: absolute;top: 5px;right: 10px">当前用户：${session.CURRENTUSER.userRealName}</div>
		</div>
	</div><!-- 整个页面的div结束 -->
</body>
</html>
