package com.test.obs.solution.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ItemSaveOrEditResponse {
    private Long id;
    private String name;
    private Float price;
}
