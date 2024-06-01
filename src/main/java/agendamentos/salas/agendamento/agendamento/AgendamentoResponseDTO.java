package agendamentos.salas.agendamento.agendamento;

public record AgendamentoResponseDTO(Integer id_agendamento, String status, String data) {

    public AgendamentoResponseDTO(Agendamento agendamento) {
        this(agendamento.getId_agendamento(), agendamento.getStatus(), agendamento.getData());
    }
}
