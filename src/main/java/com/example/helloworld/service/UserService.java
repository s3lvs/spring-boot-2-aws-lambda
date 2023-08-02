package com.example.helloworld.service;

import com.example.helloworld.config.HashEncoder;
import com.example.helloworld.domain.Usuario;
import com.example.helloworld.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final TokenAuthenticationService tokenAuthenticationService;

    private final HashEncoder hashEncoder;

    public UserService(@Autowired UserRepository userRepository,
                       @Autowired BCryptPasswordEncoder bCryptPasswordEncoder,
                       @Autowired TokenAuthenticationService tokenAuthenticationService,
                       @Autowired HashEncoder hashEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenAuthenticationService = tokenAuthenticationService;
        this.hashEncoder = hashEncoder;
    }

    public Usuario register(Usuario usuario) {
        usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
        Usuario save = userRepository.save(usuario);
        save.setSid(hashEncoder.generateMurmurHashHex(save.getId().toString()));
        return usuario;
    }

    public String login(String username, String password) {
        Usuario usuario = userRepository.findByUsername(username);
        if(usuario == null)
            throw new UsernameNotFoundException("User not found");
        boolean passwordsMatch = bCryptPasswordEncoder.matches(password, usuario.getPassword());
        if(!passwordsMatch)
            throw new IllegalArgumentException("Bad credentials");
        return tokenAuthenticationService.createToken(username);
    }
}