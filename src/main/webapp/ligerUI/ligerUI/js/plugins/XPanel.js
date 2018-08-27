/**
 * ligerPanel扩展组件：
 * @override  _render 函数
 *    	 *******************  重写目的  ********************************************
	     * 对ligerPanel标题进行扩展，添加标题图标配置									*
 */
(function($) {
	
	/**
	 * 功能：扩充 ligerPanel的配置项信息
	 */
	$.ligerDefaults.PanelOptions = {
		titleIcon: null // 标题图标	
	};
	
	
	$.ligerMethos.Panel = {
		
		/**
		 * 重写方法
		 */
		  _setIcon : function(url)
        {
            var g = this;
            if (!url)
            {
                g.panel.find('.l-panel-hasicon img:first').remove();
                g.panel.removeClass("l-panel-hasicon");
            } else
            {
                g.panel.addClass("l-panel-hasicon");
                g.panel.append('<img src="' + url + '" />');
            }
        }, 
        
        /**
         * 新增方法
         */
		 _setTitleIcon: function (url)
        {
        	var header = this.panel.find(".l-panel-header span");
            if (!url)
            {
               header.removeClass("l-panel-header-hasicon");
               header.find("img").remove();
            } else
            {
            	header.addClass("l-panel-header-hasicon");
            	header.append("<img src='" + url + "' />");
            }
        },
        
         _setTitle: function (value)
        {
            this.panel.find(".l-panel-header span:first").text(value);
        } 
	};

})(jQuery);
