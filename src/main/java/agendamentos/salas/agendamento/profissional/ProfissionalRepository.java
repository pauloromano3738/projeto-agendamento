package agendamentos.salas.agendamento.profissional;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfissionalRepository extends JpaRepository<Profissional, Integer> {
    Optional<Profissional> findByLoginAndSenha(String login, String senha);
}
