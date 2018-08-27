/**
 * ligerComboBox扩展组件：所谓的扩展组件就是对原来功能的补充和出现bug的修复。
 * 2013-6-27 9:43:25
 * 
 */
(function($) {
	
	/**
	 * 功能：扩充 ligerComboBox的配置项信息
	 */
	$.ligerDefaults.ComboBoxOptions = {
		root: 'Rows',   //数据源字段名
        record: 'Total', //数据源记录数字段名
        cascade: null,    // 级联配置,目前只进行级联清空的处理，里面放置级联的htmldom的id，一个的话直接配置cascade: 'id',多个时用数组的方式：cascade:['id1', 'id2']
		empx: false,	    // 空选项配置，默认为非空配置
		alwayShowInTop: false,      //下拉框是否一直显示在上方
        alwayShowInDown: false,      //下拉框是否一直显示在上方
		onTextBoxKeyDown: null,
        onTextBoxKeyEnter : null,
        delayLoad: false,      //是否延时加载
		triggerToLoad : false, //是否在点击下拉按钮时加载
		highLight: false,    //自动完成是否匹配字符高亮显示
		keySupport: false,              //按键支持： 上、下、回车 支
		initIsTriggerEvent: false,      //初始化时是否触发选择事件
		selectBoxRenderUpdate: null,  //自定义selectbox(发生值改变)
		width : null,
		selectBoxPosYDiff : -3, //下拉框位置y坐标调整
		autocompleteAllowEmpty : true, //是否允许空值搜索
		detailEnabled : true,       //detailUrl是否有效
        detailUrl: null,            //确定选项的时候，使用这个detailUrl加载到详细的数据
        detailPostIdField : null,       //提交数据id字段名
        detailDataParmName:null,       //返回数据data字段名
        detailParms: null,              //附加参数
        detailDataGetter: null, 
		ajaxComplete: null,
        ajaxBeforeSend: null,
        ajaxContentType : null,
        onAfterShowData : null,
		triggerIcon: null,         //
		onBeforeSetData: null, 
		onButtonClick: null,      //右侧图标按钮事件，可以通过return false来阻止继续操作，利用这个参数可以用来调用其他函数，比如打开一个新窗口来选择值
     	editable: true     // 下拉框是否可编辑，默认是可编辑
	};
	
	
	$.ligerMethos.ComboBox = {
		
		 _init: function ()
        {
            $.ligerui.controls.ComboBox.base._init.call(this);
            var p = this.options;
            if (p.columns)
            {
                p.isShowCheckBox = true;
            }
            if (p.isMultiSelect)
            {
                p.isShowCheckBox = true;
            }
            if (p.triggerToLoad)
            {
                p.delayLoad = true;
            }
        },
		/**
		 * @override 
		 * 功能：重写_render 函数
		 */
		_render: function (){
            var g = this, p = this.options; 
            g.data = p.data;
            g.inputText = null;
            g.select = null;
            g.textFieldID = "";
            g.valueFieldID = "";
            g.valueField = null; //隐藏域(保存值) 
            //文本框初始化
            if (this.element.tagName.toLowerCase() == "input"){
                this.element.readOnly = true; 
                g.inputText = $(this.element);
                g.textFieldID = this.element.id;
            }
            else if (this.element.tagName.toLowerCase() == "select"){
                $(this.element).hide();
                g.select = $(this.element);
                p.isMultiSelect = false;
                p.isShowCheckBox = false;
                p.cancelable = false;
                g.textFieldID = this.element.id + "_txt";
                g.inputText = $('<input type="text" readonly="true"/>');
                g.inputText.attr("id", g.textFieldID).insertAfter($(this.element));
            }  
            if (g.inputText[0].name == undefined) g.inputText[0].name = g.textFieldID; 
            g.inputText.attr("data-comboboxid", g.id);
            //隐藏域初始化
            g.valueField = null;
            if (p.valueFieldID){
                g.valueField = $("input[id='" + p.valueFieldID+"'],input[name='"+p.valueFieldID+"']" );
            	if (g.valueField.length == 0) g.valueField = $('<input type="hidden"/>');
                g.valueField[0].id = g.valueField[0].name = p.valueFieldID;
            }else{
                g.valueField = $('<input type="hidden"/>');
                g.valueField[0].id = g.valueField[0].name = g.textFieldID + "_val";
            }
            if (g.valueField[0].name == undefined) g.valueField[0].name = g.valueField[0].id;
            g.valueField.attr("data-ligerid", g.id);
            //开关
            g.link = $('<div class="l-trigger"><div class="l-trigger-icon"></div></div>');
            //下拉框
            g.selectBox = $('<div class="l-box-select" style="display:none"><div class="l-box-select-inner"><table cellpadding="0" cellspacing="0" border="0" class="l-box-select-table"></table></div></div>');
            g.selectBox.table = $("table:first", g.selectBox);
            //外层
            g.wrapper = g.inputText.wrap('<div class="l-text l-text-combobox"></div>').parent();
            g.wrapper.append('<div class="l-text-l"></div><div class="l-text-r"></div>'); 
            g.wrapper.append(g.link);
            //添加个包裹，
            g.textwrapper = g.wrapper.wrap('<div class="l-text-wrapper"></div>').parent();

            if (p.absolute)
                g.selectBox.appendTo('body').addClass("l-box-select-absolute");
            else
                g.textwrapper.append(g.selectBox);

            g.textwrapper.append(g.valueField);
            g.inputText.addClass("l-text-field");
            if (p.isShowCheckBox && !g.select){
                $("table", g.selectBox).addClass("l-table-checkbox");
            } else{
                p.isShowCheckBox = false;
                $("table", g.selectBox).addClass("l-table-nocheckbox");
            }  
            //开关 事件
            g.link.hover(function (){
                if (p.disabled || p.readonly) return;
                this.className = "l-trigger-hover";
            }, function (){
                if (p.disabled || p.readonly) return;
                this.className = "l-trigger";
            }).mousedown(function (){
                if (p.disabled || p.readonly) return;
                this.className = "l-trigger-pressed";
            }).mouseup(function (){
                if (p.disabled || p.readonly) return;
                this.className = "l-trigger-hover";
            }).click(function (){
                if (p.disabled || p.readonly) return;
                if (g.trigger('buttonClick') == false) return false;
                if (g.trigger('beforeOpen') == false) return false;
                if (p.triggerToLoad)
                {
                	g.triggerLoaded = true;
                    g._setUrl(p.url, function ()
                    { 
                        g._toggleSelectBox(g.selectBox.is(":visible"));
                    });
                } else
                {
                    g._toggleSelectBox(g.selectBox.is(":visible"));
                }

            });
            g.inputText.click(function (){
                if (p.disabled || p.readonly || !p.editable) return;
                if (g.trigger('beforeOpen') == false) return false;
                if (p.triggerToLoad)
                {
                	g.triggerLoaded = true;
                    g._setUrl(p.url, function ()
                    {
                        g._toggleSelectBox(g.selectBox.is(":visible"));
                    });
                } else
                {
                    g._toggleSelectBox(g.selectBox.is(":visible"));
                } 
                if (!p.autocomplete){
                	g.updateSelectBoxPosition();
                }

            }).blur(function (){
                if (p.disabled) return;
                g.wrapper.removeClass("l-text-focus");
            }).focus(function (){
                if (p.disabled || p.readonly || !p.editable) return;
                g.wrapper.addClass("l-text-focus");
            });
            g.wrapper.hover(function (){
                if (p.disabled || p.readonly) return;
                g.wrapper.addClass("l-text-over");
            }, function (){
                if (p.disabled || p.readonly) return;
                g.wrapper.removeClass("l-text-over");
            });
            g.resizing = false;
            g.selectBox.hover(null, function (e){
                if (p.hideOnLoseFocus && g.selectBox.is(":visible") && !g.boxToggling && !g.resizing){
                    g._toggleSelectBox(true);
                }
            }); 
            //下拉框内容初始化
            g.bulidContent();// 此处决定走不走  tree的配置项  。如果走就把这棵树交给了 ligerTree处理了。注册了三个函数 

            g.set(p); 
            //下拉框宽度、高度初始化   
            if (p.selectBoxWidth){
                g.selectBox.width(p.selectBoxWidth);
            }else{
                g.selectBox.css('width', g.wrapper.css('width'));
            }
            if (p.grid) {
                g.bind('show', function () {
                    if (!g.grid) {
                        g.setGrid(p.grid);
                        g.set('SelectBoxHeight', p.selectBoxHeight);
                    }
                });
            }
            g.updateSelectBoxPosition();
            
        },
        //表格
        setGrid: function (grid)
        {
            var g = this, p = this.options;
            if (g.grid) return;
            p.hideOnLoseFocus = p.hideGridOnLoseFocus ? true : false;
            // this.clearContent(); 2014-07-01 add note
            g.selectBox.addClass("l-box-select-lookup");
            g.selectBox.table.remove();
            var panel = $("div:first", g.selectBox);
            var conditionPanel = $("<div></div>").appendTo(panel);
            var gridPanel = $("<div></div>").appendTo(panel);
            g.conditionPanel = conditionPanel;
            //搜索框
            if (p.condition) {
                var conditionParm = $.extend({
                    labelWidth: 60,
                    space: 20
                }, p.condition);
                g.condition = conditionPanel.ligerForm(conditionParm);
            } else {
                conditionPanel.remove();
            }
            //列表
            grid = $.extend({
                columnWidth: 120,
                alternatingRow: false,
                frozen: true,
                rownumbers: true,
                allowUnSelectRow:true
            }, grid, {
                width: "100%",
                height: g.getGridHeight(),
                inWindow: false,
                isChecked: function (rowdata) {
                    var value = g.getValue();
                    if (!value) return false;
                    if (!p.valueField || !rowdata[p.valueField]) return false;
                    return $.inArray(rowdata[p.valueField].toString(), value.split(p.split)) != -1;
                }
            });
            g.grid = g.gridManager = gridPanel.ligerGrid(grid);
            var selecteds = [], onGridSelect = function () { 
                var value = [], text = [];
                $(selecteds).each(function (i, rowdata) {
                    value.push(rowdata[p.valueField]);
                    text.push(rowdata[p.textField]);
                }); 
                g._changeValue(value.join(p.split), text.join(p.split), true);
                g.trigger('gridSelect', {
                    value: value.join(p.split),
                    text: text.join(p.split),
                    data: selecteds
                });
            }, removeSelected = function (rowdata) {
                for (var i = selecteds.length - 1; i >= 0; i--) {
                    if (selecteds[i][p.valueField] == rowdata[p.valueField]) {
                        selecteds.splice(i, 1);
                    }
                }
            }, addSelected = function (rowdata) {
                for (var i = selecteds.length - 1; i >= 0; i--) {
                    if (selecteds[i][p.valueField] == rowdata[p.valueField]) {
                        return;
                    }
                }
                selecteds.push(rowdata);
            };
            if (grid.checkbox)
            {
                var onCheckRow = function (checked, rowdata) {
                    checked && addSelected(rowdata);
                    !checked && removeSelected(rowdata);
                };
                g.grid.bind('CheckAllRow', function (checked) {
                    $(g.grid.rows).each(function (rowdata) {
                        onCheckRow(checked, rowdata);
                    });
                    onGridSelect();
                });
                g.grid.bind('CheckRow', function (checked, rowdata) {
                    onCheckRow(checked, rowdata);
                    onGridSelect();
                });
            }
            else
            {
                g.grid.bind('SelectRow', function (rowdata) {
                    selecteds = [rowdata]; 
                    onGridSelect();
                    g._toggleSelectBox(true);// mxq 2014-06-30 add
                });
                g.grid.bind('UnSelectRow', function () {
                    selecteds = [];
                    onGridSelect();
                });
            }
            g.bind('show', function () {
                g.grid.refreshSize();
            });
            g.bind("clear", function () {
                selecteds = [];
                g.grid.selecteds = [];
                g.grid._showData();
            });
            if (p.condition) {
                var containerBtn1 = $('<li style="margin-right:9px"><div></div></li>');
                var containerBtn2 = $('<li style="margin-right:9px;float:right"><div></div></li>');
                $("ul:first", conditionPanel).append(containerBtn1).append(containerBtn2).after('<div class="l-clear"></div>');
                $("div", containerBtn1).ligerButton({
                    text: '搜索', width: 40,
                    click: function () {
                        var rules = $.ligerui.getConditions(conditionPanel);
                        g.grid.setParm('condition', $.ligerui.toJSON(rules));
                        g.grid.reload();
                    }
                });
                $("div", containerBtn2).ligerButton({
                    text: '关闭',width:40,
                    click: function () {
                        g.selectBox.hide();
                    }
                });
            }
            g.grid.refreshSize();
        },
         _setValue: function (value, text)
        {
            var g = this, p = this.options;
            var isInit = false, isTriggerEvent = true;
            if (text == "init")
            {
                text = null;
                isInit = true;
                isTriggerEvent = p.initIsTriggerEvent ? true : false;
            }
            text = text || g.findTextByValue(value);
            if (p.tree)
            {
                g.selectValueByTree(value);
            }
            else if (!p.isMultiSelect)
            {
                g._changeValue(value, text, isTriggerEvent);
                $("tr[value='" + value + "'] td", g.selectBox).addClass("l-selected");
                $("tr[value!='" + value + "'] td", g.selectBox).removeClass("l-selected");
            }
            else
            {
                g._changeValue(value, text, isTriggerEvent);
                if (value != null)
                {
                    var targetdata = value.toString().split(p.split);
                    $("table.l-table-checkbox :checkbox", g.selectBox).each(function () { this.checked = false; });
                    for (var i = 0; i < targetdata.length; i++)
                    {
                        $("table.l-table-checkbox tr[value=" + targetdata[i] + "] :checkbox", g.selectBox).each(function () { this.checked = true; });
                    }
                }
            }
            if (p.selectBoxRenderUpdate)
            {
                p.selectBoxRenderUpdate.call(g, {
                    selectBox: g.selectBox,
                    value: value,
                    text: text
                });
            }
        },
        selectValueByTree: function (value)
        {
            var g = this, p = this.options;
            if (value != null)
            {
                var text = "";
                var valuelist = value.toString().split(p.split);
                $(valuelist).each(function (i, item)
                { 
                    g.treeManager.selectNode(item.toString(),false);
                    text += g.treeManager.getTextByID(item);
                    if (i < valuelist.length - 1) text += p.split;
                });
                
                g._changeValue(value, text, p.initIsTriggerEvent);
            }
        },
		/**
		 * @override 
		 * 功能：重写ligerComboBox的_setDisabled
		 *     重写原因： 没有设置表单元素的 disabled属性值
		 * @param {Boolean} value
		 */
		 _setDisabled: function (value){
           var g = this, p = this.options; 
            if (value){
            	g.inputText.attr("disabled", true);
            	g.valueField.attr("disabled", true);
                p.disabled = true ;
            } else{
            	g.inputText.removeAttr("disabled");
            	g.valueField.removeAttr("disabled");
                p.disabled = false ;
            }
        },
        _setReadonly: function (readonly){ 
        	var g = this, p = this.options; 
            if (readonly){
            	g.inputText.attr("readonly", true);
                this.wrapper.addClass("l-text-readonly");
                p.readonly = true ;
            } else { 
                this.wrapper.removeClass("l-text-readonly");
                g.inputText.removeAttr("readonly");
                p.readonly = false ;
            }
        },
        _setEditable: function (editable){ 
        	var g = this, p = this.options; 
        	if (editable){
                g.inputText.removeAttr("readonly");
                p.editable = true;
            } else { 
                g.inputText.attr("readonly", true);
                p.editable = false;
            }
        },
        upFocus : function()
        {
            var g = this, p = this.options;
            var currentIndex = g.selectBox.table.find("td.l-over").attr("index");
            if (currentIndex == undefined || currentIndex == "0")
            {
                return;
            } 
            else
            {
                currentIndex = parseInt(currentIndex) - 1;
            } 
            g.selectBox.table.find("td.l-over").removeClass("l-over"); 
            g.selectBox.table.find("td[index=" + currentIndex + "]").addClass("l-over");

            g._scrollAdjust(currentIndex);
        },
        downFocus : function()
        {
            var g = this, p = this.options; 
            var currentIndex = g.selectBox.table.find("td.l-over").attr("index");
            if (currentIndex == g.data.length - 1) return;
            if (currentIndex == undefined)
            {
                currentIndex = 0;
            }
            else
            {
                currentIndex = parseInt(currentIndex) + 1;
            }
            g.selectBox.table.find("td.l-over").removeClass("l-over");
            g.selectBox.table.find("td[index=" + currentIndex + "]").addClass("l-over");

            g._scrollAdjust(currentIndex); 
        },
         _scrollAdjust:function(currentIndex)
        {
            var g = this, p = this.options; 
            var boxHeight = $(".l-box-select-inner", g.selectBox).height();
            var fullHeight = $(".l-box-select-inner table", g.selectBox).height();
            if (fullHeight <= boxHeight) return;
            var pageSplit = parseInt(fullHeight / boxHeight) + ((fullHeight % boxHeight) ? 1 : 0);//分割成几屏
            var itemHeight = fullHeight / g.data.length; //单位高度
            //计算出位于第几屏
            var pageCurrent = parseInt((currentIndex + 1) * itemHeight / boxHeight) + (((currentIndex + 1) * itemHeight % boxHeight) ? 1 : 0);
            $(".l-box-select-inner", g.selectBox).scrollTop((pageCurrent - 1) * boxHeight);
        },
        _setUrl: function (url,callback)
        {
             var g = this, p = this.options; 
            if (!url) return;
            if (g.trigger('beforeOpen') == false) return; // mxq 2015-01-19 为了解决下拉框表格动态传参的问题
            if (p.delayLoad && !g.isAccessDelay && !g.triggerLoaded)
            {
                g.isAccessDelay = true;//已经有一次延时加载了
                return;
            }
            url = $.isFunction(url) ? url.call(g) : url;
            var urlParms = $.isFunction(p.urlParms) ? p.urlParms.call(g) : p.urlParms;
            if (urlParms)
            {
                for (name in urlParms)
                {
                    url += url.indexOf('?') == -1 ? "?" : "&";
                    url += name + "=" + urlParms[name];
                }
            }
            var parms = $.isFunction(p.parms) ? p.parms.call(g) : p.parms;
            if (p.ajaxContentType == "application/json" && typeof (parms) != "string")
            {
                parms = liger.toJSON(parms);
            } 
            var ajaxOp = {
                type: p.ajaxType,
                url: url,
                data: parms,
                cache: false,
                dataType: 'json',
                beforeSend: p.ajaxBeforeSend,
                complete: p.ajaxComplete,
                success: function (result)
                {
                    var data = $.isFunction(p.dataGetter) ? data = p.dataGetter.call(g, result) : result;
                    data = p.root && data ? data[p.root] : data;
                    if (g.trigger('beforeSetData', [data]) == false){
                        return;
                    }
                    g.setData(data);
                    g.trigger('success', [data]);
                    if ($.isFunction(callback)) callback(data);
                },
                error: function (XMLHttpRequest, textStatus)
                {
                    g.trigger('error', [XMLHttpRequest, textStatus]);
                }
            };
            if (p.ajaxContentType)
            {
                ajaxOp.contentType = p.ajaxContentType;
            }
            $.ajax(ajaxOp);
        },
        setUrl: function (url,callback)
        {
            return this._setUrl(url, callback);
        },
        // 设置 值. 此处处理的是下拉框配置：isShowCheckBox: true, isMultiSelect: true,时初始化选中值的问题。
        setData: function (data){
            var g = this, p = this.options; 
            if (!data || !data.length || p == null) { 
            	this.clearContent(); // mxq 2015-03-13 add 
            	return;
            }
            if (g.data != data) g.data = data;
            this.clearContent();
            if (p.columns){
                g.selectBox.table.headrow = $("<tr class='l-table-headerow'><td width='18px'></td></tr>");
                g.selectBox.table.append(g.selectBox.table.headrow);
                g.selectBox.table.addClass("l-box-select-grid");
                for (var j = 0; j < p.columns.length; j++){
                    var headrow = $("<td columnindex='" + j + "' columnname='" + p.columns[j].name + "'>" + p.columns[j].header + "</td>");
                    if (p.columns[j].width){
                        headrow.width(p.columns[j].width);
                    }
                    g.selectBox.table.headrow.append(headrow);

                }
            }
            var out = [];
            // mxq 2014-08-09 add
            if (p.empx) {
            	var value = p.valueField;
            	var text = p.textField;
            	data.splice(0, 0, { value: '', text: '---' });
            }
            for (var i = 0; i < data.length; i++){
                var val = data[i][p.valueField];
                if (p.valueField.indexOf('.') != -1) { // mxq 2015-02-13 add 修改p.valueField不是对象直属属性时所做的处理，p.textField同理
                	val = eval("data[i]." + p.valueField);
                }
                val = val || '';// mxq 2014-09-03 add
                var txt = data[i][p.textField];
                if (p.textField.indexOf('.') != -1) {
                	txt = eval("data[i]." + p.textField);
                }
                if (!p.columns){
                    out.push("<tr value='" + val + "'>");
                    if(p.isShowCheckBox){
                        out.push("<td style='width:18px;'  index='" + i + "' value='" + val + "' text='" + txt + "' ><input type='checkbox' /></td>");
                    }
                    var itemHtml = txt;
                    if (p.renderItem) {
                        itemHtml = p.renderItem.call(g, {
                            data: data[i],
                            value: val,
                            text: txt,
                            key: g.inputText.val()
                        });
                    } else if (p.autocomplete && p.highLight){
                        itemHtml = g._highLight(txt, g.inputText.val());
                    } else{
                        itemHtml = "<span>" + itemHtml + "</span>";
                    }
                    out.push("<td index='" + i + "' value='" + val + "' text='" + txt + "' align='left'>" + itemHtml + "</td></tr>");
                } else{
                    out.push("<tr value='" + val + "'><td style='width:18px;'  index='" + i + "' value='" + val + "' text='" + txt + "' ><input type='checkbox' /></td>");
                    for (var j = 0; j < p.columns.length; j++) {
                        var columnname = p.columns[j].name;
                        out.push("<td>" + data[i][columnname] + "</td>");
                    }
                    out.push('</tr>');  
                }
            } 
            if (!p.columns) {
                if (p.isShowCheckBox) {
                    $("table.l-table-checkbox", g.selectBox).append(out.join(''));
                }else{
                    $("table.l-table-nocheckbox", g.selectBox).append(out.join(''));
                }
            } else { 
                g.selectBox.table.append(out.join(''));
            }
            //自定义复选框支持
            if (p.isShowCheckBox && $.fn.ligerCheckBox){ //TODO  2013-7-17 10:54:11
                $("table input:checkbox", g.selectBox).ligerCheck();
            }
            $(".l-table-checkbox input:checkbox", g.selectBox).change(function (){
                if (this.checked && g.hasBind('beforeSelect')){
                    var parentTD = null;
                    if ($(this).parent().get(0).tagName.toLowerCase() == "td")
                    {
                        parentTD = $(this).parent();
                    } else{
                        parentTD = $(this).parent();
                    }
                    if (parentTD != null && g.trigger('beforeSelect', [parentTD.attr("value"), parentTD.attr("text")]) == false)
                    {
                        g.selectBox.slideToggle("fast");
                        return false;
                    }
                }
                if (!p.isMultiSelect){
                    if (this.checked){
                        $("input:checked", g.selectBox).not(this).each(function (){
                            this.checked = false;
                            $(".l-checkbox-checked", $(this).parent()).removeClass("l-checkbox-checked");
                        });
                        g.selectBox.slideToggle("fast");
                    }
                }
                g._checkboxUpdateValue();
            });
            $("table.l-table-nocheckbox td", g.selectBox).hover(function (){
                $(this).addClass("l-over");
            }, function (){
                $(this).removeClass("l-over");
            });
            g._addClickEven();
            //选择项初始化
            if (!p.autocomplete) {
                g._dataInit();
            }
        },
         _highLight: function (str, key)
        {
            if (!str) return str;
            var index = str.indexOf(key);
            if (index == -1) return str;
            return str.substring(0, index) + "<span class='l-highLight'>" + key + "</span>" + str.substring(key.length + index);
        },
        //更新选中的值(复选框)
        _checkboxUpdateValue: function (){
            var g = this, p = this.options;
            var valueStr = "";
            var textStr = "";
            $("input:checked", g.selectBox).each(function (){
                var parentTD = null;
                if ($(this).parent().get(0).tagName.toLowerCase() == "td"){
                    parentTD = $(this).parent();
                } else{
                    parentTD = $(this).parent();
                }
                if (!parentTD) return;
                valueStr += parentTD.attr("value") + p.split;
                textStr += parentTD.attr("text") + p.split;
            });
            if (valueStr.length > 0) valueStr = valueStr.substr(0, valueStr.length - 1);
            if (textStr.length > 0) textStr = textStr.substr(0, textStr.length - 1);
            g._changeValue(valueStr, textStr);
        },
         updateComboBoxCell: function(arg, value, text, rowdata) {
        	var g = this, p = this.options;
            var column;
            if (typeof (arg) == "string") //column name
            {
                for (var i = 0, l = g.columns.length; i < l; i++)
                {
                    if (g.columns[i].name == arg)
                    {
                       column = g.getColumn(i);
                       break;
                    }
                }
            }
            if (typeof (arg) == "number")
            {
                column = g.getColumn(arg);
            }
            else if (typeof (arg) == "object" && arg['__id'])
            {
                column = arg;
            }
            var rowindex = rowdata['__index'];
             var editParm = { record: rowdata, value: value, column: column, rowindex: rowindex };
             if (column.textField) {
             	editParm.text = text;
             }
             g._checkEditAndUpdateCell(editParm);
        },
        _addClickEven: function ()
        {
            var g = this, p = this.options;
            //选项点击
            $(".l-table-nocheckbox td", g.selectBox).click(function ()
            {
                var value = $(this).attr("value");
                var index = parseInt($(this).attr('index'));
                var text = $(this).attr("text");
                if (g.hasBind('beforeSelect') && g.trigger('beforeSelect', [value, text]) == false)
                {
                    if (p.slide) g.selectBox.slideToggle("fast");
                    else g.selectBox.hide();
                    return false;
                }
                if ($(this).hasClass("l-selected"))
                {
                    if (p.slide) g.selectBox.slideToggle("fast");
                    else g.selectBox.hide();
                    return;
                }
                $(".l-selected", g.selectBox).removeClass("l-selected");
                $(this).addClass("l-selected");
                if (g.select)
                {
                    if (g.select[0].selectedIndex != index)
                    {
                        g.select[0].selectedIndex = index;
                        g.select.trigger("change");
                    }
                }
                if (p.slide)
                {
                    g.boxToggling = true;
                    g.selectBox.hide("fast", function ()
                    {
                        g.boxToggling = false;
                    })
                } else g.selectBox.hide();
                g._changeValue(value, text, true);
            });
        },
        getRowIndex : function(value)
        {
            var g = this, p = this.options;
            if (!value) return -1;
            if (!g.data || !g.data.length) return -1;
            for (var i = 0; i < g.data.length; i++)
            {
                var val = g.data[i][p.valueField];
                if (val == value) return i;
            }
            return -1;
        },
         //获取行数据
        getRow : function(value)  
        {
            var g = this, p = this.options;
            if (!value) return null;
            if (!g.data || !g.data.length) return null;
            for (var i = 0; i < g.data.length; i++)
            {
                var val = g.data[i][p.valueField];
                if (p.valueField.indexOf('.') != -1) { // mxq 2016-09-18 add 修改p.valueField不是对象直属属性时所做的处理
                	val = eval("g.data[i]." + p.valueField);
                }
                if (val == value) return g.data[i];
            }
            return null;
        },
      /**
       * 下拉树：修复bug
       *       当点击选中下拉树中的一个节点 不能够将下拉树隐藏的问题。
       * @param {Object} tree
       */
        setTree: function (tree){
            var g = this, p = this.options;
            //this.clearContent(); zhangyq 
            g.selectBox.table.remove();
            if (tree.checkbox != false){
                tree.onCheck = function (){
                    var nodes = g.treeManager.getChecked();
                    var value = [];
                    var text = [];
                    $(nodes).each(function (i, node){
                        if (p.treeLeafOnly && node.data.children) return;
                        value.push(node.data[p.valueField]);
                        text.push(node.data[p.textField]);
                    });
                    g._changeValue(value.join(p.split), text.join(p.split), true);
                };
            }else{
                tree.onSelect = function (node){
                    if (g.trigger('BeforeSelect') == false) return;
                    if (p.treeLeafOnly && node.data.children) return;
                    //zhangyq add  start
                    if(node.target){
	                	   if (p.slide){
		                    g.boxToggling = true;
		                    g.selectBox.hide("fast", function (){// fast
		                        g.boxToggling = false;
		                    })
	                    } else g.selectBox.hide();
	                    //zhangyq add  end 
	                 }
                    var value = node.data[p.valueField];
                    var text = node.data[p.textField];
                    g._changeValue(value, text, true);
                };
                tree.onCancelSelect = function (node){
                   // g._changeValue("", "", true);
                };
            }
            tree.onAfterAppend = function (domnode, nodedata){
                if (!g.treeManager) return;
                if (p.initValue != null && p.initText != null){
               		g._changeValue(p.initValue, p.initText, true);
            	}else {
	                var value = null;
	                if (p.initValue) value = p.initValue;
	                if(value)
	                   g.selectValueByTree(value);
            	} 
            };
            g.tree = $("<ul></ul>");
            $("div:first", g.selectBox).append(g.tree);
            g.tree.ligerTree(tree);
            g.treeManager = g.tree.ligerGetTreeManager();
        },
        /**
         * 修复一个bug -> 配置initText 不能够显示值
         * update 2013-7-23 17:31:28
         */
        _dataInit: function (){
            var g = this, p = this.options;
            var value = null; 
            if (p.initValue != null && p.initText != null){
                g._changeValue(p.initValue, p.initText);
            }else {
	            if (p.initValue != null) {//根据值来初始化
	                value = p.initValue;
	                if (p.tree){
	                    if(value)
	                        g.selectValueByTree(value);
	                }
	                else if (g.data){
	                    var text = g.findTextByValue(value);
	                    g._changeValue(value, text);
	                }
	            }  //根据文本来初始化 
	            else if (p.initText != null)
	            {
	                value = g.findValueByText(p.initText);
	                g._changeValue(value, p.initText);
	            }
	            else if (g.valueField.val() != ""){
	                value = g.valueField.val();
	                if (p.tree)
	                {
	                    if(value)
	                        g.selectValueByTree(value);
	                }
	                else if(g.data)
	                {
	                    var text = g.findTextByValue(value);
	                    g._changeValue(value, text);
	                }
	            }
            } 
            if (!p.isShowCheckBox)
            {
                $("table tr", g.selectBox).find("td:first").each(function ()
                {
                    if (value != null && value == $(this).attr("value"))
                    {
                        $(this).addClass("l-selected");
                    } else
                    {
                        $(this).removeClass("l-selected");
                    }
                });
            }
            else
            { 
                $(":checkbox", g.selectBox).each(function ()
                {
                    var parentTD = null;
                    var checkbox = $(this);
                    if (checkbox.parent().get(0).tagName.toLowerCase() == "div")
                    {
                        parentTD = checkbox.parent().parent();
                    } else
                    {
                        parentTD = checkbox.parent();
                    }
                    if (parentTD == null) return;
                    $(".l-checkbox", parentTD).removeClass("l-checkbox-checked");
                    checkbox[0].checked = false;
                    var valuearr = (value || "").toString().split(p.split);
                    $(valuearr).each(function (i, item)
                    {
                        if (value != null && item == parentTD.attr("value"))
                        {
                            $(".l-checkbox", parentTD).addClass("l-checkbox-checked");
                            checkbox[0].checked = true;
                        }
                    });
                });
            }
        },
         //设置值到 文本框和隐藏域
        //isSelectEvent：是否选择事件
        _changeValue: function (newValue, newText,isSelectEvent)
        {
            var g = this, p = this.options;
            g.valueField.val(newValue);
            if (p && p.render)
            {
                g.inputText.val(p.render(newValue, newText));
            }
            else
            {
                g.inputText.val(newText);
            }
            if (g.select)
            {
                $("option", g.select).each(function ()
                {
                    $(this).attr("selected", $(this).attr("value") == newValue);
                });
            }
            g.selectedValue = newValue;
            g.selectedText = newText;

            g.inputText.trigger("change");

            if (isSelectEvent && newText)
            {
                g.inputText.focus();
            }

            var rowData = null;
            if (newValue && typeof(newValue) == "string" &&  newValue.indexOf(p.split) > -1)
            {
                rowData = [];
                var values = newValue.split(p.split);
                $(values).each(function (i, v)
                {
                    rowData.push(g.getRow(v));
                });
            }
            else if(newValue)
            {
                rowData = g.getRow(newValue);
            }
            if (isSelectEvent)
            {
                g.trigger('selected', [newValue, newText, rowData]);
            }
        },
         _setVisible: function (value)
        {
        	var g = this, p = this.options;
            //显示样式
            if (value)
            {
                g.textwrapper.parent().parent().css({ display: 'block' });
            } else
            {
                g.textwrapper.parent().parent().css({ display: 'none' });
            }
        },
         /**
         * @override
         * 功能： 清空选择项中的值
         */
        clear : function()
        {
        	var g = this, p = this.options;
        	if (p.disabled) {
        		return;
        	}
        	
        	// mxq 2013-10-20添加开始
        	$(".l-selected", this.selectBox).removeClass("l-selected");
        	//mxq 2013-10-20添加结束
        	
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
            
            this._changeValue("", "");
            this.trigger('clear');
        },
         _selectBoxShow : function()
        {
            var g = this, p = this.options;
            if (!p.grid && !p.tree)
            {
                if (g.selectBox.table.find("tr").length || (p.selectBoxRender && g.selectBoxInner.html()))
                {
                    g.selectBox.show();
                } else
                {
                    g.selectBox.hide();
                }
                return;
            }
            g.selectBox.show();
            return;
        },
        _setAutocomplete: function (value)
        {
            var g = this, p = this.options;
            if (!value) return;
            g.inputText.removeAttr("readonly");
            g.lastInputText = g.inputText.val();
            g.inputText.keyup(function (event)
            {
                if (event.keyCode == 38 || event.keyCode == 40 || event.keyCode == 13) //up 、down、enter
                {
                    return;
                } 
                if (this._acto)
                    clearTimeout(this._acto);
                this._acto = setTimeout(function ()
                { 
                    if (g.lastInputText == g.inputText.val()) return;
                    p.initValue = "";
                    g.valueField.val("");

                    var currentKey = g.inputText.val();
                    if (currentKey) currentKey = currentKey.replace(/(^\s*)|(\s*$)/g, "");
                    if ($.isFunction(value))
                    {
                        value.call(g, {
                            key: currentKey,
                            show: function ()
                            {
                                g._selectBoxShow();
                            }
                        });
                        return;
                    }
                    if (!p.autocompleteAllowEmpty && !currentKey)
                    {
                        g.clear();
                        g.selectBox.hide(); 
                        return;
                    }
                    if (p.url)
                    {
                        g.setParm('key', currentKey);
                        g.setUrl(p.url, function ()
                        {
                            g._selectBoxShow();
                        });
                    } else if (p.grid)
                    {
                        g.grid.setParm('key', currentKey);
                        g.grid.reload();
                    }
                    g.lastInputText = g.inputText.val();
                    this._acto = null;
                }, 300);
            });
            g._dataInit(); // 2015-11-13 add 解决不会初始化值得问题
        },
         updateSelectBoxPosition: function ()
        {
            var g = this, p = this.options;
            if (p && p.absolute)
            {
                var contentHeight = $(document).height();
                if (p.alwayShowInTop || Number(g.wrapper.offset().top + 1 + g.wrapper.outerHeight() + g.selectBox.height()) > contentHeight
            			&& contentHeight > Number(g.selectBox.height() + 1))
                {
                    //若下拉框大小超过当前document下边框,且当前document上留白大于下拉内容高度,下拉内容向上展现
                    g.selectBox.css({ left: g.wrapper.offset().left, top: g.wrapper.offset().top - 1 - g.selectBox.height() + (p.selectBoxPosYDiff || 0) });
                } else
                {
                    g.selectBox.css({ left: g.wrapper.offset().left, top: g.wrapper.offset().top + 1 + g.wrapper.outerHeight() + (p.selectBoxPosYDiff || 0) });
                }
                if (p.alwayShowInDown)
                {
                    g.selectBox.css({ left: g.wrapper.offset().left, top: g.wrapper.offset().top + 1 + g.wrapper.outerHeight() });
                }
            }
            else
            {
                var topheight = g.wrapper.offset().top - $(window).scrollTop();
                var selfheight = g.selectBox.height() + textHeight + 4;
                if (topheight + selfheight > $(window).height() && topheight > selfheight)
                {
                    g.selectBox.css("marginTop", -1 * (g.selectBox.height() + textHeight + 5) + (p.selectBoxPosYDiff || 0));
                }
            }
        },
        loadDetail : function(value,callback)
        { 
            var g = this, p = this.options;
            var parms = $.isFunction(p.detailParms) ? p.detailParms.call(g) : p.detailParms;
            parms[p.detailPostIdField || "id"] = value;
            if (p.ajaxContentType == "application/json")
            {
                parms = liger.toJSON(parms);
            }
            var ajaxOp = {
                type: p.ajaxType,
                url: p.detailUrl,
                data: parms,
                cache: true,
                dataType: 'json',
                beforeSend: p.ajaxBeforeSend,
                complete: p.ajaxComplete,
                success: function (result)
				{
                    var data = $.isFunction(p.detailDataGetter) ? p.detailDataGetter(result) : result;
	                    data = p.detailDataParmName ? data[p.detailDataParmName] : data;
	                    callback && callback(data);
	                }
	            };
	
	            if (p.ajaxContentType)
	            {
	                ajaxOp.contentType = p.ajaxContentType;
	            }
	            $.ajax(ajaxOp);
	
	        },
	        enabledLoadDetail : function()
	        {
	            var g = this, p = this.options;
	            return p.detailUrl && p.detailEnabled ? true : false;
	        }
	};

	 //Key Init Combobox按键支持
    (function ()
    {
        $(document).unbind('keydown.ligercombobox');
        $(document).bind('keydown.ligercombobox',function (event)
        {
            function down()
            {
                if (!combobox.selectBox.is(":visible"))
                {
                    combobox.selectBox.show();
                }
                combobox.downFocus();
            }
            function toSelect()
            {
                combobox._changeValue(value, curTd.attr("text"), true);
                combobox.selectBox.hide();
                combobox.trigger('textBoxKeyEnter', [{
                    element: curTd.get(0)
                }]);
            }
            var curInput = $("input:focus");
            if (curInput.length && curInput.attr("data-comboboxid"))
            { 
                var combobox = liger.get(curInput.attr("data-comboboxid"));
                if (!combobox) return;
                if (!combobox.get("keySupport")) return;
                if (event.keyCode == 38) //up 
                {
                    combobox.upFocus(); 
                } else if (event.keyCode == 40) //down
                {
                    if (combobox.hasBind('textBoxKeyDown'))
                    {
                        combobox.trigger('textBoxKeyDown', [
                            {
                                callback: function ()
                                {
                                    down();
                                }
                            }]);
                    }
                    else
                    {
                        down();
                    }  
                }
                else if (event.keyCode == 13) //enter
                {
                    if (!combobox.selectBox.is(":visible")) return;
                    var curTd = combobox.selectBox.table.find("td.l-over");
                    if (curTd.length)
                    {
                        var value = curTd.attr("value");
                        
                        if (combobox.enabledLoadDetail())
                        {
                            combobox.loadDetail(value, function (data)
                            {
                                var index = combobox.getRowIndex(value);
                                if (index == -1) return;
                                combobox.data = combobox.data || [];
                                combobox.data[index] = combobox.selected = data;
                                toSelect();
                            });
                        } else
                        {
                            toSelect();
                        }
                       
                    }
                  
                }
            } 
        });

    })();

})(jQuery);
