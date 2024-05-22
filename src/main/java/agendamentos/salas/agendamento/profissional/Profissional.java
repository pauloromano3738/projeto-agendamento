package agendamentos.salas.agendamento.profissional;

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
    private String ocupacao;
    private String login;
    private String senha;

    public Profissional(ProfissionalRequestDTO data) {
        this.nome = data.nome();
        this.cpf = data.cpf();
        this.ocupacao = data.ocupacao();
        this.login = data.login();
        this.senha = data.senha();
    }
}