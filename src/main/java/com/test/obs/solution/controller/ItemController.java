package com.test.obs.solution.controller;

import com.test.obs.solution.constant.Endpoint;
import com.test.obs.solution.dto.request.ItemRequest;
import com.test.obs.solution.dto.response.ItemResponse;
import com.test.obs.solution.service.ItemService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Endpoint.ITEM)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ItemResponse save(
            @RequestBody @Valid ItemRequest request
    ) {
        return itemService.save(request);
    }

    @PutMapping("/{id}")
    public ItemResponse edit(
            @PathVariable Long id,
            @RequestBody @Valid ItemRequest request
    ) {
        return itemService.edit(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        itemService.delete(id);
    }
}
