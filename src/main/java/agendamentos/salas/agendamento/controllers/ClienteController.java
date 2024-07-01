package agendamentos.salas.agendamento.controllers;

import agendamentos.salas.agendamento.domain.cliente.Cliente;
import agendamentos.salas.agendamento.domain.cliente.ClienteRepository;
import agendamentos.salas.agendamento.domain.cliente.ClienteRequestDTO;
import agendamentos.salas.agendamento.domain.cliente.ClienteResponseDTO;
import agendamentos.salas.agendamento.domain.endereco.Endereco;
import agendamentos.salas.agendamento.domain.endereco.EnderecoRepository;
import agendamentos.salas.agendamento.domain.endereco.EnderecoRequestDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ClienteController {

    @Autowired
    private ClienteRepository repositoryCliente;

    @Autowired
    private EnderecoRepository repositoryEndereco;

    @GetMapping("/cadastroCliente")
    public String showAddClientePage(HttpServletRequest request, Model model) {

        model.addAttribute("urlCadastroCliente" ,request);
        return "cadastroCliente";
    }

    @GetMapping("/consultaClientes")
    public String getAll(@RequestParam(value = "idCliente", required = false) Integer idCliente, HttpServletRequest request, Model model) {

        List<ClienteResponseDTO> clienteList = repositoryCliente.findAll().stream().map(ClienteResponseDTO::new).toList();
        model.addAttribute("consultaClientes", clienteList);
        model.addAttribute("urlConsultaCliente" ,request);

        if (idCliente != null) {
            Optional<Cliente> clienteOptional = repositoryCliente.findById(idCliente);
            Cliente cliente = clienteOptional.get();
            model.addAttribute("clienteDetails", cliente);
        }

        return "consultaClientes";
    }

    @PostMapping("/insereCliente")
    public String addCliente(@ModelAttribute EnderecoRequestDTO dataEndereco, @ModelAttribute ClienteRequestDTO dataCliente, Model model) {

        Endereco enderecoData = new Endereco(dataEndereco);
        repositoryEndereco.save(enderecoData);

        Optional<Endereco> enderecoOptional = repositoryEndereco.findLastrowId();
        Endereco endereco = enderecoOptional.get();

        Cliente clienteData = new Cliente(dataCliente, endereco);
        repositoryCliente.save(clienteData);

        model.addAttribute("message", "Cliente cadastrado com sucesso!");
        return "redirect:/consultaClientes";  // Você pode redirecionar para a mesma página com uma mensagem de sucesso ou para outra página
    }

    @RequestMapping(value = "/consultaClientes/details/{id}", method = {RequestMethod.GET, RequestMethod.DELETE})
    public String detailsCliente(@PathVariable Integer id) {
        return "redirect:/consultaClientes?idCliente=" + id;
    }

    @RequestMapping(value = "/consultaClientes/delete/{id}", method = {RequestMethod.GET, RequestMethod.DELETE})
    public String deleteCliente(@PathVariable Integer id) {
        repositoryCliente.deleteById(id);
        return "redirect:/consultaClientes";
    }
}
