/**
 * 隐藏、显示错误信息
 * @param onOff true 验证成功，隐藏错误 false 验证失败，显示错误
 * @param input 表单域选择器
 * @param errSelect 错误提示选择器
 * @param message 错误信息
 * @returns
 */
function switchVaild(onOff, input, errSelect, message){
	if(onOff == false){
		$(errSelect).text(message);
		$(input).addClass("error_input");
		$(errSelect).addClass("error_message");
	}else{
		$(errSelect).text("");
		$(input).removeClass("error_input");
		$(errSelect).removeClass("error_message");
	}
}
/**
 * 检查是否为空
 * @param input 表单域选择器 
 * @param errSelect 错误提示选择器
 * @param true-校验成功 false-校验失败
 */
function checkEmpty(input, errSelect) {
	var val = $(input).val();
	if($.trim(val)==""){
		switchVaild(false, input, errSelect, "请输入内容");
		return false;
	}else{
		switchVaild(true, input, errSelect);
		return true;
	}
}
/**
 * 类别必选
 * @param input 表单域选择器 
 * @param errSelect 错误提示选择器
 * @param true-校验成功 false-校验失败
 */
function checkCategory(input, errSelect){
	var val = $(input).val();
	if (val == -1) {
		switchVaild(false, input, errSelect, "请选择油画类型");
		return false;
	}else{
		switchVaild(true, input, errSelect);
		return true;
	}
}
/**
 * 价格必须是正整数
 * @param input 表单域选择器 
 * @param errSelect 错误提示选择器
 * @param true-校验成功 false-校验失败
 */
function checkPrice(input, errSelect) {
	var val = $(input).val();
	var regex = /^[1-9][0-9]*$/;
	if (!regex.test(val)) {
		switchVaild(false, input, errSelect, "无效的价格");
		return false;
	}else{
		switchVaild(true, input, errSelect);
		return true;
	}
}
/**
 * 上传文件必须是图片
 * @param input 表单域选择器 
 * @param errSelect 错误提示选择器
 * @param true-校验成功 false-校验失败
 */
function checkFile(input, errSelect){
	if(!checkEmpty(input, errSelect)){
		return false;
	}
	
	var val = $(input).val().toLowerCase();
	
	if (val.length < 4) {
		switchVaild(false, input, errSelect, "请选择有效的图片");
		return false;
	}
	
	var suffix = val.substring(val.length-3);
	if(suffix == "jpg" || suffix == "png" || suffix == "gif"){
		switchVaild(true, input, errSelect);
		return true;
	}else{
		switchVaild(false, input, errSelect, "请选择有效的图片");
		return false;
	}
}