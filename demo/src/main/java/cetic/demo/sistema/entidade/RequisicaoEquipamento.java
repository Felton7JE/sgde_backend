package cetic.demo.sistema.entidade;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import org.springframework.beans.BeanUtils;

import cetic.demo.sistema.dto.RequisicaoDTO;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "requisicao_equipamentos")
public class RequisicaoEquipamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate dataRequisicao;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String justificativaRequisicao;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String status2;

    @Column(nullable = false)
    private String responsavel;

    @Column(nullable = false)
    private String nomeEquipamento;

    @Column(nullable = false)
    private String modeloEquipamento;

    @Column(nullable = false)
    private String tipoEquipamento;

    @Column(nullable = true)
    private String descricaoEquipamento;

    public RequisicaoEquipamento(RequisicaoDTO requisicaoDTO) {
        BeanUtils.copyProperties(requisicaoDTO, this);

    }
}
