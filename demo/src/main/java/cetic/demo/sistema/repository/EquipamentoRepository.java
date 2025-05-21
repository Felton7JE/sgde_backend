package cetic.demo.sistema.repository; 

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cetic.demo.sistema.entidade.AlocacaoEquipamento;
import cetic.demo.sistema.entidade.Equipamento;


@Repository
public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {
    boolean existsByNumeroSerie(String numeroSerie);
    Optional<Equipamento> findByNumeroSerie(String numeroSerie);

    
    List<Equipamento> findByStatus(String status);
}
