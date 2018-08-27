<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
	<meta content='width=330, height=400, initial-scale=1' name='viewport' />
	<link rel="icon" href="favicon.ico" type="../image/x-icon" />
	<title>${initParam.title}</title>
    <link href="../ligerUI/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="../ligerUI/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />	
    <script src="../ligerUI/jquery/jquery-1.6.4.js" type="text/javascript"></script>
	<script src="../ligerUI/jquery-validation/jquery.form.js" type="text/javascript"></script> 
	<script src="../ligerUI/jquery-validation/jquery.validate.js" type="text/javascript"></script> 
    <script src="../ligerUI/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
    <script src="../ligerUI/jquery-validation/messages_cn.js" type="text/javascript"></script>    
    <script src="../ligerUI/ligerUI/js/ligerui.all.js" type="text/javascript"></script>   
    <script src="../ligerUI/ligerUI/js/pap.js" type="text/javascript" ></script>     
    <script type="text/javascript">
   		var groupicon = "../ligerUI/ligerUI/skins/icons/communication.gif";
   		var settingicon = "../ligerUI/ligerUI/skins/icons/modify.gif";
   		var form = null;
   		var currentPage = $.pap.getOpenPage(window);
		var currentParam = currentPage.getParam("param");	
		 
        $(function(){
			// 分钟
            var minutes = [
    			    { text: '0', id: '0' },{ text: '1', id: '1' },{ text: '2', id: '2' },{ text: '3', id: '3' },
    			    { text: '4', id: '4' },{ text: '5', id: '5' },{ text: '6', id: '6' },{ text: '7', id: '7' },
	       		    { text: '8', id: '8' },{ text: '9', id: '9' },{ text: '10', id: '10' },{ text: '11', id: '11' },
	    			{ text: '12', id: '12' },{ text: '13', id: '13' },{ text: '14', id: '14' },{ text: '15', id: '15' },
	    			{ text: '16', id: '16' },{ text: '17', id: '17' },{ text: '18', id: '18' },{ text: '19', id: '19' },
	    			{ text: '20', id: '20' },{ text: '21', id: '21' },{ text: '22', id: '22' },{ text: '23', id: '23' },
	    			{ text: '24', id: '24' },{ text: '25', id: '25' },{ text: '26', id: '26' },{ text: '27', id: '27' },
	    			{ text: '28', id: '28' },{ text: '29', id: '29' },{ text: '30', id: '30' },{ text: '31', id: '31' },
	    			{ text: '32', id: '32' },{ text: '33', id: '33' },{ text: '34', id: '34' },{ text: '35', id: '35' },
	    			{ text: '36', id: '36' },{ text: '37', id: '37' },{ text: '38', id: '38' },{ text: '39', id: '39' },
	    			{ text: '40', id: '40' },{ text: '41', id: '41' },{ text: '42', id: '42' },{ text: '43', id: '43' },
	    			{ text: '44', id: '44' },{ text: '45', id: '45' },{ text: '46', id: '46' },{ text: '47', id: '47' },
	    			{ text: '48', id: '48' },{ text: '49', id: '49' },{ text: '50', id: '50' },{ text: '51', id: '51' },
	    			{ text: '52', id: '52' },{ text: '53', id: '53' },{ text: '54', id: '54' },{ text: '55', id: '55' },
	    			{ text: '56', id: '56' },{ text: '57', id: '57' },{ text: '58', id: '58' },{ text: '59', id: '59' }
                ];
            
            var internalMinutes = [
       			    { text: '1', id: '1' },{ text: '2', id: '2' },{ text: '3', id: '3' },
       			    { text: '4', id: '4' },{ text: '5', id: '5' },{ text: '6', id: '6' },{ text: '7', id: '7' },
   	       		    { text: '8', id: '8' },{ text: '9', id: '9' },{ text: '10', id: '10' },{ text: '11', id: '11' },
   	    			{ text: '12', id: '12' },{ text: '13', id: '13' },{ text: '14', id: '14' },{ text: '15', id: '15' },
   	    			{ text: '16', id: '16' },{ text: '17', id: '17' },{ text: '18', id: '18' },{ text: '19', id: '19' },
   	    			{ text: '20', id: '20' },{ text: '21', id: '21' },{ text: '22', id: '22' },{ text: '23', id: '23' },
   	    			{ text: '24', id: '24' },{ text: '25', id: '25' },{ text: '26', id: '26' },{ text: '27', id: '27' },
   	    			{ text: '28', id: '28' },{ text: '29', id: '29' },{ text: '30', id: '30' },{ text: '31', id: '31' },
   	    			{ text: '32', id: '32' },{ text: '33', id: '33' },{ text: '34', id: '34' },{ text: '35', id: '35' },
   	    			{ text: '36', id: '36' },{ text: '37', id: '37' },{ text: '38', id: '38' },{ text: '39', id: '39' },
   	    			{ text: '40', id: '40' },{ text: '41', id: '41' },{ text: '42', id: '42' },{ text: '43', id: '43' },
   	    			{ text: '44', id: '44' },{ text: '45', id: '45' },{ text: '46', id: '46' },{ text: '47', id: '47' },
   	    			{ text: '48', id: '48' },{ text: '49', id: '49' },{ text: '50', id: '50' },{ text: '51', id: '51' },
   	    			{ text: '52', id: '52' },{ text: '53', id: '53' },{ text: '54', id: '54' },{ text: '55', id: '55' },
   	    			{ text: '56', id: '56' },{ text: '57', id: '57' },{ text: '58', id: '58' },{ text: '59', id: '59' }
                   ];
                 
       		  // 小时
            var hours = [
    			    { text: '0', id: '0' },{ text: '1', id: '1' },{ text: '2', id: '2' },{ text: '3', id: '3' },
    			    { text: '4', id: '4' },{ text: '5', id: '5' },{ text: '6', id: '6' },{ text: '7', id: '7' },
	       		    { text: '8', id: '8' },{ text: '9', id: '9' },{ text: '10', id: '10' },{ text: '11', id: '11' },
	    			{ text: '12', id: '12' },{ text: '13', id: '13' },{ text: '14', id: '14' },{ text: '15', id: '15' },
	    			{ text: '16', id: '16' },{ text: '17', id: '17' },{ text: '18', id: '18' },{ text: '19', id: '19' },
	    			{ text: '20', id: '20' },{ text: '21', id: '21' },{ text: '22', id: '22' },{ text: '23', id: '23' }
                ]; 
            
            var internalHours = [
         			    { text: '1', id: '1' },{ text: '2', id: '2' },{ text: '3', id: '3' },
         			    { text: '4', id: '4' },{ text: '5', id: '5' },{ text: '6', id: '6' },{ text: '7', id: '7' },
     	       		    { text: '8', id: '8' },{ text: '9', id: '9' },{ text: '10', id: '10' },{ text: '11', id: '11' },
     	    			{ text: '12', id: '12' },{ text: '13', id: '13' },{ text: '14', id: '14' },{ text: '15', id: '15' },
     	    			{ text: '16', id: '16' },{ text: '17', id: '17' },{ text: '18', id: '18' },{ text: '19', id: '19' },
     	    			{ text: '20', id: '20' },{ text: '21', id: '21' },{ text: '22', id: '22' },{ text: '23', id: '23' }
                     ]; 
         	// 天
            var days = [
    			    { text: '1', id: '1' },{ text: '2', id: '2' },{ text: '3', id: '3' },
    			    { text: '4', id: '4' },{ text: '5', id: '5' },{ text: '6', id: '6' },{ text: '7', id: '7' },
	       		    { text: '8', id: '8' },{ text: '9', id: '9' },{ text: '10', id: '10' },{ text: '11', id: '11' },
	    			{ text: '12', id: '12' },{ text: '13', id: '13' },{ text: '14', id: '14' },{ text: '15', id: '15' },
	    			{ text: '16', id: '16' },{ text: '17', id: '17' },{ text: '18', id: '18' },{ text: '19', id: '19' },
	    			{ text: '20', id: '20' },{ text: '21', id: '21' },{ text: '22', id: '22' },{ text: '23', id: '23' },
	    			{ text: '24', id: '24' },{ text: '25', id: '25' },{ text: '26', id: '26' },{ text: '27', id: '27' },
	    			{ text: '28', id: '28' },{ text: '29', id: '29' },{ text: '30', id: '30' },{ text: '31', id: '31' }
                ];  
            
         	// 月
            var months = [
   			    { text: '1', id: '1' },{ text: '2', id: '2' },{ text: '3', id: '3' },
   			    { text: '4', id: '4' },{ text: '5', id: '5' },{ text: '6', id: '6' },{ text: '7', id: '7' },
       		    { text: '8', id: '8' },{ text: '9', id: '9' },{ text: '10', id: '10' },{ text: '11', id: '11' },
    			{ text: '12', id: '12' }
            ];   
            var weeks = [
   			    { text: '星期日', id: '1' },{ text: '星期一', id: '2' },{ text: '星期二', id: '3' },{ text: '星期三', id: '4' },
   			    { text: '星期四', id: '5' },{ text: '星期五', id: '6' },{ text: '星期六', id: '7' }
            ];  

            form = $("form").ligerForm({
   	  		 	 inputWidth: 300,
   	  		 	 labelWidth: 60, 
   	  		 	 space: 20,
	  			 fields: [
					{ display: "作业编号", name: "e.quartzJobId", options: { value: currentParam['id'] }, type: "text", hidden: true },
					{ display: '规则设置', name: "e.setOptions", type: "radiogroup", newline: true, options:{               
					   	data: [
					        { text: '自定义', id: '0' },
						   	{ text: '手动设置', id: '1' }
					     ],
					     name: 'e.setOptions',
					     value: '0'     
					}},
					{ display: '', type: 'label', space: 95, newline: true, group: '自定义', groupicon: groupicon },
					{ display: '秒', name: 'lsecond', type: 'label', space: 70 },
					{ display: '分钟', type: 'label', space: 65 },
					{ display: '小时', type: 'label', space: 75 },
					{ display: '天', type: 'label', space: 75 },
					{ display: '月', type: 'label', space: 70 },
					{ display: '星期', type: 'label', space: 70 },
					{ display: '年', type: 'label' },
					{ display: '自定义', name: 'e.dsecond', type: 'text', options: { value: '${e.dsecond}'}, width: 80, space: 10, newline: true },
					{ hideLabel: true, name: 'e.dminute', type: 'text', options: { value: '${e.dminute}'}, width: 80, space: 10 },
					{ hideLabel: true, name: 'e.dhour', type: 'text', options: { value: '${e.dhour}'}, width: 80, space: 10 },
					{ hideLabel: true, name: 'e.dday', type: 'text', options: { value: '${e.dday}'}, width: 80, space: 10 },
					{ hideLabel: true, name: 'e.dmonth', type: 'text', options: { value: '${e.dmonth}'}, width: 80, space: 10 },
					{ hideLabel: true, name: 'e.dweek', type: 'text', options: { value: '${e.dweek}'}, width: 80, space: 10 },
					{ hideLabel: true, name: 'e.dyear', type: 'text', options: { value: '${e.dyear}'}, width: 80 },
					{ display: '', type: 'label', newline: true },
					{ display: '', type: 'label', newline: true, group: '手动设置', groupicon: groupicon },
					{ display: '条件', name: "e.minuteOptions", type: "radiogroup", newline: true, group: '分钟', groupicon: settingicon, options:{               
					   	data: [
							{ text: '每分钟', id: '0' },
						   	{ text: '周期', id: '1' },
					        { text: '指定', id: '2' }
				         ],
				         name: 'e.minuteOptions',
				         value: '0',
				         disabled: true     
					}},
					{ display: '周期：', type: 'label', space: 25, newline: true },
                	{ display: '从', type: 'label', space: 5 },
                	{ hideLabel: true, name: "e.startMinute", type: "select", width: 50, space: 5, comboboxName: 'startMinuteName', options:{               
  				       	data: minutes,
  			            selectBoxHeight: 100,
  			          	disabled: true
  	                }},
  	              { display: '分钟开始到', type: 'label', space: 5 },
              	  { hideLabel: true, name: "e.endMinute", type: "select", space: 5, comboboxName: 'endMinuteName', width: 50, options:{               
				       	data: minutes,
			            selectBoxHeight: 100,
			            disabled: true
	                }},
                	{ display: '分钟结束，每', type: 'label', space: 5 },
                	{ hideLabel: true, name: "e.intervalMinute", type: "select", space: 5, comboboxName: 'intervalMinuteName', width: 50, options:{               
  				       	data: internalMinutes,
  			            selectBoxHeight: 100,
  			            disabled: true
  	                }},
                	{ display: '分钟执行一次', type: 'label'},
                	{ display: '指定', name: "e.assignMinutes", type: "checkboxgroup", newline: true, width: 500, options:{       
                		rowSize: 12,        
    			       	data: minutes,
    			       	name: 'e.assignMinutes',
    			       	disabled: true   
                    }},
                    { display: '条件', name: "e.hourOptions", type: "radiogroup", newline: true, group: '小时', groupicon: settingicon, options:{               
					   	data: [
						   	{ text: '每小时', id: '0' },
						   	{ text: '周期', id: '1' },
					        { text: '指定', id: '2' }
				         ],
				         name: 'e.hourOptions',
				         value: '0',
				         disabled: true    
					}},
					{ display: '周期：', type: 'label', space: 25, newline: true },
	               	{ display: '从', type: 'label', space: 5 },
	               	{ hideLabel: true, name: "e.startHour", type: "select", width: 50, space: 5, comboboxName: 'startHourName', options:{               
 				       	data: hours,
 			            selectBoxHeight: 100,
 			            disabled: true
 	                }},
  	              { display: '小时开始到', type: 'label', space: 5 },
              	  { hideLabel: true, name: "e.endHour", type: "select", space: 5, comboboxName: 'endHourName', width: 50, options:{               
				       	data: hours,
			            selectBoxHeight: 100,
			            disabled: true
	                }},
                	{ display: '小时结束，每', type: 'label', space: 5 },
                	{ hideLabel: true, name: "e.intervalHour", type: "select", space: 5, comboboxName: 'intervalHourName', width: 50, options:{               
  				       	data: internalHours,
  			            selectBoxHeight: 100,
  			          	disabled: true
  	                }},
                	{ display: '小时执行一次', type: 'label'},
                	{ display: '指定', name: "e.assignHours", type: "checkboxgroup", newline: true, width: 500, options:{       
                		rowSize: 12,        
    			       	data: hours,
    			       	name: 'e.assignHours',
    			       	disabled: true
                    }},
                    { display: '条件', name: "e.dayOptions", type: "radiogroup", newline: true, group: '天', groupicon: settingicon, options:{               
					   	data: [
						   	{ text: '每天', id: '0' },
					        { text: '指定', id: '1' }
				         ],
				         name: 'e.dayOptions',
				         value: '0',
				         disabled: true     
					}},
                	{ display: '指定', name: "e.assignDays", type: "checkboxgroup", newline: true, width: 500, options:{       
                		rowSize: 12,        
    			       	data: days,
    			       	name: 'e.assignDays',
    			       	disabled: true
                    }},
                    { display: '条件', name: "e.monthOptions", type: "radiogroup", newline: true, group: '月', groupicon: settingicon, options:{               
					   	data: [
						   	{ text: '每月', id: '0' },
					        { text: '指定', id: '1' }
				         ],
				         name: 'e.monthOptions',
				         value: '0',
				         disabled: true   
					}},
                	{ display: '指定', name: "e.assignMonths", type: "checkboxgroup", newline: true, width: 500, options:{       
                		rowSize: 12,        
    			       	data: months,
    			       	name: 'e.assignMonths',
    			       	disabled: true
                    }},
                    { display: '使用星期', name: "e.useWeek", type: "checkbox", width: 500, newline: true, group: '星期', groupicon: settingicon, options: { checkedValue: 'Y', uncheckedValue: 'N' }},
                    { display: '条件', name: "e.weekOptions", type: "radiogroup", newline: true, options:{               
					   	data: [
						   	{ text: '每星期', id: '0' },
					        { text: '指定', id: '1' }
				         ],
				        name: 'e.weekOptions',
				        value: '0',
				        disabled: true
					}},
                	{ display: '指定', name: "e.assignWeeks", type: "checkboxgroup", newline: true, width: 500, options:{       
                		rowSize: 7,        
    			       	data: weeks,
    			       	name: 'e.assignWeeks',
    			       	disabled: true
                    }}
               	 ]
        	});
        	
          	  // 自定义文本框元素
		      var definedElements = ['e.dsecond', 'e.dminute', 'e.dhour', 'e.dday', 'e.dmonth', 'e.dweek', 'e.dyear'];
		      // 手动设置单选框元素
		      var handedElements = ['e.minuteOptions', 'e.hourOptions', 'e.dayOptions', 'e.monthOptions', 'e.useWeek'];
		      // 分钟周期和指定元素
		      var minuteEles = ['startMinuteName', 'endMinuteName', 'intervalMinuteName', 'e.assignMinutes'];
		      // 小时周期和指定元素
		      var hourEles = ['startHourName', 'endHourName', 'intervalHourName', 'e.assignHours'];
		      // 天指定元素
		      var dayEles = 'e.assignDays';
		      // 月指定元素
		      var monthEles = 'e.assignMonths';
		      // 星期
		      var weekEles = ['e.weekOptions', 'e.assignWeeks'];

            //规则设置单选框
           
		    $("input[name='e.setOptions']").change(function() {
          	  var value = liger.get("e.setOptions").getValue();
		      // 若自定义==0，手动设置==1
          	  if (value == '0') {
				   // 自定义元素置为可用状态
				   setDisabled(definedElements, false);
          		// 手动设置元素置为禁用状态
				   setDisabled(handedElements, true);
				   setDisabled(minuteEles, true);
				   setDisabled(hourEles, true);
				   setDisabled(dayEles, true);
				   setDisabled(monthEles, true);
				   setDisabled(weekEles, true);
			   }else {
				   // 自定义元素置为禁用状态
				   setDisabled(definedElements, true);
				   // 手动设置元素置为可用状态
				   setDisabled(handedElements, false);

				   // 分钟单选框
				   var minuteItem = liger.get("e.minuteOptions").getValue();
				   if (minuteItem == '1') {// 周期
				    	setDisabled(minuteEles, false);
				    	setDisabled(minuteEles[3], true); // 指定设置为禁用
					}else if (minuteItem == '2') {// 指定
						setDisabled(minuteEles, true);
						setDisabled(minuteEles[3], false);
					}
				   // 小时单选框
				   var hourItem = liger.get("e.hourOptions").getValue();
				   if (hourItem == '1') {// 周期
				    	setDisabled(hourEles, false);
				    	setDisabled(hourEles[3], true); // 指定设置为禁用
					}else if (hourItem == '2') {// 指定
						setDisabled(hourEles, true);
						setDisabled(hourEles[3], false);
					}
				   // 月单选框
				   var monthItem = liger.get("e.monthOptions").getValue();
				   if (monthItem == '1') {// 周期
						setDisabled(monthEles, false);
					}
				   // 使用星期
				   // 星期单选框
				   if ($("#e.useWeek").attr("checked")){
	                	setDisabled('e.weekOptions', false);
					    var weekItem = liger.get("e.weekOptions").getValue();
	                    if (weekItem == '1') {
	                    	setDisabled('e.assignWeeks', false);
	                    	setDisabled(handedElements[2], true);
	                    }
	                }else {
					   // 天单选框
					   var dayItem = liger.get("e.dayOptions").getValue();
					   if (dayItem == '1') {// 指定
							setDisabled(dayEles, false);
						}
			        }
			   }
          });

            // 启用/禁用方法
            function setDisabled(elements, disabled) {
                if ($.isArray(elements)) {
	                for (var index in elements) {
	                	liger.get(elements[index]).set('disabled', disabled);
	                }
                }else {
                	liger.get(elements).set('disabled', disabled);
                }
            }
            
         	// 分钟规则设置单选框
            $("input[name='e.minuteOptions']").change(function() {
                var value = liger.get("e.minuteOptions").getValue();
                
		    	if (value == '0') {// 每分钟
		    		setDisabled(minuteEles, true);
			    }else if (value == '1') {// 周期
			    	setDisabled(minuteEles, false);
			    	setDisabled(minuteEles[3], true); // 指定设置为禁用
				}else {// 指定
					setDisabled(minuteEles, true);
					setDisabled(minuteEles[3], false);
				}
            });

         	// 小时规则
            $("input[name='e.hourOptions']").change(function() {
           	    var value = liger.get("e.hourOptions").getValue();
                  
  		    	if (value == '0') {// 每小时
  		    		setDisabled(hourEles, true);
  			    }else if (value == '1') {// 周期
  			    	setDisabled(hourEles, false);
  			    	setDisabled(hourEles[3], true); // 指定设置为禁用
  				}else {// 指定
  					setDisabled(hourEles, true);
  					setDisabled(hourEles[3], false);
  				}
            });

         	// 天规则
            $("input[name='e.dayOptions']").change(function() {
            	var value = liger.get("e.dayOptions").getValue();
                    
   		    	if (value == '0') {// 每天
   		    		setDisabled(dayEles, true);
   			    }else if (value == '1') {// 指定
   			    	setDisabled(dayEles, false);
   				}
            });

         	// 月规则
            $("input[name='e.monthOptions']").change(function() {
           	    var value = liger.get("e.monthOptions").getValue();
                      
   		    	if (value == '0') {// 每月
   		    		setDisabled(monthEles, true);
   			    }else if (value == '1') {// 指定
   			    	setDisabled(monthEles, false);
   				}
            });
            
            // 使用星期
            $("input[name='e.useWeek']").change(function() {
                if ($("input[name='e.useWeek']").is(':checked')){
                	setDisabled('e.weekOptions', false);
                	var value = liger.get("e.weekOptions").getValue();
                    if (value == '0') {
                    	setDisabled('e.assignWeeks', true);
                    }else {
                    	setDisabled('e.assignWeeks', false);
                    }
                    // 天和星期不能同时指定
                    // 若启用星期，则将天禁用掉
                    setDisabled(handedElements[2], true); // 禁用天条件单选框
                    setDisabled(dayEles, true);// 禁用指定元素
                }else {
                	setDisabled('e.assignWeeks', true);
                	setDisabled('e.weekOptions', true);

                	setDisabled(handedElements[2], false); // 禁用天条件单选框
                	// 天单选框
				   var dayItem = liger.get("e.dayOptions").getValue();
				   if (dayItem == '1') {// 周期
				    	setDisabled(dayEles, false);
					}
                }
            });

            // 星期规则设置单选框
           $("input[name='e.weekOptions']").change(function() {
                var value = liger.get("e.weekOptions").getValue();
                if (value == '0') {
                	setDisabled('e.assignWeeks', true);
                }else {
                	setDisabled('e.assignWeeks', false);
                }
            });
                
      });

        /** form提交 */
        var onSave = function(){
	  		$("form").submit();
			return $('form').formSerialize();
		}
    </script>
</head>
<body style="padding: 10px">
<form />
</body>
</html>

