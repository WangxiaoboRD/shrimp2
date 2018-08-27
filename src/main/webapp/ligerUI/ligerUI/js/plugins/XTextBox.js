/*
 * ligerTextBox扩展组件：所谓的扩展组件就是对原来功能的补充和出现bug的修复。
 * 2013-6-27 9:43:25
 * 
 * 
 */
(function($) {
	
	/**
	 * 功能：扩充 ligerTextBox的配置项信息
	 */
	$.ligerDefaults.TextBoxOptions = {
		initSelect : false // 文本框获得焦点时文本框内容是否全选
	};
	
	
	$.ligerMethos.TextBox = {
		
		/**
		 * 重写文本框事件，为实现文本框获得焦点时其内容自动选中
		 * @date 2016-03-15
		 * @author mxq 
		 */
		_setEvent : function() {
			var g = this, p = this.options;
			g.inputText.bind('blur.textBox', function() {
				g.trigger('blur');
				g.checkNotNull();
				g.checkValue();
				g.wrapper.removeClass("l-text-focus");
			}).bind('focus.textBox', function() {
				g.trigger('focus');
				if (p.nullText) {
					if ($(this).hasClass("l-text-field-null")) {
						$(this).removeClass("l-text-field-null").val("");
					}
				}
				g.wrapper.addClass("l-text-focus");
                if (p.initSelect){
                     setTimeout(function (){
                        g.inputText.select();
                    }, 50);
                } 
			})/*.bind('click.textBox', function() {
				if (p.initSelect){
                    setTimeout(function (){
                        g.inputText.select();
                    }, 50);
                } 
			})*/.bind('keydown.textBox', function(e) {
				var key = e.keyCode;
				if (key == 37 || key == 38 || key == 39 || key == 40 || key == 13) {
					g.checkNotNull();
					g.checkValue();
                }
			}).change(function() {
				g.trigger('changeValue', [ this.value ]);
			});
			g.wrapper.hover(function() {
				g.trigger('mouseOver');
				g.wrapper.addClass("l-text-over");
			}, function() {
				g.trigger('mouseOut');
				g.wrapper.removeClass("l-text-over");
			});
		},
		 _setVisible: function (value)
        {
        	var g = this, p = this.options;
            //显示样式
            if (value)
            {
                g.wrapper.parent().parent().css({ display: 'block' });
            } else
            {
                g.wrapper.parent().parent().css({ display: 'none' });
            }
        },
		/**
		 * @override 
		 * 功能：重写ligerTextBox的_setDisabled
		 *     重写原因： 如果表单元素设置了 disabled 为true 则设置该属性但是老框架设置的是 readonly 属性。
		 *             其实不爽。
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
		 * 设置文本框只读
		 */
		_setReadonly : function(value) {
			var g = this, p = this.options;
			if (value) {
				this.inputText.attr("readonly", "readonly");
				this.wrapper.addClass("l-text-readonly");
			} else if (!p.readonly) {
				this.inputText.removeAttr("readonly");
				this.wrapper.removeClass("l-text-readonly");
			}
		},
		/**
		 * @override  
		 * 
		 */
		checkValue : function() {
			var g = this, p = this.options;
			var v = g.inputText.val() || "";
			if(v == p.nullText){
				return ;
			}
			if (p.currency)
				v = v.replace(/\$|\,/g, '');
			var isFloat = p.number || p.currency, 
				isDigits = p.digits;
			if (isFloat && !/^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/.test(v) || isDigits
					&& !/^\d+$/.test(v)) {
				//不符合,恢复到原来的值
				g.inputText.val(g.value || 0);
				p.currency && g.inputText.val(currencyFormatter(g.value));
				return;
			}
			g.value = v;
			p.currency && g.inputText.val(currencyFormatter(g.value));
		}
	};
	
	function currencyFormatter(num) {
		if (!num)
			return "0.00";
		num = num.toString().replace(/\$|\,/g, '');
		if (isNaN(num))
			num = "0.00";
		sign = (num == (num = Math.abs(num)));
		num = Math.floor(num * 100 + 0.50000000001);
		cents = num % 100;
		num = Math.floor(num / 100).toString();
		if (cents < 10)
			cents = "0" + cents;
		for ( var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
			num = num.substring(0, num.length - (4 * i + 3)) + ','
					+ num.substring(num.length - (4 * i + 3));
		return "" + (((sign) ? '' : '-') + '' + num + '.' + cents);
	}


})(jQuery);
