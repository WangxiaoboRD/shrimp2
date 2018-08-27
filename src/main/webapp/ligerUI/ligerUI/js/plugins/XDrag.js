/**
 * @override  _applyDrag 函数
 *    	 *******************  重写目的  ********************************************
	     * 拖拽当前组件时的范围限制top>=0、left>=0                                       *
	     * * *********************************************************************
 *       
 * 
 * 		 #########################################################################
 *       ###注意:  视情况而定你也可以更改                                                #
 *       #########################################################################
 * 
 */
(function($) {
	
	/**
	 * 功能：扩充 ligerDrag的配置项信息
	 */
	$.ligerDefaults.DragOptions = {
		isHidden :false // 修复了作者的一个bug
	};
	
	
	$.ligerMethos.Drag = {
		
		_applyDrag: function (applyResultBody){
            var g = this, p = this.options;
            applyResultBody = applyResultBody || g.proxy || g.target;
            var cur = {}, changed = false; //定义元素新位置坐标的变量  
            var noproxy = applyResultBody == g.target;
            if (g.current.diffX){
                if (noproxy || p.proxyX == null)
                    cur.left = g.current.left + g.current.diffX<0?0:g.current.left + g.current.diffX;//zhangyq TODO
                else
                    cur.left = g.current.startX + p.proxyX + g.current.diffX;
                changed = true;
            }
            if (g.current.diffY){
                if (noproxy || p.proxyY == null)
                    cur.top = g.current.top + g.current.diffY<0?0:cur.top = g.current.top + g.current.diffY;//zhangyq TODO
                else
                    cur.top = g.current.startY + p.proxyY + g.current.diffY;
                changed = true;
            }
            if (applyResultBody == g.target && g.proxy && p.animate){
                g.reverting = true;
                applyResultBody.animate(cur, function (){
                    g.reverting = false;
                });
            }else{
                applyResultBody.css(cur);
            }
        }
	
    };
	
})(jQuery);
