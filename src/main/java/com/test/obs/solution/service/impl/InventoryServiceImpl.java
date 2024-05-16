package com.test.obs.solution.service.impl;

import com.test.obs.solution.constant.GlobalMessage;
import com.test.obs.solution.constant.InventoryType;
import com.test.obs.solution.dto.request.InventoryRequest;
import com.test.obs.solution.dto.response.InventoryResponse;
import com.test.obs.solution.entity.Inventory;
import com.test.obs.solution.entity.Item;
import com.test.obs.solution.exception.BusinessException;
import com.test.obs.solution.helper.EntityHelper;
import com.test.obs.solution.helper.Helper;
import com.test.obs.solution.repository.InventoryRepository;
import com.test.obs.solution.repository.ItemRepository;
import com.test.obs.solution.service.InventoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ItemRepository itemRepository;

    @Override
    public InventoryResponse save(InventoryRequest request) {
        Item item = findItemById(request.getItemId());
        checkStockItem(item, request);

        Inventory inventory = Inventory.builder()
                .item(item)
                .quantity(request.getQuantity())
                .inventoryType(request.getInventoryType())
                .build();
        Inventory inventorySaved = inventoryRepository.save(inventory);
        return EntityHelper.toInventoryResponse(inventorySaved);
    }

    private Item findItemById(Long id) {
        return itemRepository.findById(id).orElseThrow(
                () -> new BusinessException(GlobalMessage.ITEM_NOT_EXIST)
        );
    }

    private void checkStockItem(Item item, InventoryRequest request) {
        int prevStock = calculateStock(item);
        int currentStock;
        if (request.getInventoryType().equals(InventoryType.T)) {
            currentStock = prevStock + request.getQuantity();
        } else {
            currentStock = prevStock - request.getQuantity();
        }

        if (currentStock < 0) {
            throw new BusinessException(GlobalMessage.ITEM_STOCK_INSUFFICIENT);
        }
    }

    private int calculateStock(Item item) {
        List<Inventory> inventories = inventoryRepository.findByItem(item);
        return Helper.calculateStockItem(inventories);
    }
}
