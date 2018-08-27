/**
 * jQuery ligerUI 1.2.0
 * 
 * http://ligerui.com
 *  
 * Author daomi 2013 [ gd_star@163.com ] 
 * 
 */
(function($) {
	$.fn.ligerXEFormGridShow = function(options) {
		return $.ligerui.run.call(this, "ligerXEFormGridShow", arguments);
	};

	$.fn.ligerGetXEFormGridShowManager = function() {
		return $.ligerui.run.call(this, "ligerGetXEFormGridShowManager", arguments);
	};

	$.ligerDefaults.XEFormGridShow = {
		toolbar:{},
		from:{},
		grid:{}
	};

	$.ligerMethos.XEFormGridShow = $.ligerMethos.XEFormGridShow || {};

	$.ligerui.controls.XEFormGridShow = function(element, options) {
		$.ligerui.controls.XEFormGridShow.base.constructor.call(this, element, options);
	};
	
	$.ligerui.controls.XEFormGridShow.ligerExtend($.ligerui.controls.Template,{
			__getType : function() {
				return 'XEFormGridShow';
			},
			__idPrev : function() {
				return 'XEFormGridShow';
			},
			_extendMethods : function() {
				return $.ligerMethos.XEFormGridShow;
			},
			_preRender : function() {
				var g = this, p = this.options;
				// 处理 可编辑表格的配置项信息
				if(p.grid){
					var baseGrid = {
						 title:'数据表格',
		    			 headerImg: '../ligerUI/ligerUI/skins/icons/grid.gif',
						 delayLoad : false,
						 //detailsIsNotNull:false,// 此属性配置grid表格中的数据提交时不能为空
		  			     enabledEdit: false,
		  			     usePager:false,
		  			     checkbox: false,
		  			     frozen:false,
	                     rownumbers:true 
	                 } ;
					p.grid = $.extend({}, baseGrid, p.grid );
				}
				 var closeClick = function(item){
				 	$.pap.removeTabItem();
			     };
				 //如果 form中配置了 url 属性
				 if($.isEmptyObject(p.toolbar)){
					  p.toolbar = { items:[
						  { text: '关闭', click: closeClick ,icon: 'delete' }
					  ]};
				 }
				 
				 // 表格会根据分组按钮收缩/展开自动调整高度
				 $(".togglebtn").live('click', function (){
			    	g.getGrid().setHeight(g.getGrid().options.height);
			     });
				
			},
			_init : function() {
				 $.ligerui.controls.XEFormGridShow.base._init.call(this);
			}
   });

})(jQuery);