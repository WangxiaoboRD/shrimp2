<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!-- 帆软报表工具条 -->
<body onload="afterload()">
	<div id="toolbar" class="l-toolbar">
		<div id="firstPage" class="l-toolbar-item l-panel-btn l-toolbar-item-hasicon l-btnfirst l-toolbar-item-disable">
			<span>首页</span>
			<div class="l-panel-btn-l"></div>
			<div class="l-panel-btn-r"></div>
			<div class="l-icon l-icon-page-first-disabled"></div>
		</div>
		<div class="l-bar-separator"></div>
		<div id="prevPage" class="l-toolbar-item l-panel-btn l-toolbar-item-hasicon l-btnprev l-toolbar-item-disable">
			<span>上一页</span>
			<div class="l-panel-btn-l"></div>
			<div class="l-panel-btn-r"></div>
			<div class="l-icon l-icon-page-prev-disabled"></div>
		</div>
		<div class="l-bar-separator"></div>
		<div class="l-bar-group">
			<span class="pcontrol"> 
				<input id="pageIndex" type="text" size="4" style="width:40px;" readonly="true" /> / 
				<span></span>
			</span>
		</div>
		<div class="l-bar-separator"></div>
		<div id="nextPage" class="l-toolbar-item l-panel-btn l-toolbar-item-hasicon l-btnnext l-toolbar-item-disable">
			<span>下一页</span>
			<div class="l-panel-btn-l"></div>
			<div class="l-panel-btn-r"></div>
			<div class="l-icon l-icon-page-next-disabled"></div>
		</div>
		<div class="l-bar-separator"></div>
		<div id="lastPage" class="l-toolbar-item l-panel-btn l-toolbar-item-hasicon l-btnlast l-toolbar-item-disable">
			<span>末页</span>
			<div class="l-panel-btn-l"></div>
			<div class="l-panel-btn-r"></div>
			<div class="l-icon l-icon-page-last-disabled"></div>
		</div>
	</div>
</body>