package repz.app.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "checkin")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Frequencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataHora;

    @ManyToOne
    @JoinColumn(name = "id_aluno")
    private User aluno;

    @ManyToOne
    @JoinColumn(name = "id_personal")
    private Personal registradoPor;

    @ManyToOne
    @JoinColumn(name = "id_academia")
    private Academia academia;

    @Column(name = "ativo")
    private Boolean ativo = true;

}
