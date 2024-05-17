package com.test.obs.solution.service;

import com.test.obs.solution.dto.request.OrderRequest;
import com.test.obs.solution.dto.response.OrderResponse;

public interface OrderService {

    OrderResponse save(OrderRequest request);

    OrderResponse edit(Long id, OrderRequest request);
}
