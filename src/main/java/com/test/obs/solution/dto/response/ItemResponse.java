package com.test.obs.solution.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ItemResponse {
    private Long id;
    private String name;
    private Float price;
    private int stock;
}
