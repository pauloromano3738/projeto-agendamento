package agendamentos.salas.agendamento.controller;

import agendamentos.salas.agendamento.profissional.Profissional;
import agendamentos.salas.agendamento.profissional.ProfissionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private ProfissionalRepository repository;

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
