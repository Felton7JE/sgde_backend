package cetic.demo.sistema.controler;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cetic.demo.sistema.dto.AvariaDTO;
import cetic.demo.sistema.services.AvariaEquipamentoService;

@RestController
@RequestMapping("/sistema/avarias")
public class EqAvarias {

    @Autowired
    private AvariaEquipamentoService avariaEquipamentoService;

    // Criar avaria
    @PostMapping
    public ResponseEntity<AvariaDTO> criarAvaria(@RequestBody AvariaDTO avariaDTO) {
        AvariaDTO avariaSalva = avariaEquipamentoService.salvarAvaria(avariaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(avariaSalva);
    }

    // Listar todas as avarias
    @GetMapping
    public ResponseEntity<List<AvariaDTO>> listarAvarias() {
        List<AvariaDTO> avarias = avariaEquipamentoService.listarAvarias();
        return new ResponseEntity<>(avarias, HttpStatus.OK);
    }

    // Buscar avaria por número de série
    @PostMapping("/buscar")
    public ResponseEntity<AvariaDTO> buscarAvariaPorNumeroSerie(@RequestBody AvariaDTO avariaDTO) {
        Optional<AvariaDTO> avaria = avariaEquipamentoService.buscarAvariaPorNumeroSerie(avariaDTO);
        return avaria.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Atualizar avaria por número de série
    @PutMapping
    public ResponseEntity<AvariaDTO> atualizarAvariaPorNumeroSerie(@RequestBody AvariaDTO avariaDTO) {
        Optional<AvariaDTO> avaria = avariaEquipamentoService.atualizarAvariaPorNumeroSerie(avariaDTO);
        return avaria.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Excluir avaria por número de série
    @DeleteMapping
    public ResponseEntity<Void> excluirAvariaPorNumeroSerie(@RequestBody AvariaDTO avariaDTO) {
        boolean deleted = avariaEquipamentoService.excluirAvariaPorNumeroSerie(avariaDTO);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Verificar se existe avaria para o número de série
    @GetMapping("/exists/{numeroSerie}")
    public ResponseEntity<Boolean> verificarSeAvariaExiste(@PathVariable String numeroSerie) {
        AvariaDTO avariaDTO = new AvariaDTO();
        avariaDTO.setNumeroSerie(numeroSerie);
        boolean existe = avariaEquipamentoService.verificarSeAvariaExiste(avariaDTO);
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
        AvariaDTO avariaDTO = new AvariaDTO();
        avariaDTO.setStatus(status);
        List<AvariaDTO> avarias = avariaEquipamentoService.buscarAvariasPorStatus(avariaDTO);
        return new ResponseEntity<>(avarias, HttpStatus.OK);
    }
}
