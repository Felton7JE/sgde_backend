package cetic.demo.sistema.controler;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cetic.demo.sistema.dto.EmprestimoDTO;
import cetic.demo.sistema.entidade.EmprestimoEquipamento;
import cetic.demo.sistema.services.EmprestimoEquipamentoService;

@RestController
@RequestMapping("/sistema/emprestimos")
public class EqEmprestimos {
    
     @Autowired
    private EmprestimoEquipamentoService emprestimoEquipamentoService;

    // Salvar um empréstimo
    @PostMapping("/{numeroSerie}")
    public ResponseEntity<EmprestimoDTO> salvarEmprestimo(@PathVariable String numeroSerie, 
                                                          @RequestBody EmprestimoEquipamento emprestimoEquipamento) {
        EmprestimoDTO emprestimoDTO = emprestimoEquipamentoService.salvarEmprestimo(numeroSerie, emprestimoEquipamento);
        return ResponseEntity.ok(emprestimoDTO);
    }

    // Listar todos os empréstimos
    @GetMapping
    public ResponseEntity<List<EmprestimoDTO>> listarEmprestimos() {
        List<EmprestimoDTO> emprestimos = emprestimoEquipamentoService.listarEmprestimos();
        return ResponseEntity.ok(emprestimos);
    }

    // Buscar empréstimo por número de série
    @GetMapping("/{numeroSerie}")
    public ResponseEntity<EmprestimoDTO> buscarEmprestimoPorNumeroSerie(@PathVariable String numeroSerie) {
        Optional<EmprestimoDTO> emprestimoDTO = emprestimoEquipamentoService.buscarEmprestimoPorNumeroSerie(numeroSerie);
        return emprestimoDTO.map(ResponseEntity::ok)
                            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Atualizar empréstimo por número de série
    @PutMapping("/{numeroSerie}")
    public ResponseEntity<EmprestimoDTO> atualizarEmprestimoPorNumeroSerie(@PathVariable String numeroSerie,
                                                                            @RequestBody EmprestimoEquipamento dadosAtualizados) {
        Optional<EmprestimoDTO> emprestimoDTO = emprestimoEquipamentoService.atualizarEmprestimoPorNumeroSerie(numeroSerie, dadosAtualizados);
        return emprestimoDTO.map(ResponseEntity::ok)
                            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Excluir empréstimo por número de série
    @DeleteMapping("/{numeroSerie}")
    public ResponseEntity<Void> excluirEmprestimoPorNumeroSerie(@PathVariable String numeroSerie) {
        boolean excluido = emprestimoEquipamentoService.excluirEmprestimoPorNumeroSerie(numeroSerie);
        return excluido ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Verificar se o empréstimo existe
    @GetMapping("/verificar/{numeroSerie}")
    public ResponseEntity<Void> verificarSeEmprestimoExiste(@PathVariable String numeroSerie) {
        boolean existe = emprestimoEquipamentoService.verificarSeEmprestimoExiste(numeroSerie);
        return existe ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    // Contar o número de empréstimos
    @GetMapping("/contagem")
    public ResponseEntity<Long> contarEmprestimos() {
        long contagem = emprestimoEquipamentoService.contarEmprestimos();
        return ResponseEntity.ok(contagem);
    }

    // Buscar empréstimos por responsável
    @GetMapping("/responsavel/{responsavel}")
    public ResponseEntity<List<EmprestimoDTO>> buscarEmprestimosPorResponsavel(@PathVariable String responsavel) {
        List<EmprestimoDTO> emprestimos = emprestimoEquipamentoService.buscarEmprestimosPorResponsavel(responsavel);
        return ResponseEntity.ok(emprestimos);
    }

    // Buscar empréstimos por status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<EmprestimoDTO>> buscarEmprestimosPorStatus(@PathVariable String status) {
        List<EmprestimoDTO> emprestimos = emprestimoEquipamentoService.buscarEmprestimosPorStatus(status);
        return ResponseEntity.ok(emprestimos);
    }
}
