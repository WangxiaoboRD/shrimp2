<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    
    <title>查看</title>
	<link href="../ligerUI/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="../ligerUI/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />	
    <script src="../ligerUI/jquery/jquery-1.6.4.js" type="text/javascript"></script>
	<script src="../ligerUI/jquery-validation/jquery.form.js" type="text/javascript"></script> 
	<script src="../ligerUI/jquery-validation/jquery.validate.js" type="text/javascript"></script> 
    <script src="../ligerUI/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
    <script src="../ligerUI/jquery-validation/messages_cn.js" type="text/javascript"></script> 
    <script type="text/javascript" src="../ligerUI/ligerUI/js/core/base.js"></script>
    <script type="text/javascript" src="../ligerUI/ligerUI/js/plugins/ligerDialog.js"></script>
    <script type="text/javascript" src="../ligerUI/ligerUI/js/plugins/XDialog.js"></script>
    <script type="text/javascript" src="../ligerUI/ligerUI/js/plugins/ligerGrid2.js"></script>    
	<script type="text/javascript" src="../ligerUI/original/ligerTree.js"></script>
    <script type="text/javascript" src="../ligerUI/ligerUI/js/plugins/XGrid.js"></script>
    <script type="text/javascript" src="../ligerUI/ligerUI/js/pap.js"></script>
	<script type="text/javascript" src="../ligerUI/json2.js"></script>

    <script type="text/javascript">

          var manager = null;
          var tree=null;
		  $(function(){

		    	var url = document.location.href;  
		    	var arrStr = url.substring(url.indexOf("?")+1).split("&");  
		        var eid;
		    	for(var i =0;i<arrStr.length;i++)  
			    {  
						var loc = arrStr[i].indexOf("=");  
					  
						if(loc!=-1)  
						{  
						   var param=arrStr[i].split("=");
		                   eid=param[1];
						}  
			    }

		    // var currentPage = $.pap.getOpenPage(window);			    
			// console.dir(currentPage.ligerPageParam['myParam'])			    
	          tree=$("#tree1").ligerTree(
	             {
       				   url:'bussiness_object_detail!loadRoot.action?e.property='+eid,
       				   async:true,
                       idFieldName :'id',
                       textFieldName:'property',
                       nodeWidth : 200,
                       attribute: ['id','type'],
       				   checkbox:false,
                       onBeforeExpand: onBeforeExpand,
                       onClick: clickNote
	                        
	           });
	         	manager = $("#tree1").ligerGetTreeManager();
	                           
	  	    });

	        function onBeforeExpand(note)
	        {
	        
	            if (note.data.children && note.data.children.length == 0)
	            {
	                var sf=note.data;              
	                //这里模拟一个加载节点的方法，append方法也用loadData(target,url)代替
	                //manager.append(note.target, [{id:"2" , text: "111" }]);	                
	                var root=tree.getDataByID(0);
	                var names=root.property;
	                var type=note.data.type;
					manager.loadData(note.target,"bussiness_object_detail!loadProperty.action",{
					    'e.pid':sf.id,
					    'e.bussinessObject.bussCode':root.property,
					    'e.bocode':type
					})
	            }
	        }
	        
	        function clickNote(note){
	           var sf=note;
	           var ss=note.data.type;
	           
	           if(note.data.type==''&&note.data.id!=0){
	        	   var parent=tree.getParent(note.target);
   	    			$.ligerDialog.open({
  							title:'查看',
  							width:600,
  							height:300, 
  							url: 'base_table_column!loadDetail?e.fdcode='+note.data.property+'&e.baseTable.tabCode='+parent.type, 				  
  							buttons: [ 
  		        			]
  		        		});
		      }
	        }
	 
 	 
    </script>
    <style type="text/css">
         body{ font-size:12px;}
        .l-table-edit {}
        .l-table-edit-td{ padding:4px;}
    </style>
</head>
    <div  style="width:400px; height:500px; left: ">
    <ul id="tree1" >
    </ul> 
    </div>
        <div style="display:none ">    
    </div>

</html>
