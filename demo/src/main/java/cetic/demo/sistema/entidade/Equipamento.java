package cetic.demo.sistema.entidade;

import java.time.LocalDate;

import org.springframework.beans.BeanUtils;

import cetic.demo.sistema.dto.EquipamentosDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "equipamentos")
public class Equipamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "numero_serie", nullable = false, unique = true)
    private String numeroSerie;

    @Column(nullable = false)
    private String categoria;

    @Column(nullable = false)
    private String marca;

    @Column(nullable = false)
    private String modelo;

    @Column(name = "descricao_tecnica", columnDefinition = "TEXT")
    private String descricaoTecnica;

    @Column(name = "data_aquisicao")
    private LocalDate dataAquisicao;

    @Column(nullable = false)
    private String fornecedor;

    @Column(nullable = false)
    private Integer quantidade; 
    
    @Column(nullable = false)
    private String status;

      @Column(nullable = false)
    private String status2;



    public Equipamento(EquipamentosDTO equipamentosDTO) {

        BeanUtils.copyProperties(equipamentosDTO, this);
    }

    public void atualizarComDTO(EquipamentosDTO dto) {
        this.nome = dto.getNome();
        this.categoria = dto.getCategoria();
        this.marca = dto.getMarca();
        this.modelo = dto.getModelo();
        this.descricaoTecnica = dto.getDescricaoTecnica();
        this.dataAquisicao = dto.getDataAquisicao();
        this.fornecedor = dto.getFornecedor();
        this.status = dto.getStatus();
        this.status2 = dto.getStatus2();
    }

}