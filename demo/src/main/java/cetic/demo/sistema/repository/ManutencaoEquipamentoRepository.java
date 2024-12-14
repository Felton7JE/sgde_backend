package cetic.demo.sistema.repository; 

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cetic.demo.sistema.entidade.Equipamento;
import cetic.demo.sistema.entidade.ManutencaoEquipamento;


@Repository
public interface ManutencaoEquipamentoRepository extends JpaRepository<ManutencaoEquipamento, Long> {
    List<ManutencaoEquipamento> findByEquipamentoId(Long equipamentoId);
    List<ManutencaoEquipamento> findByStatus(String status);
    Optional<ManutencaoEquipamento> findByIdAndEquipamento(Long id, Equipamento equipamento);
    List<ManutencaoEquipamento> findByEquipamento(Equipamento equipamento);
    long countByStatus(String status);


}