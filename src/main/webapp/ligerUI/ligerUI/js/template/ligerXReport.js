/**
 * jQuery ligerUI 1.2.0
 * 
 * http://ligerui.com
 *  
 * Author daomi 2013 [ gd_star@163.com ] 
 * 
 */
(function($) {
	$.fn.ligerXReport = function(options) {
		return $.ligerui.run.call(this, "ligerXReport", arguments);
	};

	$.fn.ligerGetXReportManager = function() {
		return $.ligerui.run.call(this, "ligerGetXReportManager", arguments);
	};

	$.ligerDefaults.XReport = {
		report:{}
	};

	$.ligerMethos.XReport = $.ligerMethos.XReport || {};

	$.ligerui.controls.XReport = function(element, options) {
		$.ligerui.controls.XReport.base.constructor.call(this, element, options);
	};
	
	$.ligerui.controls.XReport.ligerExtend($.ligerui.controls.ReportTemp,{
			__getType : function() {
				return 'XReport';
			},
			__idPrev : function() {
				return 'XReport';
			},
			_extendMethods : function() {
				return $.ligerMethos.XReport;
			},
			_init: function() {
				 $.ligerui.controls.XReport.base._init.call(this);
			},
			_render: function() {
				$.ligerui.controls.XReport.base._render.call(this);
			},
			createReport: function(reportOptions, dom){
			 	var g = this ,p = this.options, rp = reportOptions;
			 	if (!rp.url) {
			 		alert("对不起，您未指定报表的url路径，报表加载失败！");
			        throw new Error("对不起，您未指定报表的url路径，报表加载失败！");
			 	}
				var targetTag = ['<iframe id="reportFrame" name="reportFrame" frameborder="0" marginwidth="0" marginheight="0" '];
				var width = "100%";
				var scrolling = "anto";
				if (rp) {
					var reportURL = "../ReportServer?reportlet=" + rp.url;
					// 是否分页，默认为分页true
                	if (rp.usePager != undefined && !rp.usePager) {
                		reportURL += "&op=view";
                	} 
                	// 是否显示工具条，默认为显示true
                	if (rp.showToolbar != undefined && !rp.showToolbar) {
                		reportURL += "&__showtoolbar__=false";
                	}
                	reportURL = 'src="' + reportURL + '"';
                	targetTag.push(reportURL);
					if (rp.width) {
						width = rp.width;
					}
					if (rp.height) {
						targetTag.push(" height=");
						targetTag.push(rp.height)
					}
					if (rp.scrolling) {
						scrolling = rp.scrolling;
					}
				}
				targetTag.push(" width=");
				targetTag.push(width)
				var scrollStr = ' scrolling="' + scrolling + '"';
				targetTag.push(scrollStr);
				targetTag.push("/>");
	    		if(arguments[1]){
	    			$(dom).append( targetTag.join(''));
	    		}else{
	    			$('body').append(targetTag.join(''));
	    		}
		 }
   });

})(jQuery);