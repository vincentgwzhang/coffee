package tina.coffee.system.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tina.coffee.system.security.data.model.CoffeeUserEntity;
import tina.coffee.system.security.data.repository.CoffeeUserRepository;

import java.util.Optional;

@Service
public class CoffeeUserDetailsService implements UserDetailsService {

    @Autowired
    private CoffeeUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<CoffeeUserEntity> userEntity = repository.findByUsername(username);
        return userEntity
                .map(CoffeeUserDetails::new)
                .orElseThrow(()-> new UsernameNotFoundException(username));
    }

}
