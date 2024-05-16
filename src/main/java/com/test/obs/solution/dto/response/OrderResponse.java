package com.test.obs.solution.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderResponse {
    private Long id;
    private String orderNumber;
    private ItemResponse item;
    private Integer quantity;
}
