/**
 * 
 */
(function($) {
	$.fn.ligerTemplate = function(options) {
		return $.ligerui.run.call(this, "ligerTemplate", arguments);
	};

	$.fn.ligerGetTemplateManager = function() {
		return $.ligerui.run.call(this, "ligerGetTemplateManager", arguments);
	};

	$.ligerDefaults.Template = {
		toolbar:{},
		from:{},
		grid:{}
	};

	$.ligerMethos.Template = $.ligerMethos.Template || {};

	$.ligerui.controls.Template = function(element, options) {
		$.ligerui.controls.Template.base.constructor.call(this, element, options);
	};
	
	$.ligerui.controls.Template.ligerExtend($.ligerui.core.UIComponent,{
			__getType : function() {
				return 'Template';
			},
			__idPrev : function() {
				return 'Template';
			},
			_extendMethods : function() {
				return $.ligerMethos.Template;
			},
			_init : function() {
				 $.ligerui.controls.Template.base._init.call(this);
			},
			
			_render : function() {
				var g = this, p = this.options;
				g.template = $(this.element);
				g.template.toolbar = $("div:first",g.template) ;
				g.template.form = $("div:eq(1)",g.template) ;
				g.template.grid = $("div:last",g.template) ;
				
				if(!$.isEmptyObject(p.form)){
					g.form = g.createForm(p.form, g.template.form);
				}else{
					g.template.form.remove(); // 删除该form
				}
				if(!$.isEmptyObject(p.grid)){
					g.grid = g.createGrid(p.grid, g.template.grid);
				}else{
					g.template.grid.remove(); // 删除该form
				}
				
				// 工具条是不能缺少的。
				if(!$.isEmptyObject(p.toolbar)){
					g.toolbar = g.createToolbar(p.toolbar, g.template.toolbar);
				}
			},
			getToolbar: function(){
				return this.toolbar ; 
			},
			/**
			 * 创建工具条
			 * @param {Object} toolBarOptions
			 * @param {Object} dom
			 * @return {TypeName} 
			 */
			createToolbar: function(toolBarOptions,dom){
				var g = this,p = g.options;
				var argsLength = arguments.length, toolBarOption = null ;
				if( arguments[0]){
					toolBarOption = arguments[0];
			    	if(jQuery.isArray(toolBarOption)){
			    		toolBarOption = {items:toolBarOption};
			    	}else if(jQuery.type(toolBarOption) == 'object'){
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
			    	// TODO  TODO
			    	if(toolBarOption['items'].length > 0){
			    		$.each(toolBarOption['items'],function(i,item){
			    			if(g.grid){
			    				item['selectGrid'] = g.grid ;
			    			}
			    			if(g.form){
			    				item['currentForm'] = g.form ;
			    			}
			    		});
			    	}
			    	return  targetTag.ligerToolBar(toolBarOption);
				}
				return   null;
		   },
		   
		   getForm:function(){
			  return  this.form ;  
		   },
		   
		   createForm: function(formOptions,dom){
			 var g = this ,p = this.options ;
			 if(jQuery.fn.ligerForm){
				 var argsLength = arguments.length, formOption = {} ;
		    	 if(arguments[0] ){
		    		 var baseOption = { inputWidth: 170, 
						                labelWidth: 110, 
						                space: 40,
						                buttons:[
						                	/*
						                    {text:'查询',click:function(item){
						                           //item.options.ligerform 
						                    	var arra = item.options.jqueryform.formToArray() ;
						                    	if(g.grid){
						                    		g.grid.setParms(arra);
								           		    g.grid.loadData(true);
						                    	}
					 							
						                    }},
						                    {text:'清空',click:function(item){
						               				   item.options.jqueryform.resetForm(); 
						                    }}
						                    */
						                 ]},
						  overrideOptions =null ;
		    		 //  配置项formOptions 为数组  >> if
		    		 //  配置项formOptions 为对象   >> else if(jQuery.type(formOptions) == 'object')
		    		 if(jQuery.isArray(formOptions)){
		    			  overrideOptions = {fields:formOptions};
		    		 }else if(jQuery.type(formOptions) === 'object'){
		    			 overrideOptions = formOptions ;
		    		 }
		    		 $.extend(formOption, baseOption, overrideOptions );
		    		 if(!formOption['id']){
		    			 formOption['id'] = $.ligerui.getId('form');
			    	 }
			    	 var targetTag = $("#"+formOption['id']);
			    	 if(targetTag.length != 1){
			    		targetTag = $('<form id="'+formOption['id']+'"></form>');
			    		if(arguments[1]){
				    		//targetTag.appendTo($(dom)) ;
			    			$(dom).append( targetTag );
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
		 getGrid: function(){
			 return this.grid ; 
		 },
		 /**
		  * @param {Object} gridOptions
		  * @param {Object} dom
		  * @return {TypeName} 
		  */
		 createGrid: function(gridOptions, dom){
			 var g = this,p = g.options ;
			 if(jQuery.fn.ligerGrid){
				 var argsLength = arguments.length, gridOption = {} ;
		    	 if(arguments[0]){
		    		 var baseOption = {
		    			        title:'我的表格',
		    			        delayLoad : true,
		    			        selectRowButtonOnly:true, 
				                pageSizeOptions:[10,20,30],
				                pageParmName:'pageBean.pageNo', //当前页数
				                pagesizeParmName:'pageBean.pageSize',//每页条数
				                sortnameParmName:'pageBean.sortName',//排序字段
				                sortorderParmName:'pageBean.sortorder',//排序方向
				                enabledSort:false,
				                usePager: true,        //是否分页
				                alternatingRow: true, //奇数行 和偶数行效果
				                width: '99.8%',
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
			    	 /**
			    	   * 此处添加 选中行事件 四个  
			    	   * 	分别为： onBeforeCheckRow  onCheckRow  
			    	   * 		  onBeforeCheckAllRow  onCheckAllRow
			    	   *    
			    	 gridOption.onCheckRow = gridOption.onCheckAllRow = function(uncheck, data, dataID, row){
			    		     var selectedCount = null;
				    		 if(g.grid){
				    		     selectedCount =  g.grid.selected.length ;
				    		 }
				    		 if(g.toolbar){
				    			 var toolbar_buttons = g.toolbar.toolButtons ;
				    			 $.each(toolbar_buttons,function(i,item){
				    				 item.trigger('selectionchange',[item,selectedCount]);
				    			 });
				    		 }
			    	 }*/
			    	 return  targetTag.ligerGrid(gridOption);
		    	 }
		    	 return null;
			 }else{
				  $.pap.throwError("你没有引入ligerGrid.js文件");
			 }
		 },
		 /***
		  * 为提交方法追加其他数据，比如删除的记录 
		  * zp 添加
		  */
		 appendOtherData:function(){			 			 
		 }
		 
		 
		 
   });
	
})(jQuery);