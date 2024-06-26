package agendamentos.salas.agendamento.domain.endereco;

public record EnderecoResponseDTO(Integer id_endereco, String rua, int numero, String bairro, String complemento) {
    public EnderecoResponseDTO(Endereco endereco) {
        this(endereco.getId_endereco(), endereco.getRua(), endereco.getNumero(), endereco.getBairro(), endereco.getComplemento());
    }
}
