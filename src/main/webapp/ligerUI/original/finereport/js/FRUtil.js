 /**
  * 功能：对中日韩文字符进行编码，主要解决帆软报表传中文参数出现乱码的问题
  * @param {} text
  * @return {String}
  */   
function cjkEncode(text) {                                                                             
  if (text == null) {          
    return "";          
  }          
  var newText = "";          
  for (var i = 0; i < text.length; i++) {          
    var code = text.charCodeAt (i);           
    if (code >= 128 || code == 91 || code == 93) { 
      newText += "[" + code.toString(16) + "]";          
    } else {          
      newText += text.charAt(i);          
    }          
  }          
  return newText;          
}  