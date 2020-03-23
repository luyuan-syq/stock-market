window.SYQ = window.SYQ || {};
SYQ.Console = SYQ.Console || {};

/*
 * 收缩 插件 
 */
SYQ.Console.switchHandle = function() {
	var $switch = $('#aps-col2 .aps-console-column-switch');
	$switch.click(function(event) {
		// 为了兼容IE 7
		if (-1 == event.target.parentNode.className.indexOf('aps-console-column-switch')) {
			return;
		}
		var $left = $('#aps-col1');
		var $right = $('#aps-col3');
		$left.toggle();
		$(this).toggleClass('aps-console-column-exspand').toggleClass('aps-console-column-exspand-layout-console').
				toggleClass('aps-console-column-colspand').toggleClass('aps-console-column-colspand-layout-console');

		var apsPageMargins = $($(this).parents('.aps-page_margins').get(0));
		apsPageMargins.toggleClass('aps-page_margins_exspand');

		var marginLeftVal = $right.css('margin-left').substring(0, $right.css('margin-left').length - 2) / 1;
		var marginLeftTargetVal = 0;
		// var widthTargetVal = 0;
		if ($left.css('display') === 'none') {
			marginLeftTargetVal = marginLeftVal - $left.outerWidth();
			// widthTargetVal = $right.width() + $left.outerWidth();
		} else {
			marginLeftTargetVal = marginLeftVal + $left.outerWidth();
			// widthTargetVal = $right.width() - $left.outerWidth();
		}
		$right.css('margin-left', marginLeftTargetVal);
		// 布局是自动撑大, 所以不用设宽度
		// $right.width(widthTargetVal);
	});
};


/**
 * 覆盖层 
 */
$(function (){
	SYQ.Console.Overlay = new function() {
		this.init = function (){
			if(SYQ.Console.Overlay.dom){
				return ;
			}
			$('body').append('<div class="aps-overlay">&nbsp;</div>');
			SYQ.Console.Overlay.dom = $(".aps-overlay");
			SYQ.Console.Overlay.dom.width(document.body.scrollWidth).height(document.body.scrollHeight);
		}
		
		this.show  = function (){
			this.init();
			SYQ.Console.Overlay.dom.show();
		}
		
		this.hide = function (){
			this.init();
			SYQ.Console.Overlay.dom.hide();
		}
	};
})
		



