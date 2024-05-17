package com.test.obs.solution.controller;

import com.test.obs.solution.constant.Endpoint;
import com.test.obs.solution.dto.request.OrderRequest;
import com.test.obs.solution.dto.response.OrderResponse;
import com.test.obs.solution.service.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Endpoint.ORDER)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderResponse save(@RequestBody @Valid OrderRequest request) {
        return orderService.save(request);
    }

    @PutMapping("/{id}")
    public OrderResponse edit(
            @PathVariable Long id,
            @RequestBody @Valid OrderRequest request
    ) {
        return orderService.edit(id, request);
    }
}
