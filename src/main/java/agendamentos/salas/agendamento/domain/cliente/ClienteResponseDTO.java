package agendamentos.salas.agendamento.domain.cliente;

import agendamentos.salas.agendamento.domain.endereco.Endereco;

public record ClienteResponseDTO(Integer id_cliente, String nome, String cpf, int idade, String telefone, Endereco endereco) {

    public ClienteResponseDTO(Cliente cliente) {
        this(cliente.getId_cliente(), cliente.getNome(), cliente.getCpf(), cliente.getIdade(), cliente.getTelefone(), cliente.getEndereco());
    }
}
