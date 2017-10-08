var myContextPath = global_getContextPath();
$(document).ready(pageInit());

function pageInit() {
    hideTip();
    initMenuCategoryTab();
    initTRMenuItem();
    reCalculatePrice();
    markCloseButton();
    bindRefreshBtn();
}

function bindRefreshBtn() {
    $("#btn_refresh").click(function () {
        location.reload();
    });
}

function markCloseButton() {
    $("#btn_close_take_away").click(function () {
        var orderTotal = parseFloat($("#span_order_total").text());
        var money_format = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
        bootbox.setLocale($("#hid_txt_language").val());
        bootbox.prompt($("#hid_txt_order_request").val(), function(result){

            if(result && money_format.test(result)) {
                var acutalPaid = parseFloat(result);
                var lastMark = acutalPaid < orderTotal ? acutalPaid : orderTotal;
                var returnTotal = acutalPaid - orderTotal;

                var requestURL = REST_PATH_ORDER_MARK_CLOSE_TAKE_AWAY;

                var items = [];
                $("#customer_selection_area > tbody > tr").each(function () {
                    var item_count_id = $(this).find('select[id^="select_count_"]').prop("id");
                    var item_count = $(this).find('select[id^="select_count_"]').val();
                    var item_id    = getIdentity(item_count_id);

                    var itemDTO = new Object();
                    itemDTO.menuItemId = item_id;
                    itemDTO.count      = item_count;
                    items.push(itemDTO);
                });

                var takeAwayDTO = new Object();
                takeAwayDTO.orderItemDTOList = items;
                takeAwayDTO.actualPrice     = orderTotal.toFixed(2);
                takeAwayDTO.customerPay     = acutalPaid.toFixed(2);
                takeAwayDTO.customerReceive = returnTotal.toFixed(2);

                $.ajax(
                    {
                        url: myContextPath + requestURL,
                        contentType: "application/json; charset=utf-8",
                        dataType: "json",
                        type: "POST",
                        data:JSON.stringify(takeAwayDTO),
                        complete: function(xhr, statusText) {
                            if(returnTotal>0) {
                                bootbox.alert($("#hid_txt_order_return").val() + " : " + returnTotal.toFixed(2))
                            }
                            $("#btn_refresh").removeClass("btn_disppear");
                            $("#btn_close_take_away").addClass("btn_disppear");
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

function reCalculatePrice() {
    var orderPrice = 0;
    $("#customer_selection_area > tbody > tr").each(function() {
        orderPrice = orderPrice + parseFloat($(this).find('span[id^="span_subtotal_"]').text());
    });
    $("#span_order_total").text(orderPrice.toFixed(2));
}

function changeMenuItemCount(e) {
    var select_id = e.target.id;
    var miId = getIdentity(select_id);
    var td_price_id = changeToDestinationID(select_id, "#td_menu_item_price_");
    var price = parseFloat($(td_price_id).html());
    var count = parseFloat($(this).prop("value"));
    var total = price * count;

    $(this).parent().parent().find("#span_subtotal_" + miId).text(total.toFixed(2));
    reCalculatePrice();
}

function createCustomerSelectLine(price, itemText,miId) {
    //this is for number
    var order = parseInt($("#hdn_customer_count").val());
    order = order + 1;
    $("#hdn_customer_count").val(order);
    var $createdTD1 = $("<td>", {"style": "text-align: center;", "html":order});

    //this is for the item text
    var $createdTD2 = $("<td>", {"style": "text-align: left;", "html":itemText, "id":"td_remove_" + order});
    $createdTD2.dblclick(row_delete_triggered);

    //this is for the count
    var $createdTD3 = $("<td>", {"style": "text-align: left;"});
    var $sel = $('<select>', {"id": "select_count_" + miId}).appendTo($createdTD3);
    var arr = [
        {val : 1, text: '1'},
        {val : 2, text: '2'},
        {val : 3, text: '3'},
        {val : 4, text: '4'},
        {val : 5, text: '5'},
        {val : 6, text: '6'},
        {val : 7, text: '7'},
        {val : 8, text: '8'},
        {val : 9, text: '9'}
    ];
    $(arr).each(function() {
        $sel.append($("<option>").attr('value',this.val).text(this.text));
    });
    $sel.css("width", "50px");
    $sel.change(changeMenuItemCount);

    //this is for the send button
    var sendtext = $("#hid_txt_send").val();
    var $createdTD4 = $("<td style='text-align: center'>");
    var $btn = $('<input type="button" value="' + sendtext + '" id="btn_send_' + miId + '"/>').appendTo($createdTD4);
    $btn.click(btn_send_triggered);

    //this is for the sub item total price
    var $createdTD5 = $("<td style='text-align: left'>");
    var $span = $('<span id="span_subtotal_' + miId + '"/>').appendTo($createdTD5);

    var td_price_id = "#td_menu_item_price_" + miId;
    var menu_item_unit_price = parseFloat($(td_price_id).html());
    $span.text(menu_item_unit_price.toFixed(2));


    var $createdTR = $("<tr>");

    $createdTR.append($createdTD1);
    $createdTR.append($createdTD2);
    $createdTR.append($createdTD3);
    $createdTR.append($createdTD5);
    $createdTR.append($createdTD4);

    $("#customer_selection_area > tbody:last-child").append($createdTR);
}

function row_delete_triggered(e) {
    var sourceID = e.target.id;
    var confirmRemove = $("#hid_txt_confirm_remove").val();
    bootbox.setLocale($("#hid_txt_language").val());
    bootbox.confirm(confirmRemove, function(result){
        if(result) {
            $("#" + sourceID).parent().remove();
            reCalculatePrice();
        }
    });
}

function disabledRemove(e) {
    var deletetext = $("#hid_txt_delete").val();
    handlerBusinessException2(deletetext);
}

function btn_send_triggered(e) {
    var id = e.target.id;
    var miId = getIdentity(id);
    var menuitemID = miId;
    var count = $(this).parent().parent().find("select").prop("value");

    var requestURL = REST_PATH_ORDER_TAKE_AWAY.replace(/menuitemID/g, menuitemID).replace(/count/g, count);

    $.ajax(
        {
            url: myContextPath + requestURL,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            type: "POST",
            complete: function(xhr, statusText) {
                if ( xhr.status == HTTP_STATUS_ACCEPTED ) {
                    try{
                        $("#" + id).parent().parent().find("select").attr('disabled','disabled');
                        $("#" + id).parent().parent().find('td[id^="td_remove_"]').off("dblclick");
                        $("#" + id).parent().parent().find('td[id^="td_remove_"]').dblclick(disabledRemove);
                        $("#" + id).remove();
                    }
                    catch(e){
                        handlerFrontEndException(e);
                    }
                }
            },
            error: global_handler_ajax_exception
        }
    );
}

function initTRMenuItem() {
    $('tr[id^="tr_menu_item_"]').dblclick(function(e) {
        emptySelection();

        var trID = $(this).prop("id");
        var miId = getIdentity(trID);
        var priceId = changeToDestinationID(trID, "#td_menu_item_price_");
        var textId  = changeToDestinationID(trID, "#td_menu_item_text_");

        var price = $(priceId).html();
        var itemText = $(textId).html();

        createCustomerSelectLine(price, itemText, miId);
        reCalculatePrice();
    });
}

function initMenuCategoryTab() {
    $('div[id^="menu_cate_unit_"]').mouseover(function() {
        if($("#clickedTab").val() == $(this).prop("id")) return;
        $(this).css("background-color", "#339999");
    });

    $('div[id^="menu_cate_unit_"]').mouseleave(function() {
        if($("#clickedTab").val() == $(this).prop("id")) return;
        $(this).css("background-color", "#99ccff");
    });

    $('div[id^="menu_cate_unit_"]').click(function() {
        $('div[id^="menu_cate_unit_"]').css("background-color", "#99ccff");
        $('div[id^="menu_cate_unit_"]').css("color", "#000000");

        $(this).css("background-color", "#339999");
        $(this).css("color", "#fff");
        $("#clickedTab").val($(this).prop("id"));

        $('div[id^="menu_item_list_"]').removeClass();
        $('div[id^="menu_item_list_"]').addClass("menu_items_none");

        var i_menu_id = changeToDestinationID($(this).prop("id"), "#menu_item_list_");
        $(i_menu_id).removeClass();
        $(i_menu_id).addClass("menu_items_block");
    });

    $('div[id^="menu_cate_unit_"]:first').trigger("click");
}

function hideTip() {
    $("#success-alert").hide();
}

function showTip() {
    $("#success-alert").fadeTo(2000, 500).slideUp(500, function(){
        $("#success-alert").slideUp(500);
    });
}

var selectText = $("#hid_txt_select").val();

function renderOption(value) {
    if(value==-1){
        $('#desk_change').append($('<option>', {
            value: -1,
            text: selectText
        }));
    } else {
        $('#desk_change').append($('<option>', {
            value: value.deskNo,
            text: value.deskNo
        }));
    }
}