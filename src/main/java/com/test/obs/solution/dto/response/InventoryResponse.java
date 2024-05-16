package com.test.obs.solution.dto.response;

import com.test.obs.solution.constant.InventoryType;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class InventoryResponse {
    private Long id;
    private ItemResponse item;
    private Integer quantity;
    private InventoryType inventoryType;
}
