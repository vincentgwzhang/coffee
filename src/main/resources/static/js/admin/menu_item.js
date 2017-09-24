var myContextPath = global_getContextPath();
$(document).ready(pageInit());

function pageInit() {
    bindSaveBtn();
    bindDeleteBtn();
    bindAddBtn();
    bindReturnBtn();
    hideTip();
    prependAndRebindImportProduct();
}

function prependAndRebindImportProduct() {
    $('#menu_item_import_product').prepend($('<option value="-1">--</option>'));
    $('#menu_item_import_product').val(-1);

    $('select[id^="menu_item_import_product_"]').prepend($('<option value="-1">--</option>'));
    $('select[id^="menu_item_import_product_"]').each(function(index, value){
        var selectID = $(value).prop("id");
        var hiddenID = changeToDestinationID(selectID, "#menu_item_value_");
        var menuItemJson = $(hiddenID).attr("value");
        var menuItem = JSON.parse(menuItemJson);
        var bindedImportProductID = menuItem.ipId;
        if(bindedImportProductID==0) {
            $(value).val(-1);
        }
    });
}

function hideTip() {
    $('div[id^="mi_tip_"]').hide();
}

function bindReturnBtn() {
    $("#nav_return_btn").click(function(){
        window.location.href = myContextPath + PATH_MENU_CATEGORY;
    });
}

function bindDeleteBtn() {
    $('button[id^="mi_del_btn_"]').click(function(event) {
        bootbox.confirm("确认删除该菜单吗？（注意：该菜单所涉及的订单在被文档化前是无法被删除的）", function(result){
            if(result) {
                deleteMenuItem(event);
            }
        });
    });
}

function deleteMenuItem(event) {
    try {
        var sourceId = event.target.id;
        var index = sourceId.lastIndexOf("_");
        var id = sourceId.substring(index+1);
        //Step 1: send AJAX
        $.ajax(
            {
                url: myContextPath + REST_PATH_MENU_ITEM + "/" + id,
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                type: "DELETE",
                complete: function(xhr, statusText) {
                    try{
                        if ( xhr.status == HTTP_STATUS_ACCEPTED ) {
                            $("#mi_top_" + id).remove();
                        }
                    }
                    catch(e){console.error(e);}
                },
                error: global_handler_ajax_exception
            }
        );
    } catch (e) {
        handlerFrontEndException(e);
    }
}

var validationMessages = new Array();

function verifyBeforeSaveOrUpdateMenuItem(id_CN, id_EN, id_SP, id_Price) {
    validationMessages = [];
    verifyFieldNotEmpty(id_CN, ADMIN_VALIDATION_MENU_ITEM_LANGUAGE_CN);
    verifyFieldNotEmpty(id_EN, ADMIN_VALIDATION_MENU_ITEM_LANGUAGE_EN);
    verifyFieldNotEmpty(id_SP, ADMIN_VALIDATION_MENU_ITEM_LANGUAGE_SP);
    verifyFieldNotEmpty(id_Price, ADMIN_VALIDATION_PRICE);
    verifyFieldValidDigitFormat(id_Price, REGEX_FLOAT_2_DIGIT, ADMIN_VALIDATION_PRICE_FORMAT);
    showErrorMessageWindow(validationMessages);
}

