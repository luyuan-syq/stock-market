$(function(){
	
	$("#button_submit").click(function(){
		  _submitPassportInfo();
	});
	  
	$("#button_clear").click(function(){
		  $form.reset();
	});
	  
	  function _submitPassportInfo() {
		  var data = {};
		  var id = $("#id").val();
		  var taskId = $("#taskId").val();
		  var passportNo = $("#passportNo").val();
		  if (passportNo == null || passportNo == ''){
			  alert('护照编码不能为空');
			  return false;
		  }
		  var surname = $("#surname").val();
		  if (surname == null || surname == ''){
			  alert('姓名不能为空');
			  return false;
		  }
		  var sex = $("#sex").val();
		  var idNumber = $("#idNumber").val();
		  if (idNumber == null || idNumber == ''){
			  alert('身份证号不能为空');
			  return false;
		  }
		  var dateIssue = $("#dateIssue").val();
		  if (dateIssue == null || dateIssue == ''){
			  alert('护照办法日期不能为空');
			  return false;
		  }
		  var dateExpire = $("#dateExpire").val();
		  if (dateExpire == null || dateExpire == ''){
			  alert('护照过期日期不能为空');
			  return false;
		  }
		  var placeBirth = $("#placeBirth").val();
		  var placeIssue = $("#placeIssue").val();
		  // 流转状态至已归档5 
		  var flowStatus = 5; 
		  var flowMsg = "已归档"; 
		  data.id = id;
		  data.passportNo = passportNo;
		  data.name = surname;
		  data.sex = sex;
		  data.idNumber = idNumber;
		  data.dateIssue = dateIssue;
		  data.dateExpire = dateExpire;
		  data.placeBirth = placeBirth;
		  data.placeIssue = placeIssue;
		  data.flowStatus = flowStatus;
		  data.flowMsg = flowMsg;
		  $.ajax({
				url : "passport/update",
				dataType : 'json',
				data : data,
				type : "POST",
				async: false,
				success : function(data) {
					if (data.success = "true" || data.success) {
						window.location.href = "passport/list";
						//window.location.href = "person/list?taskId=" + taskId;
					}else{
						SYQ.Console.showTip('录入护照资料失败', 'info',5000);
					}
				}
		  });
	  }
});

function FormatDate (t) {
    var dateStr=t.trim().split(" ");
    // 就是这一行代码和上面的字符串分隔，然后拼接而成的GMT
    var strGMT = dateStr[0]+" "+dateStr[1]+" "+dateStr[2]+" "+dateStr[5]+" "+dateStr[3]+" GMT+0800";
    var time = new Date(Date.parse(strGMT));
    return time
}
