$(function(){
	
	// 全局Menu变量
	var actionMenu = null;
	var actionShadow = null;
	
    // 鼠标悬停事件
	$('.l-toolbar-item').hover(function() {
		if ($(this).hasClass("l-toolbar-item-disable")) 
			return;
		$(this).addClass("l-panel-btn-over");
		
		// 对下拉菜单按钮进行特殊处理
		var btnid = $(this).attr("id");
        if (!btnid) return;
		actionMenu && actionMenu.hide();
		actionShadow && actionShadow.hide();
        var left = $(this).offset().left;
        var top = $(this).offset().top + $(this).height();
        // 打印按钮
		if (btnid == 'printBtn') {
			 var menu = $(".l-print-menu");
			 var shadow = $(".l-print-menu-shadow");
             showMenu(menu, shadow, { top: top, left: left });
             actionMenu = menu;
             actionShadow = shadow;
			 menu.addClass("l-panel-btn-selected").siblings(".l-menu").removeClass("l-panel-btn-selected");
		}else if (btnid == 'outputBtn') {
			// 输出按钮
			 var menu = $(".l-output-menu");
			 var shadow = $(".l-output-menu-shadow");
             showMenu(menu, shadow, { top: top, left: left });
             actionMenu = menu;
             actionShadow = shadow;
             menu.addClass("l-panel-btn-selected").siblings(".l-menu").removeClass("l-panel-btn-selected");
		}
	}, function (){
		if ($(this).hasClass("l-toolbar-item-disable")) 
			return;
		$(this).removeClass("l-panel-btn-over");
	});

	// 按钮单击事件
	$('.l-toolbar-item').bind('click', function() {
		if ($(this).hasClass("l-toolbar-item-disable")) 
			return;
    	var btnid = $(this).attr("id");
        if (!btnid) return;
		var contentPane = document.getElementById('reportFrame').contentWindow.contentPane;
		var pageIndex = contentPane.currentPageIndex; // 当前页
		var totalPage = contentPane.reportTotalPage; // 总页数
		
		// 首页
    	if (btnid == 'firstPage') {
    		$(".pcontrol input").val(pageIndex);
    		contentPane.gotoFirstPage();
    		
    		setDisabled(["first", "prev"]);// 禁用首页、上一页按钮
    		setEnabled(["next", "last"]); // 启用末页、下一页按钮
    		
	    }else if (btnid == 'prevPage') {
	    	//上一页
	    	$(".pcontrol input").val(pageIndex);
	    	contentPane.gotoPreviousPage();

	    	if (pageIndex == 1) {// 若当前页==1时
	    		setDisabled(["first", "prev"]); // 禁用首页、上一页按钮
	    		setEnabled(["next", "last"]); // 启用末页、下一页按钮
		    }else if (pageIndex < totalPage && totalPage > 0 && pageIndex > 1) {
                setEnabled(["first", "prev", "next", "last"]); // 启用首页、上一页、末页、下一页按钮
            }
		}else if (btnid == 'nextPage') {
			//下一页
			$(".pcontrol input").val(pageIndex);
			contentPane.gotoNextPage();
			
            if (pageIndex == totalPage) {//当当前页==总页数时
                setDisabled(["next", "last"]); // 禁用末页、下一页按钮
                setEnabled(["first", "prev"]); // 启用首页、上一页按钮
            }else if (pageIndex < totalPage && totalPage > 0) {
                setEnabled(["first", "prev", "next", "last"]); // 启用首页、上一页、末页、下一页按钮
            }
		}else if (btnid == 'lastPage') {
			// 末页
			$(".pcontrol input").val(pageIndex);
			contentPane.gotoLastPage();

			setDisabled(["next", "last"]);// 禁用末页、下一页按钮
			setEnabled(["first", "prev"]); // 启用首页、上一页按钮
		}else if (btnid == 'pdfPrintBtn') {
			//Flash客户端打印
			contentPane.flashPrint();
		}else if (btnid == 'emailBtn') {
			//邮件
			contentPane.emailReport();
		}
    });
   
    // 下拉菜单项的单击事件
    $('.l-menu-item').bind('click', function() {
    	if ($(this).hasClass("l-menu-item-disable")) 
			return;
    	var menuid = $(this).attr("id");
        if (!menuid) return;
		var contentPane = document.getElementById('reportFrame').contentWindow.contentPane;
		// PDF客户端打印
		if (menuid == 'pdfPrint') { 
			contentPane.pdfPrint();
		}else if (menuid == 'appletPrint') { 
			//Applet打印
			contentPane.appletPrint();
		}else if (menuid == 'flashPrint') { 
			//Flash客户端打印
			contentPane.flashPrint();
		}else if (menuid == 'pdf') { 
			//PDF导出
			contentPane.exportReportToPDF();
		}else if (menuid == 'excelPage') { 
			//Excel分页导出
			contentPane.exportReportToExcel('page');
		}else if (menuid == 'excelSimple') { 
			//Excel原样导出
			contentPane.exportReportToExcel('simple');
		}else if (menuid == 'excelSheet') { 
			//Excel分页分sheet导出
			contentPane.exportReportToExcel('sheet');
		}else if (menuid == 'word') { 
			//word导出
			contentPane.exportReportToWord();
		}
    });
    
    // 页面绑定单击菜单事件
    $(document).bind('click.menu', function (){
    	var menus = $('.l-menu');
    	if (menus.is(":visible"))
        	menus.hide();
        	
        var shadows = $('.l-menu-shadow');
        if (shadows.is(":visible"))
        	shadows.hide();
    });
    
    // 下拉菜单悬停事件
    $('.l-menu-item').hover(function() {
		var menu = $(".l-panel-btn-selected");
	    var menuover = $("> .l-menu-over:first", menu);
    	if ($(this).hasClass("l-menu-item-disable")) return;
    	 var itemtop = $(this).offset().top;
         var top = itemtop - menu.offset().top;
         menuover.css({ top: top });
    });
    
     // 下拉菜单悬停消失事件
     $('.l-menu').hover(null, function (){
    	if ($(this).is(":visible"))
        	$(this).hide();
        	
        var shadows = $('.l-menu-shadow');
        if (shadows.is(":visible"))
        	shadows.hide();   
     });
});

