package agendamentos.salas.agendamento.domain.agendamento;

import agendamentos.salas.agendamento.domain.cliente.Cliente;
import agendamentos.salas.agendamento.domain.profissional.Profissional;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
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
    private String horaInicio;
    private String horaFim;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "Profissional_id")
    private Profissional profissional;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "Cliente_id")
    private Cliente cliente;

    public Agendamento(AgendamentoRequestDTO data, Profissional profissional, Cliente cliente) {
        this.status = data.status();
        this.data = data.data();
        this.horaInicio = data.horaInicio();
        this.horaFim = data.horaFim();
        this.profissional = profissional;
        this.cliente = cliente;
    }
}
