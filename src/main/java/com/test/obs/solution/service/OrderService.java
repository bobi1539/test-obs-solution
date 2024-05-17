package com.test.obs.solution.service;

import com.test.obs.solution.dto.request.OrderRequest;
import com.test.obs.solution.dto.request.PageAndSizeRequest;
import com.test.obs.solution.dto.response.OrderResponse;
import org.springframework.data.domain.Page;

public interface OrderService {

    OrderResponse save(OrderRequest request);

    OrderResponse edit(Long id, OrderRequest request);

    void delete(Long id);

    OrderResponse getById(Long id);

    Page<OrderResponse> listWithPagination(PageAndSizeRequest request);
}
