package agendamentos.salas.agendamento.domain.profissional;

import agendamentos.salas.agendamento.domain.disponibilidade.Disponibilidade;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id_profissional")
@Table(name = "profissional")
@Entity(name = "profissional")
public class Profissional {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_profissional;
    private String nome;
    private String cpf;
    private String login;
    private String senha;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "Disponibilidade_id")
    private Disponibilidade disponibilidade;

    public Profissional(ProfissionalRequestDTO data, Disponibilidade disponibilidade) {
        this.nome = data.nome();
        this.cpf = data.cpf();
        this.login = data.login();
        this.senha = data.senha();
        this.disponibilidade = disponibilidade;
    }
}