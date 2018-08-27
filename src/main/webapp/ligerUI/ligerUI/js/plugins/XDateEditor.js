/*
 * ligerDateEditor扩展组件：所谓的扩展组件就是对原来功能的补充和出现bug的修复。
 * 2013-6-27 9:43:25
 * 
 * 
 */
(function($) {
	
	/**
	 * 功能：扩充 ligerDateEditor的配置项信息
	 */
	$.ligerDefaults.DateEditorOptions = {
		value: null,
		alwayShowInTop: false,      //下拉框是否一直显示在上方
        alwayShowInDown: false      //下拉框是否一直显示在上方
	};
	
	
	$.ligerMethos.DateEditor = {
		
		/**
		 * @override 
		 * 功能：重写ligerDateEditor的_setDisabled
		 *     重写原因： 没有设置表单元素的 disabled属性值
		 * @param {Boolean} value
		 */
		_setDisabled : function(value) {
			var g = this, p = this.options;
			if(value){
				this.inputText.attr("disabled", true);
				this.text.addClass('l-text-disabled');
				p.disabled = true;
			}else{
				this.inputText.removeAttr("disabled");
				this.text.removeClass('l-text-disabled');
				p.disabled = false;
			}
		},
		setEnabled : function() {
			var g = this, p = this.options;
			this.inputText.removeAttr("disabled");
			this.text.removeClass('l-text-disabled');
			p.disabled = false;
		},
		// mxq 2014-12-26 重写：解决可编辑表格，日期框选择后点击清空按钮清空不了的问题
		_getValue : function() {
			var g = this, p = this.options;
			var _value = g.inputText.val();
			if (_value) {
				return this.usedDate;
			}
			
			return '';
		},
		updateSelectBoxPosition : function() {
			var g = this, p = this.options;
			if (p.absolute) {
				var contentHeight = $(document).height();
				if (p.alwayShowInTop || Number(g.text.offset().top + 1+ g.text.outerHeight()+ g.dateeditor.height()) > contentHeight
						&& contentHeight > Number(g.dateeditor.height() + 1)) {
					//若下拉框大小超过当前document下边框,且当前document上留白大于下拉内容高度,下拉内容向上展现
					g.dateeditor.css( {left : g.text.offset().left,top : g.text.offset().top - 1- g.dateeditor.height()
					});
				} else {
					g.dateeditor.css({left : g.text.offset().left,top : g.text.offset().top + 1+ g.text.outerHeight()});
				}
				if (p.alwayShowInDown)
                {
                    g.dateeditor.css({left : g.text.offset().left,top : g.text.offset().top + 1+ g.text.outerHeight()});
                }
			} else {
				if (g.text.offset().top + 4 > g.dateeditor.height() && g.text.offset().top+ g.dateeditor.height()+ textHeight + 4
					- $(window).scrollTop() > $(window).height()) {
					g.dateeditor.css("marginTop", -1* (g.dateeditor.height()+ textHeight + 5));
					g.showOnTop = true;
				} else {
					g.showOnTop = false;
				}
			}
		}
		
	};

})(jQuery);
