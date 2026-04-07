package com.cts.auth.mapper;

import com.cts.auth.dto.UserRequestDTO;
import com.cts.auth.dto.UserResponseDTO;
import com.cts.auth.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "role", target = "role")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "isActive", target = "isActive")
    @Mapping(source = "createdAt", target = "createdAt")
    UserResponseDTO toResponseDTO(User entity);

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "password", source = "password")
    User toEntity(UserRequestDTO dto);

    List<UserResponseDTO> toResponseDTOList(List<User> entities);
}
