package cetic.demo.sistema.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cetic.demo.sistema.dto.EquipamentosDTO;
import cetic.demo.sistema.entidade.AlocacaoEquipamento;
import cetic.demo.sistema.entidade.Equipamento;
import cetic.demo.sistema.repository.AlocacaoEquipamentoRepository;
import cetic.demo.sistema.repository.EquipamentoRepository;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EquipamentoService {

    @Autowired
    private EquipamentoRepository equipamentoRepository;

    @Autowired
    private AlocacaoEquipamentoRepository alocacaoRepository;

    @Transactional
    public EquipamentosDTO salvarEquipamento(EquipamentosDTO equipamentoDTO) {
        Equipamento equipamento = new Equipamento(equipamentoDTO);
        equipamentoRepository.save(equipamento);

        AlocacaoEquipamento alocacao = new AlocacaoEquipamento();
        alocacao.setEquipamento(equipamento);
        alocacao.setLocalAlocado("Não definido"); // Ajuste conforme sua lógica
        alocacao.setUsuario("Não definido"); // Ajuste conforme necessário
        alocacao.setDataAlocacao(LocalDate.now());
        alocacao.setStatus("Não alocado");

        alocacaoRepository.save(alocacao);
        return equipamentoDTO;
    }

    public long contarEquipamentos() {
        return equipamentoRepository.count();
    }

    
    @Transactional
    public EquipamentosDTO editarEquipamento(String numeroSerie, EquipamentosDTO equipamentoDTO) {
        Equipamento equipamento = equipamentoRepository.findByNumeroSerie(numeroSerie)
                .orElseThrow(() -> new IllegalArgumentException("Equipamento não encontrado"));

        equipamentoDTO.setNumeroSerie(equipamento.getNumeroSerie()); // Previne alteração do número de série

        equipamento.atualizarComDTO(equipamentoDTO);
        equipamentoRepository.save(equipamento);

        // Atualiza ou cria a alocação
        Optional<AlocacaoEquipamento> alocacaoOptional = alocacaoRepository.findByEquipamento_NumeroSerie(numeroSerie);
        AlocacaoEquipamento alocacao;
        if (alocacaoOptional.isPresent()) {
            alocacao = alocacaoOptional.get();
            alocacao.setDataAlocacao(alocacao.getDataAlocacao()); 
            alocacao.setStatus(alocacao.getStatus()); 
            alocacao.setUsuario(alocacao.getUsuario()); 
            alocacao.setLocalAlocado(alocacao.getLocalAlocado());
            alocacaoRepository.save(alocacao);
        } else {
            alocacao = new AlocacaoEquipamento();
            alocacao.setEquipamento(equipamento);
            alocacao.setLocalAlocado("Não definido");
            alocacao.setUsuario("Não definido");
            alocacao.setDataAlocacao(LocalDate.now());
            alocacao.setStatus("Não alocado");
            alocacaoRepository.save(alocacao);
        }

        return new EquipamentosDTO(equipamento);
    }

    public EquipamentosDTO buscarPorNumeroSerie(String numeroSerie) {
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
    public void deletarEquipamento(String numeroSerie) {
        Equipamento equipamento = equipamentoRepository.findByNumeroSerie(numeroSerie)
                .orElseThrow(() -> new IllegalArgumentException("Equipamento não encontrado"));
        equipamentoRepository.delete(equipamento);
    }
}
