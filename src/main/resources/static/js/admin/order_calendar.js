var myContextPath = global_getContextPath();
$(document).ready(pageInit());

function pageInit() {
    initCalendar();
    bindAdjustBtn();
}

function bindAdjustBtn() {
    $('button[id^="btn_adjust_"]').click(function(event) {
        var sourceId = event.target.id;
        var index = sourceId.lastIndexOf("_");
        var day = sourceId.substring(index+1);

        var param_date = getUrlParam("date");
        var param = "";

        if (!isEmpty(param_date)) {
            param = param_date + "_" + day;
        } else {
            var newDate = new Date();
            param += newDate.getFullYear() + "_";
            param += (newDate.getMonth() + 1) + "_";
            param += day;
        }

        window.location.href = myContextPath + PATH_ORDER_ADJUSTMENT_SPECIFIC + "?date=" + param;
    });
}

function initCalendar() {
    $('#slt_calendar').datepicker({
        startView: 1,
        minViewMode: 1,
        maxViewMode: 2,
        autoclose: true
    }).on("changeMonth", onChangeMonth);

    var param_date = getUrlParam("date");
    if (!isEmpty(param_date)) {
        var dates = param_date.split("_");
        var setDate = new Date(dates[0], dates[1] - 1, 1); // in JS, month start from 0
        $('#slt_calendar').datepicker('setDate', setDate);
    }
}

function onChangeMonth(e) {
    try{
        var month = e.date.getMonth() + 1; // in JS, month start from 0
        var year  = e.date.getFullYear();
        var value = year + "_" + month;
        window.location.href = myContextPath + PATH_ORDER_ADJUSTMENT_CALENDAR + "?date=" + value;
    }
    catch(e) {
        handlerFrontEndException(e);
    }
}