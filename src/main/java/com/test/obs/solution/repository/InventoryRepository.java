package com.test.obs.solution.repository;

import com.test.obs.solution.entity.Inventory;
import com.test.obs.solution.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    List<Inventory> findByItem(Item item);

    long countByItem(Item item);
}
