package com.test.obs.solution.service.impl;

import com.test.obs.solution.constant.Constant;
import com.test.obs.solution.constant.InventoryType;
import com.test.obs.solution.dto.request.InventoryRequest;
import com.test.obs.solution.dto.response.InventoryResponse;
import com.test.obs.solution.entity.Inventory;
import com.test.obs.solution.entity.Item;
import com.test.obs.solution.exception.BusinessException;
import com.test.obs.solution.repository.InventoryRepository;
import com.test.obs.solution.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceImplTest {

    @InjectMocks
    private InventoryServiceImpl inventoryService;

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private ItemRepository itemRepository;

    @Test
    void testSaveSuccess() {
        InventoryRequest request = buildRequest();
        Item item = buildItem();
        Inventory inventory = buildInventory(item, request);

        when(itemRepository.findById(item.getId())).thenReturn(Optional.of(item));
        when(inventoryRepository.save(any())).thenReturn(inventory);

        assertDoesNotThrow(() -> {
            InventoryResponse response = inventoryService.save(request);
            assertEquals(request.getItemId(), response.getItem().getId());
            assertEquals(request.getQuantity(), response.getQuantity());
            assertEquals(request.getInventoryType(), response.getInventoryType());
        });

        verify(itemRepository, times(1)).findById(item.getId());
        verify(inventoryRepository, times(1)).save(any());
    }

    @Test
    void testSaveFailed() {
        InventoryRequest request = buildRequest();
        Item item = buildItem();

        when(itemRepository.findById(item.getId())).thenReturn(Optional.empty());

        BusinessException e = assertThrows(BusinessException.class, () -> inventoryService.save(request));
        assertEquals(Constant.ITEM_NOT_EXIST, e.getMessage());

        verify(itemRepository, times(1)).findById(item.getId());
    }

    private InventoryRequest buildRequest() {
        return InventoryRequest.builder()
                .itemId(1L)
                .quantity(10)
                .inventoryType(InventoryType.T)
                .build();
    }

    private Item buildItem() {
        return Item.builder()
                .id(1L)
                .name("item 1")
                .price(10_000F)
                .build();
    }

    private Inventory buildInventory(Item item, InventoryRequest request) {
        return Inventory.builder()
                .id(1L)
                .item(item)
                .quantity(request.getQuantity())
                .inventoryType(request.getInventoryType())
                .build();
    }
}