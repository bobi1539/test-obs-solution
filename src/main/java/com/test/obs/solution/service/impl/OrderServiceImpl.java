package com.test.obs.solution.service.impl;

import com.test.obs.solution.constant.GlobalMessage;
import com.test.obs.solution.dto.request.OrderRequest;
import com.test.obs.solution.dto.response.OrderResponse;
import com.test.obs.solution.entity.Item;
import com.test.obs.solution.entity.Order;
import com.test.obs.solution.exception.BusinessException;
import com.test.obs.solution.helper.EntityHelper;
import com.test.obs.solution.repository.ItemRepository;
import com.test.obs.solution.repository.OrderRepository;
import com.test.obs.solution.service.ItemService;
import com.test.obs.solution.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
        checkItemStock(item, request.getQuantity());
        String orderNumber = generateOrderNumber();
        Order order = Order.builder()
                .orderNumber(orderNumber)
                .item(item)
                .quantity(request.getQuantity())
                .build();
        Order orderSaved = orderRepository.save(order);
        return EntityHelper.toOrderResponse(orderSaved);
    }

    private Item findItemById(Long id) {
        return itemRepository.findById(id).orElseThrow(
                () -> new BusinessException(GlobalMessage.ITEM_NOT_EXIST)
        );
    }

    private void checkItemStock(Item item, int orderQuantity) {
        int stock = itemService.calculateStock(item);
        if (stock - orderQuantity < 0) {
            throw new BusinessException(GlobalMessage.ITEM_STOCK_INSUFFICIENT);
        }
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
}
