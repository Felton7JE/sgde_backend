package cetic.demo.sistema.controler;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cetic.demo.sistema.dto.EquipamentosDTO;
import cetic.demo.sistema.services.EquipamentoService;

import java.util.List;

@RestController
@RequestMapping(value = "/sistema/equipamento")
public class EqController {

    @Autowired
    private EquipamentoService equipamentoService;

    // Adicionar equipamento
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/add")
    public ResponseEntity<EquipamentosDTO> addEquipamento(Authentication authentication,
            @RequestBody EquipamentosDTO equipamentosDTO) {
        EquipamentosDTO equipamentoSalvo = equipamentoService.salvarEquipamento(equipamentosDTO);
        return ResponseEntity.ok(equipamentoSalvo);
    }

    // Editar equipamento pelo número de série
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/edit/{numeroSerie}")
    public ResponseEntity<EquipamentosDTO> editarEquipamento(@PathVariable String numeroSerie,
            @RequestBody EquipamentosDTO equipamentosDTO) {
        EquipamentosDTO equipamentoEditado = equipamentoService.editarEquipamento(numeroSerie, equipamentosDTO);
        return ResponseEntity.ok(equipamentoEditado);
    }

    // Buscar equipamento pelo número de série
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/buscar/{numeroSerie}")
    public ResponseEntity<EquipamentosDTO> buscarPorNumeroSerie(@PathVariable String numeroSerie) {
        EquipamentosDTO equipamento = equipamentoService.buscarPorNumeroSerie(numeroSerie);
        return ResponseEntity.ok(equipamento);
    }

    // Listar todos os equipamentos
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/listar")
    public ResponseEntity<List<EquipamentosDTO>> listarEquipamentos() {
        List<EquipamentosDTO> equipamentos = equipamentoService.listarEquipamentos();
        return ResponseEntity.ok(equipamentos);
    }

    // Pesquisar por atributo
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/pesquisar")
    public ResponseEntity<List<EquipamentosDTO>> pesquisarEquipamentos(@RequestParam String atributo,
            @RequestParam String valor) {
        List<EquipamentosDTO> equipamentos = equipamentoService.pesquisarPorAtributo(atributo, valor);
        return ResponseEntity.ok(equipamentos);
    }

    // Deletar equipamento pelo número de série
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/deletar/{numeroSerie}")
    public ResponseEntity<Void> deletarEquipamento(@PathVariable String numeroSerie) {
        equipamentoService.deletarEquipamento(numeroSerie);
        return ResponseEntity.noContent().build();
    }
}
