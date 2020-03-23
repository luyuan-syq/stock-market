$(function() {
	$("#country-accordion").omAccordion({
		width : '100%',
		height : 500
	});
	loadVisaTemplate($("#personId").val(),$("#taskId").val());
	
});

function loadVisaTemplate(personId,taskId) {
	var countryApplyVisaInfo = $("div[name=countryApplyVisaInfo]");
	
	$.each(countryApplyVisaInfo,function(index,value,array){
		var data = {};
		data.id = personId;
		data.code = $(value).attr("code");
		data.taskId = taskId;
		$.ajax({
			url : './visa/getInfo',
			type : 'GET',
			data : data,
			success : function(dataHtml) {
				$(value).html(dataHtml);
			}
		});
	});
	
}