function saveMenuItem(event) {
    try {
        var hiddenId = changeToDestinationID(event.target.id, "#menu_item_value_");
        var objHidden = $(hiddenId);
        var menuItemJson = $(objHidden).attr("value");
        var menuItem = JSON.parse(menuItemJson);

        var miId = menuItem.miId;

        var enabled_id = "#menu_item_active_" + miId;
        var toChief_id = "#menu_item_tochief_" + miId;
        var miPrice_id = "#menu_item_price_" + miId;
        var descEN_id  = "#menu_item_lg_en_" + miId;
        var descCN_id  = "#menu_item_lg_cn_" + miId;
        var descSP_id  = "#menu_item_lg_sp_" + miId;
        var miMc_id    = "#menu_item_cat_" + miId;
        var bindImportProduct_id    = "#menu_item_import_product_" + miId;

        var enabled = $(enabled_id).prop('checked');
        var toChief = $(toChief_id).prop('checked');
        var miPrice = $(miPrice_id).val();
        var descEN  = $(descEN_id).val();
        var descCN  = $(descCN_id).val();
        var descSP  = $(descSP_id).val();
        var miMcId  = $(miMc_id).val();
        var bindImportProductId  = $(bindImportProduct_id).val();

        verifyBeforeSaveOrUpdateMenuItem(descCN_id, descEN_id, descSP_id, miPrice_id);
        if(validationMessages.length > 0) {
            return;
        }

        menuItem.miEnable = enabled;
        menuItem.toChief = toChief;
        menuItem.miPrice = miPrice;
        menuItem.descEN = descEN;
        menuItem.descCN = descCN;
        menuItem.descSP = descSP;
        menuItem.miMcId = miMcId;
        menuItem.ipId = bindImportProductId;

        //Step 1: send AJAX
        $.ajax(
            {
                url: myContextPath + REST_PATH_MENU_ITEM,
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                type: "PUT",
                data:JSON.stringify(menuItem),
                success: function(data) {
                    try{
                        //Step 2: check if miMcId already change, if yes, then just refresh
                        if(data.miMcId != $("#this_category_id").val()) {
                            $("#mi_top_" + id).remove();
                        }

                        //Step 3: update css class
                        renderMenuItem(data);

                        //Step 4: update hidden field
                        var resJson = JSON.stringify(data);
                        $(objHidden).attr("value", resJson);

                        //Step 5: show tip
                        var tipID = changeToDestinationID(event.target.id, "#mi_tip_");
                        showTip(tipID);
                    } catch(e){
                        handlerFrontEndException(e);
                    }
                },
                error: function handlerError(xhr, status, error) {
                    global_handler_ajax_exception(xhr, status, error);

                    menuItem = JSON.parse(menuItemJson);
                    $(descEN_id).val(menuItem.descEN);
                    $(descCN_id).val(menuItem.descCN);
                    $(descSP_id).val(menuItem.descSP);
                    $(enabled_id).prop('checked', menuItem.miEnable).change();
                    $(toChief_id).prop('checked', menuItem.toChief).change();
                    if(menuItem.ipId==0){
                        $(bindImportProductId).val(-1);
                    } else {
                        $(bindImportProductId).val(menuItem.ipId);
                    }
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

function newMenuItem(event) {
    try {
        var mcId  = $("#this_category_id").val();

        var descCN_id = "#new_menu_item_lg_cn";
        var descEN_id = "#new_menu_item_lg_en";
        var descSP_id = "#new_menu_item_lg_sp";
        var miPrice_id = "#new_menu_item_price";
        var importProduct_id = "#menu_item_import_product";

        verifyBeforeSaveOrUpdateMenuItem(descCN_id, descEN_id, descSP_id, miPrice_id);
        if(validationMessages.length > 0) {
            return;
        }

        var enabled = $("#new_menu_item_active").prop('checked');
        var toChief = $("#new_menu_item_tochief").prop('checked');
        var miPrice = $(miPrice_id).val();
        var descEN  = $(descEN_id).val();
        var descCN  = $(descCN_id).val();
        var descSP  = $(descSP_id).val();
        var importProduct  = $(importProduct_id).val();

        var menuItem = new Object();
        menuItem.miEnable = enabled;
        menuItem.toChief = toChief;
        menuItem.miPrice = miPrice;
        menuItem.miPic = "/mock/pic/now";
        menuItem.descEN = descEN;
        menuItem.descCN = descCN;
        menuItem.descSP = descSP;
        menuItem.miMcId = mcId;
        menuItem.ipId = importProduct;

        //Step 1: send AJAX
        $.ajax(
            {
                url: myContextPath + REST_PATH_MENU_ITEM,
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                type: "POST",
                data:JSON.stringify(menuItem),
                success: function(data) {
                    try{
                        location.reload();
                    }
                    catch(e){console.error(e);}
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
    $("[id^='mi_save_btn_']").click(saveMenuItem);
}

function bindAddBtn() {
    $("#new_menu_item_btn").click(newMenuItem);
}