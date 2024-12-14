package cetic.demo.login.repository;

import java.time.Instant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cetic.demo.login.entidade.Codigo2FAEnt;
import cetic.demo.login.entidade.UsuarioEnty;

@Repository
public interface Codigo2FARep extends JpaRepository<Codigo2FAEnt, Long> {

    void deleteAllByDataExpiracaoBefore(Instant data);

    Codigo2FAEnt findByUsuario(UsuarioEnty usuario);

}