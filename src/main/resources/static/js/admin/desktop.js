var myContextPath = global_getContextPath();

function pageInit() {
    renderDesktops();
    bindCheckbox();
    bindDeleteBtn();
    bindAddBtn();
}

function bindAddBtn() {
    $("#desk_new_btn").click(function(e){
        var desktopNumber   = $("#desktop_new_desknumber").val();
        var desktopEnable   = $("#desktop_new_enabled").prop('checked');
        var desktopOccupied = $("#desktop_new_occupied").prop('checked');

        var newDesktop = new Object();
        newDesktop.deskNo=desktopNumber;
        newDesktop.deskEnable=desktopEnable;
        newDesktop.deskOccupied=desktopOccupied;

        $.ajax(
            {
                url: myContextPath + REST_PATH_DESKTOP,
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                type: "POST",
                data:JSON.stringify(newDesktop),
                success: function(data) {
                    try{
                        location.reload();
                    }
                    catch(e){handlerFrontEndException(e);}
                },
                error: global_handler_ajax_exception
            }
        );
    });
}

function bindDeleteBtn() {
    $('button[id^="desktop_btn_del_"]').click(function(e){
        bootbox.confirm("确认删除桌子吗？", function(result){
            var objHidden = $(e.target).siblings(":hidden");
            var desktopJson = $(objHidden).attr("value");
            var desktop = JSON.parse(desktopJson);
            var delPath = myContextPath + REST_PATH_DESKTOP + "/" + desktop.deskNo;
            if(result) {
                $.ajax(
                    {
                        url: delPath,
                        contentType: "application/json; charset=utf-8",
                        dataType: "json",
                        type: "DELETE",
                        complete: function(xhr, statusText) {
                            try{
                                if ( xhr.status == HTTP_STATUS_ACCEPTED ) {
                                    $("#desktop_top_" + desktop.deskNo).remove();
                                }
                            }
                            catch(e){handlerFrontEndException(e);}
                        },
                        error: global_handler_ajax_exception
                    }
                );
            }
        });
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
    var updatedStatus = desktop.deskEnable;
    if(updatedStatus) {
        $("#desktop_div_" + desktop.deskNo).attr("class", "panel panel-info");
    } else {
        $("#desktop_div_" + desktop.deskNo).attr("class", "panel panel-default");
    }
}

function bindCheckbox() {
    $("input[type=checkbox]").not("#desktop_new_enabled").not("#desktop_new_occupied").change(
        function() {
            try {
                var objHidden = $(this).parent().parent().children(":hidden");
                var desktopJson = $(objHidden).attr("value");
                var desktop = JSON.parse(desktopJson);

                var desktopNo = desktop.deskNo;
                var enabled   = $("#desktop_" + desktopNo).prop('checked');
                var opccupied = $("#desktop_occupied_" + desktopNo).prop('checked');

                desktop.deskEnable = enabled;
                desktop.deskOccupied = opccupied;

                //Step 1: send AJAX
                $.ajax(
                    {
                        url: myContextPath + REST_PATH_DESKTOP,
                        contentType: "application/json; charset=utf-8",
                        dataType: "json",
                        type: "PUT",
                        data:JSON.stringify(desktop),
                        success: function(data) {
                            try{
                                //Step 2: update css class
                                renderDesktop(data);

                                //Step 3: update hidden field
                                var resJson = JSON.stringify(data);
                                $(objHidden).attr("value", resJson);
                            }
                            catch(e){handlerFrontEndException(e);}
                        },
                        error: global_handler_ajax_exception
                    }
                );
            } catch (e) {
                handlerFrontEndException(e);
            }

        }
    );
}

//jQuery init script
$(document).ready(pageInit());