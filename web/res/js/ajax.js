jQuery.extend({
    isProperty: function (obj, type) {
        if ((obj == "undefined") || (obj == null) || (typeof(obj) != type)) return false;
        return true
    },
    mask:function(){
        var MaskID = "Mask";
        var MaskWidth = document.body.scrollWidth;
        var MaskHeight = document.body.scrollHeight;
        var Mask = document.createElement("div");
        Mask.id = MaskID;
        Mask.style.position = "absolute";
        Mask.style.zIndex = "10";
        Mask.style.width = MaskWidth + "px";
        Mask.style.height = MaskHeight + "px";
        Mask.style.top = "0px";
        Mask.style.left = "0px";
        Mask.style.background = "#000000";
        Mask.style.filter = "alpha(opacity=40)";
        Mask.style.opacity = "0.40";
        document.body.appendChild(Mask);
    },
    unmask:function(){
        $("#Mask").remove();
    },
    alert: function (title, html, handler) {
        if ($.isProperty(html, "string") == false) html = '';
        if ($.isProperty(title, "string") == false) title = '';
        var dialogID = "TMTipDialogTmk" + parseInt(Math.random() * 10000);
        var maskId = "dialogMaskTmk" + parseInt(Math.random() * 10000);
        var dialogObj = $("<div id='" + dialogID + "' class='DiaWin'> \
        <div class='DWTil Acen'> \
        <a class='BtnCls Fr Close' href='javascript:void(0);'></a>"+title+"\
        </div>\
        "+html+"\
        </div>");
        var maskObj=$("<div id='"+maskId+"' style=\"position:absolute; left:0; top:0; background:#000; -ms-filter:'progid:DXImageTransform.Microsoft.Alpha(Opacity=20)'; filter:alpha(opacity=20); opacity:0.2\"><!--[if IE 6]><iframe class='WiF' src='####' scrolling='no' frameborder='0'></iframe><![endif]--></div>");
        dialogObj.appendTo($("body"));
        maskObj.appendTo($("body"));
        Dialog(maskId,dialogID);
        dialogObj.find(".Close").click(function(){
            if(handler && jQuery.isFunction(handler)) handler();
            try{Dialog.Close();}catch(e){}
            dialogObj.remove();
            maskObj.remove();
            dialogObj = null;
            maskObj = null;
        });
        return dialogObj;

    },
    verifyTip:function(obj){
        if($.isProperty(obj,"object")==false) return;
        var fieldError=obj.fieldErrors;
        var rtnStr="";
        for(x in fieldError){
            var ary=fieldError[x];
            $(ary).each(function(idx,ele){
                rtnStr+=ele;
            });
        }
        return rtnStr;
    },
    ajaxextend: function (s) {
        if (jQuery.isFunction(s.init)) s.init();
        if ($.isProperty(s.url, "string") == false) return;
        if ($.isProperty(s.data, "object") == false) return;
        var data = {};
        for (x in s.data)
            data[x] = s.data[x];

        var msgBox = null;
        $.ajax(s.url, {
            data: data,
            type: "POST",
            timeout: 60000,
            datatype: "json",
            statusCode: {
                1: function (data) {
                    var response = data.responseText;
                    response = eval("(" + response + ")");
                    if (jQuery.isFunction(s.verifyError)) s.verifyError(response);
                },
                2: function (data) {
                    var response = data.responseText;
                    response = eval("(" + response + ")");
                    if (jQuery.isFunction(s.returnError)) s.returnError(response);
                },
                3: function (data) {
                    var response = data.responseText;
                    response = eval("(" + response + ")");
                    if (jQuery.isFunction(s.returnSuccess)) s.returnSuccess(response);
                },
                4: function (data) {
                    var response = data.responseText;
                    response = eval("(" + response + ")");
                    if (jQuery.isFunction(s.fileTypeError)) s.fileTypeError(response);
                }
            },
            success:function(data, textStatus,jqXHR){
                if(data && data.indexOf("<script>")!=-1){
                    data = data.replace("<script>","");
                    data = data.replace("</script>","");
                    eval("((function(){"+data+"}))")();
                }
            }
        });
    }
});