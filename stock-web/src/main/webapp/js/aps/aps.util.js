/*! aps.util.js */
/**
 * add by yepeng
 * 2012-07-04
 * 
 */

window.APS || (window.APS = {});

APS.Util = APS.Util || {};

/**
 * 检测浏览器是否启用了cookie功能
 * @param c_name
 * @returns {Boolean}
 */
APS.Util.isCookieEnable = function(c_name) {
	return navigator.cookieEnabled;
};

/**
 * 删除指定名称的cookie
 * @param c_name
 */
APS.Util.delCookie = function(c_name) {
	APS.Util.setCookie(c_name, 'v', 0);
};
/**
 * 
 * @param c_name
 * @param value
 * @param expiredays 默认值 360
 */
APS.Util.setCookie = function(c_name, value, expiredays) {
	/* 多次设置cookie之后, 可能会存在key相同的cookie, 所以每次修过值的时候先清空 */
	if(0 == expiredays) {
		APS.Util.delCookie(c_name);
	}
	
	var exdate = new Date();
	if(null == expiredays) {
		expiredays = 360;
	}
	exdate.setDate(exdate.getDate() + expiredays);
	document.cookie = c_name + "=" +escape(value) + 
		((expiredays == null) ? "" : ";expires=" + exdate.toGMTString());
},
/**
 * 不存在则返回 ""
 * @param c_name
 * @returns
 */
APS.Util.getCookie = function(c_name) {
	if (document.cookie.length > 0) {
		c_start = document.cookie.indexOf(c_name + "=");
		if(c_start != -1) {
			c_start = c_start + c_name.length + 1;
			c_end = document.cookie.indexOf(";", c_start);
		    if(-1 == c_end) {
		    	c_end = document.cookie.length;
		    }
	    return unescape(document.cookie.substring(c_start, c_end));
		} 
	}
	return "";
};

/**
 * 除了一下4中情况, 其他返回false
 * 1. o1, o2同时为空, 返回true
 * 2. o1, o2 typeof所取的值当中, 一个为 'string', 另一个为 'number', 返回 o1 == o2
 * 3. o1, o2 typeof所取的值当中, 同时为 'string', 或'number', 或'boolean', 返回 o1 == o2
 * 4. o1, o2 typeof所取的值当中, 同时为 'object', 返回 o1 == o2
 * 
 * @param o1
 * @param o2
 */
APS.Util.equals = function(o1, o2) {
	// 对 o1, o2 进行空判断
	if(null == o1 && null == o2) {
		return true;
	}
	if(null == o1 && null != o2 || null != o1 && null == o2) {
		return false;
	}
	
	/* 
	 * typeof 可能获取到的值: 
	 * 
	 * undefined, 即变量没有定义
	 * boolean, 如果变量是Boolean（布尔）类型的
	 * number, 如果变量是Number（数字）类型的
	 * string, 如果变量是String（字符串）类型的
	 * object, 如果变量是一种引用类型或变量值为null
	 * 
	 */
	if(typeof o1 != typeof o2) {
		if(typeof o1 == 'string' && typeof o2 == 'number' ||
				typeof o1 == 'number' && typeof o2 == 'string') {
			return o1 == o2;
		} else {
			return false;
		}
	}
	
	// 以下 typeof o1 == typeof o2, 所以只对 typeof o1 作判断
	
	if(typeof o1 == 'string' || typeof o1 == 'number' || typeof o1 == 'boolean') {
		return o1 == o2;
	}
	
	// 以下只剩下 typeof o1 == 'object' 的情况, 使用 instanceof 判断
	return o1 == o2;
	
};


function keyLogin(e) {
    var keyCode;
    if (window.event) {
        /*ie support event&keyCode*/
        keyCode = event.keyCode;
    } else {
        /*ff support e.which*/
        keyCode = e.which;
    }
    var status = $("#message_dialog").css("display"); 
    if (keyCode == 13) {
        if (status == "none") {
        	checkFormForPassword();
        }else{
            closeDialog("message_dialog");
            if($("#username").val().length==0){
            	$("#username").focus();
            }else{
            	$("#password").focus();
            }
            return;
        }
    } 
}

function swithtap(num) {
	var resMsg = $("span[name=respMsg]");
	resMsg.html('');
    if (num == "1") {
        $("#type2").removeClass("def");
        $("#type1").addClass("def");
        $("#passwordLogin").show();
        $("#certLogin").hide();
    } else {
        $("#type1").removeClass("def");
        $("#type2").addClass("def");
        $("#certLogin").show();
        $("#passwordLogin").hide();
    }

}


