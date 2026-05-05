package repz.app.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import repz.app.persistence.entity.common.AuditoriaBase;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "aluno")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Aluno extends AuditoriaBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private User usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_academia", nullable = false)
    private Academia academia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_personal")
    private Personal personal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plano_id")
    private Plano plano;

    @Column(name = "data_inicio")
    private LocalDate dataInicio;

    @Column(name = "objetivo")
    private String objetivo;

    @Column(name = "ativo")
    private Boolean ativo = true;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "foto_url")
    private String fotoUrl;
}
