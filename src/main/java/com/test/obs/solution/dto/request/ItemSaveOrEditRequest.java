package com.test.obs.solution.dto.request;

import com.test.obs.solution.constant.Constant;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ItemSaveOrEditRequest {

    @NotNull(message = Constant.NAME_REQUIRED)
    @NotEmpty(message = Constant.NAME_REQUIRED)
    private String name;

    @NotNull(message = Constant.PRICE_REQUIRED)
    private Float price;
}
