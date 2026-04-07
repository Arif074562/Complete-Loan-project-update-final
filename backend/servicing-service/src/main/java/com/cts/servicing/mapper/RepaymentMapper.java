package com.cts.servicing.mapper;

import com.cts.servicing.dto.RepaymentRequestDTO;
import com.cts.servicing.dto.RepaymentResponseDTO;
import com.cts.servicing.entity.Repayment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RepaymentMapper {

    @Mapping(target = "repaymentId", ignore = true)
    @Mapping(target = "status", ignore = true)
    RepaymentResponseDTO toResponseDTO(Repayment entity);

    @Mapping(target = "repaymentId", ignore = true)
    @Mapping(target = "status", ignore = true)
    Repayment toEntity(RepaymentRequestDTO dto);

    List<RepaymentResponseDTO> toResponseDTOList(List<Repayment> entities);
}
