package com.test.obs.solution.service;

import com.test.obs.solution.dto.request.InventoryRequest;
import com.test.obs.solution.dto.response.InventoryResponse;

public interface InventoryService {

    InventoryResponse save(InventoryRequest request);

    InventoryResponse edit(Long id, InventoryRequest request);
}
