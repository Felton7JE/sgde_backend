package cetic.demo.sistema.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cetic.demo.sistema.dto.RequisicaoDTO;
import cetic.demo.sistema.entidade.RequisicaoEquipamento;
import cetic.demo.sistema.repository.RequisicaoEquipamentoRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequisicaoEquipamentoService {

    @Autowired
    private RequisicaoEquipamentoRepository requisicaoEquipamentoRepository;

    @Transactional
    public RequisicaoDTO salvarRequisicao(RequisicaoDTO requisicaoDTO) {
        RequisicaoEquipamento requisicaoEquipamento = new RequisicaoEquipamento(requisicaoDTO);
        RequisicaoEquipamento requisicaoSalva = requisicaoEquipamentoRepository.save(requisicaoEquipamento);
        return new RequisicaoDTO(requisicaoSalva);
    }

    public List<RequisicaoDTO> buscarRequisicoesPorResponsavel(String responsavel) {
        List<RequisicaoEquipamento> requisicoes = requisicaoEquipamentoRepository.findByResponsavel(responsavel);
        return requisicoes.stream()
                .map(RequisicaoDTO::new)
                .collect(Collectors.toList());
    }

    public List<RequisicaoDTO> buscarRequisicoesPorStatus(String status) {
        List<RequisicaoEquipamento> requisicoes = requisicaoEquipamentoRepository.findByStatus(status);
        return requisicoes.stream()
                .map(RequisicaoDTO::new)
                .collect(Collectors.toList());
    }

    public List<RequisicaoDTO> buscarTodasRequisicoes() {
        List<RequisicaoEquipamento> requisicoes = requisicaoEquipamentoRepository.findAll();
        return requisicoes.stream()
                .map(RequisicaoDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public RequisicaoDTO atualizarStatus(Long id, String status) {
        RequisicaoEquipamento requisicao = requisicaoEquipamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Requisição não encontrada"));
        requisicao.setStatus(status);
        RequisicaoEquipamento requisicaoAtualizada = requisicaoEquipamentoRepository.save(requisicao);
        return new RequisicaoDTO(requisicaoAtualizada);
    }

    @Transactional
    public boolean removerRequisicao(RequisicaoDTO requisicaoDTO) {
        if (requisicaoDTO.getId() == null) {
            throw new IllegalArgumentException("ID da requisição é obrigatório para remoção.");
        }
        if (requisicaoEquipamentoRepository.existsById(requisicaoDTO.getId())) {
            requisicaoEquipamentoRepository.deleteById(requisicaoDTO.getId());
            return true;
        }
        return false;
    }

    @Transactional
    public RequisicaoDTO atualizarRequisicao(RequisicaoDTO requisicaoDTO) {
        if (requisicaoDTO.getId() == null) {
            throw new IllegalArgumentException("ID da requisição é obrigatório para atualização.");
        }
        RequisicaoEquipamento requisicao = requisicaoEquipamentoRepository.findById(requisicaoDTO.getId())
                .orElseThrow(() -> new RuntimeException("Requisição não encontrada"));
        // Atualiza os campos permitidos
        requisicao.setDataRequisicao(requisicaoDTO.getDataRequisicao());
        requisicao.setQuantidade(requisicaoDTO.getQuantidade());
        requisicao.setJustificativaRequisicao(requisicaoDTO.getJustificativaRequisicao());
        requisicao.setStatus(requisicaoDTO.getStatus());
        requisicao.setStatus2(requisicaoDTO.getStatus2());
        requisicao.setResponsavel(requisicaoDTO.getResponsavel());
        requisicao.setNomeEquipamento(requisicaoDTO.getNomeEquipamento());
        requisicao.setModeloEquipamento(requisicaoDTO.getModeloEquipamento());
        requisicao.setTipoEquipamento(requisicaoDTO.getTipoEquipamento());
        requisicao.setDescricaoEquipamento(requisicaoDTO.getDescricaoEquipamento());
        RequisicaoEquipamento requisicaoAtualizada = requisicaoEquipamentoRepository.save(requisicao);
        return new RequisicaoDTO(requisicaoAtualizada);
    }

    public long contarRequisicoes() {
        return requisicaoEquipamentoRepository.count();
    }
}
