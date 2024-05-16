package com.test.obs.solution.controller;

import com.test.obs.solution.constant.Endpoint;
import com.test.obs.solution.dto.request.InventoryRequest;
import com.test.obs.solution.dto.response.InventoryResponse;
import com.test.obs.solution.service.InventoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
}
