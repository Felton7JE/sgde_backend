package cetic.demo.sistema.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cetic.demo.sistema.dto.AvariaDTO;
import cetic.demo.sistema.entidade.AvariaEquipamento;
import cetic.demo.sistema.entidade.Equipamento;
import cetic.demo.sistema.enums.StatusAvaria;
import cetic.demo.sistema.repository.AvariaEquipamentoRepository;
import cetic.demo.sistema.repository.EquipamentoRepository;

@Service
public class AvariaEquipamentoService {

    @Autowired
    private AvariaEquipamentoRepository avariaEquipamentoRepository;

    @Autowired
    private EquipamentoRepository equipamentoRepository;

    public AvariaDTO salvarAvaria(AvariaDTO avariaDTO) {
        Equipamento equipamento = equipamentoRepository.findByNumeroSerie(avariaDTO.getNumeroSerie())
                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado"));

        AvariaEquipamento avariaEquipamento = new AvariaEquipamento();
        avariaEquipamento.setEquipamento(equipamento);
        avariaEquipamento.setDepartamento(avariaDTO.getDepartamento());
        avariaEquipamento.setData(LocalDate.now());
        avariaEquipamento.setTipoAvaria(avariaDTO.getTipoAvaria());
        avariaEquipamento.setDescricaoAvaria(avariaDTO.getDescricaoAvaria());
        avariaEquipamento.setGravidade(avariaDTO.getGravidade());
        avariaEquipamento.setStatus(StatusAvaria.AVARIADO);
        
        // Atualiza o status do equipamento para "AVARIADO" automaticamente
        equipamento.setStatus(StatusAvaria.AVARIADO.name());
        equipamentoRepository.save(equipamento);

        AvariaEquipamento avariaSalva = avariaEquipamentoRepository.save(avariaEquipamento);
        return new AvariaDTO(avariaSalva);
    }

    public Optional<AvariaDTO> buscarAvariaPorNumeroSerie(AvariaDTO avariaDTO) {
        Optional<AvariaEquipamento> avaria = avariaEquipamentoRepository
                .findByEquipamento_NumeroSerie(avariaDTO.getNumeroSerie());
        return avaria.map(AvariaDTO::new);
    }

    public Optional<AvariaDTO> atualizarAvariaPorNumeroSerie(AvariaDTO avariaDTO) {
        Optional<AvariaEquipamento> avariaExistente = avariaEquipamentoRepository
                .findByEquipamento_NumeroSerie(avariaDTO.getNumeroSerie());

        if (avariaExistente.isPresent()) {
            AvariaEquipamento avaria = avariaExistente.get();

            avaria.setDepartamento(avariaDTO.getDepartamento());
            avaria.setData(avariaDTO.getData());
            avaria.setTipoAvaria(avariaDTO.getTipoAvaria());
            avaria.setDescricaoAvaria(avariaDTO.getDescricaoAvaria());
            avaria.setGravidade(avariaDTO.getGravidade());
            avaria.setStatus(StatusAvaria.valueOf(avariaDTO.getStatus()));  
                        

            AvariaEquipamento avariaAtualizada = avariaEquipamentoRepository.save(avaria);
            return Optional.of(new AvariaDTO(avariaAtualizada));
        }

        return Optional.empty();
    }

    public boolean excluirAvariaPorNumeroSerie(AvariaDTO avariaDTO) {
        Optional<AvariaEquipamento> avariaExistente = avariaEquipamentoRepository
                .findByEquipamento_NumeroSerie(avariaDTO.getNumeroSerie());

        if (avariaExistente.isPresent()) {
            avariaEquipamentoRepository.delete(avariaExistente.get());
            return true;
        }
        return false;
    }

    public boolean verificarSeAvariaExiste(AvariaDTO avariaDTO) {
        Optional<AvariaEquipamento> avariaExistente = avariaEquipamentoRepository
                .findByEquipamento_NumeroSerie(avariaDTO.getNumeroSerie());
        return avariaExistente.isPresent();
    }

    public long contarAvarias() {
        return avariaEquipamentoRepository.count();
    }

    public long contarAvariasPorStatus(AvariaDTO avariaDTO) {
        return avariaEquipamentoRepository.countByStatus(avariaDTO.getStatus());
    }

    public List<AvariaDTO> listarAvarias() {
        List<AvariaEquipamento> avarias = avariaEquipamentoRepository.findAll();
        return avarias.stream()
                .map(AvariaDTO::new)
                .toList();
    }

    public List<AvariaDTO> buscarAvariasPorStatus(AvariaDTO avariaDTO) {
        List<AvariaEquipamento> avarias = avariaEquipamentoRepository.findByStatus(avariaDTO.getStatus());
        return avarias.stream()
                .map(AvariaDTO::new)
                .toList();
    }
}
