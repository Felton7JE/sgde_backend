package cetic.demo.sistema.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cetic.demo.sistema.dto.EquipamentosDTO;
import cetic.demo.sistema.entidade.Equipamento;
import cetic.demo.sistema.repository.EquipamentoRepository;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EquipamentoService {

    @Autowired
    private EquipamentoRepository equipamentoRepository;

    @Transactional
    public EquipamentosDTO salvarEquipamento(EquipamentosDTO equipamentoDTO) {
        Equipamento equipamento = new Equipamento(equipamentoDTO);
        equipamento.setStatus2("NAO ALOCADO");
        equipamentoRepository.save(equipamento);
        return new EquipamentosDTO(equipamento);
    }

    public long contarEquipamentos() {
        return equipamentoRepository.count();
    }

    @Transactional
    public EquipamentosDTO editarEquipamento(EquipamentosDTO equipamentoDTO) {
        String numeroSerie = equipamentoDTO.getNumeroSerie();

        Equipamento equipamento = equipamentoRepository.findByNumeroSerie(numeroSerie)
                .orElseThrow(() -> new IllegalArgumentException("Equipamento não encontrado"));

        equipamentoDTO.setNumeroSerie(equipamento.getNumeroSerie()); // garante que o número de série não seja alterado

        equipamento.atualizarComDTO(equipamentoDTO);
        equipamentoRepository.save(equipamento);

        return new EquipamentosDTO(equipamento);
    }

    public EquipamentosDTO buscarPorNumeroSerie(EquipamentosDTO equipamentoDTO) {
        String numeroSerie = equipamentoDTO.getNumeroSerie();

        Equipamento equipamento = equipamentoRepository.findByNumeroSerie(numeroSerie)
                .orElseThrow(() -> new IllegalArgumentException("Equipamento não encontrado"));

        return new EquipamentosDTO(equipamento);
    }

    public List<EquipamentosDTO> listarEquipamentos() {
        return equipamentoRepository.findAll().stream()
                .map(EquipamentosDTO::new)
                .collect(Collectors.toList());
    }

    public List<EquipamentosDTO> pesquisarPorAtributo(String atributo, String valor) {
        return equipamentoRepository.findAll().stream()
                .filter(equipamento -> {
                    try {
                        Field field = Equipamento.class.getDeclaredField(atributo);
                        field.setAccessible(true);
                        Object fieldValue = field.get(equipamento);
                        return fieldValue != null && fieldValue.toString().toLowerCase().contains(valor.toLowerCase());
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        return false;
                    }
                })
                .map(EquipamentosDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deletarEquipamento(EquipamentosDTO equipamentoDTO) {
        String numeroSerie = equipamentoDTO.getNumeroSerie();

        Equipamento equipamento = equipamentoRepository.findByNumeroSerie(numeroSerie)
                .orElseThrow(() -> new IllegalArgumentException("Equipamento não encontrado"));

        equipamentoRepository.delete(equipamento);
    }
}
