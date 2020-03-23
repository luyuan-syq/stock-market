
function _imgOp(img) {
    //图片点击事件
    var imgH = img.height; //获取图片的高度
    var imgW = img.width; //获取图片的宽度
    if(imgH < 500){
        img.height = imgH*3;
        img.width = imgW*3;
    }else{
        img.height = imgH/3;
        img.width = imgW/3;
    }
	return false;
};

function download(src) {
    var $a = $("<a></a>").attr("href", src).attr("download", "img.png");
    $a[0].click();
}

$(function(){
	//tab
	var baseUrl = location.protocol + "//" + location.host + location.pathname.substring(0,location.pathname.indexOf("/",1)) + "/uploadImg/";
	var showIdCardImg = $("#showIdCardImg");
	var showFamilyBookImg = $("#showFamilyBookImg");
	var auditDialog = $("#edit-task-dialog");
	var idCardfileName = showIdCardImg.attr("imgName");
	var familyBookfileName = showFamilyBookImg.attr("imgName");
	// 审核
	if ($("#dealFlag").val() == 1){
		auditDialog.show();
	}
		 
	$("<img onclick='_imgOp(this)' height = '200' width ='200' src="+baseUrl+idCardfileName+"></img>").appendTo(showIdCardImg);
	$("<img onclick='_imgOp(this)' height = '200' width ='200' src="+baseUrl+familyBookfileName+"></img>").appendTo(showFamilyBookImg);
	
	$("#idCardDownLoad").click(function() {
		download(baseUrl+idCardfileName);
        return false;
	});
	
	$("#familyBookDownLoad").click(function() {
		download(baseUrl+familyBookfileName);
        return false;
	});
		
	$("#buttonPass").click(function() {
		var personId = $("#personId").val();
		var rejectMsg = $("#form1 input[name=rejectMsg]").val();
		var auditStatus = 0;
		var postData = {
				personId:personId,
				auditMsg:rejectMsg,
				auditStatus:auditStatus
		}
		
		$.ajax({
			url : './passport/auditPassportInfo',
			type : 'POST',
			dataType : "json",
			data : postData,
			success : function(data) {
				if (data.success) {
					SYQ.Console.showTip('审核护照申请资料成功', 'success', 4000,new function(){
						window.location.href = "./passport/todoList?select=2";
					});
				} else {
					SYQ.Console.showTip('审核护照申请资料失败', 'error', 5000);
				}
			}
		});
		return false;
	});
	
	$("#buttonReject").click(function() {
		var personId = $("#personId").val();
		var rejectMsg = $("#form1 input[name=rejectMsg]").val();
		var auditStatus = 1;
		var postData = {
				personId:personId,
				auditMsg:rejectMsg,
				auditStatus:auditStatus
		}
		
		$.ajax({
			url : './passport/auditPassportInfo',
			type : 'POST',
			dataType : "json",
			data : postData,
			success : function(data) {
				if (data.success) {
					
					
					SYQ.Console.showTip('操作成功', 'success', 4000,new function(){
						window.location.href = "./passport/todoList?select=2";
					});
					
				} else {
					SYQ.Console.showTip('操作失败', 'error', 5000);
				}
			}
		});
		return false;
	})
})