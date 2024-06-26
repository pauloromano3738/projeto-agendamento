package agendamentos.salas.agendamento.domain.agendamento;

import agendamentos.salas.agendamento.domain.cliente.Cliente;
import agendamentos.salas.agendamento.domain.profissional.Profissional;

public record AgendamentoResponseDTO(Integer id_agendamento, String status, String data, String horaInicio, String horaFim, Profissional profissional, Cliente cliente) {

    public AgendamentoResponseDTO(Agendamento agendamento) {
        this(agendamento.getId_agendamento(), agendamento.getStatus(), agendamento.getData(), agendamento.getHoraInicio(), agendamento.getHoraFim(), agendamento.getProfissional(), agendamento.getCliente());
    }
}
