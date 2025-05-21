package cetic.demo.sistema.controler;

import java.util.List;
import java.util.Optional;

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

import cetic.demo.sistema.dto.AlocacaoDTO;
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



    @PutMapping
    public ResponseEntity<AlocacaoDTO> atualizarAlocacaoPorNumeroSerie(@RequestBody AlocacaoDTO alocacaoDTO) {
        Optional<AlocacaoDTO> alocacao = alocacaoEquipamentoService.atualizarAlocacao(alocacaoDTO);
        return alocacao.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping
    public ResponseEntity<Void> excluirAlocacaoPorNumeroSerie(@RequestBody AlocacaoDTO alocacaoDTO) {
        boolean isDeleted = alocacaoEquipamentoService.excluirAlocacao(alocacaoDTO);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/verificar/{numeroSerie}")
    public ResponseEntity<Boolean> verificarSeAlocacaoExiste(@PathVariable String numeroSerie) {
        AlocacaoDTO alocacaoDTO = new AlocacaoDTO();
        alocacaoDTO.setNumeroSerie(numeroSerie);
        boolean existe = alocacaoEquipamentoService.verificarSeAlocacaoExiste(alocacaoDTO);
        return new ResponseEntity<>(existe, HttpStatus.OK);
    }

    @GetMapping("/contar")
    public ResponseEntity<Long> contarAlocacoes() {
        long quantidade = alocacaoEquipamentoService.contarAlocacoes();
        return new ResponseEntity<>(quantidade, HttpStatus.OK);
    }

    @PostMapping("/usuario")
    public ResponseEntity<List<AlocacaoDTO>> buscarAlocacoesPorUsuario(@RequestBody AlocacaoDTO alocacaoDTO) {
        List<AlocacaoDTO> alocacoes = alocacaoEquipamentoService.buscarAlocacoesPorUsuario(alocacaoDTO);
        return new ResponseEntity<>(alocacoes, HttpStatus.OK);
    }

    @PostMapping("/status")
    public ResponseEntity<List<AlocacaoDTO>> buscarAlocacoesPorStatus(@RequestBody AlocacaoDTO alocacaoDTO) {
        List<AlocacaoDTO> alocacoes = alocacaoEquipamentoService.buscarAlocacoesPorStatus(alocacaoDTO);
        return new ResponseEntity<>(alocacoes, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<AlocacaoDTO> adicionarAlocacao(@RequestBody AlocacaoDTO alocacaoDTO) {
        AlocacaoDTO alocacao = alocacaoEquipamentoService.salvarAlocacao(alocacaoDTO);
        if (alocacao != null) {
            return new ResponseEntity<>(alocacao, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}