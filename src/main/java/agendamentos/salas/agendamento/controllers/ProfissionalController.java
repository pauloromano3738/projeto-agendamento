package agendamentos.salas.agendamento.controllers;

import agendamentos.salas.agendamento.domain.disponibilidade.Disponibilidade;
import agendamentos.salas.agendamento.domain.disponibilidade.DisponibilidadeRepository;
import agendamentos.salas.agendamento.domain.disponibilidade.DisponibilidadeRequestDTO;
import agendamentos.salas.agendamento.domain.profissional.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String getAll(HttpServletRequest request, Model model) {

        List<ProfissionalResponseDTO> profissionalList = repositoryProfissional.findAll().stream().map(ProfissionalResponseDTO::new).toList();
        model.addAttribute("consultaProfissionais", profissionalList);
        model.addAttribute("urlConsultaProfissional" ,request);
        return "consultaProfissionais";
    }

    @GetMapping("/cadastroProfissional")
    public String showAddProfissionalPage(HttpServletRequest request, Model model) {
        model.addAttribute("urlCadastroProfissional" ,request);
        return "cadastroProfissional";  // Nome do template Thymeleaf para a página de login
    }

    @GetMapping("/cadastroProfissional2")
    public String showAddProfissional2Page() {
        return "cadastroProfissional2";  // Nome do template Thymeleaf para a página de login
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

    @RequestMapping(value = "/consultaProfissionais/delete/{id}", method = {RequestMethod.GET, RequestMethod.DELETE})
    public String deleteProfissional(@PathVariable Integer id) {
        repositoryProfissional.deleteById(id);
        return "redirect:/consultaProfissionais";
    }
}
