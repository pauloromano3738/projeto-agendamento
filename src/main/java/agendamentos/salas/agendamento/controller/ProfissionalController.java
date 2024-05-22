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
import java.util.Optional;

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

    @PostMapping("/insereProfissionais")
    public void addProfissional(@RequestBody ProfissionalRequestDTO data) {

        Profissional profissionalData = new Profissional(data);
        repository.save(profissionalData);
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";  // Nome do template Thymeleaf para a página de login
    }

    @PostMapping("/verifyLogin")
    public String verifyLogin(@RequestParam String login, @RequestParam String senha, Model model) {
        Optional<Profissional> profissionalOptional = repository.findByLoginAndSenha(login, senha);

        if (profissionalOptional.isPresent()) {

            model.addAttribute("profissional", profissionalOptional.get());
            return "redirect:/consultaProfissionais";
        } else {

            model.addAttribute("error", "Login ou senha inválidos");
            return "redirect:/login";
        }
    }
}
