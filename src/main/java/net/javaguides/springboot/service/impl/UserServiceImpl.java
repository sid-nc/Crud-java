package net.javaguides.springboot.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.springboot.dto.UserDto;
import net.javaguides.springboot.entity.User;
import net.javaguides.springboot.mapper.UserMapper;
import net.javaguides.springboot.repository.UserRepository;
import net.javaguides.springboot.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    //Don't have to create a constructor coz - AllArgsConstructor. Don't have to use @Autowired
    //annotation coz Spring bean which requires only one parameterized constructor can use AllArgsConstructor(Lombok dependency)
    //ModelMapper dependency, maps from one entity to another
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        //convert UserDto to JPA entity
        //User user = UserMapper.maptoUser(userDto);
        User user = modelMapper.map(userDto, User.class);

        User savedUser = userRepository.save(user);

        //convert savedUser back to userDto
        //UserDto savedUserDto = UserMapper.maptoUserDto(savedUser);
        UserDto savedUserDto = modelMapper.map(savedUser, UserDto.class);
        return savedUserDto;
    }

    @Override
    public UserDto getUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        User user = optionalUser.get();
        //return UserMapper.maptoUserDto(user);
        return modelMapper.map(user,UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        //return users.stream().map(UserMapper::maptoUserDto).toList();
        return users.stream().map((user) -> modelMapper.map(user,UserDto.class)).collect(Collectors.toList());

    }

    @Override
    public UserDto updateUser(UserDto user) {
        User existinguser = userRepository.findById(user.getId()).get();
        existinguser.setFirstName(user.getFirstName());
        existinguser.setLastName(user.getLastName());
        existinguser.setEmail(user.getEmail());
        User updatedUser = userRepository.save(existinguser);
        //return UserMapper.maptoUserDto(updatedUser);
        return modelMapper.map(updatedUser,UserDto.class);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }


}
