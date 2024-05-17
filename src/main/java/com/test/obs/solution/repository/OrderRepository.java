package com.test.obs.solution.repository;

import com.test.obs.solution.entity.Item;
import com.test.obs.solution.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByItem(Item item);

    Optional<Order> findFirstByOrderByOrderNumberDesc();

    long countByItem(Item item);
}
