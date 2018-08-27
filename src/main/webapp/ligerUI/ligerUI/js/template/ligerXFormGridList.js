/**
 * jQuery ligerUI 1.2.0
 * 
 * http://ligerui.com
 *  
 * Author daomi 2013 [ gd_star@163.com ] 
 * 
 */
(function($) {
	$.fn.ligerXFormGridList = function(options) {
		return $.ligerui.run.call(this, "ligerXFormGridList", arguments);
	};

	$.fn.ligerGetXFormGridListManager = function() {
		return $.ligerui.run.call(this, "ligerGetXFormGridListManager", arguments);
	};

	$.ligerDefaults.XFormGridList = {
		toolbar:{},
		from:{},
		grid:{}
	};

	$.ligerMethos.XFormGridList = $.ligerMethos.XFormGridList || {};

	$.ligerui.controls.XFormGridList = function(element, options) {
		$.ligerui.controls.XFormGridList.base.constructor.call(this, element, options);
	};
	
	$.ligerui.controls.XFormGridList.ligerExtend($.ligerui.controls.Template,{
			__getType : function() {
				return 'XFormGridList';
			},
			__idPrev : function() {
				return 'XFormGridList';
			},
			_extendMethods : function() {
				return $.ligerMethos.XFormGridList;
			},
			_preRender : function() {
				var g = this, p = this.options;
				// 表格会根据分组按钮收缩/展开自动调整高度
			    $(".togglebtn").live('click', function (){
		    		g.getGrid().setHeight(g.getGrid().options.height);
		        });
			},
			_init: function() {
				 $.ligerui.controls.XFormGridList.base._init.call(this);
			},
			_render: function() {
				$.ligerui.controls.XFormGridList.base._render.call(this);
			},
		    createForm: function(formOptions,dom){
				 var g = this ,p = g.options ;
				 if(jQuery.fn.ligerForm){
					 var argsLength = arguments.length, formOption = {} ;
			    	 if(arguments[0] ){
			    		 var baseOption = { inputWidth: 130, 
				                labelWidth: 70, 
				                space:20,
				                buttons:[
				                    { text:'查询', icon: '../ligerUI/ligerUI/skins/icons/search2.gif', click:function(item){
				                    	//var arra = item.options.jqueryform.formToArray(); //按钮添加了newline配置后此种方法不可用
				                    	var arra = $('form').formToArray();
				                    	if(g.grid){
				                    		g.grid.setParms(arra);
				                    		g.grid.options.newPage=1;
						           		    g.grid.loadData(true);
				                    	}
			 							
				                    }}/*, 2014-04-14给注释掉
				                    {text:'清空',click:function(item){
				                    	// item.options.jqueryform.resetForm(); old
				                    	//var form = item.options.jqueryform.ligerForm();//按钮添加了newline配置后此种方法不可用
				                    	var form = $('form').ligerForm();
				                    	// 循环清空表单的元素
				                    	$.each(form.element, function(index) {
				                    		form.element[index].value = "";
				                    	});
				                    	
				                    	// 清空组合框选中样式
				                    	$.each(form.options.fields, function(index) {
				                    		var field = form.options.fields[index];
				                    		if (field['type'] == "select") {
				                    			var selectCombo = $("input[id='" + field['name'] + "']");
				                    			if (selectCombo.val() == "") {
				                    				$(".l-selected", this.selectCombo).removeClass("l-selected");
				                    			}
				                    		}
				                    	});
				                    }}*/
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
					    		//targetTag.appendTo(dom) ;//
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
		createGrid: function(gridOptions, dom){
			 var g = this,p = g.options ;
			 if(jQuery.fn.ligerGrid){
				 var argsLength = arguments.length, gridOption = {} ;
		    	 if(arguments[0]){
		    		 var baseOption = {
		    			        title:'数据表格',
		    			        headerImg: '../ligerUI/ligerUI/skins/icons/grid.gif',
		    			        delayLoad : true,
		    			        selectRowButtonOnly:true, 
		    			        pageSize: 20,  
				                pageSizeOptions:[20,35,50],
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
			    	   */
			    	 if (!gridOption.onCheckRow) {
			    	 	gridOption.onCheckRow = function(uncheck, data, dataID, row){
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
			    	 	}
			    	 }
			    	 if (!gridOption.onCheckAllRow) {
				    	 gridOption.onCheckAllRow = function(uncheck, data, dataID, row){
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
				    	 }
			    	 }
			    	 return  targetTag.ligerGrid(gridOption);
		    	 }
		    	 return null;
			 }else{
				  $.pap.throwError("你没有引入ligerGrid.js文件");
			 }
		 }
		 
   });

})(jQuery);