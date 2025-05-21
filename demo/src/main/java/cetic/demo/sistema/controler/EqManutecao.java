package cetic.demo.sistema.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cetic.demo.sistema.dto.ManutencaoDTO;
import cetic.demo.sistema.services.ManutencaoEquipamentoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sistema/manutencao")
public class EqManutecao {

    @Autowired
    private ManutencaoEquipamentoService manutencaoEquipamentoService;

    @PostMapping
    public ResponseEntity<ManutencaoDTO> criarManutencao(@RequestBody ManutencaoDTO manutencaoDTO) {
        try {
            ManutencaoDTO novaManutencao = manutencaoEquipamentoService.criarManutencao(manutencaoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaManutencao);
        } catch (RuntimeException e) {
            // Log detalhado do erro para facilitar o diagnóstico
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Listar todas as manutenções
    @GetMapping("/listar")
    public ResponseEntity<List<ManutencaoDTO>> listarTodasManutencoes() {
        List<ManutencaoDTO> manutencaoDTOs = manutencaoEquipamentoService.listarManutencao();
        return ResponseEntity.ok(manutencaoDTOs);
    }

    // Listar manutenções de um equipamento pelo número de série
    @PostMapping("/porEquipamento")
    public ResponseEntity<List<ManutencaoDTO>> listarManutencoesPorEquipamento(@RequestBody ManutencaoDTO manutencaoDTO) {
        try {
            List<ManutencaoDTO> manutencoes = manutencaoEquipamentoService.listarManutencoesPorEquipamento(manutencaoDTO);
            return ResponseEntity.ok(manutencoes);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Buscar manutenção específica de um equipamento
    @PostMapping("/buscar")
    public ResponseEntity<ManutencaoDTO> buscarManutencao(@RequestBody ManutencaoDTO manutencaoDTO) {
        try {
            Optional<ManutencaoDTO> manutencao = manutencaoEquipamentoService.buscarManutencao(manutencaoDTO);
            return manutencao.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Atualizar manutenção de um equipamento
    @PutMapping
    public ResponseEntity<ManutencaoDTO> atualizarManutencao(@RequestBody ManutencaoDTO manutencaoDTO) {
        try {
            Optional<ManutencaoDTO> manutencaoAtualizada = manutencaoEquipamentoService.atualizarManutencao(manutencaoDTO, null);
            return manutencaoAtualizada.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Remover manutenção de um equipamento
    @DeleteMapping
    public ResponseEntity<Void> removerManutencao(@RequestBody ManutencaoDTO manutencaoDTO) {
        try {
            boolean removido = manutencaoEquipamentoService.removerManutencao(manutencaoDTO);
            if (removido) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
