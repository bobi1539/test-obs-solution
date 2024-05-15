package com.test.obs.solution.service.impl;

import com.test.obs.solution.dto.request.ItemSaveRequest;
import com.test.obs.solution.dto.response.ItemSaveOrEditResponse;
import com.test.obs.solution.entity.Item;
import com.test.obs.solution.helper.EntityHelper;
import com.test.obs.solution.repository.ItemRepository;
import com.test.obs.solution.service.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Override
    public ItemSaveOrEditResponse save(ItemSaveRequest request) {
        Item item = EntityHelper.toItem(request);
        Item itemSaved = itemRepository.save(item);
        return EntityHelper.toItemCreateOrUpdateResponse(itemSaved);
    }
}
