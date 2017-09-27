var myContextPath = global_getContextPath();
$(document).ready(pageInit());

function pageInit() {
    bindSaveBtn();
    bindDeleteBtn();
    bindAddBtn();
    bindCheckBtn();
    hideTip();
}

function hideTip() {
    $('div[id^="mc_tip_"]').hide();
}

function bindDeleteBtn() {
    $('button[id^="desk_del_btn_"]').click(function(event) {
        bootbox.confirm("确认删除该菜单栏吗？(若菜单栏有子菜单将无法被删除)", function(result){
            if(result) {
                deleteMenuCategory(event);
            }
        });
    });
}

function deleteMenuCategory(event) {
    try {
        var sourceId = event.target.id;
        var index = sourceId.lastIndexOf("_");
        var id = sourceId.substring(index+1);
        //Step 1: send AJAX
        $.ajax(
            {
                url: myContextPath + REST_PATH_MENU_CATEGORY + "/" + id,
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                type: "DELETE",
                complete: function(xhr, statusText) {
                    if ( xhr.status == HTTP_STATUS_ACCEPTED ) {
                        $("#mc_top_" + id).remove();
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

function verifyBeforeSaveOrUpdateMenuCategory(id_CN, id_EN, id_SP) {
    validationMessages = [];
    verifyFieldNotEmpty(id_CN, ADMIN_VALIDATION_MENU_CATEGORY_LANGUAGE_CN);
    verifyFieldNotEmpty(id_EN, ADMIN_VALIDATION_MENU_CATEGORY_LANGUAGE_EN);
    verifyFieldNotEmpty(id_SP, ADMIN_VALIDATION_MENU_CATEGORY_LANGUAGE_SP);
    showErrorMessageWindow(validationMessages);
}

function saveMenuCategory(event) {
    try {
        var sourceId = event.target.id;
        var index = sourceId.lastIndexOf("_");
        var id = sourceId.substring(index+1);

        var objHidden = $("#menu_cate_value_" + id);
        var menuCategoryJson = $(objHidden).attr("value");
        var menuCategory = JSON.parse(menuCategoryJson);

        var mcId = menuCategory.mcId;

        var descCN_id = "#menu_cate_lg_cn_" + mcId;
        var descEN_id = "#menu_cate_lg_en_" + mcId;
        var descSP_id = "#menu_cate_lg_sp_" + mcId;

        verifyBeforeSaveOrUpdateMenuCategory(descCN_id, descEN_id, descSP_id);
        if(validationMessages.length > 0) {
            return;
        }

        var enabled   = $("#menu_cate_active_" + mcId).prop('checked');
        var descEN = $(descEN_id).val();
        var descCN = $(descCN_id).val();
        var descSP = $(descSP_id).val();

        menuCategory.mcEnable = enabled;
        menuCategory.descEN = descEN;
        menuCategory.descCN = descCN;
        menuCategory.descSP = descSP;

        //Step 1: send AJAX
        $.ajax(
            {
                url: myContextPath + REST_PATH_MENU_CATEGORY,
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                type: "PUT",
                data:JSON.stringify(menuCategory),
                success: function(data) {
                    try{
                        //Step 2: update css class
                        renderMenuCategory(data);

                        //Step 3: update hidden field
                        var resJson = JSON.stringify(data);
                        $(objHidden).attr("value", resJson);

                        //ShowMsg("保存成功");
                        var tipID = changeToDestinationID(event.target.id, "#mc_tip_");
                        showTip2(tipID);
                    }
                    catch(e){handlerFrontEndException(e);}
                },
                error: function handlerError(xhr, status, error) {
                    global_handler_ajax_exception(xhr, status, error);

                    menuCategory = JSON.parse(menuCategoryJson);
                    $("#menu_cate_lg_en_" + mcId).val(menuCategory.descEN);
                    $("#menu_cate_lg_cn_" + mcId).val(menuCategory.descCN);
                    $("#menu_cate_lg_sp_" + mcId).val(menuCategory.descSP);

                    $("#menu_cate_active_" + mcId).prop('checked', menuCategory.mcEnable).change();
                }
            }
        );
    } catch (e) {
        handlerFrontEndException(e);
    }
}

function newMenuCategory(event) {
    try {
        var descCN_id = "#new_menu_cate_lg_cn";
        var descEN_id = "#new_menu_cate_lg_en";
        var descSP_id = "#new_menu_cate_lg_sp";

        verifyBeforeSaveOrUpdateMenuCategory(descCN_id, descEN_id, descSP_id);
        if(validationMessages.length > 0) {
            return;
        }

        var enabled = $("#new_menu_cate_active").prop('checked');
        var descEN  = $(descEN_id).val();
        var descCN  = $(descCN_id).val();
        var descSP  = $(descSP_id).val();

        var menuCategory = new Object();
        menuCategory.mcEnable = enabled;
        menuCategory.descEN = descEN;
        menuCategory.descCN = descCN;
        menuCategory.descSP = descSP;

        //Step 1: send AJAX
        $.ajax(
            {
                url: myContextPath + REST_PATH_MENU_CATEGORY,
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                type: "POST",
                data:JSON.stringify(menuCategory),
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

function renderMenuCategory(menuCategory) {
    var isEnable = menuCategory.mcEnable;
    if(isEnable) {
        $("#mc_top_" + menuCategory.mcId).attr("class", "panel panel-primary");
    } else {
        $("#mc_top_" + menuCategory.mcId).attr("class", "panel panel-default");
    }
    $("#menu_cate_active_" + menuCategory.mcId).prop('checked', isEnable).change();
}

function checkMenuItems(event) {
    try {
        var sourceId = event.target.id;
        var index = sourceId.lastIndexOf("_");
        var id = sourceId.substring(index+1);

        var url = myContextPath + PATH_MENU_ITEM + "?mcId=" + id;
        window.location.href = url;
    } catch (e) {
        handlerFrontEndException(e);
    }
}

function bindSaveBtn() {
    $("[id^='desk_save_btn_']").click(saveMenuCategory);
}

function bindCheckBtn() {
    $("[id^='desk_check_btn_']").click(checkMenuItems);
}

function bindAddBtn() {
    $("#new_desk_btn").click(newMenuCategory);
}