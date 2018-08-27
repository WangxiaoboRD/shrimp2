/**
 * jQuery ligerUI 1.2.0
 * 
 * http://ligerui.com
 *  
 * Author daomi 2013 [ gd_star@163.com ] 
 * 
 */
(function($) {
	$.fn.ligerXReportByCon = function(options) {
		return $.ligerui.run.call(this, "ligerXReportByCon", arguments);
	};

	$.fn.ligerGetXReportByConManager = function() {
		return $.ligerui.run.call(this, "ligerGetXReportByConManager", arguments);
	};

	$.ligerDefaults.XReportByCon = {
		toolbar:{},
		form:{},
		report:{}
	};

	$.ligerMethos.XReportByCon = $.ligerMethos.XReportByCon || {};

	$.ligerui.controls.XReportByCon = function(element, options) {
		$.ligerui.controls.XReportByCon.base.constructor.call(this, element, options);
	};
	
	$.ligerui.controls.XReportByCon.ligerExtend($.ligerui.controls.ReportTemp,{
			__getType : function() {
				return 'XReportByCon';
			},
			__idPrev : function() {
				return 'XReportByCon';
			},
			_extendMethods : function() {
				return $.ligerMethos.XReportByCon;
			},
			_preRender : function() {
				var g = this, p = this.options;
				 //如果 form中配置了 url 属性
				 if($.isEmptyObject(p.toolbar)){
					  p.toolbar = { items:[
						  { text: '关闭', icon: 'delete', click: function(){
						  	 $.pap.removeTabItem();
						  }}
					  ]};
				 }
			},
			_init: function() {
				 $.ligerui.controls.XReportByCon.base._init.call(this);
			},
			_render: function() {
				$.ligerui.controls.XReportByCon.base._render.call(this);
			},
		    createForm: function(formOptions, dom){
				 var g = this ,p = g.options ;
				 var _url = p.report.url;
				 if (!_url) {
					 alert("对不起，您未指定报表文件url路径，报表加载失败！");
			         throw new Error("对不起，您未指定报表文件url路径，报表加载失败！");
				 }
				 if(jQuery.fn.ligerForm){
					 var argsLength = arguments.length, formOption = {} ;
			    	 if(arguments[0]){
			    		 var baseOption = { inputWidth: 150, 
				                labelWidth: 60, 
				                space:20,
				                buttons:[
				                    { text:'查询', icon: '../ligerUI/ligerUI/skins/icons/search2.gif', click:function(item){
				                    	var reportURL = "../ReportServer?reportlet=" + _url ;
				                    	// 是否分页，默认为分页true
				                    	if (p.report.usePager != undefined && !p.report.usePager) {
				                    		reportURL += "&op=view";
				                    	} 
				                    	// 是否显示工具条，默认为显示true
				                    	if (p.report.showToolbar != undefined && !p.report.showToolbar) {
				                    		reportURL += "&__showtoolbar__=false";
				                    	}
				                    	var arra = $('form').formToArray();
				                    	$(arra).each(function (){
					                        reportURL += "&" + this.name + "=" + this.value;
					                    });
									    $("form")[0].action = cjkEncode(reportURL); //通过form的name获取表单，并将报表访问路径赋给表单的action   
									    $("form")[0].submit(); //触发表单提交事件  
			 							
				                    }}
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
				    		targetTag = $('<form id="'+formOption['id']+'" method="post" target="reportFrame"></form>');
				    		if(arguments[1]){
				    			$(dom).append(targetTag);
				    		}else{
				    			$('body').append(targetTag);
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
		 createReport: function(reportOptions, dom){
		 	var g = this ,p = this.options, rp = reportOptions;
			var targetTag = ['<iframe id="reportFrame" name="reportFrame" frameborder="0" marginwidth="0" marginheight="0" '];
			var width = "100%";
			var scrolling = "anto";
			if (rp) {
				if (rp.width) {
					width = rp.width;
				}
				if (rp.height) {
					targetTag.push(" height=");
					targetTag.push(rp.height)
				}
				if (rp.scrolling) {
					scrolling = rp.scrolling;
				}
			}
			targetTag.push(" width=");
			targetTag.push(width)
			var scrollStr = ' scrolling="' + scrolling + '"';
			targetTag.push(scrollStr);
			targetTag.push("/>");
    		if(arguments[1]){
    			$(dom).append( targetTag.join(''));
    		}else{
    			$('body').append(targetTag.join(''));
    		}
		 }
   });

})(jQuery);