/**
 * 
 */
(function($) {
	$.fn.ligerXSFormModify = function(options) {
		return $.ligerui.run.call(this, "ligerXSFormModify", arguments);
	};

	$.fn.ligerGetXSFormModifyManager = function() {
		return $.ligerui.run.call(this, "ligerGetXSFormModifyManager", arguments);
	};

	$.ligerDefaults.XSFormModify = {
		toolbar:{},
		from:{}//,
		//grid:{}
	};

	$.ligerMethos.XSFormModify = $.ligerMethos.XSFormModify || {};

	$.ligerui.controls.XSFormModify = function(element, options) {
		$.ligerui.controls.XSFormModify.base.constructor.call(this, element, options);
	};
	
	$.ligerui.controls.XSFormModify.ligerExtend($.ligerui.controls.Template,{
			__getType : function() {
				return 'XSFormModify';
			},
			__idPrev : function() {
				return 'XSFormModify';
			},
			_extendMethods : function() {
				return $.ligerMethos.XSFormModify;
			},
			_init : function() {
				 $.ligerui.controls.XSFormModify.base._init.call(this);
				 var g = this ,p = g.options ;
				 var addClick  = function(item){ };
				 var fileElementId = 'doc'; // 文件上传表单默认的文件名
				 var fileUpload = p.form.fileUpload;
				 //如果 form中配置了 url 属性
				 if( p.form.url ){
				    var onSave = function(item){
				    	var ligerForm =  item.currentForm ;//g.getForm(), old 
				    		formElement = ligerForm.element ,
				    	    v = ligerForm.validateForm();
				  		$(formElement).submit();
						if( v.valid() ){
							if (fileUpload) {
								if (p.form.fileElementId) {
									fileElementId = p.form.fileElementId;
								}
								var submitData = {}; // 提交数据
								var formData = $(formElement).formToArray();
								$.each(formData, function(i, item){
								     if(item.name){
								        submitData[item.name ] = item.value || "";
								     }
								});
								return submitData;
							}else {
								return  $(formElement).formSerialize();
							}
						}else{
							return null;
						}
					}  	 
					 // 显示请求遮罩
					 var showMask = function() {
					 	if ($("#loading")) {
							$("#loading").show();
						}
						if ($.ligerDialog) {
							$.ligerDialog.waitting('系统正在处理，请稍候......');
						}
					 }
					 // 隐藏请求遮罩
					  var hideMask = function() {
					 	if( $.ligerDialog ){
							$.ligerDialog.closeWaitting();
						}
						if ($("#loading")) {
							$("#loading").hide();
						}
					 }
					 addClick = function(item){
						    var opt = item;
				    		var data = onSave(opt);
					    		// 请求成功后所做处理
					    		var successHandle = function(result) {
					    			if(result == 'MODIFYOK'){
										tips = $.ligerDialog.tip({ title: '提示信息',modal: true, content: '更新成功！' });
										window.setTimeout(function(){ 
											tips.close();
											$.pap.removeTabItem() ;
										} ,2000);
									}
					    		}
					    		if(data != null){
						    		if (fileUpload) {
						    			$.ajaxFileUpload({
								    		 url: p.form.url,
								    		 secureuri: false,
								    		 fileElementId: fileElementId,//input框的ID
								    		 dataType: "text",
											 data: data,
											 beforeSend: function(){
												showMask();
											 },
											 success: function(result, textStatus){
												successHandle(result);
											 },
											 complete: function() {
												hideMask();
											 }
							    		});
						    		}else {
										$.ligerui.ligerAjax({
											url: p.form.url,
											dataType: "text",
											data:data,
											beforeSend: function(){
												showMask();
											 },
											success:function(result, textStatus){
												successHandle(result);
											},
											 complete: function() {
												hideMask();
											 }
										});
						    		}
				    			}
					 	} // end of addClick 
					  p.toolbar = {items:[
						  { text: '保存', click: addClick ,icon:'save' }
					  ]};
				 }else{
					  alert("使用ligerXSFormModify模板必须在form配置项中添加url属性");
				 	throw new Error("使用ligerXSFormModify模板必须在form配置项中添加url属性");
				 }
				
			},
			
			_render : function() {
				$.ligerui.controls.XSFormModify.base._render.call(this);
			}
		 
   });

})(jQuery);