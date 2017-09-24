package tina.coffee.data.model;

import com.google.common.collect.Sets;

import java.util.Set;

public enum OrderItemStatus {
    OPEN,
    PROGRESS,
    DONE,
    CANCELED;

    public static Set<OrderItemStatus> operatorAllowUpdateStatus() {
        return Sets.newHashSet(OPEN, DONE, CANCELED);
    }

    public static Set<OrderItemStatus> chiefAllowUpdateStatus() {
        return Sets.newHashSet(OPEN, PROGRESS);
    }

    public static Set<String> allStatuses() {
        return Sets.newHashSet(
                OPEN.name(),
                PROGRESS.name(),
                DONE.name(),
                CANCELED.name()
        );
    }
}
