<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    
    <title>预览</title>
	<link href="../ligerUI/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="../ligerUI/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />	
	<link rel="stylesheet" type="text/css" href="../ReportServer?op=emb&resource=finereport.css" />   
    <script src="../ligerUI/jquery/jquery-1.6.4.js" type="text/javascript"></script>
	<script src="../ligerUI/jquery-validation/jquery.form.js" type="text/javascript"></script> 
	<script src="../ligerUI/jquery-validation/jquery.validate.js" type="text/javascript"></script> 
    <script src="../ligerUI/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
    <script src="../ligerUI/jquery-validation/messages_cn.js" type="text/javascript"></script> 
    <script type="text/javascript" src="../ReportServer?op=emb&resource=finereport.js" charset="gbk"></script>
    <script type="text/javascript" src="../ligerUI/original/finereport/js/pager.js"></script>
    <script type="text/javascript">
    	$(function() {
			$("#reportFrame").css({ height: ($("body").height() - 24) });
        });
    </script>
    <style type="text/css">
    	html{overflow:hidden;}
    </style>
</head>
	<!-- 引用帆软报表工具条 -->
	<%@include file="/ligerUI/original/finereport/inc/pager_toolbar.jsp" %>
	<iframe id="reportFrame" src="../ReportServer?reportlet=${e.urlPath}&${e.params}&__showtoolbar__=false" width=100% scrolling="auto" frameborder="0" marginwidth="0" marginheight="0" />
</html>
