package cetic.demo.login.dto;


import java.util.UUID;

import org.springframework.beans.BeanUtils;

import cetic.demo.login.entidade.UsuarioEnty;
import cetic.demo.login.enums.EstadoUsuario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioDTO {

    private UUID id;

    private String email;

    private Integer contato;

    private String senha;

    private String nome;

    private EstadoUsuario situacao;

    public UsuarioDTO(UsuarioEnty usuarioEnty) {
        BeanUtils.copyProperties(usuarioEnty, usuarioEnty);
    }

}
