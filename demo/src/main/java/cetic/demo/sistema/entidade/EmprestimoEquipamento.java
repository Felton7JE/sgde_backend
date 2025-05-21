package cetic.demo.sistema.entidade;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import org.springframework.beans.BeanUtils;

import cetic.demo.sistema.dto.EmprestimoDTO;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "emprestimos_equipamentos")
public class EmprestimoEquipamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "equipamento_id", nullable = false)
    private Equipamento equipamento;

    @Column(nullable = false)
    private LocalDate dataEmprestimo;

    @Column(nullable = false)
    private Integer quantidade;

    private LocalDate dataDevolucao;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String justificativaEmprestimo;

    @Column(nullable = false)
    private String status; // "Pendente", "Devolvido"

    @Column(nullable = false)
    private String responsavel;

    @Column(nullable = false)
    private String quemFezEmprestimo;

        private String status2;


    public EmprestimoEquipamento(EmprestimoDTO emprestimoDTO) {

        BeanUtils.copyProperties(emprestimoDTO, this);

    }
}
