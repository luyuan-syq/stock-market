function editSecurityRole() {
	var rights = getRightIds();
	var name = $("#role-info-form input[name=name]").val();
	var description = $("#role-info-form input[name=description]").val();
	var id = $("#role-info-form input[name=id]").val();
	var data = {
			id : id,
			rights:rights,
			name:name,
			description:description
			
	}
	$.ajax({
		url : "./role/edit",
		dataType : 'json',
		data : data,
		type : "POST",
		success : function(data) {
			SYQ.Console.showTip('修改外事账号成功', 'success',
					1000,function(){
				window.location.href = "role/list";
			});
		}
	});
}

function getRightIds() {
	try {
		var menu = $.jstree._reference("rightTree123").get_checked(-1, true);
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