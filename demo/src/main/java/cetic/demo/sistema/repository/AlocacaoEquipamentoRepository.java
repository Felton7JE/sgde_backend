package cetic.demo.sistema.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cetic.demo.sistema.entidade.AlocacaoEquipamento;
import cetic.demo.sistema.entidade.Equipamento;

@Repository
public interface AlocacaoEquipamentoRepository extends JpaRepository<AlocacaoEquipamento, Long> {
    List<AlocacaoEquipamento> findByStatus(String status);

    List<AlocacaoEquipamento> findByEquipamentoId(Long equipamentoId);

    List<AlocacaoEquipamento> findAllByEquipamento(Equipamento equipamento);

    Optional<AlocacaoEquipamento> findByEquipamento_NumeroSerie(String numeroSerie);
    List<AlocacaoEquipamento> findByUsuario(String usuario);

    List<AlocacaoEquipamento> findByDataAlocacao(LocalDate dataAlocacao);

    long count();
}