package cetic.demo.sistema.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cetic.demo.sistema.dto.AlocacaoDTO;
import cetic.demo.sistema.entidade.AlocacaoEquipamento;
import cetic.demo.sistema.entidade.Equipamento;
import cetic.demo.sistema.repository.EquipamentoRepository;
import cetic.demo.sistema.repository.AlocacaoEquipamentoRepository;

@Service
public class AlocacaoEquipamentoService {

    @Autowired
    private AlocacaoEquipamentoRepository alocacaoEquipamentoRepository;

    @Autowired
    private EquipamentoRepository equipamentoRepository; // Added EquipamentoRepository

    // Salvar uma alocação (seguindo o padrão Avaria/Emprestimo/Manutencao)
    public AlocacaoDTO salvarAlocacao(AlocacaoDTO alocacaoDTO) {
        // Assume que AlocacaoDTO tem um método getNumeroSerie()
        Equipamento equipamento = equipamentoRepository.findByNumeroSerie(alocacaoDTO.getNumeroSerie())
                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado"));

        AlocacaoEquipamento alocacaoEquipamento = new AlocacaoEquipamento();
        alocacaoEquipamento.setEquipamento(equipamento);

        // Popula os campos do AlocacaoEquipamento a partir do AlocacaoDTO
        alocacaoEquipamento.setLocalAlocado(alocacaoDTO.getLocalAlocado());
        alocacaoEquipamento.setUsuario(alocacaoDTO.getUsuario());
        alocacaoEquipamento.setDataAlocacao(LocalDate.now());
        alocacaoEquipamento.setStatus(equipamento.getStatus());
        alocacaoEquipamento.setStatus2("ALOCADO");
        equipamento.setStatus2(alocacaoEquipamento.getStatus2());
        equipamentoRepository.save(equipamento); // Salva o equipamento atualizado

        AlocacaoEquipamento alocacaoSalva = alocacaoEquipamentoRepository.save(alocacaoEquipamento);
        return new AlocacaoDTO(alocacaoSalva);
    }

    public List<AlocacaoDTO> listarAlocacoes() {
        List<AlocacaoEquipamento> alocacoes = alocacaoEquipamentoRepository.findAll();
        return alocacoes.stream()
                .map(AlocacaoDTO::new)
                .toList();
    }

   public Optional<AlocacaoDTO> buscarAlocacao(AlocacaoDTO alocacaoDTO) {
    return alocacaoEquipamentoRepository
            .findByEquipamento_NumeroSerie(alocacaoDTO.getNumeroSerie())
            .map(AlocacaoDTO::new);
}

public Optional<AlocacaoDTO> atualizarAlocacao(AlocacaoDTO alocacaoDTO) {
    Optional<AlocacaoEquipamento> alocacaoExistente = alocacaoEquipamentoRepository
            .findByEquipamento_NumeroSerie(alocacaoDTO.getNumeroSerie());

    if (alocacaoExistente.isPresent()) {
        AlocacaoEquipamento alocacao = alocacaoExistente.get();

        // Atualiza campos permitidos (você pode ajustar conforme necessidade)
        if (alocacaoDTO.getLocalAlocado() != null) {
            alocacao.setLocalAlocado(alocacaoDTO.getLocalAlocado());
        }
        if (alocacaoDTO.getUsuario() != null) {
            alocacao.setUsuario(alocacaoDTO.getUsuario());
        }
        if (alocacaoDTO.getDataAlocacao() != null) {
            alocacao.setDataAlocacao(alocacaoDTO.getDataAlocacao());
        }
        if (alocacaoDTO.getStatus() != null) {
            alocacao.setStatus(alocacaoDTO.getStatus());
        }

        AlocacaoEquipamento alocacaoAtualizada = alocacaoEquipamentoRepository.save(alocacao);
        return Optional.of(new AlocacaoDTO(alocacaoAtualizada));
    }
    return Optional.empty();
}

public boolean excluirAlocacao(AlocacaoDTO alocacaoDTO) {
    Optional<AlocacaoEquipamento> alocacaoExistente = alocacaoEquipamentoRepository
            .findByEquipamento_NumeroSerie(alocacaoDTO.getNumeroSerie());

    if (alocacaoExistente.isPresent()) {
        alocacaoEquipamentoRepository.delete(alocacaoExistente.get());
        return true;
    }
    return false;
}

public boolean verificarSeAlocacaoExiste(AlocacaoDTO alocacaoDTO) {
    return alocacaoEquipamentoRepository
            .findByEquipamento_NumeroSerie(alocacaoDTO.getNumeroSerie())
            .isPresent();
}

public long contarAlocacoes() {
    return alocacaoEquipamentoRepository.count();
}

public List<AlocacaoDTO> buscarAlocacoesPorUsuario(AlocacaoDTO alocacaoDTO) {
    List<AlocacaoEquipamento> alocacoes = alocacaoEquipamentoRepository.findByUsuario(alocacaoDTO.getUsuario());
    return alocacoes.stream()
            .map(AlocacaoDTO::new)
            .toList();
}

public List<AlocacaoDTO> buscarAlocacoesPorStatus(AlocacaoDTO alocacaoDTO) {
    List<AlocacaoEquipamento> alocacoes = alocacaoEquipamentoRepository.findByStatus(alocacaoDTO.getStatus());
    return alocacoes.stream()
            .map(AlocacaoDTO::new)
            .toList();
}

}
