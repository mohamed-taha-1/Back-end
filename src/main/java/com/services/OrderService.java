package com.services;

import java.util.List;
import com.model.dto.OrderDto;

public interface OrderService {
	public List<OrderDto> findAllOrders();
	public void SaveNewOrder(OrderDto orderDto);
}
