package com.test.obs.solution.dto.request;

import com.test.obs.solution.constant.Constant;
import com.test.obs.solution.constant.InventoryType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class InventoryRequest {

    @NotNull(message = Constant.ITEM_ID_REQUIRED)
    private Long itemId;

    @NotNull(message = Constant.QUANTITY_REQUIRED)
    private Integer quantity;

    @NotNull(message = Constant.INVENTORY_TYPE_REQUIRED)
    private InventoryType inventoryType;
}
