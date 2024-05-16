package com.test.obs.solution.service.impl;

import com.test.obs.solution.constant.GlobalMessage;
import com.test.obs.solution.dto.request.ItemRequest;
import com.test.obs.solution.dto.response.ItemResponse;
import com.test.obs.solution.entity.Item;
import com.test.obs.solution.exception.BusinessException;
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
    public ItemResponse save(ItemRequest request) {
        Item item = Item.builder()
                .name(request.getName())
                .price(request.getPrice())
                .build();
        Item itemSaved = itemRepository.save(item);
        return EntityHelper.toItemResponse(itemSaved);
    }

    @Override
    public ItemResponse edit(Long id, ItemRequest request) {
        Item item = findItemById(id);
        item.setName(request.getName());
        item.setPrice(request.getPrice());
        Item itemUpdated = itemRepository.save(item);
        return EntityHelper.toItemResponse(itemUpdated);
    }

    @Override
    public void delete(Long id) {
        Item item = findItemById(id);
        itemRepository.delete(item);
    }

    private Item findItemById(Long id) {
        return itemRepository.findById(id).orElseThrow(
                () -> new BusinessException(GlobalMessage.ITEM_NOT_EXIST)
        );
    }
}
