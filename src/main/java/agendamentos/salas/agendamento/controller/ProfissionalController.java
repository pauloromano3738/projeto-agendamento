package agendamentos.salas.agendamento.controller;

import agendamentos.salas.agendamento.profissional.Profissional;
import agendamentos.salas.agendamento.profissional.ProfissionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/profissionais")
public class ProfissionalController {

    @Autowired
    private ProfissionalRepository repository;

    @GetMapping
    public List<Profissional> getAll() {

        List<Profissional> profissionalList = repository.findAll();
        return profissionalList;
    }
}
