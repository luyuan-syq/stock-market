$(function(){
	var todoCount = $("#todoCount");
	if (todoCount != null && todoCount !='' && todoCount != 'undefined'){
	$.ajax({
		url : './passport/todoCount',
		type : 'GET',
		dataType : "json",
		success : function(data) {
		    var count = data.todoCount;
		    todoCount.append(count);
		}
	});
	}
})