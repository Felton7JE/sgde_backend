package cetic.demo.sistema.dto;

import java.time.LocalDate;

import org.springframework.beans.BeanUtils;

import cetic.demo.sistema.entidade.Equipamento;
import cetic.demo.sistema.entidade.ManutencaoEquipamento;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ManutencaoDTO {
    

    private Long id;

    private Equipamento equipamento;

    private String tipoManutencao; // Exemplo: "Preventiva", "Corretiva"

    private LocalDate dataManutencao;

    private String descricaoManutencao;

    private String responsavel;

    private String status; // Exemplo: "Concluído", "Pendente"

    private String tempoInatividade;

    private String numeroSerie;

    private String nome; 

    

    public ManutencaoDTO (ManutencaoEquipamento manutencaoEquipamento){

        BeanUtils.copyProperties(manutencaoEquipamento, this);
        this.numeroSerie = manutencaoEquipamento.getEquipamento().getNumeroSerie();
        this.nome = manutencaoEquipamento.getEquipamento().getNome();



    }
}
