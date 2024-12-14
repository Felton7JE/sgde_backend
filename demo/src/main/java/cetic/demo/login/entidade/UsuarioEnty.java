package cetic.demo.login.entidade;

import java.util.UUID;

import org.springframework.beans.BeanUtils;


import cetic.demo.login.dto.UsuarioDTO;
import cetic.demo.login.enums.EstadoUsuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Usuario")
public class UsuarioEnty {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "`id`", nullable = false)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private Integer contato;

    @Column(nullable = false, unique = true)
    private String senha;

    @Column(nullable = false, unique = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoUsuario situacao;

    public UsuarioEnty(UsuarioDTO UsuarioDTO) {

        BeanUtils.copyProperties(UsuarioDTO, UsuarioDTO);
    }

  
    

}
