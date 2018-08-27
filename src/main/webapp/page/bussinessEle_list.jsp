<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
     		            { text: '修改', click: modifyClick, icon: 'modify',expression:'!=1',disabled:true  },
     		            { line:true },
  	   	                { text: '删除', icon: 'delete', expression:'==0', disabled:true, click: function(){
  	   	            	  if( grid.selected.length > 0 ){
  	   	      	    		$.ligerDialog.confirm('数据删除后不可恢复,你确认要删除?', function(data) {
  	   	          				var delIds = "";
  	   	          				$(grid.selected).each(function(i, item){
  	   	          					delIds += item['ecode']+",";
  	   	          				});
  	   	          				if(data){
  	   	          					$.ligerui.ligerAjax({
  	   	      							type: "POST",
  	   	      							async:  false,
  	   	      							url: "bussiness_ele!delete",
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
    		            { line: true }
               		]
         	};


            var form={
                   labelWidth: 60,
                   fields:[
                       { display: "编码", name: "e.ecode", type: "text" },
                       { display: "名称", name: "e.ename", type: "text" },
                       { display: "值类型", name: "e.valueType", type: "text" }
			       ]
             }

  			  
	       
          	var gridoption={
                  columns:[
                        { display:'编码',name:'ecode' },
                        { display:'名称',name:'ename' },
                        { display:'类别',name:'classType'},
           				{ display:'数据类型',name:'dataType'},
           				{ display:'长度',name:'len'},
           				{ display:'小数位数',name:'decimalLen'},
           				{ display:'是否记录日志', name:'flagLog', render: function(data){
		    				var text = data.flagLog;
	    					switch(data.flagLog){
	    						case 'Y': text = "是"; break;
	    						case 'N': text = "否"; break;
	    					}
	    					return text;
	    				}},
           				{ display:'值类型',name:'valueType', render: function(data){
		    				var text = data.valueType;
	    					switch(data.valueType){
	    						case '0': text = "无"; break;
	    						case '1': text = "范围段"; break;
	    						case '2': text = "参考表"; break;
	    						case '3': text = "固定值"; break;
	    					}
	    					return text;
	    				}}
                  ],
                  url:'bussiness_ele!loadByPage'
            }

          
           var glist=$.pap.createFormGridList({ toolbar:toolBarOption,form:form,grid:gridoption});

           grid=glist.getGrid();


        });



        var addClick=function(item){
   			$.pap.addTabItem({ 
			  	 text: '业务元素/新增',
			 	 url: 'bussinessEle_add.jsp'   			 						
  			 })
       }

    	var show=function(item){

   			var selectedRows = item.selectGrid.selected ; 
   			$.pap.addTabItem({ text: '业务元素/查看', url: 'bussiness_ele!loadDetailById?id='+grid.selected[0]['ecode'],param:selectedRows });   		
       }


        var modifyClick=function(item){
   	    	if(! jQuery.isEmptyObject(grid.selected) ){
   	    		if(grid.selected.length > 1){
   	    			$.ligerDialog.warn('请选择一条要修改的记录！');
   	    		}else if(grid.selected.length ==1){
   	    		     var selectedRows = item.selectGrid.selected ; 
  	   	    	     $.pap.addTabItem({ tabid:'bussedit',text: '业务元素/修改', url: 'bussiness_ele!loadUpdateById?id='+grid.selected[0]['ecode'],param:selectedRows });
  	    		}
   	    	}else{
   	    		$.ligerDialog.warn('至少选择一条要修改的记录！');
   	    	}
    	}; 


    	function save(){
            return grid.getSelecteds();
        }

    	function getSelecteds(){
            return grid.getSelecteds();
        }

    	function getSelected(){
            return grid.getSelected();
        }
   
   </script>
 
</head>

<body>

</body>
</html>
