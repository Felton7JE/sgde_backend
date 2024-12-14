package cetic.demo.sistema.entidade;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

import cetic.demo.sistema.dto.AvariaDTO;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "avarias_equipamentos")
public class AvariaEquipamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "equipamento_id", nullable = false)
    private Equipamento equipamento;

    @Column(nullable = false)
    private String departamento;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private String tipoAvaria; // Exemplo: "Física", "Funcional"

    @Column(columnDefinition = "TEXT", nullable = false)
    private String descricaoAvaria;

    @Column(nullable = false)
    private String gravidade; // Exemplo: "Baixa", "Média", "Alta"

    @Column(nullable = false)
    private String status; // Exemplo: "Ativo", "Concluído"


    public AvariaEquipamento (AvariaDTO avariaDTO){

        BeanUtils.copyProperties(avariaDTO, this);

    }
}
