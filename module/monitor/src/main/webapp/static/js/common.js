/**
 * 获取时间
 * 
 */
formateTime = function(x) {
	var time = new Date();
	time.setTime(x);
	
	// 小时
	var hours = time.getHours();
	if (hours < 10) {
		hours = "0" + hours;
	}
	// 分钟
	var minutes = time.getMinutes();
	if (minutes < 10) {
		minutes = "0" + minutes;
	}
	// 秒
	var seconds = time.getSeconds();
	if (seconds < 10) {
		seconds = "0" + seconds;
	}
	// 月份
	var month = time.getMonth() + 1;
	if (month < 10) {
		month = "0" + month;
	}
	// 日期
	var date = time.getDate();
	if (date < 10) {
		date = "0" + date;
	}

	var hour = hours + ":" + minutes + ":" + seconds;
	var date = month + "-" + date;
	var year = time.getYear();

	return date + " " + hour;
}
// 画图
draw = function(datax, datay, title, yTitle, id, scale){
    var container = document.getElementById(id);

    var myChart = echarts.init(container);
    
	option = {
		    tooltip: {
		        trigger: 'axis',
		        position: function (pt) {
		            return [pt[0], '10%'];
		        }
		    },
		    title: {
		        left: 'center',
		        text: title,
		    },
		    toolbox: {
		        feature: {
		            dataZoom: {
		                yAxisIndex: 'none'
		            },
		            restore: {},
		            saveAsImage: {}
		        }
		    },
		    xAxis: {
		        type: 'category',
		        axisLine: {onZero: true},
		        boundaryGap: false,
		        data: datax
		    },
		    yAxis: {
		        type: 'value',
		        boundaryGap: [0, '100%'],
		        axisLabel : {
		        	formatter : '{value}' + yTitle
		        }
		    },
		    dataZoom: [{
		        type: 'inside',
		        start: 0,
		        end: 300
		    }, {
		        start: 0,
		        end: 300,
		        handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
		        handleSize: '80%',
		        handleStyle: {
		            color: '#fff',
		            shadowBlur: 3,
		            shadowColor: 'rgba(0, 0, 0, 0.6)',
		            shadowOffsetX: 2,
		            shadowOffsetY: 2
		        }
		    }],
		    series: [
		        {
		            name:title,
		            type:'line',
		            smooth:true,
		            symbol: 'none',
		            sampling: 'average',
		            itemStyle: {
		                normal: {
		                    color: 'rgb(135, 206, 250)'
		                }
		            },
		            label : {
		            	normal : {
		            		formatter : '{b}: {c}' + yTitle
		            	}
		            },
		            areaStyle: {
		                normal: {
		                    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
		                        offset: 0,
		                        color: 'rgb(255, 158, 68)'
		                    }, {
		                        offset: 1,
		                        color: 'rgb(135, 206, 250)'
		                    }])
		                }
		            },
		            data: datay
		        }
		    ]
		};
	myChart.setOption(option);
};