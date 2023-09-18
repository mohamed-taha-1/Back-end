package com.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.model.dto.OrderDto;
import com.services.impl.OrderServiceImplementation;
import jakarta.validation.Valid;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/orders")
public class OrdersContoller {
	@Autowired
	private OrderServiceImplementation orderService;

	@GetMapping(value = "/all")
	public Flux<OrderDto> findAllOrders() {
		return Flux.fromIterable(orderService.findAllOrders());
	}

	@PostMapping("/save")
	public ResponseEntity<?> saveuser(@Valid @RequestBody OrderDto orderDto) {
		orderService.SaveNewOrder(orderDto);
		return ResponseEntity.ok().build();
	}
}
