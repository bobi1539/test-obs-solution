package com.test.obs.solution.service.impl;

import com.test.obs.solution.dto.request.OrderRequest;
import com.test.obs.solution.dto.request.PageAndSizeRequest;
import com.test.obs.solution.dto.response.OrderResponse;
import com.test.obs.solution.entity.Item;
import com.test.obs.solution.entity.Order;
import com.test.obs.solution.repository.ItemRepository;
import com.test.obs.solution.repository.OrderRepository;
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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ItemService itemService;

    @Test
    void testSaveSuccess() {
        OrderRequest request = buildOrderRequest();
        Item item = buildItem();
        Order order = buildOrder(item);

        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(itemService.calculateStock(item)).thenReturn(10);
        when(orderRepository.findFirstByOrderByOrderNumberDesc()).thenReturn(Optional.empty());
        when(orderRepository.save(any())).thenReturn(order);

        assertDoesNotThrow(() -> {
            OrderResponse response = orderService.save(request);
            assertEquals(1L, response.getId());
            assertEquals("O1", response.getOrderNumber());
            assertEquals(10, response.getQuantity());
        });

        verify(itemRepository, times(1)).findById(1L);
        verify(itemService, times(1)).calculateStock(item);
        verify(orderRepository, times(1)).findFirstByOrderByOrderNumberDesc();
        verify(orderRepository, times(1)).save(any());
    }

    @Test
    void testEditSuccess() {
        OrderRequest request = buildOrderRequest();
        Item item = buildItem();
        Order order = buildOrder(item);

        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(itemService.calculateStock(item)).thenReturn(10);
        when(orderRepository.save(any())).thenReturn(order);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        assertDoesNotThrow(() -> {
            OrderResponse response = orderService.edit(1L, request);
            assertEquals(1L, response.getId());
            assertEquals("O1", response.getOrderNumber());
            assertEquals(10, response.getQuantity());
        });

        verify(itemRepository, times(1)).findById(1L);
        verify(itemService, times(1)).calculateStock(item);
        verify(orderRepository, times(1)).save(any());
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteSuccess() {
        Order order = buildOrder(buildItem());
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        assertDoesNotThrow(() -> orderService.delete(1L));

        verify(orderRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).delete(order);
    }

    @Test
    void testGetByIdSuccess() {
        Order order = buildOrder(buildItem());
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        assertDoesNotThrow(() -> {
            OrderResponse response = orderService.getById(1L);
            assertEquals(1L, response.getId());
            assertEquals(10, response.getQuantity());
        });

        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void testListWithPaginationSuccess() {
        Item item = buildItem();
        Order order1 = buildOrder(item);
        Order order2 = buildOrder(item);
        List<Order> orders = List.of(order1, order2);

        PageAndSizeRequest request = PageAndSizeRequest.builder().page(1).size(10).build();
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());
        when(orderRepository.findAll(pageable)).thenReturn(new PageImpl<>(orders));

        assertDoesNotThrow(() -> {
            Page<OrderResponse> responses = orderService.listWithPagination(request);
            assertEquals(2, responses.getSize());
        });

        verify(orderRepository, times(1)).findAll(pageable);
    }

    private OrderRequest buildOrderRequest() {
        return OrderRequest.builder()
                .itemId(1L)
                .quantity(10)
                .build();
    }

    private Item buildItem() {
        return Item.builder()
                .id(1L)
                .name("Item 1")
                .price(2_000F)
                .build();
    }

    private Order buildOrder(Item item) {
        return Order.builder()
                .id(1L)
                .item(item)
                .orderNumber("O1")
                .quantity(10)
                .build();
    }
}