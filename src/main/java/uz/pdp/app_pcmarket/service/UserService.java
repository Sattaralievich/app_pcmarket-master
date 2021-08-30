package uz.pdp.app_pcmarket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.app_pcmarket.entity.user.User;
import uz.pdp.app_pcmarket.payload.ApiResponse;
import uz.pdp.app_pcmarket.payload.UserDto;
import uz.pdp.app_pcmarket.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public ApiResponse add(UserDto userDto) {
        boolean existsByPhoneNumber = userRepository.existsByPhoneNumber(userDto.getPhoneNumber());

        if (existsByPhoneNumber) {
            return new ApiResponse("This user already added", false);
        }

        User user = new User();
        user.setName(userDto.getName());
        user.setUsername(userDto.getUsername());
        user.setPhoneNumber(userDto.getPhoneNumber());

        userRepository.save(user);

        return new ApiResponse("The user added", true);
    }

    public List<User> getAll() {
        List<User> all = userRepository.findAll();

        return all;
    }

    public User getById(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        return new User();
    }

    public ApiResponse edit(UserDto userDto, Integer id) {

        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty())
            return new ApiResponse("The user not found", false);

        User user = optionalUser.get();

        user.setName(userDto.getName());
        user.setUsername(userDto.getUsername());
        user.setPhoneNumber(userDto.getPhoneNumber());

        userRepository.save(user);

        return new ApiResponse("The user edited", true);
    }

    public ApiResponse delete(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty())
            return new ApiResponse("The user not found", false);

        userRepository.deleteById(id);
        return new ApiResponse("The user deleted", true);
    }
}
