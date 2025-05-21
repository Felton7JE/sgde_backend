package cetic.demo.sistema.controler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @PostMapping("/responsavel")
    public ResponseEntity<List<RequisicaoDTO>> buscarRequisicoesPorResponsavel(@RequestBody RequisicaoDTO requisicaoDTO) {
        List<RequisicaoDTO> requisicoes = requisicaoEquipamentoService.buscarRequisicoesPorResponsavel(requisicaoDTO.getResponsavel());
        return ResponseEntity.ok(requisicoes);
    }

    @PostMapping("/status")
    public ResponseEntity<List<RequisicaoDTO>> buscarRequisicoesPorStatus(@RequestBody RequisicaoDTO requisicaoDTO) {
        List<RequisicaoDTO> requisicoes = requisicaoEquipamentoService.buscarRequisicoesPorStatus(requisicaoDTO.getStatus());
        return ResponseEntity.ok(requisicoes);
    }

    @GetMapping
    public ResponseEntity<List<RequisicaoDTO>> buscarTodasRequisicoes() {
        List<RequisicaoDTO> requisicoes = requisicaoEquipamentoService.buscarTodasRequisicoes();
        return ResponseEntity.ok(requisicoes);
    }

   
    @PutMapping("/status")
    public ResponseEntity<RequisicaoDTO> atualizarStatus(@RequestBody RequisicaoDTO requisicaoDTO) {
        RequisicaoDTO requisicaoAtualizada = requisicaoEquipamentoService.atualizarStatus(requisicaoDTO.getId(), requisicaoDTO.getStatus());
        return ResponseEntity.ok(requisicaoAtualizada);
    }

    @PutMapping
    public ResponseEntity<RequisicaoDTO> atualizarRequisicao(@RequestBody RequisicaoDTO requisicaoDTO) {
        RequisicaoDTO requisicaoAtualizada = requisicaoEquipamentoService.atualizarRequisicao(requisicaoDTO);
        return ResponseEntity.ok(requisicaoAtualizada);
    }

    @PostMapping("/remover")
    public ResponseEntity<Void> removerRequisicao(@RequestBody RequisicaoDTO requisicaoDTO) {
        boolean removido = requisicaoEquipamentoService.removerRequisicao(requisicaoDTO);
        if (removido) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
