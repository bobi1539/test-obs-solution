package com.test.obs.solution.helper;

import com.test.obs.solution.constant.GlobalMessage;
import com.test.obs.solution.constant.InventoryType;
import com.test.obs.solution.entity.Inventory;
import com.test.obs.solution.exception.BusinessException;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public final class Helper {

    private Helper() {
        throw new BusinessException(GlobalMessage.CANNOT_INSTANCE_HELPER_CLASS);
    }

    public static int calculateStockItem(List<Inventory> inventories) {
        AtomicInteger stock = new AtomicInteger();
        inventories.forEach(inventory -> {
            if (inventory.getInventoryType().equals(InventoryType.T)) {
                stock.addAndGet(inventory.getQuantity());
            } else {
                stock.addAndGet(-inventory.getQuantity());
            }
        });
        return stock.get();
    }
}
