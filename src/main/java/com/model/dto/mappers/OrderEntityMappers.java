package com.model.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import com.model.OrderEntity;
import com.model.dto.OrderDto;

@Mapper(componentModel = "spring")
public interface OrderEntityMappers {

	OrderEntityMappers INSTANCE = Mappers.getMapper(OrderEntityMappers.class);

	OrderDto entityToDto(OrderEntity entity);

	@Mapping(target = "id", ignore = true)
	OrderEntity dtoToEntity(OrderDto order);
}
