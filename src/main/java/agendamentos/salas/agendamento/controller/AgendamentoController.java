package agendamentos.salas.agendamento.controller;

import agendamentos.salas.agendamento.agendamento.Agendamento;
import agendamentos.salas.agendamento.agendamento.AgendamentoRepository;
import agendamentos.salas.agendamento.agendamento.AgendamentoRequestDTO;
import agendamentos.salas.agendamento.agendamento.AgendamentoResponseDTO;
import agendamentos.salas.agendamento.cliente.Cliente;
import agendamentos.salas.agendamento.cliente.ClienteRepository;
import agendamentos.salas.agendamento.cliente.ClienteResponseDTO;
import agendamentos.salas.agendamento.disponibilidade.Disponibilidade;
import agendamentos.salas.agendamento.disponibilidade.DisponibilidadeRepository;
import agendamentos.salas.agendamento.profissional.Profissional;
import agendamentos.salas.agendamento.profissional.ProfissionalRepository;
import agendamentos.salas.agendamento.profissional.ProfissionalResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/consultarAgendamentos")
    public String getAll(Model model) {

        List<AgendamentoResponseDTO> agendamentoList = repositoryAgendamento.findAll().stream().map(AgendamentoResponseDTO::new).toList();

        model.addAttribute("consultaAgendamentos", agendamentoList);
        return "consultaAgendamentos";
    }

    @GetMapping("/adicionaAgendamento")
    public String showAddAgendamentoPage(Model model) {

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

        return "adicionarAgendamento";  // Nome do template Thymeleaf para a página de login
    }

    @PostMapping("/insereAgendamento")
    public String addAgendamento(@ModelAttribute AgendamentoRequestDTO data) {

        System.out.println(data.status());
        System.out.println(data.data());
        System.out.println(data.horaInicio());
        System.out.println(data.horaFim());
        System.out.println(data.profissionalId());
        System.out.println(data.clienteId());

        Optional<Profissional> profissionalOptional = repositoryProfissional.findById(Integer.valueOf(data.profissionalId()));
        Profissional profissional = profissionalOptional.get();

        Optional<Cliente> clienteOptional = repositoryCliente.findById(Integer.valueOf(data.clienteId()));
        Cliente cliente = clienteOptional.get();

        Agendamento agendamentoData = new Agendamento(data, profissional, cliente);
        repositoryAgendamento.save(agendamentoData);
        return "redirect:/consultarAgendamentos";  // Você pode redirecionar para a mesma página com uma mensagem de sucesso ou para outra página
    }
}
