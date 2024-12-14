package cetic.demo.sistema.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cetic.demo.sistema.services.AlocacaoEquipamentoService;
import cetic.demo.sistema.services.AvariaEquipamentoService;
import cetic.demo.sistema.services.EmprestimoEquipamentoService;
import cetic.demo.sistema.services.EquipamentoService;
import cetic.demo.sistema.services.ManutencaoEquipamentoService;
import cetic.demo.sistema.services.RequisicaoEquipamentoService;

@RestController
@RequestMapping(value = "/dashboard")
public class EqDashboard {
    
    
    @Autowired
    private EquipamentoService equipamentoService;

    @Autowired
    private AvariaEquipamentoService avariaEquipamentoService;

    @Autowired
    private AlocacaoEquipamentoService alocacaoEquipamentoService;

    @Autowired
    private ManutencaoEquipamentoService manutencaoEquipamentoService;

    @Autowired
    private EmprestimoEquipamentoService emprestimoEquipamentoService;

    @Autowired
    private RequisicaoEquipamentoService requisicaoEquipamentoService;

    // Contar o total de equipamentos
    @GetMapping("/tEquipamento")
    public ResponseEntity<Long> contarEquipamentos() {
        long totalEquipamentos = equipamentoService.contarEquipamentos();
        return new ResponseEntity<>(totalEquipamentos, HttpStatus.OK);
    }

    // Contar o total de alocações
    @GetMapping("/tAlocao")
    public ResponseEntity<Long> contarAlocacoes() {
        long quantidade = alocacaoEquipamentoService.contarAlocacoes();
        return new ResponseEntity<>(quantidade, HttpStatus.OK);
    }

    // Contar o total de requisições
    @GetMapping("/tRequisicao")
    public long contarRequisicoes() {
        return requisicaoEquipamentoService.contarRequisicoes();
    }

    // Contar o total de manutenções
    @GetMapping("/tManutencao")
    public ResponseEntity<Long> contarTotalManutencoes() {
        long totalManutencoes = manutencaoEquipamentoService.contarTotalManutencoes();
        return new ResponseEntity<>(totalManutencoes, HttpStatus.OK);
    }

    // Contar o total de avarias
    @GetMapping("/tAvaria")
    public long contarAvarias() {
        return avariaEquipamentoService.contarAvarias();
    }

    // Contar o total de empréstimos
    @GetMapping("/tEmprestimo")
    public long contarEmprestimos() {
        return emprestimoEquipamentoService.contarEmprestimos();
    }

    // Contar manutenções PENDENTES
    @GetMapping("/MPendente")
    public long contarManutencaoPendente() {
        return manutencaoEquipamentoService.contarManutencaoPorStatus("PENDENTE");
    }

    // Contar manutenções EM ANDAMENTO
    @GetMapping("/MAndamento")
    public long contarManutencaoEmAndamento() {
        return manutencaoEquipamentoService.contarManutencaoPorStatus("EM_ANDAMENTO");
    }

    // Contar manutenções CONCLUÍDAS
    @GetMapping("/MConcluida")
    public long contarManutencaoConcluida() {
        return manutencaoEquipamentoService.contarManutencaoPorStatus("CONCLUIDO");
    }

    // Contar avarias PENDENTES
    @GetMapping("/APendente")
    public long contarAvariaPendente() {
        return avariaEquipamentoService.contarAvariasPorStatus("PENDENTE");
    }

    // Contar avarias EM ANDAMENTO
    @GetMapping("/AAndamento")
    public long contarAvariaEmAndamento() {
        return avariaEquipamentoService.contarAvariasPorStatus("EM_ANDAMENTO");
    }
}
