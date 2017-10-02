function global_getContextPath(){
    //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
    var curWwwPath=window.document.location.href;
    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8083
    var localhostPaht=curWwwPath.substring(0,pos);
    //获取带"/"的项目名，如：/uimcardprj
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    return projectName;
}

function isEmpty(value) {
    return typeof value == 'string' && !value.trim() || typeof value == 'undefined' || value === null;
}

function changeToDestinationID(oriID, prefix) {
    return prefix + getIdentity(oriID);
}

function getIdentity(oriID) {
    var pos = oriID.lastIndexOf("_");
    var id = oriID.substring(pos+1);
    return id;
}

function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}

/**
 * xhr: {"readyState":4,"responseText":"","status":401,"statusText":"Unauthorized"}
 */
function global_handler_ajax_exception(xhr, status, error) {
    bootbox.setLocale($("#hid_txt_language").val());
    if(xhr.status == HTTP_STATUS_SHUTDOWN) {
        bootbox.confirm($("#hid_txt_err_shutdown").val(), function(result){
            window.location.href = global_getContextPath();
        });
    }

    if(xhr.status == HTTP_STATUS_UNAUTHORIZED) {
        window.location.href = global_getContextPath();
    }

    if(xhr.status != HTTP_STATUS_ACCEPTED || xhr.status != HTTP_STATUS_NO_CONTENT) {
        bootbox.alert({
            title: "操作出错",
            message: parseErrorMessage(xhr.responseText),
            className: 'bb-alternate-modal',
            size: 'large'
        });
    }
}

function handlerBusinessException(errMessage) {
    bootbox.alert({
        title: "操作出错",
        message: errMessage,
        className: 'bb-alternate-modal',
        size: 'large'
    });
}

function handlerBusinessException2(errMessage) {
    bootbox.alert({
        title: "",
        message: errMessage,
        className: 'bb-alternate-modal',
        size: 'large'
    });
}

function parseErrorMessage(errorMessage) {
    var message = JSON.parse(errorMessage);
    var errorCode = "";
    var errorDetail = "";
    try{
        errorCode   = message.httpCode;
        errorDetail = message.message;
    } catch(e){}

    return "出错代码：" + errorCode + "<br />" + "出错信息：" + errorDetail;
}

function handlerFrontEndException(err) {
    console.error("ErrorName:" + err.name);
    console.error("ErrorMessage:" + err.message);
}

function setDefaultIfEmpty(component, value) {
    if( !$(component).val() ) {
        $(component).val(value);
    }
}

function verifyFieldNotEmpty(field, errMessage) {
    if(!$(field).val()) {
        validationMessages.push(errMessage);
    }
}

var REGEX_FLOAT_2_DIGIT = /(^[1-9]\d*(\.\d{1,2})?$)|(^0(\.\d{1,2})?$)/;
var REGEX_POSITIVE_INT  = /^\d+$/;

function verifyFieldValidDigitFormat(field, regex, errMessage) {
    numStr = $(field).val();
    if (!regex.test(numStr)) {
        validationMessages.push(errMessage);
    }
}

function ShowMsg(msg) {
    showTip(msg, 'info');
}

function showTip(tip, type) {
    var $tip = $('#tip');
    $tip.stop(true).prop('class', 'alert alert-' + type).text(tip).css('margin-left', - $tip.outerWidth() / 2).fadeIn(500).delay(500).fadeOut(500);
}

function showTip2(tipID) {
    $(tipID).fadeTo(2000, 500).slideUp(500, function(){
        $(tipID).slideUp(500);
    });
}

function showErrorMessageWindow(errorMessages) {
    if(errorMessages.length == 0) {
        return;
    }

    var message = "";

    for (var i=0; i < errorMessages.length; i++) {
        message = message + errorMessages[i] + "<br />";
    }

    handlerBusinessException(message);
}

function restrictNumberAndDotOnly(event) {
    var event= event || window.event;
    var getValue = $(this).val();

    if (event.which && event.which == 13) {
        $(this).trigger("blur");
        return;
    }

    if (getValue.length == 0 && event.which == 46) {//控制第一个不能输入小数点"."
        handlerBusinessException("第一位不能是小数点");
        event.preventDefault();
        return;
    }

    if (getValue.indexOf('.') != -1 && event.which == 46) {//控制只能输入一个小数点"."
        handlerBusinessException("只能有一个小数点");
        event.preventDefault();
        return;
    }

    if (event.which && (event.which < 48 || event.which > 57) && event.which != 8 && event.which != 46) {
        handlerBusinessException("只允许输入数字和小数点");
        event.preventDefault();
        return;
    }
}

function restrictNumberOnly(event) {
    var event= event || window.event;
    var getValue = $(this).val();

    if (event.which && event.which == 13) {
        $(this).trigger("blur");
        return;
    }

    if (event.which && (event.which < 48 || event.which > 57)) {
        handlerBusinessException("只允许输入数字,而且不能有小数点");
        event.preventDefault();
        return;
    }
}

