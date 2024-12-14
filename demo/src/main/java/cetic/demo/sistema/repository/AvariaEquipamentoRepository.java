package cetic.demo.sistema.repository; 

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cetic.demo.sistema.entidade.AvariaEquipamento;
import cetic.demo.sistema.entidade.Equipamento;
import cetic.demo.sistema.enums.StatusAvaria;


@Repository
public interface AvariaEquipamentoRepository extends JpaRepository<AvariaEquipamento, Long> {
   


      // Método para buscar uma avaria pelo número de série do equipamento
      Optional<AvariaEquipamento> findByEquipamento_NumeroSerie(String numeroSerie);

      // Método para buscar avarias por status
      List<AvariaEquipamento> findByStatus(String status);
  
AvariaEquipamento findByEquipamentoAndStatus(Equipamento equipamento, StatusAvaria status);

      // Método para verificar se existe uma avaria por número de série
      boolean existsByEquipamento_NumeroSerie(String numeroSerie);

      long countByStatus(String status);

}