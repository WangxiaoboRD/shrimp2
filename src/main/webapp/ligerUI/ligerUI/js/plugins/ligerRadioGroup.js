/**
 * @zhangyq add ligerRadioGroup 组件 
 * @override   该组件 ligerCheckBoxGroup  type=radiogroup  
 *       *******************  重写目的  ********************************************
	     *  雷同ligerCheckBoxGroup的重写目的                                         *
	     * ***********************************************************************
	      
	     #########################################################################
 *       ###注意:     雷同ligerCheckBoxGroup的注意事项                                 # 
 *       #########################################################################
 * 
 *       ########################################################################
 *       ####example:  /LigerUI2/WebRoot/ligerUI2/demos/form/checkboxGroup.htm  #
 *       ########################################################################
 */
(function ($){

    $.fn.ligerRadioGroup = function (options){
        return $.ligerui.run.call(this, "ligerRadioGroup", arguments);
    }; 

    $.ligerDefaults.RadioGroup = {  //12个属性   两个以on开头的属性
        rowSize: 3,            //每行显示元素数   
        valueField: 'id',       //值成员
        textField: 'text',      //显示成员 
        name : 'RadioGroup',   //表单名 >>生成的 radio 组件的 name名称 TODO
        data: null,             //数据  
        parms: null,            //ajax提交表单 
        url: null,              //数据源URL(需返回JSON)。数组类型 和data配置项的值相同。不能同时出现 TODO
        onSuccess: null,
        onError: null,  
        css: null,               //附加css  
        value: null,//值   如果该值与data中其中一个数据值相同则会选中该选择项。
        root: 'Rows',   //数据源字段名
        record: 'Total' //数据源记录数字段名
    };

    //扩展方法
    $.ligerMethos.RadioGroup = $.ligerMethos.RadioGroup || {};

    $.ligerui.controls.RadioGroup = function (element, options){
        $.ligerui.controls.RadioGroup.base.constructor.call(this, element, options);
    };
    $.ligerui.controls.RadioGroup.ligerExtend($.ligerui.controls.Input, {
        __getType: function (){
            return 'RadioGroup';
        },
        _extendMethods: function (){
            return $.ligerMethos.RadioGroup;
        },
        _init: function (){
            $.ligerui.controls.RadioGroup.base._init.call(this);
        },
        _render: function (){
            var g = this, p = this.options; 
            g.data = p.data;    
           
            g.RadioGroup = $(this.element);
            g.RadioGroup.html('<div class="l-radiolist-inner"><table cellpadding="0" cellspacing="0" border="0" class="l-radiolist-table"></table></div>').addClass("l-radiolist");
            g.RadioGroup.table = $("table:first", g.RadioGroup); 

            g.set(p); 
            g._addClickEven();
        },
        destroy: function (){ 
            if (this.RadioGroup) this.RadioGroup.remove();
            this.options = null;
            $.ligerui.remove(this);
        },
        clear : function(){
            this._changeValue("");
            this.trigger('clear');
        },
        indexOf : function(item){
            var g = this, p = this.options;
            if (!g.data) return -1;
            for (var i = 0, l = g.data.length; i < l; i++){
                if (typeof (item) == "object"){
                    if (g.data[i] == item) return i;
                } else{
                    if (g.data[i][p.valueField].toString() == item.toString()) return i;
                }
            }
            return -1;
        },
        removeItems : function(items){
            var g = this;
            if (!g.data) return;
            $(items).each(function (i,item){
                var index = g.indexOf(item);
                if (index == -1) return;
                g.data.splice(index, 1);
            });
            g.refresh();
        },
        removeItem: function (item){
            if (!this.data) return;
            var index = this.indexOf(item);
            if (index == -1) return;
            this.data.splice(index, 1);
            this.refresh();
        },
        insertItem: function (item,index){
            var g = this;
            if (!g.data) g.data = []; 
            g.data.splice(index, 0, item);
            g.refresh();
        },
        addItems: function (items){
            var g = this;
            if (!g.data) g.data = [];
            $(items).each(function (i, item){
                g.data.push(item);
            });
            g.refresh();
        },
        addItem: function (item){
            var g = this;
            if (!g.data) g.data = [];
            g.data.push(item);
            g.refresh();
        }, //0. 
        _setValue: function (value){ 
            var g = this, p = this.options;
            p.value = value;
            this._dataInit(); 
        },
        setValue: function (value){ 
            this._setValue(value);
        }, //1.
        _setCss : function(css){
            if (css) {
                this.RadioGroup.addClass(css);
            } 
        }, //2.
        _setDisabled: function (value){
            //禁用样式
            if (value){
                this.RadioGroup.addClass('l-radiolist-disabled');
                $("input:radio", this.RadioGroup).attr("disabled", true);
            } else{
                this.RadioGroup.removeClass('l-radiolist-disabled');
                $("input:radio", this.RadioGroup).removeAttr("disabled");
            }
        }, //3.
        _setWidth: function (value){
            this.RadioGroup.width(value);
        },//4.
        _setHeight: function (value){
            this.RadioGroup.height(value);
        }, //5. 
        _setUrl: function (url) {
            if (!url) return;
            var g = this, p = this.options; 
            $.ligerui.ligerAjax( {
                type: 'post',
                url: url,
                data: p.parms,
                cache: false,
                dataType: 'json',
                success: function (data) { 
                    g.data = data[p.root]||data;
					g.setData(g.data);
                    g.trigger('success', [data]);
                },
                error: function (XMLHttpRequest, textStatus) {
                    g.trigger('error', [XMLHttpRequest, textStatus]);
                }
            });
        },
        setUrl: function (url) {
            return this._setUrl(url);
        },//6.
        _setData : function(data){
            this.setData(data);
        },
        setData: function (data){
            var g = this, p = this.options; 
            if (!data || !data.length) return;
            g.data = data;
            g.refresh();
        },
        setParm: function (name, value) {
            if (!name) return;
            var g = this;
            var parms = g.get('parms');
            if (!parms) parms = {};
            parms[name] = value;
            g.set('parms', parms); 
        },
        clearContent: function (){
            var g = this, p = this.options;
            $("table", g.RadioGroup).html(""); 
        },//此处作者用的方法 真的需要学习呀！ XXX.到这一步内容已经填充了。
        refresh:function(){
            var g = this, p = this.options, data = this.data; 
            this.clearContent();
            if (!data) return; 
            var out = [], rowSize = p.rowSize, appendRowStart = false, name = p.name || g.id;
            for (var i = 0; i < data.length; i++){
                var val = data[i][p.valueField], txt = data[i][p.textField], id = name + "-" + i;
                var newRow = i % rowSize == 0;
                //0,5,10
                if (newRow){
                    if (appendRowStart) 
                    	out.push('</tr>'); 
                    out.push("<tr>");
                    appendRowStart = true;
                }
                out.push("<td><input type='radio' name='" + name + "' value='" + val + "' id='" + id + "'/>&nbsp;<label for='" + id + "'>" + txt + "</label></td>");
            }
            if (appendRowStart) out.push('</tr>');
            g.RadioGroup.table.append(out.join(''));
            g.RadioGroup.width(g.RadioGroup.table.width());//zhangyq add TODO
        },//注释部分为没有修改的部分
        _getValue: function (){ 
            var g = this, p = this.options, name = p.name || g.id;
            return $('input:radio[name="' + name + '"]:checked').val();
        },
        getValue: function (){
            //获取值
            return this._getValue();
        },  
        updateStyle: function (){ 
            this._dataInit();
        },
        _dataInit: function (){
            var g = this, p = this.options;
            var value =  g._getValue() || p.value;
            g._changeValue(value);
        },
        _changeValue: function (newValue){
            var g = this, p = this.options, name = p.name || g.id;
            $("input:radio[name='" + name + "']", g.RadioGroup).each(function (){
                if(this.value == newValue)
                  $(this).attr("checked","checked");
            });
        },
        _addClickEven: function (){
            var g = this, p = this.options;
            //选项点击
            p.click && $("td input:radio",g.RadioGroup.table).bind('click',p.click);
        } 
    });
      

})(jQuery);