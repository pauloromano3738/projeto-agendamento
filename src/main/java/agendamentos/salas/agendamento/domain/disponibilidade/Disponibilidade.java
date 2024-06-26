package agendamentos.salas.agendamento.domain.disponibilidade;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "disponibilidade")
@Entity(name = "disponibilidade")
public class Disponibilidade {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_disponibilidade;
    private String dias_semana;
    private Time horario_inicio;
    private Time horario_fim;

    public Disponibilidade(DisponibilidadeRequestDTO data, String dias) {
        this.dias_semana = dias;
        this.horario_inicio = Time.valueOf(data.horario_inicio() + ":00");
        this.horario_fim = Time.valueOf(data.horario_fim() + ":00");
    }
}
