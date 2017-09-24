var myContextPath = global_getContextPath();
$(document).ready(pageInit());

function pageInit() {
    renderDesktops();
    bindBtn_OpenDesk();
    bindBtn_Entrance();
}

function bindBtn_OpenDesk() {
    $('input[id^="op_desk_btn_open_"]').click(function(e){
        var objID   = $(this).prop("id");
        var desktopNumber = getIdentity(objID);
        var url = myContextPath + PATH_OPEN_DESK + "/" + desktopNumber;
        window.location.replace(url);
    });
}

function bindBtn_Entrance() {
    $('input[id^="op_desk_btn_occupied_"]').click(function(e){
        var objID   = $(this).prop("id");
        var desktopNumber = getIdentity(objID);
        var url = myContextPath + PATH_INTO_DESK + "/" + desktopNumber;
        window.location.replace(url);
    });
}

function renderDesktops() {
    $.ajax(
        {
            url: myContextPath + REST_PATH_DESKTOP2,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            type: "GET",
            success: function(data) {
                try{
                    $.each(data, function(index, value) {
                        renderDesktop(value);
                    });
                }
                catch(e){handlerFrontEndException(e);}
            },
            error: global_handler_ajax_exception
        }
    );
}

function renderDesktop(desktop) {
    var ENABLE_CLASS  = "panel desk_avail";
    var DISABLE_CLASS = "panel desk_disabled";
    var OCCUPY_CLASS  = "panel desk_occupied";

    var isEnabled  = desktop.deskEnable;
    var isOccupied = desktop.deskOccupied;

    if(isEnabled) {
        $("#op_desk_div_" + desktop.deskNo).attr("class", ENABLE_CLASS);
    } else {
        $("#op_desk_div_" + desktop.deskNo).attr("class", DISABLE_CLASS);
    }

    if(isOccupied) {
        $("#op_desk_div_" + desktop.deskNo).attr("class", OCCUPY_CLASS);
    }
}