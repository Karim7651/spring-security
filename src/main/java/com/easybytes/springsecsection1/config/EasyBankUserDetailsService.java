package com.easybytes.springsecsection1.config;

import com.easybytes.springsecsection1.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.easybytes.springsecsection1.repository.CustomerRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EasyBankUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User details not found for: " + username));

        return new org.springframework.security.core.userdetails.User(
                customer.getEmail(),
                customer.getPwd(),
                List.of(new SimpleGrantedAuthority(customer.getRole()))
        );
    }
}
