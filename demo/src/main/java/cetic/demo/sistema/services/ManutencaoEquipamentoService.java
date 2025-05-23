package cetic.demo.sistema.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cetic.demo.sistema.dto.ManutencaoDTO;
import cetic.demo.sistema.entidade.AvariaEquipamento;
import cetic.demo.sistema.entidade.Equipamento;
import cetic.demo.sistema.entidade.ManutencaoEquipamento;
import cetic.demo.sistema.enums.StatusAvaria;
import cetic.demo.sistema.repository.AvariaEquipamentoRepository;
import cetic.demo.sistema.repository.EquipamentoRepository;
import cetic.demo.sistema.repository.ManutencaoEquipamentoRepository;

@Service
public class ManutencaoEquipamentoService {

    @Autowired
    private ManutencaoEquipamentoRepository manutencaoEquipamentoRepository;

    @Autowired
    private EquipamentoRepository equipamentoRepository;

    @Autowired
    private AvariaEquipamentoRepository avariaEquipamentoRepository;

    // Salvar manutenção
    public ManutencaoDTO criarManutencao(ManutencaoDTO manutencaoDTO) {
        Equipamento equipamento = equipamentoRepository.findByNumeroSerie(manutencaoDTO.getNumeroSerie())
                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado"));

        AvariaEquipamento avariaEquipamento = avariaEquipamentoRepository
                .findByEquipamentoAndStatus(equipamento, StatusAvaria.AVARIADO);

        if (avariaEquipamento == null) {
            throw new RuntimeException("O equipamento precisa ter uma avaria pendente ou em andamento para ser colocado em manutenção.");
        }

        ManutencaoEquipamento manutencaoEquipamento = new ManutencaoEquipamento();
        manutencaoEquipamento.setEquipamento(equipamento);
        manutencaoEquipamento.setTipoManutencao(manutencaoDTO.getTipoManutencao());
        manutencaoEquipamento.setDataManutencao(LocalDate.now());
        manutencaoEquipamento.setDescricaoManutencao(manutencaoDTO.getDescricaoManutencao());
        manutencaoEquipamento.setResponsavel(manutencaoDTO.getResponsavel());
        manutencaoEquipamento.setStatus("EM MANUTENÇÃO");
        equipamento.setStatus(manutencaoEquipamento.getStatus());
        equipamentoRepository.save(equipamento);
        manutencaoEquipamento.setTempoInatividade(manutencaoDTO.getTempoInatividade());

        ManutencaoEquipamento manutencaoSalva = manutencaoEquipamentoRepository.save(manutencaoEquipamento);
        return new ManutencaoDTO(manutencaoSalva);
    }

    public long contarTotalManutencoes() {
        return manutencaoEquipamentoRepository.count();
    }

    public long contarManutencaoPorStatus(String status) {
        return manutencaoEquipamentoRepository.countByStatus(status);
    }

    public List<ManutencaoDTO> listarManutencoesPorEquipamento(ManutencaoDTO manutencaoDTO) {
        Equipamento equipamento = equipamentoRepository.findByNumeroSerie(manutencaoDTO.getNumeroSerie())
                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado"));

        return manutencaoEquipamentoRepository.findByEquipamento(equipamento).stream()
                .map(ManutencaoDTO::new)
                .toList();
    }

    public Optional<ManutencaoDTO> buscarManutencao(ManutencaoDTO manutencaoDTO) {
        Equipamento equipamento = equipamentoRepository.findByNumeroSerie(manutencaoDTO.getNumeroSerie())
                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado"));

        return manutencaoEquipamentoRepository.findByEquipamento(equipamento).stream()
                .findFirst()
                .map(ManutencaoDTO::new);
    }

    public Optional<ManutencaoDTO> atualizarManutencao(ManutencaoDTO manutencaoDTO) {
        Optional<ManutencaoEquipamento> manutencaoExistente = manutencaoEquipamentoRepository
                .findByEquipamento(equipamentoRepository.findByNumeroSerie(manutencaoDTO.getNumeroSerie())
                        .orElse(null))
                .stream()
                .findFirst();

        if (manutencaoExistente.isPresent()) {
            ManutencaoEquipamento manutencao = manutencaoExistente.get();

            manutencao.setTipoManutencao(manutencaoDTO.getTipoManutencao());
            manutencao.setDataManutencao(manutencaoDTO.getDataManutencao());
            manutencao.setDescricaoManutencao(manutencaoDTO.getDescricaoManutencao());
            manutencao.setResponsavel(manutencaoDTO.getResponsavel());
            manutencao.setStatus(manutencaoDTO.getStatus());
            manutencao.setTempoInatividade(manutencaoDTO.getTempoInatividade());

            // Atualiza o status do equipamento também
            Equipamento equipamento = manutencao.getEquipamento();
            equipamento.setStatus(manutencaoDTO.getStatus());
            equipamentoRepository.save(equipamento);

            // Se a manutenção foi concluída, atualizar status da avaria para CONCLUIDO
            if ("Concluído".equalsIgnoreCase(manutencaoDTO.getStatus()) || "RESOLVIDO".equalsIgnoreCase(manutencaoDTO.getStatus())) {
                AvariaEquipamento avaria = avariaEquipamentoRepository.findByEquipamentoAndStatus(equipamento, StatusAvaria.AVARIADO);
                if (avaria != null) {
                    avaria.setStatus(StatusAvaria.CONCLUIDO); // valor correto da enum
                    avariaEquipamentoRepository.save(avaria);
                }
            }

            ManutencaoEquipamento manutencaoAtualizada = manutencaoEquipamentoRepository.save(manutencao);
            return Optional.of(new ManutencaoDTO(manutencaoAtualizada));
        }
        return Optional.empty();
    }

    public List<ManutencaoDTO> listarManutencao() {
        return manutencaoEquipamentoRepository.findAll().stream()
                .map(ManutencaoDTO::new)
                .toList();
    }

    public boolean removerManutencao(ManutencaoDTO manutencaoDTO) {
        Equipamento equipamento = equipamentoRepository.findByNumeroSerie(manutencaoDTO.getNumeroSerie())
                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado"));

        Optional<ManutencaoEquipamento> manutencao = manutencaoEquipamentoRepository
                .findByEquipamento(equipamento)
                .stream()
                .findFirst();

        if (manutencao.isPresent()) {
            manutencaoEquipamentoRepository.delete(manutencao.get());
            return true;
        }
        return false;
    }
}
