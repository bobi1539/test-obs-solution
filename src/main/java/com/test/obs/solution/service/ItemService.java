package com.test.obs.solution.service;

import com.test.obs.solution.dto.request.ItemRequest;
import com.test.obs.solution.dto.response.ItemResponse;

public interface ItemService {

    ItemResponse save(ItemRequest request);

    ItemResponse edit(Long id, ItemRequest request);

    void delete(Long id);

    ItemResponse getById(Long id, boolean showStock);
}
