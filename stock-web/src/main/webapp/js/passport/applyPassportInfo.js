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

$(function(){
	var baseUrl = location.protocol + "//" + location.host + location.pathname.substring(0,location.pathname.indexOf("/",1)) + "/uploadImg/";
	var showIdCardImg = $("#showIdCardImg");
	var showFamilyBookImg = $("#showFamilyBookImg");
	var idCardfileName = showIdCardImg.attr("imgName");
	var familyBookfileName = showFamilyBookImg.attr("imgName");
	
	if (idCardfileName != null && idCardfileName != ''){
		$("<img onclick='_imgOp(this)' height = '200' width ='200' src="+baseUrl+idCardfileName+"></img>").appendTo(showIdCardImg);
	}
	
	if (familyBookfileName != null && familyBookfileName != ''){
	    $("<img onclick='_imgOp(this)' height = '200' width ='200' src="+baseUrl+familyBookfileName+"></img>").appendTo(showFamilyBookImg);
	}
	
	$('#idCardImg').omFileUpload({
        action : '../fileUpload/uploadImg',
        swf : 'js/omui/swf/om-fileupload.swf',
        onComplete : function(ID,fileObj,response,data,event){
        	var fileName = JSON.parse(response).success;
        	$("#form1 input[name=idCardImg]").val(fileName);
        	showIdCardImg.empty()
            $("<img style='height:80px;width:80px' src="+baseUrl+fileName+"></img>").appendTo(showIdCardImg);
        }
    });
	
	$('#familyBookImg').omFileUpload({
        action : '../fileUpload/uploadImg',
        swf : 'js/omui/swf/om-fileupload.swf',
        onComplete : function(ID,fileObj,response,data,event){
        	var fileName = JSON.parse(response).success;
        	$("#form1 input[name=familyBookImg]").val(fileName);
        	showFamilyBookImg.empty()
            $("<img style='height:80px;width:80px' src="+baseUrl+fileName+"></img>").appendTo(showFamilyBookImg);
        }
    });
	
	$("#uploadIdCardImg").click(function(){
		$('#idCardImg').omFileUpload('upload');
		return false;
	});
	
	$("#buttonApplyPassportInfo").click(function() {
		var personId = $("#personId").val();
		var idCardImg = $("#form1 input[name=idCardImg]").val();
		var familyBookImg = $("#form1 input[name=familyBookImg]").val();
		var placeBirth = $("#placeBirth").val();
		var pictureNo = $("#pictureNo").val();
		var postData = {
				personId:personId,
				idCardImg:idCardImg,
				familyBookImg:familyBookImg,
				placeBirth:placeBirth,
				pictureNo:pictureNo
		}
		
		$.ajax({
			url : './passport/applyPassportInfo',
			type : 'POST',
			dataType : "json",
			data : postData,
			success : function(data) {
				if (data.success) {
					SYQ.Console.showTip('提交护照申请资料成功', 'success', 4000,new function(){
						
					});
				} else {
					SYQ.Console.showTip('提交护照申请资料失败', 'error', 5000);
				}
			}
		});
		return false;
	})
})