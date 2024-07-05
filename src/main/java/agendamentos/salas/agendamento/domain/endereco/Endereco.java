package agendamentos.salas.agendamento.domain.endereco;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "endereco")
@Entity(name = "endereco")
public class Endereco {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_endereco;
    private String rua;
    private int numero;
    private String bairro;
    private String complemento;

    public Endereco(EnderecoRequestDTO data) {
        this.rua = data.rua();
        this.numero = data.numero();
        this.bairro = data.bairro();
        this.complemento = data.complemento();
    }
}
