package repz.app.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "plano")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plano {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    @Column(name = "duracao_dias")
    private Integer duracaoDias;

    private BigDecimal valor;

    private Boolean ativo = true;

    @ManyToOne
    @JoinColumn(name = "academia_id")
    private User academia;
}