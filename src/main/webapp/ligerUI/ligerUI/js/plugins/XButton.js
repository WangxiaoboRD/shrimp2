/**
 * ligerButton扩展组件：
 * @override  _render 函数
 *    	 *******************  重写目的  ********************************************
	     * 对按钮组件的click事件的参数添加两个属性名称分别为:									*
	     *  options       ->  该组件配置项的所有属性                                   *
	     *  ligerButton   ->  该ligerButton对象，你可以通过它调用ligerButton的所有方法     *
	     * ***********************************************************************
 *       
 * 
 * 		 #########################################################################
 *       ###注意:  该组件定义的按钮为非圆角按钮，此按钮不适合在工具条中添加按钮故而我们扩充了一个工具条按钮组件#
 *       ###      参考ligerToolButton.js 组件定义                                    #
 *       #########################################################################
 * 
 */
(function($) {
	
	/**
	 * 功能：扩充 ligerButton的配置项信息
	 */
	$.ligerDefaults.ButtonOptions = { 
		visible: true // 按钮可见设置，mxq 2015-02-07 add
	};
	
	
	$.ligerMethos.Button = {
		/**
		 * @override
		 */
		_render: function() {
				var g = this, p = this.options;
				g.button = $(g.element);
				g.button.addClass("l-button");
				g.button.append('<div class="l-button-l"></div><div class="l-button-r"></div><span></span>');
				g.button.hover(function() {
					if (p.disabled)
						return;
					g.button.addClass("l-button-over");
				}, function() {
					if (p.disabled)
						return;
					g.button.removeClass("l-button-over");
				});
				/*  old 
				p.click && g.button.click(function() {
					if (!p.disabled)
						p.click();
				});*/
				p.click && g.button.click(function(item) {
					item = item ||{} ;
					if (!p.disabled){
						item['options'] = p;
						item['ligerButton'] = g ;// 将当前按钮的liger对象给ligerButton对象
						p.click(item);
					}
				});
				g.set(p);
		},
		
		// 按钮可见设置
		_setVisible: function (value){
			if (value) {
				this.button.css({display: 'block'});
				this.options.visible = true;
			} else {
				this.button.css({display: 'none'});
				this.options.visible = false;
			}
        },
        // 设置可见
        setVisible: function (){
			this.set('visible', true);
        },
        // 设置不可见
        setInvisible: function (){
        	this.set('visible', false);
        }
	}; 

})(jQuery);
