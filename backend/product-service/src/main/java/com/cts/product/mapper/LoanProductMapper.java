package com.cts.product.mapper;

import com.cts.product.dto.LoanProductRequestDTO;
import com.cts.product.dto.LoanProductResponseDTO;
import com.cts.product.entity.LoanProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LoanProductMapper {

    @Mapping(target = "status", ignore = true)
    LoanProductResponseDTO toResponseDTO(LoanProduct entity);

    @Mapping(target = "productId", ignore = true)
    @Mapping(target = "status", ignore = true)
    LoanProduct toEntity(LoanProductRequestDTO dto);

    List<LoanProductResponseDTO> toResponseDTOList(List<LoanProduct> entities);
}
