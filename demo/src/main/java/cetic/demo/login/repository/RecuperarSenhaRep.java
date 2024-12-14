package cetic.demo.login.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import cetic.demo.login.entidade.RecuperarSenhaEnty;

public interface RecuperarSenhaRep extends JpaRepository<RecuperarSenhaEnty, Long> {
            Optional<RecuperarSenhaEnty> findByUuid(UUID uuid);

    
}
