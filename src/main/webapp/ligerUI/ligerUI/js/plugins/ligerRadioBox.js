/**
 * @zhangyq    
 * @override   该组件 ligerRaidoBox type=radio
 *       *******************  重写目的  ********************************************
	     *  和ligerCheck、ligerCheckBoxGroup 雷同
	     *  使用原始html中的radio重写 替换原来通过背景图片切换样式呈现复选框的模式   			*
	     *  在结合form组件时原始组件不支持form(reset)清空表单                              *
	     * ***********************************************************************
	      
	     #########################################################################
 *       ###注意:  小心原始html的 <input type="radio" /> 如果未选中不会提交值的问题       #
 *       ###      一定要当心呀！！！
 *       ###      <<不要在用使用ligerRadio组件了。>>
 *       #########################################################################
 *      
 */

(function($) {

	$.fn.ligerRadioBox = function() {
		return $.ligerui.run.call(this, "ligerRadioBox", arguments);
	};

	$.fn.ligerGetRadioBoxManager = function() {
		return $.ligerui.run.call(this, "ligerGetRadioBoxManager", arguments);
	};

	$.ligerDefaults.RadioBox = {
		disabled : false,
		checked : false
	};

	$.ligerMethos.RadioBox = $.ligerMethos.RadioBox || {};

	$.ligerui.controls.RadioBox = function(element, options) {
		$.ligerui.controls.RadioBox.base.constructor.call(this, element, options);
	};
	$.ligerui.controls.RadioBox.ligerExtend($.ligerui.controls.Input, {
		__getType : function() {
			return 'RadioBox';
		},
		__idPrev : function() {
			return 'RadioBox';
		},
		_extendMethods : function() {
			return $.ligerMethos.RadioBox;
		},
		_render : function() {
			var g = this, p = this.options;
			g.input = $(this.element);
			//zhnagyq add 
			if(!g.input.attr("name")){
				g.input.attr("name",g.element.id||liger.getId("Check"));
			}
			p.click && g.input.bind('click', p.click);// 此处添加一个 click 事件  2013-7-27 10:32:50
			g.set(p);
		},
		setValue : function(value) {
			var g = this, p = this.options;
			g._setValue(value);
		},
		_setValue :function(value){
			 this.input[0].value == value ;
		},
		getValue : function() {
			return this.input[0].checked && this.input[0].value ;
		},
		_setChecked: function(checked){
			if(checked){
				this.input.attr("checked",true);
			}
		},
		setEnabled : function() {
			this.input.attr('disabled', false);
			this.options.disabled = false;
		},
		setDisabled : function() {
			this.input.attr('disabled', true);
			this.options.disabled = true;
		}
	});

})(jQuery);