package com.cts.product.mapper;

import com.cts.product.dto.LoanProductRequestDTO;
import com.cts.product.dto.LoanProductResponseDTO;
import com.cts.product.entity.LoanProduct;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-07T15:21:47+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260224-0835, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class LoanProductMapperImpl implements LoanProductMapper {

    @Override
    public LoanProductResponseDTO toResponseDTO(LoanProduct entity) {
        if ( entity == null ) {
            return null;
        }

        LoanProductResponseDTO.LoanProductResponseDTOBuilder loanProductResponseDTO = LoanProductResponseDTO.builder();

        loanProductResponseDTO.interestRate( entity.getInterestRate() );
        loanProductResponseDTO.maxAmount( entity.getMaxAmount() );
        loanProductResponseDTO.maxTenure( entity.getMaxTenure() );
        loanProductResponseDTO.minAmount( entity.getMinAmount() );
        loanProductResponseDTO.minTenure( entity.getMinTenure() );
        loanProductResponseDTO.name( entity.getName() );
        loanProductResponseDTO.productId( entity.getProductId() );

        return loanProductResponseDTO.build();
    }

    @Override
    public LoanProduct toEntity(LoanProductRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        LoanProduct.LoanProductBuilder loanProduct = LoanProduct.builder();

        loanProduct.interestRate( dto.getInterestRate() );
        loanProduct.maxAmount( dto.getMaxAmount() );
        loanProduct.maxTenure( dto.getMaxTenure() );
        loanProduct.minAmount( dto.getMinAmount() );
        loanProduct.minTenure( dto.getMinTenure() );
        loanProduct.name( dto.getName() );

        return loanProduct.build();
    }

    @Override
    public List<LoanProductResponseDTO> toResponseDTOList(List<LoanProduct> entities) {
        if ( entities == null ) {
            return null;
        }

        List<LoanProductResponseDTO> list = new ArrayList<LoanProductResponseDTO>( entities.size() );
        for ( LoanProduct loanProduct : entities ) {
            list.add( toResponseDTO( loanProduct ) );
        }

        return list;
    }
}
