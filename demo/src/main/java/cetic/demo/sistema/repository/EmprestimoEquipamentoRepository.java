package cetic.demo.sistema.repository; 

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cetic.demo.sistema.entidade.EmprestimoEquipamento;

@Repository
public interface EmprestimoEquipamentoRepository extends JpaRepository<EmprestimoEquipamento, Long> {
    List<EmprestimoEquipamento> findByStatus(String status);

    Optional<EmprestimoEquipamento> findByEquipamento_NumeroSerie(String numeroSerie);

    List<EmprestimoEquipamento> findByResponsavel(String responsavel);

    List<EmprestimoEquipamento> findByEquipamentoId(Long equipamentoId);
}

