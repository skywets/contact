package com.example.contact.services.servicesImpl;

import com.example.contact.models.dtos.UserDto;
import com.example.contact.models.entitiies.User;
import com.example.contact.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public User signup(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return userRepository.save(user);
    }

    public User authenticate(UserDto userDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDto.getName(),
                        userDto.getPassword()
                )
        );

        return userRepository.findByName(userDto.getName())
                .orElseThrow();
    }
}
