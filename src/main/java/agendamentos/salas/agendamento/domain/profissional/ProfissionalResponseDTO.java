package agendamentos.salas.agendamento.domain.profissional;

import agendamentos.salas.agendamento.domain.disponibilidade.Disponibilidade;

public record ProfissionalResponseDTO(Integer id_profissional, String nome, String cpf, String login, String senha, Disponibilidade disponibilidade) {

    public ProfissionalResponseDTO(Profissional profissional) {
        this(profissional.getId_profissional(), profissional.getNome(), profissional.getCpf(), profissional.getLogin(), profissional.getSenha(), profissional.getDisponibilidade());
    }
}
