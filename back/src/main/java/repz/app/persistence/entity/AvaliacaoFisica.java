package repz.app.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "avaliacao_fisica")
public class AvaliacaoFisica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String data;

    @OneToOne
    @JoinColumn(name = "id_personal")
    private Personal personal;
    @OneToOne
    @JoinColumn(name = "id_usuario")
    private  User user;
    private Long idAluno = user.getId();
    private Double pesoKg;
    private Double alturaCm;
    private Double imc;

    
    

    public AvaliacaoFisica(Long id, String data, Long idAluno, Personal personal, User usuario, Double pesoKg, Double alturaCm) {
        this.id = id;
        this.data = data;
        this.idAluno = idAluno;
        this.personal = personal;
        this.user = usuario;
        this.pesoKg = pesoKg;
        this.alturaCm = alturaCm;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Long getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Long idAluno) {
        this.idAluno = idAluno;
    }

    public Personal getPersonal() {
        return personal;
    }

    public void setPersonal(Personal personal) {
        this.personal = personal;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getPesoKg() {
        return pesoKg;
    }

    public void setPesoKg(Double pesoKg) {
        this.pesoKg = pesoKg;
    }

    public Double getAlturaCm() {
        return alturaCm;
    }

    public void setAlturaCm(Double alturaCm) {
        this.alturaCm = alturaCm;
    }

    public Double getImc() {
        return this.imc;
    }

    public void setImc(Double imc) {
        this.imc = imc;
    }
}
