package cetic.demo.sistema.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cetic.demo.sistema.dto.ManutencaoDTO;
import cetic.demo.sistema.entidade.ManutencaoEquipamento;
import cetic.demo.sistema.services.ManutencaoEquipamentoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sistema/manutencao")
public class EqManutecao {

    @Autowired
    private ManutencaoEquipamentoService manutencaoEquipamentoService;

    @PostMapping("/{numeroSerie}")
    public ResponseEntity<ManutencaoEquipamento> criarManutencao(@PathVariable String numeroSerie, @RequestBody ManutencaoEquipamento manutencao) {
        try {
            ManutencaoEquipamento novaManutencao = manutencaoEquipamentoService.criarManutencao(numeroSerie, manutencao);
            return new ResponseEntity<>(novaManutencao, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    // Listar todas as manutenções
    @GetMapping("/listar")
    public ResponseEntity<List<ManutencaoDTO>> listarTodasManutencoes() {
        List<ManutencaoDTO> manutencaoDTOs = manutencaoEquipamentoService.listarManutencao();
        return ResponseEntity.ok(manutencaoDTOs);
    }

    // Listar manutenções de um equipamento pelo número de série
    @GetMapping("/{numeroSerie}")
    public ResponseEntity<List<ManutencaoDTO>> listarManutencoesPorEquipamento(@PathVariable String numeroSerie) {
        try {
            List<ManutencaoDTO> manutencaoDTOs = manutencaoEquipamentoService
                    .listarManutencoesPorEquipamento(numeroSerie);
            return ResponseEntity.ok(manutencaoDTOs);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Buscar manutenção específica de um equipamento
    @GetMapping("/{numeroSerie}/buscar")
    public ResponseEntity<ManutencaoDTO> buscarManutencao(@PathVariable String numeroSerie) {
        try {
            Optional<ManutencaoDTO> manutencaoDTO = manutencaoEquipamentoService.buscarManutencao(numeroSerie);
            return manutencaoDTO.isPresent() ? ResponseEntity.ok(manutencaoDTO.get())
                    : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Atualizar manutenção de um equipamento
    @PutMapping("/{numeroSerie}")
    public ResponseEntity<ManutencaoDTO> atualizarManutencao(
            @PathVariable String numeroSerie,
            @RequestBody ManutencaoEquipamento manutencaoEquipamento) {
        try {
            Optional<ManutencaoDTO> manutencaoDTO = manutencaoEquipamentoService.atualizarManutencao(numeroSerie,
                    manutencaoEquipamento);
            return manutencaoDTO.isPresent() ? ResponseEntity.ok(manutencaoDTO.get())
                    : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Remover manutenção de um equipamento
    @DeleteMapping("/{numeroSerie}")
    public ResponseEntity<Void> removerManutencao(@PathVariable String numeroSerie) {
        try {
            boolean removido = manutencaoEquipamentoService.removerManutencao(numeroSerie);
            return removido ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

  
}
