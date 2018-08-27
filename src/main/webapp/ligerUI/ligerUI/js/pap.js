(function($){
	
	$.pap = $.pap || {} ;
	
	$.extend($.pap,{
		/**
		 * 
		 * @param {Object} 打开Tab要传递的参数(Window,iframe)
		 */
		addTabItem: function(param){
			
			var b="true";
			$.ligerui.ligerAjax({
				url:"user_auth_value!check",
				dataType:"text",
				async:false,
				data:{"url":param.url},
				success:function(_data,textStatus){
					b=_data;
				},
				error: function(XMLHttpRequest,textStatus){
				    $.ligerDialog.warn('错误代码执行！');
				},
				complete: function(){}
			});
			if(b=="false"){
				$.ligerDialog.warn("权限不足");
				return;
			}else{
				if($.type(param) === 'object') {
		        	window.top.tab.addTabItem(param);
				}
			}
		    //if($.type(param) === 'object')
		    //   window.top.tab.addTabItem(param);
		},
		/**
		 * 
		 * @param {Object|String} tabid
		 */
		removeTabItem: function(tabid){
			if(tabid){
				if(typeof tabid == "object"){
				    window.top.tab.removeTabItem(tabid.id||tabid.name);
				}else {
					window.top.tab.removeTabItem(tabid);
				}
			}else{
				window.top.tab.removeTabItem(window.id||window.name);
			}
			
		},
		
		getGlobalWindow: function(win){
		    return win.top ;
		},
		
		/**
		 * 在使用对话框或者页签两种形式打开的页面中，在当前页面获得该页面的liger页面对象
		 * @param {Window} win
		 * @param {window||iframDom} frame
		 * @return {ligerObect} 
		 */
		getOpenPage: function(win , frame){
		       var argsLength = arguments.length ,results = null ;
		       if(argsLength == 1){
		    	   var superWindow = win.top ;
   			       var currentFrame = superWindow.ligerFrame[win.name||win.id] ;
   			       var returnPage = currentFrame.ligerPage ;
   			       if(returnPage){
   			    	   results =  returnPage ;   
   			       }
		    	   
		       }else if(argsLength >= 2){
		    	    var superWindow = win.top ;
   			        var currentFrame = superWindow.ligerFrame[frame.name||frame.id] ;
   			        var returnPage = currentFrame.ligerPage ;
   			        if(returnPage){
   			    	   results =  returnPage ;   
   			        }
		       }
		       return results;
		},
		/**
		 * 抛出异常提示错误
		 * @param {String} message
		 * @exception {String} 
		 */
		throwError: function(message){
		   alert(message);
 	       throw new Error(message);
		},
		
		/**
		 * 测试使用的 工具条按钮事件
		 * @param {Object} item
		 */
		toolBarClick: function (item){
		    alert(item.text);
		},
		
		/**
		 * 创建工具条：如果传递一个参数  工具条的html将会添加到body后
		 * 			如果传递两个参数 第二个参数可以是DOM等 
		 *                      工具条的html 将会添加到该dom元素里边.
		 * @param {Object} toolBarOptions
		 * @return {TypeName} 
		 * @exception {TypeName} 
		 */
		getToolbar: function(toolBarOptions,dom){
			var argsLength = arguments.length, toolBarOption = null ;
			if( arguments[0]){
				/*
				 toolBarOption = { items: [
					                { text: '增加', click: $.pap.toolBarClick},
					                { line:true },
					                { text: '修改', click: $.pap.toolBarClick },
					                { line:true },
					                { text: '删除', click: $.pap.toolBarClick }
		            		    ]
		     			  };
		        */
				toolBarOption = arguments[0];
		    	if(jQuery.isArray(toolBarOption)){
		    		toolBarOption = {items:toolBarOption};
		    	}else if(jQuery.type(toolBarOption) == 'object'){
		    		 // 如果配置项 为空对象{} ,
		    		 if(!jQuery.isArray(toolBarOption.items)){
		    			 toolBarOption['items'] = [] ;
		    		 }
		    	}
		    	if(!toolBarOption['id']){
		    			 toolBarOption['id'] = $.ligerui.getId('toolbar');
		    	}
		    	var targetTag = $("#"+toolBarOption['id']);
		    	if(targetTag.length != 1){
		    		targetTag = $('<div id="'+toolBarOption['id']+'"></div>');
		    		//$('body').append( targetTag );
		    		if(arguments[1]){
			    		targetTag.appendTo($(dom)) ;//$(dom).append( targetTag );
		    		}else{
		    			$('body').append( targetTag );
		    		}
		    	}
		    	return  targetTag.ligerToolBar(toolBarOption);
			}
			return   null ;//$("#"+arguments[0]).ligerToolBar(toolBarOption);
		 },
		 
		/**
		 * @param idSelect 
		 *        id选择器名称
		 * @param toolBarOptions
		 *  创建工具条:三种可选类型
		 *   第一： 实参为 null || undefined
		 *   第二： 数组集合： []
		 *   第三： {items : [] } 
		 **/
		 createToolbar: function(idSelect,toolBarOptions){
			var argsLength = arguments.length, toolBarOption = null ;
			if( argsLength == 0 ){
				 alert("必须指定id选择器名称");
		    	 throw new Error("必须指定id选择器名称");
			}else if(argsLength == 1){
				 toolBarOption = { items: [
					                { text: '增加', click: $.pap.toolBarClick },
					                { line:true },
					                { text: '修改', click: $.pap.toolBarClick },
					                { line:true },
					                { text: '删除', click: $.pap.toolBarClick }
		            		    ]
		     			  };
			}else{
				toolBarOption = arguments[1];
		    	if(jQuery.isArray(toolBarOption)){
		    		toolBarOption = {items:toolBarOption};
		    	} else if(jQuery.type(toolBarOption) == 'object'){
		    		 // 如果配置项 为空对象{} ,
		    		 if(!jQuery.isArray(toolBarOption.items)){
		        		 alert("配置项items 出现错误");
		        		 throw new Error("配置项items 出现错误");
		    		 }
		    		 
		    	 }
			}
			return $("#"+arguments[0]).ligerToolBar(toolBarOption);
		 },
		 // end  createToolbar
		 
		 /**
		  * 创建ligerForm 对象. 参数介绍雷同 getToolbar方法
		  * @param {Object} formOptions
		  * @param {HTMLDom} dom
		  * @return {TypeName} 
		  * @exception {TypeName} 
		  */
		 getForm: function(formOptions,dom){
			 if(jQuery.fn.ligerForm){
				 
				 var argsLength = arguments.length, formOption = {} ;
		    	 if(arguments[0] ){
		    		 var baseOption = { inputWidth: 170, 
						                labelWidth: 110, 
						                space: 40,
						                buttons:[
						                    {text:'查询',click:function(item){
						                            alert(item.options.text); 
						                    }},
						                    {text:'清空',click:function(item){
						               				   //$("#"+idSelect).resetForm(); 
						                    }}
						                 ]},
						  overrideOptions =null ;
		    		 //  配置项formOptions 为数组  >> if
		    		 //  配置项formOptions 为对象   >> else if(jQuery.type(formOptions) == 'object')
		    		 if(jQuery.isArray(formOptions)){
		    			  overrideOptions = {fields:formOptions};
		    		 }else if(jQuery.type(formOptions) === 'object'){
		    			 /*
		    			 if(!jQuery.isArray(formOptions.fields)){
			        		 alert("配置项fields 出现错误");
			        		 throw new Error("配置项fields 出现错误");
		        		 }*/
		    			 overrideOptions = formOptions ;
		    		 }
		    		 $.extend(formOption, baseOption, overrideOptions );
		    		 if(!formOption['id']){
		    			 formOption['id'] = $.ligerui.getId('form');
			    	 }
			    	 var targetTag = $("#"+formOption['id']);
			    	 if(targetTag.length != 1){
			    		targetTag = $('<form id="'+formOption['id']+'"></from>');
			    		//$('body').append( targetTag );
			    		if(arguments[1]){
				    		targetTag.appendTo($(dom)) ;//$(dom).append( targetTag );
			    		}else{
			    			$('body').append( targetTag );
			    		}
			    	 }
			    	 return  targetTag.ligerForm(formOption);
		    	 }
		    	 
		    	 return null;
		    	 
			 }else{
				  alert("你没有引入ligerForm.js文件");
		          throw new Error("你没有引入ligerForm.js文件");
			 }
		 },
		 
		/**
		 * 
		 * @param {String} idSelect
		 *        <from id='idSelect'></form>form的id属性值
		 * @param {String} action
		 *        如果是saveform配置项需要的提交地址
		 * @param {Object} formOptions
		 *        自动化生成form的 配置项，参照ligerUI API 
		 * @return {ligerObject}
		 *         ligerForm 对象
		 * @exception {Error} 自定义的抛出异常
		 */
		 createForm: function(idSelect,action,formOptions){
			 if(jQuery.fn.ligerForm){
				 var argsLength = arguments.length, formOption = {} ;
		    	 if(arguments.length < 3){
		    		 alert("createForm的参数长度必须大于等于3");
		        	 throw new Error("createForm的参数长度必须大于等于3");
		    	 }else{
		    		 var baseOption = { inputWidth: 170, 
						                labelWidth: 110, 
						                space: 40,
						                buttons:[
						                    {text:'查询',click:function(item){
						                            alert(item.options.text); 
						                    }},
						                    {text:'清空',click:function(item){
						               				   $("#"+idSelect).resetForm(); 
						                    }}
						                 ]},
						  overrideOptions =null ;
		    		 //  第三个配置项formOptions 为数组  >> if
		    		 //  第三个配置项formOptions 为对象   >> else if(jQuery.type(formOptions) == 'object')
		    		 if(jQuery.isArray(formOptions)){
		    			  
		    			  overrideOptions = {fields:formOptions};
		    			 
		    		 }else if(jQuery.type(formOptions) === 'object' /*&& !jQuery.isPlainObject(formOptions) */){
		    			 
		    			 if(!jQuery.isArray(formOptions.fields)){
			        		 alert("配置项fields 出现错误");
			        		 throw new Error("配置项fields 出现错误");
		        		 }
		    			 overrideOptions = formOptions ;
		    			 
		    		 }else{
		    			 
		    			 alert("createForm的第三个配置项必须为数组or对象");
		        	 	 throw new Error("createForm的第三个配置项必须为数组or对象");
		    		 }
		    		 
		    		 $.extend(formOption, baseOption, overrideOptions );
		    	 }
		    	 return $("#"+arguments[0]).ligerForm( formOption );
		    	 
			 }else{
				  alert("你没有引入ligerForm.js文件");
		          throw new Error("你没有引入ligerForm.js文件");
			 }
		 },
		 // end of createForm 
		
	   /**
		  * 创建ligerGrid对象 参数雷同 getForm、getToolbar
		  * @param {Object} gridOptions
		  * @param {Object} dom
		  * @return {TypeName} 
		  */
		getGrid: function(gridOptions,dom){
			 if(jQuery.fn.ligerGrid){
				 var argsLength = arguments.length, gridOption = {} ;
		    	 if(arguments[0]){
		    		 var baseOption = {
		    			      	title:'数据表格',
		    			        headerImg: '../ligerUI/ligerUI/skins/icons/grid.gif',
		    			        delayLoad : false,
		    			        selectRowButtonOnly:true, 
				                pageSizeOptions:[10,20,30],
				                pageParmName:'pageBean.pageNo', //当前页数
				                pagesizeParmName:'pageBean.pageSize',//每页条数
				                sortnameParmName:'pageBean.sortName',//排序字段
				                sortorderParmName:'pageBean.sortorder',//排序方向
				                enabledSort:false,
				                usePager: true,        //是否分页
				                alternatingRow: true, //奇数行 和偶数行效果
				                width: '99%',
				                height:'99%', 
				                checkbox: true,
				                rownumbers:true
				                //editorable:true,
				               },
						  overrideOptions = null ;
		    		 //  配置项formOptions 为数组  >> if
		    		 //  配置项formOptions 为对象   >> else if(jQuery.type(formOptions) == 'object')
		    		 if(jQuery.isArray(gridOptions)){
		    			  overrideOptions = {columns:gridOptions};
		    		 }else if(jQuery.type(gridOptions) === 'object'){
		    			 overrideOptions = gridOptions ;
		    		 }
		    		 $.extend(gridOption, baseOption, overrideOptions );
		    		 
		    		 if(!gridOption['id']){
		    			 gridOption['id'] = $.ligerui.getId('grid');
			    	 }
			    	 var targetTag = $("#"+gridOption['id']);
			    	 if(targetTag.length != 1){
			    		targetTag = $('<div id="'+gridOption['id']+'"></div>');
			    		// $('body').append( targetTag );  //old 
			    		if(arguments[1]){
			    			targetTag.appendTo($(dom)) ;//$(dom).append( targetTag );
			    		}else{
			    			$('body').append( targetTag );
			    		}
			    	 }
			    	 return  targetTag.ligerGrid(gridOption);
		    	 }
		    	 return null;
		    	 
			 }else{
				  $.pap.throwError("你没有引入ligerGrid.js文件");
			 }
		 },
		 
		/**
		 * @param {String} idSelect
		 *        <div id='idSelect'></div>
		 * @param {Object} gridOptions
		 *        ligerGrid 的表格配置项
		 * @return {ligerObject} 返回的ligerGrid 对象
		 * @exception {Error}  自定义异常   
		 */
		 createGrid: function(idSelect,gridOptions){
			 
			 if(jQuery.fn.ligerGrid){
				 var argsLength = arguments.length, gridOption = {} ;
		    	 if(arguments.length != 2){
		    		 alert("createGrid的参数长度必须为2");
		        	 throw new Error("createGrid的参数长度必须为2");
		    	 }else{
		    		 var baseOption = {
		    			        title:'数据表格',
		    			        headerImg: '../ligerUI/ligerUI/skins/icons/grid.gif',
		    			        delayLoad : false,
		    			        selectRowButtonOnly:true, 
				                pageSizeOptions:[10,20,30],
				                pageParmName:'pageBean.pageNo', //当前页数
				                pagesizeParmName:'pageBean.pageSize',//每页条数
				                sortnameParmName:'pageBean.sortName',//排序字段
				                sortorderParmName:'pageBean.sortorder',//排序方向
				                enabledSort:false,
				                usePager: true,        //是否分页
				                alternatingRow: true, //奇数行 和偶数行效果
				                width: '99%',
				                height:'99%', 
				                checkbox: true,
				                rownumbers:true
				                //editorable:true,
				               },
						  overrideOptions = null ;
		    		 //  第三个配置项formOptions 为数组  >> if
		    		 //  第三个配置项formOptions 为对象   >> else if(jQuery.type(formOptions) == 'object')
		    		 if(jQuery.isArray(gridOptions)){
		    			  
		    			  overrideOptions = {columns:gridOptions};
		    			 
		    		 }else if(jQuery.type(gridOptions) === 'object' /*&& !jQuery.isPlainObject(gridOptions) */){
		    			 
		    			 if(!jQuery.isArray(gridOptions.columns)){
			        		 alert("配置项columns 出现错误");
			        		 throw new Error("配置项columns 出现错误");
		        		 }
		    			 overrideOptions = gridOptions ;
		    			 
		    		 }else{
		    			  $.pap.throwError( "createGrid的第2个配置项必须为数组or对象" )
		    		 }
		    		 
		    		 $.extend(gridOption, baseOption, overrideOptions );
		    	 }
		    	 return $("#"+arguments[0]).ligerGrid( gridOption );
		    	 
			 }else{
				  $.pap.throwError("你没有引入ligerGrid.js文件");
			 }
		 },
		 
		 /**
		  * 功能： 创建同步树
		  * @param {String} isSelect 
		  *       id选择器
		  * @param {Object} treeOptions
		  *       创建树的配置项参数
		  * @return {ligerTree} ligerTree对象 
		  */
		 createAsyncTree: function(isSelect,treeOptions){
			 if(jQuery.fn.ligerTree){
				 var argsLength = arguments.length, treeOption = {} ;
		    	 if(arguments.length != 2){
		    		 alert("createTree的参数长度必须为2");
		        	 throw new Error("createTree的参数长度必须为2");
		    	 }else{
		    		 var baseOption = {
		    			          async: true,
		    			          nodeWidth: 100,
		    			          btnClickToToggleOnly: true,
		    			          checkbox: false
				               },
						  overrideOptions = null ;
		    		 if(jQuery.type(treeOptions) === 'object' ){
		    			 overrideOptions = treeOptions ;
		    		 }
		    		 
		    		 $.extend(treeOption, baseOption, overrideOptions );
		    	 }
		    	 return $("#"+arguments[0]).ligerTree( treeOption );
		    	 
			 }else{
				  $.pap.throwError("你没有引入ligerTree.js文件");
			 }
			 
		 },
		 
		 /**
		  *  功能： 创建异步树
		  * @param {Object} idSelect
		  * @param {Object} treeOptions
		  * @return {TypeName} 
		  */
		 createTree: function(idSelect,treeOptions){
			  var treeOption = {initExpand:false,async:false};
			  return $.pap.createAsyncTree(idSelect ,$.extend({},treeOption,treeOptions));
		 },
		 /**
		  * 功能：模板创建时的基础类
		  * @return {DOM} 
		  */
		 createBaseTag: function(){
			  $('body').html(""); //将body的元素清空
			  $('body').css({margin:'0px',padding:'0px',border:'0px'});
			  var tag = $("<div></div>");
			  $('body').append( tag );
			  return tag ;
		 },
		 /**
		  * 功能：模板创建时的基础类
		  * @return {DOM} 
		  */
		 createTemplateTag: function(){
			  $('body').html(""); //将body的元素清空
			  $('body').css({margin:'0px',padding:'0px',border:'0px'});
			  //var tag = $("<div><div class='toolbar-pap' style='height:23px;'></div><div class='form-pap'></div><div class='grid-pap'></div></div>");
			  var tag = $("<div><div class='toolbar-pap'></div><div class='form-pap'></div><div class='grid-pap'></div></div>");
			  var loading = "<div id='loading' class='l-sumbit-loading' style='display: none;'></div>";
			  $('body').append(loading);
			  $('body').append(tag);
			  return tag ;
		 },
		 
		 /**
		  * 功能：报表模板基础类
		  */
		 createReportTempTag: function() {
		 	  $('body').html(""); //将body的元素清空
			  $('body').css({margin:'0px',padding:'0px',border:'0px'});
			  var tag = $("<div><div class='toolbar-pap'></div><div class='form-pap'></div><div class='report-pap'></div></div>");
			  $('body').append( tag );
			  return tag ;
		 },
		 
		 /**
		  * update1 2013-7-22 17:21:31
		  * 使用模板组件创建： 
		  *    只需要添加你的 toolbar、form 、 grid 配置项 自动为你创建页面元素
		  * @param {Object} options
		  * @return {TypeName} 
		  */
		 createTemplate: function(options){
			 if($.type(options) == 'object'){
				 var tag = this.createTemplateTag();
				 return tag.ligerTemplate(options);
			 }
			 return null ;
		 },
		 /**
		  * 创建可编辑的 上边Form  下边可编辑Grid 页面模板  查看页面
		  * @param {String} options
		  */
		 createEFormGridShow: function(options){
			  if($.type(options) == 'object'){
				 var tag = this.createTemplateTag() ;
				 return tag.ligerXEFormGridShow(options);
			 }
			 return null ;
		 },
		 
		 /**
		  * 创建可编辑的 上边Form  下边可编辑Grid 页面模板  编辑页面
		  * @param {String} options
		  */
		 createEFormGridEdit: function(options){
			  if($.type(options) == 'object'){
				 var tag = this.createTemplateTag() ;
				 return tag.ligerXEFormGridEdit(options);
			 }
			 return null ;
		 },
		 /**
		  * 创建可编辑的 上边Form  下边可编辑Grid 页面模板  保存页面
		  * @param {Object} options
		  * 
		  */
		 createEFormGridSave: function(options){
			  if($.type(options) == 'object'){
				 var tag = this.createTemplateTag() ;
				 return tag.ligerXEFormGridSave(options);
			 }
			 return null ;
		 },
		 /**
		  * 上边Form 下边Grid 页面模板
		  * 
		  * @param {Object} options
		  * @return {TypeName} 
		  */
		 createFormGridList: function(options){
			 if($.type(options) == 'object'){
				 var tag = this.createTemplateTag();
				 return tag.ligerXFormGridList(options);
			 }
			 return null ;
		 },
		 /**
		  * 单一Grid 页面模板
		  * 
		  * @param {Object} options
		  * @return {TypeName} 
		  */
		 createGridList: function(options){
			 if($.type(options) == 'object'){
				 var tag = this.createTemplateTag();
				 return tag.ligerXGridList(options);
			 }
			 return null ;
		 },
		 /**
		  *  单一form编辑页面模板
		  *      注意form配置项增加 一个url属性配置项项
		  * @param {Object} options
		  * @return {TypeName} 
		  */
		 createSFormModify:function(options){
			  if($.type(options) == 'object'){
				 var tag = this.createTemplateTag() ;
				 return tag.ligerXSFormModify(options);
			 }
			 return null;
		 },
		 
		 /**
		  * 单一form创建页面模板
		  *     注意form配置项增加 一个url属性配置项
		  */
		 createSFormCreate:function(options){
			  if($.type(options) == 'object'){
				 var tag = this.createTemplateTag() ;
				 return tag.ligerXSFormCreate(options);
			 }
			 return null ;
		 },
		 createSFormShow: function(options){
			 if($.type(options) == 'object'){
				 var tag = this.createTemplateTag() ;
				 return tag.ligerXSFormShow(options);
			 }
			 return null ;
		 },
		 
		  /**
		  * 单一EditorGrid 页面模板
		  * 
		  * @param {Object} options
		  * @return {TypeName} 
		  */
		 createEGridEdit: function(options){
			 if($.type(options) == 'object'){
				 var tag = this.createTemplateTag();
				 return tag.ligerXEGridEdit(options);
			 }
			 return null ;
		 },
		 /**
		  * 带查询条件的报表模板
		  * 
		  * @param {Object} options
		  * @return {TypeName} 
		  */
		 createXReportByCon: function(options){
			 if($.type(options) == 'object'){
				 var tag = this.createReportTempTag();
				 return tag.ligerXReportByCon(options);
			 }
			 return null ;
		 },
		 
		  /**
		  * 无查询条件的报表模板
		  * 
		  * @param {Object} options
		  * @return {TypeName} 
		  */
		 createXReport: function(options){
			 if($.type(options) == 'object'){
				 var tag = this.createReportTempTag();
				 return tag.ligerXReport(options);
			 }
			 return null ;
		 }
   });
})(jQuery)