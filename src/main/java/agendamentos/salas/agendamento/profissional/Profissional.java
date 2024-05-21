package agendamentos.salas.agendamento.profissional;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
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

}
