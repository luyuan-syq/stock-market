$(function(){
	
	
	$("#button_submit").click(function(){
		_submitDeliveryInfo();
	});
	  
	$("#button_clear").click(function(){
	});
	  
	  function _submitDeliveryInfo() {
		  var data = {};
		  var id = $("#id").val();
		  var taskId = $("#taskId").val();
		  var number = $("#number").val();
		  var company = $("#company").val();
		  var receiver = $("#receiver").val();
		  var address = $("#address").val();
		  data.id = id;
		  data.taskId = taskId;
		  data.number = number;
		  data.company = company;
		  data.receiver = receiver;
		  data.address = address;
		  $.ajax({
				url : "visa/deliveryUpdate",
				dataType : 'json',
				data : data,
				type : "POST",
				async: false,
				success : function(data) {
					if (data.success) {
						SYQ.Console.showTip('录入快递单信息成功', 'info');
					}else{
						SYQ.Console.showTip('录入快递单信息失败', 'info');
					}
				}
		  });
	  }
});
