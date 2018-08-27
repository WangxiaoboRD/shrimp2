/*
 * ligerGrid扩展组件：所谓的扩展组件就是对原来功能的补充和出现bug的修复。
 *   1、注册一个 urlLoadSuccess 事件 
 *     函数 解决前台返回对象属性包含对象属性时column填写render函数的弊端
 *   2、_removeSelected  在可编辑表格中出现删除问题时 留心该bug
 * 
 */
(function($) {
	/**
	 * 功能：扩充 ligerGrid的配置项信息
	 */
	$.ligerDefaults.GridOptions = {
		onUrlLoadSuccess: function(){},
		onBeforeAddRow: function(){},
		submitDetailsPrefix: '',// 可编辑表格中使用
		filterParam:[],// 可编辑表格支持
        replaceField:[],// 可编辑表格支持
        replaceFieldMapping:[],// 可编辑表格支持
        inWindow: true, //此处影响初始化宽度 
        exportBtn: false, // 导出按钮配置，exportUrl配置依赖于该项配置
        exportUrl: '', // 导出URL，
        exportParms: [], // 导出条件参数，该配置依赖于exportUrl配置
        pagerRender : null,       //分页栏自定义渲染函数
        scrollToPage: false,      //滚动时分页
        scrollToAppend: true,      //滚动时分页 是否追加分页的形式
        urlParms: null                    //url带参数
	};
	
	/**
	 * 功能： 扩充ligerGrid的原型方法函数 
	 */
	$.ligerMethos.Grid = {
		
		/** 
		 * @override: 重写渲染方法
		 */
		_render: function ()
        {
            var g = this, p = this.options;
            g.grid = $(g.element);
            g.grid.addClass("l-panel");
            var gridhtmlarr = [];
            gridhtmlarr.push("        <div class='l-panel-header'><span></span></div>");
            gridhtmlarr.push("        <div class='l-grid-loading'></div>");
            gridhtmlarr.push("        <div class='l-panel-topbar'></div>");
            gridhtmlarr.push("        <div class='l-panel-bwarp'>");
            gridhtmlarr.push("            <div class='l-panel-body'>");
            gridhtmlarr.push("                <div class='l-grid'>");
            gridhtmlarr.push("                  <div class='l-grid-dragging-line'></div>");
            gridhtmlarr.push("                  <div class='l-grid-popup' style='overflow: auto;'><table cellpadding='0' cellspacing='0'><tbody></tbody></table></div>");

            gridhtmlarr.push("                  <div class='l-grid1'>");
            gridhtmlarr.push("                      <div class='l-grid-header l-grid-header1'>");
            gridhtmlarr.push("                          <div class='l-grid-header-inner'><table class='l-grid-header-table' cellpadding='0' cellspacing='0'><tbody></tbody></table></div>");
            gridhtmlarr.push("                      </div>");
            gridhtmlarr.push("                      <div class='l-grid-body l-grid-body1'>");
            gridhtmlarr.push("                      </div>");
            gridhtmlarr.push("                  </div>");

            gridhtmlarr.push("                  <div class='l-grid2 l-scroll'>");
            gridhtmlarr.push("                      <div class='l-grid-header l-grid-header2'>");
            gridhtmlarr.push("                          <div class='l-grid-header-inner'><table class='l-grid-header-table' cellpadding='0' cellspacing='0'><tbody></tbody></table></div>");
            gridhtmlarr.push("                      </div>");
            gridhtmlarr.push("                      <div class='l-grid-body l-grid-body2 l-scroll'>");
            gridhtmlarr.push("                      </div>");
            gridhtmlarr.push("                  </div>");


            gridhtmlarr.push("                 </div>");
            gridhtmlarr.push("              </div>");
            gridhtmlarr.push("         </div>");
            gridhtmlarr.push("         <div class='l-panel-bar'>");
            gridhtmlarr.push("            <div class='l-panel-bbar-inner'>");
            gridhtmlarr.push("                <div class='l-bar-group  l-bar-message'><span class='l-bar-text'></span></div>");
            gridhtmlarr.push("                <div class='l-bar-group l-bar-selectpagesize'></div>");
            gridhtmlarr.push("                <div class='l-bar-separator'></div>");
            gridhtmlarr.push("                <div class='l-bar-group'>");
            gridhtmlarr.push("                    <div class='l-bar-button l-bar-btnfirst'><span title='第一页'></span></div>");
            gridhtmlarr.push("                    <div class='l-bar-button l-bar-btnprev'><span title='上一页'></span></div>");
            gridhtmlarr.push("                </div>");
            gridhtmlarr.push("                <div class='l-bar-separator'></div>");
            gridhtmlarr.push("                <div class='l-bar-group'><span class='pcontrol'> <input type='text' size='4' value='1' style='width:20px' maxlength='3' /> / <span></span></span></div>");
            gridhtmlarr.push("                <div class='l-bar-separator'></div>");
            gridhtmlarr.push("                <div class='l-bar-group'>");
            gridhtmlarr.push("                     <div class='l-bar-button l-bar-btnnext'><span title='下一页'></span></div>");
            gridhtmlarr.push("                    <div class='l-bar-button l-bar-btnlast'><span title='最后页'></span></div>");
            gridhtmlarr.push("                </div>");
            gridhtmlarr.push("                <div class='l-bar-separator'></div>");
            gridhtmlarr.push("                <div class='l-bar-group'>");
            gridhtmlarr.push("                     <div class='l-bar-button l-bar-btnload'><span title='刷新'></span></div>");
            gridhtmlarr.push("                </div>");
            gridhtmlarr.push("                <div class='l-bar-separator'></div>");
            
            if (p.exportBtn) {
				gridhtmlarr.push("                <div class='l-bar-group'>");
	            gridhtmlarr.push("                     <div class='l-bar-button l-bar-btnexport'><span title='导出'></span></div>");
	            gridhtmlarr.push("                </div>");
            }
            gridhtmlarr.push("                <div class='l-clear'></div>");
            gridhtmlarr.push("            </div>");
            gridhtmlarr.push("         </div>");
            g.grid.html(gridhtmlarr.join(''));
            //头部
            g.header = $(".l-panel-header:first", g.grid);
            //主体
            g.body = $(".l-panel-body:first", g.grid);
            //底部工具条         
            g.toolbar = $(".l-panel-bar:first", g.grid);
            //显示/隐藏列      
            g.popup = $(".l-grid-popup:first", g.grid);
            //加载中
            g.gridloading = $(".l-grid-loading:first", g.grid);
            //调整列宽层 
            g.draggingline = $(".l-grid-dragging-line", g.grid);
            //顶部工具栏
            g.topbar = $(".l-panel-topbar:first", g.grid);

            g.gridview = $(".l-grid:first", g.grid);
            g.gridview.attr("id", g.id + "grid");
            g.gridview1 = $(".l-grid1:first", g.gridview);
            g.gridview2 = $(".l-grid2:first", g.gridview);
            //表头     
            g.gridheader = $(".l-grid-header:first", g.gridview2);
            //表主体     
            g.gridbody = $(".l-grid-body:first", g.gridview2);

            //frozen
            g.f = {};
            //表头     
            g.f.gridheader = $(".l-grid-header:first", g.gridview1);
            //表主体     
            g.f.gridbody = $(".l-grid-body:first", g.gridview1);

            g.currentCell = null; // 当前所在的可编辑单元格 // mxq 2015-06-18 add
            g.firstEditColIndex = null; // 表格第一个可编辑列索引，mxq 2015-06-19 add
            g.lastEditColIndex = null; // 表格最后一个可编辑列索引，mxq 2015-06-19 add
            g.scrollIsAutoTrigger = false; // 定义表格滚动条是否自动触，mxq 2016-02-04 add
            
            g.currentData = null;
            g.changedCells = {};
            g.editors = {};                 //多编辑器同时存在
            g.editor = { editing: false };  //单编辑器,配置clickToEdit
            
            g.cacheData = {}; //缓存数据
            
            if (p.height == "auto")
            {
                g.bind("SysGridHeightChanged", function ()
                {
                    if (g.enabledFrozen())
                        g.gridview.height(Math.max(g.gridview1.height(), g.gridview2.height()));
                });
            }
			//地址发生转移了
            var pc = $.extend({}, p);

            this._bulid();
            this._setColumns(p.columns);

            delete pc['columns'];
            delete pc['data'];
            delete pc['url'];
            g.set(pc);
            if (!p.delayLoad)
            {
                if (p.url)
                    g.set({ url: p.url });
                else if (p.data)
                    g.set({ data: p.data });
            }
        },
       
        /** 
		 * @override: 重写 2016-03-21
		 */
        _initColumns: function ()
        {
            var g = this, p = this.options;
            g._columns = {};             //全部列的信息  
            g._columnCount = 0;
            g._columnLeafCount = 0;
            g._columnMaxLevel = 1;
            if (!p.columns) return;
            function removeProp(column, props)
            {
                for (var i in props)
                {
                    if (props[i] in column)
                        delete column[props[i]];
                }
            }
            //设置id、pid、level、leaf，返回叶节点数,如果是叶节点，返回1
            function setColumn(column, level, pid, previd)
            {
                removeProp(column, ['__id', '__pid', '__previd', '__nextid', '__domid', '__leaf', '__leafindex', '__level', '__colSpan', '__rowSpan']);
                if (level > g._columnMaxLevel) g._columnMaxLevel = level;
                g._columnCount++;
                column['__id'] = g._createColumnId(column);
                column['__domid'] = g.id + "|hcell|" + column['__id'];
                g._columns[column['__id']] = column;
                if (!column.columns || !column.columns.length)
                    column['__leafindex'] = g._columnLeafCount++;
                column['__level'] = level;
                column['__pid'] = pid;
                column['__previd'] = previd;
                if (!column.columns || !column.columns.length)
                {
                    column['__leaf'] = true;
                    return 1;
                }
                var leafcount = 0;
                var newid = -1;
                for (var i = 0, l = column.columns.length; i < l; i++)
                {
                    var col = column.columns[i];
                    leafcount += setColumn(col, level + 1, column['__id'], newid);
                    newid = col['__id'];
                }
                column['__leafcount'] = leafcount;
                return leafcount;
            }
            var lastid = -1;
            //行序号
            if (p.rownumbers)
            {
                var frozenRownumbers = g.enabledGroup() ? false : p.frozen && p.frozenRownumbers;
                var col = { isrownumber: true, issystem: true, width: p.rownumbersColWidth, frozen: frozenRownumbers };
                setColumn(col, 1, -1, lastid);
                lastid = col['__id'];
            }
            //明细列
            if (g.enabledDetail())
            {
                var frozenDetail = g.enabledGroup() ? false : p.frozen && p.frozenDetail;
                var col = { isdetail: true, issystem: true, width: p.detailColWidth, frozen: frozenDetail };
                setColumn(col, 1, -1, lastid);
                lastid = col['__id'];
            }
            //复选框列
            if (g.enabledCheckbox())
            {
                var frozenCheckbox = g.enabledGroup() ? false : p.frozen && p.frozenCheckbox;
                var col = { ischeckbox: true, issystem: true, width: p.detailColWidth, frozen: frozenCheckbox };
                setColumn(col, 1, -1, lastid);
                lastid = col['__id'];
            }
            for (var i = 0, l = p.columns.length; i < l; i++)
            {
                 var col = p.columns[i];
                //增加以下一句. 因为在低版本IE中, 可能因为修改了prototype, 
                //导致这里取出undefined, 并进一步导致后续的函数调用出错. 
                if (!col) continue;
                setColumn(col, 1, -1, lastid);
                lastid = col['__id'];
            }
            //设置colSpan和rowSpan
            for (var id in g._columns)
            {
                var col = g._columns[id];
                if (col['__leafcount'] > 1)
                {
                    col['__colSpan'] = col['__leafcount'];
                }
                if (col['__leaf'] && col['__level'] != g._columnMaxLevel)
                {
                    col['__rowSpan'] = g._columnMaxLevel - col['__level'] + 1;
                }
            }
            //叶级别列的信息  
            g.columns = g.getColumns();
            $(g.columns).each(function (i, column)
            {
                column.columnname = column.name;
                column.columnindex = i;
                column.type = column.type || "string";
                column.islast = i == g.columns.length - 1;
                column.isSort = column.isSort == false ? false : true;
                column.frozen = column.frozen ? true : false;
                column._width = g._getColumnWidth(column);
                column._hide = column.hide ? true : false;
                
                // mxq 2015-06-19 add
                if (column.editor) {
                	if (g.firstEditColIndex == null) {
                		g.firstEditColIndex = i;
                	}
                	g.lastEditColIndex = i;
                }
            });
        },
         /**
         *@override:重写  mxq 2014-11-26 修复表格中有隐藏列汇总行列出现错位的问题
         */
        _getTotalSummaryHtml: function (data, classCssName, frozen)
        {
            var g = this, p = this.options;
            var totalsummaryArr = [];
            if (classCssName)
                totalsummaryArr.push('<tr class="l-grid-totalsummary ' + classCssName + '">');
            else
                totalsummaryArr.push('<tr class="l-grid-totalsummary">');
            $(g.columns).each(function (columnindex, column)
            {
                if (this.frozen != frozen) return;
                //如果是行序号(系统列)
                if (this.isrownumber)
                {
                    totalsummaryArr.push('<td class="l-grid-totalsummary-cell l-grid-totalsummary-cell-rownumbers" style="width:' + this.width + 'px"><div>&nbsp;</div></td>');
                    return;
                }
                //如果是复选框(系统列)
                if (this.ischeckbox)
                {
                    totalsummaryArr.push('<td class="l-grid-totalsummary-cell l-grid-totalsummary-cell-checkbox" style="width:' + this.width + 'px"><div>&nbsp;</div></td>');
                    return;
                }
                    //如果是明细列(系统列)
                else if (this.isdetail)
                {
                    totalsummaryArr.push('<td class="l-grid-totalsummary-cell l-grid-totalsummary-cell-detail" style="width:' + this.width + 'px"><div>&nbsp;</div></td>');
                    return;
                }
                totalsummaryArr.push('<td class="l-grid-totalsummary-cell');
                if (this.islast)
                    totalsummaryArr.push(" l-grid-totalsummary-cell-last");
                totalsummaryArr.push('" ');
                totalsummaryArr.push('id="' + g.id + "|total" + g.totalNumber + "|" + column.__id + '" ');
                //totalsummaryArr.push('width="' + this._width + '" ');
              	totalsummaryArr.push(' style = "');
                totalsummaryArr.push('width:' + this._width + 'px; ');
                if (column._hide)
                {
                    totalsummaryArr.push('display:none;');
                }
                totalsummaryArr.push('"');
                columnname = this.columnname;
                if (columnname)
                {
                    totalsummaryArr.push('columnname="' + columnname + '" ');
                }
                totalsummaryArr.push('columnindex="' + columnindex + '" ');
                totalsummaryArr.push('><div class="l-grid-totalsummary-cell-inner"');
                if (column.align)
                    totalsummaryArr.push(' style="text-Align:' + column.align + ';"');
                totalsummaryArr.push('>');
                totalsummaryArr.push(g._getTotalCellContent(column, data));
                totalsummaryArr.push('</div></td>');
            });
            totalsummaryArr.push('</tr>');
            if (!frozen) g.totalNumber++;
            return totalsummaryArr.join('');
        },
        
        /**
         *@override:重写 修复显示/隐藏列不会出现滚动条的问题
         */
         _onContextmenu: function (e)
        {
            var g = this, p = this.options;
            var src = g._getSrcElementByEvent(e);
            if (src.row)
            {
                if (p.whenRClickToSelect)
                    g.select(src.data);
                if (g.hasBind('contextmenu'))
                {
                    return g.trigger('contextmenu', [{ data: src.data, rowindex: src.data['__index'], row: src.row }, e]);
                }
            }
            else if (src.hcell)
            {
                if (!p.allowHideColumn) return true;
                var columnindex = $(src.hcell).attr("columnindex");
                if (columnindex == undefined) return true;
                var left = (e.pageX - g.body.offset().left + parseInt(g.body[0].scrollLeft));
                if (columnindex == g.columns.length - 1) left -= 50;
                g.popup.css({ left: left, top: g.gridheader.height() + 1 }); 
                if (g.popup.height() > g.body.height()) {// mxq 2014-10-31 add height css属性，为解决显示/隐藏列不会出现滚动条的问题
                	g.popup.css({ left: left, top: g.gridheader.height() + 1, height: g.body.height() }); 
                }
                g.popup.toggle();
                return false;
            }
        },
        /**
         *@override:重写 
         */
        _getSrcElementByEvent: function (e)
        {
            var g = this, p = this.options;
            var obj = (e.target || e.srcElement);
            var jobjs = $(obj).parents().add(obj);
            var fn = function (parm)
            {
                for (var i = 0, l = jobjs.length; i < l; i++)
                {
                    if (typeof parm == "string")
                    {
                        if ($(jobjs[i]).hasClass(parm)) return jobjs[i];
                    }
                    else if (typeof parm == "object")
                    {
                        if (jobjs[i] == parm) return jobjs[i];
                    }
                }
                return null;
            };
            if (fn("l-grid-editor")) return { editing: true, editor: fn("l-grid-editor") };
            if (jobjs.index(this.element) == -1)
            {
                if (g._isEditing(jobjs)) return { editing: true };
                else return { out: true };
            }
            var indetail = false;
            if (jobjs.hasClass("l-grid-detailpanel") && g.detailrows)
            {
                for (var i = 0, l = g.detailrows.length; i < l; i++)
                {
                    if (jobjs.index(g.detailrows[i]) != -1)
                    {
                        indetail = true;
                        break;
                    }
                }
            }
            var r = {
                grid: fn("l-panel"),
                indetail: indetail,
                frozen: fn(g.gridview1[0]) ? true : false,
                header: fn("l-panel-header"), //标题
                gridheader: fn("l-grid-header"), //表格头 
                gridbody: fn("l-grid-body"),
                total: fn("l-panel-bar-total"), //总汇总 
                popup: fn("l-grid-popup"),
                toolbar: fn("l-panel-bar")
            };
            if (r.gridheader)
            {
                r.hrow = fn("l-grid-hd-row");
                r.hcell = fn("l-grid-hd-cell");
                r.hcelltext = fn("l-grid-hd-cell-text");
                r.checkboxall = fn("l-grid-hd-cell-checkbox");
                if (r.hcell)
                {
                    var columnid = r.hcell.id.split('|')[2];
                    r.column = g._columns[columnid];
                }
            }
            if (r.gridbody)
            {
                r.row = fn("l-grid-row");
                r.cell = fn("l-grid-row-cell");
                r.checkbox = fn("l-grid-row-cell-btn-checkbox");
                r.groupbtn = fn("l-grid-group-togglebtn");
                r.grouprow = fn("l-grid-grouprow");
                r.detailbtn = fn("l-grid-row-cell-detailbtn");
                r.detailrow = fn("l-grid-detailpanel");
                r.totalrow = fn("l-grid-totalsummary");
                r.totalcell = fn("l-grid-totalsummary-cell");
                r.rownumberscell = $(r.cell).hasClass("l-grid-row-cell-rownumbers") ? r.cell : null;
                r.detailcell = $(r.cell).hasClass("l-grid-row-cell-detail") ? r.cell : null;
                r.checkboxcell = $(r.cell).hasClass("l-grid-row-cell-checkbox") ? r.cell : null;
                r.treelink = fn("l-grid-tree-link");
                r.editor = fn("l-grid-editor");
                if (r.row) r.data = this._getRowByDomId(r.row.id);
                if (r.cell) r.editing = $(r.cell).hasClass("l-grid-row-cell-editing");
                if (r.editor) r.editing = true;
                if (r.editing) r.out = false;
            }
            if (r.toolbar)
            {
                r.first = fn("l-bar-btnfirst");
                r.last = fn("l-bar-btnlast");
                r.next = fn("l-bar-btnnext");
                r.prev = fn("l-bar-btnprev");
                r.load = fn("l-bar-btnload");
                r.button = fn("l-bar-button");
                if (p.exportBtn) {
	                r.exportFile = fn("l-bar-btnexport");
                }
            }

            return r;
        },
        /** 为了添加可编辑表格单元格上下左右方向键事件 */
        _setEvent: function ()
        {
            var g = this, p = this.options;
            g.grid.bind("mousedown.grid", function (e)
            {
                g._onMouseDown.call(g, e);
            });
            g.grid.bind("dblclick.grid", function (e)
            {
                g._onDblClick.call(g, e);
            });
            g.grid.bind("contextmenu.grid", function (e)
            {
                return g._onContextmenu.call(g, e);
            });
             g.grid.bind("keydown.grid", function (e)
            {
            	if (e.ctrlKey) g.ctrlKey = true;
                // 以下是mxq 2015-06-18 add
            	var key = e.keyCode;
                if (g.currentCell != null && (key == 37 || key == 38 || key == 39 || key == 40 || key == 13)) {
	                g._onEditorKey.call(g, g.currentCell, e);
                }
            });
            $(document).bind("mouseup.grid", function (e)
            {
                g._onMouseUp.call(g, e);
            });
            $(document).bind("click.grid", function (e)
            {
                g._onClick.call(g, e);
            });
            $(window).bind("resize.grid", function (e)
            {
                g._onResize.call(g);
            });
            
            $(document).bind("keydown.grid", function (e)
            {
                if (e.ctrlKey) g.ctrlKey = true;
            });
            $(document).bind("keyup.grid", function (e)
            {
                delete g.ctrlKey;
            });
            //表体 - 滚动联动事件 
            g.gridbody.bind('scroll.grid', function ()
            {
                var scrollLeft = g.gridbody.scrollLeft();
                var scrollTop = g.gridbody.scrollTop();
                if (scrollLeft != null)
                    g.gridheader[0].scrollLeft = scrollLeft;
                if (scrollTop != null)
                    g.f.gridbody[0].scrollTop = scrollTop;
                
                // 滚动分页事件
                if (p.scrollToPage && p.usePager && !g.loading)
                {
                    var innerHeight = g.gridbody.find(".l-grid-body-inner:first").height();
                    var toHeight = scrollTop + g.gridbody.height();
                    if (p.scrollToAppend)
                    {
                        if (p.newPage != p.pageCount)
                        {
                            if (toHeight >= innerHeight)
                            {
		                        g.loadData(p.newPage + 1, "scrollappend");
                            }
                        }
                    } else
                    { 
                        var topage = toHeight >= innerHeight ? p.pageCount : Math.ceil(toHeight / g._getOnePageHeight());
                        if (!g.scrollLoading)
                        {
                            g.scrollLoading = true;
                            g.lastScrollTop = scrollTop;
                            g.unbind("sysScrollLoaded");
                            g.bind("sysScrollLoaded", function ()
                            {  
                                g.gridbody.scrollTop(scrollTop);
                                setTimeout(function ()
                                {
                                    g.scrollLoading = false;
                                }, 500);
                            });
                            g.scrollLoading = true;

                            g.loadData(topage, "scroll");
                             
                        }
                    }
                }
                if (g.scrollIsAutoTrigger) {
                	g.scrollIsAutoTrigger = false;
                }else {
                	g.endEdit(); // 若滚动条不是手动触发的，则将表格结束编辑 mxq 2016-01-04 add
                }
                g.trigger('SysGridHeightChanged');
            });
            //工具条 - 切换每页记录数事件
            $('select', g.toolbar).change(function ()
            {
                if (g.isDataChanged && p.dataAction != "local" && !confirm(p.isContinueByDataChanged))
                    return false;
                p.newPage = 1;
                p.pageSize = this.value;
                g.loadData(p.dataAction != "local" ? p.where : false);
            });
            //工具条 - 切换当前页事件
            $('span.pcontrol :text', g.toolbar).blur(function (e)
            {
                g.changePage('input');
            });
            $("div.l-bar-button", g.toolbar).hover(function ()
            {
                $(this).addClass("l-bar-button-over");
            }, function ()
            {
                $(this).removeClass("l-bar-button-over");
            });
            //列拖拽支持
            if ($.fn.ligerDrag && p.colDraggable)
            {
                g.colDroptip = $("<div class='l-drag-coldroptip' style='display:none'><div class='l-drop-move-up'></div><div class='l-drop-move-down'></div></div>").appendTo('body');
                g.gridheader.add(g.f.gridheader).ligerDrag({
                    revert: true, animate: false,
                    proxyX: 0, proxyY: 0,
                    proxy: function (draggable, e)
                    {
                        var src = g._getSrcElementByEvent(e);
                        if (src.hcell && src.column)
                        {
                            var content = $(".l-grid-hd-cell-text:first", src.hcell).html();
                            var proxy = $("<div class='l-drag-proxy' style='display:none'><div class='l-drop-icon l-drop-no'></div></div>").appendTo('body');
                            proxy.append(content);
                            return proxy;
                        }
                    },
                    onRevert: function () { return false; },
                    onRendered: function ()
                    {
                        this.set('cursor', 'default');
                        g.children[this.id] = this;
                    },
                    onStartDrag: function (current, e)
                    {
                        if (e.button == 2) return false;
                        if (g.colresizing) return false;
                        this.set('cursor', 'default');
                        var src = g._getSrcElementByEvent(e);
                        if (!src.hcell || !src.column || src.column.issystem || src.hcelltext) return false;
                        if ($(src.hcell).css('cursor').indexOf('resize') != -1) return false;
                        this.draggingColumn = src.column;
                        g.coldragging = true;

                        var gridOffset = g.grid.offset();
                        this.validRange = {
                            top: gridOffset.top,
                            bottom: gridOffset.top + g.gridheader.height(),
                            left: gridOffset.left - 10,
                            right: gridOffset.left + g.grid.width() + 10
                        };
                    },
                    onDrag: function (current, e)
                    {
                        this.set('cursor', 'default');
                        var column = this.draggingColumn;
                        if (!column) return false;
                        if (g.colresizing) return false;
                        if (g.colDropIn == null)
                            g.colDropIn = -1;
                        var pageX = e.pageX;
                        var pageY = e.pageY;
                        var visit = false;
                        var gridOffset = g.grid.offset();
                        var validRange = this.validRange;
                        if (pageX < validRange.left || pageX > validRange.right
                            || pageY > validRange.bottom || pageY < validRange.top)
                        {
                            g.colDropIn = -1;
                            g.colDroptip.hide();
                            this.proxy.find(".l-drop-icon:first").removeClass("l-drop-yes").addClass("l-drop-no");
                            return;
                        }
                        for (var colid in g._columns)
                        {
                            var col = g._columns[colid];
                            if (column == col)
                            {
                                visit = true;
                                continue;
                            }
                            if (col.issystem) continue;
                            var sameLevel = col['__level'] == column['__level'];
                            var isAfter = !sameLevel ? false : visit ? true : false;
                            if (column.frozen != col.frozen) isAfter = col.frozen ? false : true;
                            if (g.colDropIn != -1 && g.colDropIn != colid) continue;
                            var cell = document.getElementById(col['__domid']);
                            var offset = $(cell).offset();
                            var range = {
                                top: offset.top,
                                bottom: offset.top + $(cell).height(),
                                left: offset.left - 10,
                                right: offset.left + 10
                            };
                            if (isAfter)
                            {
                                var cellwidth = $(cell).width();
                                range.left += cellwidth;
                                range.right += cellwidth;
                            }
                            if (pageX > range.left && pageX < range.right && pageY > range.top && pageY < range.bottom)
                            {
                                var height = p.headerRowHeight;
                                if (col['__rowSpan']) height *= col['__rowSpan'];
                                g.colDroptip.css({
                                    left: range.left + 5,
                                    top: range.top - 9,
                                    height: height + 9 * 2
                                }).show();
                                g.colDropIn = colid;
                                g.colDropDir = isAfter ? "right" : "left";
                                this.proxy.find(".l-drop-icon:first").removeClass("l-drop-no").addClass("l-drop-yes");
                                break;
                            }
                            else if (g.colDropIn != -1)
                            {
                                g.colDropIn = -1;
                                g.colDroptip.hide();
                                this.proxy.find(".l-drop-icon:first").removeClass("l-drop-yes").addClass("l-drop-no");
                            }
                        }
                    },
                    onStopDrag: function (current, e)
                    {
                        var column = this.draggingColumn;
                        g.coldragging = false;
                        if (g.colDropIn != -1)
                        {
                            g.changeCol.ligerDefer(g, 0, [column, g.colDropIn, g.colDropDir == "right"]);
                            g.colDropIn = -1;
                        }
                        g.colDroptip.hide();
                        this.set('cursor', 'default');
                    }
                });
            }
            //行拖拽支持
            if ($.fn.ligerDrag && p.rowDraggable)
            {
                g.rowDroptip = $("<div class='l-drag-rowdroptip' style='display:none'></div>").appendTo('body');
                g.gridbody.add(g.f.gridbody).ligerDrag({
                    revert: true, animate: false,
                    proxyX: 0, proxyY: 0,
                    proxy: function (draggable, e)
                    {
                        var src = g._getSrcElementByEvent(e);
                        if (src.row)
                        {
                            var content = p.draggingMessage.replace(/{count}/, draggable.draggingRows ? draggable.draggingRows.length : 1);
                            if (p.rowDraggingRender)
                            {
                                content = p.rowDraggingRender(draggable.draggingRows, draggable, g);
                            }
                            var proxy = $("<div class='l-drag-proxy' style='display:none'><div class='l-drop-icon l-drop-no'></div>" + content + "</div>").appendTo('body');
                            return proxy;
                        }
                    },
                    onRevert: function () { return false; },
                    onRendered: function ()
                    {
                        this.set('cursor', 'default');
                        g.children[this.id] = this;
                    },
                    onStartDrag: function (current, e)
                    {
                        if (e.button == 2) return false;
                        if (g.colresizing) return false;
                        if (!g.columns.length) return false;
                        this.set('cursor', 'default');
                        var src = g._getSrcElementByEvent(e);
                        if (!src.cell || !src.data || src.checkbox) return false;
                        var ids = src.cell.id.split('|');
                        var column = g._columns[ids[ids.length - 1]];
                        if (src.rownumberscell || src.detailcell || src.checkboxcell || column == g.columns[0])
                        {
                            if (g.enabledCheckbox())
                            {
                                this.draggingRows = g.getSelecteds();
                                if (!this.draggingRows || !this.draggingRows.length) return false;
                            }
                            else
                            {
                                this.draggingRows = [src.data];
                            }
                            this.draggingRow = src.data;
                            this.set('cursor', 'move');
                            g.rowdragging = true;
                            this.validRange = {
                                top: g.gridbody.offset().top,
                                bottom: g.gridbody.offset().top + g.gridbody.height(),
                                left: g.grid.offset().left - 10,
                                right: g.grid.offset().left + g.grid.width() + 10
                            };
                        }
                        else
                        {
                            return false;
                        }
                    },
                    onDrag: function (current, e)
                    {
                        var rowdata = this.draggingRow;
                        if (!rowdata) return false;
                        var rows = this.draggingRows ? this.draggingRows : [rowdata];
                        if (g.colresizing) return false;
                        if (g.rowDropIn == null) g.rowDropIn = -1;
                        var pageX = e.pageX;
                        var pageY = e.pageY;
                        var visit = false;
                        var validRange = this.validRange;
                        if (pageX < validRange.left || pageX > validRange.right
                            || pageY > validRange.bottom || pageY < validRange.top)
                        {
                            g.rowDropIn = -1;
                            g.rowDroptip.hide();
                            this.proxy.find(".l-drop-icon:first").removeClass("l-drop-yes l-drop-add").addClass("l-drop-no");
                            return;
                        }
                        for (var i in g.rows)
                        {
                            var rd = g.rows[i];
                            var rowid = rd['__id'];
                            if (rowdata == rd) visit = true;
                            if ($.inArray(rd, rows) != -1) continue;
                            var isAfter = visit ? true : false;
                            if (g.rowDropIn != -1 && g.rowDropIn != rowid) continue;
                            var rowobj = g.getRowObj(rowid);
                            var offset = $(rowobj).offset();
                            var range = {
                                top: offset.top - 4,
                                bottom: offset.top + $(rowobj).height() + 4,
                                left: g.grid.offset().left,
                                right: g.grid.offset().left + g.grid.width()
                            };
                            if (pageX > range.left && pageX < range.right && pageY > range.top && pageY < range.bottom)
                            {
                                var lineTop = offset.top;
                                if (isAfter) lineTop += $(rowobj).height();
                                g.rowDroptip.css({
                                    left: range.left,
                                    top: lineTop,
                                    width: range.right - range.left
                                }).show();
                                g.rowDropIn = rowid;
                                g.rowDropDir = isAfter ? "bottom" : "top";
                                if (p.tree && pageY > range.top + 5 && pageY < range.bottom - 5)
                                {
                                    this.proxy.find(".l-drop-icon:first").removeClass("l-drop-no l-drop-yes").addClass("l-drop-add");
                                    g.rowDroptip.hide();
                                    g.rowDropInParent = true;
                                }
                                else
                                {
                                    this.proxy.find(".l-drop-icon:first").removeClass("l-drop-no l-drop-add").addClass("l-drop-yes");
                                    g.rowDroptip.show();
                                    g.rowDropInParent = false;
                                }
                                break;
                            }
                            else if (g.rowDropIn != -1)
                            {
                                g.rowDropIn = -1;
                                g.rowDropInParent = false;
                                g.rowDroptip.hide();
                                this.proxy.find(".l-drop-icon:first").removeClass("l-drop-yes  l-drop-add").addClass("l-drop-no");
                            }
                        }
                    },
                    onStopDrag: function (current, e)
                    {
                        var rows = this.draggingRows;
                        g.rowdragging = false;
                        for (var i = 0; i < rows.length; i++)
                        {
                            var children = rows[i].children;
                            if (children)
                            {
                                rows = $.grep(rows, function (node, i)
                                {
                                    var isIn = $.inArray(node, children) == -1;
                                    return isIn;
                                });
                            }
                        }
                        if (g.rowDropIn != -1)
                        {
                            if (p.tree)
                            {
                                var neardata, prow;
                                if (g.rowDropInParent)
                                {
                                    prow = g.getRow(g.rowDropIn);
                                }
                                else
                                {
                                    neardata = g.getRow(g.rowDropIn);
                                    prow = g.getParent(neardata);
                                }
                                g.appendRange(rows, prow, neardata, g.rowDropDir != "bottom");
                                g.trigger('rowDragDrop', {
                                    rows: rows,
                                    parent: prow,
                                    near: neardata,
                                    after: g.rowDropDir == "bottom"
                                });
                            }
                            else
                            {
                                g.moveRange(rows, g.rowDropIn, g.rowDropDir == "bottom");
                                g.trigger('rowDragDrop', {
                                    rows: rows,
                                    parent: prow,
                                    near: g.getRow(g.rowDropIn),
                                    after: g.rowDropDir == "bottom"
                                });
                            }

                            g.rowDropIn = -1;
                        }
                        g.rowDroptip.hide();
                        this.set('cursor', 'default');
                    }
                });
            }
        },
        /**
         *  mxq 2015-06-18 add 上下左右方向键及enter键事件，
         *  可以实现可编辑单元格上下左右移动
         */
        _onEditorKey: function(cellObj, e) {
        	
        	var g = this, p = this.options;
        	if (!cellObj) return; 
        	
            var rowcell = cellObj, ids = rowcell.id.split('|');
            var columnid = ids[ids.length - 1], column = g._columns[columnid];
            var row = $(rowcell).parent(), rowdata = g.getRow(row[0]), rowindex = rowdata['__index'];
            var columnindex = column.columnindex;
            if (g.editor.editing) g.endEdit(); // 按enter键完成编辑，按左右上下方向键时结束上次的编辑
            
            var key = e.keyCode;
            if (key == 37) { // 左方向键
        		columnindex = columnindex - 1;
        		if (columnindex >= g.firstEditColIndex) {
            		for (var i = columnindex; i >= g.firstEditColIndex; i--) {
	            		column = g.getColumn(i);
	            		if (column && p.enabledEdit) {
	            			if (!column.editor) {
	            				continue;
	            			}else {
        						g.scrollAuto(row, rowindex, i, true, 'left');
	            				cellObj = g.getCellObj(rowdata, i);
        						g._applyEditor(cellObj);
        						break;
	            			}
	            		}
            		}
        		}else {
        			rowindex = rowindex - 1;
            		rowdata = g.getRow(rowindex);
            		if (rowdata) {
            			columnindex = g.lastEditColIndex;
            			g.scrollAuto(row, rowindex, columnindex, true, 'left');
            			cellObj = g.getCellObj(rowdata, columnindex);
            			g._applyEditor(cellObj);
            		}
        		}
        		
        	}else if (key == 39) { // 右方向键
        		columnindex = columnindex + 1;
        		if (columnindex <= g.lastEditColIndex) {
            		for (var i = columnindex; i <= g.lastEditColIndex; i++) {
	            		column = g.getColumn(i);
	            		if (column && p.enabledEdit) {
	            			if (!column.editor) {
	            				continue;
	            			}else {
        						g.scrollAuto(row, rowindex, i, true, 'right');
	            				cellObj = g.getCellObj(rowdata, i);
        						g._applyEditor(cellObj);
        						break;
	            			}
	            		}
            		}
        		}else {
        			rowindex = rowindex + 1;
            		rowdata = g.getRow(rowindex);
            		if (rowdata) {
            			columnindex = g.firstEditColIndex;
            			g.scrollAuto(row, rowindex, columnindex, true, 'right');
            			cellObj = g.getCellObj(rowdata, columnindex);
            			g._applyEditor(cellObj);
            		}
        		}
        	}else if (key == 38) { // 上方向键
        		rowindex = rowindex - 1;
        		rowdata = g.getRow(rowindex);
        		if (rowdata) {
	        		g.scrollAuto(row, rowindex, columnindex, false, 'up');
        			cellObj = g.getCellObj(rowdata, columnindex);
        			g._applyEditor(cellObj);
        		}
            }else if (key == 40) { // 下方向键
            	rowindex = rowindex + 1;
        		rowdata = g.getRow(rowindex);
        		if (rowdata) {
	        		g.scrollAuto(row, rowindex, columnindex, false, 'down');
        			cellObj = g.getCellObj(rowdata, columnindex);
        			g._applyEditor(cellObj);
        		}
            }
        },
        /** 滚动条自动滚动
         * @author mxq
         * @createDate 2016-02-04
         * @param
         * 1、row 数据行对象
         * 2、rowindex 行索引
         * 3、ishorizontal 是否是水平滚动条
         * 4、orientation 滚动条滚动方向，是向上、下、左、右
         */
        scrollAuto: function(row, rowindex, columnindex, ishorizontal, orientation) {
        	var g = this, p = this.options;
            if (ishorizontal) { // 若是水平滚动条
	        	var scrollLeft = g.gridbody.scrollLeft();
	        	var gridBodyWidth = g.gridbody.width();
	        	var col =  g.columns[columnindex];
	        	if (orientation == 'left' || orientation == 'right') {
	        		g.scrollAuto(row, rowindex, columnindex, false, 'down');
	    			var totalColWidth = 0;
		        	for (var i = 0; i <= columnindex; i ++) {
		        		var column = g.columns[i];
		        		if (column.isrownumber || column.ischeckbox || column._hide) 
		        			continue;
		        		totalColWidth += column._width;
		        	}
		        	if (totalColWidth > gridBodyWidth) {
		        		if (scrollLeft != null) {
			    			scrollLeft = totalColWidth - gridBodyWidth;
			    			// 垂直滚动条误差
			    			scrollLeft += 40;
		        			g.gridbody.scrollLeft(scrollLeft);
		        			g.scrollIsAutoTrigger = true; // 自动触发滚动条
		    			}
		        	}else {
		        		if (scrollLeft != 0) {
		        			scrollLeft = 0;
			        		g.gridbody.scrollLeft(scrollLeft);
			        		g.scrollIsAutoTrigger = true; // 自动触发滚动条
		        		}
		        	}
	        	}
            }else {
            	// 若是垂直滚动条
	            var scrollTop = g.gridbody.scrollTop();
	        	var gridBodyHeight = g.gridbody.height();
	        	if (orientation == 'down' || orientation == 'up') {
		        	var rowNum = rowindex + 1;
		        	var currentHeight = rowNum * row.height();
	    			if (g.gridview.hasClass("l-grid-hashorizontal")) {
	        			currentHeight += 35;
	        		}
		        	if (currentHeight > gridBodyHeight) {
		    			if (scrollTop != null) {
			    			scrollTop = currentHeight - gridBodyHeight;
		        			g.gridbody.scrollTop(scrollTop);
		        			g.scrollIsAutoTrigger = true; // 自动触发滚动条
		    			}
		        	}else {
		        		if (scrollTop != 0) {
		        			scrollTop = 0;
			        		g.gridbody.scrollTop(scrollTop);
			        		g.scrollIsAutoTrigger = true; // 自动触发滚动条
		        		}
		        	}
	        	}
            }
        },
        /**
         * @override: 获得第一页的高度
         */
        _getOnePageHeight : function()
        {
            var g = this, p = this.options;
            return (parseFloat(p.rowHeight || 24) + 1)*parseInt(p.pageSize);
        },
         _setData: function (value)
        {
            this.loadData(this.options.data);
            this.trigger('afterSetData');
        },
        /**
         * @override: 重写
         */
        _onClick: function (e)
        {
            var obj = (e.target || e.srcElement);
            var g = this, p = this.options;
            var src = g._getSrcElementByEvent(e);
            if (src.out)
            {
                if (g.editor.editing && !$.ligerui.win.masking) g.endEdit();
                if (p.allowHideColumn) g.popup.hide();
                return;
            }
            if (src.indetail || src.editing)
            {
                return;
            }
            if (g.editor.editing)
            {
                g.endEdit();
            }
            if (p.allowHideColumn)
            {
                if (!src.popup)
                {
                    g.popup.hide();
                }
            }
            if (src.checkboxall) //复选框全选
            {
                var row = $(src.hrow);
                var uncheck = row.hasClass("l-checked");
                if (g.trigger('beforeCheckAllRow', [!uncheck, g.element]) == false) return false;
                if (uncheck){
                    row.removeClass("l-checked");
                }
                else{
                    row.addClass("l-checked");
                }
                g.selected = [];
                for (var rowid in g.records){
                    if (uncheck)
                        g.unselect(g.records[rowid]);
                    else
                        g.select(g.records[rowid]);
                }
                g.trigger('checkAllRow', [!uncheck, g.element]);
            }
            else if (src.hcelltext) //排序
            {
                var hcell = $(src.hcelltext).parent().parent();
                if (!p.enabledSort || !src.column) return;
                if (src.column.isSort == false) return;
                if (p.url && p.dataAction != "local" && g.isDataChanged && !confirm(p.isContinueByDataChanged)) return;
                var sort = $(".l-grid-hd-cell-sort:first", hcell);
                var columnName = src.column.name;
                if (!columnName) return;
                if (sort.length > 0)
                {
                    if (sort.hasClass("l-grid-hd-cell-sort-asc"))
                    {
                        sort.removeClass("l-grid-hd-cell-sort-asc").addClass("l-grid-hd-cell-sort-desc");
                        hcell.removeClass("l-grid-hd-cell-asc").addClass("l-grid-hd-cell-desc");
                        g.changeSort(columnName, 'desc');
                    }
                    else if (sort.hasClass("l-grid-hd-cell-sort-desc"))
                    {
                        sort.removeClass("l-grid-hd-cell-sort-desc").addClass("l-grid-hd-cell-sort-asc");
                        hcell.removeClass("l-grid-hd-cell-desc").addClass("l-grid-hd-cell-asc");
                        g.changeSort(columnName, 'asc');
                    }
                }
                else
                {
                    hcell.removeClass("l-grid-hd-cell-desc").addClass("l-grid-hd-cell-asc");
                    $(src.hcelltext).after("<span class='l-grid-hd-cell-sort l-grid-hd-cell-sort-asc'>&nbsp;&nbsp;</span>");
                    g.changeSort(columnName, 'asc');
                }
                $(".l-grid-hd-cell-sort", g.gridheader).add($(".l-grid-hd-cell-sort", g.f.gridheader)).not($(".l-grid-hd-cell-sort:first", hcell)).remove();
            }
                //明细
            else if (src.detailbtn && p.detail)
            {
                var item = src.data;
                var row = $([g.getRowObj(item, false)]);
                if (g.enabledFrozen()) row = row.add(g.getRowObj(item, true));
                var rowid = item['__id'];
                if ($(src.detailbtn).hasClass("l-open"))
                {
                    if (p.detail.onCollapse)
                        p.detail.onCollapse(item, $(".l-grid-detailpanel-inner:first", nextrow)[0]);
                    row.next("tr.l-grid-detailpanel").hide();
                    $(src.detailbtn).removeClass("l-open");
                }
                else
                {
                    var nextrow = row.next("tr.l-grid-detailpanel");
                    if (nextrow.length > 0)
                    {
                        nextrow.show();
                        if (p.detail.onExtend)
                            p.detail.onExtend(item, $(".l-grid-detailpanel-inner:first", nextrow)[0]);
                        $(src.detailbtn).addClass("l-open");
                        g.trigger('SysGridHeightChanged');
                        return;
                    }
                    $(src.detailbtn).addClass("l-open");
                    var frozenColNum = 0;
                    for (var i = 0; i < g.columns.length; i++)
                        if (g.columns[i].frozen) frozenColNum++;
                    var detailRow = $("<tr class='l-grid-detailpanel'><td><div class='l-grid-detailpanel-inner' style='display:none'></div></td></tr>");
                    var detailFrozenRow = $("<tr class='l-grid-detailpanel'><td><div class='l-grid-detailpanel-inner' style='display:none'></div></td></tr>");
                    detailRow.attr("id", g.id + "|detail|" + rowid);
                    g.detailrows = g.detailrows || [];
                    g.detailrows.push(detailRow[0]);
                    g.detailrows.push(detailFrozenRow[0]);
                    var detailRowInner = $("div:first", detailRow);
                    detailRowInner.parent().attr("colSpan", g.columns.length - frozenColNum);
                    row.eq(0).after(detailRow);
                    if (frozenColNum > 0)
                    {
                        detailFrozenRow.find("td:first").attr("colSpan", frozenColNum);
                        row.eq(1).after(detailFrozenRow);
                    }
                    if (p.detail.onShowDetail)
                    {
                        p.detail.onShowDetail(item, detailRowInner[0], function ()
                        {
                            g.trigger('SysGridHeightChanged');
                        });
                        $("div:first", detailFrozenRow).add(detailRowInner).show().height(p.detail.height || p.detailHeight);
                    }
                    else if (p.detail.render)
                    {
                        detailRowInner.append(p.detail.render());
                        detailRowInner.show();
                    }
                    g.trigger('SysGridHeightChanged');
                }
            }
            else if (src.groupbtn)
            {
                var grouprow = $(src.grouprow);
                var opening = true;
                if ($(src.groupbtn).hasClass("l-grid-group-togglebtn-close"))
                {
                    $(src.groupbtn).removeClass("l-grid-group-togglebtn-close");

                    if (grouprow.hasClass("l-grid-grouprow-last"))
                    {
                        $("td:first", grouprow).width('auto');
                    }
                }
                else
                {
                    opening = false;
                    $(src.groupbtn).addClass("l-grid-group-togglebtn-close");
                    if (grouprow.hasClass("l-grid-grouprow-last"))
                    {
                        $("td:first", grouprow).width(g.gridtablewidth);
                    }
                }
                var currentRow = grouprow.next(".l-grid-row,.l-grid-totalsummary-group,.l-grid-detailpanel");
                while (true)
                {
                    if (currentRow.length == 0) break;
                    if (opening)
                    {
                        currentRow.show();
                        //如果是明细展开的行，并且之前的状态已经是关闭的，隐藏之
                        if (currentRow.hasClass("l-grid-detailpanel") && !currentRow.prev().find("td.l-grid-row-cell-detail:first span.l-grid-row-cell-detailbtn:first").hasClass("l-open"))
                        {
                            currentRow.hide();
                        }
                    }
                    else
                    {
                        currentRow.hide();
                    }
                    currentRow = currentRow.next(".l-grid-row,.l-grid-totalsummary-group,.l-grid-detailpanel");
                }
                g.trigger(opening ? 'groupExtend' : 'groupCollapse');
                g.trigger('SysGridHeightChanged');
            }
                //树 - 伸展/收缩节点
            else if (src.treelink)
            {
                g.toggle(src.data);
            }
            else if (src.row && g.enabledCheckbox()) //复选框选择行
            {
                //复选框
                var selectRowButtonOnly = p.selectRowButtonOnly ? true : false;
                if (p.enabledEdit) selectRowButtonOnly = true;
                if (src.checkbox || !selectRowButtonOnly)
                {
                    var row = $(src.row);
                    var uncheck = row.hasClass("l-selected");
                    if (g.trigger('beforeCheckRow', [!uncheck, src.data, src.data['__id'], src.row]) == false)
                        return false;
                    var met = uncheck ? 'unselect' : 'select';
                    g[met](src.data);
                    if (p.tree && p.autoCheckChildren)
                    {
                        var children = g.getChildren(src.data, true);
                        for (var i = 0, l = children.length; i < l; i++)
                        {
                            g[met](children[i]);
                        }
                    }
                    g.trigger('checkRow', [!uncheck, src.data, src.data['__id'], src.row]);
                }
                if (!src.checkbox && src.cell && p.enabledEdit && p.clickToEdit)
                {
                    g._applyEditor(src.cell);
                }
            }
            else if (src.row && !g.enabledCheckbox())
            {
                if (src.cell && p.enabledEdit && p.clickToEdit)
                {
                    g._applyEditor(src.cell);
                }

                //选择行
                if ($(src.row).hasClass("l-selected"))
                {
                    if (!p.allowUnSelectRow)
                    {
                        $(src.row).addClass("l-selected-again");
                        return;
                    }
                    g.unselect(src.data);
                }
                else
                {
                    g.select(src.data);
                }
            }
            else if (src.toolbar)
            {
                if (src.first)
                {
                    if (g.trigger('toFirst', [g.element]) == false) return false;
                    g.changePage('first');
                }
                else if (src.prev)
                {
                    if (g.trigger('toPrev', [g.element]) == false) return false;
                    g.changePage('prev');
                }
                else if (src.next)
                {
                    if (g.trigger('toNext', [g.element]) == false) return false;
                    g.changePage('next');
                }
                else if (src.last)
                {
                    if (g.trigger('toLast', [g.element]) == false) return false;
                    g.changePage('last');
                }
                else if (src.load)
                {
                    if ($("span", src.load).hasClass("l-disabled")) return false;
                    if (g.trigger('reload', [g.element]) == false) return false;
                    if (p.url && g.isDataChanged && !confirm(p.isContinueByDataChanged))
                        return false;
                    g.loadData(p.where);
                    
                }else if (src.exportFile) { // 导出文件事件
                	// 导出url，导出依赖的form
                	var formObj = $("form");
                	if (p.exportUrl && formObj) {
	                	// 导出条件参数
                		var exportParms = p.exportParms || {};
                		var paramNames = [];
                		if (exportParms instanceof Array) {
                			$.each(exportParms, function(index, item){
                				if (item.value) {
                					formObj.append("<input name='" + item.name + "' id='" + item.name + "' type='hidden' value='" + item.value + "'>");
                					paramNames.push(item.name);
                				}
			       	    	});
                		}else {
                			$.each(exportParms, function(name, value){
                				if (value) {
                					formObj.append("<input name='" + name + "' id='" + name + "' type='hidden' value='" + value + "'>");
                					paramNames.push(name);
                				}
			       	    	});
                		}
                		var fields= ""; // 导出列字段
			       	    $.each(g.columns, function(index, col){
			       		     if(!col.issystem && !col._hide ){
			       		     	fields += col.name + ":" + col.display + ",";
			       		     }
			       	    });
			       	    formObj.append("<input name='e.exportFields' id='fields' type='hidden' value='" + fields + "'>");
			            formObj[0].action = p.exportUrl;
			            formObj[0].submit();
			            $("#fields").remove();
			            if (paramNames.length > 0) {
			            	for(var i = 0; i < paramNames.length; i ++) {
			            		$("#" + paramNames[i]).remove();
			            	}
			            }
                	}
                }
            }
        },
		_preRender : function() {
			var g = this, p = this.options;
			// 注册事件
            p.onUrlLoadSuccess = function(data, g){
            	  var grid = g, p = grid.options,
            	      columns = p.columns ||[] ;
            	  if (!data || !data[p.root] || !data[p.root].length){
            		  return ;
            	  }else{
            		  $.each(data[p.root],function(i,record){
            			  $.each(columns, function(j,column){
            				  if(column.name) // 获得 columns 配置项中 name字段值
            				     grid.addExtractAttr(record,column.name);
            			  });
            		  });
            	  }
              }
            //zhangyq add 注册 onBeforeAddRow事件 如果用户配置了replaceField、replaceFieldMapping 
            if(p.replaceField.length  >0 && p.replaceFieldMapping.length > 0  ){
            	if(p.replaceField.length == p.replaceFieldMapping.length){
            		p.onBeforeAddRow = function(insertRow,rows,grid){
		    	          // 添加你的过滤属性    // 定义过滤的属性   
		    	          var g = grid,p = grid.options ;
		    	          if(p.replaceField && p.replaceFieldMapping){
		    	        	  for(var i=0;i < p.replaceField.length ;i++){
		    	        		  var temp = insertRow[ p.replaceField[i]];// 获得要替换的值
		    	        		  if(temp){
		    	        			  insertRow[ p.replaceFieldMapping[i] ] = temp ;
		    	        			  //删除 原来的属性值
		    	        			  delete insertRow[ p.replaceField[i]] ;
		    	        		  }
		    	        	  }
		    	          }
		    	          var filterParams = p.filterParam ||[] ;
		    	          if ((filterParams instanceof Array) == false){
				                filterParams = [filterParams];
				          }
		    	          //如果用户配置了 过滤属性
		    	          if(filterParams.length > 0){
		    	        	  for(var param in filterParams){
		    	        		  var judgeParam = filterParams[param] ;
		    	        		  nextCycle:  for(var i=0; i<rows.length; i++){
				    	        	  var tempRow = rows[i] ;
				    	        	  if(tempRow[judgeParam] ){ 
				    	        		  var judgeValue = tempRow[judgeParam];
				    	        		  if(judgeValue && insertRow[judgeParam]){
				    	        			   if( judgeValue != insertRow[judgeParam] ){
				    	        				   continue  nextCycle;
				    	        			   }else{
				    	        				   return false ;
				    	        			   }
				    	        		  }
				    	        	  }
				    	        	}
		    	        	  }
		    	          }
		              }
            	}else{
            		alert("表格配置项：replaceField、replaceFieldMapping 配置错误！");
            		return false ;
            	}
            }//zhangyq add  end 注册 onBeforeAddRow事件 
		},
		
		/**
		 * 新增：是否显示水平工具条
		 */
		 isHorizontalScrollShowed: function ()
        {
            var g = this;
            var inner = g.gridbody.find(".l-grid-body-inner:first");
            if (!inner.length) return false;
            //20为横向滚动条的宽度
            return g.gridbody.width() - 20 < inner.width();
        },
        /**
         * 重写：设置表格高度
         */
		_setHeight: function (h){
            var g = this, p = this.options;
            g.unbind("SysGridHeightChanged");
            if (h == "auto")
            {
                g.bind("SysGridHeightChanged", function ()
                {
                    if (g.enabledFrozen())
                        g.gridview.height(Math.max(g.gridview1.height(), g.gridview2.height()));
                });
                return;
            }
            h = g._calculateGridBodyHeight(h);
            if (h > 0)
            {
                g.gridbody.height(h);

                if (p.frozen)
                { 
                    //解决冻结列和活动列由上至下滚动错位的问题
                    var w = g.gridbody.width(), w2 = $(":first-child", g.gridbody).width();
                    if (w2 && (w2 + 18 > w))
                    {
                        if (h > 18)
                            g.f.gridbody.height(h - 18);
                    } else
                    {
                        g.f.gridbody.height(h);
                    }
                }

                var gridHeaderHeight = p.headerRowHeight * (g._columnMaxLevel - 1) + p.headerRowHeight - 1;
                g.gridview.height(h + gridHeaderHeight);
            }
            g._updateHorizontalScrollStatus.ligerDefer(g, 10);
        },
         _calculateGridBodyHeight: function (h)
        {
            var g = this, p = this.options;
            if (typeof h == "string" && h.indexOf('%') > 0)
            {
                 /* //  原来的版本
            	if (p.inWindow)
                    h = $(window).height() * parseFloat(h) * 0.01;
                else
                    h = g.grid.parent().height() * parseFloat(h) * 0.01;
            	*/
            	var windowHeight = $(window).height();
	        	var gridparent = g.grid.parent();
                if (p.inWindow){
                    parentHeight = windowHeight;
                    parentHeight -= parseInt($('body').css('paddingTop'));
                    parentHeight -= parseInt($('body').css('paddingBottom'));
                }
                else{
                    parentHeight = gridparent.height();
                }
                h = parentHeight * parseFloat(p.height) * 0.01;
                if (p.inWindow || gridparent[0].tagName.toLowerCase() == "body")
                    h -= (g.grid.offset().top - parseInt($('body').css('paddingTop')));
            }
            if (p.title) h -= 24;
            //if (p.usePager) h -= 32;// 2016-03-16替换为下面一句，滚动分页所用
            if (p.usePager || (p.pagerRender && p.scrollToPage)) h -= (g.toolbar.outerHeight() - 1); 
            if (p.totalRender) h -= 25;
            if (p.toolbar) h -= g.topbar.outerHeight();
            var gridHeaderHeight = p.headerRowHeight * (g._columnMaxLevel - 1) + p.headerRowHeight - 1;
            h -= gridHeaderHeight;
            return h;
        },
        /**
         *  2016-03-16 mxq override
         */
        _initFootbar: function ()
        {
            var g = this, p = this.options;
            if (p.usePager)
            {
            	// 滚动分页实现
            	if (p.pagerRender)
                {
                    g.toolbar.html(p.pagerRender.call(g));
                    return;
                }
                if (p.scrollToPage)
                {
                    g.toolbar.hide();
                    return;
                }
                //创建底部工具条 - 选择每页显示记录数
                var optStr = "";
                var selectedIndex = -1;
                $(p.pageSizeOptions).each(function (i, item)
                {
                    var selectedStr = "";
                    if (p.pageSize == item) selectedIndex = i;
                    optStr += "<option value='" + item + "' " + selectedStr + " >" + item + "</option>";
                });

                $('.l-bar-selectpagesize', g.toolbar).append("<select name='rp'>" + optStr + "</select>");
                if (selectedIndex != -1) $('.l-bar-selectpagesize select', g.toolbar)[0].selectedIndex = selectedIndex;
                if (p.switchPageSizeApplyComboBox && $.fn.ligerComboBox)
                {
                    $(".l-bar-selectpagesize select", g.toolbar).ligerComboBox(
                    {
                        onBeforeSelect: function ()
                        {
                            if (p.url && g.isDataChanged && !confirm(p.isContinueByDataChanged))
                                return false;
                            return true;
                        },
                        width: 45
                    });
                }
            }
            else
            {
                g.toolbar.hide();
            }
        },
        /**
         * @param 根据记录号获得行id，记录号和id号都是从1开始，行索引是从0开始
         * @author mxq 2016-03-17 add
         */
         _getRowidByRecordNum: function (recordNum)
        {
            return "r" + (1000 + recordNum);
        },
        _addNewRecord: function (o, previd, pid)
        {
            var g = this, p = this.options;
            g.recordNumber++;
            o['__id'] = g._createRowid();
            o['__previd'] = previd;
            if (previd && previd != -1)
            {
                var prev = g.records[previd];
                if (prev) {
	                if (prev['__nextid'] && prev['__nextid'] != -1)
	                {
	                    var prevOldNext = g.records[prev['__nextid']];
	                    if (prevOldNext)
	                        prevOldNext['__previd'] = o['__id'];
	                }
	                prev['__nextid'] = o['__id'];
	                o['__index'] = prev['__index'] + 1;
                }else {
                	o['__previd'] = g._getRowidByRecordNum(previd);
                	o['__index'] = previd;
                }
            }
            else
            {
                o['__index'] = 0;
            }
            if (p.tree)
            {
                if (pid && pid != -1)
                {
                    var parent = g.records[pid];
                    o['__pid'] = pid;
                    o['__level'] = parent['__level'] + 1;
                }
                else
                {
                    o['__pid'] = -1;
                    o['__level'] = 1;
                }
                o['__hasChildren'] = o[p.tree.childrenName] ? true : false;
            }
            if (o[p.statusName] != "add")
                o[p.statusName] = "nochanged";
            g.rows[g.rowsIndex] = o;
            g.records[o['__id']] = o;
            g.rowsIndex ++;
            return o;
        },
        /**
         *  2016-03-17 mxq override
         */
         _updateGridData: function ()
        {
            var g = this, p = this.options;
            g.rows = [];
            g.rowsIndex = 0; // 数据行索引
            g.records = {};
            var previd = -1;
            // 若不是滚动分页时记录数才归零
            if (!p.scrollToPage && !p.scrollToAppend) {
	            g.recordNumber = 0;
            }
            if (g.recordNumber != 0) {
            	previd = g.recordNumber;
            }
            function load(data, pid)
            {
                if (!data || !data.length) return;
                for (var i = 0, l = data.length; i < l; i++)
                {
                    var o = data[i];
                    g.formatRecord(o);
                    if (o[p.statusName] == "delete") continue;
                    g._addNewRecord(o, previd, pid);
                    previd = o['__id'];
                    if (o['__hasChildren'])
                    {
                        load(o[p.tree.childrenName], o['__id']);
                    }
                }
            }
            load(g.currentData[p.root], previd);
            return g.rows;
        },
        /**
         *  2016-03-16 mxq override
         */
         _fillGridBody: function (data, frozen, sourceType)
        {
            var g = this, p = this.options;
             //加载数据 
            var gridhtmlarr = sourceType == "scrollappend" ? [] : ['<div class="l-grid-body-inner"><table class="l-grid-body-table" cellpadding=0 cellspacing=0><tbody>'];
            if (g.enabledGroup()) //启用分组模式
            {
                var groups = []; //分组列名数组
                var groupsdata = []; //切成几块后的数据
                g.groups = groupsdata;
                for (var rowparm in data)
                {
                    var item = data[rowparm];
                    var groupColumnValue = item[p.groupColumnName];
                    var valueIndex = $.inArray(groupColumnValue, groups);
                    if (valueIndex == -1)
                    {
                        groups.push(groupColumnValue);
                        valueIndex = groups.length - 1;
                        groupsdata.push([]);
                    }
                    groupsdata[valueIndex].push(item);
                }
                $(groupsdata).each(function (i, item)
                {
                    if (groupsdata.length == 1)
                        gridhtmlarr.push('<tr class="l-grid-grouprow l-grid-grouprow-last l-grid-grouprow-first"');
                    if (i == groupsdata.length - 1)
                        gridhtmlarr.push('<tr class="l-grid-grouprow l-grid-grouprow-last"');
                    else if (i == 0)
                        gridhtmlarr.push('<tr class="l-grid-grouprow l-grid-grouprow-first"');
                    else
                        gridhtmlarr.push('<tr class="l-grid-grouprow"');
                    gridhtmlarr.push(' groupindex"=' + i + '" >');
                    gridhtmlarr.push('<td colSpan="' + g.columns.length + '" class="l-grid-grouprow-cell">');
                    gridhtmlarr.push('<span class="l-grid-group-togglebtn">&nbsp;&nbsp;&nbsp;&nbsp;</span>');
                    if (p.groupRender)
                        gridhtmlarr.push(p.groupRender(groups[i], item, p.groupColumnDisplay));
                    else
                        gridhtmlarr.push(p.groupColumnDisplay + ':' + groups[i]);


                    gridhtmlarr.push('</td>');
                    gridhtmlarr.push('</tr>');

                    gridhtmlarr.push(g._getHtmlFromData(item, frozen));
                    //汇总
                    if (g.isTotalSummary())
                        gridhtmlarr.push(g._getTotalSummaryHtml(item, "l-grid-totalsummary-group", frozen));
                });
            }
            else
            {
                gridhtmlarr.push(g._getHtmlFromData(data, frozen));
            }
            
            if (!sourceType == "scrollappend")
            {
                gridhtmlarr.push('</tbody></table></div>'); 
            }
         
            if (sourceType == "scrollappend")
            {
                (frozen ? g.f.gridbody : g.gridbody).find("tbody:first").append(gridhtmlarr.join(''));
            }
            else
            {
                (frozen ? g.f.gridbody : g.gridbody).html(gridhtmlarr.join(''));
            }

            if (frozen)
            {
                g.f.gridbody.find(">l-jplace").remove();
                g.f.gridbody.append('<div class="l-jplace"></div>');
            }

            if (p.usePager && p.scrollToPage && !p.scrollToAppend)
            {
                var jgridbodyinner = frozen ? g.f.gridbody.find("> .l-grid-body-inner") : g.gridbody.find("> .l-grid-body-inner");
                var jreplace = jgridbodyinner.find("> .l-scrollreplacetop");
                jreplace = jreplace.length ? jreplace : $('<div class="l-scrollreplacetop"></div>').prependTo(jgridbodyinner);
                jreplace.css("width", "80%").height(g.lastScrollTop);

                jreplace = jgridbodyinner.find("> .l-scrollreplacebottom");
                jreplace = jreplace.length ? jreplace : $('<div class="l-scrollreplacebottom"></div>').appendTo(jgridbodyinner);
                jreplace.css("width", "80%").height((p.pageCount - p.newPage) * g._getOnePageHeight());
            }
            //分组时不需要            
            if (!g.enabledGroup())
            {
                //创建汇总行
                g._bulidTotalSummary(frozen);
            }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          
            $("> div:first", g.gridbody).width(g.gridtablewidth);
            g._onResize();
        },
        _updateHorizontalScrollStatus: function ()
        {
            var g = this, p = this.options;
            if (g.isHorizontalScrollShowed())
            {
                g.gridview.addClass("l-grid-hashorizontal");
            }
            else
            {
                g.gridview.removeClass("l-grid-hashorizontal");
            }
        },
         _onResize: function ()
        {
            var g = this, p = this.options;
            if (p.height && p.height != 'auto')
            {
                var windowHeight = $(window).height();
                //if(g.windowHeight != undefined && g.windowHeight == windowHeight) return;

                var h = 0;
                var parentHeight = null;
                if (typeof (p.height) == "string" && p.height.indexOf('%') > 0)
                {
                    var gridparent = g.grid.parent();
                    if (p.inWindow)
                    {
                        parentHeight = windowHeight;
                        parentHeight -= parseInt($('body').css('paddingTop'));
                        parentHeight -= parseInt($('body').css('paddingBottom'));
                    }
                    else
                    {
                        parentHeight = gridparent.height();
                    }
                    h = parentHeight * parseInt(p.height) * 0.01;
                    if (p.inWindow || gridparent[0].tagName.toLowerCase() == "body")
                        h -= (g.grid.offset().top - parseInt($('body').css('paddingTop')));
                }
                else
                {
                    h = parseInt(p.height);
                }

                h += p.heightDiff;
                g.windowHeight = windowHeight;
                g._setHeight(h);
            }
            else
            {
                g._updateHorizontalScrollStatus.ligerDefer(g, 10);
            }
            if (g.enabledFrozen())
            {
                var gridView1Width = g.gridview1.width();
                var gridViewWidth = g.gridview.width()
                g.gridview2.css({
                    width: gridViewWidth - gridView1Width
                });
            }

            g.trigger('SysGridHeightChanged');
        },
         //设置列宽
        setColumnWidth: function (columnparm, newwidth)
        {
            var g = this, p = this.options;
            if (!newwidth) return;
            newwidth = parseInt(newwidth, 10);
            var column;
            if (typeof (columnparm) == "number")
            {
                column = g.columns[columnparm];
            }
            else if (typeof (columnparm) == "object" && columnparm['__id'])
            {
                column = columnparm;
            }
            else if (typeof (columnparm) == "string")
            {
                if (g._isColumnId(columnparm)) // column id
                {
                    column = g._columns[columnparm];
                }
                else  // column name
                {
                    $(g.columns).each(function ()
                    {
                        if (this.name == columnparm)
                            g.setColumnWidth(this, newwidth);
                    });
                    return;
                }
            }
            if (!column) return;
            var mincolumnwidth = p.minColumnWidth;
            if (column.minWidth) mincolumnwidth = column.minWidth;
            newwidth = newwidth < mincolumnwidth ? mincolumnwidth : newwidth;
            var diff = newwidth - column._width;
            if (g.trigger('beforeChangeColumnWidth', [column, newwidth]) == false) return;
            column._width = newwidth;
            if (column.frozen)
            {
                g.f.gridtablewidth += diff;
                $("div:first", g.f.gridheader).width(g.f.gridtablewidth);
                $("div:first", g.f.gridbody).width(g.f.gridtablewidth);
            }
            else
            {
                g.gridtablewidth += diff;
                $("div:first", g.gridheader).width(g.gridtablewidth + 40);
                $("div:first", g.gridbody).width(g.gridtablewidth);
            }
            $(document.getElementById(column['__domid'])).css('width', newwidth);
            var cells = [];
            for (var rowid in g.records)
            {
                var obj = g.getCellObj(g.records[rowid], column);
                if (obj) cells.push(obj);

                if (!g.enabledDetailEdit() && g.editors[rowid] && g.editors[rowid][column['__id']])
                {
                    var o = g.editors[rowid][column['__id']];
                    if (o.editor.resize) o.editor.resize(o.input, newwidth, o.container.height(), o.editParm);
                }
            }
            for (var i = 0; i < g.totalNumber; i++)
            {
                var tobj = document.getElementById(g.id + "|total" + i + "|" + column['__id']);
                if (tobj) cells.push(tobj);
            }
            $(cells).css('width', newwidth).find("> div.l-grid-row-cell-inner:first").css('width', newwidth - 8);

            g._updateFrozenWidth();
            g._updateHorizontalScrollStatus.ligerDefer(g, 10);

            g.trigger('afterChangeColumnWidth', [column, newwidth]);
        },
        _setWidth:function(value){
        	//  此处的注释你知道为何吗？
        },
		/**
		 * 此处修复 grid 双toolbar 
		 */
		_setToolbar: function (value){
			// 清空原来的设置 toolbar的函数  。如果不清空将会出现双份 toolbar
		},
		
		/**
		 * @重写：mxq 2014-08-15，可编辑同时有title和toolbar时可编辑框的位置不正确问题修复
		 */
		 _applyEditor: function (obj)
        {
            var g = this, p = this.options;
            g.currentCell = obj; // mxq 2015-06-18 add
            
            var rowcell = obj, ids = rowcell.id.split('|');
            var columnid = ids[ids.length - 1], column = g._columns[columnid];
            var row = $(rowcell).parent(), rowdata = g.getRow(row[0]), rowid = rowdata['__id'], rowindex = rowdata['__index'];
            if (!column || !column.editor) return;
            var columnname = column.name, columnindex = column.columnindex;
            if (column.editor.type && p.editors[column.editor.type])
            {
                var currentdata = g._getValueByName(rowdata, columnname);
                var editParm = { record: rowdata, value: currentdata, column: column, rowindex: rowindex };
                if (column.textField) editParm.text = g._getValueByName(rowdata, column.textField);
                if (g.trigger('beforeEdit', [editParm]) == false) return false;
                g.lastEditRow = rowdata;
                var editor = p.editors[column.editor.type];
                var jcell = $(rowcell), offset = $(rowcell).offset();
                jcell.html("");
                //g.setCellEditing(rowdata, column, true);
                var width = $(rowcell).width(), height = $(rowcell).height();
                var container = $("<div class='l-grid-editor'></div>").appendTo(g.grid);
                var left = 0, top = 0;
                var pc = jcell.position();
                var pb = g.gridbody.position();
                var pv = g.gridview2.position();
                var topbarHeight = p.toolbar ? g.topbar.height() : 0;
                var headerHeight = p.title ? g.header.height() + 1 : 0; // mxq add 2014-08-15 解决可编辑表格的添加标题头后可编辑框错误的问题
                // top = pc.top + pb.top + pv.top + topbarHeight ; old mxq update
                top = pc.top + pb.top + pv.top + topbarHeight + headerHeight;
                left = pc.left + pb.left + pv.left;
                // mxq 2014-10-26 add start 解决设置固定列后可编辑框出现错误的问题
                if (column.frozen) {
                	left = pc.left;
                }
				// mxq 2014-10-26 add end
                
                if ($.browser.mozilla)
                    container.css({ left: left, top: top }).show();
                else
                    container.css({ left: left, top: top + 1 }).show();
                if (column.textField) editParm.text = g._getValueByName(rowdata, column.textField); 
                var editorInput = g._createEditor(editor, container, editParm, width, height);
                editorInput.focus(); // mxq 2015-06-23 add 为解决鼠标单击单元格获得焦点可编辑
                g.editor = { editing: true, editor: editor, input: editorInput, editParm: editParm, container: container };
                g.unbind('sysEndEdit');
                g.bind('sysEndEdit', function ()
                {
                    var newValue = editor.getValue(editorInput, editParm);
                    if (newValue != currentdata)
                    {
                        $(rowcell).addClass("l-grid-row-cell-edited");
                        g.changedCells[rowid + "_" + column['__id']] = true; 
                        editParm.value = newValue;
                        if (column.textField && editor.getText)
                        {
                            editParm.text = editor.getText(editorInput, editParm);
                        }
                        if (editor.onChange) editor.onChange(editParm);
                        if (g._checkEditAndUpdateCell(editParm))
                        {
                            if (editor.onChanged) editor.onChanged(editParm);
                        }
                    }
                });
            }
        },
		
        /**
		 * 重写：_checkEditAndUpdateCell方法
		 * mxq 2014-10-26
		 */
         _checkEditAndUpdateCell: function (editParm)
        {
            var g = this, p = this.options;
            if (g.trigger('beforeSubmitEdit', [editParm]) == false) return false;
            var column = editParm.column;
            if (column.textField) g._setValueByName(editParm.record, column.textField, editParm.text);
            g.updateCell(column, editParm.value, editParm.record);
            if (column.render || g.enabledTotal()) g.reRender({ column: column });
            g.reRender({ rowdata: editParm.record });
            return true;
        },
        
        /**
		 * 重写：更新下拉框单元格，可以更新下拉框的name值和textField显示值
		 * @param arg 可以是列索引和列名称
		 * mxq 2015-10-25
		 */
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
        
		/**
		 * 重写：loadData方法
		 * mxq 2013-11-30
		 */
		loadData: function (loadDataParm, sourceType) {
			var g = this, p = this.options;
            g.loading = true;
            g.trigger('loadData');
            var clause = null;
            var loadServer = true;
            if (typeof (loadDataParm) == "function")
            {
                clause = loadDataParm;
                if (g.lastData)
                {
                    g.data = g.lastData;
                } else
                {
                    g.data = g.currentData;
                    if (!g.data) g.data = {};
                    if (!g.data[p.root]) g.data[p.root] = [];
                    g.lastData = g.data;
                }
                loadServer = false;
            }
            else if (typeof (loadDataParm) == "boolean")
            {
                loadServer = loadDataParm;
            }
            else if (typeof (loadDataParm) == "object" && loadDataParm)
            {
                loadServer = false;
                p.dataType = "local";
                p.data = loadDataParm;
            }
            else if (typeof (loadDataParm) == "number")
            {
                p.newPage = loadDataParm;
            }
            //参数初始化
            if (!p.newPage) p.newPage = 1;
            if (p.dataAction == "server")
            {
                if (!p.sortOrder) p.sortOrder = "asc";
            }
            var param = [];
            if (p.parms)
            {
                var parms = $.isFunction(p.parms) ? p.parms() : p.parms;
                if (parms.length)
                {
                    $(parms).each(function ()
                    {
                        param.push({ name: this.name, value: this.value });
                    });
                    for (var i = parms.length - 1; i >= 0; i--)
                    {
                        if (parms[i].temp)
                            parms.splice(i, 1);
                    }
                }
                else if (typeof parms == "object")
                {
                    for (var name in parms)
                    {
                        param.push({ name: name, value: parms[name] });
                    }
                }
            }
            if (p.dataAction == "server")
            {
                if (p.usePager)
                {
                    param.push({ name: p.pageParmName, value: p.newPage });
                    param.push({ name: p.pagesizeParmName, value: p.pageSize });
                }
                if (p.sortName)
                {
                    param.push({ name: p.sortnameParmName, value: p.sortName });
                    param.push({ name: p.sortorderParmName, value: p.sortOrder });
                }
            };
            $(".l-bar-btnload span", g.toolbar).addClass("l-disabled");
            if (p.dataType == "local")
            {
                //原语句: g.filteredData = p.data || g.currentData;
                //该语句修改了p.data, 导致过滤数据后, 丢失了初始数据.
                g.filteredData = $.extend(true, {}, p.data || g.currentData);
                if (clause)
                    g.filteredData[p.root] = g._searchData(g.filteredData[p.root], clause);
                if (p.usePager)
                    g.currentData = g._getCurrentPageData(g.filteredData);
                else
                {
                    g.currentData = g.filteredData;
                }
                g._convertTreeData();
                g._showData();
            }
            else if (p.dataAction == "local" && !loadServer)
            {
                if (g.data && g.data[p.root])
                {
                    g.filteredData = g.data;
                    if (clause)
                        g.filteredData[p.root] = g._searchData(g.filteredData[p.root], clause);
                    g.currentData = g._getCurrentPageData(g.filteredData);
                    g._convertTreeData();
                    g._showData();
                }
            }
            else
            {
                g.loadServerData(param, clause, sourceType);
                //g.loadServerData.ligerDefer(g, 10, [param, clause]);
            }
            g.loading = false;
        },
		
        //改变分页
        changePage: function (ctype)
        {
            var g = this, p = this.options;
            if (g.loading) return true;
            if (p.dataAction != "local" && g.isDataChanged && !confirm(p.isContinueByDataChanged))
                return false;
            p.pageCount = parseInt($(".pcontrol span", g.toolbar).html());
            switch (ctype)
            {
                case 'first': if (p.page == 1) return; p.newPage = 1; break;
                case 'prev': if (p.page == 1) return; if (p.page > 1) p.newPage = parseInt(p.page) - 1; break;
                case 'next': if (p.page >= p.pageCount) return; p.newPage = parseInt(p.page) + 1; break;
                case 'last': if (p.page >= p.pageCount) return; p.newPage = p.pageCount; break;
                case 'input':
                    var nv = parseInt($('.pcontrol input', g.toolbar).val());
                    if (isNaN(nv)) nv = 1;
                    if (nv < 1) nv = 1;
                    else if (nv > p.pageCount) nv = p.pageCount;
                    $('.pcontrol input', g.toolbar).val(nv);
                    p.newPage = nv;
                    break;
            }
            if (p.newPage == p.page) return false;
            if (p.newPage == 1)
            {
                $(".l-bar-btnfirst span", g.toolbar).addClass("l-disabled");
                $(".l-bar-btnprev span", g.toolbar).addClass("l-disabled");
            }
            else
            {
                $(".l-bar-btnfirst span", g.toolbar).removeClass("l-disabled");
                $(".l-bar-btnprev span", g.toolbar).removeClass("l-disabled");
            }
            if (p.newPage == p.pageCount)
            {
                $(".l-bar-btnlast span", g.toolbar).addClass("l-disabled");
                $(".l-bar-btnnext span", g.toolbar).addClass("l-disabled");
            }
            else
            {
                $(".l-bar-btnlast span", g.toolbar).removeClass("l-disabled");
                $(".l-bar-btnnext span", g.toolbar).removeClass("l-disabled");
            }
            g.trigger('changePage', [p.newPage]);
            if (p.dataAction == "server")
            {
                if (!p.parms)
                {
                    p.parms = [];
                }
                if ($.isArray(p.parms))
                {
                    p.parms.push({ name: "changepage", value: "1", temp: true });
                } else
                {
                    p.parms["changepage"] = "1";
                }
                g.loadData(p.where);
            }
            else
            {
                g.currentData = g._getCurrentPageData(g.filteredData);
                //增加以下一句调用: 在显示数据之前, 应该先调用转换tree的函数. 
                //否则会导致树的数据重复显示
                if (p.tree)
                {
                    var childrenName = p.tree.childrenName;
                    $(g.filteredData[p.root]).each(function (index, item)
                    {
                        if (item[childrenName])
                            item[childrenName] = [];
                    });
                    g._convertTreeData();
                }
                g._showData();
            }
        },
        /**
		 * 重写：_showData方法
		 * mxq 2016-03-16
		 */
		_showData: function (sourceType)
        {
            var g = this, p = this.options;
            g.changedCells = {};
            var data = g.currentData[p.root];
            if (p.usePager)
            {
                //更新总记录数
                if (p.dataAction == "server" && g.data && g.data[p.record])
                    p.total = g.data[p.record];
                else if (g.filteredData && g.filteredData[p.root])
                    p.total = g.filteredData[p.root].length;
                else if (g.data && g.data[p.root])
                    p.total = g.data[p.root].length;
                else if (data)
                    p.total = data.length;

                p.page = p.newPage;
                if (!p.total) p.total = 0;
                if (!p.page) p.page = 1;
                p.pageCount = Math.ceil(p.total / p.pageSize);
                if (!p.pageCount) p.pageCount = 1;
                if (!p.scrollToPage)
                {
                    //更新分页
                    g._buildPager();
                }
            }
            //加载中
            $('.l-bar-btnloading:first', g.toolbar).removeClass('l-bar-btnloading');
            if (g.trigger('beforeShowData', [g.currentData]) == false) return;
            if (sourceType != "scrollappend")
            {
                g._clearGrid();
            }
            g.isDataChanged = false;
            if (!data || !data.length)
            {
                g.gridview.addClass("l-grid-empty");
                $(g.element).addClass("l-empty");
                $("<div></div>").addClass("l-grid-body-inner").appendTo(g.gridbody).css({
                    width: g.gridheader.find(">div:first").width(),
                    height: g.gridbody.height()
                });
                if (p.pagerRender)
                {
                    g.toolbar.html(p.pagerRender.call(g));
                    return;
                }
                g._onResize.ligerDefer(g, 50);
                return;
            }
            else
            {
                g.gridview.removeClass("l-grid-empty");
                $(g.element).removeClass("l-empty");
            }
            $(".l-bar-btnload:first span", g.toolbar).removeClass("l-disabled");
            g._updateGridData();
            if (g.enabledFrozen())
                g._fillGridBody(g.rows, true, sourceType);
            g._fillGridBody(g.rows, false, sourceType);
            g.trigger('SysGridHeightChanged');
            if (sourceType == "scroll")
            {
                g.trigger('sysScrollLoaded');
            }
            if (p.totalRender)
            {
                $(".l-panel-bar-total", g.element).remove();
                $(".l-panel-bar", g.element).before('<div class="l-panel-bar-total">' + p.totalRender(g.data, g.filteredData) + '</div>');
            }
            if (p.mouseoverRowCssClass)
            {
                for (var i in g.rows)
                {
                    var rowobj = $(g.getRowObj(g.rows[i]));
                    if (g.enabledFrozen())
                        rowobj = rowobj.add(g.getRowObj(g.rows[i], true));
                    rowobj.bind('mouseover.gridrow', function ()
                    {
                        g._onRowOver(this, true);
                    }).bind('mouseout.gridrow', function ()
                    {
                        g._onRowOver(this, false);
                    });
                }
            }
            g._fixHeight();
            if (p.pagerRender)
            {
                g.toolbar.html(p.pagerRender.call(g));
                return;
            }
            g.gridbody.trigger('scroll.grid');
            g.trigger('afterShowData', [g.currentData]);
        },
        /**
		 * 重写：loadData方法 2016-03-16
		 */
         _buildPager: function ()
        {
            var g = this, p = this.options;
            if (p.pagerRender)
            { 
                return;
            }
            $('.pcontrol input', g.toolbar).val(p.page);
            if (!p.pageCount) p.pageCount = 1;
            $('.pcontrol span', g.toolbar).html(p.pageCount);
            var r1 = parseInt((p.page - 1) * p.pageSize) + 1.0;
            var r2 = parseInt(r1) + parseInt(p.pageSize) - 1;
            if (!p.total) p.total = 0;
            if (p.total < r2) r2 = p.total;
            if (!p.total) r1 = r2 = 0;
            if (r1 < 0) r1 = 0;
            if (r2 < 0) r2 = 0;
            var stat = p.pageStatMessage;
            stat = stat.replace(/{from}/, r1);
            stat = stat.replace(/{to}/, r2);
            stat = stat.replace(/{total}/, p.total);
            stat = stat.replace(/{pagesize}/, p.pageSize);
            $('.l-bar-text', g.toolbar).html(stat);
            if (!p.total)
            {
                $(".l-bar-btnfirst span,.l-bar-btnprev span,.l-bar-btnnext span,.l-bar-btnlast span", g.toolbar)
                    .addClass("l-disabled");
            }
            if (p.page == 1)
            {
                $(".l-bar-btnfirst span", g.toolbar).addClass("l-disabled");
                $(".l-bar-btnprev span", g.toolbar).addClass("l-disabled");
            }
            else if (p.page > p.pageCount && p.pageCount > 0)
            {
                $(".l-bar-btnfirst span", g.toolbar).removeClass("l-disabled");
                $(".l-bar-btnprev span", g.toolbar).removeClass("l-disabled");
            }
            if (p.page == p.pageCount)
            {
                $(".l-bar-btnlast span", g.toolbar).addClass("l-disabled");
                $(".l-bar-btnnext span", g.toolbar).addClass("l-disabled");
            }
            else if (p.page < p.pageCount && p.pageCount > 0)
            {
                $(".l-bar-btnlast span", g.toolbar).removeClass("l-disabled");
                $(".l-bar-btnnext span", g.toolbar).removeClass("l-disabled");
            }
        },
		//将ID、ParentID这种数据格式转换为树格式
		arrayToTree: function (data, id, pid) {
            var g = this, p = this.options;
            var childrenName = "children";
            if (p.tree) childrenName = p.tree.childrenName;
            if (!data || !data.length) return [];
            var targetData = [];                    //存储数据的容器(返回) 
            var records = {};
            var itemLength = data.length;           //数据集合的个数
            for (var i = 0; i < itemLength; i++)
            {
                var o = data[i];
                var key = getKey(o[id]);
                records[key] = o;
            }
            for (var i = 0; i < itemLength; i++)
            {
                var currentData = data[i];
               //var key = getKey(currentData[pid]);
                 // mxq add 2013-11-29
                var pdata = currentData[pid];
                if (pdata) {
	                if (typeof (pdata) == "object") {
	                	pdata = currentData[pid][id]
	                }
                }
                 //mxq add 2013-11-29
                var key = getKey(pdata);
                var parentData = records[key];
                if (!parentData)
                {
                    targetData.push(currentData);
                    continue;
                }
                parentData[childrenName] = parentData[childrenName] || [];
                parentData[childrenName].push(currentData);
            }
            return targetData;

            function getKey(key)
            {
                if (typeof (key) == "string") key = key.replace(/[.]/g, '').toLowerCase();
                return key;
            }
		},    
		
		// 将list数据转换成tree数据
		_convertTreeData: function ()
        {
            var g = this, p = this.options;
            if (p.tree && p.tree.idField && p.tree.parentIDField)
            {
                g.currentData[p.root] = g.arrayToTree(g.currentData[p.root], p.tree.idField, p.tree.parentIDField);
                g.currentData[p.record] = g.currentData[p.root].length;
            }
        },
        
		/**
		 * 重写：loadServerData方法
		 */
        loadServerData: function (param, clause, sourceType){
            var g = this, p = this.options;
            var url = p.url;
            if ($.isFunction(url)) url = url.call(g);
            var urlParms = $.isFunction(p.urlParms) ? p.urlParms.call(g) : p.urlParms;
            if (urlParms)
            {
                for (name in urlParms)
                {
                    url += url.indexOf('?') == -1 ? "?" : "&";
                    url += name + "=" + urlParms[name];
                }
            }
            var ajaxOptions = {
                type: p.method,
                url: p.url,
                data: param,
                async: p.async,
                dataType: 'json',
                beforeSend: function (){
                    if (g.hasBind('loading')){
                        g.trigger('loading');
                    }else{
                        g.toggleLoading(true);
                    }
                },
                success: function (data){
                	// zhangyq update TODO
                	g.trigger('urlLoadSuccess',[data,g]);
                    g.trigger('success', [data, g]);
                    if (!data || !data[p.root] || !data[p.root].length){
                        g.currentData = g.data = {};
                        g.currentData[p.root] = g.data[p.root] = [];
                        if (data && data[p.record]){
                            g.currentData[p.record] = g.data[p.record] = data[p.record];
                        } else{
                            g.currentData[p.record] = g.data[p.record] = 0;
                        }
                        g._convertTreeData();// mxq add 
                        g._showData(sourceType);
                        return;
                    }
                    g.data = data;
                    //保存缓存数据-记录总数
                    if (g.data[p.record] != null)
                    {
                        g.cacheData.records = g.data[p.record];
                    }
                    if (p.dataAction == "server"){//服务器处理好分页排序数据
                        g.currentData = g.data;
                         //读取缓存数据-记录总数(当没有返回总记录数)
                        if (g.currentData[p.record] == null && g.cacheData.records)
                        {
                            g.currentData[p.record] = g.cacheData.records;
                        }
                    }else{//在客户端处理分页排序数据
                        g.filteredData = g.data;
                        if (clause) g.filteredData[p.root] = g._searchData(g.filteredData[p.root], clause);
                        if (p.usePager)
                            g.currentData = g._getCurrentPageData(g.filteredData);
                        else
                            g.currentData = g.filteredData;
                    }
                    
                     g._convertTreeData(); // mxq add
                     g._showData.ligerDefer(g, 10, [sourceType]);
                },
                complete: function (){
                    g.trigger('complete', [g]);
                    if (g.hasBind('loaded')){
                        g.trigger('loaded', [g]);
                    }
                    else{
                        g.toggleLoading.ligerDefer(g, 10, [false]);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown){
                    g.currentData = g.data = {};
                    g.currentData[p.root] = g.data[p.root] = [];
                    g.currentData[p.record] = g.data[p.record] = 0;
                    g.toggleLoading.ligerDefer(g, 10, [false]);
                    $(".l-bar-btnload span", g.toolbar).removeClass("l-disabled");
                    g.trigger('error', [XMLHttpRequest, textStatus, errorThrown]);
                }
            };
            if (p.contentType) ajaxOptions.contentType = p.contentType;
            $.ligerui.ligerAjax(ajaxOptions);
        },
        
		/**
		 * 功能：设置发送请求时的参数值
		 * @param {Object} value
		 * mxq修改兼容表单和parms同时提交查询条件，若表单和parms同时存在的话，优先取表单参数
		 */
		setParms:function(value){
        	this.options.parms = value ;
        },
        
        /**
         * 
         */
        unselect: function (rowParm){
            var g = this, p = this.options;
            var rowdata = g.getRow(rowParm);
            var rowid = rowdata['__id'];
            var rowobj = g.getRowObj(rowid);
            var rowobj1 = g.getRowObj(rowid, true);
            $(rowobj).removeClass("l-selected l-selected-again");
            if (g.enabledFrozen())
                $(rowobj1).removeClass("l-selected l-selected-again");
            //g._removeSelected(rowdata); old 2013-7-23 21:27:32
            g.un_removeSelected(rowdata);
            g.trigger('unSelectRow', [rowdata, rowid, rowobj]);
        },
        //此处 为专一 为unselect方法写的函数 TODO 和_removeSelected 进行对比你就会发现问题  哈哈哈
        un_removeSelected: function(rowdata){
        	var g = this, p = this.options;
            if (p.tree){
                var children = g.getChildren(rowdata, true);
                if (children){
                    for (var i = 0, l = children.length; i < l; i++){
                        var index2 = $.inArray(children[i], g.selected);
                        if (index2 != -1) g.selected.splice(index2, 1);
                    }
                }
            }
            var index = $.inArray(rowdata, g.selected);
            if (index != -1) g.selected.splice(index, 1);
        },
        /**
         * 删除的bug，只是删除了 g.selected 的值没有删除 g.data 和 g.currentdata
         * 因为此函数调用了两次，在unselecte方法中也调用了 所以重写了unselect要调用的
         * @param {Object} rowParm
         * @memberOf {TypeName} 
         */
         _removeSelected: function (rowdata){
            var g = this, p = this.options;
            if (p.tree){
                var children = g.getChildren(rowdata, true);
                if (children){
                    for (var i = 0, l = children.length; i < l; i++){
                        var index2 = $.inArray(children[i], g.selected);
                        if (index2 != -1) g.selected.splice(index2, 1);
                    }
                }
            }
            var index = $.inArray(rowdata, g.selected);
            if (index != -1) g.selected.splice(index, 1);
            // 修复bug  zhangyq 2013-7-20 14:58:50
            if (g.currentData[p.root]){
            	var listdata = g.currentData[p.root];
            	var index = $.inArray(rowdata,listdata);
                if (index != -1) listdata.splice(index, 1);
            }
            // end of zhangyq 
        },
        addRow: function (rowdata, neardata, isBefore, parentRowData){
            var g = this, p = this.options;
            rowdata = rowdata || {};
           // g._addData(rowdata, parentRowData, neardata, isBefore);  //old  version 2013-6-14 18:05:10
            if(!g._addData(rowdata, parentRowData, neardata, isBefore)){
            	return ;
            }
            g.reRender();
            //标识状态
            rowdata[p.statusName] = 'add';
            if (p.tree){
                var children = g.getChildren(rowdata, true);
                if (children){
                    for (var i = 0, l = children.length; i < l; i++)
                    {
                        children[i][p.statusName] = 'add';
                    }
                }
            }
            g.isDataChanged = true;
            p.total = p.total ? (p.total + 1) : 1;
            p.pageCount = Math.ceil(p.total / p.pageSize);
            g._buildPager();
            g.trigger('SysGridHeightChanged');
            g.trigger('afterAddRow', [rowdata]);
            return rowdata;
        },
        _addData: function (rowdata, parentdata, neardata, isBefore){
            var g = this, p = this.options;
            if (!g.currentData) g.currentData = {};
            if (!g.currentData[p.root]) g.currentData[p.root] = [];
            var listdata = g.currentData[p.root];
            //zhangyq add
            if (g.trigger('beforeAddRow', [rowdata,listdata,g]) == false) return false;
            //end of zhangyq add
            if (neardata){
                if (p.tree){
                    if (parentdata)
                        listdata = parentdata[p.tree.childrenName];
                    else if (neardata['__pid'] in g.records)
                        listdata = g.records[neardata['__pid']][p.tree.childrenName];
                }
                var index = $.inArray(neardata, listdata);//TODO 此处能确定 对象在对象数组中的准确位置吗？
                listdata.splice(index == -1 ? -1 : index + (isBefore ? 0 : 1), 0, rowdata);
            }
            else{
                if (p.tree && parentdata){
                    listdata = parentdata[p.tree.childrenName];
                }
                listdata.push(rowdata);
            }
            return true ;// zhangyq add 2013-6-14 18:03:57
        },
         // mxq 2014-05-26 修改
         _setValueByName: function (rowdata, columnname, value)
        {
            if (!rowdata || !columnname) return null;
             if (columnname.indexOf('.') == -1)
            {
                rowdata[columnname] = value;
            }
            else
            {
            	var names = columnname.split('.');
            	var name0 = eval("rowdata." + names[0]);
            	// 若列表中包含.则以.分隔成数组，若rowdata.数组[0]对象未定义或为null，
            	//则取(rowdata['列名'])的方式设置，否则以(rowdata.列名) 设置
            	if (!name0) { 
            		rowdata[columnname] = value;
            	}else {
	                try
	                {
	                    new Function("rowdata,value", "rowdata." + columnname + "=value;")(rowdata, value);
	                }
	                catch (e)
	                {
	                }
            	}
            }
        },
        // mxq 2014-05-26 修改
        _getValueByName: function (rowdata, columnname)
        {
            if (!rowdata || !columnname) return null;
            if (columnname.indexOf('.') == -1)
            {
                return rowdata[columnname];
            }
            else
            {
            	var names = columnname.split('.');
            	var name0 = eval("rowdata." + names[0]);
            	if (!name0) {
            		return rowdata[columnname];
            	}else {
	                try
	                {
	                    return new Function("rowdata", "return rowdata." + columnname + ";")(rowdata);
	                }
	                catch (e)
	                {
	                    return null;
	                }
            	}
            }
        }, //显示隐藏列   新增需求  当在grid之外想隐藏和显示grid中的列时 需要调用该方法隐藏和显示
        //update该方法是用场景为： 
        toggleCol: function (columnparm, visible, toggleByPopup)
        {
            var g = this, p = this.options;
            var column;
            if (typeof (columnparm) == "number")
            {
                column = g.columns[columnparm];
            }
            else if (typeof (columnparm) == "object" && columnparm['__id'])
            {
                column = columnparm;
            }
            else if (typeof (columnparm) == "string")
            {
                if (g._isColumnId(columnparm)) // column id
                {
                    column = g._columns[columnparm];
                }
                else  // column name
                {
                    $(g.columns).each(function ()
                    {
                        if (this.name == columnparm){
                            g.toggleCol(this, visible, toggleByPopup);
                        }// else 语句为 新添加的  2013-7-27 9:59:12
                        else if(this.columnname == columnparm ){
                        	g.toggleCol(this, visible, toggleByPopup); // zhangyq update 
                        }
                    });
                    return;
                }
            }
            if (!column) return;
            var columnindex = column['__leafindex'];
            var headercell = document.getElementById(column['__domid']);
            if (!headercell) return;
            headercell = $(headercell);
            var cells = [];
            for (var i in g.rows)
            {
                var obj = g.getCellObj(g.rows[i], column);
                if (obj) cells.push(obj);
            }
            for (var i = 0; i < g.totalNumber; i++)
            {
                var tobj = document.getElementById(g.id + "|total" + i + "|" + column['__id']);
                if (tobj) cells.push(tobj);
            }
            var colwidth = column._width;
            //显示列
            if (visible && column._hide)
            {
                if (column.frozen)
                    g.f.gridtablewidth += (parseInt(colwidth) + 1);
                else
                    g.gridtablewidth += (parseInt(colwidth) + 1);
                g._setColumnVisible(column, false);
                $(cells).show();
            }
                //隐藏列
            else if (!visible && !column._hide)
            {
                if (column.frozen)
                    g.f.gridtablewidth -= (parseInt(colwidth) + 1);
                else
                    g.gridtablewidth -= (parseInt(colwidth) + 1);
                g._setColumnVisible(column, true);
                $(cells).hide();
            }
            if (column.frozen)
            {
                $("div:first", g.f.gridheader).width(g.f.gridtablewidth);
                $("div:first", g.f.gridbody).width(g.f.gridtablewidth);
            }
            else
            {
                $("div:first", g.gridheader).width(g.gridtablewidth + 40);
                $("div:first", g.gridbody).width(g.gridtablewidth);
            }
            g._updateFrozenWidth();
            if (!toggleByPopup)
            {
                $(':checkbox[columnindex=' + columnindex + "]", g.popup).each(function ()
                {
                    this.checked = visible;
                    if ($.fn.ligerCheckBox)
                    {
                        var checkboxmanager = $(this).ligerGetCheckBoxManager();
                        if (checkboxmanager) checkboxmanager.updateStyle();
                    }
                });
            }
        },
        /**
         * 功能：根据列配置项名称过滤 表格一行数据中包含该配置项名称的数据
         * @param {Object} rowdata
         *        表格的一行数据
         * @param {String} columnname
         *        表格的任意一个列配置项名称
         */
        addExtractAttr: function(rowdata, columnname){
        	 if (!rowdata || !columnname) return null;
	    	 var columnArrs = columnname.split(".");
	    	 var resultName = "";
	    	 switch(columnArrs.length){
	    	   case 1:
	    		    resultName = rowdata[columnname];
	    		   break;
	    	   case 2:
	    		   var temp = rowdata[columnArrs[0]] ;
	    		   if(temp){
	    			   resultName = temp[columnArrs[1]];
	    		   }
	    		   break;
	    	   case 3:
	    		    var tempParent = rowdata[columnArrs[0]],temp ;
	    		    if(tempParent){
	    		    	temp = tempParent[columnArrs[1]];
	    		    	if(temp){
	    		    		resultName = temp[columnArrs[2]]
	    		    	}
	    		    }
	    	 }//end of switch
	    	 if(!rowdata[columnname]){
	    		 rowdata[columnname] = resultName ;
	    	 }
        },
        /**
         * 功能：根据配置项 columns 获得可编辑表格明细的方法
         * @return {Object} 
         */
        getEditGridDetails: function(){
        	 var g = this, p = this.options;
        	 var callBackDetails =  {} ;
        	 if(p.enabledEdit){
        		 var columns = p.columns||[] ;
				 var prefix = p.submitDetailsPrefix || 'e.details';
				 $.each(g.getData(),function(i,record){
					$.each(columns,function(j,column){
						var columnname = column.name;
						if(columnname){
 							 // mxq update 2014-08-07
							var columnvalue = g._getValueByName(record, columnname);
							if (columnvalue instanceof Date) {
								columnvalue = p.formatters[column.type].call(g, columnvalue, column);
							}
							if (columnvalue != 0) {
								columnvalue = columnvalue ||'';
							}
							callBackDetails[prefix+'['+i+'].'+columnname] = columnvalue ;
						}
					});
				});
        	 }
			return callBackDetails ;
        },
        // 获得表格明细
        getGridDetails: function(){
        	 var g = this, p = this.options;
        	 var callBackDetails =  {} ;
    		 var columns = p.columns||[] ;
			 var prefix = p.submitDetailsPrefix || 'e.details';
			 $.each(g.getData(),function(i,record){
				$.each(columns,function(j,column){
					var columnname = column.name;
					if(columnname){
						 // mxq update 2014-08-07
						var columnvalue = g._getValueByName(record, columnname);
						if (columnvalue instanceof Date) {
							columnvalue = p.formatters[column.type].call(g, columnvalue, column);
						}
						if (columnvalue != 0) {
							columnvalue = columnvalue ||'';
						}
						callBackDetails[prefix+'['+i+'].'+columnname] = columnvalue ;
					}
				});
			});
			return callBackDetails ;
        },
        /**
         * @see 获得多表头可编辑表格数据，兼容单表头
         * @author mxq
         * @createDate 2014-09-08
         */
        getMultiHeaderEditGridDatas: function() {
        	 var g = this, p = this.options;
        	 var callBackDetails =  {} ;
        	 if(p.enabledEdit){
        		 var columns = p.columns||[] ;
				 $.each(g.getData(), function(i, record){
				 	g.recursionColumns(columns, callBackDetails, record, i);
				 });
        	 }
        	 return callBackDetails;
        },
        
        /**
         * @see 递归表格列
         * @author mxq
         * @createDate 2014-09-18
         */
        recursionColumns: function(columns, callBackDetails, record, i) {
        	var g = this, p = this.options;
        	 var prefix = p.submitDetailsPrefix || 'e.details';
        	$.each(columns,function(j,column){
				if (column.columns) {
					g.recursionColumns(column.columns, callBackDetails, record, i);
				}
				var columnname = column.name;
				if(columnname){
					var columnvalue = g._getValueByName(record, columnname);
					if (columnvalue instanceof Date) {
						columnvalue = p.formatters[column.type].call(g, columnvalue, column);
					}
					if (columnvalue != 0) {
						columnvalue = columnvalue ||'';
					}
					callBackDetails[prefix+'['+i+'].'+columnname] = columnvalue ;
				}
        	});
        },
         _enabledEditByCell : function(cell)
        {
            var g = this, p = this.options;
            var column = g.getColumn(cell);
            if (!column) return false;
            
            return column.editor && column.editor.type;
        },
        //结束当前的编辑 并进入下一个单元格的编辑状态(如果位于最后单元格，进入下一行第一个单元格)
        endEditToNext : function()
        {
            var g = this, p = this.options;
            var editor = g.editor, jnext = null, jprev = null;
            if (editor)
            {
                var editParm = editor.editParm;
                var column = editParm.column;
                var columnIndex = $.inArray(column, g.columns); 
                var cell = g.getCellObj(editParm.record, editParm.column);
                jprev = $(cell);
                jnext = jprev.next();
                if (!jnext.length) jnext = getNextRowCell();//已经是当前行最后一个单元格了
                if (jnext.length)
                {
                    //获取到下一个可编辑的列
                    while (!g._enabledEditByCell(jnext.get(0)))
                    {
                        jprev = jnext;
                        jnext = jnext.next();
                        if (!jnext.length)//已经是当前行最后一个单元格了
                        {
                            jnext = getNextRowCell();
                        }
                        // 下面语句解决最后一列不是可编辑单元格出现死循环问题
                        if (!jnext.length) break;
                    }
                }
                //获取下一行第一个列对象
                function getNextRowCell()
                { 
                    return jprev.parent("tr").next(".l-grid-row").find("td:first");
                }
            }
           
            g.endEdit();
            if (jnext && jnext.length)
            {
                g._applyEditor(jnext.get(0));
            }
        }
    };
    //获取 默认的编辑器构造函数。 >>返回的是一个参数对象。
	
    //zp注释，原因：表格弹出页面代码未同步到最新
/*    function getEditorBuilder(e){
        var type = e.type, control = e.control;
        if (!type || !control) return null;
        control = control.substr(0, 1).toUpperCase() + control.substr(1);
        return $.extend({
            create: function (container, editParm){
                var column = editParm.column;
                var input = null ;//$("<input type='text'/>")//.appendTo(container);
                // zhangyq update 
                if(type == "checkbox" || type == "check"){
                	input = $("<input type='checkbox' style='margin-top:5px;margin-left:15px;'/>").appendTo(container);
                }else if(type == "radio" || type == "radiobox"){
                	input = $("<input type='radio'/>").appendTo(container);
                }else{
                	input = $("<input type='text'/>").appendTo(container);
                }
                //end zhangyq update 
                
                var p = $.extend({}, column.editor.options);// 获得用户配置的editor属性中的 options参数>> 将地址转移
                if (column.editor.valueColumnName) p.valueField = column.editor.valueColumnName;
                if (column.editor.displayColumnName) p.textField = column.editor.displayColumnName;
                //zhangyq add 添加级联部门
                if(column.editor.cascade && column.editor.type == 'popup'){
                	 column.editor['onButtonClick'] = popupButtonClick ;
                }
                //end of zhangyq add
                var defaults = liger.defaults[control];
                for (var proName in defaults){ // 这一招用的也是非常棒的 
                    if (proName in column.editor){
                        p[proName] = column.editor[proName];
                    }
                }
                //可扩展参数,支持动态加载
                var ext = column.editor.p || column.editor.ext;
                ext = typeof (ext) == 'function' ? ext(editParm) : ext;
                $.extend(p, ext);
                // input['liger' + control](p);//调用组件的方法 生成要生成的组件。 old
                var ligerComponent = input['liger' + control](p);//调用组件的方法 生成要生成的组件。 
                //zhangyq add  解决级联问题
                if(column.editor.cascade && column.editor.type == 'popup'){
                	ligerComponent.link.unbind("click");
                	ligerComponent.link.bind('click',function(){
                		//if (p.disabled) return;  此处其实是没有用的。 
                		//此处调用的是ligerUI的trigger方法 重点别和jquery混杂
                		if (ligerComponent.trigger('buttonClick',editParm.record) == false) return false;
                	});
                }
                // end of zhangyq 
                return input;
            },
            getValue: function (input, editParm){
                var obj = liger.get(input);
                if (obj) return obj.getValue(); 
            },
            setValue: function (input, value, editParm){
                var obj = liger.get(input);
                if (obj) obj.setValue(value); 
            },
            resize: function (input, width, height, editParm){
                var obj = liger.get(input);
                if (obj) obj.resize(width, height); 
            },
            destroy: function (input, editParm){
                var obj = liger.get(input);
                if (obj) obj.destroy();
            }
        }, e); 
    }
    
*/    
    
    function getEditorBuilder(e){
        var type = e.type, control = e.control;
        if (!type || !control) return null;
        control = control.substr(0, 1).toUpperCase() + control.substr(1);
        return $.extend({
            create: function (container, editParm){
                var column = editParm.column;
                var width = column._width || column.minWidth; // mxq 2014-08-13 add
                var input = null ;//$("<input type='text'/>")//.appendTo(container);
                // zhangyq update 
                if(type == "checkbox" || type == "check"){
                	input = $("<input type='checkbox' style='width: " + width + "px;height: 20px;'/>").appendTo(container);
                }else if(type == "radio" || type == "radiobox"){
                	input = $("<input type='radio' style='" + width + "px;height: 20px;'/>").appendTo(container);
                }else if(type == "textarea"){
                	input = $("<textarea class='l-textarea' style='width:" + width + "px;'></textarea>").appendTo(container);
                }else{
                	input = $("<input type='text'/>").appendTo(container);
                }
                //end zhangyq update 
                
                var p = $.extend({}, column.editor.options);// 获得用户配置的editor属性中的 options参数>> 将地址转移
                if (column.editor.valueColumnName) p.valueField = column.editor.valueColumnName;
                if (column.editor.displayColumnName) p.textField = column.editor.displayColumnName;
                //zhangyq add 添加级联部门
                if(column.editor.cascade && column.editor.type == 'popup'){
                	 p.cancelable = false;
                	 var classTypeOptions = column.editor.onButtonClick ;
                	 var buttonClickFun;
                	 // zhangyq add 
                	 if(column.editor.onButtonClick){
                		 var classType = typeof classTypeOptions ;
                		 if(classType == "function"){
                			 buttonClickFun = classTypeOptions ;
                		 }else if(classType == 'object'){
                			  // 对话框配置项
                			  var dialogOptions = $.extend({},classTypeOptions);
                			  var hideToolbar = dialogOptions['hideToolbar'] || dialogOptions['hideToolBar'] ;
                			  var dataFunName = dialogOptions['dataFunName'] || dialogOptions['onSave'] ;
                			  // 如果配置了隐藏查询的工具条
            				  dialogOptions['onLoaded'] = function(param){
								   var documentF = param.contentDocument||param.document ;//兼容IE 和 FF
	                			   if(hideToolbar){ // 隐藏工具条
									   $('div.l-panel-topbar,div.l-toolbar',documentF).hide();
	                			   }
							  };
                			  buttonClickFun = function(rowdata){
                				  var g = this ,p= g.options;
						            $.ligerDialog.open($.extend({},
						            	{ buttons : [ {   text : '确定',
												onclick :function(item,dialog){
												    var returnFrame = dialog.frame || dialog.frame.window;
												    console.log(dataFunName);
												    console.dir(returnFrame);
												    var data = returnFrame[dataFunName]() ;
												    if(data.length == 1){
												    	if (g.trigger('select', data) == false) return;
							                            if (g.trigger('selected', [data[0], rowdata])== false) return;
													    g.setValue(data[0][p.valueField]); //设置也可以解决 问题
						                                g.setText(data[0][p.textField]);// 设置应该可以的
													    dialog.close() ;
													    
												    }else{
												    	$.ligerDialog.warn("请选择一条记录进行操作！");
												    }
												}
											}, {text : '取消',
												onclick : function(item, dialog){
												   dialog.close();
												}
											}],
											width:800,
											height:800,
											title:'请选择'
						            	},
										dialogOptions
						            ));
                			  }
                		 }
                	 }
                	 column.editor['onButtonClick'] = buttonClickFun  ; // TODO 
                }
                //end of zhangyq add
                var defaults = liger.defaults[control];
                for (var proName in defaults){ // 这一招用的也是非常棒的 
                    if (proName in column.editor){
                        p[proName] = column.editor[proName];
                    }
                }
                //可扩展参数,支持动态加载
                var ext = column.editor.p || column.editor.ext;
                ext = typeof (ext) == 'function' ? ext(editParm) : ext;
                $.extend(p, ext);
                // input['liger' + control](p);//调用组件的方法 生成要生成的组件。 old
                if (type != 'textarea') { 
	                var ligerComponent = input['liger' + control](p);//调用组件的方法 生成要生成的组件。 
	                //zhangyq add  解决级联问题
	                if(column.editor.cascade && column.editor.type == 'popup'){
	                	ligerComponent.link.unbind("click");
	                	ligerComponent.link.bind('click',function(){
	                		//if (p.disabled) return;  此处其实是没有用的。 
	                		//此处调用的是ligerUI的trigger方法 重点别和jquery混杂
	                		if (ligerComponent.trigger('buttonClick',editParm.record) == false) return false;
	                	});
	                }
                }
                // end of zhangyq 
                return input;
            },
            getValue: function (input, editParm){
            	var type = editParm.column.editor['type'];
            	if (type == 'textarea') {
	            	return $(input).val();
            	}
                var obj = liger.get(input);
                if (obj) return obj.getValue(); 
            },
            setValue: function (input, value, editParm){
            	var type = editParm.column.editor['type'];
            	if (type == 'textarea') {
	                $(input).val(value);
            	}else {
	                var obj = liger.get(input);
	                if (obj) obj.setValue(value); 
            	}
            },
            resize: function (input, width, height, editParm){
                var obj = liger.get(input);
                if (obj) obj.resize(width, height); 
            },
            destroy: function (input, editParm){
                var obj = liger.get(input);
                if (obj) obj.destroy();
            }
        }, e); 
    }
    
    //几个默认的编辑器构造函数 // 如果包含参数函数则是扩充的函数。
    var defaultEditorBuilders = {
        "text": {
            control: 'TextBox'
        },
        "date": {
            control: 'DateEditor',
            setValue: function (input, value, editParm){
                // /Date(1328423451489)/
                if (typeof value == "string" && /^\/Date/.test(value)){
                    value = value.replace(/^\//, "new ").replace(/\/$/, "");
                    eval("value = " + value);
                }
                liger.get(input).setValue(value);
            }
        },
        "combobox": {
            control: 'ComboBox',
            getText: function (input, editParm){ 
                return liger.get(input).getText();
            },
            setText: function (input, value, editParm){ 
                liger.get(input).setText(value);
            }
        },
        "int": {
            control: 'Spinner',
            getValue: function (input, editParm){
                return parseInt(input.val(), 10);
            }
        },
        "spinner": {
            control: 'Spinner',
            getValue: function (input, editParm){
                return parseFloat(input.val());
            }
        },
        "checkbox": {
            //control: 'CheckBox',
        	control: 'Check',
            getValue: function (input, editParm){
               return input[0].checked ? "Y" : "N";  //old
            },
            setValue: function (input, value, editParm){
            	if (value) {
	                value = value.toUpperCase();
	            	//input.val(value ? true : false); // old
	                if(value == "Y"){
	                	input.attr("checked","checked");
	                }else{
	                	input.removeAttr("checked")
	                	value = "N";
	                }
	                input.val(value); //
            	}
            }
        },
        "popup": {
            control: 'PopupEdit',
            getText: function (input, editParm){
                return liger.get(input).getText();
            },
            setText: function (input, value, editParm){ 
                liger.get(input).setText(value);
            }
        },
        "textarea": {
            control: 'TextArea',
            getText: function (input, editParm){
                return $(input).val();
            },
            setText: function (input, value, editParm){ 
                $(input).val(value);
            }
        }
    };
    defaultEditorBuilders["string"] = defaultEditorBuilders["text"];
    defaultEditorBuilders["string"] = defaultEditorBuilders["textarea"];
    defaultEditorBuilders["select"] = defaultEditorBuilders["combobox"];
    defaultEditorBuilders["float"] = defaultEditorBuilders["spinner"];
    defaultEditorBuilders["chk"] = defaultEditorBuilders["checkbox"];

    //页面初始化以后才加载
    $(function (){
    	$.ligerDefaults.Grid.editors = {} ;// 清空 配置项
        for (var type in defaultEditorBuilders){
            var p = defaultEditorBuilders[type];// 获得配置的编辑器参数
            if (!p || !p.control || type in $.ligerDefaults.Grid.editors) continue;
            $.ligerDefaults.Grid.editors[type] = getEditorBuilder($.extend({
                type: type
            }, p));
        }
    });
})(jQuery);
