/**
 * @zhangyq 修改该 复选框组件
 * @override   该组件 ligerCheck  type=checkbox
 *       *******************  重写目的  ********************************************
	     *  使用原始html中的checkbox重写 替换原来通过背景图片切换样式呈现复选框的模式   			*
	     *  在结合form组件时原始组件不支持form(reset)清空表单                              *
	     * ***********************************************************************
	      
	     #########################################################################
 *       ###注意:  小心原始html的 <input type="checkbox" /> 如果未选中不会提交值的问题       #
 *       ###      一定要当心呀！！！
 *       ###      <<不要在用使用ligerCheckBox组件了。>>
 *       #########################################################################
 *      
 */
(function($) {
	$.fn.ligerCheck = function(options) {
		return $.ligerui.run.call(this, "ligerCheck", arguments);
	};
	$.fn.ligerGetCheckManager = function() {
		return $.ligerui.run.call(this, "ligerGetCheckManager", arguments);
	};
	$.ligerDefaults.Check = {
		name:null,
		disabled : false //只读
	};

	$.ligerMethos.Check = $.ligerMethos.Check ||{};

	$.ligerui.controls.Check = function(element, options) {
		$.ligerui.controls.Check.base.constructor.call(this, element,options);
	};
	$.ligerui.controls.Check.ligerExtend($.ligerui.controls.Input, {
		__getType : function() {
			return 'Check';
		},
		__idPrev : function() {
			return 'Check';
		},
		_extendMethods : function() {
			return $.ligerMethos.Check;
		},
		_render : function() {
			var g = this, p = this.options;
			g.input = $(g.element); // zhangyq add
			if(!g.input.attr("name")){
				g.input.attr("name",g.element.id||liger.getId("Check"));
			}
			p.click && g.input.bind('click', p.click);// 此处添加一个 click 事件  2013-7-27 10:32:50
			this.set(p); //end of zhangyq add 
		},
		setValue : function(value) {
			var g = this, p = this.options;
			g._setValue(value);
		},
		_setValue :function(value){
			  this.input.attr("value", value) ;
		},
		_setChecked : function(value) {
			var g = this, p = this.options;
			if (!value) {
				g.input.attr('checked', false);
			} else {
				g.input.attr('checked', true);
			}
		},
		_setDisabled : function(value) {
			if (value) {
				this.input.attr('disabled', true);
			} else {
				this.input.attr('disabled', false);
			}
		}
		
	});
})(jQuery);