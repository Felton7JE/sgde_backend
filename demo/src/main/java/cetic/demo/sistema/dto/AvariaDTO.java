package cetic.demo.sistema.dto;

import java.time.LocalDate;

import org.springframework.beans.BeanUtils;

import cetic.demo.sistema.entidade.AvariaEquipamento;
import cetic.demo.sistema.entidade.Equipamento;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class AvariaDTO {
    

    private Long id;

    private Equipamento equipamento;

    private String departamento;

    private LocalDate data;

    private String tipoAvaria; // Exemplo: "Física", "Funcional"

    private String descricaoAvaria;

    private String gravidade; // Exemplo: "Baixa", "Média", "Alta"

    private String status; // Exemplo: "Resolvida", "Pendente", "Em análise"

    private String numeroSerie; // Adicionando o campo numeroSerie

    private String nome; // Adicionando o campo nome


    public AvariaDTO (AvariaEquipamento avariaEquipamento){

        BeanUtils.copyProperties(avariaEquipamento, this);
        this.numeroSerie = avariaEquipamento.getEquipamento().getNumeroSerie();
        this.nome = avariaEquipamento.getEquipamento().getNome();
        if (avariaEquipamento.getStatus() != null) {
            this.status = avariaEquipamento.getStatus().name();
        }
    }
}
