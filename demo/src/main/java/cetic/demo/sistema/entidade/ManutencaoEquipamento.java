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

import cetic.demo.sistema.dto.ManutencaoDTO;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "manutencoes_equipamentos")
public class ManutencaoEquipamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "equipamento_id", nullable = false)
    private Equipamento equipamento;

    @Column(nullable = false)
    private String tipoManutencao; // Exemplo: "Preventiva", "Corretiva"

    @Column(nullable = false)
    private LocalDate dataManutencao;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String descricaoManutencao;

    @Column(nullable = false)
    private String responsavel;

  
    @Column(nullable = false)
    private String status; // "Pendente", "Devolvido"



    @Column(nullable = false)
    private String tempoInatividade;


    public ManutencaoEquipamento (ManutencaoDTO manutencaoDTO){

        BeanUtils.copyProperties(manutencaoDTO, this);

    }
}
