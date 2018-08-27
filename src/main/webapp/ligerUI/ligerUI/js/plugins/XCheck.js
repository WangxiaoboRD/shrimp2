/*
 * ligerCheck扩展组件：所谓的扩展组件就是对原来功能的补充和出现bug的修复。
 * mxq 2014-06-01 create
 * 1、可以配置复选框[选中/非选中]状态想向后台提交的值
 * 2、添加复选框的change事件 
 */
(function($) {
	
	/**
	 * 功能：扩充 ligerCheckBoxGroup的配置项信息
	 */
	$.ligerDefaults.CheckOptions = {
		value: false,            // 初始化值，设置或返回 checkbox 的 value 属性的值，默认值和 uncheckedValue（未选中的值）的值相同
		checkedValue: true,     //选中时向后台提交的值，默认为true
		uncheckedValue: false,  //未选中时向后台提交的值，默认为false
		onChange: null,          // 改变事件
		readonly : false //只读
	};
	
	$.ligerMethos.Check = {
		/**
		 * 重写：添加click单击事件
		 */
		_render : function() {
			var g = this, p = this.options;
			g.input = $(g.element); // zhangyq add
			if(!g.input.attr("name")){
				g.input.attr("name",g.element.id||liger.getId("Check"));
			}
			
			// mxq 2014-05-29 add start
			g.input.click(function ()
            {
                if (g.input.attr('disabled') || g.input.attr('readonly')) { return false; }
                if (p.disabled || p.readonly) return false;
                if (this.checked){
                    g._setValue(p.checkedValue);
                }else{
                    g._setValue(p.uncheckedValue);
                }
                
                g.trigger("change", this);
            });
            // mxq 2014-05-29 add end
			this.set(p); 
		},
		_setValue: function(value){
			var g = this, p = this.options;
			// mxq 2014-05-29 add start
			if (value == p.checkedValue) {
				g.setChecked(true);
			}else {
				g.setChecked(false);
				value = p.uncheckedValue;
			}
			// mxq 2014-05-29 add end
			this.input.attr("value", value) ;
		},
		_getValue: function() {
			var g = this, p = this.options;
			return this.input.attr("value");
		},
		// 外部方法：是否为选中状态
		getChecked : function() {
			var g = this, p = this.options;
			return p._getValue() == p.checkedValue;
		},
		// 外部方法：外部方法，设置选中状态
		setChecked : function(value) {
			var g = this, p = this.options;
			if (!value) {
				g.input.attr('checked', false);
			} else {
				g.input.attr('checked', true);
			}
		}
	};
	
})(jQuery);
