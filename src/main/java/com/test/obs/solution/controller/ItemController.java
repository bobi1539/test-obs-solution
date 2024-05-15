package com.test.obs.solution.controller;

import com.test.obs.solution.constant.Endpoint;
import com.test.obs.solution.dto.request.ItemSaveOrEditRequest;
import com.test.obs.solution.dto.response.ItemSaveOrEditResponse;
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
    public ItemSaveOrEditResponse save(
            @RequestBody @Valid ItemSaveOrEditRequest request
    ) {
        return itemService.save(request);
    }

    @PutMapping("/{id}")
    public ItemSaveOrEditResponse edit(
            @PathVariable Long id,
            @RequestBody @Valid ItemSaveOrEditRequest request
    ) {
        return itemService.edit(id, request);
    }
}
