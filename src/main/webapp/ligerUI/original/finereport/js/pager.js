$(function(){
    // 鼠标悬停事件
	$('.l-toolbar-item').hover(function() {
		if ($(this).hasClass("l-toolbar-item-disable")) 
			return;
		$(this).addClass("l-panel-btn-over");
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
	    	// 上一页
	    	$(".pcontrol input").val(pageIndex);
	    	contentPane.gotoPreviousPage();

	    	if (pageIndex == 1) {// 若当前页==1时
	    		setDisabled(["first", "prev"]); // 禁用首页、上一页按钮
	    		setEnabled(["next", "last"]); // 启用末页、下一页按钮
		    }else if (pageIndex < totalPage && totalPage > 0 && pageIndex > 1) {
                setEnabled(["first", "prev", "next", "last"]); // 启用首页、上一页、末页、下一页按钮
            }
		}else if (btnid == 'nextPage') {
			// 下一页
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
		}
    });
});
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
