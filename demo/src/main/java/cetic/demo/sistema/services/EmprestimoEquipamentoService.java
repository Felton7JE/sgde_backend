package cetic.demo.sistema.services;

import java.time.LocalDate;
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

    public EmprestimoDTO salvarEmprestimo(EmprestimoDTO emprestimoDTO) {
        Equipamento equipamento = equipamentoRepository.findByNumeroSerie(emprestimoDTO.getNumeroSerie())
                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado"));

        EmprestimoEquipamento emprestimoEquipamento = new EmprestimoEquipamento();
        emprestimoEquipamento.setEquipamento(equipamento);
        emprestimoEquipamento.setDataEmprestimo(LocalDate.now());
        emprestimoEquipamento.setQuantidade(emprestimoDTO.getQuantidade());
        emprestimoEquipamento.setDataDevolucao(emprestimoDTO.getDataDevolucao());
        emprestimoEquipamento.setJustificativaEmprestimo(emprestimoDTO.getJustificativaEmprestimo());
        emprestimoEquipamento.setStatus(equipamento.getStatus());
        emprestimoEquipamento.setResponsavel(emprestimoDTO.getResponsavel());
        emprestimoEquipamento.setStatus2("EMPRESTADO");

        equipamento.setStatus2(emprestimoEquipamento.getStatus2());
        equipamentoRepository.save(equipamento); // Salva o equipamento atualizado
        emprestimoEquipamento.setQuemFezEmprestimo(emprestimoDTO.getQuemFezEmprestimo());

        EmprestimoEquipamento emprestimoSalvo = emprestimoEquipamentoRepository.save(emprestimoEquipamento);
        return new EmprestimoDTO(emprestimoSalvo);
    }

    public List<EmprestimoDTO> listarEmprestimos() {
        return emprestimoEquipamentoRepository.findAll()
                .stream()
                .map(EmprestimoDTO::new)
                .toList();
    }

    public Optional<EmprestimoDTO> buscarEmprestimoPorNumeroSerie(EmprestimoDTO emprestimoDTO) {
        return emprestimoEquipamentoRepository.findByEquipamento_NumeroSerie(emprestimoDTO.getNumeroSerie())
                .map(EmprestimoDTO::new);
    }

    public Optional<EmprestimoDTO> atualizarEmprestimoPorNumeroSerie(EmprestimoDTO emprestimoDTO) {
        Optional<EmprestimoEquipamento> emprestimoExistente = emprestimoEquipamentoRepository
                .findByEquipamento_NumeroSerie(emprestimoDTO.getNumeroSerie());

        if (emprestimoExistente.isPresent()) {
            EmprestimoEquipamento emprestimo = emprestimoExistente.get();

            emprestimo.setResponsavel(emprestimoDTO.getResponsavel());
            emprestimo.setDataEmprestimo(emprestimoDTO.getDataEmprestimo());
            emprestimo.setDataDevolucao(emprestimoDTO.getDataDevolucao());
            emprestimo.setJustificativaEmprestimo(emprestimoDTO.getJustificativaEmprestimo());
            emprestimo.setStatus(emprestimoDTO.getStatus());
            emprestimo.setQuantidade(emprestimoDTO.getQuantidade());
            emprestimo.setQuemFezEmprestimo(emprestimoDTO.getQuemFezEmprestimo());

            EmprestimoEquipamento emprestimoAtualizado = emprestimoEquipamentoRepository.save(emprestimo);
            return Optional.of(new EmprestimoDTO(emprestimoAtualizado));
        }

        return Optional.empty();
    }

    public boolean excluirEmprestimoPorNumeroSerie(EmprestimoDTO emprestimoDTO) {
        Optional<EmprestimoEquipamento> emprestimoExistente = emprestimoEquipamentoRepository
                .findByEquipamento_NumeroSerie(emprestimoDTO.getNumeroSerie());

        if (emprestimoExistente.isPresent()) {
            emprestimoEquipamentoRepository.delete(emprestimoExistente.get());
            return true;
        }

        return false;
    }

    public boolean verificarSeEmprestimoExiste(EmprestimoDTO emprestimoDTO) {
        return emprestimoEquipamentoRepository
                .findByEquipamento_NumeroSerie(emprestimoDTO.getNumeroSerie())
                .isPresent();
    }

    public long contarEmprestimos() {
        return emprestimoEquipamentoRepository.count();
    }

    public List<EmprestimoDTO> buscarEmprestimosPorResponsavel(EmprestimoDTO emprestimoDTO) {
        return emprestimoEquipamentoRepository.findByResponsavel(emprestimoDTO.getResponsavel())
                .stream()
                .map(EmprestimoDTO::new)
                .toList();
    }

    public List<EmprestimoDTO> buscarEmprestimosPorStatus(EmprestimoDTO emprestimoDTO) {
        return emprestimoEquipamentoRepository.findByStatus(emprestimoDTO.getStatus())
                .stream()
                .map(EmprestimoDTO::new)
                .toList();
    }
}
