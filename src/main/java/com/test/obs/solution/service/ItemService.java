package com.test.obs.solution.service;

import com.test.obs.solution.dto.request.ItemSaveOrEditRequest;
import com.test.obs.solution.dto.response.ItemSaveOrEditResponse;

public interface ItemService {

    ItemSaveOrEditResponse save(ItemSaveOrEditRequest request);

    ItemSaveOrEditResponse edit(Long id, ItemSaveOrEditRequest request);
}
