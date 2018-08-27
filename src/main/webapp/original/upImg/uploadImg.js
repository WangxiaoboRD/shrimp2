var imgSrc = []; //图片路径
var imgFile = []; //文件流
var imgName = []; //图片名字
var imgNum=0;
//选择图片
function imgUpload(obj) {
	var oInput = '#' + obj.inputId;
	var imgBox = '#' + obj.imgBox;
	var btn = '#' + obj.buttonId;
	var size = obj.fileSize;
	var byteSize;
	var fileNum = obj.fileNum;
	if(size)
		byteSize = size*1*1024;
	var form = new FormData();// 可以增加表单数据
	$(oInput).on("change", function() {
		var fileImg = $(oInput)[0];
		var fileList = fileImg.files;
		imgNum = imgNum+fileList.length;
		if(fileNum){
			if(imgNum*1>fileNum*1){
				$.ligerDialog.warn('不允许超过'+fileNum+'张图片');
				imgNum = imgNum-fileList.length;
				return;
			}
		}
		for(var i = 0; i < fileList.length; i++) {
			if(byteSize){
				if((fileList[i].size)*1>byteSize){
					$.ligerDialog.warn('不允许图片大小超过'+size+'K');
					return;
				}
			}
			var imgSrcI = getObjectURL(fileList[i]);
			imgName.push(fileList[i].name);
			imgSrc.push(imgSrcI);
			imgFile.push(fileList[i]);
			
			form.append("files", fileList[i]);// 文件对象
			form.append("fileNames", fileList[i].name);// 文件对象
		}
		addNewContent(imgBox);
	})
	return form;
}
//图片展示
function addNewContent(obj) {
	$(imgBox).html("");
	for(var a = 0; a < imgSrc.length; a++) {
		var oldBox = $(obj).html();
		$(obj).html(oldBox + '<div class="imgContainer"><img title=' + imgName[a] + ' alt=' + imgName[a] + ' src=' + imgSrc[a] + ' onclick="imgDisplay(this)"><p onclick="removeImg(this,' + a + ')" class="imgDelete">删除</p></div>');
	}
}
//删除
function removeImg(obj, index) {
	imgSrc.splice(index, 1);
	imgFile.splice(index, 1);
	imgName.splice(index, 1);
	
	imgNum = imgNum-1;
	
	var boxId = "#" + $(obj).parent('.imgContainer').parent().attr("id");
	addNewContent(boxId);
}
//上传(将文件流数组传到后台)
function submitPicture(url,data) {
	console.log(data);
	alert('请打开控制台查看传递参数！');
	if(url&&data){
		$.ajax({
			type: "post",
			url: url,
			async: true,
			data: data,
			//traditional: true,
			//关闭序列化-
			processData: false,
		    contentType: false,
			success: function(dat) {
	//			console.log(dat);
			}
		});
	}
}
//图片灯箱
function imgDisplay(obj) {
	var src = $(obj).attr("src");
	var imgHtml = '<div style="width: 100%;height: 100vh;overflow: auto;background: rgba(0,0,0,0.5);text-align: center;position: fixed;top: 0;left: 0;z-index: 1000;"><img src=' + src + ' style="margin-top: 100px;width: 70%;margin-bottom: 100px;"/><p style="font-size: 50px;position: fixed;top: 30px;right: 30px;color: white;cursor: pointer;" onclick="closePicture(this)">×</p></div>'
	$('body').append(imgHtml);
}
//关闭
function closePicture(obj) {
	$(obj).parent("div").remove();
}

//图片预览路径
function getObjectURL(file) {
	var url = null;
	if(window.createObjectURL != undefined) { // basic
		url = window.createObjectURL(file);
	} else if(window.URL != undefined) { // mozilla(firefox)
		url = window.URL.createObjectURL(file);
	} else if(window.webkitURL != undefined) { // webkit or chrome
		url = window.webkitURL.createObjectURL(file);
	}
	return url;
}