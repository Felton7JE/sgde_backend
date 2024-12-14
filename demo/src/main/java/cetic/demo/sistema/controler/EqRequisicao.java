package cetic.demo.sistema.controler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cetic.demo.sistema.dto.RequisicaoDTO;
import cetic.demo.sistema.services.RequisicaoEquipamentoService;


@RestController
@RequestMapping("/sistema/requisicao")
public class EqRequisicao {
    
     @Autowired
    private RequisicaoEquipamentoService requisicaoEquipamentoService;

    @PostMapping
    public ResponseEntity<RequisicaoDTO> salvarRequisicao(@RequestBody RequisicaoDTO requisicaoDTO) {
        RequisicaoDTO requisicaoSalva = requisicaoEquipamentoService.salvarRequisicao(requisicaoDTO);
        return ResponseEntity.ok(requisicaoSalva);
    }

    @GetMapping("/responsavel/{responsavel}")
    public ResponseEntity<List<RequisicaoDTO>> buscarRequisicoesPorResponsavel(@PathVariable String responsavel) {
        List<RequisicaoDTO> requisicoes = requisicaoEquipamentoService.buscarRequisicoesPorResponsavel(responsavel);
        return ResponseEntity.ok(requisicoes);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<RequisicaoDTO>> buscarRequisicoesPorStatus(@PathVariable String status) {
        List<RequisicaoDTO> requisicoes = requisicaoEquipamentoService.buscarRequisicoesPorStatus(status);
        return ResponseEntity.ok(requisicoes);
    }

    @GetMapping
    public ResponseEntity<List<RequisicaoDTO>> buscarTodasRequisicoes() {
        List<RequisicaoDTO> requisicoes = requisicaoEquipamentoService.buscarTodasRequisicoes();
        return ResponseEntity.ok(requisicoes);
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<RequisicaoDTO> atualizarStatus(@PathVariable Long id, @RequestParam String status) {
        RequisicaoDTO requisicaoAtualizada = requisicaoEquipamentoService.atualizarStatus(id, status);
        return ResponseEntity.ok(requisicaoAtualizada);
    }
}
