package org.example.controller;

import jakarta.validation.Valid;
import org.example.exception.RegraNegocioException;
import org.example.model.Agenda;
import org.example.service.AgendaService;
import org.example.service.ClienteService;
import org.example.service.ServicoService;
import org.example.service.UsuarioService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/agendamentos")
public class AgendaController {

    private final AgendaService service;
    private final ClienteService clienteService;
    private final ServicoService servicoService;
    private final UsuarioService usuarioService;

    public AgendaController(AgendaService service, ClienteService clienteService,
                            ServicoService servicoService, UsuarioService usuarioService) {
        this.service = service;
        this.clienteService = clienteService;
        this.servicoService = servicoService;
        this.usuarioService = usuarioService;
    }

    private void opcoes(Model model) {
        model.addAttribute("clientes", clienteService.findAll());
        model.addAttribute("servicos", servicoService.findAll());
        model.addAttribute("usuarios", usuarioService.findAll());
    }

    @GetMapping
    public String listar(@RequestParam(required = false) Long cliente,
                         @RequestParam(required = false) Long servico,
                         @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate de,
                         @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ate,
                         Model model) {
        model.addAttribute("lista", service.pesquisar(cliente, servico, de, ate));
        model.addAttribute("clientes", clienteService.findAll());
        model.addAttribute("servicos", servicoService.findAll());
        model.addAttribute("fCliente", cliente);
        model.addAttribute("fServico", servico);
        model.addAttribute("fDe", de);
        model.addAttribute("fAte", ate);
        return "agendamentos";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("agenda", new Agenda());
        opcoes(model);
        return "agendaForm";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        var obj = service.findOne(id);
        if (obj.isEmpty()) {
            return "redirect:/agendamentos";
        }
        model.addAttribute("agenda", obj.get());
        opcoes(model);
        return "agendaForm";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute("agenda") Agenda agenda, BindingResult result,
                         Model model, RedirectAttributes ra) {
        if (result.hasErrors()) {
            opcoes(model);
            return "agendaForm";
        }
        try {
            service.save(agenda);
        } catch (RegraNegocioException ex) {
            model.addAttribute("erro", ex.getMessage());
            opcoes(model);
            return "agendaForm";
        }
        ra.addFlashAttribute("sucesso", "Agendamento salvo com sucesso.");
        return "redirect:/agendamentos";
    }

    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes ra) {
        service.delete(id);
        ra.addFlashAttribute("sucesso", "Agendamento excluído com sucesso.");
        return "redirect:/agendamentos";
    }
}
