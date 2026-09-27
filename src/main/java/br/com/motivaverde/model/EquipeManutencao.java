package br.com.motivaverde.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "TB_EQUIPE_MANUTENCAO")
public class EquipeManutencao {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_equipe_manutencao"
    )
    @SequenceGenerator(
            name = "seq_equipe_manutencao",
            sequenceName = "SEQ_EQUIPE_MANUTENCAO",
            allocationSize = 1
    )
    @Column(name = "ID_EQUIPE")
    private Long id;

    @NotBlank(message = "O nome da equipe é obrigatório")
    @Size(max = 100, message = "O nome deve possuir no máximo 100 caracteres")
    @Column(name = "NOME", nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "A especialidade é obrigatória")
    @Size(max = 100, message = "A especialidade deve possuir no máximo 100 caracteres")
    @Column(name = "ESPECIALIDADE", nullable = false, length = 100)
    private String especialidade;

    public EquipeManutencao() {
    }

    public EquipeManutencao(String nome, String especialidade) {
        this.nome = nome;
        this.especialidade = especialidade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
}