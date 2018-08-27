/**
 * 2013-7-12 10:30:01 做了一次修改
 * 
 */
(function($) {
	
	$.ligerui = $.ligerui ||{} ; //type,url, data, callback, dataType
	
	$.ligerui.ligerAjax =   function(cfg) {
			
			var _dataFilter = function(_data, _type){
					if(_data.indexOf('EXCEPTION') != -1){
                   	    var _dataTemp = $.parseJSON(  _data  );
				    	if(_dataTemp['MSG']){
				        	$.ligerDialog.errorTip(_dataTemp.EXCEPTION,_dataTemp.MSG);
				        }else
				        	$.ligerDialog.warn( _dataTemp.EXCEPTION) ;
				        _data="";
				     }else if (_data == "NOPASS") {
				         $.ligerDialog.warn("权限不足");
				         _data = "";
				     } 
                   //if(_type === 'json'){
			    	//    var _dataTemp = $.parseJSON(  _data  );
			    	//    if(_dataTemp['EXCEPTION']){
			    	//    	if(_dataTemp['MSG']){
			    	//    		$.ligerDialog.errorTip(_dataTemp.EXCEPTION,_dataTemp.MSG);
			    	//    	}else
			    	//    		$.ligerDialog.success( _dataTemp.EXCEPTION) ;
			    	//    	_data="";
			    	//	}
			    	//}else if(_type === 'text'){
			    	//if ( _data.indexOf('EXCEPTION') != -1 ){
			        // 	if( _data.indexOf('MSG') != -1){
			        // 		var exceArray = _data.split(",");
			        // 		var simpleResult,complexResult;
			        // 		if(exceArray[0].indexOf('EXCEPTION') != -1){
			        // 			simpleResult =exceArray[0].substring(exceArray[0].indexOf(":")+1).replace(/"/g, "");
			        // 			complexResult =exceArray[1].substring(exceArray[1].indexOf(":")+1).replace(/"/g, "").replace("}","");
			        // 		}else{
			        // 			simpleResult = exceArray[1].substring(exceArray[1].indexOf(":")+1).replace(/"/g, "").replace("}","");
			        // 			complexResult = exceArray[0].substring(exceArray[0].indexOf(":")+1).replace(/"/g, "");
			        // 		}
			        // 		$.ligerDialog.errorTip(simpleResult,complexResult);
			        // 	}else{
			        // 		var exceResult = _data.substring(_data.indexOf(":")+1).replace(/"/g, "").replace("}","");
			        //     	$.ligerDialog.warn(exceResult) ;
			        //    }
				    return  _data ;
			 };
			// 如果有请求中有参数 类型为 POST 
			cfg.type = cfg.data ?'POST':'GET';
			var ajaxOptions = {
				dataFilter: _dataFilter,
				/*beforeSend: function(){
					if ($("#loading")) {
						$("#loading").show();
					}
					if ($.ligerDialog) {
						$.ligerDialog.waitting('系统正在处理，请稍候......');
					}
				},*/
				error: function(xhr,status,errorThrown){
				    if(cfg.error){
				    	cfg.error(xhr,status,errorThrown);
				    	return ;
				    }else{
				    	if( $.ligerDialog ){
						   $.ligerDialog.error("提交操作异常！") ;
						}
					}
				}/*,
				complete: function() {
					if( $.ligerDialog ){
						$.ligerDialog.closeWaitting();
					}
					if ($("#loading")) {
						$("#loading").hide();
					}
					if (cfg.complete) {
						cfg.complete();
					}
				}*/
			};
			return $.ajax( $.extend({}, ajaxOptions, cfg) );
		};
	
})(jQuery);