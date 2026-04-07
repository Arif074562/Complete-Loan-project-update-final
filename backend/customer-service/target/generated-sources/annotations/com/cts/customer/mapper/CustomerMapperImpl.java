package com.cts.customer.mapper;

import com.cts.customer.dto.CustomerRequestDTO;
import com.cts.customer.dto.CustomerResponseDTO;
import com.cts.customer.entity.Customer;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-07T17:26:35+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.43.0.v20250819-1513, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class CustomerMapperImpl implements CustomerMapper {

    @Override
    public Customer toEntity(CustomerRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Customer.CustomerBuilder customer = Customer.builder();

        customer.aadharNumber( dto.getAadharNumber() );
        customer.address( dto.getAddress() );
        customer.city( dto.getCity() );
        customer.dateOfBirth( dto.getDateOfBirth() );
        customer.email( dto.getEmail() );
        customer.firstName( dto.getFirstName() );
        customer.lastName( dto.getLastName() );
        customer.panNumber( dto.getPanNumber() );
        customer.phone( dto.getPhone() );
        customer.pinCode( dto.getPinCode() );
        customer.segment( dto.getSegment() );
        customer.state( dto.getState() );

        return customer.build();
    }

    @Override
    public CustomerResponseDTO toResponseDTO(Customer entity) {
        if ( entity == null ) {
            return null;
        }

        CustomerResponseDTO.CustomerResponseDTOBuilder customerResponseDTO = CustomerResponseDTO.builder();

        customerResponseDTO.aadharNumber( entity.getAadharNumber() );
        customerResponseDTO.address( entity.getAddress() );
        customerResponseDTO.city( entity.getCity() );
        customerResponseDTO.createdAt( entity.getCreatedAt() );
        customerResponseDTO.customerId( entity.getCustomerId() );
        customerResponseDTO.dateOfBirth( entity.getDateOfBirth() );
        customerResponseDTO.email( entity.getEmail() );
        customerResponseDTO.firstName( entity.getFirstName() );
        customerResponseDTO.kycStatus( entity.getKycStatus() );
        customerResponseDTO.lastName( entity.getLastName() );
        customerResponseDTO.panNumber( entity.getPanNumber() );
        customerResponseDTO.phone( entity.getPhone() );
        customerResponseDTO.pinCode( entity.getPinCode() );
        customerResponseDTO.segment( entity.getSegment() );
        customerResponseDTO.state( entity.getState() );
        customerResponseDTO.status( entity.getStatus() );
        customerResponseDTO.updatedAt( entity.getUpdatedAt() );

        return customerResponseDTO.build();
    }

    @Override
    public List<CustomerResponseDTO> toResponseDTOList(List<Customer> entities) {
        if ( entities == null ) {
            return null;
        }

        List<CustomerResponseDTO> list = new ArrayList<CustomerResponseDTO>( entities.size() );
        for ( Customer customer : entities ) {
            list.add( toResponseDTO( customer ) );
        }

        return list;
    }
}
