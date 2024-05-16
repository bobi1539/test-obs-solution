package com.test.obs.solution.service.impl;

import com.test.obs.solution.constant.Constant;
import com.test.obs.solution.dto.request.ItemRequest;
import com.test.obs.solution.dto.request.PageAndSizeRequest;
import com.test.obs.solution.dto.response.ItemResponse;
import com.test.obs.solution.entity.Item;
import com.test.obs.solution.exception.BusinessException;
import com.test.obs.solution.repository.ItemRepository;
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
class ItemServiceImplTest {

    @InjectMocks
    private ItemServiceImpl itemService;

    @Mock
    private ItemRepository itemRepository;

    @Test
    void testSaveSuccess() {
        ItemRequest request = saveRequest();
        when(itemRepository.save(any())).thenReturn(itemFromRequest(request));

        assertDoesNotThrow(() -> {
            ItemResponse response = itemService.save(request);
            assertEquals(request.getName(), response.getName());
            assertEquals(request.getPrice(), response.getPrice());
        });

        verify(itemRepository, times(1)).save(any());
    }

    @Test
    void testEditSuccess() {
        Long id = 1L;
        ItemRequest request = editRequest();

        when(itemRepository.findById(any())).thenReturn(Optional.of(itemFromRequest(request)));
        when(itemRepository.save(any())).thenReturn(itemFromRequest(request));

        assertDoesNotThrow(() -> {
            ItemResponse response = itemService.edit(id, request);
            assertEquals(request.getName(), response.getName());
            assertEquals(request.getPrice(), response.getPrice());
        });

        verify(itemRepository, times(1)).findById(any());
        verify(itemRepository, times(1)).save(any());
    }

    @Test
    void testEditFailed() {
        Long id = 1L;
        ItemRequest request = editRequest();
        when(itemRepository.findById(any())).thenReturn(Optional.empty());

        BusinessException e = assertThrows(BusinessException.class, () -> itemService.edit(id, request));
        assertEquals(Constant.ITEM_NOT_EXIST, e.getMessage());

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
        assertEquals(Constant.ITEM_NOT_EXIST, e.getMessage());
    }

    @Test
    void testGetByIdSuccess() {
        Long id = 1L;
        Item item = buildItem(id);
        when(itemRepository.findById(id)).thenReturn(Optional.of(item));

        assertDoesNotThrow(() -> {
            ItemResponse response = itemService.getById(id, false);
            assertEquals(item.getId(), response.getId());
            assertEquals(item.getName(), response.getName());
            assertEquals(item.getPrice(), response.getPrice());
        });

        verify(itemRepository, times(1)).findById(id);
    }

    @Test
    void testListWithPaginationSuccess() {
        PageAndSizeRequest request = PageAndSizeRequest.builder().page(1).size(10).build();
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());

        Item item1 = buildItem(1L);
        Item item2 = buildItem(2L);
        List<Item> items = List.of(item1, item2);

        when(itemRepository.findAll(pageable)).thenReturn(new PageImpl<>(items));

        assertDoesNotThrow(() -> {
            Page<ItemResponse> responses = itemService.listWithPagination(false, request);
            assertEquals(2, responses.getSize());
        });

        verify(itemRepository, times(1)).findAll(pageable);
    }

    private ItemRequest saveRequest() {
        return ItemRequest.builder()
                .name("Item 1")
                .price(10_000F)
                .build();
    }

    private ItemRequest editRequest() {
        return ItemRequest.builder()
                .name("Item 1000")
                .price(50_000f)
                .build();
    }

    private Item itemFromRequest(ItemRequest request) {
        return Item.builder()
                .id(1L)
                .name(request.getName())
                .price(request.getPrice())
                .build();
    }

    private Item buildItem(long id) {
        return Item.builder()
                .id(id)
                .name("Item 1")
                .price(1_000F)
                .build();
    }
}