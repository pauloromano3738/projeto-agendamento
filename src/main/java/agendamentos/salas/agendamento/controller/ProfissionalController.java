package agendamentos.salas.agendamento.controller;

import agendamentos.salas.agendamento.profissional.Profissional;
import agendamentos.salas.agendamento.profissional.ProfissionalRepository;
import agendamentos.salas.agendamento.profissional.ProfissionalRequestDTO;
import agendamentos.salas.agendamento.profissional.ProfissionalResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProfissionalController {

    @Autowired
    private ProfissionalRepository repository;

    @GetMapping("/consultaProfissionais")
    public String getAll(Model model) {

        List<ProfissionalResponseDTO> profissionalList = repository.findAll().stream().map(ProfissionalResponseDTO::new).toList();
        model.addAttribute("consultaProfissionais", profissionalList);
        return "consultaProfissionais";
    }

    @GetMapping("/cadastroProfissional")
    public String showAddProfissionalPage() {

        return "cadastroProfissional";  // Nome do template Thymeleaf para a página de login
    }

    @PostMapping("/insereProfissionais")
    public String addProfissional(@ModelAttribute ProfissionalRequestDTO data, Model model) {

        Profissional profissionalData = new Profissional(data);
        repository.save(profissionalData);
        model.addAttribute("message", "Profissional cadastrado com sucesso!");
        return "redirect:/consultaProfissionais";  // Você pode redirecionar para a mesma página com uma mensagem de sucesso ou para outra página
    }

    @PostMapping("/deletarProfissional")
    public String deleteProfissional(@RequestParam Integer id) {

        repository.deleteById(id);
        return "redirect:/consultaProfissionais";
    }
}
