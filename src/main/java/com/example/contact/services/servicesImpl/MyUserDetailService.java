package com.example.contact.services.servicesImpl;

import com.example.contact.exception.NotFoundException;
import com.example.contact.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        var name = userRepository.findByName(userName).orElseThrow(
                () -> new NotFoundException("There is no user with that name!"));
        return name;
    }
}
