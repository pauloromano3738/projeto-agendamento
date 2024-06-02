package agendamentos.salas.agendamento.controller;

import agendamentos.salas.agendamento.disponibilidade.Disponibilidade;
import agendamentos.salas.agendamento.disponibilidade.DisponibilidadeRepository;
import agendamentos.salas.agendamento.disponibilidade.DisponibilidadeRequestDTO;
import agendamentos.salas.agendamento.profissional.Profissional;
import agendamentos.salas.agendamento.profissional.ProfissionalRepository;
import agendamentos.salas.agendamento.profissional.ProfissionalRequestDTO;
import agendamentos.salas.agendamento.profissional.ProfissionalResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class ProfissionalController {

    @Autowired
    private DisponibilidadeRepository repositoryDisponibilidade;

    @Autowired
    private ProfissionalRepository repositoryProfissional;

    @GetMapping("/consultaProfissionais")
    public String getAll(Model model) {

        List<ProfissionalResponseDTO> profissionalList = repositoryProfissional.findAll().stream().map(ProfissionalResponseDTO::new).toList();
        model.addAttribute("consultaProfissionais", profissionalList);
        return "consultaProfissionais";
    }

    @GetMapping("/cadastroProfissional")
    public String showAddProfissionalPage() {

        return "cadastroProfissional";  // Nome do template Thymeleaf para a página de login
    }

    @PostMapping("/insereProfissionais")
    public String addProfissional(@ModelAttribute DisponibilidadeRequestDTO dataDisponibilidade, @ModelAttribute ProfissionalRequestDTO dataProfissional, Model model) {

        Disponibilidade disponibilidadeData = new Disponibilidade(dataDisponibilidade);
        repositoryDisponibilidade.save(disponibilidadeData);

        Optional<Disponibilidade> disponibilidadeOptional = repositoryDisponibilidade.findLastrowId();;
        Disponibilidade disponibilidade = disponibilidadeOptional.get();

        Profissional profissionalData = new Profissional(dataProfissional, disponibilidade);
        repositoryProfissional.save(profissionalData);

        model.addAttribute("message", "Profissional cadastrado com sucesso!");
        return "redirect:/consultaProfissionais";  // Você pode redirecionar para a mesma página com uma mensagem de sucesso ou para outra página
    }

    @PostMapping("/deletarProfissional")
    public String deleteProfissional(@RequestParam Integer id) {

        repositoryProfissional.deleteById(id);
        return "redirect:/consultaProfissionais";
    }
}
