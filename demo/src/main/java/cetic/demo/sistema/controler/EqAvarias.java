package cetic.demo.sistema.controler;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cetic.demo.sistema.dto.AvariaDTO;
import cetic.demo.sistema.entidade.AvariaEquipamento;
import cetic.demo.sistema.services.AvariaEquipamentoService;

@RestController
@RequestMapping("/sistema/avarias")
public class EqAvarias {

    @Autowired
    private AvariaEquipamentoService avariaEquipamentoService;

    // Criar avaria
    @PostMapping("/{numeroSerie}")
    public ResponseEntity<AvariaDTO> criarAvaria(@PathVariable String numeroSerie,
            @RequestBody AvariaEquipamento avariaEquipamento) {
        try {
            AvariaDTO avariaDTO = avariaEquipamentoService.salvarAvaria(numeroSerie, avariaEquipamento);
            return new ResponseEntity<>(avariaDTO, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // Caso o equipamento não seja encontrado
        }
    }

    // Listar todas as avarias
    @GetMapping
    public ResponseEntity<List<AvariaDTO>> listarAvarias() {
        List<AvariaDTO> avarias = avariaEquipamentoService.listarAvarias();
        return new ResponseEntity<>(avarias, HttpStatus.OK);
    }

    // Buscar avaria por número de série
    @GetMapping("/{numeroSerie}")
    public ResponseEntity<AvariaDTO> buscarAvariaPorNumeroSerie(@PathVariable String numeroSerie) {
        Optional<AvariaDTO> avariaDTO = avariaEquipamentoService.buscarAvariaPorNumeroSerie(numeroSerie);
        return avariaDTO.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Atualizar avaria por número de série
    @PutMapping("/{numeroSerie}")
    public ResponseEntity<AvariaDTO> atualizarAvariaPorNumeroSerie(@PathVariable String numeroSerie,
            @RequestBody AvariaEquipamento dadosAtualizados) {
        Optional<AvariaDTO> avariaDTO = avariaEquipamentoService.atualizarAvariaPorNumeroSerie(numeroSerie,
                dadosAtualizados);
        return avariaDTO.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Excluir avaria por número de série
    @DeleteMapping("/{numeroSerie}")
    public ResponseEntity<Void> excluirAvariaPorNumeroSerie(@PathVariable String numeroSerie) {
        boolean deleted = avariaEquipamentoService.excluirAvariaPorNumeroSerie(numeroSerie);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Verificar se existe avaria para o número de série
    @GetMapping("/exists/{numeroSerie}")
    public ResponseEntity<Boolean> verificarSeAvariaExiste(@PathVariable String numeroSerie) {
        boolean existe = avariaEquipamentoService.verificarSeAvariaExiste(numeroSerie);
        return new ResponseEntity<>(existe, HttpStatus.OK);
    }

    // Contar o número de avarias
    @GetMapping("/count")
    public ResponseEntity<Long> contarAvarias() {
        long totalAvarias = avariaEquipamentoService.contarAvarias();
        return new ResponseEntity<>(totalAvarias, HttpStatus.OK);
    }

    // Buscar avarias por status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<AvariaDTO>> buscarAvariasPorStatus(@PathVariable String status) {
        List<AvariaDTO> avarias = avariaEquipamentoService.buscarAvariasPorStatus(status);
        return new ResponseEntity<>(avarias, HttpStatus.OK);
    }
}
