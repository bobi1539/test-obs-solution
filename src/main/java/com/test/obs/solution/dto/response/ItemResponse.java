package com.test.obs.solution.dto.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ItemResponse {
    private Long id;
    private String name;
    private Float price;
    private List<InventoryResponse> inventories;
}
