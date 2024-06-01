package agendamentos.salas.agendamento.cliente;

import agendamentos.salas.agendamento.endereco.Endereco;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
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

    @OneToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    public Cliente(ClienteRequestDTO data, Endereco endereco) {
        this.nome = data.nome();
        this.cpf = data.cpf();
        this.idade = data.idade();
        this.telefone = data.telefone();
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        this.endereco = endereco;
    }
}
