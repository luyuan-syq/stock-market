var selectContryDialog;
var chuchacountry;
var chuchaevent;
var updateTaskURL = "task/update";
$(function() {
	var dataSource = [];
	var taskCountryValue = $("input[name='taskCountry']").val();
	var taskCountrys = taskCountryValue.split(",");
	var hasGroupMember = $('#hasGroupMemberHidden').val();
	var costSource = $('#costSourceHidden').val();
	var taskStatus = $('#taskStatusHidden').val();
	if (taskStatus != -1) {
		$("#itemSave").html('返回');
		$("#itemSave").click(function() {
			window.location.href = "passport/todoFaffList";
		});
	} else {
		$("#itemSave").click(function() {
			_submitTaskInfoForTaskEdit();
		});
	}

	// 获取国家数据列表
	$.ajax({
		url : "task/queryVisaCountryData",
		dataType : 'json',
		data : "",
		type : "GET",
		async : false,
		success : function(data) {
			dataSource = data.countryDatas;
		}
	});

	var elementCountryDialog = $("#select-country-dialog");

	for (var i = 0; i < dataSource.length; i++) {
		var $input = $("<span style='margin-right:15px'><input name='chuchacountry' type='radio' value='"
				+ dataSource[i].value
				+ "' countryText='"
				+ dataSource[i].text
				+ "'/>" + dataSource[i].text + "</span>");
		$input.appendTo(elementCountryDialog);
	}

	selectContryDialog = elementCountryDialog
			.omDialog({
				title : '选择国家',
				height : '390',
				width : '520',
				resizable : false,
				modal : true,
				autoOpen : false,
				buttons : [
						{
							text : "确定",
							click : function() {
								chuchacountry = $(
										"input[name=chuchacountry]:checked")
										.val();
								chuchacountryText = $(
										"input[name=chuchacountry]:checked")
										.attr("countryText");
								var target = chuchaevent.target ? chuchaevent.target
										: chuchaevent.srcElement;
								var parent = $(target).parents(
										'.default-value-item');
								parent.find("span.taskCountry").text(
										chuchacountryText);
								parent.find("input[name=selectTaskCountry]")
										.val(chuchacountry);
								selectContryDialog.omDialog('close');
							}
						}, {
							text : "取消",
							click : function() {
								selectContryDialog.omDialog('close');
							}
						} ]
			});

	$("#costSource option").each(function() {
		var test1 = $(this).val();
		var costSource1 = costSource;
		if ($(this).val() == costSource) {
			$(this).attr("selected", "selected");
		}
	});

	$("input[type=radio][name=hasGroupMember]").each(function() {
		if ($(this).val() == hasGroupMember) {
			$(this).attr("checked", "checked");
		}
	});

	var taskCountry = $('#taskCountry').omItemSelector({
		availableTitle : '可选择国家',
		selectedTitle : '已选择国家',
		height : 300,
		width : 300,
		dataSource : dataSource,
		value : taskCountrys
	});

});

function _submitTaskInfoForTaskEdit() {
		var $form = $("#task-add-form");
		var costSourceSelect = $('#costSource').val();
		var taskDescription = $('#taskDescription').val();

		var self = this;
		if (!$form.valid()) {
			return false;
		}
		if (costSourceSelect == 3
				&& (taskDescription == null || taskDescription == '')) {
			alert("经费来源为【其他】时需填写备注信息");
			return false;
		}
		var data = $form.formToArray();
		$.ajax({
			url : updateTaskURL,
			dataType : 'json',
			data : data,
			type : "POST",
			async : false,
			success : function(data) {
				if (data.success) {
					SYQ.Console.showTip('更新任务成功', 'info', 5000);
					window.location.href = "passport/todoFaffList?select=1";
				} else {
					SYQ.Console.showTip('更新任务失败', 'info', 5000);
				}
			}
		});

	}

/**
 * 添加默认值 如果默认值列表中有一个输入框是空或者空字符串，那么不会添加新的默认值项
 * 本函数对dom是有要求的，'.default-value-action'与.default-value-content必须是兄弟元素
 */
function addCountry(event) {
	var e = event || window.event;
	var target = e.target ? e.target : e.srcElement;
	var parent = $(target).parents('.default-value-action');
	var defaultValueEmpty = false;
	var defaultValueContent = parent.siblings('.default-value-content');
	var defaultValueInputSelector = 'input[name="defaultValues"]';
	$(defaultValueInputSelector, defaultValueContent).each(function() {
		var defaultValue = $(this).val();
		if ('' == $.trim(defaultValue)) {
			defaultValueEmpty = true;
			$(this).focus();
			return false;
		}
	});
	if (true == defaultValueEmpty) {
		return;
	} else {
		var template = $('.default-value-item-template').children(
				'.default-value-item');
		var templateDom = template.clone();
		templateDom.appendTo(defaultValueContent);
		$(defaultValueInputSelector, templateDom).focus();
	}
}

function createDefaultValue(event) {
	var e = event || window.event;
	var target = e.target ? e.target : e.srcElement;
	var parent = $(target).parents('.default-value-action');
	var defaultValueEmpty = false;
	var defaultValueContent = parent.siblings('.default-value-content');

	if (true == defaultValueEmpty) {
		return;
	} else {
		var template = $("#default-value-item-clone").children(
				".default-value-item");
		var templateDom = template.clone();
		templateDom.appendTo(defaultValueContent);
		$(defaultValueInputSelector, templateDom).focus();
	}
}

/**
 * 删除默认值
 */
function deleteDefaultValue(event) {
	var e = event || window.event;
	var target = e.target ? e.target : e.srcElement;
	var defaultValueItem = $(target).parent('.default-value-item');
	defaultValueItem.remove();
}

function selectCountry(event) {
	chuchaevent = event;
	selectContryDialog.omDialog('open');
}
