package repz.app.persistence.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "checkin")
public class CheckIn {

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

    public CheckIn(Long id, LocalDateTime dataHora, User aluno, Personal registradoPor, Academia academia) {
        this.id = id;
        this.dataHora = dataHora;
        this.aluno = aluno;
        this.registradoPor = registradoPor;
        this.academia = academia;

    }

    public CheckIn() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public User getAluno() {
        return aluno;
    }

    public void setAluno(User aluno) {
        this.aluno = aluno;
    }

    public Personal getRegistradoPor() {
        return registradoPor;
    }

    public void setRegistradoPor(Personal registradoPor) {
        this.registradoPor = registradoPor;
    }

    public Academia getAcademia() {
        return academia;
    }

    public void setAcademia(Academia academia) {
        this.academia = academia;
    }

}