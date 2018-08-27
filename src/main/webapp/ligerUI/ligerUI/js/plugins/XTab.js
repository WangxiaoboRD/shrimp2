/**
 * @override   addTabItem 函数
 *    	 *******************  重写目的  ********************************************
	     * 和Dialog.js中的重写目的雷同虽然一个是对话框一个是Tab但是两者其实对象都是一个IFRAM        *                                       *
	     * * *********************************************************************
 *       
 * 
 *       ########################################################################
 *       ####example:  $.pap命名空间下的方法参考 pap.js 文件定义
 *       ####   打开一个页签：
 *       ###      $.pap.addTabItem({ 
 *   					text: '添加用户信息', // tab 头
 * 						url: 'user_add.jsp', // 页面定义
 * 						param:item.selectGrid.selected // 传递的参数 param
 *  			  });
 *       ###    user_add.jsp页面处理传递的参数：
 *       ###		 var currentPage = $.pap.getOpenPage(window);
  	                 var currentParam = currentPage.getParam("param");
 * 		 #####################################################################
 *       ########################################################################

 * 
 * 		 #########################################################################
 *       ###注意: Dialog.js中的注意雷同                                                 #
 *       #########################################################################
 * 
 */
(function($) {
	
	/**
	 * 功能：扩充 ligerTab的配置项信息
	 */
	$.ligerDefaults.TabOptions = {
		
	};
	
	$.ligerMethos.Tab = {
		//增加一个tab
        addTabItem: function (options){
            var g = this, p = this.options;
            // 此处为的是使用  系统提供getParam方法
            g.userParam = options ;// zhangyq add 2013-7-16 17:36:04
            if (g.trigger('beforeAddTabItem', [tabid]) == false)
                return false;
            var tabid = options.tabid;
            if (tabid == undefined) tabid = g.getNewTabid();
            var url = options.url;
            var content = options.content;
            var text = options.text;
            var showClose = options.showClose;
            var height = options.height;
            //如果已经存在
            if (g.isTabItemExist(tabid)){
                g.selectTabItem(tabid);
                return;
            }
            var tabitem = $("<li><a></a><div class='l-tab-links-item-left'></div><div class='l-tab-links-item-right'></div><div class='l-tab-links-item-close'></div></li>");
            var contentitem = $("<div class='l-tab-content-item'><div class='l-tab-loading' style='display:block;'></div><iframe frameborder='0'></iframe></div>");
            var iframeloading = $("div:first", contentitem);
            var iframe = $("iframe:first", contentitem);
            if (g.makeFullHeight){
                var newheight = g.tab.height() - g.tab.links.height();
                contentitem.height(newheight);
            }
            tabitem.attr("tabid", tabid);
            contentitem.attr("tabid", tabid);
            if (url){  
            	 /*$(contentitem).load(url,{height:p.height});*/
                 iframe.attr("name", tabid)
                 .attr("id", tabid)
                 .attr("src", url)
                 .bind('load.tab', function (){
                     iframeloading.hide();
                     if (options.callback)
                         options.callback();
                 });
                 // zhangyq add XXX
            	window.top.ligerFrame =  window.top.ligerFrame ||{} ; 
            	window.top.ligerFrame[tabid] = iframe[0] ;
            	iframe[0].ligerPageParam = options ; g.ligerPageParam = options ;// 此处仍然保留
            	iframe[0].ligerPage = g ;
            	//end of zhangyq 
            }else{
                iframe.remove(); 
                iframeloading.remove();
            }
            if (content){
                contentitem.html(content);
            }else if (options.target){
                contentitem.append(options.target);
            }
            if (showClose == undefined) showClose = true;
            if (showClose == false) $(".l-tab-links-item-close", tabitem).remove();
            if (text == undefined) text = tabid;
            if (height) contentitem.height(height);
            $("a", tabitem).text(text);

            g.tab.links.ul.append(tabitem);
            g.tab.content.append(contentitem);
            g.selectTabItem(tabid);
            if (g.setTabButton())
            {
                g.moveToLastTabItem();
            }
            //增加事件
            g._addTabItemEvent(tabitem);
            if (p.dragToMove && $.fn.ligerDrag)
            {
                g.drags = g.drags || [];
                tabitem.each(function ()
                {
                    g.drags.push(g._applyDrag(this));
                });
            }
            g.trigger('afterAddTabItem', [tabid]);
        },
        //移除tab项
        /**
         * @override 
         * 清楚全局window的ligerFrame对象中的 tabid 属性值
         * 目的：防止内存泄露
         */
        removeTabItem: function (tabid){
            var g = this, p = this.options;
            if (g.trigger('beforeRemoveTabItem', [tabid]) == false)
                return false;
            var currentIsSelected = $("li[tabid=" + tabid + "]", g.tab.links.ul).hasClass("l-selected");
            if (currentIsSelected)
            {
                $(".l-tab-content-item[tabid=" + tabid + "]", g.tab.content).prev().show();
                $("li[tabid=" + tabid + "]", g.tab.links.ul).prev().addClass("l-selected").siblings().removeClass("l-selected");
            }
            $(".l-tab-content-item[tabid=" + tabid + "]", g.tab.content).remove();
            $("li[tabid=" + tabid + "]", g.tab.links.ul).remove();
            g.setTabButton();
            g.trigger('afterRemoveTabItem', [tabid]);
            //zhangyq add 
            window.top.ligerFrame[tabid] = null ;
            delete window.top.ligerFrame[tabid] ;
            //end of zhangyq add 
        }
		
	
 };
})(jQuery);
