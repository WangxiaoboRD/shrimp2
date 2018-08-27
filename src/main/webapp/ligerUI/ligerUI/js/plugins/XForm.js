/**
* jQuery ligerUI 1.2.0
* 
* http://ligerui.com
*  
* Author daomi 2013 [ gd_star@163.com ] 
* 1.对form功能的扩展
*  //zhangyq add 2013-7-2 11:37:12
        var parentElement = jinput.parent('.l-fieldcontainer');
        if(parentElement.length > 0 ){
        	options['parentElement'] = parentElement.first();
        }
   //end of zhangyq add 2013-7-2 11:37:19 
   在 base.js中添加了一个 setVisible方法 对其对象算是一个使用了。
  2. 舍弃 老ligerForm 使用新的自定义的ligerForm 
  3.需注意的地方：针对验证框架中combobox或popup组件在条件是不验证问题的原因在：
  	没有给这两个组件配置comboboxName和comboboxNameSubmit: true这两个配置，
  	因为在jquery.validate.js中是搜寻input、select、textarea组件并获取其名称进行添加到验证框架中的
  	而没有设置以上两个配置，在生成的组件中没有name配置项，所有在没有填写必填项时提交是不会校验的。
*/
(function ($){

    $.fn.ligerForm = function (){
        return $.ligerui.run.call(this, "ligerForm", arguments);
    };

    $.ligerui.getConditions = function (form){
        if (!form) return null;
        var conditions = [];  //筛选出与指定表达式匹配的元素集合
        $(":input", form).filter(".condition,.field").each(function (){
            var value = $(this).val() || $(this).attr("value");
            if (!this.name || !value) return;
            conditions.push({
                op: $(this).attr("op") || "like",
                field: this.name,
                value: value,
                type: $(this).attr("vt") || "string"
            });
        });
        return conditions;
    };

    $.ligerDefaults = $.ligerDefaults || {};
    
    $.ligerDefaults.Form = {
        //控件宽度
        inputWidth: 180,
        //标签宽度
        labelWidth: 90,
        //间隔宽度
        space: 40,
        rightToken: '：',
        //标签对齐方式
        labelAlign: 'left',
        //控件对齐方式
        align: 'left',
        //字段
        fields: [],
        //创建的表单元素是否附加ID
        appendID: true,
        //生成表单元素ID的前缀
        prefixID: "",
        //json解析函数
        toJSON: $.ligerui.toJSON,
        labelCss: null,
        fieldCss: null,
        spaceCss: null,
        onAfterSetFields: null,
        buttons: null,              //按钮组
        readonly:false,              //是否只读
        excludeClearFields: null,      // 排除清空字段，单个字段不清空的配置：excludeClearFields: 'input框name值'，若是多个文件则以数组方式配置，如：excludeClearFields: ['name1', 'name2']
        editors: {},              //编辑器集合,使用同$.ligerDefaults.Grid.editors
        validate: null,           //验证
        tab: null,
        clsTab: 'ui-tabs-nav ui-helper-clearfix',
        clsTabItem: 'ui-state-default',
        clsTabItemSelected: 'ui-tabs-selected',
        clsTabContent: 'ui-tabs-panel ui-widget-content'
        //fileUpload: false,        // 文件上传标识 2013-12-11添加
        //fileElementId: 'doc'    // 上传文件选择框name，默认为doc，与框架后台BaseAction定义的文件类型保持一致
        						// 该配置需配合fileUpload使用，只有fileUpload为true时才生效
        						// 单个文件上传配置：fileElementId: 'input框name值'，若是多个文件则以数组方式配置，如：fileElementId: ['doc1', 'doc2']
    };
	$.ligerMethos.Form = $.ligerMethos.Form ||{};
	
    $.ligerDefaults.Form_fields = {
        name: null,             //字段name
        textField: null,       //文本框name
        type: null,             //表单类型
        editor: null,           //编辑器扩展类型
        label: null,            //Label
        newline: true,          //换行显示
        op: null,               //操作符 附加到input
        vt: null,               //值类型 附加到input
        attr: null,             //属性列表 附加到input
        style : null,
        rightToken: null,    
        containerCls : null,
        attrRender: null,
        labelInAfter: false,  //label显示在后面
        afterContent: null,  //后置内容
        beforeContent : null, //前置内容
        hideSpace: false,       //隐藏空格
        hideLabel: false,       //隐藏标签
        validate: null,         //验证参数，比如required:true
        hidden: false           //隐藏参数配置，默认为false，不进行隐藏
    };

    $.ligerDefaults.Form_editor = {
        textFieldName: null    //文本框name 
    };

    $.ligerDefaults.Form.editorBulider = function (jinput, fields){
        var g = this, p = this.options;
        var options = {}, field = null;
        //zhangyq add 2013-7-2 11:37:12
        var parentElement = jinput.parents('.l-fieldcontainer');
        if(parentElement.length > 0 ){
        	options['parentElement'] = parentElement.first();
        }
        //end of zhangyq add 2013-7-2 11:37:19
        var fieldIndex = jinput.attr("fieldindex"), ltype = jinput.attr("ltype");
        if (fieldIndex != null){
        	// 2016-09-22 mxq update
        	if (fields) {
        		field = fields[fieldIndex];
        	}
            if (field && g.editors && g.editors[field.type]){ 
                g.editors[field.type].call(g, jinput, field);
                return;
            }
        }
        field = field || {};
        if (p.readonly) options.readonly = true;
        options = $.extend({
               width: (field.width || p.inputWidth) - 2
        	}, field.options, field.editor, options);
        if (ltype == "autocomplete")
            options.autocomplete = true;
        
            
        if (jinput.is("select")){// 使用form自动创建的表单是不会走这一步的。
            jinput.ligerComboBox(options);
        }
        else if (jinput.is(":password")){
            jinput.ligerTextBox(options);
        }else if (jinput.is(":text")){
        	   switch (ltype){//当心 case后没有break的语句呀 TODO
        	        case "label": //mxq add  2013-12-30
        	        	jinput.ligerLabel(options);
        	            break; 
        	        case "displayfield": //zhangyq add  2013-7-19 17:48:51
        	        	jinput.ligerDisplayField(options);
        	            break; 
	                case "spinner":
	                    jinput.ligerSpinner(options);
	                    break;
	                case "date":
	                    jinput.ligerDateEditor(options);
	                    break;
	                case "currency":
	                    options.currency = true;
	                case "float":
	                case "number":
	                    options.number = true;
	                    jinput.ligerTextBox(options);
	                    break;
	                case "int":
	                case "digits":
	                    options.digits = true;
	                default:
	                    jinput.ligerTextBox(options);
	                    break;
	           }//end of switch
        }else if (jinput.is(":hidden")){ //此处是自动创建表格 其它7种类型渲染的关键步骤  TODO
                if ($.inArray(ltype, ["select", "combobox", "autocomplete", "popup"]) != -1){
                    if (!jinput.attr("name")) jinput.attr("name", liger.getId('hidden'));
                    options.valueFieldID = jinput.attr("name");//options.valueFieldID|| 这一步也是关键的步骤 TODO  改为 attr('name') 是不是更好?? 以前是id 我现在已经更改了 有问题再处理吧
                
	        		var textField = field.textField || field.comboboxName || liger.getId();
	                var textInput = $("[id='" + textField + "']", g.element);
	                if (!textInput.length)
	                    textInput = $("<input type='text' id='" + textField + "' />").insertAfter(jinput);
	                if(field.comboboxName && field.comboboxNameSubmit){
	                	//给  textInput 设置name属性值方便提交该值  TODO 2013-05-31 
	                	textInput.attr("name",field.comboboxName);
	                }
	                if (p.appendID && !textInput.attr("id") )
	                    textInput.attr("id", textField);
	                if(field.validate){
	                	textInput.attr('validate',p.toJSON(field.validate));
	                }
	                if(ltype == "popup")
	                    textInput.ligerPopupEdit(options);
	                else
	                    textInput.ligerComboBox(options);
	           }
                
        }else if (jinput.is(":radio")){ // zhangyq update TODO
            jinput.ligerRadioBox(options);
        }else if (jinput.is(":checkbox")){// zhangyq update TODO
            jinput.ligerCheck(options);
        }else if (jinput.is("textarea")){
            jinput.addClass("l-textarea");
            jinput.width(options.width);//zhangyq update TODO 
            jinput.height(options.height);
            if(options.value)
            	jinput.text(options.value)
        }else if(jinput.is('div')){ // 使用 radiogroup checkboxgroup
        	if(ltype == 'checkboxgroup'){
        		jinput.ligerCheckBoxGroup(options);
        	}
        	if(ltype == 'radiogroup'){
        		jinput.ligerRadioGroup(options);
        	}
        	if(ltype == 'button'){// button 按钮 mxq 2016-04-08 add
        		jinput.ligerButton(options);
        	}
        }
    }

    //表单组件
    $.ligerui.controls.Form = function (element, options){
        $.ligerui.controls.Form.base.constructor.call(this, element, options);
    };

    $.ligerui.controls.Form.ligerExtend($.ligerui.core.UIComponent, {
        __getType: function ()
        {
            return 'Form'
        },
        __idPrev: function ()
        {
            return 'Form';
        },
        _extendMethods : function() {
			return  $.ligerMethos.Form;
		},
        _init: function ()
        {
            var g = this, p = this.options;
            $.ligerui.controls.Form.base._init.call(this);
            //编辑构造器初始化
            for (var type in liger.editors)
            {
                var editor = liger.editors[type];
                //如果没有默认的或者已经定义
                if (!editor || type in p.editors) continue;
                p.editors[type] = liger.getEditor($.extend({
                    type: type,
                    master: g
                }, editor));
            }
        },
        _render: function (){
            var g = this, p = this.options;
            var jform = $(this.element);
            //生成ligerui表单样式
            $("input,select,textarea", jform).each(function (){
                p.editorBulider.call(g, $(this));
            });
            g.set(p);
            // mxq 2014-04-07 update start
            if (p.buttons){
            	var lastBtnContainer = $('.l-form-container > ul:last'); // 最后一次按钮容器
                $(p.buttons).each(function (){
                	var jbutton;
                	if (this.newline == undefined && !this.newline) {
	                    jbutton = $('<li style="margin-right:10px;"><div></div></li>').appendTo(lastBtnContainer);
                	}else {
                		// 若newline为true则进行换行
		                var jbuttons = $('.l-form-container > ul:last').after('<ul class="l-form-buttons"></ul>');
		                lastBtnContainer = $('.l-form-container > ul:last');
                		jbutton = $('<li style="margin-right:10px;"><div></div></li>').appendTo(lastBtnContainer);
                	}
                    $("div:first", jbutton).ligerButton(this);
                });
            } // mxq 2014-04-07 update end
        },
        getField: function (index){
            var g = this, p = this.options;
            if (!p.fields) return null;
            return p.fields[index];
        },
        toConditions: function ()
        {
            return $.ligerui.getConditions(this.element);
        },
        //预处理字段 , 排序和分组。给每一个配置项的field 添加 group、groupicon属性
        _preSetFields: function (fields){
            var g = this, p = this.options, lastVisitedGroup = null, lastVisitedGroupIcon = null;
            //分组： 先填充没有设置分组的字段，然后按照分组排序
            $(p.fields).each(function (i, field){
            	if (p.readonly || field.readonly || (field.editor && field.editor.readonly))
                {
                    //if (!field.editor) field.editor = {};
                    //field.editor.readonly = field.editor.readonly || true;
                    delete field.validate;
                }
                if (field.type == "hidden") return; 
                if (field.newline == null || field.newline==undefined) 
                	field.newline = false;
                if (lastVisitedGroup && !field.group){
                    field.group = lastVisitedGroup;
                    field.groupicon = lastVisitedGroupIcon;
                }
                if (field.group){
                    //trim
                    field.group = field.group.toString().replace(/^\s\s*/, '' ).replace(/\s\s*$/, '' );
                    lastVisitedGroup = field.group;
                    lastVisitedGroupIcon = field.groupicon;
                }
            }); 
        },
        validateForm : function() {
        	var g = this, p = this.options;
        	var jform = $(this.element);
        	$.metadata.setType("attr", "validate"); //此句话不能少
        	return	jform.validate({
	              success: function (lable) {
        		      // alert("input[id='"+lable.attr("for")+"'],input[name='"+lable.attr("for")+"']");
	                   var element = $("input[id='"+lable.attr("for")+"'],input[name='"+lable.attr("for")+"']");
	                   var nextCell = element.parents("li:first").next("li");
	                   if (element.hasClass("l-textarea")) {
	                       element.removeClass("l-textarea-invalid");
	                   }else if (element.hasClass("l-text-field")) {
	                       element.parent().removeClass("l-text-invalid");
	                   }
	                   nextCell.children("div").removeClass('l-exclamation').addClass('l-validated-correct');
	              },
	           	  errorPlacement: function (lable, element) {
	            	  //alert("input[id= "+lable.attr("for")+" ");
	           	      var element = $("input[id='"+lable.attr("for")+"'],input[name='"+lable.attr("for")+"']");
	                  if (element.hasClass("l-textarea")) {
	                      element.addClass("l-textarea-invalid");
	                  } else if (element.hasClass("l-text-field")) {
	                      element.parent().addClass("l-text-invalid");
	                  }
	                  var nextCell = element.parents("li:first").next("li");
	                  nextCell.empty();//nextCell.find("div.l-exclamation").remove();//增强的函数
	                  $('<div class="l-exclamation" title="' + lable.html() + '"></div>').appendTo(nextCell).ligerTip(); 
	               },
	               submitHandler:function(form){
	               		//alert("成功！");
	    	 	   }
            }); 
        },
        _setFields: function (fields){
        	var g = this, p = this.options; 
            if ($.isFunction(p.prefixID)) p.prefixID = p.prefixID(g);
            var jform = $(g.element).addClass("l-form"); 
            g._initFieldsHtml({
                panel: jform,
                fields: fields
            }); 
            g.trigger('afterSetFields');
        },
        //标签部分
        _buliderLabelContainer: function (field){
            var g = this, p = this.options;
            var label = field.label || field.display;
            var labelWidth = field.labelWidth || field.labelwidth || p.labelWidth;
            var labelAlign = field.labelAlign || p.labelAlign;
            
            // mxq add start 2013-12-29 
            var type = field.type;
            var fname = field.name;
            var out = [];
            if (type == 'label') {
            	out.push('<li');
            	out.push(' style="');
	            if (labelAlign){
	                out.push('text-align:' + labelAlign + ';');
	            }
            	out.push('">');
            	//out.push('<label');
            	out.push('<span');
            	if (fname) {
            		//out.push(' for=');
            		out.push(' id=');
            		out.push(fname);
            	}
            	out.push('>');
            	
            	if (label) {
            		out.push(label);
            	}
            	//out.push('</label>');
            	out.push('</span>');
            	out.push('</li>');
            	return out.join('');
            }
            // mxq add end 2013-12-29 
            
            if (label) label += p.rightToken;
            out = [];
            out.push('<li');
            
        	 if (p.labelCss){
                out.push(' class="' + p.labelCss+' ' );
                if(field.labelCss){
                	out.push(field.labelCss+'"') ;
                }else{
                	out.push('"') ;
                }
            }else{
            	if(field.labelCss){
                	out.push(' class="' + field.labelCss+'"' );
                }
            }
            
            out.push(' style="');
             if (/px$/i.test(labelWidth) || /auto/i.test(labelWidth) || /%$/i.test(labelWidth))
            {
                out.push('width:' + labelWidth + ';');
            }
            else if (labelWidth)
            {
                out.push('width:' + labelWidth + 'px;');
            }
            if (labelAlign && labelAlign != "top"){
                out.push('text-align:' + labelAlign + ';');
            }
            out.push('">');
            if (label){
                out.push(label);
            }
            if (field.validate && field.validate.required){
                out.push("<span class='l-star'>*</span>");
            }
            out.push('</li>');
            return out.join('');
        },
        //控件部分
        _buliderControlContainer: function (field, fieldIndex, idPrev){
            var g = this, p = this.options;
            var width = field.width || p.inputWidth;
            var height = field.height || p.height;
            var align = field.align || field.textAlign || field.textalign || p.align;
            var out = [];
            idPrev = idPrev || g.id;
            var labelAlign = field.labelAlign || p.labelAlign;
            out.push('<li');
            out.push(' id="' + (idPrev + "|" + fieldIndex) + '"');
        	if (p.fieldCss){
                out.push(' class="' + p.fieldCss+' ' );
                if(field.fieldCss){
                	out.push(field.fieldCss+'"') ;
                }else{
                	out.push('"') ;
                }
            }else{
            	if(field.fieldCss){
                	out.push(' class="' + field.fieldCss+'"' );
                }
            }
            out.push(' style="');
           if (/px$/i.test(width) || /auto/i.test(width) || /%$/i.test(width))
            {
                out.push('width:' + width + ';');
            }
            else if (width)
            {
                out.push('width:' + width + 'px;');
            }
            if (height){
                out.push('height:' + height + 'px;');
            }
            if (align){
                out.push('text-align:' + align + ';');
            }
            if (labelAlign == "top")
            {
                out.push('clear:both;');
            }
            out.push('">');
            out.push(g._buliderControl(field, fieldIndex));
            out.push('</li>');
            return out.join('');
        },
        //间隔部分
        _buliderSpaceContainer: function (field){
            var g = this, p = this.options;
            var spaceWidth = field.space || field.spaceWidth || p.space;
            var out = [];
            out.push('<li');
            if (p.spaceCss){
                out.push(' class="' + p.spaceCss + '"');
            }
            out.push(' style="');
           if (/px$/i.test(spaceWidth) || /auto/i.test(spaceWidth) || /%$/i.test(spaceWidth))
            {
                out.push('width:' + spaceWidth + ';');
            }
            if (spaceWidth)
            {
                out.push('width:' + spaceWidth + 'px;');
            }
            out.push('">'); 
            out.push('</li>');
            return out.join('');
        },
        // zhangyq add stype attribute
        _buliderControl: function (field, fieldIndex){
            var g = this, p = this.options;
            var width = field.width || p.inputWidth,
            name = field.name || field.id|| liger.getId("autoForm"),
            type = (field.type || "text").toLowerCase(),
            readonly = (field.readonly || (field.editor && field.editor.readonly)) ? true : false;
            var out = [];   
            if (type == "textarea" || type == "htmleditor"){ 
                out.push('<textarea '); 
                if (readonly) 
                	out.push('readonly=true '); 
            }else if ($.inArray(type, ["checkbox", "radio", "password", "file"]) != -1){
                out.push('<input type="' + type + '" ');
            }// zhangyq add 2013-6-28 17:02:57
            else if($.inArray(type, ["radiogroup", "checkboxgroup", "button"]) != -1){
            	out.push('<div ');
            }/* 
            else if ($.inArray(type, ["radiolist", "checkboxlist", "listbox"]) != -1){
                out.push('<input type="hidden" ');
            }*/
            else if($.inArray(type, ["select", "combobox", "autocomplete", "popup"]) != -1){
            	//out.push('<input type="text" stype="stype" style="display:none" ');
            	out.push('<input type="hidden"  ');
            }else if ($.inArray(type, ["hidden"]) != -1) {
            	if (field.options) {
	            	out.push('<input type="hidden" value="'+field.options.value +'" ');
            	}else {
            		out.push('<input type="hidden" ');
            	}
            }else if ($.inArray(type, ["image"]) != -1) {
            	var height = field.height || p.height;
            	out.push('<input type="' + type + '" ');
	            out.push(' style="');
	            if (width){
	                out.push('width:' + width + 'px;');
	            }
	            if (height){
	                out.push('height:' + height + 'px;');
	            }
	            out.push('" ');
            }else{
                out.push('<input type="text" ');
            }
            out.push('name="' + name + '" ');
            out.push('fieldindex="' + fieldIndex + '" '); 
            field.cssClass && out.push('class="' + field.cssClass + '" ');
            p.appendID  && out.push(' id="' + name + '" '); 
            out.push(g._getInputAttrHtml(field));// 添加 ltype 属性值
            //zhangyq add if 语句
            if($.inArray(type, ["textarea", "select", "combobox", "autocomplete", "popup","radiogroup", "checkboxgroup"]) == -1){
            	if (field.validate && !readonly){
                out.push(" validate='" + p.toJSON(field.validate) + "' ");
                g.validate = g.validate || {};
                g.validate.rules = g.validate.rules || {};
                g.validate.rules[name] = field.validate;
                if (field.validateMessage){
                    g.validate.messages = g.validate.messages || {};
                    g.validate.messages[name] = field.validateMessage;
                }
              }
            }
            out.push(' />');
            return out.join('');
        },
        _getInputAttrHtml: function (field){
            var out = [], type = (field.type || "text").toLowerCase();
            if (type == "textarea"){
                field.cols && out.push('cols="' + field.cols + '" ');
                field.rows && out.push('rows="' + field.rows + '" ');
            }
            out.push('ltype="' + type + '" ');
            field.op && out.push('op="' + field.op + '" ');
            field.vt && out.push('vt="' + field.vt + '" ');
            if (field.attr){
                for (var attrp in field.attr){
                    out.push(attrp + '="' + field.attr[attrp] + '" ');
                }
            }
            return out.join('');
        },
         _setTab: function (tab)
        {
            var g = this, p = this.options;
            if (!tab || !tab.items) return;
            var jtab = $('<div class="l-form-tabs"></div>').appendTo(g.element);
            var jtabNav = $('<ul class="' + p.clsTab + '" original-title="">').appendTo(jtab);
            for (var i = 0; i < tab.items.length; i++)
            {
                var tabItem = tab.items[i],
                    jnavItem = $('<li class="'+p.clsTabItem+'"><a href="javascript:void(0)"></a></li>').appendTo(jtabNav),
                    jcontentItem = $('<div class="'+p.clsTabContent+'">').appendTo(jtab),
                    idPrev = g.id + "|tab" + i;
                jnavItem.add(jcontentItem).attr("data-index", i);
                if (tabItem.id)
                {
                    jnavItem.attr("data-id", tabItem.id);
                }
                jnavItem.find("a:first").text(tabItem.title);
                g._initFieldsHtml({
                    panel: jcontentItem,
                    fields: tabItem.fields,
                    tabindex : i,
                    idPrev: idPrev
                }); 
            }
            jtabNav.find("li").click(function ()
            {
                $(this).addClass(p.clsTabItemSelected);
            }, function ()
            {
                $(this).removeClass(p.clsTabItemSelected);
            }).click(function ()
            {
                var index = $(this).attr("data-index");
                g.selectTab(index);
            });
            g.selectTab(0);
        },
        selectTab: function (index)
        {
            var g = this, p = this.options;
            var jtab = $(g.element).find(".l-form-tabs:first");
            var links = jtab.find(".ui-tabs-nav li"), contents = jtab.find(".ui-tabs-panel");
           
            links.filter("[data-index=" + index + "]")
                .addClass(p.clsTabItemSelected);
            links.filter("[data-index!=" + index + "]")
                .removeClass(p.clsTabItemSelected);
            contents.filter("[data-index=" + index + "]").show();
            contents.filter("[data-index!=" + index + "]").hide();
        },
         _initFieldsHtml: function (e)
        {
            var g = this, p = this.options;
            var jform = e.panel,
                fields = e.fields,
                idPrev = e.idPrev || g.id,
                tabindex = e.tabindex;
            $(">.l-form-container", jform).remove();
            var lineWidth = 0, maxWidth = 0;
            if (fields && fields.length)
            {
                g._preSetFields(fields);
                var out = ['<div class="l-form-container">'],
                appendULStartTag = false,
                lastVisitedGroup = null,
                groups = []; 
                $(fields).each(function (index, field)
                {
                    if (!field.group) field.group = "";
                    if ($.inArray(field.group, groups) == -1)
                        groups.push(field.group);
                });
                $(groups).each(function (groupIndex, group)
                {
                    $(fields).each(function (i, field)
                    { 
                        if (field.group != group) return;
                        var index = $.inArray(field, fields);
                        var name = field.id || field.name, newline = field.newline;
                        var inputName = (p.prefixID || "") + (field.id || field.name);
                        //if (!name) return;
                        // mxq修改 2013-12-30
                        if (!name && field.type != 'label') return;
                        var toAppendGroupRow = field.group && field.group != lastVisitedGroup;
                        if (index == 0 || toAppendGroupRow) newline = true;
                        if (newline)
                        {
                            lineWidth = 0;
                            if (appendULStartTag)
                            {
                                out.push('</ul>');
                                appendULStartTag = false;
                            }
                            if (toAppendGroupRow)
                            {
                                out.push('<div class="l-group');
                                if (field.groupicon)
                                    out.push(' l-group-hasicon');
                                out.push('">');
                                if (field.groupicon)
                                    out.push('<img src="' + field.groupicon + '" />');
                                out.push('<span>' + field.group + '</span></div>');
                                lastVisitedGroup = field.group;
                            }
                            out.push('<ul>');
                            appendULStartTag = true;
                        }
                        out.push('<li class="l-fieldcontainer');
                        if (newline)
                        {
                            out.push(' l-fieldcontainer-first');
                        }
                        //zhangyq add 添加属性  hidden:true 隐藏该组件渲染 
                        if((field.hidden || p.hidden) || (field.type && field.type == 'hidden')){
                        	out.push(' l-hidden');
                        }
                        // zhangyq add end 
                        if (field.containerCls)
                        {
                            out.push(' ' + field.containerCls);
                        }
                        out.push('"');
                        if (field.style)
                        {
                            out.push(' style="' + field.style + '"');
                        }
                        out.push(' fieldindex="' + index + '"');
                        if (tabindex != null)
                        {
                            out.push(' tabindex="' + tabindex + '"');
                        }
                        if (field.attrRender)
                        {
                            out.push(' ' + field.attrRender());
                        }
                        out.push('><ul>');
                        //append field 编辑后面自定义内容
                        if (field.beforeContent) //前置内容
                        {
                            var beforeContent = $.isFunction(field.beforeContent) ? field.afterContent(field) : field.beforeContent;
                            beforeContent && out.push(beforeContent);
                        }
                        if (!field.hideLabel && !field.labelInAfter)
                        {
                            out.push(g._buliderLabelContainer(field, index));
                        }
                        //append input 
                        //out.push(g._buliderControlContainer(field, index, e.idPrev));
                        if (field.type != 'label') {
	                        out.push(g._buliderControlContainer(field, index, e.idPrev));
                        }
                        if (field.labelInAfter)
                        {
                            out.push(g._buliderLabelContainer(field, index));
                        }
                        //append field 编辑后面自定义内容
                        if (field.afterContent)
                        {
                            var afterContent = $.isFunction(field.afterContent) ? field.afterContent(field) : field.afterContent;
                            afterContent && out.push(afterContent);
                        }
                        //append space
                        if (!field.hideSpace)
                        {
                            out.push(g._buliderSpaceContainer(field, index));
                        }
                        out.push('</ul></li>');

                        lineWidth += (field.width || p.inputWidth || 0);
                        lineWidth += (field.space || p.space || 0);
                        lineWidth += (field.labelWidth || p.labelWidth || 0);
                        if (lineWidth > maxWidth) maxWidth = lineWidth;
                    });
                });
                if (appendULStartTag)
                {
                    out.push('</ul>');
                    appendULStartTag = false;
                }
                out.push('<div class="l-clear"></div>');
                out.push('</div>');
                jform.append(out.join(''));
                if (!p.width || maxWidth > p.width)
                {
                    //jform.width(maxWidth + 10);
                }
                $(".l-group .togglebtn", jform).remove();
                $(".l-group", jform).width(jform.width() * 0.95).append("<div class='togglebtn'></div>");
            }
            //生成ligerui表单样式。
            $(".l-form-container", jform).find("input,select,textarea,div").each(function (){
                p.editorBulider.call(g, $(this), fields);
            });
        },
         _createEditors : function(e)
        { 
            var g = this, p = this.options;
            var fields = e.fields,
                idPrev = e.idPrev || g.id,
                editPrev = e.editPrev || "";
            g.editors = g.editors || {}; 
	        var jform = $(g.element);
            $(fields).each(function (fieldIndex, field)
            {
                var container = document.getElementById(idPrev + "|" + fieldIndex),
                    editor = p.editors[field.type],
                    editId = editPrev + fieldIndex; 
                if (!container) return; 
                container = $(container);
                var editorControl = g._createEditor(editor, container, {
                    field: field
                }, container.width(), container.height());
                if (!editorControl) return;
                if (g.editors[editId] && g.editors[editId].control && g.editors[editId].control.destroy)
                {
                    g.editors[editId].control.destroy();
                }
                g.editors[editId] = {
                    control: editorControl,
                    editor: editor
                };
            });
        },
        destroy: function ()
        {
            try
            {
                var g = this, p = this.options;
                liger.remove(this);
                for (var index in g.editors)
                {
                    var control = g.editors[index].control;
                    if (control && control.destroy) control.destroy();
                }
                $(g.element).remove();
            }
            catch (e)
            {

            }
        }
});
      
    //分组 收缩/展开  TODO
    //$(".l-form .l-group .togglebtn").live('click', function (){
    //    if ($(this).hasClass("togglebtn-down")) $(this).removeClass("togglebtn-down");
   //     else $(this).addClass("togglebtn-down");
    //    var boxs = $(this).parent().nextAll("ul,div");
    //    for (var i = 0; i < boxs.length; i++){
   //         var jbox = $(boxs[i]);
    //        if (jbox.hasClass("l-group")) break;
    //        jbox.toggle();
    //    }
   // });
    
})(jQuery);