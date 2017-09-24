package tina.coffee.system.exceptions.menuitem;

import tina.coffee.data.model.OrderEntity;
import tina.coffee.data.model.OrderItemEntity;
import tina.coffee.system.exceptions.EntityBusinessException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static tina.coffee.system.config.SystemConstant.LONG_DATE_FORMAT;

public class MenuItemAssociateOrdersException extends EntityBusinessException {

    private static final String ERROR_DELETE_WITH_ORDERS = "Menu item has orders accosiated, orders happened time: %s";

    public MenuItemAssociateOrdersException(String detail) {
        super(String.format(ERROR_DELETE_WITH_ORDERS, detail));
    }

    public static Supplier<MenuItemAssociateOrdersException> newMenuItemBusinessException(String detail) {
        return () -> new MenuItemAssociateOrdersException(detail);
    }

}
