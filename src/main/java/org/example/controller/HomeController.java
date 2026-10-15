package org.example.controller;

import org.example.service.AgendaService;
import org.example.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final AgendaService agendaService;
    private final ClienteService clienteService;

    public HomeController(AgendaService agendaService, ClienteService clienteService) {
        this.agendaService = agendaService;
        this.clienteService = clienteService;
    }

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("totalAgendamentos", agendaService.count());
        model.addAttribute("faturamento", agendaService.faturamentoPrevisto());
        model.addAttribute("totalClientes", clienteService.count());
        return "index";
    }
}
