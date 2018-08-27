/*
 * ligerRadioGroup扩展组件：所谓的扩展组件就是对原来功能的补充和出现bug的修复。
 * mxq 2014-06-01 create 
 */
(function($) {
	
	/**
	 * 功能：扩充 ligerRadioGroup的配置项信息
	 */
	$.ligerDefaults.RadioGroupOptions = {
		
	};
	
	$.ligerMethos.RadioGroup = {
		/**
		 * 重写：设置URL
		 * 重写原因：从后台URL获取的单选框值，选中提交后，再加载时选中的值不会选上
		 */
		_setUrl: function (url) {
            if (!url) return;
            var g = this, p = this.options; 
            $.ligerui.ligerAjax( {
                type: 'post',
                url: url,
                data: p.parms,
                cache: false,
                dataType: 'json',
                success: function (data) { 
                    g.data = data[p.root]||data;
					g.setData(g.data);
                    g.trigger('success', [data]);
                    // mxq 2014-05-28 add
					if (p.value) {
						g.setValue(p.value);
					}
                },
                error: function (XMLHttpRequest, textStatus) {
                    g.trigger('error', [XMLHttpRequest, textStatus]);
                }
            });
        }
	};
	
})(jQuery);
