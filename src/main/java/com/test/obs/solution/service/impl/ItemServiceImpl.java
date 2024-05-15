package com.test.obs.solution.service.impl;

import com.test.obs.solution.constant.GlobalMessage;
import com.test.obs.solution.dto.request.ItemSaveOrEditRequest;
import com.test.obs.solution.dto.response.ItemSaveOrEditResponse;
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
    public ItemSaveOrEditResponse save(ItemSaveOrEditRequest request) {
        Item item = EntityHelper.toItem(request);
        Item itemSaved = itemRepository.save(item);
        return EntityHelper.toItemSaveOrEditResponse(itemSaved);
    }

    @Override
    public ItemSaveOrEditResponse edit(Long id, ItemSaveOrEditRequest request) {
        Item item = findItemById(id);
        item.setName(request.getName());
        item.setPrice(request.getPrice());
        Item itemUpdated = itemRepository.save(item);
        return EntityHelper.toItemSaveOrEditResponse(itemUpdated);
    }

    @Override
    public void delete(Long id) {
        Item item = findItemById(id);
        itemRepository.delete(item);
    }

    private Item findItemById(Long id) {
        return itemRepository.findById(id).orElseThrow(
                () -> new BusinessException(GlobalMessage.DATA_NOT_EXIST)
        );
    }
}
