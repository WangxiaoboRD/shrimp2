/*
 * ligerRadioBox扩展组件：所谓的扩展组件就是对原来功能的补充和出现bug的修复。
 * mxq 2014-06-01 create
 * 1、添加单选框的change事件 
 */
(function($) {
	
	/**
	 * 功能：扩充 ligerRadioBoxGroup的配置项信息
	 */
	$.ligerDefaults.RadioBoxOptions = {
		onChange: null          // 改变事件
	};
	
	$.ligerMethos.RadioBox = {
		/**
		 * 重写：添加click单击事件
		 */
		_render : function() {
			var g = this, p = this.options;
			g.input = $(this.element);
			//zhnagyq add 
			if(!g.input.attr("name")){
				g.input.attr("name",g.element.id||liger.getId("RadioBox"));
			}
			// mxq 2014-05-29 add start
			g.input.click(function ()
            {
                if (g.input.attr('disabled') || g.input.attr('readonly')) { return false; }
                if (p.disabled || p.readonly) return false;
                g.trigger("change", this);
            });
			g.set(p);
		}
	};
	
})(jQuery);
