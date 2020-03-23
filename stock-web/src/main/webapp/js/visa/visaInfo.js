$(function() {
	$("#buttonApplyVisaInfo").click(function(event) {
		var targetObject = $(event.currentTarget);
		var code = $(targetObject).attr("code");
		var $form = $("#visa-apply-info-form-"+code);
		var options = {
			iframe : false,
			url : './visa/applyVisaInfoAJAX',
			success : function(data, status, xhr) {
				if (data.success) {
					SYQ.Console.showTip('提交签证申请资料成功', 'info', 5000);
					window.location.reload();
				} else {
					SYQ.Console.showTip('提交签证申请资料失败', 'info', 5000);
				}
			}
		};
		$form.ajaxSubmit(options);
	})
})