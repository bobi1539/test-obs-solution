package com.test.obs.solution.service.impl;

import com.test.obs.solution.constant.GlobalMessage;
import com.test.obs.solution.constant.InventoryType;
import com.test.obs.solution.dto.request.InventoryRequest;
import com.test.obs.solution.dto.request.PageAndSizeRequest;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        checkStockItemWhenSave(item, request);

        Inventory inventory = Inventory.builder()
                .item(item)
                .quantity(request.getQuantity())
                .inventoryType(request.getInventoryType())
                .build();
        Inventory inventorySaved = inventoryRepository.save(inventory);
        return EntityHelper.toInventoryResponse(inventorySaved);
    }

    @Override
    public InventoryResponse edit(Long id, InventoryRequest request) {
        Inventory inventory = findInventoryById(id);
        Item item = findItemById(request.getItemId());
        checkStockItemWhenEdit(item, inventory, request);

        inventory.setItem(item);
        inventory.setQuantity(request.getQuantity());
        inventory.setInventoryType(request.getInventoryType());
        Inventory inventoryUpdated = inventoryRepository.save(inventory);
        return EntityHelper.toInventoryResponse(inventoryUpdated);
    }

    @Override
    public void delete(Long id) {
        Inventory inventory = findInventoryById(id);
        inventoryRepository.delete(inventory);
    }

    @Override
    public InventoryResponse getById(Long id) {
        Inventory inventory = findInventoryById(id);
        return EntityHelper.toInventoryResponse(inventory);
    }

    @Override
    public Page<InventoryResponse> listWithPagination(PageAndSizeRequest request) {
        request.setPage(request.getPage() - 1);
        Page<Inventory> inventories = inventoryRepository.findAll(
                PageRequest.of(request.getPage(), request.getSize())
        );
        return inventories.map(inventory -> InventoryResponse.builder()
                .id(inventory.getId())
                .item(EntityHelper.toItemResponse(inventory.getItem()))
                .quantity(inventory.getQuantity())
                .inventoryType(inventory.getInventoryType())
                .build());
    }

    private Item findItemById(Long id) {
        return itemRepository.findById(id).orElseThrow(
                () -> new BusinessException(GlobalMessage.ITEM_NOT_EXIST)
        );
    }

    private void checkStockItemWhenSave(Item item, InventoryRequest request) {
        int prevStock = calculateStock(item);
        int currentStock;
        if (request.getInventoryType().equals(InventoryType.T)) {
            currentStock = prevStock + request.getQuantity();
        } else {
            currentStock = prevStock - request.getQuantity();
        }
        validateStock(currentStock);
    }

    private int calculateStock(Item item) {
        List<Inventory> inventories = inventoryRepository.findByItem(item);
        return Helper.calculateStockItem(inventories);
    }

    private Inventory findInventoryById(Long id) {
        return inventoryRepository.findById(id).orElseThrow(
                () -> new BusinessException(GlobalMessage.INVENTORY_NOT_EXIST)
        );
    }

    private void checkStockItemWhenEdit(Item item, Inventory inventory, InventoryRequest request) {
        int prevStock = calculateStock(item);
        int currentStock;
        if (request.getInventoryType().equals(InventoryType.T)) {
            currentStock = prevStock + request.getQuantity();
        } else {
            currentStock = prevStock - request.getQuantity() + inventory.getQuantity();
        }
        validateStock(currentStock);
    }

    private void validateStock(int stock) {
        if (stock < 0) {
            throw new BusinessException(GlobalMessage.ITEM_STOCK_INSUFFICIENT);
        }
    }
}
