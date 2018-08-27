(function($){
	$.extend({
		
		/**
		 * 格式化日期，将日期类型格式化为字符串
		 */
		formatDate: function(date, format) {
            if (isNaN(date)) return null;
            var o = {
                "M+": date.getMonth() + 1,
                "d+": date.getDate(),
                "h+": date.getHours(),
                "m+": date.getMinutes(),
                "s+": date.getSeconds(),
                "q+": Math.floor((date.getMonth() + 3) / 3),
                "S": date.getMilliseconds()
            }
            if (/(y+)/.test(format))
            {
                format = format.replace(RegExp.$1, (date.getFullYear() + "")
            .substr(4 - RegExp.$1.length));
            }
            for (var k in o)
            {
                if (new RegExp("(" + k + ")").test(format))
                {
                    format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
                : ("00" + o[k]).substr(("" + o[k]).length));
                }
            }
            return format;
        },
        /**
         * 格式化开始日期
         */
        formatStartDate: function(date, format) {
        	var dateStr = null;
        	if ($.type(date) == 'date') {
        		dateStr = $.formatDate(date, format);
        	}else if ($.type(date) == 'string') {
        		dateStr = date;
        	}
        	if (null != dateStr) {
	        	var dates = dateStr.split(" ");
	        	if (dates[1].length == 2) {
	        		// hh 时间格式
	        		dateStr = dates[0] + " 00";
	        	}else if (dates[1].length == 5) {
	        		// hh:mm时间格式
	        		dateStr = dates[0] + " 00:00";
	        	}else if (dates[1].length == 8) { 
	        		// hh:mm:ss时间格式
	        		dateStr = dates[0] + " 00:00:00";
	        	}
        	}
        	return dateStr;
        },
        
        /**
         * 格式化结束日期
         */
        formatEndDate: function(date, format) {
        	var dateStr = null;
        	if ($.type(date) == 'date') {
        		dateStr = $.formatDate(date, format);
        	}else if ($.type(date) == 'string') {
        		dateStr = date;
        	}
        	if (null != dateStr) {
	        	var dates = dateStr.split(" ");
	        	if (dates[1].length == 2) {
	        		// hh 时间格式
	        		dateStr = dates[0] + " 23";
	        	}else if (dates[1].length == 5) {
	        		// hh:mm时间格式
	        		dateStr = dates[0] + " 23:59";
	        	}else if (dates[1].length == 8) { 
	        		// hh:mm:ss时间格式
	        		dateStr = dates[0] + " 23:59:59";
	        	}
        	}
        	return dateStr;
        },
        
        // 将yyyy-MM-dd格式字符串或日期转换成yyyyMMdd格式日期字符串
	  	formatDateToStr: function(date) {
	  		var dateStr = '';
	  		if (date) {
	             if ($.type(date) == 'date') {
	               	dateStr = $.formatDate(date, 'yyyyMMdd');
	             }else {
	            	 dateStr = date.replace(new RegExp("-", "g"), '');
		         }
	  		}
	         return dateStr;
		},
        /**
		 * 导出到文件
		 * @Params
		 * formObj：form对象，如：$('form')
		 * grid: 导出数据的当前载体表格
		 * exportUrl: 导出URL
		 */
        exportFile: function(formObj, grid, url) {
        	var fields="" ;
        	if (grid) {
	       	    $.each(grid.columns, function(index, col){
	       		     if(!col.issystem && !col._hide ){
	       		     	fields += col.name + ":" + col.display + ",";
	       		     }
	       	    });
	       	    formObj.append("<input name='e.exportFields' id='fields' type='hidden' value='" + fields + "'>");
        	}
            formObj[0].action = url;
            formObj[0].submit();
            $("#fields").remove();
        },
        
        /**
		 * 下载文件
		 * @Params
		 * formObj：form对象，如：$('form')
		 * downloadUrl: 导出URL
		 */
        download: function(formObj, url) {
            formObj[0].action = url;
            formObj[0].submit();
        },
        
        /**
		 * 可带参数的下载文件
		 * @Params
		 * formObj：form对象，如：$('form')
		 * downloadUrl: 导出URL
		 */
        downloadByParam: function(formObj, url) {
        	var paramNames = [];
        	var array = url.split("?");
        	if (array.length > 1) {
        		var params = array[1].split("&");
        		for (var i = 0; i < params.length; i ++) {
        			var obj = params[i].split("=");
		        	formObj.append("<input name='" + obj[0] + "' id='" + obj[0] + "' type='hidden' value='" + obj[1] + "'>");
		        	paramNames.push(obj[0]);
        		}
        	}
        	
            formObj[0].action = url;
            formObj[0].submit();
            if (paramNames.length > 0) {
            	for(var i = 0; i < paramNames.length; i ++) {
            		$("#" + paramNames[i]).remove();
            	}
            }
        },
        
       /**
		 *   功能:计算指定日期加上指定天数月份年份的日期
		 *   参数:type,字符串表达式，表示要添加的时间间隔类型.
		 *   参数:number,数值表达式，表示要添加的时间间隔的个数.
		 *   参数:date,时间对象.
		 *   返回:新的时间对象.
		 *   var now = new Date();
		 *   var newDate = DateAdd( "d", 5, now);
		 *---------------   DateAdd(interval,number,date)   -----------------
		 */
        dateAdd: function(type, number, date) {
		    switch (type) {
			    case "y": { //=year 年
			        date.setFullYear(date.getFullYear() + number);
			        return date;
			        break;
			    }
			    case "q": { //=quarter 季度
			        date.setMonth(date.getMonth() + number * 3);
			        return date;
			        break;
			    }
			    case "M": { //=month 月
			        date.setMonth(date.getMonth() + number);
			        return date;
			        break;
			    }
			    case "w": { //=week 周
			        date.setDate(date.getDate() + number * 7);
			        return date;
			        break;
			    }
			    case "d": { //=day 天
			        date.setDate(date.getDate() + number);
			        return date;
			        break;
			    }
			    case "h": { //=hour 小时
			        date.setHours(date.getHours() + number);
			        return date;
			        break;
			    }
			    case "m": { //=minute 分钟
			        date.setMinutes(date.getMinutes() + number);
			        return date;
			        break;
			    }
			    case "s": { //=second 秒
			        date.setSeconds(date.getSeconds() + number);
			        return date;
			        break;
			    }
			    default: { // 间隔类型以上都不匹配时默认按天
			        date.setDate(d.getDate() + number);
			        return date;
			        break;
			    }
		    }
		},
        
        /**
         * 获得指定日期添加天数后的新日期
         * dateObj: 该参数可以是日期格式也可以是字符串格式
		 *	字符串格式要是合法的日期格式字符串
         * 如 yyyy/MM/dd, yyyy-MM-dd
         * days：添加天数
         * 
         * 返回日期类型
         */
        getNewDate: function(dateObj, days) { 
        	var date = null;
		    if (dateObj && dateObj != '') {
		    	if ($.type(dateObj) == "date") {
			    	date = dateObj;
		    	}else if ($.type(dateObj) == "string") {
		    		date = new Date(dateObj);
		    	}else {
		    		$.ligerDialog.warn('日期参数非法！');
		    	}
		    }else {
		    	date = new Date();
		    }
		    var millSeconds = date.getTime() + (days * 24 * 60 * 60 * 1000);  
		    var ndate = new Date(millSeconds);  
		   
		    return ndate;  
		},
		
		 // 计算两个日期的差值
		dateDiff: function(d1, d2){
		    return $.compareDate(d1, d2);
		},

		// 判断是否为闰年
		isLeapYear: function(year){
		    if(year % 4 == 0 && ((year % 100 != 0) || (year % 400 == 0))){
		           return true;
		      }
		       return false;
		},
 
		 // 获得指定月份天数
		 getDays: function(year, month) {
		 	var days = 30;
		 	var isThirtyOneMonth = false;
		 	var thirtyOneMonth = [1, 3, 5, 7, 8, 10, 12];
		 	for (var a in thirtyOneMonth) {
		 		if (month == thirtyOneMonth[a]) {
		 			isThirtyOneMonth = true;
		 			break;
		 		}
		 	}
		 	if (isThirtyOneMonth) {
		 		days = 31;
		 	} else if (month == 2) {
		 		if ($.isLeapYear(year) == true) {
		 			days = 29;
		 		}else {
		 			days = 28;
		 		}
		 	}
		 	return days;
		 },
		 
		 // 获得新月份
		 getNewMonth: function(date, months) {
		 
		 },
 
		// 计算两个日期之间的天数
		// parseInt() 函数可解析一个字符串，并返回一个整数。
		// parseInt(string, radix)，radix为进制，确定将String转换为那种进制
		//radix=10为十进制，radix=8为八进制，radix=2为二进制
		compareDate: function(date1,date2){
		    // date1格式：yyyyMMdd
			// date2格式：yyyyMMdd
			// 截取年月日
			var year1 = parseInt(date1.substr(0, 4), 10);
			var month1 = parseInt(date1.substr(4, 2), 10);
			var day1 = parseInt(date1.substr(6, 2), 10);
			
			var year2 = parseInt(date2.substr(0, 4), 10);
			var month2 = parseInt(date2.substr(4, 2), 10);
			var day2 = parseInt(date2.substr(6, 2), 10);
			
			// 判断年
			if (parseInt(date1) > parseInt(date2)) {
				alert('对不起，开始日期不能大于结束日期！');
				return;
			}
			
			// 计算年份相差总天数
			var sumYearDays = 0;
			for (var year = year1 + 1; year < year2; year ++) {
				if ($.isLeapYear(year) == true) {
					sumYearDays += 366;
				}else {
					sumYearDays += 365;
				}
			}
			
			// 计算月份相差总天数
			var sumMonthDays = 0;
			// 若为同一年
			if (year1 == year2) {
				if (month1 != month2) {
					for (var month = month1 + 1; month < month2; month ++) {
						sumMonthDays += $.getDays(year1, month);
					}
				}
			}else {
				for (var month = month1 + 1; month <= 12; month ++) {
					sumMonthDays += $.getDays(year1, month);
				}
				for(var month = 1; month < month2; month ++) {
					sumMonthDays += $.getDays(year2, month);
				}
			}
			
			// 计算相差总天数
			var sumDays = 0;
			// 若日期在同一年同一个月
			if (year1 == year2 && month1 == month2) {
				sumDays = day2 - day1 + 1;
			}else {
				sumDays += ($.getDays(year1, month1) - day1 + 1) + day2;
			}
			
			return (sumYearDays + sumMonthDays + sumDays);
		},
		
		// 获得两个日期相差的月数
		// parseInt() 函数可解析一个字符串，并返回一个整数。
		// parseInt(string, radix)，radix为进制，确定将String转换为那种进制
		//radix=10为十进制，radix=8为八进制，radix=2为二进制
		getMonths: function(date1, date2) {
			// date1格式：yyyyMMdd
			// date2格式：yyyyMMdd
			// 截取年月日
			var year1 = parseInt(date1.substr(0, 4), 10);
			var month1 = parseInt(date1.substr(4, 2), 10);
			// 得到月数
			date1 = year1 * 12 + month1;
			
			var year2 = parseInt(date2.substr(0, 4), 10);
			var month2 = parseInt(date2.substr(4, 2), 10);
			// 得到月数
			date2 = year2 * 12 + month2;
			return Math.abs(date1 - date2);
		},
		
		isInt: function(str){
			var reg = /^\d+$/;
			return reg.test(str);
		},
		
		//千分位
		thousands:function (num){
			 result = '';
			 if(num){
				num = num.toString(); 
				var s = "";
				if(num.indexOf('-') !=-1){
					num = num.substring(1);
					s="-";
				}
				//截取小数点左边的
				var _num = num;
				if(num.indexOf(".")>0){
					_num = num.substring(0,num.indexOf(".")); 
					result = num.substring(num.indexOf("."));
				}
				while (_num.length > 3) {
			        result = ',' + _num.slice(-3) + result;
			        _num = _num.slice(0, _num.length - 3);
			    }
			    if (_num) { result = s+_num + result; }
			 }
		     return result;
		}
	})
})(jQuery)