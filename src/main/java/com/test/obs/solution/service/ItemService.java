package com.test.obs.solution.service;

import com.test.obs.solution.dto.request.ItemSaveRequest;
import com.test.obs.solution.dto.response.ItemSaveOrEditResponse;

public interface ItemService {
    ItemSaveOrEditResponse save(ItemSaveRequest request);
}
