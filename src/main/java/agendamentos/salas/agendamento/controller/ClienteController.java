package agendamentos.salas.agendamento.controller;

import agendamentos.salas.agendamento.cliente.Cliente;
import agendamentos.salas.agendamento.cliente.ClienteRepository;
import agendamentos.salas.agendamento.cliente.ClienteRequestDTO;
import agendamentos.salas.agendamento.cliente.ClienteResponseDTO;
import agendamentos.salas.agendamento.endereco.Endereco;
import agendamentos.salas.agendamento.endereco.EnderecoRepository;
import agendamentos.salas.agendamento.endereco.EnderecoRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class ClienteController {

    @Autowired
    private ClienteRepository repositoryCliente;

    @Autowired
    private EnderecoRepository repositoryEndereco;

    @GetMapping("/consultaClientes")
    public String getAll(Model model) {

        List<ClienteResponseDTO> clienteList = repositoryCliente.findAll().stream().map(ClienteResponseDTO::new).toList();
        model.addAttribute("consultaClientes", clienteList);
        return "consultaClientes";
    }

    @GetMapping("/cadastroCliente")
    public String showAddClientePage() {

        return "cadastroCliente";
    }

    @PostMapping("/insereCliente")
    public String addCliente(@ModelAttribute EnderecoRequestDTO dataEndereco, @ModelAttribute ClienteRequestDTO dataCliente, Model model) {

        Endereco enderecoData = new Endereco(dataEndereco);
        repositoryEndereco.save(enderecoData);

        Optional<Endereco> enderecoOptional = repositoryEndereco.findLastrowId();;
        Endereco endereco = enderecoOptional.get();

        Cliente clienteData = new Cliente(dataCliente, endereco);
        repositoryCliente.save(clienteData);

        model.addAttribute("message", "Cliente cadastrado com sucesso!");
        return "redirect:/consultaClientes";  // Você pode redirecionar para a mesma página com uma mensagem de sucesso ou para outra página
    }
}
