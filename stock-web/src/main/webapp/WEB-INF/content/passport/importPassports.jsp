<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
  <base href="<%=basePath %>" />
</head>
<body>

	<input id="file_upload" name="file" type="file" />
	<button value="上传" onclick="$('#file_upload').omFileUpload('upload')">上传</button>
	
	<div id="responseUpload">
	</div>

	<script type="text/javascript">
	var responseUpload = $("#responseUpload");
		$(document).ready(function() {
			responseUpload.omGrid({
				title : '错误记录',
				data : {},
				height:800,
        		limit : 0,
        		dataSource : "./task/testData",
				colModel :[ {
					header : '序号',
					name : 'rowNum',
					width : '80',
					editor : {}
				}, {
					header : '错误信息',
					name : 'errorMsg',
					width : '600',
					editor : {}
				}]
			});
			
			$('#file_upload').omFileUpload({
				action : '../passport/upload',
				swf : 'js/omui/swf/om-fileupload.swf',
				fileExt : '*.xlsx',
				fileDesc : 'xlsx Files',
				onComplete : function(ID,fileObj,response,data,event){
					var countIndex = 0;
		        	var result = JSON.parse(response);
		        	responseUpload.omGrid("refresh");
		        	for(var i = 0;i<result.list.length;i++) {
		        		if(result.list[i].ok) {
		        			countIndex++;
		        		}else{
		        			$('#responseUpload').omGrid('insertRow',i,{"rowNum":result.list[i].rowNum,"errorMsg":result.list[i].errorMsg},true);
		        			
		        		}
		        	}
		        	if(countIndex == result.list.length) {
		        		alert("恭喜你全部导入成功!!!");
		        		window.location.href = "passport/list";
		        	}
		        }
			});
		});
		

	</script>
</body>
</html>