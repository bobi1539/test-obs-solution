package com.test.obs.solution.controller;

import com.test.obs.solution.constant.Endpoint;
import com.test.obs.solution.dto.request.InventoryRequest;
import com.test.obs.solution.dto.request.PageAndSizeRequest;
import com.test.obs.solution.dto.response.InventoryResponse;
import com.test.obs.solution.service.InventoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Endpoint.INVENTORY)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public InventoryResponse save(@RequestBody @Valid InventoryRequest request) {
        return inventoryService.save(request);
    }

    @PutMapping("/{id}")
    public InventoryResponse edit(
            @PathVariable Long id,
            @RequestBody @Valid InventoryRequest request
    ) {
        return inventoryService.edit(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        inventoryService.delete(id);
    }

    @GetMapping("/{id}")
    public InventoryResponse getById(@PathVariable Long id) {
        return inventoryService.getById(id);
    }

    @GetMapping
    public Page<InventoryResponse> listWithPagination(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageAndSizeRequest request = PageAndSizeRequest.builder()
                .page(page)
                .size(size)
                .build();
        return inventoryService.listWithPagination(request);
    }
}
