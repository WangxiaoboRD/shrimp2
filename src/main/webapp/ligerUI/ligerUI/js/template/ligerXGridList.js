/**
 * jQuery ligerUI 1.2.0
 * 
 * http://ligerui.com
 *  
 * Author daomi 2013 [ gd_star@163.com ] 
 * 
 */
(function($) {
	$.fn.ligerXGridList = function(options) {
		return $.ligerui.run.call(this, "ligerXGridList", arguments);
	};

	$.fn.ligerGetXGridListManager = function() {
		return $.ligerui.run.call(this, "ligerGetXGridListManager", arguments);
	};

	$.ligerDefaults.XGridList = {
		toolbar:{},
		grid:{}
	};

	$.ligerMethos.XGridList = $.ligerMethos.XGridList || {};

	$.ligerui.controls.XGridList = function(element, options) {
		$.ligerui.controls.XGridList.base.constructor.call(this, element, options);
	};
	
	$.ligerui.controls.XGridList.ligerExtend($.ligerui.controls.Template,{
			__getType : function() {
				return 'XGridList';
			},
			__idPrev : function() {
				return 'XGridList';
			},
			_extendMethods : function() {
				return $.ligerMethos.XGridList;
			},
			_init: function() {
				 $.ligerui.controls.XGridList.base._init.call(this);
			},
			_render: function() {
				$.ligerui.controls.XGridList.base._render.call(this);
			},
			_preRender : function() {
				var g = this, p = this.options;
				// 若工具条为空，则删除工具条样式
				 if($.isEmptyObject(this.toolbar)){
				 	$('body').find('div div:first').removeClass('toolbar-pap');
				 }
			},
			createGrid: function(gridOptions, dom){
				 var g = this,p = g.options ;
				 if(jQuery.fn.ligerGrid){
					 var argsLength = arguments.length, gridOption = {} ;
			    	 if(arguments[0]){
			    		 var baseOption = {
		    			        title:'数据表格',
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