var jBoxTitle="提示";

$.jBox.defaults = {
//    id: null, /* 在页面中的唯一id，如果为null则自动生成随机id,一个id只会显示一个jBox */
    top: '30%', /* 窗口离顶部的距离,可以是百分比或像素(如 '100px') */
    border: 5, /* 窗口的外边框像素大小,必须是0以上的整数 */
    opacity: 0.2, /* 窗口隔离层的透明度,如果设置为0,则不显示隔离层 */
//    timeout: 0, /* 窗口显示多少毫秒后自动关闭,如果设置为0,则不自动关闭 */
    showType: 'fade', /* 窗口显示的类型,可选值有:show、fade、slide */
    showSpeed: 'fast', /* 窗口显示的速度,可选值有:'slow'、'fast'、表示毫秒的整数 */
//    showIcon: true, /* 是否显示窗口标题的图标，true显示，false不显示，或自定义的CSS样式类名（以为图标为背景） */
//    showClose: true, /* 是否显示窗口右上角的关闭按钮 */
    draggable: true, /* 是否可以拖动窗口 */
    dragLimit: true, /* 在可以拖动窗口的情况下，是否限制在可视范围 */
    dragClone: false, /* 在可以拖动窗口的情况下，鼠标按下时窗口是否克隆窗口 */
    persistent: true, /* 在显示隔离层的情况下，点击隔离层时，是否坚持窗口不关闭 */
    showScrolling: true, /* 是否显示浏览的滚动条 */
    ajaxData: {},  /* 在窗口内容使用get:或post:前缀标识的情况下，ajax post的数据，例如：{ id: 1 } 或 "id=1" */
    iframeScrolling: 'auto', /* 在窗口内容使用iframe:前缀标识的情况下，iframe的scrolling属性值，可选值有：'auto'、'yes'、'no' */

    title: jBoxTitle, /* 窗口的标题 */
    width: 450, /* 窗口的宽度，值为'auto'或表示像素的整数 */
    height: 'auto', /* 窗口的高度，值为'auto'或表示像素的整数 */
    bottomText: '', /* 窗口的按钮左边的内容，当没有按钮时此设置无效 */
    buttons: { '确定': 'ok' }, /* 窗口的按钮 */
    buttonsFocus: 0, /* 表示第几个按钮为默认按钮，索引从0开始 */
    loaded: function (h) { }, /* 窗口加载完成后执行的函数，需要注意的是，如果是ajax或iframe也是要等加载完http请求才算窗口加载完成， 参数h表示窗口内容的jQuery对象 */
    submit: function (v, h, f) { return true; },
    /* 点击窗口按钮后的回调函数，返回true时表示关闭窗口， 参数有三个，v表示所点的按钮的返回值，h表示窗口内容的jQuery对象，f表示窗口内容里的form表单键值 */
    closed: function () { } /* 窗口关闭后执行的函数 */
};

$.jBox.tipDefaults = {
    content: '', /* 提示的内容，不支持前缀标识 */
    icon: 'info', /* 提示的图标，可选值有'info'、'success'、'warning'、'error'、'loading'， 默认值为'info'，当为'loading'时，timeout值会被设置为0，表示不会自动关闭。 */
    top: '40%', /* 提示离顶部的距离,可以是百分比或像素(如 '100px') */
    width: 'auto', /* 提示的高度，值为'auto'或表示像素的整数 */
    height: 'auto', /* 提示的高度，值为'auto'或表示像素的整数 */
    timeout: 600, /* 提示显示多少毫秒后自动关闭,必须是大于0的整数 */
    closed: function () { } /* 提示关闭后执行的函数 */
};



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
	$.jBox.confirm(msg, jBoxTitle, function(v, h, f){
	 	if (v== 'ok'){
	 		fn();
	 	}
	    return true;
	});
}


/**
 * 封装需要回调函数时-警告窗口(无图标)
 * 如果没有回调函数直接使用原生方法: $.jBox.alert("这是警告窗口");
 *
 * 
 * @param msg 提示信息
 * @param fn 回调函数
 * 
 * 例子:
 * 		jBoxConfirm("这是警告窗口",function(){
 * 			//点击确认按钮后的事件
 * 		});
 */
function jBoxAlert(msg,fn){
	$.jBox.alert(msg, jBoxTitle,{submit:function(v, h, f){
		fn();
	}});
}

/**
 * 封装需要回调函数时-提示窗口(有图标)
 * 如果没有回调函数直接使用原生方法: $.jBox.info("提示窗口(有图标)");
 * 
 * @param msg 提示信息
 * @param fn 回调函数
 * 
 * 例子:
 * 		jBoxConfirm("这是含图标的提示窗口",function(){
 * 			//点击确认按钮后的事件
 * 		});
 */
function jBoxInfo(msg,fn){
	$.jBox.info(msg, jBoxTitle,{submit:function(v, h, f){
		fn();
	}});
}

/**
 * 封装需要回调函数时-成功提示窗口
 * 如果没有回调函数直接使用原生方法: $.jBox.success("操作成功了");
 * 
 * @param msg 提示信息
 * @param fn 回调函数
 * 
 * 例子:
 * 		jBoxConfirm("这是成功时的提示窗口",function(){
 * 			//点击确认按钮后的事件
 * 		});
 */
function jBoxSuccess(msg,fn){
	$.jBox.success(msg, jBoxTitle,{submit:function(v, h, f){
		fn();
	}});
}

/**
 * 封装需要回调函数时-错误提示窗口
 * 如果没有回调函数直接使用原生方法: $.jBox.error("操作失败了");
 * 
 * @param msg 提示信息
 * @param fn 回调函数
 * 
 * 例子:
 * 		jBoxConfirm("这是失败时的提示窗口",function(){
 * 			//点击确认按钮后的事件
 * 		});
 */
function jBoxError(msg,fn){
	$.jBox.error(msg, jBoxTitle,{submit:function(v, h, f){
		fn();
	}});
}

/**
 * 封装需要回调函数时-自动会消息的提示框
 * 如果没有回调函数直接使用原生方法: $.jBox.tip("我会自己消失的..", icon);// 提示的图标，可选值有'info'、'success'、'warning'、'error'、'loading'， 默认值为'info'，当为'loading'时，timeout值会被设置为0，表示不会自动关闭。 
 * 
 * @param msg	提示信息
 * @param icon 提示的图标，可选值有'info'、'success'、'warning'、'error'、'loading'， 默认值为'info'，当为'loading'时，timeout值会被设置为0，表示不会自动关闭。
 * @param fn 回调函数
 * @param [timeout] 多久消失单位毫秒,默认800毫秒,可不传些参数
 * 
 * 例子:
 * 	jBoxTip(r.desc,"info",function(){
		 //自己消失后的回调函数
	}); 
 */
function jBoxTip(msg,icon,fn,timeout){
	if(!timeout){
		timeout=800;
	}
	$.jBox.tip(msg, icon,{timeout:timeout,closed:fn});
}


