package cetic.demo.sistema.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cetic.demo.sistema.dto.AlocacaoDTO;
import cetic.demo.sistema.entidade.AlocacaoEquipamento;
import cetic.demo.sistema.entidade.Equipamento;
import cetic.demo.sistema.repository.AlocacaoEquipamentoRepository;
import cetic.demo.sistema.repository.EquipamentoRepository;

@Service
public class AlocacaoEquipamentoService {

    @Autowired
    private AlocacaoEquipamentoRepository alocacaoEquipamentoRepository;


    public List<AlocacaoDTO> listarAlocacoes() {
        List<AlocacaoEquipamento> alocacoes = alocacaoEquipamentoRepository.findAll();
        return alocacoes.stream()
                .map(AlocacaoDTO::new)
                .toList();
    }

    public Optional<AlocacaoDTO> buscarAlocacaoPorNumeroSerie(String numeroSerie) {
        Optional<AlocacaoEquipamento> alocacao = alocacaoEquipamentoRepository
                .findByEquipamento_NumeroSerie(numeroSerie);
        return alocacao.map(AlocacaoDTO::new);
    }

    public Optional<AlocacaoDTO> atualizarAlocacaoPorNumeroSerie(String numeroSerie,
            AlocacaoEquipamento dadosAtualizados) {
        Optional<AlocacaoEquipamento> alocacaoExistente = alocacaoEquipamentoRepository
                .findByEquipamento_NumeroSerie(numeroSerie);
        if (alocacaoExistente.isPresent()) {
            AlocacaoEquipamento alocacao = alocacaoExistente.get();
            alocacao.setLocalAlocado(dadosAtualizados.getLocalAlocado());
            alocacao.setUsuario(dadosAtualizados.getUsuario());
            alocacao.setDataAlocacao(dadosAtualizados.getDataAlocacao());
            alocacao.setStatus(dadosAtualizados.getStatus());

            AlocacaoEquipamento alocacaoAtualizada = alocacaoEquipamentoRepository.save(alocacao);
            return Optional.of(new AlocacaoDTO(alocacaoAtualizada));
        }
        return Optional.empty();
    }

    public boolean excluirAlocacaoPorNumeroSerie(String numeroSerie) {
        Optional<AlocacaoEquipamento> alocacaoExistente = alocacaoEquipamentoRepository
                .findByEquipamento_NumeroSerie(numeroSerie);
        if (alocacaoExistente.isPresent()) {
            alocacaoEquipamentoRepository.delete(alocacaoExistente.get());
            return true;
        }
        return false;
    }

    public boolean verificarSeAlocacaoExiste(String numeroSerie) {
        Optional<AlocacaoEquipamento> alocacaoExistente = alocacaoEquipamentoRepository
                .findByEquipamento_NumeroSerie(numeroSerie);
        return alocacaoExistente.isPresent();
    }

    public long contarAlocacoes() {
        return alocacaoEquipamentoRepository.count();
    }

    public List<AlocacaoDTO> buscarAlocacoesPorUsuario(String usuario) {
        List<AlocacaoEquipamento> alocacoes = alocacaoEquipamentoRepository.findByUsuario(usuario);
        return alocacoes.stream()
                .map(AlocacaoDTO::new)
                .toList();
    }

    public List<AlocacaoDTO> buscarAlocacoesPorStatus(String status) {
        List<AlocacaoEquipamento> alocacoes = alocacaoEquipamentoRepository.findByStatus(status);
        return alocacoes.stream()
                .map(AlocacaoDTO::new)
                .toList();
    }

}
