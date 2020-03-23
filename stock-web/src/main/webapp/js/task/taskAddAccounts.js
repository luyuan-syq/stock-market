var selectAccountListGrid = null;
$(function() {
	var assignUrl = "task/assign";
	var taskId = $("#taskId").val();
	selectAccountListGrid = $('#selectAccountListGrid').omGrid({
		limit : 0,
		title : '添加出国人员',
		width : 1000,
		height : 300,
		colModel : [ {
			header : '账户姓名',
			name : 'userName',
			width : 100,
			align : 'center',
			editor : {}
		}, {
			header : '部门名称',
			name : 'deptName',
			width : 200,
			align : 'left',
			editor : {}
		},  {
			header : '身份证号',
			name : 'idNumber',
			width : 200,
			align : 'left',
			editor : {}
		}, {
			header : '单位电话',
			name : 'mobile',
			align : 'left',
			width : 100,
			editor : {}
		}, {
			header : '手机',
			name : 'phone',
			align : 'left',
			width : 100,
			editor : {}
		}, {
			header : '邮箱地址',
			name : 'email',
			align : 'left',
			width : 400,
			editor : {}
		} ],
		dataSource : "task/getPersonsByTaskId?taskId="+taskId,
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
		
		addPersonDialogTemp.omDialog("open");
	});
	$('#deleteAccount').click(function() {
		var dels = $('#selectAccountListGrid').omGrid('getSelections');
		if (dels.length <= 0) {
			alert('请选择删除的记录！');
			return;
		}
		$('#selectAccountListGrid').omGrid('deleteRow', dels[0]);
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
				} else {
					SYQ.Console.showTip('分配外事人员失败', 'error', 5000);
				}
			}
		});

	}
	
	var addPersonDialogTemp = $("#create-person-dialog")
	.omDialog({
		title : '添加外事人员',
		height : '390',
		width : '520',
		resizable : false,
		modal : true,
		autoOpen : false,
		buttons : [
				{
					text : "确定",
					click : function() {
						addPerson();
					}
				},
				{
					text : "取消",
					click : function() {
						addPersonDialogTemp
								.omDialog('close');
					}
				} ]
	});
	
	function addPerson() {
		var userName = $("input[name=userName]").val();
		var deptName = $("input[name=deptName]").val();
		var idCard = $("input[name=idCard]").val();
		var contactPhone = $("input[name=contactPhone]").val();
		var mobile = $("input[name=mobile]").val();
		var contactEmail = $("input[name=contactEmail]").val();
		var postData = {
				userName:userName,
				deptName:deptName,
				idCard:idCard,
				contactPhone:contactPhone,
				mobile:mobile,
				contactEmail:contactEmail
		};
		$.ajax({
			url : "person/add",
			type : 'POST',
			dataType : "json",
			data : postData,
			success : function(data) {
				if (data.success) {
					SYQ.Console.showTip('添加出差人员成功', 'success', 4000,new function(){
						selectAccountListGrid.omGrid('insertRow',0,data.personBo,true);
//						selectAccountListGrid.omGrid("reload");
						addPersonDialogTemp.omDialog('close');
					});
				} else {
					SYQ.Console.showTip('添加出差人员失败', 'error', 5000);
					addPersonDialogTemp.omDialog('close');
				}
			}
		});
	}
})

function insertSelectedAccount(id,userName,deptName,idNumber,mobile,phone,email) {
	$('#selectAccountListGrid').omGrid('insertRow', 0, {
		id : id,userName:userName,deptName:deptName,idNumber:idNumber,mobile:mobile,phone:phone,email:email
	},true);
}