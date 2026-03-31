package repz.app.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import repz.app.persistence.entity.common.AuditoriaBase;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "academia")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Academia extends AuditoriaBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cnpj", unique = true, length = 14, nullable = false)
    private String cnpj;

    @Column(name = "nome", nullable = false, length = 255)
    private String name;

    @Column(name = "endereco", nullable = false, length = 500)
    private String address;

    @Column(name = "responsavel", nullable = false, length = 255)
    private String responsible;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_responsavel")
    private User responsibleUser;

    @Column(name = "ativo", nullable = false)
    private Boolean active = true;

    @Column(name = "total_alunos")
    private Integer totalStudents = 0;

    @Column(name = "total_professores")
    private Integer totalInstructors = 0;

    @Column(name = "telefone", length = 20)
    private String phone;

    @Column(name = "email", length = 255)
    private String email;
}
