package agendamentos.salas.agendamento.disponibilidade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DisponibilidadeRepository extends JpaRepository<Disponibilidade, Integer> {
    @Query(value = "SELECT x FROM disponibilidade x ORDER BY x.id_disponibilidade DESC LIMIT 1")
    Optional<Disponibilidade> findLastrowId();
}
