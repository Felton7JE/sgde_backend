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

import cetic.demo.sistema.dto.EmprestimoDTO;
import cetic.demo.sistema.services.EmprestimoEquipamentoService;

@RestController
@RequestMapping("/sistema/emprestimos")
public class EqEmprestimos {
    
     @Autowired
    private EmprestimoEquipamentoService emprestimoEquipamentoService;

 
       @PostMapping
    public ResponseEntity<EmprestimoDTO> criarEmprestimo(@RequestBody EmprestimoDTO emprestimoDTO) {
        EmprestimoDTO emprestimoSalvo = emprestimoEquipamentoService.salvarEmprestimo(emprestimoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(emprestimoSalvo);
    }

    // Listar todos os empréstimos
    @GetMapping
    public ResponseEntity<List<EmprestimoDTO>> listarEmprestimos() {
        List<EmprestimoDTO> emprestimos = emprestimoEquipamentoService.listarEmprestimos();
        return ResponseEntity.ok(emprestimos);
    }

    // Buscar empréstimo por número de série
    @PostMapping("/buscar")
    public ResponseEntity<EmprestimoDTO> buscarEmprestimoPorNumeroSerie(@RequestBody EmprestimoDTO emprestimoDTO) {
        Optional<EmprestimoDTO> resultado = emprestimoEquipamentoService.buscarEmprestimoPorNumeroSerie(emprestimoDTO);
        return resultado.map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Atualizar empréstimo por número de série
    @PutMapping
    public ResponseEntity<EmprestimoDTO> atualizarEmprestimoPorNumeroSerie(@RequestBody EmprestimoDTO emprestimoDTO) {
        Optional<EmprestimoDTO> resultado = emprestimoEquipamentoService.atualizarEmprestimoPorNumeroSerie(emprestimoDTO);
        return resultado.map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Excluir empréstimo por número de série
    @DeleteMapping("/excluir")
    public ResponseEntity<Void> excluirEmprestimoPorNumeroSerie(@RequestBody EmprestimoDTO emprestimoDTO) {
        boolean excluido = emprestimoEquipamentoService.excluirEmprestimoPorNumeroSerie(emprestimoDTO);
        return excluido ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Verificar se o empréstimo existe
    @GetMapping("/verificar/{numeroSerie}")
    public ResponseEntity<Boolean> verificarSeEmprestimoExiste(@PathVariable String numeroSerie) {
        EmprestimoDTO emprestimoDTO = new EmprestimoDTO();
        emprestimoDTO.setNumeroSerie(numeroSerie);
        boolean existe = emprestimoEquipamentoService.verificarSeEmprestimoExiste(emprestimoDTO);
        return ResponseEntity.ok(existe);
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
        EmprestimoDTO emprestimoDTO = new EmprestimoDTO();
        emprestimoDTO.setResponsavel(responsavel);
        List<EmprestimoDTO> emprestimos = emprestimoEquipamentoService.buscarEmprestimosPorResponsavel(emprestimoDTO);
        return ResponseEntity.ok(emprestimos);
    }

    // Buscar empréstimos por status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<EmprestimoDTO>> buscarEmprestimosPorStatus(@PathVariable String status) {
        EmprestimoDTO emprestimoDTO = new EmprestimoDTO();
        emprestimoDTO.setStatus(status);
        List<EmprestimoDTO> emprestimos = emprestimoEquipamentoService.buscarEmprestimosPorStatus(emprestimoDTO);
        return ResponseEntity.ok(emprestimos);
    }
}
