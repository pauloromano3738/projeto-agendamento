package agendamentos.salas.agendamento.controller;

import agendamentos.salas.agendamento.agendamento.Agendamento;
import agendamentos.salas.agendamento.agendamento.AgendamentoRepository;
import agendamentos.salas.agendamento.agendamento.AgendamentoRequestDTO;
import agendamentos.salas.agendamento.agendamento.AgendamentoResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AgendamentoController {

    @Autowired
    private AgendamentoRepository repository;

    @GetMapping("/consultarAgendamentos")
    public String getAll(Model model) {

        List<AgendamentoResponseDTO> agendamentoList = repository.findAll().stream().map(AgendamentoResponseDTO::new).toList();

        model.addAttribute("consultaAgendamentos", agendamentoList);
        return "consultaAgendamentos";
    }

    @GetMapping("/adicionaAgendamento")
    public String showAddAgendamentoPage() {

        return "adicionarAgendamento";  // Nome do template Thymeleaf para a página de login
    }

    @PostMapping("/insereAgendamento")
    public String addAgendamento(@ModelAttribute AgendamentoRequestDTO data, Model model) {

        Agendamento agendamentoData = new Agendamento(data);
        repository.save(agendamentoData);
        model.addAttribute("message", "Profissional cadastrado com sucesso!");
        return "redirect:/consultarAgendamentos";  // Você pode redirecionar para a mesma página com uma mensagem de sucesso ou para outra página
    }
}
