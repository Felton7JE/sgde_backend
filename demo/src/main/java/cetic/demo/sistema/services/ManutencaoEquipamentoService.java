package cetic.demo.sistema.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cetic.demo.sistema.dto.ManutencaoDTO;
import cetic.demo.sistema.entidade.AvariaEquipamento;
import cetic.demo.sistema.entidade.Equipamento;
import cetic.demo.sistema.entidade.ManutencaoEquipamento;
import cetic.demo.sistema.enums.StatusAvaria;
import cetic.demo.sistema.enums.StatusManutencao;
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

        // Salvar manutenção pelo número de série
        public ManutencaoEquipamento criarManutencao(String numeroSerie, ManutencaoEquipamento manutencao) {
                Equipamento equipamento = equipamentoRepository.findByNumeroSerie(numeroSerie)
                        .orElseThrow(() -> new RuntimeException("Equipamento não encontrado"));
        
                AvariaEquipamento avariaEquipamento = avariaEquipamentoRepository.findByEquipamentoAndStatus(equipamento, StatusAvaria.PENDENTE);
        
                if (avariaEquipamento == null) {
                    throw new RuntimeException("O equipamento precisa ter uma avaria pendente ou em andamento para ser colocado em manutenção.");
                }
        
                manutencao.setEquipamento(equipamento);
        
                return manutencaoEquipamentoRepository.save(manutencao);
            }
        public long contarTotalManutencoes() {
                return manutencaoEquipamentoRepository.count();
        }

        // Método para contar manutenções por status
        public long contarManutencaoPorStatus(String status) {
                return manutencaoEquipamentoRepository.countByStatus(status);
        }

        // Listar todas as manutenções pelo número de série
        public List<ManutencaoDTO> listarManutencoesPorEquipamento(String numeroSerie) {
                Equipamento equipamento = equipamentoRepository.findByNumeroSerie(numeroSerie)
                                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado"));

                return manutencaoEquipamentoRepository.findByEquipamento(equipamento).stream()
                                .map(ManutencaoDTO::new)
                                .toList();
        }

        // Buscar manutenção específica pelo número de série
        public Optional<ManutencaoDTO> buscarManutencao(String numeroSerie) {
                Equipamento equipamento = equipamentoRepository.findByNumeroSerie(numeroSerie)
                                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado"));

                return manutencaoEquipamentoRepository.findByEquipamento(equipamento).stream()
                                .findFirst()
                                .map(ManutencaoDTO::new);
        }

        // Atualizar manutenção pelo número de série
        public Optional<ManutencaoDTO> atualizarManutencao(String numeroSerie, ManutencaoEquipamento dadosAtualizados) {
                Equipamento equipamento = equipamentoRepository.findByNumeroSerie(numeroSerie)
                                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado"));

                // Encontrar a manutenção mais recente para o equipamento
                Optional<ManutencaoEquipamento> manutencaoExistente = manutencaoEquipamentoRepository
                                .findByEquipamento(equipamento)
                                .stream()
                                .findFirst(); // Ajuste dependendo de como você quer buscar a manutenção (mais recente
                                              // ou específica)

                if (manutencaoExistente.isPresent()) {
                        ManutencaoEquipamento manutencao = manutencaoExistente.get();

                        // Atualiza os dados da manutenção
                        manutencao.setTipoManutencao(dadosAtualizados.getTipoManutencao());
                        manutencao.setDataManutencao(dadosAtualizados.getDataManutencao());
                        manutencao.setDescricaoManutencao(dadosAtualizados.getDescricaoManutencao());
                        manutencao.setResponsavel(dadosAtualizados.getResponsavel());
                        manutencao.setStatus(dadosAtualizados.getStatus());
                        manutencao.setTempoInatividade(dadosAtualizados.getTempoInatividade());

                        ManutencaoEquipamento manutencaoAtualizada = manutencaoEquipamentoRepository.save(manutencao);

                        return Optional.of(new ManutencaoDTO(manutencaoAtualizada));
                }

                return Optional.empty();
        }

        // Listar todas as manutenções
        public List<ManutencaoDTO> listarManutencao() {
                List<ManutencaoEquipamento> manutencao = manutencaoEquipamentoRepository.findAll();
                return manutencao.stream()
                                .map(ManutencaoDTO::new)
                                .toList();
        }

        // Remover manutenção pelo número de série
        public boolean removerManutencao(String numeroSerie) {
                Equipamento equipamento = equipamentoRepository.findByNumeroSerie(numeroSerie)
                                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado"));

                Optional<ManutencaoEquipamento> manutencao = manutencaoEquipamentoRepository
                                .findByEquipamento(equipamento)
                                .stream()
                                .findFirst(); // Ajuste dependendo de como você quer identificar a manutenção (mais
                                              // recente ou específica)

                if (manutencao.isPresent()) {
                        manutencaoEquipamentoRepository.delete(manutencao.get());
                        return true;
                }
                return false;
        }
}
