if(!window.Aps) {
    var Aps = {UI:{}};
    Aps.$ = Aps.jQuery = jQuery;
}
(function(){
    var _id = 1;
    Aps.id = function(){
        return "aps-"+_id++;
    };
})();
Aps.mixin = function(t, s) {
    for(k in s) {
        t[k] = s[k];
    }
    return t;
};
//清空当前数组
/*
Array.prototype.clear = function(){
    this.length = 0;
};
Array.prototype.remove=function(dx){ 
	if(isNaN(dx)||dx>this.length){return false;} 
	for(var i=0,n=0;i<this.length;i++) {
		if(this[i]!=this[dx]) { 
			this[n++]=this[i]; 
		} 
	} 
	this.length-=1;
}; */

// 创建APS命名空间
var APS = window.APS || {};
APS.contextPath = APS.contextPath || undefined;

//防止IE中调用console.log()报异常
if(typeof window.console == "undefined"){
 window.console = {
 	log: function(msg){}
 };
};

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
APS.mixin = function(t, s) {
    for(k in s) {
        t[k] = s[k];
    }
    return t;
};

//将表单数据转为JSON
APS.formToJson = function(form) {
    var r = {};
    var a = $(form).formToArray();
    for(var i = 0; i < a.length; i++) {
        var d = a[i];
        r[d.name] = d.value;
    }
    return r;
};