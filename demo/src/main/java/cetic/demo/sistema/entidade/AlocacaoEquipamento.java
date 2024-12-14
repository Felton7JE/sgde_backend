package cetic.demo.sistema.entidade;

import java.time.LocalDate;

import org.springframework.beans.BeanUtils;

import cetic.demo.sistema.dto.AlocacaoDTO;
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

@Entity
@Table(name = "alocacoes_equipamentos")
@Getter
@Setter
@NoArgsConstructor
public class AlocacaoEquipamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "equipamento_id", nullable = false)
    private Equipamento equipamento;

    @Column(nullable = false)
    private String localAlocado;

    @Column(nullable = false)
    private String usuario;

    @Column(nullable = false)
    private LocalDate dataAlocacao;

    @Column(nullable = false)
    private String status; // Exemplo: "Ativo", "Concluído"


    public AlocacaoEquipamento (AlocacaoDTO alocacaoDTO){

        BeanUtils.copyProperties(alocacaoDTO, this);
    }
}
