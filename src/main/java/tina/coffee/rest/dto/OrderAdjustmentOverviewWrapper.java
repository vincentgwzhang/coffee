package tina.coffee.rest.dto;

import com.google.common.collect.Maps;
import tina.coffee.function.OrderAdjustmentOverviewFunction;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OrderAdjustmentOverviewWrapper {

    private Map<Integer, OverviewWrapper> overviewWrapperList;

    public Map<Integer, OverviewWrapper> getOverviewWrapperList() {
        return overviewWrapperList;
    }

    public void setOverviewWrapperList(Map<Integer, OverviewWrapper> overviewWrapperList) {
        this.overviewWrapperList = overviewWrapperList;
    }

    public OrderAdjustmentOverviewWrapper(int dateCount, List<OrderAdjustmentOverviewDTO> orderAdjustmentOverviewDTOList) {
        overviewWrapperList = new LinkedHashMap<>();
        for(Integer index = 1 ; index <= dateCount; index ++ ) {
            overviewWrapperList.put(index, new OverviewWrapper());
        }

        Map<Integer, OrderAdjustmentOverviewDTO> map = Maps.uniqueIndex(orderAdjustmentOverviewDTOList, OrderAdjustmentOverviewFunction::getOverviewDate);
        map.entrySet().forEach(entry -> overviewWrapperList.get(entry.getKey()).setOrderAdjustmentOverviewDTO(entry.getValue()));
    }

    public static class OverviewWrapper {

        private OrderAdjustmentOverviewDTO orderAdjustmentOverviewDTO = null;

        private boolean hasValue = false;

        public OverviewWrapper() {
            hasValue = false;
        }

        public boolean isHasValue() {
            return hasValue;
        }

        public OrderAdjustmentOverviewDTO getOrderAdjustmentOverviewDTO() {
            return orderAdjustmentOverviewDTO;
        }

        public void setOrderAdjustmentOverviewDTO(OrderAdjustmentOverviewDTO orderAdjustmentOverviewDTO) {
            if(orderAdjustmentOverviewDTO!=null) {
                this.orderAdjustmentOverviewDTO = orderAdjustmentOverviewDTO;
                hasValue = true;
            }

        }

    }

}
