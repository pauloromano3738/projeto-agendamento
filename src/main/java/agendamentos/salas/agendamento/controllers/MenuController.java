package agendamentos.salas.agendamento.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {

    @GetMapping("/home")
    public String showMenu() {

        return "testeCheck";
    }
}
