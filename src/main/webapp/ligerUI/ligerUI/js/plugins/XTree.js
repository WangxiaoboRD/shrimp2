/*
 * ligerTree扩展组件：所谓的扩展组件就是对原来功能的补充和出现bug的修复。
 * 2013-6-27 9:43:25
 * 注册了两个新的事件：  
 * 1、onUrlLoadSuccess：url加载成功事件
 * 2、 onAfterDrag: 节点拖曳后事件
 */
(function($) {
	
	/**
	 * 功能：扩充 ligerTree的配置项信息
	 */
	$.ligerDefaults.TreeOptions = {
		root:'Rows',
		attribute: ['id', 'url'],
        attributeMapping:['id', 'url'],
        onUrlLoadSuccess: function(){}, // 此事件注册是在请求后台后 调用success之前触发的。
        onAfterDrag: function(){}, // 节点拖曳后事件 mxq 2014-04-02 add
         /*
      		是否展开 
            1.可以是true/false 
            2.也可以是数字(层次)N 代表第1层到第N层都是展开的，其他收缩
            3.或者是判断函数 函数参数e(data,level) 返回true/false
       		 优先级没有节点数据的isexpand属性高,并没有delay属性高
        */
        isExpand: null,
        /*
        	是否延迟加载 
            1.可以是true/false 
            2.也可以是数字(层次)N 代表第N层延迟加载 
            3.或者是字符串(Url) 加载数据的远程地址
            4.如果是数组,代表这些层都延迟加载,如[1,2]代表第1、2层延迟加载
            5.再是函数(运行时动态获取延迟加载参数) 函数参数e(data,level),返回true/false或者{url:...,parms:...}
      		  优先级没有节点数据的delay属性高
        */
        delay: null,
        parms: null, // 服务器提交参数
        treeWidth: null // 整个树状容器的宽度，若指定treeWidth，又指定了nodeWidth，他会优先使用treeWidth
	};
	
	/**
	 * 功能： 扩充ligerTree的原型函数 
	 */
	$.ligerMethos.Tree = {
		
		/**
		 * @override
		 */
		_render: function ()
        {
            var g = this, p = this.options;
            g.set(p, true);
            g.tree = $(g.element);
            g.tree.addClass('l-tree');
            g.toggleNodeCallbacks = [];
            g.sysAttribute = ['isexpand', 'ischecked', 'href', 'style','delay'];
            g.loading = $("<div class='l-tree-loading'></div>");
            g.tree.after(g.loading);
            g.data = [];
            g.maxOutlineLevel = 1;
            g.treedataindex = 0;
            g._applyTree();
            g._setTreeEven();
            g.set(p, false);
        },
        
        // 2015-01-03 mxq修改，可以手动指定tree宽度
        _upadteTreeWidth : function() {
			var g = this, p = this.options;
			var treeWidth = p.treeWidth;
			if (!treeWidth) {
				treeWidth = g.maxOutlineLevel * 22;
				if (p.checkbox)
					treeWidth += 22;
				if (p.parentIcon || p.childIcon)
					treeWidth += 22;
				treeWidth += p.nodeWidth;
			}
			g.tree.width(treeWidth);
		},
		
		 //判断节点是否展开状态,返回true/false
        _isExpand : function(o,level)
        {
            var g = this, p = this.options;
            var isExpand = o.isExpand != null ? o.isExpand : (o.isexpand != null ? o.isexpand : p.isExpand);
            if (isExpand == null) return true;
            if (typeof (isExpand) == "function") isExpand = p.isExpand({ data: o, level: level });
            if (typeof (isExpand) == "boolean") return isExpand;
            if (typeof (isExpand) == "string") return isExpand == "true";
            if (typeof (isExpand) == "number") return isExpand > level; 
            return true;
        },
        //获取节点的延迟加载状态,返回true/false (本地模式) 或者是object({url :'...',parms:null})(远程模式)
        _getDelay: function (o, level)
        {
            var g = this, p = this.options; 
            var delay = o.delay != null ? o.delay : p.delay;
            if (delay == null) return false; 
            if (typeof (delay) == "function") delay = delay({ data: o, level: level });
            if (typeof (delay) == "boolean") return delay;
            if (typeof (delay) == "string") return { url: delay }; 
            if (typeof (delay) == "number") delay = [delay];
            if ($.isArray(delay)) return $.inArray(level, delay) != -1;
            if (typeof (delay) == "object" && delay.url) return delay;
            return false; 
        },
        //根据data生成最终完整的tree html
		_getTreeHTMLByData : function(data, outlineLevel, isLast, isExpand) {
            var g = this, p = this.options;
            if (g.maxOutlineLevel < outlineLevel)
                g.maxOutlineLevel = outlineLevel;
            isLast = isLast || [];
            outlineLevel = outlineLevel || 1;
            var treehtmlarr = [];
            if (!isExpand) treehtmlarr.push('<ul class="l-children" style="display:none">');
            else treehtmlarr.push("<ul class='l-children'>");
            for (var i = 0; i < data.length; i++)
            {
                var o = data[i];
                var isFirst = i == 0;
                var isLastCurrent = i == data.length - 1;
                var delay = g._getDelay(o, outlineLevel);
                var isExpandCurrent = delay ? false : g._isExpand(o, outlineLevel);
                
                treehtmlarr.push('<li ');
                if (o.treedataindex != undefined)
                    treehtmlarr.push('treedataindex="' + o.treedataindex + '" ');
                if (isExpandCurrent)
                    treehtmlarr.push('isexpand=' + o.isexpand + ' ');
                treehtmlarr.push('outlinelevel=' + outlineLevel + ' ');
                //增加属性支持
                for (var j = 0; j < g.sysAttribute.length; j++)
                {
                    if ($(this).attr(g.sysAttribute[j]))
                        data[dataindex][g.sysAttribute[j]] = $(this).attr(g.sysAttribute[j]);
                }
                for (var j = 0; j < p.attribute.length; j++)
                {
                    if (o[p.attribute[j]])
                        treehtmlarr.push(p.attribute[j] + '="' + o[p.attribute[j]] + '" ');
                }

                //css class
                treehtmlarr.push('class="');
                isFirst && treehtmlarr.push('l-first ');
                isLastCurrent && treehtmlarr.push('l-last ');
                isFirst && isLastCurrent && treehtmlarr.push('l-onlychild ');
                treehtmlarr.push('"');
                treehtmlarr.push('>');
                treehtmlarr.push('<div class="l-body');
                if (p.selectable && p.selectable(o) == false)
                {
                    treehtmlarr.push(' l-unselectable');
                }
                treehtmlarr.push('">');
                for (var k = 0; k <= outlineLevel - 2; k++)
                {
                    if (isLast[k]) treehtmlarr.push('<div class="l-box"></div>');
                    else treehtmlarr.push('<div class="l-box l-line"></div>');
                } 
                if (g.hasChildren(o))
                {
                    if (isExpandCurrent) treehtmlarr.push('<div class="l-box l-expandable-open"></div>');
                    else treehtmlarr.push('<div class="l-box l-expandable-close"></div>');
                    if (p.checkbox)
                    {
                        if (o.ischecked)
                            treehtmlarr.push('<div class="l-box l-checkbox l-checkbox-checked"></div>');
                        else
                            treehtmlarr.push('<div class="l-box l-checkbox l-checkbox-unchecked"></div>');
                    }
                    if (p.parentIcon)
                    {
                        //node icon
                        treehtmlarr.push('<div class="l-box l-tree-icon ');
                        treehtmlarr.push(g._getParentNodeClassName(isExpandCurrent ? true : false) + " ");
                        if (p.iconFieldName && o[p.iconFieldName])
                            treehtmlarr.push('l-tree-icon-none');
                        treehtmlarr.push('">');
                        if (p.iconFieldName && o[p.iconFieldName])
                            treehtmlarr.push('<img src="' + o[p.iconFieldName] + '" />');
                        treehtmlarr.push('</div>');
                    }
                }
                else
                {
                    if (isLastCurrent) treehtmlarr.push('<div class="l-box l-note-last"></div>');
                    else treehtmlarr.push('<div class="l-box l-note"></div>');
                    if (p.checkbox)
                    {
                        if (o.ischecked)
                            treehtmlarr.push('<div class="l-box l-checkbox l-checkbox-checked"></div>');
                        else
                            treehtmlarr.push('<div class="l-box l-checkbox l-checkbox-unchecked"></div>');
                    }
                    if (p.childIcon)
                    {
                        //node icon 
                        treehtmlarr.push('<div class="l-box l-tree-icon ');
                        treehtmlarr.push(g._getChildNodeClassName() + " ");
                        if (p.iconFieldName && o[p.iconFieldName])
                            treehtmlarr.push('l-tree-icon-none');
                        treehtmlarr.push('">');
                        if (p.iconFieldName && o[p.iconFieldName])
                            treehtmlarr.push('<img src="' + o[p.iconFieldName] + '" />');
                        treehtmlarr.push('</div>');
                    }
                }
                if (p.render)
                {
                    treehtmlarr.push('<span>' + p.render(o, o[p.textFieldName]) + '</span>');
                } else
                {
                    treehtmlarr.push('<span>' + o[p.textFieldName] + '</span>');
                }
                treehtmlarr.push('</div>');
                if (g.hasChildren(o))
                {
                    var isLastNew = [];
                    for (var k = 0; k < isLast.length; k++)
                    {
                        isLastNew.push(isLast[k]);
                    }
                    isLastNew.push(isLastCurrent); 
                    if (delay)
                    { 
                        if (delay == true)
                        {
                            g.toggleNodeCallbacks.push({
                                data: o,
                                callback: function (dom, o)
                                {
                                    var content = g._getTreeHTMLByData(o.children, outlineLevel + 1, isLastNew, isExpandCurrent).join('');
                                    $(dom).append(content);
                                    $(">.l-children .l-body", dom).hover(function ()
                                    {
                                        $(this).addClass("l-over");
                                    }, function ()
                                    {
                                        $(this).removeClass("l-over");
                                    });
                                    g._removeToggleNodeCallback(o);
                                }
                            });
                        }
                        else if(delay.url)
                        {
                            (function (o, url, parms)
                            {
                                g.toggleNodeCallbacks.push({
                                    data: o,
                                    callback: function (dom, o)
                                    {
                                        g.loadData(dom, url, parms, {
                                            showLoading: function ()
                                            { 
                                                $("div.l-expandable-close:first", dom).addClass("l-box-loading");
                                            },
                                            hideLoading: function ()
                                            {
                                                $("div.l-box-loading:first", dom).removeClass("l-box-loading");
                                            }
                                        });
                                        g._removeToggleNodeCallback(o);
                                    }
                                });
                            })(o, delay.url, delay.parms);
                        }
                    }
                    else
                    {
                        treehtmlarr.push(g._getTreeHTMLByData(o.children, outlineLevel + 1, isLastNew, isExpandCurrent).join(''));
                    }

                }
                treehtmlarr.push('</li>');
            }
            treehtmlarr.push("</ul>");
            return treehtmlarr;
        },
        
        /**
         * @override
         * 清空
         */
		clear : function() {
			var g = this, p = this.options;
			g.data = null; // 2014-04-03 mxq add
            g.data = [];
            g.toggleNodeCallbacks = [];
            g.nodes = null; // 2014-04-03 mxq end
            g.tree.html("");
		},
        
		 _removeToggleNodeCallback: function (nodeData){
            var g = this, p = this.options;
            for (var i = 0; i <= g.toggleNodeCallbacks.length; i++)
            {
                if (g.toggleNodeCallbacks[i] && g.toggleNodeCallbacks[i].data == nodeData)
                {
                    g.toggleNodeCallbacks.splice(i, 1);
                    break;
                }
            }
        },
		
        /**
         * @override
         */
        _setTreeEven : function() {
			var g = this, p = this.options;
			if (g.hasBind('contextmenu')) {
				g.tree
						.bind(
								"contextmenu",
								function(e) {
									var obj = (e.target || e.srcElement);
									var treeitem = null;
									if (obj.tagName
											.toLowerCase() == "a"
											|| obj.tagName
													.toLowerCase() == "span"
											|| $(obj).hasClass(
													"l-box"))
										treeitem = $(obj)
												.parent()
												.parent();
									else if ($(obj).hasClass(
											"l-body"))
										treeitem = $(obj)
												.parent();
									else if (obj.tagName
											.toLowerCase() == "li")
										treeitem = $(obj);
									if (!treeitem)
										return;
									var treedataindex = parseInt(treeitem
											.attr("treedataindex"));
									var treenodedata = g
											._getDataNodeByTreeDataIndex(
													g.data,
													treedataindex);
									return g
											.trigger(
													'contextmenu',
													[
															{
																data : treenodedata,
																target : treeitem[0]
															},
															e ]);
								});
			}
			g.tree.click(function(e) {
						var obj = (e.target || e.srcElement);
						var treeitem = null;
						if (obj.tagName.toLowerCase() == "a"
								|| obj.tagName.toLowerCase() == "span"
								|| $(obj).hasClass("l-box"))
							treeitem = $(obj).parent().parent();
						else if ($(obj).hasClass("l-body"))
							treeitem = $(obj).parent();
						else
							treeitem = $(obj);
						if (!treeitem)
							return;
						var treedataindex = parseInt(treeitem
								.attr("treedataindex"));
						var treenodedata = g
								._getDataNodeByTreeDataIndex(
										g.data, treedataindex);
						var treeitembtn = $("div.l-body:first",
								treeitem)
								.find(
										"div.l-expandable-open:first,div.l-expandable-close:first");
						var clickOnTreeItemBtn = $(obj)
								.hasClass("l-expandable-open")
								|| $(obj).hasClass(
										"l-expandable-close");
						if (!$(obj).hasClass("l-checkbox")
								&& !clickOnTreeItemBtn) {
							if ($(">div:first", treeitem)
									.hasClass("l-selected")
									&& p.needCancel) {
								if (g
										.trigger(
												'beforeCancelSelect',
												[ {
													data : treenodedata,
													target : treeitem[0]
												} ]) == false)
									return false;
		
								$(">div:first", treeitem)
										.removeClass(
												"l-selected");
								g.trigger('cancelSelect', [ {
									data : treenodedata,
									target : treeitem[0]
								} ]);
							} else {
								if (g
										.trigger(
												'beforeSelect',
												[ {
													data : treenodedata,
													target : treeitem[0]
												} ]) == false)
									return false;
								$(".l-body", g.tree)
										.removeClass(
												"l-selected");
								$(">div:first", treeitem)
										.addClass("l-selected");
								g.trigger('select', [ {
									data : treenodedata,
									target : treeitem[0]
								} ])
							}
						}
						//chekcbox even
						if ($(obj).hasClass("l-checkbox")) {
							if (p.autoCheckboxEven) {
								//状态：未选中
								if ($(obj).hasClass(
										"l-checkbox-unchecked")) {
									$(obj)
											.removeClass(
													"l-checkbox-unchecked")
											.addClass(
													"l-checkbox-checked");
									$(
											".l-children .l-checkbox",
											treeitem)
											.removeClass(
													"l-checkbox-incomplete l-checkbox-unchecked")
											.addClass(
													"l-checkbox-checked");
									g.trigger('check', [ {
										data : treenodedata,
										target : treeitem[0]
									}, true ]);
								}
								//状态：选中
								else if ($(obj).hasClass(
										"l-checkbox-checked")) {
									$(obj)
											.removeClass(
													"l-checkbox-checked")
											.addClass(
													"l-checkbox-unchecked");
									$(
											".l-children .l-checkbox",
											treeitem)
											.removeClass(
													"l-checkbox-incomplete l-checkbox-checked")
											.addClass(
													"l-checkbox-unchecked");
									g.trigger('check', [ {
										data : treenodedata,
										target : treeitem[0]
									}, false ]);
								}
								//状态：未完全选中
								else if ($(obj)
										.hasClass(
												"l-checkbox-incomplete")) {
									$(obj)
											.removeClass(
													"l-checkbox-incomplete")
											.addClass(
													"l-checkbox-checked");
									$(
											".l-children .l-checkbox",
											treeitem)
											.removeClass(
													"l-checkbox-incomplete l-checkbox-unchecked")
											.addClass(
													"l-checkbox-checked");
									g.trigger('check', [ {
										data : treenodedata,
										target : treeitem[0]
									}, true ]);
								}
								g
										._setParentCheckboxStatus(treeitem);
							} else {
								//状态：未选中
								if ($(obj).hasClass(
										"l-checkbox-unchecked")) {
									$(obj)
											.removeClass(
													"l-checkbox-unchecked")
											.addClass(
													"l-checkbox-checked");
									//是否单选
									if (p.single) {
										$(".l-checkbox", g.tree)
												.not(obj)
												.removeClass(
														"l-checkbox-checked")
												.addClass(
														"l-checkbox-unchecked");
									}
									g.trigger('check', [ {
										data : treenodedata,
										target : treeitem[0]
									}, true ]);
								}
								//状态：选中
								else if ($(obj).hasClass(
										"l-checkbox-checked")) {
									$(obj)
											.removeClass(
													"l-checkbox-checked")
											.addClass(
													"l-checkbox-unchecked");
									g.trigger('check', [ {
										data : treenodedata,
										target : treeitem[0]
									}, false ]);
								}
							}
						}
						//状态：已经张开
						else if (treeitembtn
								.hasClass("l-expandable-open")
								&& (!p.btnClickToToggleOnly || clickOnTreeItemBtn)) {
							if (g.trigger('beforeCollapse', [ {
								data : treenodedata,
								target : treeitem[0]
							} ]) == false)
								return false;
							treeitembtn
									.removeClass(
											"l-expandable-open")
									.addClass(
											"l-expandable-close");
							if (p.slide)
								$("> .l-children", treeitem)
										.slideToggle('fast');
							else
								$("> .l-children", treeitem)
										.toggle();
							$(
									"> div ."
											+ g
													._getParentNodeClassName(true),
									treeitem)
									.removeClass(
											g
													._getParentNodeClassName(true))
									.addClass(
											g
													._getParentNodeClassName());
							g.trigger('collapse', [ {
								data : treenodedata,
								target : treeitem[0]
							} ]);
						}
						//状态：没有张开
						else if (treeitembtn
								.hasClass("l-expandable-close")
								&& (!p.btnClickToToggleOnly || clickOnTreeItemBtn)) {
							if (g.trigger('beforeExpand', [ {
								data : treenodedata,
								target : treeitem[0]
							} ]) == false)
								return false;
								
							$(g.toggleNodeCallbacks).each(function () {
		                        if (this.data == treenodedata){
		                            this.callback(treeitem[0], treenodedata);
		                        }
		                    });
							treeitembtn
									.removeClass(
											"l-expandable-close")
									.addClass(
											"l-expandable-open");
							var callback = function() {
								g.trigger('expand', [ {
									data : treenodedata,
									target : treeitem[0]
								} ]);
							};
							if (p.slide) {
								$("> .l-children", treeitem)
										.slideToggle('fast',
												callback);
							} else {
								$("> .l-children", treeitem)
										.toggle();
								callback();
							}
							$(
									"> div ."
											+ g
													._getParentNodeClassName(),
									treeitem)
									.removeClass(
											g
													._getParentNodeClassName())
									.addClass(
											g
													._getParentNodeClassName(true));
						}
						g.trigger('click', [ {
							data : treenodedata,
							target : treeitem[0]
						} ]);
					});
		
			//节点拖拽支持
			if ($.fn.ligerDrag && p.nodeDraggable) {
				g.nodeDroptip = $("<div class='l-drag-nodedroptip' style='display:none'></div>").appendTo('body');
				g.tree.ligerDrag( {
					revert : true,
					animate : false,
					proxyX : 20,
					proxyY : 20,
					proxy : function(draggable, e) {
						var src = g._getSrcElementByEvent(e);
						if (src.node) {
							var content = "dragging";
							if (p.nodeDraggingRender) {
								content = p.nodeDraggingRender(draggable.draggingNodes, draggable, g);
							} else {
								content = "";
								var appended = false;
								for ( var i in draggable.draggingNodes) {
									var node = draggable.draggingNodes[i];
									if (appended)
										content += ",";
									content += node.text;
									appended = true;
								}
							}
								var proxy = $("<div class='l-drag-proxy' style='display:none'><div class='l-drop-icon l-drop-no'></div>"+ content+ "</div>").appendTo('body');
								return proxy;
							}
						},
						onRevert : function() {
							return false;
						},
						onRendered : function() {
							this.set('cursor', 'default');
							g.children[this.id] = this;
						},
							onStartDrag : function(current, e) {
								if (e.button == 2)
									return false;
								this.set('cursor', 'default');
								var src = g._getSrcElementByEvent(e);
								if (src.checkbox)
									return false;
								if (p.checkbox) {
									var checked = g.getChecked();
									this.draggingNodes = [];
									for ( var i in checked) {
										this.draggingNodes.push(checked[i].data);
									}
									if (!this.draggingNodes || !this.draggingNodes.length)
										return false;
								} else {
									this.draggingNodes = [ src.data ];
								}
								this.draggingNode = src.data;
								this.set('cursor', 'move');
								g.nodedragging = true;
								this.validRange = {
									top : g.tree.offset().top,
									bottom : g.tree.offset().top + g.tree.height(),
									left : g.tree.offset().left,
									right : g.tree.offset().left + g.tree.width()
								};
							},
							onDrag : function(current, e) {
								var nodedata = this.draggingNode;
								if (!nodedata)
									return false;
								var nodes = this.draggingNodes ? this.draggingNodes : [ nodedata ];
								if (g.nodeDropIn == null)
									g.nodeDropIn = -1;
								var pageX = e.pageX;
								var pageY = e.pageY;
								var visit = false;
								var validRange = this.validRange;
								if (pageX < validRange.left || pageX > validRange.right || pageY > validRange.bottom || pageY < validRange.top) {
									g.nodeDropIn = -1;
									g.nodeDroptip.hide();
									this.proxy.find(".l-drop-icon:first").removeClass("l-drop-yes l-drop-add").addClass("l-drop-no");
									return;
								}
								for ( var i = 0, l = g.nodes.length; i < l; i++) {
									var nd = g.nodes[i];
									var treedataindex = nd['treedataindex'];
									if (nodedata['treedataindex'] == treedataindex)
										visit = true;
									if ($.inArray(nd, nodes) != -1)
										continue;
									var isAfter = visit ? true
											: false;
									if (g.nodeDropIn != -1
											&& g.nodeDropIn != treedataindex)
										continue;
									var jnode = $("li[treedataindex="+ treedataindex+ "] div:first", g.tree);
									var offset = jnode.offset();
									var range = {
										top : offset.top,
										bottom : offset.top + jnode.height(),
										left : g.tree.offset().left,
										right : g.tree.offset().left + g.tree.width()
									};
									if (pageX > range.left
											&& pageX < range.right
											&& pageY > range.top
											&& pageY < range.bottom) {
										var lineTop = offset.top;
										if (isAfter)
											lineTop += jnode.height();
										g.nodeDroptip.css({left : range.left, top : lineTop, width : range.right - range.left}).show();
										g.nodeDropIn = treedataindex;
										g.nodeDropDir = isAfter ? "bottom" : "top";
										if (pageY > range.top + 7 && pageY < range.bottom - 7) {
											this.proxy.find(".l-drop-icon:first").removeClass("l-drop-no l-drop-yes").addClass("l-drop-add");
											g.nodeDroptip.hide();
											g.nodeDropInParent = true;
										} else {
											this.proxy.find(".l-drop-icon:first").removeClass("l-drop-no l-drop-add").addClass("l-drop-yes");
											g.nodeDroptip.show();
											g.nodeDropInParent = false;
										}
										break;
									} else if (g.nodeDropIn != -1) {
										g.nodeDropIn = -1;
										g.nodeDropInParent = false;
										g.nodeDroptip.hide();
										this.proxy.find(".l-drop-icon:first").removeClass("l-drop-yes  l-drop-add").addClass("l-drop-no");
									}
								}
							},
							onStopDrag : function(current, e) {
								var nodes = this.draggingNodes;
								g.nodedragging = false;
								var isDrag = false;
								//if (g.nodeDropIn != -1) {  mxq 2014-04-02 update end
								if ((typeof(g.nodeDropIn) != 'undefined') && (g.nodeDropIn != -1)) {
									for ( var i = 0; i < nodes.length; i++) {
										var children = nodes[i].children;
										if (children) {
											nodes = $.grep(nodes, function(node, i) {
												var isIn = $.inArray(node, children) == -1;
												return isIn;
											});
										}
									}
									
									var parentNode = null; // 父节点，mxq 2014-04-03 add
									for (var i in nodes) {
										var node = nodes[i];
										if (g.nodeDropInParent) {
											g.remove(node);
											g.append(g.nodeDropIn, [ node ]);
											parentNode = g.getParent(node);// 父节点，mxq 2014-04-03 add
										} else {
											g.remove(node);
											g.append(g.getParent(g.nodeDropIn), [ node ], g.nodeDropIn, g.nodeDropDir == "bottom")
											parentNode = g.getParent(g.nodeDropIn);// 父节点，mxq 2014-04-03 add
										}
									}
									
									// 触发节点拖动后事件
									g.trigger('afterDrag', [parentNode, nodes, g]); // mxq add 2014-04-02
									
									g.nodeDropIn = -1;
								}
								g.nodeDroptip.hide();
								this.set('cursor', 'default');
							}
						});
			}
		},
		
		// 刷新树 mxq 2014-04-03 add
        refreshTree: function() {
        	var g = this, p = this.options;
        	if (p.url) {
	        	g.clear();
    			this.loadData(null, p.url, p.parms);
        	}
        },
        
        /**
         * @override
         */
        _setUrl : function(url) {
        	 var g = this, p = this.options; 
			 if (url){
                g.clear();
                g.loadData(null, url, p.parms);
            }
		},
        
        /**
         * @override
         * @param {Object} node
         * @param {String} url
         * @param {Object} param
         */
        loadData : function(node, url, param) {
			var g = this, p = this.options;
			g.loading.show();
			var ajaxtype = param ? "post" : "get";
			param = param || [];
			//请求服务器
			$.ligerui.ligerAjax({
				type : ajaxtype,
				url : url,
				data : param,
				dataType : 'json',
				success : function(data) {
					g.loading.hide();	
					if (!data)
						return;
					g.trigger('urlLoadSuccess',[ data[p.root] ]);//zhangyq add 
					g.append(node, data[p.root]||data ); // zhangyq update 
					g.trigger('success', [ data ]);
				},
				error : function(XMLHttpRequest, textStatus,errorThrown) {
					try {
						g.loading.hide();
						g.trigger('error', [ XMLHttpRequest,
								textStatus, errorThrown ]);
					} catch (e) { }
				}
			});
		},
		//获取父节点 数据
		getParent : function(treenode, level) {
			var g = this;
			treenode = g.getNodeDom(treenode);
			var parentTreeNode = g.getParentTreeItem(treenode,
					level);
			if (!parentTreeNode)
				return null;
			var parentIndex = $(parentTreeNode).attr(
					"treedataindex");
			return g._getDataNodeByTreeDataIndex(g.data,parentIndex);
		},
		
        /**
         * @add
         * 功能： 获得半选中的项 
         * @return {Array}
         *   半选中节点组成的数组
         */
        getInComplete : function (){
            var g = this, p = this.options;
            if (!this.options.checkbox) return null;
            var nodes = [];
            $(".l-checkbox-incomplete", g.tree).parent().parent("li").each(function (){
                var treedataindex = parseInt($(this).attr("treedataindex"));
                nodes.push({ target: this, data: g._getDataNodeByTreeDataIndex(g.data, treedataindex) });
            });
            return nodes;
	    },
	    
	    /**
         * @add
         * 功能： 初始化选中状态 
         */
		initCheckedNodes : function(node, ischecked) {
			var g = this, p = this.options;
			var treeitem = $(tree.getNodeDom(node));
			var treeitembody = $("div:first", treeitem);
			if (ischecked == "checked") {
				$(".l-checkbox", treeitembody).removeClass("l-checkbox-unchecked l-checkbox-incomplete").addClass("l-checkbox-checked");
			} else if (ischecked == "incomplete") {
				$(".l-checkbox", treeitembody).removeClass("l-checkbox-unchecked l-checkbox-checked").addClass("l-checkbox-incomplete");
			} else{
				$(".l-checkbox", treeitembody).removeClass("l-checkbox-checked l-checkbox-incomplete").addClass("l-checkbox-unchecked");
			}
		}
        
   };

})(jQuery);
