package com.test.obs.solution.service.impl;

import com.test.obs.solution.constant.GlobalMessage;
import com.test.obs.solution.dto.request.ItemRequest;
import com.test.obs.solution.dto.request.PageAndSizeRequest;
import com.test.obs.solution.dto.response.ItemResponse;
import com.test.obs.solution.entity.Inventory;
import com.test.obs.solution.entity.Item;
import com.test.obs.solution.exception.BusinessException;
import com.test.obs.solution.helper.EntityHelper;
import com.test.obs.solution.helper.Helper;
import com.test.obs.solution.repository.InventoryRepository;
import com.test.obs.solution.repository.ItemRepository;
import com.test.obs.solution.service.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final InventoryRepository inventoryRepository;

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
        long itemInInventory = inventoryRepository.countByItem(item);
        if (itemInInventory > 0) {
            throw new BusinessException(GlobalMessage.ITEM_CANNOT_DELETE);
        }
        itemRepository.delete(item);
    }

    @Override
    public ItemResponse getById(Long id, boolean showStock) {
        Item item = findItemById(id);
        ItemResponse response = EntityHelper.toItemResponse(item);
        if (showStock) {
            int stock = calculateStock(item);
            response.setStock(stock);
        }
        return response;
    }

    @Override
    public Page<ItemResponse> listWithPagination(boolean showStock, PageAndSizeRequest request) {
        request.setPage(request.getPage() - 1);
        Page<Item> items = itemRepository.findAll(PageRequest.of(request.getPage(), request.getSize()));
        return items.map(item -> ItemResponse.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .stock(showStock ? calculateStock(item) : 0)
                .build());
    }

    private Item findItemById(Long id) {
        return itemRepository.findById(id).orElseThrow(
                () -> new BusinessException(GlobalMessage.ITEM_NOT_EXIST)
        );
    }

    private int calculateStock(Item item) {
        List<Inventory> inventories = inventoryRepository.findByItem(item);
        return Helper.calculateStockItem(inventories);
    }
}
