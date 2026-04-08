package com.cts.auth.mapper;

import com.cts.auth.dto.UserRequestDTO;
import com.cts.auth.dto.UserResponseDTO;
import com.cts.auth.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-08T10:38:07+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260224-0835, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserResponseDTO toResponseDTO(User entity) {
        if ( entity == null ) {
            return null;
        }

        UserResponseDTO.UserResponseDTOBuilder userResponseDTO = UserResponseDTO.builder();

        userResponseDTO.userId( entity.getUserId() );
        userResponseDTO.name( entity.getName() );
        userResponseDTO.email( entity.getEmail() );
        userResponseDTO.role( entity.getRole() );
        userResponseDTO.phone( entity.getPhone() );
        userResponseDTO.isActive( entity.getIsActive() );
        userResponseDTO.createdAt( entity.getCreatedAt() );

        return userResponseDTO.build();
    }

    @Override
    public User toEntity(UserRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.password( dto.getPassword() );
        user.email( dto.getEmail() );
        user.name( dto.getName() );
        user.phone( dto.getPhone() );
        user.role( dto.getRole() );

        return user.build();
    }

    @Override
    public List<UserResponseDTO> toResponseDTOList(List<User> entities) {
        if ( entities == null ) {
            return null;
        }

        List<UserResponseDTO> list = new ArrayList<UserResponseDTO>( entities.size() );
        for ( User user : entities ) {
            list.add( toResponseDTO( user ) );
        }

        return list;
    }
}
