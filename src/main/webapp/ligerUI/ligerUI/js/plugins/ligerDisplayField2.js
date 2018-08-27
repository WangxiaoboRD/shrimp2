
/** mxq 2013-12-28备份
 * @zhangyq    新添加的组件
 * @override   该组件 ligerDisplayField  type="displayfield" 
 *       *******************  重写目的  ********************************************
	     *  模板定义组件中，查看form详情是需要一个类似label标签定义的标签组件因此该组件应运而生   	     *
	     *  类似与Ext JS 的 DisplayField组件 
	     *  example:  有关自动创建form需要参考XForm.js定义的ligerForm组件
	     *  <pre>以下是ligerForm一条配置项：
	     *        { display: "状态", name: "e.userStatus",
		    	    type: "displayfield",newline: true, 
		    	    options:{
			              value:currentParam['userStatus'],
			              render:function(value){
		    	    	      var result = null;
		    	    	      if(value == '1'){
		    	    	    	  result = "启用";
		    	    	      }else if(value == '2'){
		    	    	    	  result = "停用" ;
		    	    	      }
		    	    	      return result ;
			              }
	                }}</pre>
	         当心其中的3个配置项 type="displayfield"、render 、value   
	     * ***********************************************************************
	      
	     #########################################################################
 *       ###注意:  此组件内部还是一个<input type="text" >只是通过css将其原始样式定义为现在样式了   #
 *       ###      如果你用在非查看明细form中 会将该displayfield显示的值提交。                 #
 *       ###      根据需求定，当前你也可以修改。                              			 #
 *       #########################################################################
 *      
 */
(function($) {
	$.fn.ligerDisplayField = function() {
		return $.ligerui.run.call(this, "ligerDisplayField", arguments);
	};

	$.fn.ligerGetDisplayFieldManager = function() {
		return $.ligerui.run.call(this, "ligerGetDisplayFieldManager", arguments);
	};
	//zhangyq add 2013-6-26 11:27:26
	$.ligerMethos.DisplayField = $.ligerMethos.DisplayField || {};
	//end of zhangyq add 2013-6-26 11:28:07
	
	$.ligerDefaults.DisplayField = {
		width : null,
		value : null, //初始化值 
		nullText : null, //不能为空时的提示
		digits : false, //是否限定为数字输入框
		number : false, //是否限定为浮点数格式输入框
		currency : false , //是否显示为货币形式
		readonly : true,
		render : null // zhangyq add TODO 
	//是否只读
	};

	$.ligerui.controls.DisplayField = function(element, options) {
		$.ligerui.controls.DisplayField.base.constructor.call(this, element, options);
	};

	$.ligerui.controls.DisplayField.ligerExtend($.ligerui.controls.Input,{
				__getType : function() {
					return 'DisplayField'
				},
				__idPrev : function() {
					return 'DisplayField';
				},
				//zhangyq add 2013-6-26 11:28:21
				_extendMethods : function() {
					return $.ligerMethos.DisplayField;
				},
				// end of zhangyq add 2013-6-26 11:28:31
				_init : function() {
					$.ligerui.controls.DisplayField.base._init.call(this);
					var g = this, p = this.options;
					if (!p.width) {
						p.width = $(g.element).width();
					}
				},
				_render : function() {
					var g = this, p = this.options;
					g.inputText = $(this.element);
					//设置为 不可用
					g.inputText.attr("readonly",true);
					//外层
					g.wrapper = g.inputText.wrap('<div class="l-text-display"></div>').parent();
					g.wrapper.append('<div class="l-text-l"></div><div class="l-text-r"></div>');
					if (!g.inputText.hasClass("l-text-field"))
						g.inputText.addClass("l-text-field");
					g.set(p);
					g.checkValue();
				},
				_getValue : function() {
					return this.inputText.val();
				},
				_setNullText : function() {
					this.checkNotNull();
				},
				checkValue : function() {
					var g = this, p = this.options;
					var v = g.inputText.val() || "";
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
				},
				checkNotNull : function() {
					var g = this, p = this.options;
					if (p.nullText && !p.disabled) {
						if (!g.inputText.val()) {
							g.inputText.addClass("l-text-field-null").val(p.nullText);
						}
					}
				},
				_setWidth : function(value) {
					if (value > 20) {
						this.wrapper.css( {
							width : value
						});
						this.inputText.css( {
							width : value - 4
						});
					}
				},
				_setHeight : function(value) {
					if (value > 10) {
						this.wrapper.height(value);
						this.inputText.height(value - 2);
					}
				},
				_setValue : function(value) {
					var g = this ,p = this.options ;
					if (value != null){
						if(p.render){
							value = p.render(value);
						}
						this.inputText.val(value);
						//this.inputText.html(value);
					}
				},
				_setLabel : function(value) {
					var g = this, p = this.options;
					if (!g.labelwrapper) {
						g.labelwrapper = g.wrapper.wrap(
								'<div class="l-labeltext"></div>')
								.parent();
						var lable = $('<div class="l-text-label" style="float:left;">' + value + ':&nbsp</div>');
						g.labelwrapper.prepend(lable);
						g.wrapper.css('float', 'left');
						if (!p.labelWidth) {
							p.labelWidth = lable.width();
						} else {
							g._setLabelWidth(p.labelWidth);
						}
						lable.height(g.wrapper.height());
						if (p.labelAlign) {
							g._setLabelAlign(p.labelAlign);
						}
						g.labelwrapper
								.append('<br style="clear:both;" />');
						g.labelwrapper
								.width(p.labelWidth + p.width + 2);
					} else {
						g.labelwrapper.find(".l-text-label").html(
								value + ':&nbsp');
					}
				},
				_setLabelWidth : function(value) {
					var g = this, p = this.options;
					if (!g.labelwrapper)
						return;
					g.labelwrapper.find(".l-text-label").width(value);
				},
				_setLabelAlign : function(value) {
					var g = this, p = this.options;
					if (!g.labelwrapper)
						return;
					g.labelwrapper.find(".l-text-label").css(
							'text-align', value);
				},
				updateStyle : function() {
					var g = this, p = this.options;
					if (g.inputText.attr('readonly')) {
						g.wrapper.addClass("l-text-readonly");
						p.disabled = true;
					} else {
						g.wrapper.removeClass("l-text-readonly");
						p.disabled = false;
					}
					if (g.inputText.attr('disabled')) {
						g.wrapper.addClass("l-text-disabled");
						p.disabled = true;
					} else {
						g.wrapper.removeClass("l-text-disabled");
						p.disabled = false;
					}
					if (g.inputText.hasClass("l-text-field-null")
							&& g.inputText.val() != p.nullText) {
						g.inputText.removeClass("l-text-field-null");
					}
					g.checkValue();
				}
			});

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