package com.test.obs.solution.repository;

import com.test.obs.solution.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
