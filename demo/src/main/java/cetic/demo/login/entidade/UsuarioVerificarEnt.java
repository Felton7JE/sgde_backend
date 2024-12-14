package cetic.demo.login.entidade;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "usuario_verificar_ent")
public class UsuarioVerificarEnt {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column (nullable = false)
    private UUID uuid;

    @Column (nullable = false)
    private Instant dataExpericacao; 
    
    @ManyToOne
    @JoinColumn (name = "ID_CADASTRO", referencedColumnName = "ID", unique = true)
    private UsuarioEnty usuarioEnty;

}
