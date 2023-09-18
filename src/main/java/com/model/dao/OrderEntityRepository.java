package com.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.model.OrderEntity;

@Repository
public interface OrderEntityRepository extends JpaRepository<OrderEntity, Long> {
}
