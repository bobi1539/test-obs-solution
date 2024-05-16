package com.test.obs.solution.helper;

import com.test.obs.solution.constant.GlobalMessage;
import com.test.obs.solution.dto.request.ItemRequest;
import com.test.obs.solution.dto.response.InventoryResponse;
import com.test.obs.solution.dto.response.ItemResponse;
import com.test.obs.solution.entity.Inventory;
import com.test.obs.solution.entity.Item;
import com.test.obs.solution.exception.BusinessException;

public final class EntityHelper {

    private EntityHelper() {
        throw new BusinessException(GlobalMessage.CANNOT_INSTANCE_HELPER_CLASS);
    }

    public static ItemResponse toItemResponse(Item item) {
        return ItemResponse.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .build();
    }

    public static InventoryResponse toInventoryResponse(Inventory inventory) {
        return InventoryResponse.builder()
                .id(inventory.getId())
                .item(toItemResponse(inventory.getItem()))
                .quantity(inventory.getQuantity())
                .inventoryType(inventory.getInventoryType())
                .build();
    }
}
