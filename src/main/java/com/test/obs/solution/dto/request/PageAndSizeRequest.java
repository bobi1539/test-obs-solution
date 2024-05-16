package com.test.obs.solution.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PageAndSizeRequest {
    private int page;
    private int size;
}
