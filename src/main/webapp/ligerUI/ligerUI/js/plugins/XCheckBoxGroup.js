/*
 * ligerCheckBoxGroup扩展组件：所谓的扩展组件就是对原来功能的补充和出现bug的修复。
 * mxq 2014-05-31 create 
 * 
 * 1、将ligerCheckBoxGroup中的split配置项禁用掉，不可进行配置，分隔符统一用逗号（，）号分隔
 * 2、重写_setUrl方法
 */
(function($) {
	
	/**
	 * 功能：扩充 ligerCheckBoxGroup的配置项信息
	 */
	$.ligerDefaults.CheckBoxGroupOptions = {
		
	};
	
	$.ligerMethos.CheckBoxGroup = {
		/**
		 * 重写：设置URL
		 * 重写原因：从后台URL获取的复选框值，选中提交后，再加载时选中的值不会选上
		 */
		_setUrl : function(url) {
			if (!url)
				return;
			var g = this, p = this.options;
			$.ligerui.ligerAjax( {
				type : 'post',
				url : url,
				data : p.parms,
				cache : false,
				dataType : 'json',
				success : function(data) {
			        g.data = data[p.root]||data;
					g.setData(g.data);
					g.trigger('success', [ data ]);
					
					// mxq 2014-05-28 add
					if (p.value) {
						g.setValue(p.value);
					}
						
				},
				error : function(XMLHttpRequest, textStatus) {
					g.trigger('error', [ XMLHttpRequest,textStatus ]);
				}
			});
		},
		/**
		 * 重写：获取值，分隔符采用逗号分隔，且不可修改
		 */
		_getValue : function() {
			var g = this, p = this.options, name = p.name || g.id;
			var values = [];
			$('input:checkbox[name="' + name + '"]:checked')
					.each(function() {
						values.push(this.value);
					});
			return values.join(',');
		},
		/**
		 * 重写: 改变值，分隔符采用逗号分隔，且不可修改
		 */
		_changeValue : function(value) {
			var g = this, p = this.options, name = p.name|| g.id;
			var valueArr = value ? value.split(',') : [];
			$("input:checkbox[name='" + name + "']",g.CheckBoxGroup)
				.each( function() {
					$(this).attr("checked",$.inArray(this.value,valueArr) > -1);
				});
		}
	};
	
})(jQuery);
