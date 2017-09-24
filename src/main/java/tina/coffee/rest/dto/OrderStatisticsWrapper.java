package tina.coffee.rest.dto;

import com.google.common.collect.Maps;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OrderStatisticsWrapper {

    private Map<Integer, StatisticsWrapper> statisticsWrapperMap = null;

    public Map<Integer, StatisticsWrapper> getStatisticsWrapperMap() {
        return statisticsWrapperMap;
    }

    public OrderStatisticsWrapper(int dayCount, List<StatisticsDTO> statisticsDTOList) {
        statisticsWrapperMap = new LinkedHashMap<>();
        for(int index = 1; index <= dayCount; index ++) {
            statisticsWrapperMap.put(index, new StatisticsWrapper());
        }
        Map<Integer, StatisticsDTO> map = Maps.uniqueIndex(statisticsDTOList, StatisticsDTO::getDayOfMonth);
        map.entrySet().forEach(entry -> statisticsWrapperMap.get(entry.getKey()).setStatisticsDTO(entry.getValue()));
    }

    public static class StatisticsWrapper {

        private boolean hasValue = false;

        private StatisticsDTO statisticsDTO = null;

        public StatisticsWrapper() {
            hasValue = false;
        }

        public boolean isHasValue() {
            return hasValue;
        }

        public StatisticsDTO getStatisticsDTO() {
            return statisticsDTO;
        }

        public void setStatisticsDTO(StatisticsDTO statisticsDTO) {
            this.statisticsDTO = statisticsDTO;
            hasValue = true;
        }
    }

    public static class StatisticsDTO {

        private Integer dayOfMonth;

        private BigDecimal totalPrice;

        public StatisticsDTO(Integer dayOfMonth, BigDecimal totalPrice) {
            this.dayOfMonth = dayOfMonth;
            this.totalPrice = totalPrice;
        }

        public Integer getDayOfMonth() {
            return dayOfMonth;
        }

        public void setDayOfMonth(Integer dayOfMonth) {
            this.dayOfMonth = dayOfMonth;
        }

        public BigDecimal getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
        }
    }

}
