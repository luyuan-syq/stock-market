/*! aps.portal.js*/;
(function() {
	APS.Portal = function(options) {
		this.ENGINE_CONTEXT_PATH = options.contextPath;
		if (null == this.ENGINE_CONTEXT_PATH) {
			throw new Error('Apusic Context Path is required!');
		}
	};
	
	APS.Portal.prototype = {
		constructor: APS.Portal,
		addPortlet: function(portletInfo) {
			if (null == portletInfo || null == portletInfo.name) {
				throw new Error('Portlet\'name is required!');
			}
			var self = this;
			$.ajax({
				url: self.ENGINE_CONTEXT_PATH + '/ajaxapi',
				data: {
					action: 'add',
					id: portletInfo.name,
					row: portletInfo.row,
					col: portletInfo.col
				}
			});
		}
	};
})(APS);