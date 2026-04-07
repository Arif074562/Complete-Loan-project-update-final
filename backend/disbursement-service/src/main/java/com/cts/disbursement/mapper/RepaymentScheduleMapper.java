package com.cts.disbursement.mapper;

import com.cts.disbursement.dto.RepaymentScheduleResponseDTO;
import com.cts.disbursement.entity.RepaymentSchedule;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RepaymentScheduleMapper {

    RepaymentScheduleResponseDTO toResponseDTO(RepaymentSchedule entity);

    List<RepaymentScheduleResponseDTO> toResponseDTOList(List<RepaymentSchedule> entities);
}
