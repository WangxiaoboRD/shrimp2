<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!-- 帆软报表工具条 -->
<body onload="afterload()">
	<div id="toolbar" class="l-toolbar">
		<div id="pdfPrintBtn" class="l-toolbar-item l-panel-btn l-toolbar-item-hasicon l-btnflashprint">
			<span>打印[客户端]</span>
			<div class="l-panel-btn-l"></div>
			<div class="l-panel-btn-r"></div>
			<div class="l-icon l-icon-flash-print"></div>
		</div>
		<div class="l-bar-separator"></div>
		<div id="printBtn" class="l-toolbar-item l-panel-btn l-toolbar-item-hasicon">
			<span>打印</span>
			<div class="l-panel-btn-l"></div>
			<div class="l-panel-btn-r"></div>
			<div class="l-icon l-icon-server-print"></div>
			<div class="l-menubar-item-down"></div>
		</div>
		<div class="l-bar-separator"></div>
		<div id="outputBtn" class="l-toolbar-item l-panel-btn l-toolbar-item-hasicon">
			<span>输出</span>
			<div class="l-panel-btn-l"></div>
			<div class="l-panel-btn-r"></div>
			<div class="l-icon l-icon-file"></div>
			<div class="l-menubar-item-down"></div>
		</div>
		<div class="l-bar-separator"></div>
		<div id="emailBtn" class="l-toolbar-item l-panel-btn l-toolbar-item-hasicon">
			<span>邮件</span>
			<div class="l-panel-btn-l"></div>
			<div class="l-panel-btn-r"></div>
			<div class="l-icon l-icon-outbox"></div>
		</div>
	</div>
	<!-- 打印子菜单 -->
	<div class="l-menu l-print-menu" style="width: 120px; display: none;">
		<div class="l-menu-yline"></div>
		<div class="l-menu-over">
			<div class="l-menu-over-l"></div> 
			<div class="l-menu-over-r"></div>
		</div>
		<div class="l-menu-inner">
			<div class="l-menu-item" id="pdfPrint">
				<div class="l-menu-item-icon l-icon-pdf-print"></div>
				<div class="l-menu-item-text">打印[客户端]</div>
			</div>
			<div class="l-menu-item" id="appletPrint">
				<div class="l-menu-item-icon l-icon-applet-print"></div>
				<div class="l-menu-item-text">Applet打印</div>
			</div>
			<div class="l-menu-item" id="flashPrint">
				<div class="l-menu-item-icon l-icon-flash-print"></div>
				<div class="l-menu-item-text">打印[客户端]</div>
			</div>
		</div>
	</div>
	<div class="l-menu-shadow l-print-menu-shadow"></div>
	<!-- 输出子菜单 -->
	<div class="l-menu l-output-menu" style="width: 170px; display: none;">
		<div class="l-menu-yline"></div>
		<div class="l-menu-over" >
			<div class="l-menu-over-l"></div> 
			<div class="l-menu-over-r"></div>
		</div>
		<div class="l-menu-inner">
			<div class="l-menu-item" id="pdf">
				<div class="l-menu-item-icon l-icon-pdf"></div>
				<div class="l-menu-item-text">PDF</div>
			</div>
			<div class="l-menu-item" id="excelPage">
				<div class="l-menu-item-icon l-icon-excel"></div>
				<div class="l-menu-item-text">Excel(分页导出)</div>
			</div>
			<div class="l-menu-item" id="excelSimple">
				<div class="l-menu-item-icon l-icon-excel"></div>
				<div class="l-menu-item-text">Excel(原样导出)</div>
			</div>
			<div class="l-menu-item" id="excelSheet">
				<div class="l-menu-item-icon l-icon-excel"></div>
				<div class="l-menu-item-text">Excel(分页分Sheet导出)</div>
			</div>
			<div class="l-menu-item" id="word">
				<div class="l-menu-item-icon l-icon-word"></div>
				<div class="l-menu-item-text">Word</div>
			</div>
		</div>
	</div>
	<div class="l-menu-shadow l-output-menu-shadow"></div>
</body>