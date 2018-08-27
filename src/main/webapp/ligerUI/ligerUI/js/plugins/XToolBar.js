/**
 * @override   addItem 函数
 *    	 *******************  重写目的  ********************************************
	     * 在定义模板组件使需求的刺激激发创建了一个ligerToolButton组件(ligerToolButton.js)    *
	     * 具体使用地方和如何使用在模板组件中有详细讲解                                       *
	     * * *********************************************************************
 *       
 */
(function($) {
	
	/**
	 * 功能：扩充 ligerToolBar的配置项信息
	 */
	$.ligerDefaults.ToolBarOptions = {};
	
	$.ligerMethos.ToolBar = {
		
		/**
		 * 重写该方法，使用自定义的ligerToolButton 创建工具条中的按钮
		 * @update 2013-7-23 10:12:13
		 */
		addItem: function (item){
            var g = this, p = this.options;
            if (item.line){
                g.toolBar.append('<div class="l-bar-separator"></div>');
                return;
            }
            var ditem = $('<div class="l-toolbar-item"></div>'),
            	toolButton =null;
            if($.fn.ligerToolButton){
            	toolButton = ditem.ligerToolButton(item);
            }
            g.toolBar.append(ditem);
            g.toolButtons = g.toolButtons  || [] ;
            g.toolButtons[g.toolButtons .length]= toolButton ;
            
        }
	
    };
})(jQuery);
