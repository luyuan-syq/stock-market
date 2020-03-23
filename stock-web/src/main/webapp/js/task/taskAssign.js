$(function() {
	var assignUrl = "task/assign";
	var taskId = $("#taskId").val();
	$('#selectAccountListGrid').omGrid({
		limit : 0,
		title : '已选择外事人员',
		height : 200,
		colModel : [ {
			header : '账户姓名',
			name : 'userName',
			width : 100,
			align : 'center',
			editor : {}
		}, {
			header : '工作单位',
			name : 'deptName',
			width : 100,
			align : 'center',
			editor : {}
		},{
			header : '单位电话',
			name : 'contactPhone',
			align : 'center',
			width : 100,
			editor : {}
		}, {
			header : '手机',
			name : 'mobile',
			align : 'center',
			width : 100,
			editor : {}
		}, {
			header : '邮箱地址',
			name : 'contactEmail',
			align : 'center',
			width : 400,
			editor : {}
		} ],
		dataSource : "task/getAccountsByTaskId?taskId="+taskId,
		onBeforeEdit : function() {
			$('#demo >:button').attr("disabled", true);
		},
		onAfterEdit : function() {
			$('#demo >:button').removeAttr("disabled");
		},
		onCancelEdit : function() {
			$('#demo >:button').removeAttr("disabled");
		}
	});

	$('#addAccount').click(function() {
		$('#selectAccountListGrid').omGrid('insertRow', 0, {});
	});
	$('#deleteAccount').click(function() {
		var dels = $('#selectAccountListGrid').omGrid('getSelections');
		if (dels.length <= 0) {
			alert('请选择删除的记录！');
			return;
		}
		$('#selectAccountListGrid').omGrid('deleteRow', dels[0]);
	});

	$('#accountListGrid').omGrid(
			{
				limit : 10,
				title : '外事人员列表',
				editable : true,
				height : 300,
				colModel : [
						{
							header : '账户姓名',
							name : 'userName',
							width : 100,
							align : 'center',
							editor : {}
						},
						{
							header : '工作单位',
							name : 'deptName',
							width : 100,
							align : 'center',
							editor : {}
						},
						{
							header : '单位电话',
							name : 'contactPhone',
							align : 'center',
							width : 100,
							editor : {}
						},
						{
							header : '手机',
							name : 'mobile',
							align : 'center',
							width : 100,
							editor : {}
						},
						{
							header : '邮箱地址',
							name : 'contactEmail',
							align : 'center',
							width : 400,
							editor : {}
						},
						{
							header : '操作',
							name : '',
							align : 'center',
							width : 200,
							renderer : function(colValue, rowData, rowIndex) {
								return "<input type = 'button' value ='选择' onclick=\"insertSelectedAccount("
										+ rowData.id + ",'"+rowData.userName+"','"+rowData.deptName+"','"+rowData.contactPhone+"','"+rowData.mobile+"','"+rowData.contactEmail+ "')\"/>"
							}
						} ],
				dataSource : "account/ajaxList",
				onBeforeEdit : function() {
					$('#demo >:button').attr("disabled", true);
				},
				onAfterEdit : function() {
					$('#demo >:button').removeAttr("disabled");
				},
				onCancelEdit : function() {
					$('#demo >:button').removeAttr("disabled");
				}
			});

	$("#itemSave").click(function() {
		_assignTask();
	});

	function _assignTask() {
		var gridData = $('#selectAccountListGrid').omGrid("getData");
		var accountIds = new Array();
		if (gridData.rows && gridData.rows.length > 0) {
			for (var i = 0; i < gridData.rows.length; i++) {
				accountIds[i] = gridData.rows[i].id;
			}
		}
		var data = {};
		data.accountIds = accountIds;
		data.taskId = $("#taskId").val();
		$.ajax({
			url : assignUrl,
			type : 'POST',
			dataType : "json",
			data : data,
			success : function(data) {
				if (data.success) {
					SYQ.Console.showTip('分配外事人员成功', 'success', 4000,new function(){
						location.reload();
					});
					window.location.href="task/list";
				} else {
					SYQ.Console.showTip('分配外事人员失败', 'error', 5000);
				}
			}
		});

	}
})

function insertSelectedAccount(id,userName,deptName,contactPhone,mobile,contactEmail) {
	id = (id == null || id == "undefined") ? "" : id;
	userName = (userName == null || userName == "undefined" || userName == "null") ? "" : userName;
	deptName = (deptName == null || deptName == "undefined" || deptName == "null") ? "" : deptName;
	mobile = (mobile == null || mobile == "undefined" || mobile == "null") ? "" : mobile;
	contactPhone = (contactPhone == null || contactPhone == "undefined" || contactPhone == "null") ? "" : contactPhone;
	contactEmail = (contactEmail == null || contactEmail == "undefined" || contactEmail == "null") ? "" : contactEmail;
	
	var accountListGrid = $('#selectAccountListGrid');
	$('#selectAccountListGrid').omGrid('insertRow', 0, {
		id : id,userName:userName,deptName:deptName,contactPhone:contactPhone,mobile:mobile,contactEmail:contactEmail
	},true);
	
	var dels = $('#accountListGrid').omGrid('getSelections');
	if (dels.length <= 0) {
		return;
	}
	var deleted = dels[0];
	$('#accountListGrid').omGrid('deleteRow', deleted);
}