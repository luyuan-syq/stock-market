var selectPersonListGrid = null;
$(function() {
	var assignUrl = "task/assign";
	var taskId = $("#taskId").val();
	selectPersonListGrid = $('#selectVisaPersonListGrid').omGrid({
		limit : 0,
		title : '出差人员',
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
			width : 100,
			editor : {}
		},{
			header : '操作',
			name : 'operations',
			align : 'center',
			width : 200,
			renderer : function(colValue, rowData, rowIndex) {
				
				var menu = "<div id='op-menu-" + rowIndex
						+ ">";
				menu += "<div class='luru' id='luru-passport' name='"
						+ rowData.id + "'><a href='visa/applyVisaInfo?personId="+rowData.id+"'>录入签证资料</a></div>";
				menu += "</div>";
				return menu;
			}
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



	$("#itemSave").click(function() {
		_assignTask();
	});

	function _assignTask() {
		var gridData = $('#selectVisaPersonListGrid').omGrid("getData");
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
})
	
	