function pasteNumberOnly(event) {
    var clipboardData = window.clipboardData || event.originalEvent.clipboardData;
    var value = clipboardData.getData("Text");
    var regex = /^[0-9\b]+$/;
    if (!regex.test(value)) {
        handlerBusinessException("只允许粘贴数字,而且不能有小数点");
        event.preventDefault();
        return;
    }
}

function pasteNumberAndDotOnly(event) {
    var clipboardData = window.clipboardData || event.originalEvent.clipboardData;
    var value = clipboardData.getData("Text");
    var regex = /^[0-9.\b]+$/;
    if (!regex.test(value)) {
        handlerBusinessException("只允许粘贴数字和小数点");
        event.preventDefault();
        return;
    }
}

function redirectWithLanguage(lang) {
    var appendStr = "lang=" + lang;

    //如果有 #就先删除 #
    var current_link = window.location.href;

    var pos = current_link.indexOf("lang");
    if(pos != -1) {
        current_link = current_link.substring(0, pos);
        current_link = current_link + appendStr;
    } else {
        pos = current_link.indexOf("?");
        if( pos != -1 ) {
            current_link = current_link + "&" + appendStr;
        } else {
            current_link = current_link + "?" + appendStr;
        }
    }
    window.location.href = current_link;
}

function emptySelection() {
    if(document.selection && document.selection.empty) {
        document.selection.empty();
    } else if(window.getSelection) {
        var sel = window.getSelection();
        sel.removeAllRanges();
    }
}

var REST_PATH_DESKTOP  = "/desktop";
var REST_PATH_ORDER  = "/order";
var REST_PATH_ORDER_ITEM  = "/orderitem";
var REST_PATH_DESKTOP2 = "/desktop/nontakeaway";
var REST_PATH_MENU_CATEGORY = "/menuCategory";
var REST_PATH_MENU_ITEM = "/menuItem";
var REST_PATH_IMPORT_HISTORY = "/import/history";
var REST_PATH_ORDER_ADJUSTMENT = "/orderAdjustment";
var REST_PATH_IMPORT_PRODUCT = "/import/product";
var REST_PATH_IMPORT_SUMMARY = REST_PATH_IMPORT_HISTORY + "/summary";
var REST_PATH_DESKTOP_CHANGE  = REST_PATH_ORDER + "/oldDesktopNumber/newDesktopNumber";
var REST_PATH_ORDER_MARK_LOST  = REST_PATH_ORDER + "/lost/desktopNumber";
var REST_PATH_ORDER_MARK_CLEAR  = REST_PATH_ORDER + "/clear/desktopNumber";
var REST_PATH_ORDER_MARK_CLOSE  = REST_PATH_ORDER + "/close/desktopNumber/actualPaid/p_actualPaid";
var REST_PATH_ORDER_MARK_CLOSE_TAKE_AWAY  = REST_PATH_ORDER + "/close/takeaway";
var REST_PATH_AVAIL_DESKTOP   = REST_PATH_DESKTOP + "/availDesks";
var REST_PATH_ORDER_NEW_ITEM  = REST_PATH_ORDER_ITEM + "/desktopNumber/menuitemID/count";
var REST_PATH_ORDER_TAKE_AWAY = REST_PATH_ORDER_ITEM + "/takeaway/menuitemID/count";

//admin
var PATH_MENU_ITEM = "/admin/menu/item";
var PATH_MENU_CATEGORY = "/admin/menu/category";
var PATH_ORDER_ADJUSTMENT = "/admin/order";
var PATH_PRODUCT_CONFIG = "/admin/importproduct/config";

var PATH_ORDER_ADJUSTMENT_CALENDAR = PATH_ORDER_ADJUSTMENT + "/calendar";
var PATH_ORDER_ADJUSTMENT_SPECIFIC = PATH_ORDER_ADJUSTMENT + "/adjustment";

var PATH_IMPORT_PRODUCT = "/admin/importproduct";
var PATH_IMPORT_PRODUCT_CALENDAR = PATH_IMPORT_PRODUCT + "/calendar";
var PATH_IMPORT_PRODUCT_DAY = PATH_IMPORT_PRODUCT + "/day";
var PATH_IMPORT_PRODUCT_TRACE = PATH_IMPORT_PRODUCT + "/trace";
var PATH_ORDER_STATISTICS = "/admin/statistics/orders";


//Operator
var PATH_OPEN_DESK = "/operator/openDesk";
var PATH_INTO_DESK = "/operator/intoDesk";

//Counter
var PATH_CLOSE_DESK = "/counter/closeDesk";
var PATH_COUNTER_DEFAULT = "/counter";

var HTTP_STATUS_ACCEPTED=202;
var HTTP_STATUS_NO_CONTENT=202;
var HTTP_STATUS_UNAUTHORIZED=401;
var HTTP_STATUS_SHUTDOWN=0;