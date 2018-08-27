<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
	<meta content='width=330, height=400, initial-scale=1' name='viewport' />
	<link rel="icon" href="favicon.ico" type="image/x-icon" />
	<title>${initParam.title}</title>
	<link href="../ligerUI/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="../ligerUI/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />	
    <script src="../ligerUI/jquery/jquery-1.6.4.js" type="text/javascript"></script>
	<script src="../ligerUI/jquery-validation/jquery.form.js" type="text/javascript"></script> 
	<script src="../ligerUI/jquery-validation/jquery.validate.js" type="text/javascript"></script> 
    <script src="../ligerUI/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
    <script src="../ligerUI/jquery-validation/messages_cn.js" type="text/javascript"></script>    
    <script src="../ligerUI/ligerUI/js/ligerui.all.js" type="text/javascript"></script>       
	<script src="../ligerUI/ligerUI/js/template/ligerTemplate.js" type="text/javascript"></script>
	<script src="../ligerUI/ligerUI/js/template/ligerXFormGridList.js" type="text/javascript"></script>
	<script type="text/javascript" src="../ligerUI/ligerUI/js/pap.js"></script>
    <script type="text/javascript">
        var grid = null;
           $(function () {
                var toolBarOption = { 
   	                   items: [
      		                { text: '删除', icon: 'delete', expression:'==0', disabled:true, click: function(item) {
      		                	 if( grid.selected.length > 0 ){
       	   	   	      	    		$.ligerDialog.confirm('数据删除后不可恢复,你确认要删除?', function(data) {
       	   	   	          				var delIds = "";
       	   	   	          				$(grid.selected).each(function(i, item){
       	   	   	          					delIds += item['id']+",";
       	   	   	          				});
       	   	   	          				if(data){
       	   	   	          					$.ligerui.ligerAjax({
       	   	   	      							type: "POST",
       	   	   	      							async:  false,
       	   	   	      							url: "debug_system!delete",
       	   	   	      							data: { ids: delIds },
       	   	   	      							dataType: "text",
       	   	   	      							success: function(result, status){
       	   	   	      								if(result != ""){
       	   	   	      									tip = $.ligerDialog.tip({ title: '提示信息', content: result + '条记录被删除！' });
       	   	   	      									window.setTimeout(function(){ tip.close()} ,2000); 
       	   	   	      								}	
       	   	   	      							},
       	   	   	      							error: function(XMLHttpRequest,status){
       	   	   	      								$.ligerDialog.error('操作出现异常');
       	   	   	      							},
       	   	   	      							complete:function(){
       	   	   	      								grid.loadData(true);
       	   	   	      							}
       	   	   	      					   	});    			
       	   	   	      	    			}
       	   	   	          			});    			
       	   	   	      	    	}else {
       	   	   	      	    		$.ligerDialog.warn('请选择要删除的记录！')
       	   	   	      	    	}
          		            }}
                  		]
             		};

                form={
                    inputWidth: 130, 
                    labelWidth: 60, 
                    space:20,
	                fields:[
						{ display: "操作人", name:"e.operatorId", type:"select", newline:true, options:{
						    url:'users!load',
						    valueField: 'userCode', //关键项
							textField: 'userRealName',
							keySupport:true,
							selectBoxHeight: 100 
						}},
						{ display: "操作日期", name: "e.createDate", type: "date" }
				    ]
               	}
   	       
             	var gridoption={
             		url:'debug_system!loadByPage',
                    columns:[
                         { display: '编号', name: 'id', hide: true },
                         { display: '操作人编码', name: 'operatorId', hide: true },
                         { display: '操作人', name: 'operatorName', width: 100 },
                         { display: '操作时间', name: 'operTime', width: 150 },
                         { display: '业务对象', name: 'operBusiness', width: 150  },
                         { display: '入口方法', name: 'operFunction', width: 150  },
                         { display: '业务方法', name: 'serviceFunction', width: 150  },
                         { display: '跟踪方法', name: 'followFunction', width: 150  },
                         { display: '耗时(毫秒)', name: 'timeConsuming' },
                         { display: '创建人', name: 'createUser', width: 100  },
                         { display: '创建日期', name: 'createDate', width: 150  }
                 	]
                }
             
              var glist=$.pap.createFormGridList({ toolbar:toolBarOption,form:form,grid:gridoption});
              grid=glist.getGrid();
        });
    	
    </script>
 
</head>
<body>
</body>
</html>
