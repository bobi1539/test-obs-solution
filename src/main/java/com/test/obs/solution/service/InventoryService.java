package com.test.obs.solution.service;

import com.test.obs.solution.dto.request.InventoryRequest;
import com.test.obs.solution.dto.request.PageAndSizeRequest;
import com.test.obs.solution.dto.response.InventoryResponse;
import org.springframework.data.domain.Page;

public interface InventoryService {

    InventoryResponse save(InventoryRequest request);

    InventoryResponse edit(Long id, InventoryRequest request);

    void delete(Long id);

    InventoryResponse getById(Long id);

    Page<InventoryResponse> listWithPagination(PageAndSizeRequest request);
}
