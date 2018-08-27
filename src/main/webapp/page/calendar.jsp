<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="x-ua-compatible" content="ie=7"/>
    <title>中国万年历</title>
    <link href="../ligerUI/original/calendar/css/all.css" rel="stylesheet" type="text/css"/>
    <link href="../ligerUI/original/calendar/css/skin.css" rel="stylesheet" type="text/css"/>
    <link href="../ligerUI/original/calendar/css/fontSize12.css" rel="stylesheet" type="text/css"/>
    <link href="../ligerUI/original/calendar/css/calendar.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="../ligerUI/jquery/jquery-1.6.4.js"></script>
    <script type="text/javascript" src="../ligerUI/original/calendar/js/calendar.js"></script>
</head>
<body>
    <div id="myrl" style="width:100%;height:260px;overflow:hidden;">
        <form name=CLD>
            <TABLE class="biao" width="100%">
                <TBODY>
                <TR>
                    <TD class="calTit" colSpan=7 style="height:29px;padding-top:3px;text-align:center;">

                        <a href="#" title="上一年" id="nianjian" class="ymNaviBtn lsArrow"></a>
                        <a href="#" title="上一月" id="yuejian" class="ymNaviBtn lArrow"></a>

                        <div style="width:250px; float:left; padding-left:110px;">
                                            <span id="dateSelectionRili" class="dateSelectionRili"
                                                  style="cursor:hand;color: white; border-bottom: 1px solid white;"
                                                  onclick="dateSelection.show()">
											<span id="nian" class="topDateFont"></span><span
                                                    class="topDateFont">年</span><span id="yue"
                                                                                      class="topDateFont"></span><span
                                                    class="topDateFont">月</span>
											<span class="dateSelectionBtn cal_next"
                                                  onclick="dateSelection.show()">▼</span></span> &nbsp;&nbsp;<font id=GZ
                                                                                                                   class="topDateFont"></font>
                        </div>

                        <!--新加导航功能-->
                        <div style="left: 152px; display: none;" id="dateSelectionDiv">
                            <div id="dateSelectionHeader"></div>
                            <div id="dateSelectionBody">
                                <div id="yearList">
                                    <div id="yearListPrev" onclick="dateSelection.prevYearPage()">&lt;</div>
                                    <div id="yearListContent"></div>
                                    <div id="yearListNext" onclick="dateSelection.nextYearPage()">&gt;</div>
                                </div>
                                <div id="dateSeparator"></div>
                                <div id="monthList">
                                    <div id="monthListContent">
										<span id="SM0" class="month" onclick="dateSelection.setMonth(0)">1</span>
										<span id="SM1" class="month" onclick="dateSelection.setMonth(1)">2</span>
										<span id="SM2" class="month" onclick="dateSelection.setMonth(2)">3</span>
										<span id="SM3" class="month" onclick="dateSelection.setMonth(3)">4</span>
										<span id="SM4" class="month" onclick="dateSelection.setMonth(4)">5</span>
										<span id="SM5" class="month" onclick="dateSelection.setMonth(5)">6</span>
										<span id="SM6" class="month" onclick="dateSelection.setMonth(6)">7</span>
										<span id="SM7" class="month" onclick="dateSelection.setMonth(7)">8</span>
										<span id="SM8" class="month" onclick="dateSelection.setMonth(8)">9</span>
										<span id="SM9" class="month" onclick="dateSelection.setMonth(9)">10</span>
										<span id="SM10" class="month" onclick="dateSelection.setMonth(10)">11</span>
										<span id="SM11" class="month curr" onclick="dateSelection.setMonth(11)">12</span>
                                    </div>
                                    <div style="clear: both;"></div>
                                </div>
                                <div id="dateSelectionBtn">
                                    <div id="dateSelectionTodayBtn" onclick="dateSelection.goToday()">今天</div>
                                    <div id="dateSelectionOkBtn" onclick="dateSelection.go()">确定</div>
                                    <div id="dateSelectionCancelBtn" onclick="dateSelection.hide()">取消</div>
                                </div>
                            </div>
                            <div id="dateSelectionFooter"></div>
                        </div>
                        <a href="#" id="nianjia" title="下一年" class="ymNaviBtn rsArrow" style="float:right;"></a>
                        <a href="#" id="yuejia" title="下一月" class="ymNaviBtn rArrow" style="float:right;"></a>
                        <!--	<a id="jintian" href="#" title="今天" class="btn" style="float:right; margin-top:-2px; font-size:12px; text-align:center;">今天</a>-->

                    </TD>
                </TR>
                <TR class="calWeekTit" style="font-size:12px; height:22px;text-align:center;">
                    <TD width="97" class="red">星期日</TD>
                    <TD width="97">星期一</TD>
                    <TD width="97">星期二</TD>
                    <TD width="97">星期三</TD>
                    <TD width="97">星期四</TD>
                    <TD width="97">星期五</TD>
                    <TD width="97" class="red">星期六</TD>
                </TR>
                <SCRIPT language="JavaScript">

                    var gNum;
                    for (var i = 0; i < 6; i++) {
                        document.write('<tr align=center height="30" id="tt">');
                        for (var j = 0; j < 7; j++) {
                            gNum = i * 7 + j ;
                            document.write('<td  id="GD' + gNum + '"  onMouseOver="mOvr(this,' + gNum + ');"  onMouseOut="mOut(this);"><font  id="SD' + gNum + '" style="font-size:12px;"  face="Arial"');
                            if (j == 0)  document.write('color=red');
                            if (j == 6)
                                if (i % 2 == 1)  document.write('color=red');
                                else  document.write('color=red');
                            document.write('  TITLE="">  </font><br><font  id="LD' + gNum + '"  size=2  style="white-space:nowrap;overflow:hidden;cursor:default;">  </font></td>');
                        }
                        document.write('</tr>');
                    }
                </SCRIPT>
                </tbody>
            </TABLE>
        </form>
    </div>
	
<div id="details" style="margin-top:-1px;"></div>
</body>
</html>