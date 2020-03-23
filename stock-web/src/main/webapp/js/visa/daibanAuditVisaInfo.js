$(function() {
	$("#country-accordion").omAccordion({
		width : '100%',
		height : 500
	});
	loadVisaTemplate($("#visaPrincipalId").val());
	
});

function loadVisaTemplate(visaPrincipalId) {
	var countryApplyVisaInfo = $("div[name=countryApplyVisaInfo]");
	
	$.each(countryApplyVisaInfo,function(index,value,array){
		var data = {};
		data.visaPrincipalId = visaPrincipalId;
		$.ajax({
			url : './visa/getDaibanAuditInfo',
			type : 'GET',
			data : data,
			success : function(dataHtml) {
				$(value).html(dataHtml);
			}
		});
	});
	
}