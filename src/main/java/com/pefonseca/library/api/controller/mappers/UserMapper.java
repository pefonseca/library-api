package com.pefonseca.library.api.controller.mappers;

import com.pefonseca.library.api.controller.dto.UserDTO;
import com.pefonseca.library.api.model.AuthUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    AuthUser toEntity(UserDTO user);

}
