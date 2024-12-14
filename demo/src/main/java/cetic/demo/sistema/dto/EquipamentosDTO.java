package cetic.demo.sistema.dto;

import java.time.LocalDate;

import org.springframework.beans.BeanUtils;

import cetic.demo.sistema.entidade.Equipamento;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class EquipamentosDTO {
    

    private Long id;

    private String nome;

    private String numeroSerie;

    private String categoria;

    private String marca;

    private String modelo;

    private String descricaoTecnica;

    private LocalDate dataAquisicao;

    private String fornecedor;
    
    private Integer quantidade; 


    private String status;


    public EquipamentosDTO (Equipamento equipamento){

        BeanUtils.copyProperties(equipamento, this);

    }


}
