//支持syq。console。tip。js时一个单独的模块
window.SYQ = window.SYQ || {};
SYQ.Console = SYQ.Console || {};
/**
 * 管理控制台统一提示，标题及内容均支持html
 * 当type为error时，提示框需要手动关闭
 * 
 * content 提示内容
 * type 提示类型，默认为info 可选值为 info success warning error
 * timeout 提示信息显示时间 单位毫秒
 *                    默认情况下，info 4000，success 3000，warning和error无限大。
 *                    非正常情况按默认值
 *  callBack 回调函数，提供一个参数，当前提示框的引用，如果成功提示，在提示框消失之后执行，
 *                  如果是错误提示，则马上执行。
 *        
 *  注：如果第三个参数是Function，则将其作为回调函数，同时忽略第四个参数
 */

SYQ.Console.showTip = function(content,type,timeout,callBack) {
	try{
		var _content = content;
		var _type = type;
		var _timeout = timeout;
		var _callBack = callBack;
		
		if(timeout instanceof Function) {
			_callBack = timeout;
			_timeout = null;
		}
		
		_type = _type == null ? "info" : _type;
		var defaultTimeout = "info" == _type?4000:3000;
		_type = "info" == _type ?"alert":_type;
		_content = null != _content?_content:
			           "sucess" == _type?"操作成功":
			           "error" == _type?"操作出现错误":
			            "info" == _type?"请注意":"您的操作可能有误";
		_timeout = ("error" == _type || "warning" == _type)?null:_timeout?_timeout:defaultTimeout;
		
		if("success" != _type) {
			var $tip = window.top.$.omMessageTip.show({
				title : "error" == _type?"错误":"warning"==_type?"警告":"提醒",
				content: _content,
				type:_type,
				timeout:_timeout
			});
			if(_callBack) {
				_callBack($tip);
			}
			return $tip;
		}else{
			var $tip = $('<div class="aps-show-tip"><span class="aps-show-tip-content">'+_content+'</span></div>').appendTo($('body')[0]).hide();
			var left = ($(window).width() - $tip.outerWidth(true)) /2;
			$tip.css('left',left).fadeIn(400);
			setTimeout(function(){
				$tip.fadeOut(800);
				setTimeout(function(){
					$tip.remove();
					if(_callBack) {
						_callBack($tip);
					}
				},1000);
			},_timeout);
			return $tip;
		}
	}catch(e) {
		throw new Error("显示提示内容出错");
	}
};

/**
 *
 */
SYQ.Console.closeTip = function($tip) {
	if($tip instanceof Array) {
		for(var i = self.$tip.length - 1;i>=0;i--) {
			_close($tip[i]);
		}
	}else{
		_close($tip);
	}
	//释放引用句柄
	$tip = null;
	function _close($tip) {
		if(null != $tip) {
			if($tip.hasClass('aps-show-tip')) {
				$tip.remove();
			}else{
				$tip.close(0);
				$tip = null;
			}
		}
	}
};