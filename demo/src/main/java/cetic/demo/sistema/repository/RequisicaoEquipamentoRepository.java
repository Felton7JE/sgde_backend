package cetic.demo.sistema.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cetic.demo.sistema.entidade.RequisicaoEquipamento;

@Repository
public interface RequisicaoEquipamentoRepository extends JpaRepository<RequisicaoEquipamento, Long> {
    List<RequisicaoEquipamento> findByStatus(String status);

    List<RequisicaoEquipamento> findByResponsavel(String responsavel);

    List<RequisicaoEquipamento> findByResponsavelAndStatus(String responsavel, String status);
}