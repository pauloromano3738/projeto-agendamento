package agendamentos.salas.agendamento.disponibilidade;

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
    private int dia_semana;
    private Time horario_inicio;
    private Time horario_fim;

    public Disponibilidade(DisponibilidadeRequestDTO data) {
        this.dia_semana = data.dia_semana();
        this.horario_inicio = data.horario_inicio();
        this.horario_fim = data.horario_fim();
    }
}
