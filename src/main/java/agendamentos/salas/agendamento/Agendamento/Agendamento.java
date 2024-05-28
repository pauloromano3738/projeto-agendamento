package agendamentos.salas.agendamento.Agendamento;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id_agendamento")
@Table(name = "agendamento")
@Entity(name = "agendamento")
public class Agendamento {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_agendamento;
    private String status;
    private String data;

    public Agendamento(AgendamentoRequestDTO data) {
        this.status = data.status();
        this.data = data.data();
    }
}
