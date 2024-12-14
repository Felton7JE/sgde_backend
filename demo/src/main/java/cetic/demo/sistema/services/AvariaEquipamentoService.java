package cetic.demo.sistema.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cetic.demo.sistema.dto.AvariaDTO;
import cetic.demo.sistema.entidade.AvariaEquipamento;
import cetic.demo.sistema.entidade.Equipamento;
import cetic.demo.sistema.repository.AvariaEquipamentoRepository;
import cetic.demo.sistema.repository.EquipamentoRepository;

@Service
public class AvariaEquipamentoService {

    @Autowired
    private AvariaEquipamentoRepository avariaEquipamentoRepository;

    @Autowired
    private EquipamentoRepository equipamentoRepository;

    public AvariaDTO salvarAvaria(String numeroSerie, AvariaEquipamento avariaEquipamento) {
        Equipamento equipamento = equipamentoRepository.findByNumeroSerie(numeroSerie)
                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado"));

        avariaEquipamento.setEquipamento(equipamento);
        AvariaEquipamento avariaSalva = avariaEquipamentoRepository.save(avariaEquipamento);

        return new AvariaDTO(avariaSalva);
    }

    public long contarAvariasPorStatus(String status) {
        return avariaEquipamentoRepository.countByStatus(status);
    }
    public List<AvariaDTO> listarAvarias() {
        List<AvariaEquipamento> avarias = avariaEquipamentoRepository.findAll();
        return avarias.stream()
                .map(AvariaDTO::new)
                .toList();
    }

    public Optional<AvariaDTO> buscarAvariaPorNumeroSerie(String numeroSerie) {
        Optional<AvariaEquipamento> avaria = avariaEquipamentoRepository.findByEquipamento_NumeroSerie(numeroSerie);
        return avaria.map(AvariaDTO::new);
    }

    public Optional<AvariaDTO> atualizarAvariaPorNumeroSerie(String numeroSerie, AvariaEquipamento dadosAtualizados) {
        Optional<AvariaEquipamento> avariaExistente = avariaEquipamentoRepository
                .findByEquipamento_NumeroSerie(numeroSerie);

        if (avariaExistente.isPresent()) {
            AvariaEquipamento avaria = avariaExistente.get();

            // Atualizando os campos diretamente sem verificação de nulidade
            avaria.setDepartamento(dadosAtualizados.getDepartamento());
            avaria.setData(dadosAtualizados.getData());
            avaria.setTipoAvaria(dadosAtualizados.getTipoAvaria());
            avaria.setDescricaoAvaria(dadosAtualizados.getDescricaoAvaria());
            avaria.setGravidade(dadosAtualizados.getGravidade());
            avaria.setStatus(dadosAtualizados.getStatus());

            // Salvar a avaria atualizada no banco de dados
            AvariaEquipamento avariaAtualizada = avariaEquipamentoRepository.save(avaria);

            // Retorna o DTO atualizado
            return Optional.of(new AvariaDTO(avariaAtualizada));
        }

        return Optional.empty();
    }

    public boolean excluirAvariaPorNumeroSerie(String numeroSerie) {
        Optional<AvariaEquipamento> avariaExistente = avariaEquipamentoRepository
                .findByEquipamento_NumeroSerie(numeroSerie);
        if (avariaExistente.isPresent()) {
            avariaEquipamentoRepository.delete(avariaExistente.get());
            return true;
        }
        return false;
    }

    public boolean verificarSeAvariaExiste(String numeroSerie) {
        Optional<AvariaEquipamento> avariaExistente = avariaEquipamentoRepository
                .findByEquipamento_NumeroSerie(numeroSerie);
        return avariaExistente.isPresent();
    }

    public long contarAvarias() {
        return avariaEquipamentoRepository.count();
    }

    public List<AvariaDTO> buscarAvariasPorStatus(String status) {
        List<AvariaEquipamento> avarias = avariaEquipamentoRepository.findByStatus(status);
        return avarias.stream()
                .map(AvariaDTO::new)
                .toList();
    }
}
