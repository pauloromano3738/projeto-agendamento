package agendamentos.salas.agendamento.agendamento;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Integer> {

    @Cacheable("agendamentos")
    List<Agendamento> findAll();

    @CacheEvict(value = "agendamentos", allEntries = true)
    <S extends Agendamento> S save(S entity);
}
