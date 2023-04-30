package com.budgetfy.app.mapstruct;

import com.budgetfy.app.mapstruct.base.ObjectMapper;
import com.budgetfy.app.model.User;
import com.budgetfy.app.model.base.Role;
import com.budgetfy.app.payload.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper extends ObjectMapper<UserDTO, User> {

    @Override
    @Mapping(source = "roleId", target = "role")
    @Mapping(target = "password", ignore = true)
    User mapDTOToEntity(UserDTO userDTO);

    @Override
    @Mapping(source = "role.id", target = "roleId")
    UserDTO mapEntityToDTO(User user);

    @Override
    List<User> listMapDTOToEntity(List<UserDTO> userDTOS);

    @Override
    List<UserDTO> listMapEntityToDTO(List<User> users);

    default Role role(Integer roleId) {

        if (roleId == null)
            return null;

        Role role = new Role();
        role.setId(roleId);

        return role;

    }
}
