package agendamentos.salas.agendamento.profissional;

import agendamentos.salas.agendamento.disponibilidade.Disponibilidade;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
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

    @OneToOne
    @JoinColumn(name = "disponibilidade_id")
    private Disponibilidade disponibilidade;

    public Profissional(ProfissionalRequestDTO data, Disponibilidade disponibilidade) {
        this.nome = data.nome();
        this.cpf = data.cpf();
        this.login = data.login();
        this.senha = data.senha();
        this.disponibilidade = disponibilidade;
    }
}