$(function(){
	  var addtaskURL = "task/add";
	  var dataSource = [];
	  
	  $("#itemSave").click(function(){
		  _submitTaskInfo();
	  });
	  
	  function _submitTaskInfo() {
		  var $form = $("#task-add-form");
		  var self = this;
			if (!$form.valid()) {
				return false;
			}
			var data = $form.formToArray();
			var options = {
				iframe : false,
				url : addtaskURL,
				data : data,
				success : function(data, status, xhr) {
					if (data.success) {
						SYQ.Console.showTip('添加任务成功', 'info',
								5000);
						window.location.href = $("#basePath").val() + "task/list";
					} else {
						SYQ.Console.showTip(data.message, 'info',
								5000);
					}
				}
			};
			$form.ajaxSubmit(options);
			
	  }
	  
	  $("#generateTaskCode").click(function(){
		  var param = {'codeNum':1};
		  $.ajax({
				url : "task/generateTaskCode",
				dataType : 'json',
				data : param,
				type : "GET",
				async: false,
				success : function(data) {
					if (data.success) {
						$("input[name='taskCode']").val(data.taskCode);
					}else{
						SYQ.Console.showTip('生成任务编码失败', 'info',
								5000);
					}
				}
		  });
	  });
	
})