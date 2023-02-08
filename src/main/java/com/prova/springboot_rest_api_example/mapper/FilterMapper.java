package com.prova.springboot_rest_api_example.mapper;

import org.mapstruct.Mapper;

import com.prova.springboot_rest_api_example.dto.FilterDTO;
import com.prova.springboot_rest_api_example.model.Filter;

@Mapper(componentModel = "spring")
public abstract class FilterMapper {

	public abstract Filter toModel(FilterDTO filterDTO);

}
