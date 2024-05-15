package com.test.obs.solution.service.impl;

import com.test.obs.solution.dto.request.ItemSaveRequest;
import com.test.obs.solution.dto.response.ItemSaveOrEditResponse;
import com.test.obs.solution.entity.Item;
import com.test.obs.solution.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

    @InjectMocks
    private ItemServiceImpl itemService;

    @Mock
    private ItemRepository itemRepository;

    @Test
    void testSaveSuccess() {
        ItemSaveRequest request = createSaveRequest();
        when(itemRepository.save(any())).thenReturn(itemFromSaveRequest(request));

        assertDoesNotThrow(() -> {
            ItemSaveOrEditResponse response = itemService.save(request);
            assertEquals("Item 1", response.getName());
            assertEquals(10_000f, response.getPrice());
        });

        verify(itemRepository, times(1)).save(any());
    }

    private ItemSaveRequest createSaveRequest() {
        return ItemSaveRequest.builder()
                .name("Item 1")
                .price(10_000f)
                .build();
    }

    private Item itemFromSaveRequest(ItemSaveRequest request) {
        return Item.builder()
                .id(1L)
                .name(request.getName())
                .price(request.getPrice())
                .build();
    }
}