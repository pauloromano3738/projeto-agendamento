package agendamentos.salas.agendamento.controllers;

import agendamentos.salas.agendamento.domain.agendamento.Agendamento;
import agendamentos.salas.agendamento.domain.agendamento.AgendamentoRepository;
import agendamentos.salas.agendamento.domain.agendamento.AgendamentoRequestDTO;
import agendamentos.salas.agendamento.domain.agendamento.AgendamentoResponseDTO;
import agendamentos.salas.agendamento.domain.cliente.Cliente;
import agendamentos.salas.agendamento.domain.cliente.ClienteRepository;
import agendamentos.salas.agendamento.domain.cliente.ClienteResponseDTO;
import agendamentos.salas.agendamento.domain.disponibilidade.Disponibilidade;
import agendamentos.salas.agendamento.domain.disponibilidade.DisponibilidadeRepository;
import agendamentos.salas.agendamento.domain.profissional.Profissional;
import agendamentos.salas.agendamento.domain.profissional.ProfissionalRepository;
import agendamentos.salas.agendamento.domain.profissional.ProfissionalResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class AgendamentoController {

    @Autowired
    private AgendamentoRepository repositoryAgendamento;

    @Autowired
    private DisponibilidadeRepository repositoryDisponibilidade;

    @Autowired
    private ClienteRepository repositoryCliente;

    @Autowired
    private ProfissionalRepository repositoryProfissional;

    @GetMapping("/adicionaAgendamento")
    public String showAddAgendamentoPage(HttpServletRequest request, Model model) {

        Optional<Disponibilidade> disponibilidadeOptional = repositoryDisponibilidade.findLastrowId();
        if (disponibilidadeOptional.isPresent()) {
            Disponibilidade disponibilidade = disponibilidadeOptional.get();
            model.addAttribute("diasDisponibilidade", disponibilidade.getDias_semana());
        } else {
            model.addAttribute("diasDisponibilidade", "");
        }

        List<ClienteResponseDTO> clienteList = repositoryCliente.findAll().stream().map(ClienteResponseDTO::new).toList();
        model.addAttribute("consultaClientes", clienteList);

        List<ProfissionalResponseDTO> profissionalList = repositoryProfissional.findAll().stream().map(ProfissionalResponseDTO::new).toList();
        model.addAttribute("consultaProfissionais", profissionalList);

        model.addAttribute("urlAdicionaAgendamento", request);

        return "adicionarAgendamento";  // Nome do template Thymeleaf para a página de login
    }

    @GetMapping("/consultarAgendamentos")
    public String getAll(HttpServletRequest request, Model model) {

        List<AgendamentoResponseDTO> agendamentoList = repositoryAgendamento.findAll().stream().map(AgendamentoResponseDTO::new).toList();
        model.addAttribute("consultaAgendamentos", agendamentoList);
        model.addAttribute("urlConsultaAgendamento", request);
        return "consultaAgendamentos";
    }

    @PostMapping("/insereAgendamento")
    public String addAgendamento(@ModelAttribute AgendamentoRequestDTO data) {

        Optional<Profissional> profissionalOptional = repositoryProfissional.findById(Integer.valueOf(data.profissionalId()));
        Profissional profissional = profissionalOptional.get();

        Optional<Cliente> clienteOptional = repositoryCliente.findById(Integer.valueOf(data.clienteId()));
        Cliente cliente = clienteOptional.get();

        Agendamento agendamentoData = new Agendamento(data, profissional, cliente);
        repositoryAgendamento.save(agendamentoData);
        return "redirect:/consultarAgendamentos";  // Você pode redirecionar para a mesma página com uma mensagem de sucesso ou para outra página
    }

    @RequestMapping(value = "/consultarAgendamentos/delete/{id}", method = {RequestMethod.GET, RequestMethod.DELETE})
    public String deleteAgendamento(@PathVariable Integer id) {
        repositoryAgendamento.deleteById(id);
        return "redirect:/consultarAgendamentos";
    }
}
