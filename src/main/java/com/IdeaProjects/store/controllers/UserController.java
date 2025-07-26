package com.IdeaProjects.store.controllers;

import com.IdeaProjects.store.dtos.RegisterUserRequest;
import com.IdeaProjects.store.dtos.UpdateUserRequest;
import com.IdeaProjects.store.dtos.UserDto;
import com.IdeaProjects.store.mappers.UserMapper;
import com.IdeaProjects.store.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @GetMapping
    public List<UserDto> getAllUser(
            @RequestParam(required = false, defaultValue = "", name = "sort") String sort)
    {
        if (!Set.of("name", "email").contains(sort))
            sort = "name";

        return userRepository.findAll(Sort.by(sort))
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id){
        var user = userRepository.findById(id).orElse(null);

        if(user == null){
            return ResponseEntity.notFound().build();
        }

//        var userDto = new UserDto(user.getId(), user.getName(), user.getEmail());
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @PostMapping
    public ResponseEntity<?> registerUser(
            @Valid @RequestBody RegisterUserRequest request,
            UriComponentsBuilder uriBuilder){
        //Bound request sent from client to user entity
//        ---------\/--------reg/save user through RegisterUSerDto
        if(userRepository.existsByEmail(request.getEmail())){
            return ResponseEntity.badRequest().body(
                    Map.of("email", "Email is already registered")
            );
        }

        var user = userMapper.toEntity(request);
        userRepository.save(user);

//        -----------\/---------- response to view through DTO
        var userDto = userMapper.toDto(user);

        //Only to implement 201 status codes
        //to pass argument into the "created" method
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();

        //can just "return userDto"
        return ResponseEntity.created(uri).body(userDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable(name = "id") Long id,
            @RequestBody UpdateUserRequest request){
        var user = userRepository.findById(id).orElse(null);
        if(user == null){
            return ResponseEntity.notFound().build();
        }

        userMapper.update(request, user);
        userRepository.save(user);

        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        var user = userRepository.findById(id).orElse(null);
        if(user == null){
            return ResponseEntity.notFound().build();
        }

        userRepository.delete(user);
        return ResponseEntity.noContent().build();
    }

}
