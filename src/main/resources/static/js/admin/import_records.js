var myContextPath = global_getContextPath();
$(document).ready(pageInit());

function pageInit() {
    bindReturnBtn();
    bindStatisticsBtn();
}

function bindReturnBtn() {
    $("#nav_return_btn").click(function(){
        window.location.href = myContextPath + PATH_PRODUCT_CONFIG;
    });
}

function bindStatisticsBtn() {
    bindTotalPriceBtn();
    bindTotalItemBtn();
}

function bindTotalPriceBtn() {
    var TOTAL_VALUE = 0;
    $('input[id^="td_price_"]').each(function(index) {
        TOTAL_VALUE = TOTAL_VALUE + parseFloat($(this).val());
    });
    $("#td_total_price").text(TOTAL_VALUE.toFixed(2));
}

function bindTotalItemBtn() {
    var TOTAL_VALUE = 0;
    $('input[id^="td_count_"]').each(function(index) {
        TOTAL_VALUE = TOTAL_VALUE + parseFloat($(this).val());
    });
    $("#td_total_count").text(TOTAL_VALUE.toFixed(0));
}