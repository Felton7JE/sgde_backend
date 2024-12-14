package cetic.demo.login.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import cetic.demo.login.entidade.UsuarioEnty;


public interface UsuarioRep extends JpaRepository<UsuarioEnty, UUID>{

    boolean existsByEmail(String email);

    boolean existsByContato(Integer contato);
    
    UsuarioEnty findByEmail(String email);

    UsuarioEnty findUsuarioByEmail(String email);





    
}
