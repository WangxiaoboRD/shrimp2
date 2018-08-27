/**
 * ligerToolButton扩展组件：
 * 
 */
(function($) {
	
	/**
	 * 功能：扩充 ligerToolButton的配置项信息
	 */
	$.ligerDefaults.ToolButtonOptions = { 
		visible: true // 按钮可见设置，mxq 2015-02-07 add
	};
	
	
	$.ligerMethos.ToolButton = {
		
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
