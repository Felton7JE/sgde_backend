package cetic.demo.login.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import cetic.demo.login.entidade.UsuarioVerificarEnt;


public interface UsuarioVerificadorRep extends JpaRepository<UsuarioVerificarEnt, Long> {
        Optional<UsuarioVerificarEnt> findByUuid(UUID uuid);
    }
    