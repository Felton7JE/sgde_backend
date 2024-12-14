package cetic.demo.sistema.controler;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cetic.demo.sistema.dto.AlocacaoDTO;
import cetic.demo.sistema.entidade.AlocacaoEquipamento;
import cetic.demo.sistema.services.AlocacaoEquipamentoService;

@RestController
@RequestMapping(value = "/sistema/alocacao")
public class EqAlocacao {

    @Autowired
    private AlocacaoEquipamentoService alocacaoEquipamentoService;

    @GetMapping
    public ResponseEntity<List<AlocacaoDTO>> listarAlocacoes() {
        List<AlocacaoDTO> alocacoes = alocacaoEquipamentoService.listarAlocacoes();
        return new ResponseEntity<>(alocacoes, HttpStatus.OK);
    }

    @GetMapping("/{numeroSerie}")
    public ResponseEntity<AlocacaoDTO> buscarAlocacaoPorNumeroSerie(@PathVariable String numeroSerie) {
        Optional<AlocacaoDTO> alocacaoDTO = alocacaoEquipamentoService.buscarAlocacaoPorNumeroSerie(numeroSerie);
        return alocacaoDTO.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{numeroSerie}")
    public ResponseEntity<AlocacaoDTO> atualizarAlocacaoPorNumeroSerie(@PathVariable String numeroSerie,
            @RequestBody AlocacaoEquipamento dadosAtualizados) {
        Optional<AlocacaoDTO> alocacaoDTO = alocacaoEquipamentoService.atualizarAlocacaoPorNumeroSerie(numeroSerie,
                dadosAtualizados);
        return alocacaoDTO.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{numeroSerie}")
    public ResponseEntity<Void> excluirAlocacaoPorNumeroSerie(@PathVariable String numeroSerie) {
        boolean isDeleted = alocacaoEquipamentoService.excluirAlocacaoPorNumeroSerie(numeroSerie);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/verificar/{numeroSerie}")
    public ResponseEntity<Boolean> verificarSeAlocacaoExiste(@PathVariable String numeroSerie) {
        boolean existe = alocacaoEquipamentoService.verificarSeAlocacaoExiste(numeroSerie);
        return new ResponseEntity<>(existe, HttpStatus.OK);
    }

    @GetMapping("/contar")
    public ResponseEntity<Long> contarAlocacoes() {
        long quantidade = alocacaoEquipamentoService.contarAlocacoes();
        return new ResponseEntity<>(quantidade, HttpStatus.OK);
    }

    @GetMapping("/usuario/{usuario}")
    public ResponseEntity<List<AlocacaoDTO>> buscarAlocacoesPorUsuario(@PathVariable String usuario) {
        List<AlocacaoDTO> alocacoes = alocacaoEquipamentoService.buscarAlocacoesPorUsuario(usuario);
        return new ResponseEntity<>(alocacoes, HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<AlocacaoDTO>> buscarAlocacoesPorStatus(@PathVariable String status) {
        List<AlocacaoDTO> alocacoes = alocacaoEquipamentoService.buscarAlocacoesPorStatus(status);
        return new ResponseEntity<>(alocacoes, HttpStatus.OK);
    }
}