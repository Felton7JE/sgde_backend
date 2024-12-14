package cetic.demo.sistema.dto;
import java.time.LocalDate;

import org.springframework.beans.BeanUtils;

import cetic.demo.sistema.entidade.AlocacaoEquipamento;
import cetic.demo.sistema.entidade.Equipamento;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class AlocacaoDTO {

    private Long id;

    private Equipamento equipamento;

    private String localAlocado;

    private String usuario;

    private LocalDate dataAlocacao;

    private String status; // Exemplo: "Ativo", "Concluído"

    private String numeroSerie; // Adicionando o campo numeroSerie

    private String nome; // Adicionando o campo nome


    public AlocacaoDTO(AlocacaoEquipamento alocacaoEquipamento) {

        BeanUtils.copyProperties(alocacaoEquipamento, this);
        this.numeroSerie = alocacaoEquipamento.getEquipamento().getNumeroSerie();
        this.nome = alocacaoEquipamento.getEquipamento().getNome();

    }
}
