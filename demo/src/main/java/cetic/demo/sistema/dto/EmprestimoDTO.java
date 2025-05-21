package cetic.demo.sistema.dto;

import java.time.LocalDate;

import org.springframework.beans.BeanUtils;

import cetic.demo.sistema.entidade.EmprestimoEquipamento;
import cetic.demo.sistema.entidade.Equipamento;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class EmprestimoDTO {

    private Long id;

    private Equipamento equipamento;

    private LocalDate dataEmprestimo;

    private LocalDate dataDevolucao;


    private Integer quantidade;

    private String justificativaEmprestimo;

    private String status; // Exemplo: "Pendente", "Aprovado", "Rejeitado"

    private String responsavel;

        private String status2;

        
    private String quemFezEmprestimo;

    private String numeroSerie; // Adicionando o campo numeroSerie

    private String nome; // Adicionando o campo nome


    public EmprestimoDTO (EmprestimoEquipamento emprestimoEquipamento){

        BeanUtils.copyProperties(emprestimoEquipamento, this);
        this.numeroSerie = emprestimoEquipamento.getEquipamento().getNumeroSerie();
        this.nome = emprestimoEquipamento.getEquipamento().getNome();


    }
}
