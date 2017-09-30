var myContextPath = global_getContextPath();
$(document).ready(pageInit());

function pageInit() {
    bindSaveBtn();
    bindDeleteBtn();
    bindAddBtn();
    bindTraceBtn();
    hideTip();
}

function hideTip() {
    $('div[id^="product_save_"]').hide();
}

function showTip(tipID) {
    $(tipID).fadeTo(2000, 500).slideUp(500, function(){
        $(tipID).slideUp(500);
    });
}

function bindTraceBtn() {
    $('button[id^="product_trace_btn_"]').click(function(event) {
        var sourceID = event.target.id;
        var index = sourceID.lastIndexOf("_");
        var prdID = sourceID.substring(index+1);

        window.location.href = myContextPath + PATH_IMPORT_PRODUCT_TRACE + "?prd_id=" + prdID;
    });
}

function bindDeleteBtn() {
    $('button[id^="product_del_btn_"]').click(function(event) {
        bootbox.confirm("确认删除该入货物品吗？", function(result){
            if(result) {
                deleteMenuItem(event);
            }
        });
    });
}

function deleteMenuItem(event) {
    try {
        var id = getIdentity(event.target.id);
        //Step 1: send AJAX
        $.ajax(
            {
                url: myContextPath + REST_PATH_IMPORT_PRODUCT + "/" + id,
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                type: "DELETE",
                complete: function(xhr, statusText) {
                    if ( xhr.status == HTTP_STATUS_ACCEPTED ) {
                        $("#product_div_" + id).remove();
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

function verifyBeforeSaveOrUpdateImportProduct(id_CN, id_EN, id_SP) {
    validationMessages = [];
    verifyFieldNotEmpty(id_CN, ADMIN_VALIDATION_IMPORT_PRODUCT_LANGUAGE_CN);
    verifyFieldNotEmpty(id_EN, ADMIN_VALIDATION_IMPORT_PRODUCT_LANGUAGE_EN);
    verifyFieldNotEmpty(id_SP, ADMIN_VALIDATION_IMPORT_PRODUCT_LANGUAGE_SP);
    showErrorMessageWindow(validationMessages);
}

function saveImportProduct(event) {
    try {
        var sourceId = event.target.id;
        var index = sourceId.lastIndexOf("_");
        var id = sourceId.substring(index+1);

        var objHidden = $("#product_import_value_" + id);
        var productJson = $(objHidden).attr("value");
        var product = JSON.parse(productJson);

        var ipId = product.ipId;

        var enName_id  = "#product_lg_en_" + ipId;
        var cnName_id  = "#product_lg_cn_" + ipId;
        var spName_id  = "#product_lg_sp_" + ipId;
        var count_id  = "#product_count_" + ipId;
        var countable_id  = "#ck_countable_" + ipId;

        var descEN  = $(enName_id).val();
        var descCN  = $(cnName_id).val();
        var descSP  = $(spName_id).val();
        var count  = $(count_id).val();
        if(isEmpty(count)) {
            count = 0;
        }

        var countable   = $(countable_id).prop('checked');

        verifyBeforeSaveOrUpdateImportProduct(cnName_id, enName_id, spName_id);

        product.enName = descEN;
        product.cnName = descCN;
        product.spName = descSP;
        product.ipId = ipId;
        product.ipCountable = countable;
        product.count = count;

        //Step 1: send AJAX
        $.ajax(
            {
                url: myContextPath + REST_PATH_IMPORT_PRODUCT,
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                type: "PUT",
                data:JSON.stringify(product),
                success: function(data) {
                    try{
                        //Step 4: update hidden field
                        var resJson = JSON.stringify(data);
                        $(objHidden).attr("value", resJson);

                        var tipID = changeToDestinationID(sourceId, "#product_save_");
                        showTip(tipID);
                    }
                    catch(e){handlerFrontEndException(e);}
                },
                error: function handlerError(xhr, status, error) {
                    global_handler_ajax_exception(xhr, status, error);

                    product = JSON.parse(productJson);
                    $(enName_id).val(product.enName);
                    $(cnName_id).val(product.cnName);
                    $(spName_id).val(product.spName);
                    $(count_id).val(product.count);
                }
            }
        );
    } catch (e) {
        handlerFrontEndException(e);
    }
}

function newImportProduct(event) {
    try {
        var id_CN = "#product_new_lg_cn";
        var id_EN = "#product_new_lg_en";
        var id_SP = "#product_new_lg_sp";
        var id_count = "#product_count";
        var id_countable = "#ck_countable";

        var cnName  = $(id_CN).val();
        var enName  = $(id_EN).val();
        var spName  = $(id_SP).val();
        var count  = $(id_count).val();
        var countable   = $(id_countable).prop('checked');

        if(isEmpty(count)) {
            count = 0;
        }

        var importProduct = new Object();
        importProduct.cnName = cnName;
        importProduct.enName = enName;
        importProduct.spName = spName;
        importProduct.ipCountable = countable;
        importProduct.count = count;

        verifyBeforeSaveOrUpdateImportProduct(id_CN, id_EN, id_SP);
        if(validationMessages.length > 0) {
            return;
        }

        //Step 1: send AJAX
        $.ajax(
            {
                url: myContextPath + REST_PATH_IMPORT_PRODUCT,
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                type: "POST",
                data:JSON.stringify(importProduct),
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

function renderMenuItem(menuItem) {
    var isEnable = menuItem.miEnable;
    if(isEnable) {
        $("#mi_top_" + menuItem.miId).attr("class", "panel panel-primary");
    } else {
        $("#mi_top_" + menuItem.miId).attr("class", "panel panel-default");
    }
    $("#menu_item_active_" + menuItem.miId).prop('checked', isEnable).change();
    $("#menu_item_tochief_" + menuItem.miId).prop('checked', menuItem.toChief).change();
}

function bindSaveBtn() {
    $("[id^='product_save_btn_']").click(saveImportProduct);
}

function bindAddBtn() {
    $("#product_new_save_btn").click(newImportProduct);
}