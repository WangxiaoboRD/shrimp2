/**
 * ligerDialog扩展组件：
 * @override  _render 函数
 *    	 *******************  重写目的  ******************************************* *
	     *  对对话框中使用url配置项时: 在window.top 中添加命名空间ligerFrame 此处是重点       *
	     *  window.top.ligerFrame =  window.top.ligerFrame ||{} ;		  *
	     *  window.top.ligerFrame[framename] = g.jiframe[0](当前Dialog对象中的iframe)*
	     *  打开一个对话框就会将该对话框IFrame保存到window.top.ligerFrame                  *
	     *  example:  如下实例使用的方法参考 pap.js 文件中定义
	     *  <pre>  打开对话框
	     *   $.ligerDialog.open({ 
	   	 			title:'选择耗材类型',
	   				url: 'consume_list.jsp',
	   				param:'你自己指定要传递参数值',
	   				height: 450,
	   				width: 650, 
	   				buttons: [ 
	   					{ text: '确定', onclick: f_importOK }, 
	   					{ text: '取消', onclick: f_importCancel}] 
   	         });</pre>
	     *   在对话框中获得传递值：
	     *     var currentPage = $.pap.getOpenPage(window);
  	   	 *	   var currentParam = currentPage.getParam("param")[0];
	     *        
	     * ***********************************************************************
	     * 对对话框中使用url配置项时: 为该对话框添加一个监听事件(loaded)如果你在打开对话框后dom加载完后  *
	     *                      可以添加监听函数onLoaded 函数参数为该对话框的 IFrame参数
	     * example：  老版本中经常使用该形式
	     *   <pre>
	     *   $.ligerDialog.open({ 
	   	 			title:'选择耗材类型',
	   				url: 'consume_list.jsp',
	   				onLoaded:function(param){ //兼容IE 和 FF
					     var documentF = param.contentDocument||param.document ;
					     // 隐藏工具条  
					     $('div.l-panel-topbar,div.l-toolbar',documentF).hide();
					},				 
	   				height: 450,
	   				width: 650, 
	   				buttons: [ 
	   					{ text: '确定', onclick: f_importOK }, 
	   					{ text: '取消', onclick: f_importCancel}] 
   	         });</pre>
	     * ***********************************************************************
 *       
 * 
 * 		 #########################################################################
 *       ###注意:  销毁函数你不需要关心，因此你可以尽情使用不需要注意太多#
 *       #########################################################################
 * 
 */
