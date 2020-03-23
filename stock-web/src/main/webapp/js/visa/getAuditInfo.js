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
				} else {
					SYQ.Console.showTip('提交签证申请资料失败', 'info', 5000);
				}
			}
		};
		$form.ajaxSubmit(options);
	})
})


function auditPass(principalId) {
	var data = {
			principalId:principalId,
			status:3
	}
	$.ajax({
		url : 'visa/audit',
		data : data,
		type : "post",
		dataType : 'json',
		success : function(
				result) {
			if (result.success) {
				SYQ.Console.showTip("审核成功!",'info',5000);
				window.location.href = "./passport/todoList?select=4";
			} else {
				SYQ.Console.showTip("审核失败！",'info',5000);
			}
		}

	});
}

function auditRefused(principalId,auditMsg) {
	var data = {
			principalId:principalId,
			status:2,
			auditMsg:$("#"+auditMsg).val()
	}
	$.ajax({
		url : 'visa/audit',
		data : data,
		type : "post",
		dataType : 'json',
		success : function(
				result) {
			if (result.success) {
				SYQ.Console.showTip("拒绝成功!",'info',5000);
			} else {
				SYQ.Console.showTip("拒绝失败！",'info',5000);
			}
		}

	});
}