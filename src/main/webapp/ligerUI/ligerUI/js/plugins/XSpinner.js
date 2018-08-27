/*
 * ligerSpinner扩展组件：所谓的扩展组件就是对原来功能的补充和出现bug的修复。
 * 2013-6-27 9:43:25
 * 
 * 
 */
(function($) {
	
	/**
	 * 功能：扩充 ligerSpinner的配置项信息
	 */
	$.ligerDefaults.SpinnerOptions = {
		value: null
	};
	
	
	$.ligerMethos.Spinner = {
		/**
		 * @override 
		 * 功能：重写ligerSpinner的_setDisabled
		 *     重写原因： 没有设置表单元素的 disabled属性值
		 * @param {Boolean} value
		 */
		_setDisabled : function(value) {
			if (value) {
				this.inputText.attr("disabled", true);
				this.wrapper.addClass("l-text-disabled");
			} else if (!this.options.disabled) {
				this.inputText.removeAttr("disabled");
				this.wrapper.removeClass('l-text-disabled');
			}
		},
		/**
		 * @override
		 * 功能： 添加了对 原始值 0、 0.00等的过滤操作，使其更加准确。 
		 * @param {String} value
		 */
		_showValue : function(value) {
			var g = this, p = this.options;
			
			if (!value || value == "NaN")
				value = 0;
			if (p.type == 'float' && value != 0) {
				value = parseFloat(value).toFixed(
						p.decimalplace);
			}
			if(p.value == null && value == 0){
				value = "";
			}
			g.inputText.val(value);
		}
	};

})(jQuery);
