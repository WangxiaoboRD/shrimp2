/*
 * ligerPopupEdit扩展组件：所谓的扩展组件就是对原来功能的补充和出现bug的修复。
 * 2013-11-26 16:20:30
 * mxq create
 * 
 */
(function($) {
	
	/**
	 * 功能：扩充 ligerPopupEdit的配置项信息
	 */
	$.ligerDefaults.PopupEditOptions = {
		initText: null,      // 初始化文本框
        initValue: null,     // 初始化隐藏域
        cascade: null       // 级联配置,目前只进行级联清空的处理，里面放置级联的htmldom的id，
        					//一个的话直接配置cascade: 'id',多个时用数组的方式：cascade:['id1', 'id2']
	};
	
	$.ligerMethos.PopupEdit = {
		/**
		 * 重写：PopupEdit的_render()方法
		 * 时间：2013-11-26
		 */
		 _render: function () {
		 	
            var g = this, p = this.options; 
            g.inputText = null;
            //文本框初始化
            if (this.element.tagName.toLowerCase() == "input") {
                this.element.readOnly = true;
                g.inputText = $(this.element);
                g.textFieldID = this.element.id;
            }
            if (g.inputText[0].name == undefined) g.inputText[0].name = g.textFieldID;
            //开关
            g.link = $('<div class="l-trigger"><div class="l-trigger-icon"></div></div>');
            //外层
            g.wrapper = g.inputText.wrap('<div class="l-text l-text-popup"></div>').parent();
            g.wrapper.append('<div class="l-text-l"></div><div class="l-text-r"></div>');
            g.wrapper.append(g.link);
             g.valueField = null;
              if (p.valueFieldID) {
            	 g.valueField = $("input[id='" + p.valueFieldID + "']");
            	 
            	  if (g.valueField.length == 0) {
            	    g.valueField = $('<input type="hidden"/>');
	                g.valueField[0].id = g.valueField[0].name = p.valueFieldID;
		            g.wrapper.append(g.valueField);
            	  }
              } else {
              	    g.valueField = $('<input type="hidden"/>');
                	g.valueField[0].id = g.valueField[0].name = g.textFieldID + "_val";
	                g.wrapper.append(g.valueField);
              }
            g.inputText.addClass("l-text-field");
            //开关 事件
            g.link.hover(function ()
            {
                if (p.disabled) return;
                this.className = "l-trigger-hover";
            }, function ()
            {
                if (p.disabled) return;
                this.className = "l-trigger";
            }).mousedown(function ()
            {
                if (p.disabled) return;
                this.className = "l-trigger-pressed";
            }).mouseup(function ()
            {
                if (p.disabled) return;
                this.className = "l-trigger-hover";
            }).click(function (){
                if (p.disabled) return;
                if (g.trigger('buttonClick') == false) return false;
            });
            g.inputText.click(function () {
                if (p.disabled) return; 
            }).blur(function () {
                if (p.disabled) return;
                g.wrapper.removeClass("l-text-focus");
            }).focus(function () {
                if (p.disabled) return;
                g.wrapper.addClass("l-text-focus");
            });
            g.wrapper.hover(function () {
                if (p.disabled) return;
                g.wrapper.addClass("l-text-over");
            }, function () {
                if (p.disabled) return;
                g.wrapper.removeClass("l-text-over");
            });

            // 弹出框内容初始化
            g.bulidContent();
            
            g.set(p);
		 },
		 
		// 内容初始化方法
        bulidContent: function() {
        	var g = this, p = this.options;
        	// 初始化隐藏域的值
        	if (p.initValue != null) {
        		g.valueField.val(p.initValue);
        	}
        	// 初始化文本框的值
        	if (p.initText != null) {
	            g.inputText.val(p.initText);
            }
        },
        
        /**
         * 重写：清空方法
         * 时间：2013-11-26
         */
        clear: function () {
            var g = this, p = this.options;
            if (p.cascade) {
	            var clearItems = p.cascade;
	            if (typeof(clearItems) == 'string') {
	            	$("input[id='" + clearItems + "']").val("");
	            } else if (typeof(clearItems) == 'object') {
	            	if (clearItems instanceof Array) {
	            		$.each(clearItems, function(i, item){
	            			$("input[id='" + item + "']").val("");
	            		});
	            	}
	            }
            }
            g.inputText.val("");
            g.valueField.val("");
        }
	};

})(jQuery);
