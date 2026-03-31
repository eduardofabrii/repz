package repz.app.persistence.entity.common;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
public abstract class AuditoriaBase {

    @CreationTimestamp
    @Column(name = "data_inclusao", updatable = false)
    private LocalDateTime dtInclusao;

    @UpdateTimestamp
    @Column(name = "data_alteracao")
    private LocalDateTime dtAlteracao;

    @Column(name = "nome_usuario", length = 50)
    private String nmUsuario;
}