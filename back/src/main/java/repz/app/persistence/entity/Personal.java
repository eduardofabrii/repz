package repz.app.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "personal")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Personal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "especialidades")
    private String especialidade;

    @Column(name = "ativo")
    private Boolean ativo;

    @OneToOne
    @JoinColumn(name = "id_usuario")
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_academia")
    private Academia academia;

    @Column(name = "data_inclusao")
    private LocalDateTime dtInclusao;

    @Column(name = "data_alteracao")
    private LocalDateTime dtAlteracao;

    @Column(name = "nome_usuario")
    private String nomeUsuario;

}
