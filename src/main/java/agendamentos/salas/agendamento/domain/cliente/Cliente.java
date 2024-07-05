package agendamentos.salas.agendamento.domain.cliente;

import agendamentos.salas.agendamento.domain.endereco.Endereco;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id_cliente")
@Table(name = "cliente")
@Entity(name = "cliente")
public class Cliente {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_cliente;
    private String nome;
    private String cpf;
    private int idade;
    private String telefone;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    public Cliente(ClienteRequestDTO data, Endereco endereco) {
        this.nome = data.nome();
        this.cpf = data.cpf();
        this.idade = data.idade();
        this.telefone = data.telefone();
        this.endereco = endereco;
    }
}
