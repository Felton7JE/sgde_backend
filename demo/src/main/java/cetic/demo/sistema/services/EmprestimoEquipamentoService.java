package cetic.demo.sistema.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cetic.demo.sistema.dto.EmprestimoDTO;
import cetic.demo.sistema.entidade.EmprestimoEquipamento;
import cetic.demo.sistema.entidade.Equipamento;
import cetic.demo.sistema.repository.EmprestimoEquipamentoRepository;
import cetic.demo.sistema.repository.EquipamentoRepository;

@Service
public class EmprestimoEquipamentoService {

    @Autowired
    private EmprestimoEquipamentoRepository emprestimoEquipamentoRepository;

    @Autowired
    private EquipamentoRepository equipamentoRepository;

    // Salvar um empréstimo
    public EmprestimoDTO salvarEmprestimo(String numeroSerie, EmprestimoEquipamento emprestimoEquipamento) {
        Equipamento equipamento = equipamentoRepository.findByNumeroSerie(numeroSerie)
                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado"));

        emprestimoEquipamento.setEquipamento(equipamento);
        EmprestimoEquipamento emprestimoSalvo = emprestimoEquipamentoRepository.save(emprestimoEquipamento);

        return new EmprestimoDTO(emprestimoSalvo);
    }

    // Listar todos os empréstimos
    public List<EmprestimoDTO> listarEmprestimos() {
        List<EmprestimoEquipamento> emprestimos = emprestimoEquipamentoRepository.findAll();
        return emprestimos.stream()
                .map(EmprestimoDTO::new)
                .toList();
    }

    // Buscar empréstimo por número de série
    public Optional<EmprestimoDTO> buscarEmprestimoPorNumeroSerie(String numeroSerie) {
        Optional<EmprestimoEquipamento> emprestimo = emprestimoEquipamentoRepository
                .findByEquipamento_NumeroSerie(numeroSerie);
        return emprestimo.map(EmprestimoDTO::new);
    }

    // Atualizar empréstimo por número de série
    public Optional<EmprestimoDTO> atualizarEmprestimoPorNumeroSerie(String numeroSerie,
            EmprestimoEquipamento dadosAtualizados) {
        Optional<EmprestimoEquipamento> emprestimoExistente = emprestimoEquipamentoRepository
                .findByEquipamento_NumeroSerie(numeroSerie);
        if (emprestimoExistente.isPresent()) {
            EmprestimoEquipamento emprestimo = emprestimoExistente.get();
            emprestimo.setResponsavel(dadosAtualizados.getResponsavel());
            emprestimo.setDataEmprestimo(dadosAtualizados.getDataEmprestimo());
            emprestimo.setDataDevolucao(dadosAtualizados.getDataDevolucao());
            emprestimo.setStatus(dadosAtualizados.getStatus());

            EmprestimoEquipamento emprestimoAtualizado = emprestimoEquipamentoRepository.save(emprestimo);
            return Optional.of(new EmprestimoDTO(emprestimoAtualizado));
        }
        return Optional.empty();
    }

    // Excluir empréstimo por número de série
    public boolean excluirEmprestimoPorNumeroSerie(String numeroSerie) {
        Optional<EmprestimoEquipamento> emprestimoExistente = emprestimoEquipamentoRepository
                .findByEquipamento_NumeroSerie(numeroSerie);
        if (emprestimoExistente.isPresent()) {
            emprestimoEquipamentoRepository.delete(emprestimoExistente.get());
            return true;
        }
        return false;
    }

    // Verificar se o empréstimo existe por número de série
    public boolean verificarSeEmprestimoExiste(String numeroSerie) {
        Optional<EmprestimoEquipamento> emprestimoExistente = emprestimoEquipamentoRepository
                .findByEquipamento_NumeroSerie(numeroSerie);
        return emprestimoExistente.isPresent();
    }

    // Contar o número de empréstimos
    public long contarEmprestimos() {
        return emprestimoEquipamentoRepository.count();
    }

    // Buscar empréstimos por responsável
    public List<EmprestimoDTO> buscarEmprestimosPorResponsavel(String responsavel) {
        List<EmprestimoEquipamento> emprestimos = emprestimoEquipamentoRepository.findByResponsavel(responsavel);
        return emprestimos.stream()
                .map(EmprestimoDTO::new)
                .toList();
    }

    // Buscar empréstimos por status
    public List<EmprestimoDTO> buscarEmprestimosPorStatus(String status) {
        List<EmprestimoEquipamento> emprestimos = emprestimoEquipamentoRepository.findByStatus(status);
        return emprestimos.stream()
                .map(EmprestimoDTO::new)
                .toList();
    }
}
