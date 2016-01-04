

function localSet(closeButton){
	var defaults = {
	    // default language
	    locale: "zh_CN",
	    // show backdrop or not
	    backdrop: true,
	    // animate the modal in/out
	    animate: true,
	    // additional class string applied to the top level dialog
	    className: null,
	    // whether or not to include a close button
	    closeButton: closeButton,
	    // show the dialog immediately by default
	    show: true,
	    // dialog container
	    container: "body"
  	};
  	return defaults;
}

function jBoxAlert(msg,fn){
	bootbox.setDefaults(localSet(true));
	bootbox.alert(msg,fn);
}
/**
 * 封装需要回调函数时-询问窗口
 * 
 * @param msg 提示信息
 * @param fn 回调函数
 * 
 * 例子:
 * 		jBoxConfirm("确认要删除吗?",function(){
 * 			//点击确认按钮后的事件
 * 		});
 */
function jBoxConfirm(msg,fn){
	bootbox.setDefaults(localSet(true));
	bootbox.confirm(msg, function(result) {
		if(result && typeof fn === "function"){
			fn();
		}
    }); 
}
/**
 * 封装需要回调函数时-自动会消息的提示框
 * 如果没有回调函数直接使用原生方法: $.jBox.tip("我会自己消失的..", icon);// 提示的图标，可选值有'info'、'success'、'warning'、'error'、'loading'， 默认值为'info'，当为'loading'时，timeout值会被设置为0，表示不会自动关闭。 
 * 
 * @param msg	提示信息
 * @param icon 提示的图标，可选值有'info'、'success'、'warning'、'danger'、'loading'， 默认值为'info'，当为'loading'时，timeout值会被设置为0，表示不会自动关闭。
 * @param fn 回调函数
 * @param [timeout] 多久消失单位毫秒,默认800毫秒,可不传些参数
 * 
 * 例子:
 * 	jBoxTip(r.desc,"info",function(){
		 //自己消失后的回调函数
	}); 
*/
function jBoxTip(msg,icon,fn,timeout){
	bootbox.setDefaults(localSet(false));
	bootbox.dialog({
        message: msg,
        title: null,
        buttons: {},
        size:'small',
        icon:icon
    });
    if (typeof timeout != "number"){
    	timeout=1000;
    }
    
    window.setTimeout(function(){
    	if(typeof icon != "string" || icon!='loading'){
    		bootbox.hideAll();
    		if(typeof fn === "function"){
				fn();
			}
    	}
    	
    	
	}, timeout);
    
}
//改写alert
// window.alert = function(msg)
// {	
	// if (typeof msg === "string"){
		// jBoxAlert(msg);
	// }else{
		// jBoxAlert(JSON.JsonToStr(msg));
	// }
	
// }
//改写confirm
window.confirm = function(msg,okCallBack,cancelCallBack)
{	
	bootbox.setDefaults(localSet(true));
	bootbox.confirm(msg, function(result) {
		if(result && typeof okCallBack === "function"){
			okCallBack();
		}
		if(!result && typeof cancelCallBack === "function"){
			cancelCallBack();
		}
    });
	return false;
}

function jBox(msg,title,buttons,isNeedCloseButton){
	bootbox.setDefaults(localSet(isNeedCloseButton));
	bootbox.dialog({
        message: msg,
        title: title,
        buttons: buttons,
        size:''
    });
    
}