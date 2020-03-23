
$(function() {
	var taskStatus = $("#taskStatus").val();
	var taskId = $("#taskId").val();
	var taskOpDiv = $("#taskOpDiv");
	var baseUrl = location.protocol + "//" + location.host + location.pathname.substring(0,location.pathname.indexOf("/",1));
	
	// 任务待接收可以触发快递确认
	if (taskStatus == 9) {
		$("<input type = 'button' style='margin-right:20px;' id='taskConfirm' value = '快递确认'/>").appendTo(taskOpDiv);
		$("#taskConfirm").click(function() {
			var postData = {
					id:taskId,
					taskStatus:2
			}
			
			$.ajax({
				url : './task/operateStatus',
				type : 'POST',
				dataType : "json",
				data : postData,
				success : function(data) {
					if (data.success) {
						alert("快递确认成功");
						window.location.href = "passport/todoFaffList";
					} else {
						SYQ.Console.showTip('快递确认失败', 'error', 5000);
					}
				}
			});
			return false;
		});
	}
	
	// 任务新建可以触发录入和添加人员
	if (taskStatus == -1) {
		$("<input type = 'button' style='margin-right:20px;' id='taskInsert' value = '任务录入'/>").appendTo(taskOpDiv);
	    $("#taskInsert").click(function() {
		    window.location.href = "./passport/todoFaffList?select=1"
		    return false;
	    });
	}
	$("<input type = 'button' style='margin-right:20px;' id='taskAddPerson' value = '添加人员'/>").appendTo(taskOpDiv);
    $("#taskAddPerson").click(function() {
    	window.location.href = "./passport/todoFaffList?select=2"
	    return false;
    });
});