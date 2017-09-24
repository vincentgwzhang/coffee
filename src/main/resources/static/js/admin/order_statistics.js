var myContextPath = global_getContextPath();
$(document).ready(pageInit());

function pageInit() {
    initCalendar();
    caculateTotal();
    bindBackBtn();
}

function caculateTotal() {
    var ORI_TOTAL_VALUE = 0;
    $('input[id^="hdn_total_price_"]').each(function(index) {
        ORI_TOTAL_VALUE = ORI_TOTAL_VALUE + parseFloat($(this).val());
    });
    $("#importTotal").text(ORI_TOTAL_VALUE.toFixed(2));
}

function bindBackBtn() {
    var date = new Date();

    var dd = date.getDate();
    var mm = date.getMonth()+1; //January is 0!
    var yyyy = date.getFullYear();

    var yyyy_MM    = yyyy + "_" + mm;

    $("#btn_back_month").click(function() {
        window.location.href = myContextPath + PATH_ORDER_STATISTICS + "?date=" + yyyy_MM;
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
        window.location.href = myContextPath + PATH_ORDER_STATISTICS + "?date=" + value;
    }
    catch(e) {
        handlerFrontEndException(e);
    }
}