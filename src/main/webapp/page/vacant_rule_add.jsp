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
            
       		  // 小时
            var hours = [
    			    { text: '0', id: '0' },{ text: '1', id: '1' },{ text: '2', id: '2' },{ text: '3', id: '3' },
    			    { text: '4', id: '4' },{ text: '5', id: '5' },{ text: '6', id: '6' },{ text: '7', id: '7' },
	       		    { text: '8', id: '8' },{ text: '9', id: '9' },{ text: '10', id: '10' },{ text: '11', id: '11' },
	    			{ text: '12', id: '12' },{ text: '13', id: '13' },{ text: '14', id: '14' },{ text: '15', id: '15' },
	    			{ text: '16', id: '16' },{ text: '17', id: '17' },{ text: '18', id: '18' },{ text: '19', id: '19' },
	    			{ text: '20', id: '20' },{ text: '21', id: '21' },{ text: '22', id: '22' },{ text: '23', id: '23' }
                ]; 
            

            form = $("form").ligerForm({
   	  		 	 inputWidth: 150,
   	  		 	 labelWidth: 90, 
   	  		 	 space: 20,
	  			 fields: [
					{ display: "管理编号", name: "e.manageId", options: { value: currentParam['id'] }, type: "text", hidden: true },
					{ display: "业务对象编码", name: "bussObj", options: { value: currentParam['bussObj'], readonly: true }, newline: true, type: "text", group: '空号规则设置', groupicon: groupicon },
					{ display: "业务对象名称", name: "bussName", options: { value: currentParam['bussName'], readonly: true }, newline: true, type: "text" },
					{ display: '生成条件', name: "e.genOptions", type: "radiogroup", width: 300, newline: true, options:{               
					   	data: [
					        { text: '每天', id: '0' },
						   	{ text: '指定(仅指定日期当天执行)', id: '1' }
					     ],
					     name: 'e.genOptions',
					     value: '0'     
					}},
					{ display: '指定日期', name: "e.execDate", type: "date", newline: true },
					{ display: '时间段', name: "e.startHour", type: "select", newline: true, validate: { required: true }, space: 5, comboboxName: 'startHourName', width: 50, options:{               
				       	data: hours,
			            selectBoxHeight: 150
	                }},
	                { display: '时', type: 'label', space: 5 },
	                { hideLabel: true, name: "e.startMinute", type: "select", width: 50, space: 5, comboboxName: 'startMinuteName', options:{               
  				       	data: minutes,
  			            selectBoxHeight: 150
  	                }},
  	                { display: '分开始到', type: 'label', space: 5 },
  	                { hideLabel: true, name: "e.endHour", type: "select", space: 5, comboboxName: 'endHourName', width: 50, options:{               
				       	data: hours,
			            selectBoxHeight: 150
	                }},
	                { display: '时', type: 'label', space: 5 },
	                { hideLabel: true, name: "e.endMinute", type: "select", space: 5, comboboxName: 'endMinuteName', width: 50, options:{               
				       	data: minutes,
			            selectBoxHeight: 150
	                }},
	                { display: '分结束', type: 'label', space: 5 },
	                { display: '号码数量', name: "e.numberNum", type: "text", options: { digits: true }, validate: { required: true }, newline: true }
               	 ]
        	});

            var genOptions = '${e.genOptions}';
            if (genOptions == '0') {
            	$("input[name='e.execDate']").parent().parent().parent().parent().css({ display: 'none' });
            }
            $("input[name='e.execDate']").parent().parent().parent().parent().css({ display: 'none' });
            $("input[name='e.genOptions']").change(function() {
            	  var value = liger.get("e.genOptions").getValue();
  		      	  // 若每天==0，指定==1
	           	  if (value == '0') {
	  				  $("input[name='e.execDate']").val("");
	  				  $("input[name='e.execDate']").parent().parent().parent().parent().css({ display: 'none' });
	  			  }else {
	  				$("input[name='e.execDate']").parent().parent().parent().parent().css({ display: 'block' });
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

