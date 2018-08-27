/**
 * 报表基础模板
 */
(function($) {
	$.fn.ligerReportTemp = function(options) {
		return $.ligerui.run.call(this, "ligerReportTemp", arguments);
	};

	$.fn.ligerGetReportTempManager = function() {
		return $.ligerui.run.call(this, "ligerGetReportTempManager", arguments);
	};

	$.ligerDefaults.ReportTemp = {
		toolbar:{},
		form:{},
		report:{}
	};

	$.ligerMethos.ReportTemp = $.ligerMethos.ReportTemp || {};

	$.ligerui.controls.ReportTemp = function(element, options) {
		$.ligerui.controls.ReportTemp.base.constructor.call(this, element, options);
	};
	
	$.ligerui.controls.ReportTemp.ligerExtend($.ligerui.core.UIComponent,{
			__getType : function() {
				return 'ReportTemp';
			},
			__idPrev : function() {
				return 'ReportTemp';
			},
			_extendMethods : function() {
				return $.ligerMethos.ReportTemp;
			},
			_init : function() {
				 $.ligerui.controls.ReportTemp.base._init.call(this);
			},
			
			_render : function() {
				var g = this, p = this.options;
				g.template = $(this.element);
				g.template.toolbar = $("div:first",g.template) ;
				g.template.form = $("div:eq(1)",g.template) ;
				g.template.report = $("div:last",g.template) ;
				
				if(!$.isEmptyObject(p.form)){
					g.form = g.createForm(p.form, g.template.form);
				}else{
					g.template.form.remove(); // 删除该form
				}
				
				// 报表
				if(!$.isEmptyObject(p.report)){
					g.form = g.createReport(p.report, g.template.report);
				}else{
					g.form = g.createReport(null, g.template.report);
				}
				
				// 工具条是不能缺少的。
				if(!$.isEmptyObject(p.toolbar)){
					g.toolbar = g.createToolbar(p.toolbar, g.template.toolbar);
				}else {
					g.template.toolbar.remove(); // 删除该toolbar
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
			    	return  targetTag.ligerToolBar(toolBarOption);
				}
				return null;
		   },
		   
		   getForm:function(){
			  return  this.form ;  
		   },
		   
		   createForm: function(formOptions,dom){
			 var g = this ,p = this.options ;
			 if(jQuery.fn.ligerForm){
				 var argsLength = arguments.length, formOption = {} ;
		    	 if(arguments[0] ){
		    		 var baseOption = { inputWidth: 150, 
						                labelWidth: 100, 
						                space: 40,
						                buttons:[
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
		 getReport: function(){
			 return this.report ; 
		 }
		 
   });
	
})(jQuery);