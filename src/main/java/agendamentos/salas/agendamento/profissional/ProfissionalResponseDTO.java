package agendamentos.salas.agendamento.profissional;

public record ProfissionalResponseDTO(Integer id_profissional, String nome, String cpf, String ocupacao, String login, String senha) {

    public ProfissionalResponseDTO(Profissional profissional) {
        this(profissional.getId_profissional(), profissional.getNome(), profissional.getCpf(), profissional.getOcupacao(), profissional.getLogin(), profissional.getSenha());
    }
}
