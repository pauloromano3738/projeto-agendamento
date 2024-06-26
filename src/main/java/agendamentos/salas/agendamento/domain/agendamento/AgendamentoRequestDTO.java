package agendamentos.salas.agendamento.domain.agendamento;

public record AgendamentoRequestDTO(String status, String data, String horaInicio, String horaFim, String profissionalId, String clienteId) {
}
