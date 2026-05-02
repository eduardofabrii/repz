package repz.app.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "avaliacao_fisica")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoFisica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private User aluno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_personal")
    private Personal personal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_academia")
    private Academia academia;

    @Column(name = "data_avaliacao")
    private LocalDateTime dataAvaliacao;

    @Column(name = "peso_kg")
    private Double pesoKg;

    @Column(name = "altura_cm")
    private Double alturaCm;

    @Column(name = "imc")
    private Double imc;

    @Column(name = "percentual_gordura")
    private Double percentualGordura;

    @Column(name = "medidas")
    private String medidas;

    @Column(name = "ativo")
    private Boolean ativo = true;
}
