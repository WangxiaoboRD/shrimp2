/**
 * 
 */
(function($) {
	$.fn.ligerXSFormShow = function(options) {
		return $.ligerui.run.call(this, "ligerXSFormShow", arguments);
	};

	$.fn.ligerGetXSFormShowManager = function() {
		return $.ligerui.run.call(this, "ligerGetXSFormShowManager", arguments);
	};

	$.ligerDefaults.XSFormShow = {
		toolbar:{},
		from:{}//,
		//grid:{}
	};

	$.ligerMethos.XSFormShow = $.ligerMethos.XSFormShow || {};

	$.ligerui.controls.XSFormShow = function(element, options) {
		$.ligerui.controls.XSFormShow.base.constructor.call(this, element, options);
	};
	
	$.ligerui.controls.XSFormShow.ligerExtend($.ligerui.controls.Template,{
			__getType : function() {
				return 'XSFormShow';
			},
			__idPrev : function() {
				return 'XSFormShow';
			},
			_extendMethods : function() {
				return $.ligerMethos.XSFormShow;
			},
			_init : function() {
				 $.ligerui.controls.XSFormShow.base._init.call(this);
				 var g = this ,p = g.options ;
				 var closeClick = function(item){
					$.pap.removeTabItem() ;
			     }
				 if($.isEmptyObject(p.toolbar)){
					 p.toolbar = {items:[
						  { text: '关闭', click: closeClick, icon: 'delete' }
					 ]};
				 }
				 p.grid = null ;
			},
			_render : function() {
				$.ligerui.controls.XSFormShow.base._render.call(this);
			}
		 
   });

})(jQuery);