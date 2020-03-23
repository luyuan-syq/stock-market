var selectPersonListGrid = null;
$(function() {
	
	$("#birthday").omCalendar({dateFormat : 'yy-mm-dd',showTime:false});
	
	var assignUrl = "task/assign";
	var taskId = $("#taskId").val();
	var hasApplyPersonIds = $("#hasApplyIds").val();
	selectPersonListGrid = $('#selectPersonListGrid').omGrid({
		limit:0,
		showIndex : false,
		width : 1160,
		title : '添加出国人员',
		colModel : [{
			header : '姓名',
			name : 'userName',
			width: '50'
		},{
			header : '性别',
			name : 'sexey',
			width: '50',
			renderer : function(colValue, rowData, rowIndex) {
				if(colValue == 0){
					return "男";
				}else if(colValue == 1) {
				   return "女"
				}else {
					return "未知性别";
				}
            }
		},{
			header : '出生日期',
			name : 'birthday',
			width: '100',
			renderer : function(colValue, rowData, rowIndex) {
				if(colValue == "" || colValue == null){
					return "";
				}
                return formattime(colValue);
            }
		},{
			header : '出生地',
			name : 'placeBirth',
			width: '100'
		},  {
			header : '工作单位',
			name : 'deptName',
			width: '200'
		},  {
			header : '身份证号',
			name : 'idNumber',
			width: '150'
		}, {
			header : '职务',
			name : 'business',
			width: '150'
		}, {
			header : '对外身份',
			name : 'identity',
			width: '50',
			renderer : function(colValue, rowData, rowIndex) {
                if(colValue == 2) {
				   return "团员"
				}else if(colValue == 3) {
				   return "团长"
				}else {
					return "未知身份";
				}
            }
		}, {
			header : '护照审核状态',
			name : 'flowStatus',
			width: '200',
			renderer : function(colValue, rowData, rowIndex) {
				if (colValue == 0){
					return "新建";
				} else if(colValue == 1) {
					return "待审核";
				} else if(colValue == 2) {
					return "审核拒绝";
				} else if(colValue == 3) {
					return "办理中";
				} else if(colValue == 4) {
					return "已借出";
				}else if(colValue == 5) {
					return "已归档";
				}else {
					return "未知状态";
				}
            }
		}],
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
		var dels = $('#selectPersonListGrid').omGrid('getSelections',true);
		if (dels.length <= 0) {
			alert('请选择删除的记录！');
			return;
		}
		deletePerson(dels[0].id);
		$('#selectPersonListGrid').omGrid('deleteRow', dels[0]);
	});


	$("#itemSave").click(function() {
		_assignTask();
	});

	function _assignTask() {
		var gridData = $('#selectPersonListGrid').omGrid("getData");
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
		title : '添加出差人员',
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
	
	function deletePerson(personId) {
		var taskId = $("#taskId").val();
		var postData = {
				personId:personId,
				taskId:taskId
		};
		$.ajax({
			url : "person/delete",
			type : 'POST',
			dataType : "json",
			data : postData,
			success : function(data) {
				if (data.success) {
						SYQ.Console.showTip('删除出差人员成功', 'success', 4000,new function(){
						location.reload();
					});
				} else {
					SYQ.Console.showTip('删除出差人员失败', 'error', 5000);
				}
			}
		});
	}
	
	function addPerson() {
		var userName = $("input[name=userName]").val();
		var deptName = $("input[name=deptName]").val();
		var idNumber = $("input[name=idNumber]").val();
		var birthday = $("input[name=birthday]").val();
		var sexey = $("input[name='sexey']:checked").val();
		var placeBirth = $("input[name=placeBirth]").val();
		var business = $("input[name=business]").val();
		var identity = $('#identity option:selected') .val();
		var taskId = $("#taskId").val();
		
		var postData = {
				userName:userName,
				deptName:deptName,
				idNumber:idNumber,
				taskId:taskId,
				sexey:sexey,
				birthday:birthday,
				placeBirth:placeBirth,
				business:business,
				identity:identity,
		};
		$.ajax({
			url : "person/add",
			type : 'POST',
			dataType : "json",
			data : postData,
			success : function(data) {
				if (data.success) {
					SYQ.Console.showTip('添加出差人员成功', 'success', 4000,new function(){
						window.location.href = './passport/todoFaffList?select=2'
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
	$('#selectPersonListGrid').omGrid('insertRow', 0, {
		id : id,userName:userName,deptName:deptName,idNumber:idNumber,mobile:mobile,phone:phone,email:email
	},true);
}