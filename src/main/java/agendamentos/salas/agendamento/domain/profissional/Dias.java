package agendamentos.salas.agendamento.domain.profissional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Dias {
    private Integer domingo;
    private Integer segunda;
    private Integer terca;
    private Integer quarta;
    private Integer quinta;
    private Integer sexta;
    private Integer sabado;

    public Dias(DiasRequestDTO data) {
        this.domingo = data.domingo();
        this.segunda = data.segunda();
        this.terca = data.terca();
        this.quarta = data.quarta();
        this.quinta = data.quinta();
        this.sexta = data.sexta();
        this.sabado = data.sabado();
    }
}
