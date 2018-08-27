/**
 * 
 */
(function($) {
	$.fn.ligerXSFormCreate = function(options) {
		return $.ligerui.run.call(this, "ligerXSFormCreate", arguments);
	};

	$.fn.ligerGetXSFormCreateManager = function() {
		return $.ligerui.run.call(this, "ligerGetXSFormCreateManager", arguments);
	};

	$.ligerDefaults.XSFormCreate = {
		toolbar:{},
		from:{}//,
		//grid:{}
	};

	$.ligerMethos.XSFormCreate = $.ligerMethos.XSFormCreate || {};

	$.ligerui.controls.XSFormCreate = function(element, options) {
		$.ligerui.controls.XSFormCreate.base.constructor.call(this, element, options);
	};
	
	$.ligerui.controls.XSFormCreate.ligerExtend($.ligerui.controls.Template,{
		__getType : function() {
			return 'XSFormCreate';
		},
		__idPrev : function() {
			return 'XSFormCreate';
		},
		_extendMethods : function() {
			return $.ligerMethos.XSFormCreate;
		},
		_init : function() {
			 $.ligerui.controls.XSFormCreate.base._init.call(this);
			 var g = this ,p = g.options ;
			 var addClick  = null,addClickadd = null;
			 var fileElementId = 'doc'; // 文件上传表单默认的文件名
			 var fileUpload = p.form.fileUpload;
			 //如果 form中配置了 url 属性
			 if( p.form.url ){
				 var ligerForm = null, 
				 	 formElement = null ,
					 v = null;
				 var onSave = function(item){
				        //console.dir(item)
				        //ligerForm = g.getForm() ;  //old 
				        ligerForm = item.currentForm ;
				        formElement = ligerForm.element ;
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
								        submitData[item.name] = item.value || "";
								     }
								});
								return submitData;
							}else {
								return  $(formElement).formSerialize();
							}
						}else{
							return null;
						}
				 };//end of onSave  
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
					    var opt = item ;
						var data = onSave(opt);
						var successHandle = function(result) {
							if(result != null && result != ""){
							   tips = $.ligerDialog.tip({ title: '提示信息',modal: true, content: '保存成功！' });
								window.setTimeout(function(){ 
									tips.close();
									$.pap.removeTabItem() ;
								} ,2000);
							}
						}
				    	if(data != null ){
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
									data: data,
									beforeSend: function(){
										showMask();
									},
									success:function(result,textStatus){
										successHandle(result);
									},
									complete: function() {
										hideMask();
									}
								});
				    		}
			    		}//
				 } // end of addClick 
				 addClickadd = function(item){
					    var opt = item ;
						var data = onSave(opt);
						var successHandle = function(result) {
							if(result != null && result !=""){
								$.ligerDialog.success("操作成功！");
								// $(formElement).resetForm() ; old1 重置表单，隐藏域不会清空
								
								var excludeClearFields = ligerForm.options.excludeClearFields;
								if (!excludeClearFields) {
									excludeClearFields = true;
								}
								$(formElement).clearForm(excludeClearFields); // 重置表单，可配置清空，具体方法使用请参见jquery.form.js文件
								// 清空组合框选中样式
		                    	$.each(ligerForm.options.fields, function(index, field) {
		                    		if (field['type'] == "select") {
		                    			var fieldId = field['name'];
			                    		if (field.options['tree']) {
			                    			fieldId = field['comboboxName'];
				                    	}
			                    		var selectCombo = $("input[id='" + fieldId + "']");
		                    			if (selectCombo.val() == "") {
		                    				$(".l-selected", this.selectCombo).removeClass("l-selected");
		                    			}
		                    		}
		                    	});
							}
						}
				    	if(data != null ){
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
									success:function(result,textStatus){
										successHandle(result);
									},
									complete: function() {
										hideMask();
									}
								});
				    		}
			    		}
				 } // end of addClick 
				  // 配置 该模板页面的两个工具条按钮
				  if($.isEmptyObject(p.toolbar)){
					 p.toolbar = {items:[
						  { text: '保存', click: addClick , icon:'save' },
						  { text: '保存并继续', click: addClickadd ,icon:'saveandnext'}
					 ]};
				  }
			 }else{
				 alert("使用ligerXSFormCreate模板必须在form配置项中添加url属性");
				 throw new Error("使用ligerXSFormCreate模板必须在form配置项中添加url属性");
			 }
		},
		
		_render : function() {
			$.ligerui.controls.XSFormCreate.base._render.call(this);
		}
		 
   });

})(jQuery);