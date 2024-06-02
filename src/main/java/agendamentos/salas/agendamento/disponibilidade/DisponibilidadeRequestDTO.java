package agendamentos.salas.agendamento.disponibilidade;

import java.sql.Time;

public record DisponibilidadeRequestDTO(int dia_semana, Time horario_inicio, Time horario_fim) {
}
