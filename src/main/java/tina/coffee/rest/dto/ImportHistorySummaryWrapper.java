package tina.coffee.rest.dto;

import com.google.common.collect.Maps;
import tina.coffee.function.ImportHistoryOverviewFunction;
import tina.coffee.function.OrderAdjustmentOverviewFunction;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ImportHistorySummaryWrapper {

    private Map<Integer, SummaryWrapper> summaryWrapperMap;

    public Map<Integer, SummaryWrapper> getSummaryWrapperMap() {
        return summaryWrapperMap;
    }

    public void setSummaryWrapperMap(Map<Integer, SummaryWrapper> summaryWrapperMap) {
        this.summaryWrapperMap = summaryWrapperMap;
    }

    public ImportHistorySummaryWrapper(int dateCount, List<ImportHistorySummaryDTO> importHistorySummaryDTOList) {
        summaryWrapperMap = new LinkedHashMap<>();
        for(Integer index = 1 ; index <= dateCount; index ++ ) {
            summaryWrapperMap.put(index, new SummaryWrapper());
        }

        Map<Integer, ImportHistorySummaryDTO> map = Maps.uniqueIndex(importHistorySummaryDTOList, ImportHistoryOverviewFunction::getImportDate);
        map.entrySet().forEach(entry -> summaryWrapperMap.get(entry.getKey()).setImportHistorySummaryDTO(entry.getValue()));
    }

    public static class SummaryWrapper {

        private ImportHistorySummaryDTO importHistorySummaryDTO = null;

        private boolean hasValue = false;

        public SummaryWrapper() {
            hasValue = false;
        }

        public boolean isHasValue() {
            return hasValue;
        }

        public ImportHistorySummaryDTO getImportHistorySummaryDTO() {
            return importHistorySummaryDTO;
        }

        public void setImportHistorySummaryDTO(ImportHistorySummaryDTO importHistorySummaryDTO) {
            if(importHistorySummaryDTO!=null) {
                this.importHistorySummaryDTO = importHistorySummaryDTO;
                this.hasValue = true;
            }
        }
    }

}
