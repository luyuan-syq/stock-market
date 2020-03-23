/*
 * @auther LiuXiaotian 2012/06/21 10:16:16
 *
 * @version 1.0
 * @requires jQuery
 * 
 * 用于生成页面上个性化的返回顶部的链接
 * 注意: 如果页面上有多个元素都设置成scrolltop, 后者覆盖前者, 所以不建议设置多个, 某个页面只是用一个scrolltop即可
 * 用户可以通过cssClass来设置class属性, 但是无法设置width, height, position, display
 * 用户可以通过position来指定scrolltop出现的位置, 
 * 有以下规则(同jQuery Css对positon: fixed;情况):
 * 1. 只有top, right, bottom, left四种设置
 * 2. top优先级大于bottom, left优先级大于right
 * 3. 设置的值必须为数字, 单位默认为(px), 如果设置的数值不符合, 设置失效
 * 4. 如果没有设置, 就按Css默认方式处理
 */
;
(function($) {
	$.fn.scrolltop = function(options) {
		var self = $(this);
		// 清空用户所设置的样式, 
		self.attr('style', '');
		self.hide();
		
		if ($.isEmptyObject(options)) {
			options = {};
		}
		
		if ($.isEmptyObject(options.position)) {
			options.position = {};
		}
		
		var _options = $.extend($.fn.scrolltop.defaults, options);
		
		self.addClass(_options.cssClass);
		
		self.css({
			'width': _options.width,
			'height': _options.height,
			'z-index': _options.zIndex,
			'position': 'fixed',
			'top': _options.position.top,
			'right': _options.position.right,
			'bottom': _options.position.bottom,
			'left': _options.position.left,
			'cursor': _options.cursor
		});
		
		// top优先级大于bottom, left优先级大于right, 这也是Css默认的规则
		$(window).scroll(function() {
			if ($(window).scrollTop() >= _options.offset) {
				self.fadeIn(_options.delay);
			} else {
				self.fadeOut(_options.delay);
			}
		});
		
		self.click(function() {
			$('html, body').animate({
				scrollTop: 0
			}, _options.delay);
		});
	};
	
	// 参数列表, 均以jQuery所默认的px为单位
	$.fn.scrolltop.defaults = {
		// 宽
		width: 64,
		// 高
		height: 64,
		// 位置
		delay: 1000,
		// 滚动条偏移值>=offset的时候出现scrolltop
		offset: 100,
		// 用户所设置的class, 将以addClass(cssClass)的形式被添加
		cssClass: null,
		cursor: 'pointer',
		zIndex: 100
	};
})(jQuery);