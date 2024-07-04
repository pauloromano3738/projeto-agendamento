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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Time;
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

    @GetMapping("/profissionais")
    public String getAll(@RequestParam(value = "idProfissional", required = false) Integer idProfissional, @RequestParam(value = "operation", required = false) String operation, HttpServletRequest request, Model model) {

        List<ProfissionalResponseDTO> profissionalList = repositoryProfissional.findAll().stream().map(ProfissionalResponseDTO::new).toList();
        model.addAttribute("consultaProfissionais", profissionalList);
        model.addAttribute("urlConsultaProfissional" ,request);

        if (idProfissional != null) {
            Optional<Profissional> profissionalOptional = repositoryProfissional.findById(idProfissional);
            Profissional profissional = profissionalOptional.get();
            model.addAttribute("profissionalDetails", profissional);
            if (operation != null) {
                return "editarProfissional";
            }
        }

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

    public String corrigeData(DiasRequestDTO diasDisponibilidade) {
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

        return dias;
    }

    @PostMapping("/insereProfissionais")
    public String addProfissional(@ModelAttribute DiasRequestDTO diasDisponibilidade, @ModelAttribute DisponibilidadeRequestDTO dataDisponibilidade, @ModelAttribute ProfissionalRequestDTO dataProfissional, Model model) {

        String dias = corrigeData(diasDisponibilidade);

        Disponibilidade disponibilidadeData = new Disponibilidade(dataDisponibilidade, dias);
        repositoryDisponibilidade.save(disponibilidadeData);

        Optional<Disponibilidade> disponibilidadeOptional = repositoryDisponibilidade.findLastrowId();
        Disponibilidade disponibilidade = disponibilidadeOptional.get();

        Profissional profissionalData = new Profissional(dataProfissional, disponibilidade);
        repositoryProfissional.save(profissionalData);

        model.addAttribute("message", "Profissional cadastrado com sucesso!");
        return "redirect:/profissionais";
    }

    @RequestMapping(value = "/insereProfissionais/update/{id}", method = {RequestMethod.GET, RequestMethod.PUT})
    public String updateProfissional(@PathVariable Integer id, @ModelAttribute DiasRequestDTO diasDisponibilidade, @ModelAttribute DisponibilidadeRequestDTO dataDisponibilidade, @ModelAttribute ProfissionalRequestDTO dataProfissional, Model model) {

        String dias = corrigeData(diasDisponibilidade);

        Optional<Profissional> profissionalOptional = repositoryProfissional.findById(id);
        Profissional profissional = profissionalOptional.get();

        Disponibilidade disponibilidade = profissional.getDisponibilidade();

        profissional.setNome(dataProfissional.nome());
        profissional.setCpf(dataProfissional.cpf());
        profissional.setLogin(dataProfissional.login());
        profissional.setSenha(dataProfissional.senha());

        disponibilidade.setDias_semana(dias);
        disponibilidade.setHorario_inicio(Time.valueOf(dataDisponibilidade.horario_inicio() + ":00"));
        disponibilidade.setHorario_fim(Time.valueOf(dataDisponibilidade.horario_fim() + ":00"));

        profissional.setDisponibilidade(disponibilidade);

        repositoryProfissional.save(profissional);
        return "redirect:/profissionais";
    }

    @RequestMapping(value = "/profissionais/details/{id}", method = {RequestMethod.GET, RequestMethod.DELETE})
    public String detailsProfissional(@PathVariable Integer id) {
        return "redirect:/profissionais?idProfissional=" + id;
    }

    @RequestMapping(value = "/profissionais/edit/{id}", method = {RequestMethod.GET, RequestMethod.DELETE})
    public String editProfissional(@PathVariable Integer id, RedirectAttributes redirectAttributes, Model model) {
        redirectAttributes.addAttribute("operation","edit");
        return "redirect:/profissionais?idProfissional=" + id;
    }

    @RequestMapping(value = "/profissionais/delete/{id}", method = {RequestMethod.GET, RequestMethod.DELETE})
    public String deleteProfissional(@PathVariable Integer id) {
        repositoryProfissional.deleteById(id);
        return "redirect:/profissionais";
    }
}
