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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/clientes")
    public String getAll(@RequestParam(value = "idCliente", required = false) Integer idCliente, @RequestParam(value = "operation", required = false) String operation, HttpServletRequest request, Model model) {

        List<ClienteResponseDTO> clienteList = repositoryCliente.findAll().stream().map(ClienteResponseDTO::new).toList();
        model.addAttribute("consultaClientes", clienteList);
        model.addAttribute("urlConsultaCliente" ,request);

        if (idCliente != null) {
            Optional<Cliente> clienteOptional = repositoryCliente.findById(idCliente);
            Cliente cliente = clienteOptional.get();
            model.addAttribute("clienteDetails", cliente);
            if (operation != null) {
                return "editarCliente";
            }
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
        return "redirect:/clientes";  // Você pode redirecionar para a mesma página com uma mensagem de sucesso ou para outra página
    }

    @RequestMapping(value = "/insereClientes/update/{id}", method = {RequestMethod.GET, RequestMethod.PUT})
    public String updateCliente(@PathVariable Integer id, @ModelAttribute EnderecoRequestDTO dataEndereco, @ModelAttribute ClienteRequestDTO dataCliente) {

        Optional<Cliente> clienteOptional = repositoryCliente.findById(id);
        Cliente cliente = clienteOptional.get();

        cliente.setNome(dataCliente.nome());
        cliente.setCpf(dataCliente.cpf());
        cliente.setIdade(dataCliente.idade());
        cliente.setTelefone(dataCliente.telefone());

        Endereco endereco = cliente.getEndereco();

        endereco.setRua(dataEndereco.rua());
        endereco.setNumero(dataEndereco.numero());
        endereco.setBairro(dataEndereco.bairro());
        endereco.setComplemento(dataEndereco.complemento());

        cliente.setEndereco(endereco);

        repositoryCliente.save(cliente);
        return "redirect:/clientes";
    }

    @RequestMapping(value = "/clientes/details/{id}", method = {RequestMethod.GET, RequestMethod.DELETE})
    public String detailsCliente(@PathVariable Integer id) {
        return "redirect:/clientes?idCliente=" + id;
    }

    @RequestMapping(value = "/clientes/edit/{id}", method = {RequestMethod.GET, RequestMethod.DELETE})
    public String editCliente(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("operation","edit");
        return "redirect:/clientes?idCliente=" + id;
    }

    @RequestMapping(value = "/clientes/delete/{id}", method = {RequestMethod.GET, RequestMethod.DELETE})
    public String deleteCliente(@PathVariable Integer id) {
        repositoryCliente.deleteById(id);
        return "redirect:/clientes";
    }
}
