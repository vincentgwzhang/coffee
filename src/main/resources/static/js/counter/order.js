var myContextPath = global_getContextPath();
$(document).ready(pageInit());

function pageInit() {
    hideTip();
    initExistOrder();
    reCalculatePrice();
    markLostButton();
    markCloseButton();
    bindReturnBtn();
}

function bindReturnBtn() {
    $("#btn_return").click(function () {
        window.location.href = myContextPath + PATH_COUNTER_DEFAULT;
    });
}

function markCloseButton() {
    $("#btn_close_order").click(function () {
        var orderTotal = parseFloat($("#span_order_total").text());
        var deskNumber = $("#span_desk_num").text();
        var money_format = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
        bootbox.setLocale($("#hid_txt_language").val());
        bootbox.prompt($("#hid_txt_order_request").val(), function(result){

            if(result && money_format.test(result)) {
                var acutalPaid = parseFloat(result);
                var lastMark = acutalPaid < orderTotal ? acutalPaid : orderTotal;
                var returnTotal = acutalPaid - orderTotal;
                //clear order
                var requestURL = REST_PATH_ORDER_MARK_CLEAR.replace(/desktopNumber/g, deskNumber);
                $.ajax(
                    {
                        url: myContextPath + requestURL,
                        contentType: "application/json; charset=utf-8",
                        dataType: "json",
                        type: "PUT",
                        complete: function(xhr, statusText) {
                            requestURL = REST_PATH_ORDER_MARK_CLOSE.replace(/desktopNumber/g, deskNumber).replace(/p_actualPaid/g, lastMark);
                            $.ajax(
                                {
                                    url: myContextPath + requestURL,
                                    contentType: "application/json; charset=utf-8",
                                    dataType: "json",
                                    type: "PUT",
                                    complete: function(xhr, statusText) {
                                        if(returnTotal>0) {
                                            bootbox.alert($("#hid_txt_order_return").val() + " : " + returnTotal.toFixed(2))
                                        }
                                        //TODO: make the button as return
                                        $("#btn_return").removeClass("btn_disppear");
                                        $("#btn_close_order").addClass("btn_disppear");
                                        $("#btn_lost_order").addClass("btn_disppear");
                                    },
                                    error: global_handler_ajax_exception
                                }
                            );
                        },
                        error: global_handler_ajax_exception
                    }
                );
            } else if(result) {
                bootbox.alert($("#hid_txt_order_format").val())
            }
        })
    });
}

function markLostButton() {
    $("#btn_lost_order").click(function() {
        var deskNumber = $("#span_desk_num").text();
        var requestURL = REST_PATH_ORDER_MARK_LOST.replace(/desktopNumber/g, deskNumber);
        $.ajax(
            {
                url: myContextPath + requestURL,
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                type: "PUT",
                complete: function(xhr, statusText) {
                    if ( xhr.status == HTTP_STATUS_ACCEPTED ) {
                        window.location.href = myContextPath + PATH_COUNTER_DEFAULT;
                    }
                },
                error: global_handler_ajax_exception
            }
        );
    });
}

function initExistOrder() {
    //for the first column, need to init count
    var existCount = $("#customer_selection_area > tbody > tr").length;
    $("#hdn_customer_count").val(existCount);

    //for the second column, need to add remove listener
    $("#customer_selection_area > tbody > tr").each(function() {
        $(this).find('td[id^="td_remove_"]').dblclick(disabledRemove);
    });
}

function reCalculatePrice() {
    var orderPrice = 0;
    $("#customer_selection_area > tbody > tr").each(function() {
        orderPrice = orderPrice + parseFloat($(this).find('span[id^="span_subtotal_"]').text());
    });
    $("#span_order_total").text(orderPrice.toFixed(2));
}

function disabledRemove(e) {
    var deletetext = $("#hid_txt_delete").val();
    handlerBusinessException2(deletetext);
}

function hideTip() {
    $("#success-alert").hide();
}

function showTip() {
    $("#success-alert").fadeTo(2000, 500).slideUp(500, function(){
        $("#success-alert").slideUp(500);
    });
}