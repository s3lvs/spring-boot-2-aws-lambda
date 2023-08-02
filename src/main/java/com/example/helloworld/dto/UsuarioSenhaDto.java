package com.example.helloworld.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("usu")
public class UsuarioSenhaDto {

    private String usuario;
    private String senha;

}
