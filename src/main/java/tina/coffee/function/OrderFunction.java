package tina.coffee.function;

import com.google.common.collect.Sets;
import tina.coffee.data.model.DesktopEntity;
import tina.coffee.data.model.OrderEntity;
import tina.coffee.data.model.OrderItemStatus;
import tina.coffee.data.model.OrderType;
import tina.coffee.dozer.convertor.BigDecimalConverter;
import tina.coffee.rest.dto.OrderDTO;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class OrderFunction {

    public static OrderEntity initOrderEntity(DesktopEntity desktopEntity) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setDesktopEntity(desktopEntity);
        orderEntity.setStartTime(Calendar.getInstance());
        orderEntity.setOrderType(OrderType.OPEN);
        orderEntity.setActualPrice(BigDecimal.ZERO);
        orderEntity.setTotalPrice(BigDecimal.ZERO);
        orderEntity.setItems(Sets.newHashSet());
        return orderEntity;
    }

    public static List<OrderDTO> filterQualifiedAdjustmentOrder(List<OrderDTO> oriOrderDTOList) {
        oriOrderDTOList = removeOrderTotalPriceIsZero(oriOrderDTOList);
        oriOrderDTOList = removeOrderItemIsCanceled(oriOrderDTOList);
        return oriOrderDTOList;
    }

    public static List<OrderDTO> removeOrderTotalPriceIsZero(List<OrderDTO> oriOrderDTOList) {
        BigDecimalConverter converter = new BigDecimalConverter();
        return oriOrderDTOList.stream().filter(dto -> converter.convertFrom(dto.getTotalPrice()).compareTo(BigDecimal.ZERO)!=0).collect(Collectors.toList());
    }

    public static List<OrderDTO> removeOrderItemIsCanceled(List<OrderDTO> oriOrderDTOList) {
        for(OrderDTO orderDTO : oriOrderDTOList) {
            orderDTO.setItems(orderDTO.getItems().stream().filter(oi -> OrderItemStatus.valueOf(oi.getStatus()) != OrderItemStatus.CANCELED).collect(Collectors.toSet()));
        }
        return oriOrderDTOList;
    }



}
