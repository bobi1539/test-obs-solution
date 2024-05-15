package com.test.obs.solution.helper;

import com.test.obs.solution.constant.GlobalMessage;
import com.test.obs.solution.dto.request.ItemSaveOrEditRequest;
import com.test.obs.solution.dto.response.ItemSaveOrEditResponse;
import com.test.obs.solution.entity.Item;
import com.test.obs.solution.exception.BusinessException;

public final class EntityHelper {

    private EntityHelper() {
        throw new BusinessException(GlobalMessage.CANNOT_INSTANCE_HELPER_CLASS);
    }

    public static ItemSaveOrEditResponse toItemSaveOrEditResponse(Item item) {
        return ItemSaveOrEditResponse.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .build();
    }

    public static Item toItem(ItemSaveOrEditRequest request) {
        return Item.builder()
                .name(request.getName())
                .price(request.getPrice())
                .build();
    }
}
