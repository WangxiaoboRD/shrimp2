/**
 * @mxq 类似于html的标签<label</label>组件
 * @override   该组件 ligerLabel  type="label" 
 *       *******************  重写目的  ********************************************
	     *  模板定义组件中，查看form详情是需要一个类似label标签定义的标签组件因此该组件应运而生   	     *
	     *  类似与html 的 <label></label>标签 
	     *  example:  有关自动创建form需要参考XForm.js定义的ligerForm组件
	     *  <pre>以下是ligerForm一条配置项：
	     *        { display: "至", name: 'to', type: "label", newline: true } 
            </pre>
	     * ***********************************************************************
 *      
 */
(function($) {
	$.fn.ligerLabel = function() {
		return $.ligerui.run.call(this, "ligerLabel", arguments);
	};

	$.fn.ligerGetLabelManager = function() {
		return $.ligerui.run.call(this, "ligerGetLabelManager", arguments);
	};
	$.ligerMethos.Label = $.ligerMethos.Label || {};
	
	// 参数设置
	$.ligerDefaults.Label = {
	};

	$.ligerui.controls.Label = function(element, options) {
		$.ligerui.controls.Label.base.constructor.call(this, element, options);
	};

	$.ligerui.controls.Label.ligerExtend($.ligerui.controls.Input,{
			__getType : function() {
				return 'Label'
			},
			__idPrev : function() {
				return 'Label';
			},
			_extendMethods : function() {
				return $.ligerMethos.Label;
			},
			_init : function() {
				$.ligerui.controls.Label.base._init.call(this);
			},
			_render : function() {
				var g = this, p = this.options;
				g.set(p);
				g.checkValue();
			}
	});

})(jQuery);