(function($) {
	 var l = $.ligerui;
	/**
	 * 功能：扩充 ligerDialog的配置项信息
	 */
	$.ligerDefaults.DialogOptions = {
		isHidden :false // 修复了作者的一个bug
	};
	
	$.ligerDialog.open = function(p) {
		if (!p.type) {
			var b="true";
			$.ligerui.ligerAjax({
				url:"user_auth_value!check",
				dataType:"text",
				data:{"url":p.url},
				async:false,
				success:function(_data,textStatus){
					b=_data;
				},
				error: function(XMLHttpRequest,textStatus){
				    $.ligerDialog.warn('错误代码执行！')
				},
				complete: function(){}
			});
			
			if(b=="false"){
				$.ligerDialog.warn("权限不足");
				return;
			}else{
				return $.ligerDialog(p);
			}
		}else {
			return $.ligerDialog(p);
		}
	};
	
	$.ligerMethos.Dialog = {
	
		_render: function (){
            var g = this, p = this.options;
            g.set(p, true); 
            var dialog = $('<div class="l-dialog"><table class="l-dialog-table" cellpadding="0" cellspacing="0" border="0"><tbody><tr><td class="l-dialog-tl"></td><td class="l-dialog-tc"><div class="l-dialog-tc-inner"><div class="l-dialog-icon"></div><div class="l-dialog-title"></div><div class="l-dialog-winbtns"><div class="l-dialog-winbtn l-dialog-close"></div></div></div></td><td class="l-dialog-tr"></td></tr><tr><td class="l-dialog-cl"></td><td class="l-dialog-cc"><div class="l-dialog-body"><div class="l-dialog-image"></div> <div class="l-dialog-content"></div><div class="l-dialog-buttons"><div class="l-dialog-buttons-inner"></div></div></div></td><td class="l-dialog-cr"></td></tr><tr><td class="l-dialog-bl"></td><td class="l-dialog-bc"></td><td class="l-dialog-br"></td></tr></tbody></table></div>');
            $('body').append(dialog);
            g.dialog = dialog;
            g.element = dialog[0];
            g.dialog.body = $(".l-dialog-body:first", g.dialog);
            g.dialog.header = $(".l-dialog-tc-inner:first", g.dialog);
            g.dialog.winbtns = $(".l-dialog-winbtns:first", g.dialog.header);
            g.dialog.buttons = $(".l-dialog-buttons:first", g.dialog);
            g.dialog.content = $(".l-dialog-content:first", g.dialog);
            g.set(p, false); 

            if (p.allowClose == false) $(".l-dialog-close", g.dialog).remove();
            // mxq 2014-07-04 add comment，将p.target去掉是想可以增加内容
            if (/*p.target || */p.url || p.type == "none"){
                p.type = null;
                g.dialog.addClass("l-dialog-win");
            }
            if (p.cls) g.dialog.addClass(p.cls);
            if (p.id) g.dialog.attr("id", p.id); 
            //设置锁定屏幕、拖动支持 和设置图片
            g.mask();
            if (p.isDrag)
                g._applyDrag();
            if (p.isResize)
                g._applyResize();
            
            if (p.type)
                g._setImage();
            else{
                $(".l-dialog-image", g.dialog).remove();
                g.dialog.content.addClass("l-dialog-content-noimage");
            }
            if (!p.show){
                g.unmask();
                g.dialog.hide();
            }
            //设置主体内容
            if (p.target){
            	// mxq 2014-07-04 add，为实现可以在弹出框中增加其他内容
            	if (p.type == 'errorTip') {
            		 g.dialog.buttons.after(p.target);
            	}else {
	                g.dialog.content.prepend(p.target);
            	}
                $(p.target).show();
            }else if (p.url){
                if (p.timeParmName){
                    p.url += p.url.indexOf('?') == -1 ? "?" : "&";
                    p.url += p.timeParmName + "=" + new Date().getTime();
                }
                if (p.load){
                    g.dialog.body.load(p.url, function (){
                        g._saveStatus();
                        g.trigger('loaded');
                    });
                }else{
                    g.jiframe = $("<iframe frameborder='0'></iframe>");
                    var framename = p.name ? p.name : "ligerwindow" + new Date().getTime();
                    g.jiframe.attr("name", framename);
                    g.jiframe.attr("id", framename);
                    g.dialog.content.prepend(g.jiframe); //g.dialog.content内容中的前部分<div target>就在此处添加 <div></div></div>
                    g.dialog.content.addClass("l-dialog-content-nopadding");
                    setTimeout(function (){
                        g.jiframe.attr("src", p.url);
                        //此处在 当前命名空间上添加一个  frame变量为以后使用提供方便  ！！！！ 通过此处我又学到了 window对象的知识！ TODO
                        g.frame = window.frames[g.jiframe.attr("name")];
                        window.top.ligerFrame =  window.top.ligerFrame ||{} ; 
            			window.top.ligerFrame[framename] = g.jiframe[0] ;
                        g.jiframe[0].ligerPage = g ;//zhangyq add XXX 
                    }, 0);// zhangyq add 2013-6-8 15:28:45
                    if (g.jiframe[0].readyState != "complete"){
                        g.dialog.content.prepend("<div class='l-tab-loading' style='display:block;'></div>");
                        var iframeloading = $(".l-tab-loading:first", g.dialog.content);
                        g.jiframe.bind('load.tab', function (){
                            iframeloading.hide();
                            g.trigger('loaded',[g.frame]); //zhangyq add
                        });
                    }//zhangyq add
                }
            }
            if (p.opener) g.dialog.opener = p.opener;
            //设置按钮
            if (p.buttons){
                $(p.buttons).each(function (i, item){
                    var btn = $('<div class="l-dialog-btn"><div class="l-dialog-btn-l"></div><div class="l-dialog-btn-r"></div><div class="l-dialog-btn-inner"></div></div>');
                    $(".l-dialog-btn-inner", btn).html(item.text);
                    $(".l-dialog-buttons-inner", g.dialog.buttons).prepend(btn);
                    item.width && btn.width(item.width);
                    item.onclick && btn.click(function () { item.onclick(item, g, i) });
                });
            } else{
                g.dialog.buttons.remove();
            }
            $(".l-dialog-buttons-inner", g.dialog.buttons).append("<div class='l-clear'></div>");

			// 以下定义不知道是做什么用的 ？？？？
            $(".l-dialog-title", g.dialog).bind("selectstart", function () { return false; });
            g.dialog.click(function (){
                l.win.setFront(g);
            });

            //设置事件
            $(".l-dialog-tc .l-dialog-close", g.dialog).click(function (){
                if (p.isHidden)
                    g.hide();
                else
                    g.close();
            });
            if (!p.fixedType){
                //位置初始化
                var left = 0;
                var top = 0;
                var width = p.width || g.dialog.width();
                if (p.slide == true) p.slide = 'fast';
                if (p.left != null) left = p.left;
                else p.left = left = 0.5 * ($(window).width() - width);
                if (p.top != null) top = p.top;										//获取匹配元素相对滚动条顶部的偏移
                else p.top = top = 0.5 * ($(window).height() - g.dialog.height()) + $(window).scrollTop() - 10;
                if (left < 0) p.left = left = 0;
                if (top < 0) p.top = top = 0;
                g.dialog.css({ left: left, top: top });
            }
            g.show();
            $('body').bind('keydown.dialog', function (e){
                var key = e.which;
                if (key == 13){
                    g.enter();
                }else if (key == 27){
                    g.esc();
                }
            });
            g._updateBtnsWidth();
            g._saveStatus();
            g._onReisze();
        },
        /**
         * @override 
         * 重写该方法是为了 将全局window的 ligerFrame 中的 p.name 的iframe 清空删除
         * 为的是释放类存防止内存泄露
         */
        doClose : function(){
            var g = this;
            if(g.options.url){
            	//zhangyq add  XXX
	            g.jiframe[0].ligerPage = null;
	            window.top.ligerFrame[g.options.name] = null ;
	            delete window.top.ligerFrame[g.options.name] ;
	            // end of zhangyq add 
            }
            l.win.removeTask(this);
            g.unmask();
            g._removeDialog();
            $('body').unbind('keydown.dialog');
        },
        
        _setImage : function() {
			var g = this, p = this.options;
			if (p.type) {
				if (p.type == 'success' || p.type == 'donne'
						|| p.type == 'ok') {
					$(".l-dialog-image", g.dialog).addClass(
							"l-dialog-image-donne").show();
					g.dialog.content.css( {
						paddingLeft : 64,
						paddingBottom : 30
					});
				} else if (p.type == 'error') {
					$(".l-dialog-image", g.dialog).addClass(
							"l-dialog-image-error").show();
					g.dialog.content.css( {
						paddingLeft : 64,
						paddingBottom : 30
					});
				} else if (p.type == 'warn') {
					$(".l-dialog-image", g.dialog).addClass(
							"l-dialog-image-warn").show();
					g.dialog.content.css( {
						paddingLeft : 64,
						paddingBottom : 30
					});
				} else if (p.type == 'question') {
					$(".l-dialog-image", g.dialog).addClass(
							"l-dialog-image-question").show();
					g.dialog.content.css({
						paddingLeft : 64,
						paddingBottom : 40
					});
					// 以下 mxq 2014-07-04 add
				} else if (p.type == 'errorTip') { 
					$(".l-dialog-image", g.dialog).addClass(
							"l-dialog-image-error").show();
					g.dialog.content.css({
						paddingLeft : 64,
						paddingBottom : 30
					});
				}
			}
		}
  };
  
  	/**
  	 * 系统异常提示框
  	 * @Param error-错误名称，errorDetails-错误详情，title-弹出框窗口名称
  	 */
  	$.ligerDialog.errorTip = function(error, errorDetails, title, callback) {
		error = error || "";
		errorDetails = errorDetails || "";
		if (typeof (title) == "function") {
			callback = title;
			type = null;
		} else if (typeof (type) == "function") {
			callback = type;
		}
		var btnclick = function(item, Dialog, index) {
			Dialog.close();
			if (callback)
				callback(item, Dialog, index);
		};
		
		var content = '<div id="errorDetails" style="display:none;padding: 10px;">' + errorDetails + '</div>';
		p = {
			type : 'errorTip',
			target: content,
			content: error,
			buttons : [ {                                                                                                                                                                                                                                                                                                                               
				text : '显示详情',
				onclick : function(item, Dialog, index) {
					$('#errorDetails').toggle();
				}
			}, {
				text : '确定',
				onclick : btnclick,
				type : 'ok'
			}]
		};
		
		if (typeof (title) == "string" && title != "")
			p.title = title;
		if (typeof (type) == "string" && type != "")
			p.type = type;
		$.extend(p, {
			showMax : false,
			showToggle : false,
			showMin : false,
			isResize: true,
			width: 400
		});
		return $.ligerDialog(p);
	};
	
})(jQuery);
