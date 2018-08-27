/**
 * @mxq 2014-01-03 类似于html的标签<fieldset></fieldset>组件
 * @override   该组件 ligerFieldSet type="fieldset" 
 *       *******************  重写目的  ********************************************
	    
	     *  <pre>以下是ligerForm一条配置项：
	     *        { display: "天", type: "fieldset", set: 'day' },
	     *        	......在这里配置form组件的其他配置
	     *        	......
	     *        { type: "fieldset", set: 'day' }
	     *        
	     *        需要注意的是：
	     *        1、配置fieldset必须成对出现，并且第一个set配置必须和第二个set值保持一致，否则则按两个fieldset进行处理
	     *           如配置了
	     *           { display: "天", type: "fieldset", set: 'day' },
	     *           必须有{ type: "fieldset", set: 'day' }结束，第二个可以不配置display，默认是采用第一个配置的display作为fieldset的legend
	     *        2、fieldset中可以嵌套fieldset，但不允许交叉配置
	     *        	如：
	     *        		{ display: "天", type: "fieldset", set: 'day' },
	     *        			......
	     *        			{ display: "小时", type: "fieldset", set: 'hour' },
	     *        				......
	     *        			{ type: "fieldset", set: 'hour' },
	     *        			......
	     *        		{ type: "fieldset", set: 'day' }
	     *        
	     *        但不允许交叉配置，如下是不允许的
	     *        { display: "天", type: "fieldset", set: 'day' },
	     *        		......
	     *        		{ display: "小时", type: "fieldset", set: 'hour' },
	     *        			......
	     *        		{ type: "fieldset", set: 'day' },
	     *        		......
	     *        { type: "fieldset", set: 'hour' }
	     *        	
            </pre> fieldset先这样进行配置，这样会存在冗余代码，不过基本可以实现fieldset功能，
            如有更好的建议，欢迎尽情提出。
	     * ***********************************************************************
 *      
 */
(function($) {
	$.fn.ligerFieldSet = function() {
		return $.ligerui.run.call(this, "ligerFieldSet", arguments);
	};

	$.fn.ligerGetFieldSetManager = function() {
		return $.ligerui.run.call(this, "ligerGetFieldSetManager", arguments);
	};
	$.ligerMethos.FieldSet = $.ligerMethos.FieldSet || {};
	
	// 参数设置
	$.ligerDefaults.FieldSet = {
	};

	$.ligerui.controls.FieldSet = function(element, options) {
		$.ligerui.controls.FieldSet.base.constructor.call(this, element, options);
	};

	$.ligerui.controls.FieldSet.ligerExtend($.ligerui.controls.Input,{
			__getType : function() {
				return 'FieldSet'
			},
			__idPrev : function() {
				return 'FieldSet';
			},
			_extendMethods : function() {
				return $.ligerMethos.FieldSet;
			},
			_init : function() {
				$.ligerui.controls.FieldSet.base._init.call(this);
			},
			_render : function() {
				var g = this, p = this.options;
				g.set(p);
			}
	});

})(jQuery);