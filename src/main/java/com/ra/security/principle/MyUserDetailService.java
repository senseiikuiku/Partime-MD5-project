package com.ra.security.principle;

import com.ra.model.entity.Users;
import com.ra.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = usersRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Email not found"));
        MyUserDetails myUserDetails = new MyUserDetails();
        myUserDetails.setUser(users);
        myUserDetails.setAuthorities(users.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toSet()));

        return myUserDetails;
    }
}
