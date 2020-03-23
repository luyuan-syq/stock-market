$(function(){
	  var addtaskURL = "task/add";
	  var dataSource = [];
	  //获取国家数据列表
	  $.ajax({
			url : "task/queryVisaCountryData",
			dataType : 'json',
			data : "",
			type : "GET",
			async: false,
			success : function(data) {
				dataSource = data.countryDatas;
			}
	  });
		
	  var taskCountry = $('#taskCountry').omItemSelector({
          availableTitle : '可选择国家',
          selectedTitle : '已选择国家',
          height:300,
          width:300,
          dataSource : dataSource,
          value:['hubei','shaanxi']
      });
	  
	  $("#itemSave").click(function(){
		  _submitTaskInfo();
	  });
	  
	  function _submitTaskInfo() {
		  var $form = $("#task-add-form");
		  var countrys = $('#taskCountry').omItemSelector('value');
		  var self = this;
			if (!$form.valid()) {
				return false;
			}
			var data = $form.formToArray();
			data.taskCountry = countrys;
			var options = {
				iframe : false,
				url : addtaskURL,
				data : data,
				success : function(data, status, xhr) {
					if (data.success) {
						SYQ.Console.showTip('添加任务成功', 'info',
								5000);
//						self.taskGrid.omGrid('reload');
//						self.$addtaskDialog
//								.omDialog("close");
						window.location.href = "task/list";
					} else {
						SYQ.Console.showTip('添加任务失败', 'info',
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