package com.test.obs.solution.service.impl;

import com.test.obs.solution.constant.Constant;
import com.test.obs.solution.dto.request.ItemSaveOrEditRequest;
import com.test.obs.solution.dto.response.ItemSaveOrEditResponse;
import com.test.obs.solution.entity.Item;
import com.test.obs.solution.exception.BusinessException;
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
class ItemServiceImplTest {

    @InjectMocks
    private ItemServiceImpl itemService;

    @Mock
    private ItemRepository itemRepository;

    @Test
    void testSaveSuccess() {
        ItemSaveOrEditRequest request = saveRequest();
        when(itemRepository.save(any())).thenReturn(itemFromRequest(request));

        assertDoesNotThrow(() -> {
            ItemSaveOrEditResponse response = itemService.save(request);
            assertEquals(request.getName(), response.getName());
            assertEquals(request.getPrice(), response.getPrice());
        });

        verify(itemRepository, times(1)).save(any());
    }

    @Test
    void testEditSuccess() {
        Long id = 1L;
        ItemSaveOrEditRequest request = editRequest();

        when(itemRepository.findById(any())).thenReturn(Optional.of(itemFromRequest(request)));
        when(itemRepository.save(any())).thenReturn(itemFromRequest(request));

        assertDoesNotThrow(() -> {
            ItemSaveOrEditResponse response = itemService.edit(id, request);
            assertEquals(request.getName(), response.getName());
            assertEquals(request.getPrice(), response.getPrice());
        });

        verify(itemRepository, times(1)).findById(any());
        verify(itemRepository, times(1)).save(any());
    }

    @Test
    void testEditFailed() {
        Long id = 1L;
        ItemSaveOrEditRequest request = editRequest();
        when(itemRepository.findById(any())).thenReturn(Optional.empty());

        BusinessException e = assertThrows(BusinessException.class, () -> itemService.edit(id, request));
        assertEquals(Constant.DATA_NOT_EXIST, e.getMessage());

        verify(itemRepository, times(1)).findById(any());
    }

    @Test
    void testDeleteSuccess() {
        Long id = 1L;

        Item itemForDelete = Item.builder()
                .id(id)
                .name("item 1")
                .price(1000f)
                .build();
        when(itemRepository.findById(id)).thenReturn(Optional.of(itemForDelete));

        assertDoesNotThrow(() -> itemService.delete(id));

        verify(itemRepository, times(1)).findById(id);
        verify(itemRepository, times(1)).delete(itemForDelete);
    }

    @Test
    void testDeleteFailed() {
        Long id = 1L;
        when(itemRepository.findById(id)).thenReturn(Optional.empty());

        BusinessException e = assertThrows(BusinessException.class, () -> itemService.delete(id));
        assertEquals(Constant.DATA_NOT_EXIST, e.getMessage());
    }

    private ItemSaveOrEditRequest saveRequest() {
        return ItemSaveOrEditRequest.builder()
                .name("Item 1")
                .price(10_000f)
                .build();
    }

    private ItemSaveOrEditRequest editRequest() {
        return ItemSaveOrEditRequest.builder()
                .name("Item 1000")
                .price(50_000f)
                .build();
    }

    private Item itemFromRequest(ItemSaveOrEditRequest request) {
        return Item.builder()
                .id(1L)
                .name(request.getName())
                .price(request.getPrice())
                .build();
    }
}