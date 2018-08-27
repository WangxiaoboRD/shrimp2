/**
 * @zhangyq add ligerCheckBoxGroup 组件 
 * @override   该组件 ligerCheckBoxGroup  type=checkboxgroup  
 *       *******************  重写目的  ********************************************
	     *  雷同ligerCheck的重写目的                                         		*
	     * ***********************************************************************
	      
	     #########################################################################
 *       ###注意: 当通过url获得json字符串时要查看是否必要配置root、record                     #
 *       ###     该组件的url配置和表格(ligerGrid)、下拉框(ligerComboBox)雷同              #
 *       ###     <<不要再使用ligerCheckBoxList组件了。>>                              # 
 *       #########################################################################
 * 
 *       ########################################################################
 *       ####example:  /LigerUI2/WebRoot/ligerUI2/demos/form/checkboxGroup.htm  #
 *       ########################################################################
 */
(function($) {

	$.fn.ligerCheckBoxGroup = function(options) {
		return $.ligerui.run.call(this, "ligerCheckBoxGroup", arguments);
	};

	$.ligerDefaults.CheckBoxGroup = {
		rowSize : 3, //每行显示元素数   
		valueField : 'id', //值成员
		textField : 'text', //显示成员 
		name : 'CheckBoxGroup', //表单名
		split : ";", //分隔符
		data : null, //数据  
		parms : null, //ajax提交表单 
		url : null, //数据源URL(需返回JSON)
		onSuccess : null,
		onError : null,
		css : null, //附加css  
		value : null, //值
		root: 'Rows',   //数据源字段名
        record: 'Total' //数据源记录数字段名
	};

	//扩展方法
	$.ligerMethos.CheckBoxGroup = $.ligerMethos.CheckBoxGroup || {};

	$.ligerui.controls.CheckBoxGroup = function(element, options) {
		$.ligerui.controls.CheckBoxGroup.base.constructor.call(this, element,options);
	};
	$.ligerui.controls.CheckBoxGroup.ligerExtend($.ligerui.controls.Input,{
				__getType : function() {
					return 'CheckBoxGroup';
				},
				_extendMethods : function() {
					return $.ligerMethos.CheckBoxGroup;
				},
				_init : function() {
					$.ligerui.controls.CheckBoxGroup.base._init.call(this);
				},
				_render : function() {
					var g = this, p = this.options;
					g.data = p.data;

					g.CheckBoxGroup = $(this.element);
					g.CheckBoxGroup.html(
									'<div class="l-checkboxlist-inner"><table cellpadding="0" cellspacing="0" border="0" class="l-checkboxlist-table"></table></div>')
							.addClass("l-checkboxlist");
					g.CheckBoxGroup.table = $("table:first", g.CheckBoxGroup);

					g.set(p);
				    g._addClickEven();
				},
				destroy : function() {
					if (this.CheckBoxGroup)
						this.CheckBoxGroup.remove();
					this.options = null;
					$.ligerui.remove(this);
				},
				clear : function() {
					this._changeValue("");
					this.trigger('clear');
				},
				_setCss : function(css) {
					if (css) {
						this.CheckBoxGroup.addClass(css);
					}
				},
				_setDisabled : function(value) {
					//禁用样式
					if (value) {
						this.CheckBoxGroup.addClass('l-checkboxlist-disabled');
						$("input:checkbox", this.CheckBoxGroup).attr(
								"disabled", true);

					} else {
						this.CheckBoxGroup.removeClass('l-checkboxlist-disabled');
						$("input:checkbox", this.CheckBoxGroup).removeAttr(
								"disabled");
					}
				},
				_setWidth : function(value) {
					this.CheckBoxGroup.width(value);
				},
				_setHeight : function(value) {
					this.CheckBoxGroup.height(value);
				},
				indexOf : function(item) {
					var g = this, p = this.options;
					if (!g.data)
						return -1;
					for ( var i = 0, l = g.data.length; i < l; i++) {
						if (typeof (item) == "object") {
							if (g.data[i] == item)
								return i;
						} else {
							if (g.data[i][p.valueField].toString() == item
									.toString())
								return i;
						}
					}
					return -1;
				},
				removeItems : function(items) {
					var g = this;
					if (!g.data)
						return;
					$(items).each(function(i, item) {
						var index = g.indexOf(item);
						if (index == -1)
							return;
						g.data.splice(index, 1);
					});
					g.refresh();
				},
				removeItem : function(item) {
					if (!this.data)
						return;
					var index = this.indexOf(item);
					if (index == -1)
						return;
					this.data.splice(index, 1);
					this.refresh();
				},
				insertItem : function(item, index) {
					var g = this;
					if (!g.data)
						g.data = [];
					g.data.splice(index, 0, item);
					g.refresh();
				},
				addItems : function(items) {
					var g = this;
					if (!g.data)
						g.data = [];
					$(items).each(function(i, item) {
						g.data.push(item);
					});
					g.refresh();
				},
				addItem : function(item) {
					var g = this;
					if (!g.data)
						g.data = [];
					g.data.push(item);
					g.refresh();
				},
				_setValue : function(value) {
					var g = this, p = this.options;
					p.value = value;
					this._dataInit();
				},
				setValue : function(value) {
					this._setValue(value);
				},
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
				setUrl : function(url) {
					return this._setUrl(url);
				},
				setParm : function(name, value) {
					if (!name)
						return;
					var g = this;
					var parms = g.get('parms');
					if (!parms)
						parms = {};
					parms[name] = value;
					g.set('parms', parms);
				},
				clearContent : function() {
					var g = this, p = this.options;
					$("table", g.CheckBoxGroup).html("");
				},
				_setData : function(data) {
					this.setData(data);
				},
				setData : function(data) {
					var g = this, p = this.options;
					if (!data || !data.length)
						return;
					g.data = data;
					g.refresh();
				},
				refresh : function() {
					var g = this, p = this.options, data = this.data;
					this.clearContent();
					if (!data)
						return;
					var out = [], rowSize = p.rowSize, appendRowStart = false, 
						name = p.name || g.id;
					for ( var i = 0; i < data.length; i++) {
						var val = data[i][p.valueField], txt = data[i][p.textField], 
							id = name+ "-" + i;
						var newRow = i % rowSize == 0;
						//0,5,10
						if (newRow) {
							if (appendRowStart)
								out.push('</tr>');
							out.push("<tr>");
							appendRowStart = true;
						}
						out.push("<td><input type='checkbox' name='"
								+ name + "' value='" + val + "' id='"
								+ id + "'/><label for='" + id + "'>&nbsp;"
								+ txt + "</label></td>");
					}
					if (appendRowStart)
						out.push('</tr>');
					g.CheckBoxGroup.table.append(out.join(''));
					g.CheckBoxGroup.width(g.CheckBoxGroup.table.width());//zhangyq add TODO
				},
				_getValue : function() {
					var g = this, p = this.options, name = p.name || g.id;
					var values = [];
					$('input:checkbox[name="' + name + '"]:checked')
							.each(function() {
								values.push(this.value);
							});
					return values.join(p.split);
				},
				getValue : function() {
					//获取值
					return this._getValue();
				},
				updateStyle : function() {
					g._dataInit();
				},
				_dataInit : function() {
					var g = this, p = this.options;
					var value =  g._getValue()|| p.value;
					g._changeValue(value);
				},
				//设置值到 隐藏域
				_changeValue : function(value) {
					var g = this, p = this.options, name = p.name|| g.id;
					var valueArr = value ? value.split(p.split) : [];
					$("input:checkbox[name='" + name + "']",g.CheckBoxGroup)
						.each( function() {
							$(this).attr("checked",$.inArray(this.value,valueArr) > -1);
						});
				},
				_addClickEven : function() {
					var g = this, p = this.options;
					p.click && $("td input:checkbox",g.CheckBoxGroup.table).bind('click',p.click);
			    }
				
	});

})(jQuery);