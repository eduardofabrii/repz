package repz.app.service.avaliacaoFisica;

import java.util.List;

import org.springframework.stereotype.Service;

import repz.app.persistence.entity.AvaliacaoFisica;
import repz.app.persistence.repository.AvaliacaoFisicaRepository;

@Service
public class AvaliacaoFisicaService {

    private final AvaliacaoFisicaRepository avaliacaoFisicaRepository;

    public AvaliacaoFisicaService(AvaliacaoFisicaRepository avaliacaoFisicaRepository) {
        this.avaliacaoFisicaRepository = avaliacaoFisicaRepository;
    }


    public AvaliacaoFisica saveAvaliacaoFisica(AvaliacaoFisica avaliacaoFisica) {
        return avaliacaoFisicaRepository.save(avaliacaoFisica);
    }

    public AvaliacaoFisica getAvaliacaoById(Long id) {
        return avaliacaoFisicaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avaliação física não encontrada"));
    }

    public void deleteAvaliacao(Long id) {
        if (!avaliacaoFisicaRepository.existsById(id)) {
            throw new RuntimeException("Avaliação física não encontrada");
        }
        avaliacaoFisicaRepository.deleteById(id);
    }

    public AvaliacaoFisica upgradeAvaliacaoFisica(Long id, AvaliacaoFisica avaliacaoFisica) {
        AvaliacaoFisica existing = getAvaliacaoById(id);
        existing.setPesoKg(avaliacaoFisica.getPesoKg());
        existing.setAlturaCm(avaliacaoFisica.getAlturaCm());
        return avaliacaoFisicaRepository.save(existing);
    }

    public AvaliacaoFisica calcularIMC(AvaliacaoFisica avaliacaoFisica) {
        Double peso = avaliacaoFisica.getPesoKg();
        Double altura = avaliacaoFisica.getAlturaCm() / 100; // converter para metros
        Double imc = peso / (altura * altura);
        avaliacaoFisica.setImc(imc);
        return avaliacaoFisicaRepository.save(avaliacaoFisica);
    }

    public List<AvaliacaoFisica> getAll() {
        return avaliacaoFisicaRepository.findAll();
    }
}
