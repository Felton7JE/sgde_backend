package cetic.demo.login.entidade;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Codigo2FA")
public class Codigo2FAEnt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String codigo2FA;

    @Column(nullable = false)
    private Instant dataCriacao = Instant.now();

    @Column(nullable = false)
    private Instant dataExpiracao;

    @Column(nullable = false)
    private boolean usado = false;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioEnty usuario;


    public Codigo2FAEnt(String codigo2FA, UsuarioEnty usuario, Instant dataExpiracao) {
        this.codigo2FA = codigo2FA;
        this.usuario = usuario;
        this.dataExpiracao = dataExpiracao;
        this.dataCriacao = Instant.now();
    }
    
    
}
