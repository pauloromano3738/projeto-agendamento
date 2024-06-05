package agendamentos.salas.agendamento.controller;

import agendamentos.salas.agendamento.disponibilidade.Disponibilidade;
import agendamentos.salas.agendamento.disponibilidade.DisponibilidadeRepository;
import agendamentos.salas.agendamento.disponibilidade.DisponibilidadeRequestDTO;
import agendamentos.salas.agendamento.profissional.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public String addProfissional(@ModelAttribute DiasRequestDTO diasDisponibilidade, @ModelAttribute DisponibilidadeRequestDTO dataDisponibilidade, @ModelAttribute ProfissionalRequestDTO dataProfissional, Model model) {

        Integer[] diasArray = {
                diasDisponibilidade.domingo(),
                diasDisponibilidade.segunda(),
                diasDisponibilidade.terca(),
                diasDisponibilidade.quarta(),
                diasDisponibilidade.quinta(),
                diasDisponibilidade.sexta(),
                diasDisponibilidade.sabado()
        };

        String dias = Arrays.stream(diasArray)
                .filter(value -> value != null)
                .map(Object::toString)
                .collect(Collectors.joining(","));


        Disponibilidade disponibilidadeData = new Disponibilidade(dataDisponibilidade, dias);
        repositoryDisponibilidade.save(disponibilidadeData);

        Optional<Disponibilidade> disponibilidadeOptional = repositoryDisponibilidade.findLastrowId();
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
