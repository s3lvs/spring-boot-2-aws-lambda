package com.example.helloworld.controller;

import com.example.helloworld.dto.UsuarioDto;
import com.example.helloworld.dto.UsuarioSenhaDto;
import com.example.helloworld.service.UserService;
import com.example.helloworld.domain.Usuario;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = {"Usuarios"})
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UsuarioDto> register(@RequestBody UsuarioSenhaDto usuario) {

        Usuario u = new Usuario();
        u.setUsername(usuario.getUsuario());
        u.setPassword(usuario.getSenha());

        Usuario createdUsuario = userService.register(u);

        UsuarioDto dto = new UsuarioDto();
        dto.setSid(createdUsuario.getSid());
        dto.setUsuario(createdUsuario.getUsername());

        return ResponseEntity.ok(dto);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Usuario usuario) {
       return ResponseEntity.ok(userService.login(usuario.getUsername(), usuario.getPassword()));
    }
}