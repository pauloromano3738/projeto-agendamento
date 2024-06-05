package agendamentos.salas.agendamento.profissional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfissionalRepository extends JpaRepository<Profissional, Integer> {
    Optional<Profissional> findByLoginAndSenha(String login, String senha);

    @Cacheable("profissionais")
    List<Profissional> findAll();

    @CacheEvict(value = "profissionais", allEntries = true)
    <S extends Profissional> S save(S entity);
}
