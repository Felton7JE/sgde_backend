package cetic.demo.sistema.dto;

import java.time.LocalDate;

import org.springframework.beans.BeanUtils;

import cetic.demo.sistema.entidade.RequisicaoEquipamento;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RequisicaoDTO {

 
    private Long id;

    private LocalDate dataRequisicao;

    private Integer quantidade;

    private String justificativaRequisicao;

    private String status; 

    private String status2; // "Pendente", "Concluído"

    private String responsavel;

    private String nomeEquipamento; 

    private String modeloEquipamento; 

    private String tipoEquipamento; 

    private String descricaoEquipamento; 

    public RequisicaoDTO(RequisicaoEquipamento requisicaoEquipamento) {
        BeanUtils.copyProperties(requisicaoEquipamento, this);

        
    }
}
