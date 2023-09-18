package com.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.model.OrderEntity;
import com.model.UserEntity;
import com.model.dao.OrderEntityRepository;
import com.model.dto.OrderDto;
import com.model.dto.UserDto;
import com.model.dto.mappers.OrderEntityMappers;
import com.services.OrderService;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;

@Service
public class OrderServiceImplementation implements OrderService {

	private final OrderEntityRepository orderRepo;
	private final OrderEntityMappers orderMapper;

	public OrderServiceImplementation(OrderEntityRepository orderRepo, OrderEntityMappers orderMapper) {
		super();
		this.orderRepo = orderRepo;
		this.orderMapper = orderMapper;
	}

	@PostConstruct
	public void insertDummyOrders() {

		if (orderRepo.count() <= 4) {
			OrderEntity order1 = new OrderEntity();
			order1.setQuantity(2);
			order1.setPrice(98.55);

			OrderEntity order2 = new OrderEntity();
			order2.setQuantity(3);
			order2.setPrice(150.55);

			OrderEntity order3 = new OrderEntity();
			order3.setQuantity(4);
			order1.setPrice(200.42);

			OrderEntity order4 = new OrderEntity();
			order1.setQuantity(7);
			order1.setPrice(220.55);

			OrderEntity[] allOrders = { order1, order2, order3, order4 };
			for (OrderEntity orderIndex : allOrders) {
				orderRepo.save(orderIndex);
			}
		}
	}

	@Override
	public List<OrderDto> findAllOrders() {
		List<OrderEntity> listOfUsers = orderRepo.findAll();
		List<OrderDto> listOfOrdersDtos = new ArrayList<>();
		for (OrderEntity order : listOfUsers) {
			listOfOrdersDtos.add(orderMapper.entityToDto(order));
		}

		return listOfOrdersDtos;
	}

	@Override
	@Transactional
	@Async
	public void SaveNewOrder(OrderDto orderDto) {
		OrderEntity order = orderMapper.dtoToEntity(orderDto);
		orderRepo.save(order);
	}

}
