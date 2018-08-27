/**
 * 批量可编辑表格
 * @createUser mxq
 * @createDate 2014-08-08
 */
(function($) {
	$.fn.ligerXEGridEdit = function(options) {
		return $.ligerui.run.call(this, "ligerXEGridEdit", arguments);
	};

	$.fn.ligerGetXEGridEditManager = function() {
		return $.ligerui.run.call(this, "ligerGetXEGridEditManager", arguments);
	};

	$.ligerDefaults.XEGridEdit = {
		toolbar:{},
		grid:{}
	};

	$.ligerMethos.XEGridEdit = $.ligerMethos.XEGridEdit || {};

	$.ligerui.controls.XEGridEdit = function(element, options) {
		$.ligerui.controls.XEGridEdit.base.constructor.call(this, element, options);
	};
	
	$.ligerui.controls.XEGridEdit.ligerExtend($.ligerui.controls.Template,{
			__getType : function() {
				return 'XEGridEdit';
			},
			__idPrev : function() {
				return 'XEGridEdit';
			},
			_extendMethods : function() {
				return $.ligerMethos.XEGridEdit;
			},
			_preRender : function() {
				var g = this, p = this.options;
				// 处理 可编辑表格的配置项信息
				if(p.grid){
					var baseGrid = {
						 delayLoad : false,
						 detailsIsNotNull:false,// 此属性配置grid表格中的数据提交时不能为空
						 title:'',
		  			     enabledEdit: true,
		  			     usePager:false,
		  			     checkbox: true,
	                     rownumbers:true,
	                     width: '99.9%',
		             	 height:'99.9%'
	                 };
					p.grid = $.extend({}, baseGrid, p.grid );
				 }
			},
			_init : function() {
				 $.ligerui.controls.XEGridEdit.base._init.call(this);
			},
			_render: function(){
				$.ligerui.controls.XEGridEdit.base._render.call(this);
			}
   });

})(jQuery);