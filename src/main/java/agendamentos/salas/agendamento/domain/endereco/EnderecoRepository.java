package agendamentos.salas.agendamento.domain.endereco;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
    @Query(value = "SELECT x FROM endereco x ORDER BY x.id_endereco DESC LIMIT 1")
    Optional<Endereco> findLastrowId();
}