// 显示子菜单
function showMenu(menu, shadow, options) {
    if (options && options.left != undefined)
    {
        menu.css({ left: options.left });
    }
    if (options && options.top != undefined)
    {
        menu.css({ top: options.top });
    }
    menu.show();
    updateShadow(menu, shadow);
}

// 更新菜单阴影、遮罩
function updateShadow(menu, shadow) {
    shadow.css({
        left: menu.css('left'),
        top: menu.css('top'),
        width: menu.outerWidth(),
        height: menu.outerHeight()
    });
    if (menu.is(":visible"))
        shadow.show();
    else
        shadow.hide();
}

// 隐藏子菜单
function hide(menu, shadow) {
    menu.hide();
    updateShadow(menu, shadow);
}

// 页面加载后事件
function afterload(){
   var contentPane = document.getElementById('reportFrame').contentWindow.contentPane;
   contentPane.on("afterload", function(){ 
	   var pageIndex = contentPane.currentPageIndex; // 当前页
	   var totalPage = contentPane.reportTotalPage; // 总页数
       $(".pcontrol input").val(pageIndex);
       $(".pcontrol span").html(totalPage);
       if (totalPage && (totalPage > 1 && pageIndex < totalPage)) {
	       setEnabled(["last", "next"]); // 启用末页、下一页按钮
       }
    }); 
}

/* 按钮禁用
* btnArr：按钮名称数组
* 首页：first，上一页：prev，下一页：next，末页：last
*/
function setDisabled(btnArr) {
	$.each(btnArr, function(index, btn) {
    	if ($(".l-btn" + btn).hasClass("l-panel-btn-over")) {
    		$(".l-btn" + btn).removeClass("l-panel-btn-over")
        }
	   $(".l-btn" + btn).addClass("l-toolbar-item-disable");
       $(".l-btn" + btn + " .l-icon").removeClass("l-icon-page-" + btn);
       $(".l-btn" + btn + " .l-icon").addClass("l-icon-page-first-disabled");
    });
}

/* 按钮可用
* btnArr：按钮名称数组
* 首页：first，上一页：prev，下一页：next，末页：last
*/
function setEnabled(btnArr) {
	$.each(btnArr, function(index, btn) {
	   $(".l-btn" + btn).removeClass("l-toolbar-item-disable");
       $(".l-btn" + btn + " .l-icon").addClass("l-icon-page-" + btn);
       $(".l-btn" + btn + " .l-icon").removeClass("l-icon-page-first-disabled");
    });
}
