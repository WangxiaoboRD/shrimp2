/**
 * @override  _drag 函数
 *    	 *******************  重写目的  ********************************************
	     * 被resize对象的top和left不能小于0                                           *
	     ************************************************************************
 *       
 * 
 * 		 #########################################################################
 *       ###注意:  视情况而定你也可以更改                                                #
 *       #########################################################################
 * 
 */
(function($) {
	
	/**
	 * 功能：扩充 ligerResizable的配置项信息
	 */
	$.ligerDefaults.ResizableOptions = {
		disabled : false , //省去的配置项
		isHidden :false // 修复了作者的一个bug
	};
	
	
	$.ligerMethos.Resizable = {
		 /**
		  * resizable 的核心方法 
		  */
		 _drag: function (e){ //调用该函数   代理已经占上风了，哈哈。
            var g = this, p = this.options;
            if (!g.current) return;
            if (!g.proxy) return;
            g.proxy.css('cursor', g.current.dir == '' ? 'default' : g.current.dir + '-resize');
            var pageX = e.pageX || e.screenX;
            pageX =  pageX<=0?0:pageX;   /* TODO zhangyq XXX 解决拖拽的问题*/
            var pageY = e.pageY || e.screenY;
            pageY = pageY<=0?0:pageY;    /* TODO zhangyq */
            g.current.diffX = pageX - g.current.startX; //获取鼠标两次的X、Y 的差距
            g.current.diffY = pageY - g.current.startY;
            g._applyResize(g.proxy);
            g.trigger('resize', [g.current, e]);
        }
		
	
    };
})(jQuery);
