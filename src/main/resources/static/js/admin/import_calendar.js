var myContextPath = global_getContextPath();
$(document).ready(pageInit());

function pageInit() {
    initCalendar();
    caculateTotal();
    bindAdjustBtn();
    bindBackBtn();
    bindDeleteBtn();
}

function bindBackBtn() {
    var date = new Date();

    var dd = date.getDate();
    var mm = date.getMonth()+1; //January is 0!
    var yyyy = date.getFullYear();

    var yyyy_MM_dd = yyyy + "_" + mm + "_" + dd;
    var yyyy_MM    = yyyy + "_" + mm;

    $("#btn_back_month").click(function() {
        window.location.href = myContextPath + PATH_IMPORT_PRODUCT_CALENDAR + "?date=" + yyyy_MM;
    });

    $("#btn_back_day").click(function() {
        window.location.href = myContextPath + PATH_IMPORT_PRODUCT_DAY + "?date=" + yyyy_MM_dd;
    });

}

function bindAdjustBtn() {
    $('button[id^="btn_detail_"]').click(function(event) {
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

        window.location.href = myContextPath + PATH_IMPORT_PRODUCT_DAY + "?date=" + param;
    });
}

function caculateTotal() {
    var ORI_TOTAL_VALUE = 0;
    $('input[id^="hdn_summary_price_"]').each(function(index) {
        ORI_TOTAL_VALUE = ORI_TOTAL_VALUE + parseFloat($(this).val());
    });
    $("#importTotal").text(ORI_TOTAL_VALUE.toFixed(2));
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
        window.location.href = myContextPath + PATH_IMPORT_PRODUCT_CALENDAR + "?date=" + value;
    }
    catch(e) {
        handlerFrontEndException(e);
    }
}

function bindDeleteBtn() {
    $('button[id^="btn_delete_"]').click(function(event) {
        bootbox.confirm("确认删除该日所有的入货记录吗？", function(result){
            if(result) {
                deleteImportProductOverview(event);
            }
        });
    });
}

function deleteImportProductOverview(event) {
    try {
        var sourceId = event.target.id;
        var hiddenID = changeToDestinationID(sourceId, "#hdn_summary_value_");
        var priceID  = changeToDestinationID(sourceId, "#hdn_summary_price_");
        var tdID     = changeToDestinationID(sourceId, "#td_");

        var summaryJson = $(hiddenID).attr("value");
        var summary     = JSON.parse(summaryJson);

        var id = summary.ihsId;
        //Step 1: send AJAX
        $.ajax(
            {
                url: myContextPath + REST_PATH_IMPORT_SUMMARY + "/" + id,
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                type: "DELETE",
                complete: function(xhr, statusText) {
                    if ( xhr.status == HTTP_STATUS_ACCEPTED ) {
                        var tr_id = changeToDestinationID(sourceId, "#tr_top_");
                        $(tr_id).attr("class","active");

                        $(event.target).remove();
                        $(hiddenID).remove();
                        $(priceID).remove();
                        $(tdID).html("0");
                        caculateTotal();
                    }
                },
                error: global_handler_ajax_exception
            }
        );
    } catch (e) {
        handlerFrontEndException(e);
    }
}