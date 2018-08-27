<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<script type="text/javascript" src="../ligerUI/jquery/jquery-1.6.4.js"></script>
<script src="../ligerUI/original/Highcharts/js/highcharts.src.js"></script>
<script src="../ligerUI/original/Highcharts/js/themes/grid.js"></script>
<script type="text/javascript">
	$(function () {
	    $(document).ready(function() {
	        Highcharts.setOptions({
	            global: {
	                useUTC: false
	            }
	        });
	    
	        var chart;
	        $('#container').highcharts({
	           chart: {
	           		 borderColor:'#ffffff',
	                type: 'spline',
	                animation: Highcharts.svg, // don't animate in old IE
	                marginRight: 10,
	                events: {
	                    load: function() {
	                        // set up the updating of the chart each second
	                        var series = this.series[0];
	                        setInterval(function() {
	                            var x = (new Date()).getTime(), // current time
	                                y = Math.random();
	                            series.addPoint([x, y], true, true);
	                        }, 5000);
	                    }
	                }
	            },
	            title: {
	                text: 'CPU使用情况'
	            },
	            xAxis: {
	                type: 'datetime',
	                tickPixelInterval: 150
	            },
	            yAxis: {
	                title: {
	                    text: '值'
	                },
	                plotLines: [{
	                    value: 0,
	                    width: 1,
	                    color: '#808080'
	                }]
	            },
	            tooltip: {
	                formatter: function() {
	                        return '<b>'+ this.series.name +'</b>:'+
	                        Highcharts.dateFormat('%H:%M:%S', this.x) +'<br/>'+
	                        Highcharts.numberFormat(this.y, 2);
	                }
	            },
	            legend: {
	                enabled: false
	            },
	            exporting: {
	                enabled: false
	            },
	            series: [{
	                name: 'cpu',
	                data: (function() {
	                    // generate an array of random data
	                    var data = [],
	                        time = (new Date()).getTime(),
	                        i;
	    
	                    for (i = -15; i <= 0; i++) {
	                        data.push({
	                            x: time + i * 1000,
	                            y: 0
	                        });
	                    }
	                    return data;
	                })()
	            }]
	        });
	        
	        $('#container2').highcharts({
	           chart: {
	                borderColor:'#ffffff',
	                type: 'spline',
	                animation: Highcharts.svg, // don't animate in old IE
	                marginRight: 10,
	                events: {
	                    load: function() {
	                        // set up the updating of the chart each second
	                        var series = this.series[0];
	                        setInterval(function() {
	                            var x = (new Date()).getTime(), // current time
	                                y = Math.random();
	                            series.addPoint([x, y], true, true);
	                        }, 2000);
	                    }
	                }
	            },
	            title: {
	                text: '内存使用情况'
	            },
	            xAxis: {
	                type: 'datetime',
	                tickPixelInterval: 150
	            },
	            yAxis: {
	                title: {
	                    text: '值'
	                },
	                plotLines: [{
	                    value: 0,
	                    width: 1,
	                    color: '#808080'
	                }]
	            },
	            tooltip: {
	                formatter: function() {
	                        return '<b>'+ this.series.name +'</b>:'+
	                        Highcharts.dateFormat('%H:%M:%S', this.x) +'<br/>'+
	                        Highcharts.numberFormat(this.y, 2);
	                }
	            },
	            legend: {
	                enabled: false
	            },
	            exporting: {
	                enabled: false
	            },
	            series: [{
	                name: 'cpu',
	                data: (function() {
	                    // generate an array of random data
	                    var data = [],
	                        time = (new Date()).getTime(),
	                        i;
	    
	                    for (i = -15; i <= 0; i++) {
	                        data.push({
	                            x: time + i * 1000,
	                            y: 0
	                        });
	                    }
	                    return data;
	                })()
	            }]
	        });
	    });
	});
	</script>
	</head>
<body>
<div id="container" style="min-width: 100%px; height: 120px; margin: 0 auto"></div>
<div id="container2" style="min-width: 100%px; height: 120px; margin: 0 auto"></div>
</body>
</html>
