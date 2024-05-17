package com.test.obs.solution.service.impl;

import com.test.obs.solution.constant.GlobalMessage;
import com.test.obs.solution.dto.request.OrderRequest;
import com.test.obs.solution.dto.request.PageAndSizeRequest;
import com.test.obs.solution.dto.response.OrderResponse;
import com.test.obs.solution.entity.Item;
import com.test.obs.solution.entity.Order;
import com.test.obs.solution.exception.BusinessException;
import com.test.obs.solution.helper.EntityHelper;
import com.test.obs.solution.helper.Helper;
import com.test.obs.solution.repository.ItemRepository;
import com.test.obs.solution.repository.OrderRepository;
import com.test.obs.solution.service.ItemService;
import com.test.obs.solution.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final ItemService itemService;

    @Override
    public OrderResponse save(OrderRequest request) {
        Item item = findItemById(request.getItemId());
        checkItemStockWhenSave(item, request.getQuantity());
        String orderNumber = generateOrderNumber();
        Order order = Order.builder()
                .orderNumber(orderNumber)
                .item(item)
                .quantity(request.getQuantity())
                .build();
        order = orderRepository.save(order);
        return EntityHelper.toOrderResponse(order);
    }

    @Override
    public OrderResponse edit(Long id, OrderRequest request) {
        Order order = findOrderById(id);
        Item item = findItemById(request.getItemId());
        checkItemStockWhenEdit(item, request.getQuantity(), order);

        order.setItem(item);
        order.setQuantity(request.getQuantity());
        order = orderRepository.save(order);
        return EntityHelper.toOrderResponse(order);
    }

    @Override
    public void delete(Long id) {
        Order order = findOrderById(id);
        orderRepository.delete(order);
    }

    @Override
    public OrderResponse getById(Long id) {
        Order order = findOrderById(id);
        return EntityHelper.toOrderResponse(order);
    }

    @Override
    public Page<OrderResponse> listWithPagination(PageAndSizeRequest request) {
        request.setPage(Helper.getPage(request.getPage()));
        Page<Order> orders = orderRepository.findAll(PageRequest.of(request.getPage(), request.getSize()));
        return orders.map(EntityHelper::toOrderResponse);
    }

    private Item findItemById(Long id) {
        return itemRepository.findById(id).orElseThrow(
                () -> new BusinessException(GlobalMessage.ITEM_NOT_EXIST)
        );
    }

    private void checkItemStockWhenSave(Item item, int orderQuantity) {
        int stock = itemService.calculateStock(item);
        Helper.checkItemStock(stock - orderQuantity);
    }

    private void checkItemStockWhenEdit(Item item, int orderQuantity, Order order) {
        int stock = itemService.calculateStock(item) + order.getQuantity();
        Helper.checkItemStock(stock - orderQuantity);
    }

    private String generateOrderNumber() {
        Optional<Order> order = orderRepository.findFirstByOrderByOrderNumberDesc();
        if (order.isEmpty()) {
            return "O1";
        } else {
            String numberStr = order.get().getOrderNumber().substring(1);
            int number = Integer.parseInt(numberStr);
            return "O" + (number + 1);
        }
    }

    private Order findOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(
                () -> new BusinessException(GlobalMessage.ORDER_NOT_EXIST)
        );
    }
}
