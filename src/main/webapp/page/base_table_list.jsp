<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
	<meta content='width=330, height=400, initial-scale=1' name='viewport' />
	<link rel="icon" href="favicon.ico" type="../image/x-icon" />
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
                       		{ text: '新增', click: addClick, icon: 'add' },
                   		    { line: true },
       		                { text: '修改', click: modifyClick, icon: 'modify' ,expression:'!=1',disabled:true },
       		               	{ line:true },
   	   	   	                { text: '删除', icon: 'delete', expression:'==0', disabled:true, click: function(){
   	   	   	            	  if( grid.selected.length > 0 ){
   	   	   	      	    		$.ligerDialog.confirm('数据删除后不可恢复,你确认要删除?', function(data) {
   	   	   	          				var delIds = "";
   	   	   	          				$(grid.selected).each(function(i, item){
   	   	   	          					delIds += item['tabCode']+",";
   	   	   	          				});
   	   	   	          				if(data){
   	   	   	          					$.ligerui.ligerAjax({
   	   	   	      							type: "POST",
   	   	   	      							async:  false,
   	   	   	      							url: "base_table!delete",
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
    	   	   			     }},
       		                { line: true },		                		                
       		                { text: '查看', click: show, icon:'view',expression:'!=1',disabled:true },
       		                { line: true },
       		                { text: '激活', click: active, icon: 'right' },
       		                { line: true }
                   		]
             	};


                form={
                        labelWidth: 50,
                        fields:[
                            { display: "表类型", name: "e.tabType",newline: false, type: "text" }, 
                            { display: "表名称", name: "e.tabCode",newline: false, type: "text" }
  					    ]
                   	}
             	var gridoption={
                        columns:[
                         { display:'表名称',name:'tabCode' },
                         { display:'表描述',name:'tabName' },
                         { display:'表类型',name:'tabType', render: function(data){
 		    				var text = data.tabType;
	    					switch(data.tabType){
	    						case 'S': text = "系统表"; break;
	    						case 'B': text = "业务表"; break;
	    					}
	    					return text;
	    				 }},
         				 { display:'版本',name:'tabVer'},
         				 { display:'激活状态',name:'isActiveable', render: function(data){
 		    				var text = data.isActiveable;
	    					switch(data.isActiveable){
	    						case 'N': text = "未激活"; break;
	    						case 'Y': text = "已激活"; break;
	    					}
	    					return text;
	    				 }}
         			],
                    url:'base_table!loadByEntity'
                }

             
              var glist=$.pap.createFormGridList({ toolbar:toolBarOption,form:form,grid:gridoption});
              grid=glist.getGrid();

        });



        var active=function(item){
	    	if( grid.selected.length > 0 ){

   				var delIds = "";
   				$(grid.selected).each(function(i,item){
   					delIds+=item['tabCode']+",";
   				});
   				
    			$.ligerui.ligerAjax({
					type:"POST",
					async: false,
					url:"base_table!activeTable",
					data:{ids:delIds},
					dataType:"text",
					beforeSend:function(){},
					success:function(data, textStatus){
						if(textStatus == 'success'){
							tip = $.ligerDialog.tip({ title: '提示信息', content: '激活成功！' });
							window.setTimeout(function(){ tip.close()} ,2000); 	
						}
						
					},
					error: function(XMLHttpRequest,textStatus){
						$.ligerDialog.error("错误代码执行");
					},
					complete: function(){ grid.loadData(true);}
				});
 	
	    	}else{
	    		$.ligerDialog.warn('你没有选择任何要激活的记录！')
	    	}

        }
        



        var addClick=function(item){
   			$.pap.addTabItem({ 
			  	 text: '表管理/新增',
			 	 url: 'base_table_add.jsp'   			 						
   			 })
        }

 	    var modifyClick=function(item){
	 	    
   	    	if(! jQuery.isEmptyObject(grid.selected) ){
   	    		if(grid.selected.length > 1){
   	    			$.ligerDialog.warn('请选择一条要修改的记录！');
   	    		}else if(grid.selected.length ==1){
   	    			if(grid.selected[0]['isActiveable']=='Y'){
   	    	    		$.ligerDialog.warn('记录已经激活，不能进行修改！');
   	   	    	    }else{
   	   	    		     var selectedRows = item.selectGrid.selected ; 
   	   	    	         $.pap.addTabItem({ text: '表管理/修改', url: 'base_table!loadUpdateById?id='+grid.selected[0]['tabCode'],param:selectedRows });

   	   	   	    	}
  	    		}
   	    	}else{
   	    		$.ligerDialog.warn('至少选择一条要修改的记录！');
   	    	}
    	}; 


    	var show=function(item){
   			var selectedRows = item.selectGrid.selected ; 
   			$.pap.addTabItem({ text: '表管理/查看', url: 'base_table!loadDetailById?id='+grid.selected[0]['tabCode'],param:selectedRows });

       }

      
   </script>
 
</head>

<body>
</body>
</html>
