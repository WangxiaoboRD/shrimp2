/**
 * @override   该组件 ligerToolButton  
 *       *******************  重写目的  ********************************************
	     *  用途：在模板类中使用 作为工具条toolbar创建按钮的组件  			                 *
	     *      在模板组件类中会对该组件的功能大放异彩                                       *
	     * ***********************************************************************
 *      
 */
(function($) {

	$.fn.ligerToolButton = function(options) {
		return $.ligerui.run.call(this, "ligerToolButton", arguments);
	};
	$.fn.ligerGetToolButtonManager = function() {
		return $.ligerui.run.call(this, "ligerGetToolButtonManager", arguments);
	};

	$.ligerDefaults.ToolButton = {
		//width : 60,
		text : 'ToolButton',
		disabled : false,
		click : null,
		icon : null,
		onSelectionchange:null // 此处注册 toolbar 中的按钮关联 grid的事件
	};

	$.ligerMethos.ToolButton = $.ligerMethos.ToolButton || {};

	$.ligerui.controls.ToolButton = function(element, options) {
		$.ligerui.controls.ToolButton.base.constructor.call(this, element, options);
	};
	$.ligerui.controls.ToolButton.ligerExtend($.ligerui.controls.Input,{
			__getType : function() {
				return 'ToolButton';
			},
			__idPrev : function() {
				return 'ToolButton';
			},
			_extendMethods : function() {
				return $.ligerMethos.ToolButton;
			},
			_preRender: function(){
				var g = this, p = this.options;
				p.onSelectionchange = function(item,selectedCount){
				   // 此处为的解决 模板类中 工具条和表格之间关联关系问题
				   if(item.options.expression && item.options.selectGrid ){
					  item._setDisabled( eval(selectedCount +item.options.expression) )   
				   }
				}
			},
			_render : function() {
				var g = this, p = this.options;
				g.button = $(g.element);
				g.button.addClass("l-panel-btn");
				g.button.append('<span></span><div class="l-panel-btn-l"></div><div class="l-panel-btn-r"></div>');
				
				g.button.hover(function() {
					if ($(this).hasClass("l-toolbar-item-disable")) 
						return;
					$(this).addClass("l-panel-btn-over");
				}, function (){
					if ($(this).hasClass("l-toolbar-item-disable")) 
						return;
					$(this).removeClass("l-panel-btn-over");
				});
				
				p.click && g.button.click(function() {
					    if ($(this).hasClass("l-toolbar-item-disable")) 
            		        return;
						p.click(p);
				});
				g.set(p);
			},
			_setIcon : function(url) {
				var g = this;
				if (!url) {
					g.button.removeClass("l-button-hasicon");
					g.button.find('img').remove();
				} else {
					g.button.addClass("l-toolbar-item-hasicon");
					g.button.append("<div class='l-icon l-icon-" + url + "'></div>");
				}
			},
			_setEnabled : function(value) {
				if (value)
					this.button.removeClass("l-toolbar-item-disable");
			},
			_setDisabled : function(value) {
				if (value) {
					this.button.addClass("l-toolbar-item-disable");
					this.options.disabled = true;
				} else {
					this.button.removeClass("l-toolbar-item-disable");
					this.options.disabled = false;
				}
			},
			_setWidth : function(value) {
				this.button.width(value);
			},
			_setText : function(value) {
				$("span", this.button).html(value);
			},
			setValue : function(value) {
				this.set('text', value);
			},
			getValue : function() {
				return this.options.text;
			},
			setEnabled : function() {
				this.set('disabled', false);
			},
			setDisabled : function() {
				this.set('disabled', true);
			}
	});

})(jQuery);