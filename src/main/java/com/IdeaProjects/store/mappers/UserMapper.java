package com.IdeaProjects.store.mappers;

import com.IdeaProjects.store.dtos.RegisterUserRequest;
import com.IdeaProjects.store.dtos.UpdateUserRequest;
import com.IdeaProjects.store.dtos.UserDto;
import com.IdeaProjects.store.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

  //<------ ooooo <-------\/
    UserDto toDto(User user);
    User toEntity(RegisterUserRequest request);

    void update(UpdateUserRequest request,@MappingTarget User user);
}
