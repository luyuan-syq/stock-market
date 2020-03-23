/*模块：命名空间*/
var SYQ = window.SYQ || {};
//防止IE中调用console.log()报异常
if(typeof window.console == "undefined") {
	window.console = {
		log:function(msg){}
	};
};

//当前对象的代理对象
Function.prototype.createDelegate = function(context) {
	var _t = this;
	return function() {
		_t.apply(context, arguments);
	};
};
//判断是否存在属性，非继承
Function.prototype.isBlankObject = function() {
	var isBlank = true;
	for(var key in this) {
		if(this.hasOwnProperty(key)) {
			isBlank = false;
			break;
		}
	}
	return isBlank;
};

//清空当前数组
Array.prototype.clear = function() {
	this.length = 0;
};
Array.prototype.remove = function(ds) {
	if(isNaN(ds) && ds >= this.length)
		return false;
	for(var i = 0,n=0;i<this.length;i++) {
		if(this[i] != this[ds]) {
			this[n++] =  this[i];
		}
	}
	this.length -= 1;
};

var formattime = function(time) {
	var format = 'yyyy-MM-dd';
	if (time == null || time == "") {
		return null;
	}
	var t = new Date(time);
	var tf = function(i) {
		return (i < 10 ? '0' : '') + i
	};
	return format.replace(/yyyy|MM|dd/g, function(a) {
		switch (a) {
			case 'yyyy':
				return tf(t.getFullYear());
				break;
			case 'MM':
				return tf(t.getMonth() + 1);
				break;
			case 'mm':
				return tf(t.getMinutes());
				break;
			case 'dd':
				return tf(t.getDate());
				break;
			case 'HH':
				return tf(t.getHours());
				break;
			case 'ss':
				return tf(t.getSeconds());
				break;
		}
	})

}

/**
 * 将毫秒时间转为 yyyy-MM-dd HH:mm:ss日期格式
 * 
 * @13011146
 */
var fullFormattime = function(time) {
	var format = 'yyyy-MM-dd HH:mm:ss';
	if (time == null || time == "") {
		return null;
	}
	var t = new Date(time);
	var tf = function(i) {
		return (i < 10 ? '0' : '') + i
	};
	return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a) {
		switch (a) {
			case 'yyyy':
				return tf(t.getFullYear());
				break;
			case 'MM':
				return tf(t.getMonth() + 1);
				break;
			case 'mm':
				return tf(t.getMinutes());
				break;
			case 'dd':
				return tf(t.getDate());
				break;
			case 'HH':
				return tf(t.getHours());
				break;
			case 'ss':
				return tf(t.getSeconds());
				break;
		}
	})

}

//字符串格式化
String.prototype.format = function() {
	var args = arguments;
	return this.replace(/\{(\d+)\}/g,
	    function(m,i){
	        return args[i];
	    });
};
String.prototype.trim=function(){
    return this.replace(/(^\s*)|(\s*$)/g, "");
};

//默认选择
$(function(){
//	$("#syq-leftnav a:first").click();
})