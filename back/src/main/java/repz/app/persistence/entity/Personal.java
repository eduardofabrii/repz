package repz.app.persistence.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "personal")
public class Personal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private List<String> especialidade;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    public Personal(Long id, List<String> especialidade, User user) {
        this.id = id;
        this.especialidade = especialidade;
        this.user = user;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(List<String> especialidade) {
        this.especialidade = especialidade;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
