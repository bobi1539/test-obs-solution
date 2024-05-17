package com.test.obs.solution.service.impl;

import com.test.obs.solution.constant.Constant;
import com.test.obs.solution.constant.InventoryType;
import com.test.obs.solution.dto.request.InventoryRequest;
import com.test.obs.solution.dto.request.PageAndSizeRequest;
import com.test.obs.solution.dto.response.InventoryResponse;
import com.test.obs.solution.entity.Inventory;
import com.test.obs.solution.entity.Item;
import com.test.obs.solution.exception.BusinessException;
import com.test.obs.solution.repository.InventoryRepository;
import com.test.obs.solution.repository.ItemRepository;
import com.test.obs.solution.service.ItemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
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

    @Mock
    private ItemService itemService;

    @Test
    void testSaveSuccess() {
        InventoryRequest request = buildRequest();
        Item item = buildItem();
        Inventory inventory = buildInventory(item, request);

        when(itemRepository.findById(item.getId())).thenReturn(Optional.of(item));
        when(inventoryRepository.save(any())).thenReturn(inventory);
        when(itemService.calculateStock(any())).thenReturn(10);

        assertDoesNotThrow(() -> {
            InventoryResponse response = inventoryService.save(request);
            assertEquals(request.getItemId(), response.getItem().getId());
            assertEquals(request.getQuantity(), response.getQuantity());
            assertEquals(request.getInventoryType(), response.getInventoryType());
        });

        verify(itemRepository, times(1)).findById(item.getId());
        verify(inventoryRepository, times(1)).save(any());
        verify(itemService, times(1)).calculateStock(any());
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

    @Test
    void testEditSuccess() {
        Long id = 1L;
        InventoryRequest request = buildRequest();
        Item item = buildItem();
        Inventory inventory = buildInventory(item, request);

        when(itemRepository.findById(item.getId())).thenReturn(Optional.of(item));
        when(inventoryRepository.save(any())).thenReturn(inventory);
        when(itemService.calculateStock(any())).thenReturn(10);
        when(inventoryRepository.findById(id)).thenReturn(Optional.of(inventory));

        assertDoesNotThrow(() -> {
            InventoryResponse response = inventoryService.edit(id, request);
            assertEquals(request.getItemId(), response.getItem().getId());
            assertEquals(request.getQuantity(), response.getQuantity());
            assertEquals(request.getInventoryType(), response.getInventoryType());
        });

        verify(itemRepository, times(1)).findById(item.getId());
        verify(inventoryRepository, times(1)).save(any());
        verify(itemService, times(1)).calculateStock(any());
        verify(inventoryRepository, times(1)).findById(id);
    }

    @Test
    void testDeleteSuccess() {
        Long id = 1L;
        Inventory inventory = buildInventory(buildItem(), buildRequest());

        when(inventoryRepository.findById(id)).thenReturn(Optional.of(inventory));

        assertDoesNotThrow(() -> inventoryService.delete(id));

        verify(inventoryRepository, times(1)).findById(id);
        verify(inventoryRepository, times(1)).delete(inventory);
    }

    @Test
    void testGetByIdSuccess() {
        Long id = 1L;
        Inventory inventory = buildInventory(buildItem(), buildRequest());

        when(inventoryRepository.findById(id)).thenReturn(Optional.of(inventory));

        assertDoesNotThrow(() -> {
            InventoryResponse response = inventoryService.getById(id);
            assertEquals(1L, response.getId());
            assertEquals(10, response.getQuantity());
            assertEquals(InventoryType.T, response.getInventoryType());
        });

        verify(inventoryRepository, times(1)).findById(id);
    }

    @Test
    void testListWithPagination() {
        Item item = buildItem();
        InventoryRequest request = buildRequest();
        Inventory inventory1 = buildInventory(item, request);
        Inventory inventory2 = buildInventory(item, request);
        List<Inventory> inventories = List.of(inventory1, inventory2);

        PageAndSizeRequest page = PageAndSizeRequest.builder().page(1).size(10).build();
        Pageable pageable = PageRequest.of(page.getPage() - 1, page.getSize());

        when(inventoryRepository.findAll(pageable)).thenReturn(new PageImpl<>(inventories));

        assertDoesNotThrow(() -> {
            Page<InventoryResponse> responses = inventoryService.listWithPagination(page);
            assertEquals(2, responses.getSize());
        });

        verify(inventoryRepository, times(1)).findAll(pageable);
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