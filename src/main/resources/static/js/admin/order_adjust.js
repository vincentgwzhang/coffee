var myContextPath = global_getContextPath();
$(document).ready(pageInit());

function pageInit() {
    initOriOrderTotal();
    initUpdateField();
    initBtnGenPDF();
}

function initBtnGenPDF() {//$('#divExportPDF').html()
    $("#btnGenPDF").click(function() {
        var arrCanvas = new Array();
        var doc = new jsPDF('p', 'mm');
        $('div[id^="pdf_page_order_"]').each(function(index){
            html2canvas($(this), {
                onrendered:function(canvas) {
                    arrCanvas[index] = canvas;
                }
            });
        });

        var filename = getUrlParam("date");

        setTimeout(function(){
            try{
                for(index = 0; index < arrCanvas.length; index ++){
                    var imgData = arrCanvas[index].toDataURL('image/jpeg');
                    doc.addImage(imgData, 'JPEG', 10, 10);
                    if(index != arrCanvas.length -1){
                        doc.addPage();
                    }
                }
                doc.save(filename + '.pdf');
            }catch(e){
                handlerFrontEndException(e);
            }
        }, 1000);

        var oriTotalOrderValue = $("#p_ori_total_order_value").text();
        var uptTotalOrderValue = $("#p_upt_total_order_value").text();

        var overviewDTO = new Object();
        overviewDTO.adjustDate = filename.replace(new RegExp("_","gm"),"-");
        overviewDTO.oriTotal = oriTotalOrderValue;
        overviewDTO.adjTotal = uptTotalOrderValue;

        $.ajax(
            {
                url: myContextPath + REST_PATH_ORDER_ADJUSTMENT,
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                type: "POST",
                data:JSON.stringify(overviewDTO),
                complete: function(xhr, statusText) {
                    //Do nothing here?
                },
                error: global_handler_ajax_exception
            }
        );
    });
}

function initOriOrderTotal() {
    var ORI_TOTAL_ORDER_VALUE = 0;
    $('input[id^="hid_ori_order_total_"]').each(function(com) {
        var input_id = $(this).attr("id");
        var input_value = $(this).attr("value");
        ORI_TOTAL_ORDER_VALUE = ORI_TOTAL_ORDER_VALUE + parseFloat(input_value);
    });
    $("#p_ori_total_order_value").text(ORI_TOTAL_ORDER_VALUE.toFixed(2));
    $("#p_upt_total_order_value").text(ORI_TOTAL_ORDER_VALUE.toFixed(2));
}

function initUpdateField() {
    initPriceField();
    initCountField();
}

function adjustPrice() {
    reCalculateSubItem(this);
    reCalculateOrder(this);
    reCalculateTotal(this)
}

function reCalculateTotal(com) {
    var UPT_TOTAL_ORDER_VALUE = 0;
    $('span[id^=span_upt_order_]').each(function(com) {
        var input_id = $(this).attr("id");
        var input_value = $(this).text();
        UPT_TOTAL_ORDER_VALUE = UPT_TOTAL_ORDER_VALUE + parseFloat(input_value);
    });
    $("#p_upt_total_order_value").text(UPT_TOTAL_ORDER_VALUE.toFixed(2));
}

function reCalculateOrder(com) {
    var UPT_ORDER_VALUE = 0;
    var suffix = com.id.substring("14");
    var orderId = suffix.split("_")[0];
    var span_prefix = "span_upt_price_"+orderId;
    $('span[id^=' + span_prefix + ']').each(function(com) {
        var input_id = $(this).attr("id");
        var input_value = $(this).text();
        UPT_ORDER_VALUE = UPT_ORDER_VALUE + parseFloat(input_value);
    });
    $("#span_upt_order_" + orderId).text(UPT_ORDER_VALUE.toFixed(2));
}

function reCalculateSubItem(com) {
    try{
        if(!isNaN(parseFloat(com.value))){
            //locate that order seciton
            var suffix = com.id.substring("14");
            var price_id = "#txt_upt_price_" + suffix;
            var count_id = "#txt_upt_count_" + suffix;
            var singleItemTotal = parseFloat($(price_id).val()) * parseInt($(count_id).val());
            $("#span_upt_price_" + suffix).html(singleItemTotal.toFixed(2));
        }
    } catch(e) {
        return ;
    }
}

function initPriceField() {
    $('input[id^="txt_upt_price_"]').bind("keypress", restrictNumberAndDotOnly);
    $('input[id^="txt_upt_price_"]').bind("paste", pasteNumberAndDotOnly);
    $('input[id^="txt_upt_price_"]').bind("blur", adjustPrice);
}

function initCountField() {
    $('input[id^="txt_upt_count_"]').bind("keypress", restrictNumberOnly);
    $('input[id^="txt_upt_count_"]').bind("paste", pasteNumberOnly);
    $('input[id^="txt_upt_count_"]').bind("blur", adjustPrice);
}