var giveRightDialogTemp;
$(function() {
	// 获取树权限
	$.ajaxSetup({
		cache : false
	});
	$("#roleTree123").jstree(
			{
				"plugins" : [ "json_data", "contextmenu", "types", "themes",
						"search", "ui", "checkbox" ],
				core : {
					strings : {
						loading : "加载中，请稍后……",
						new_node : "输入名称",
						initially_open : [ "utsnode" ]
					}
				},
				json_data : {
					"ajax" : {
						"url" : "./role/loadRoleTree",
						"data" : function(n) {
							return {
								id : $(n).attr("id") || ""
							};
						}
					}
				},
				search : {
					"case_insensitive" : true,
					"ajax" : {}
				},
				themes : {
					theme : "classic", // 设置theme主题，默认是"default"，可选值：default、apple、classic、default-rtl
					url : false, // 设置theme css文件的路径
					dots : true, // 是否显示虚线点
					icons : true
				// 是否显示节点前的图标

				},
				contextmenu : {
					select_node : true,
					show_at_node : false,
					items : function(nodeitem) {
					}
				}
			})

	giveRoleDialogTemp = $("#giveRole").omDialog({
		title : '角色树',
		height : '390',
		width : '520',
		resizable : false,
		modal : true,
		autoOpen : false,
		buttons : [ {
			text : "确定",
			click : function() {
				giveRoleDialogTemp.omDialog('close');
			}
		}, {
			text : "取消",
			click : function() {
				giveRoleDialogTemp.omDialog('close');
			}
		} ]
	});

})

function addManager() {
	var roles = getRoles();
	var name = $("#manager-info-form input[name=name]").val();
	var contactPhone = $("#manager-info-form input[name=contactPhone]").val();
	var mobile = $("#manager-info-form input[name=mobile]").val();
	var contactEmail = $("#manager-info-form input[name=contactEmail]").val();
	var status = $("#manager-info-form select[name=status]").val();
	var password = $("#manager-info-form input[name=password]").val();
	var data = {
			roles:roles,
			name:name,
			contactPhone:contactPhone,
			mobile:mobile,
			contactEmail:contactEmail,
			password:password,
			status:status
			
	}
	$.ajax({
		url : "./manager/add",
		dataType : 'json',
		data : data,
		type : "POST",
		success : function(data) {
			SYQ.Console.showTip('添加管理员成功', 'success',
					1000,function(){
				window.location.href = "manager/list";
			});
		}
	});
}

function openRole() {
	giveRoleDialogTemp.omDialog('open');
}

function getRoles() {
	try {
		var menu = $.jstree._reference("roleTree123").get_checked(-1, true);
		// 得到节点的id，拼接成字符串
		var ids = "";
		var regStr = /^[0-9]+$/;
		for (i = 0; i < menu.size(); i++) {
			var rightid = menu[i].id;
			if (regStr.test(rightid))
				ids += menu[i].id + ",";
		}
		ids = ids.substring(0, ids.length - 1);
		return ids;
	} catch (e) {
		alert(e);
		return "";
	}
}