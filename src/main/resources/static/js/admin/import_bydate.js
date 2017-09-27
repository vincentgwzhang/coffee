var myContextPath = global_getContextPath();
$(document).ready(pageInit());

function pageInit() {
    initDate();
    initUpdateField();
    reCalculateTotal();
    bindCheckbox();
    bindSaveBtn();
    bindDeleteBtn();
    bindAddBtn();
    bindReturnBtn();
    hideTip();
}

function hideTip() {
    $('div[id^="import_product_tip_"]').hide();
}

function bindCheckbox() {
    $("input[type=checkbox]").change(
        function() {
            var divID = changeToDestinationID($(this).attr("id"), "#import_product_div_");
            if($(this).prop("checked")) {
                $(divID).attr("class", "panel panel-primary");
            } else {
                $(divID).attr("class", "panel panel-success");
            }
        }
    );
}

function initUpdateField() {
    initPriceField();
    initCountField();
}

function initPriceField() {
    $('input[id^="import_product_price_"]').bind("keypress", restrictNumberAndDotOnly);
    $('input[id^="import_product_price_"]').bind("paste", pasteNumberAndDotOnly);
    $('input[id^="import_product_price_"]').bind("blur", reCalculateTotal);
}

function initCountField() {
    $('input[id^="import_product_count_"]').bind("keypress", restrictNumberOnly);
    $('input[id^="import_product_count_"]').bind("paste", pasteNumberOnly);
}

function reCalculateTotal() {
    var UPT_TOTAL_ORDER_VALUE = 0;
    $('input[id^=import_product_price_]').each(function(com) {
        var input_id = $(this).attr("id");
        var input_value = $(this).val();
        if(!input_value) {input_value=0;}
        UPT_TOTAL_ORDER_VALUE = UPT_TOTAL_ORDER_VALUE + parseFloat(input_value);
    });
    $("#import_total").text(UPT_TOTAL_ORDER_VALUE.toFixed(2));
}

function initDate() {
    var param_date = getUrlParam("date");
    var dates = param_date.split("_");

    $("#import_date").html(dates[1] + " / " + dates[2] + " / " + dates[0]);
}

function bindReturnBtn() {
    $("#nav_return_btn").click(function(){
        var param_date = getUrlParam("date");
        var pos = param_date.lastIndexOf("_");
        var year_month = param_date.substring(0, pos);

        window.location.href = myContextPath + PATH_IMPORT_PRODUCT_CALENDAR + "?date=" + year_month;
    });
}

function bindDeleteBtn() {
    $('button[id^="import_product_del_btn_"]').click(function(event) {
        bootbox.confirm("确认删除该入货项吗？", function(result){
            if(result) {
                deleteImportProduct(event);
            }
        });
    });
}

function deleteImportProduct(event) {
    try {
        var sourceId = event.target.id;
        var id = getIdentity(sourceId);
        //Step 1: send AJAX
        $.ajax(
            {
                url: myContextPath + REST_PATH_IMPORT_HISTORY + "/" + id,
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                type: "DELETE",
                complete: function(xhr, statusText) {
                    if ( xhr.status == HTTP_STATUS_ACCEPTED ) {
                        $("#import_product_top_" + id).remove();
                        reCalculateTotal();
                    }
                },
                error: global_handler_ajax_exception
            }
        );
    } catch (e) {
        handlerFrontEndException(e);
    }
}

var validationMessages = new Array();

function verifyBeforeSaveOrUpdateImportProductHistory(id_Count, id_Price) {
    validationMessages = [];

    setDefaultIfEmpty(id_Count, 0);
    setDefaultIfEmpty(id_Price, 0);

    verifyFieldNotEmpty(id_Price, ADMIN_VALIDATION_PRICE);
    verifyFieldValidDigitFormat(id_Price, REGEX_FLOAT_2_DIGIT, ADMIN_VALIDATION_PRICE_FORMAT);

    verifyFieldNotEmpty(id_Count, ADMIN_VALIDATION_COUNT);
    verifyFieldValidDigitFormat(id_Count, REGEX_POSITIVE_INT, ADMIN_VALIDATION_COUNT_FORMAT);
    showErrorMessageWindow(validationMessages);
}

function saveImportProduct(event) {
    try {
        var hiddenID = changeToDestinationID(event.target.id, "#import_product_value_");

        var objHidden = $(hiddenID);
        var importedProductJson = $(objHidden).attr("value");
        var importedProduct = JSON.parse(importedProductJson);

        var ihId = importedProduct.ihId;

        var itemID  = "#import_product_dp_" + ihId;
        var countID = "#import_product_count_" + ihId;
        var priceID = "#import_product_price_" + ihId;

        var item   = $(itemID).val();

        verifyBeforeSaveOrUpdateImportProductHistory(countID, priceID);
        if(validationMessages.length > 0) {
            return;
        }

        importedProduct.ihIpId  = item;
        importedProduct.ihPrice = $(priceID).val();
        importedProduct.count   = $(countID).val();
        //Step 1: send AJAX
        $.ajax(
            {
                url: myContextPath + REST_PATH_IMPORT_HISTORY,
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                type: "PUT",
                data:JSON.stringify(importedProduct),
                success: function(data) {
                    try{
                        var resJson = JSON.stringify(data);
                        $(objHidden).attr("value", resJson);

                        //show tip
                        var tipID = changeToDestinationID(event.target.id, "#import_product_tip_");
                        showTip(tipID);
                        reCalculateTotal();
                    }
                    catch(e){handlerFrontEndException(e);}
                },
                error: function handlerError(xhr, status, error) {
                    global_handler_ajax_exception(xhr, status, error);

                    importedProduct = JSON.parse(importedProductJson);
                    $(countID).val(importedProduct.count);
                    $(priceID).val(importedProduct.ihPrice);
                    $(itemID).val(importedProduct.ihIpId)
                }
            }
        );
    } catch (e) {
        handlerFrontEndException(e);
    }
}

function showTip(tipID) {
    $(tipID).fadeTo(2000, 500).slideUp(500, function(){
        $(tipID).slideUp(500);
    });
}

function newImportProduct(event) {
    try {
        var newImportedProduct = new Object();
        var param_date = getUrlParam("date");

        var itemID  = "#new_import_product_dp";
        var countID = "#import_product_count";
        var priceID = "#import_product_price";

        var item   = $(itemID).val();

        verifyBeforeSaveOrUpdateImportProductHistory(countID, priceID);
        if(validationMessages.length > 0) {
            return;
        }

        newImportedProduct.ihIpId  = item;
        newImportedProduct.ihPrice = $(priceID).val();
        newImportedProduct.count   = $(countID).val();

        //Step 1: send AJAX
        $.ajax(
            {
                url: myContextPath + REST_PATH_IMPORT_HISTORY + "/date/" + param_date,
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                type: "POST",
                data:JSON.stringify(newImportedProduct),
                success: function(data) {
                    location.reload();
                },
                error: global_handler_ajax_exception
            }
        );
    } catch (e) {
        handlerFrontEndException(e);
    }
}

function bindSaveBtn() {
    $("[id^='import_product_save_btn_']").click(saveImportProduct);
}

function bindAddBtn() {
    $("#import_product_new_btn").click(newImportProduct);
}