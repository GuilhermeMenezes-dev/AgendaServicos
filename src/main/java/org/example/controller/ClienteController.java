package org.example.controller;

import jakarta.validation.Valid;
import org.example.exception.RegraNegocioException;
import org.example.model.Cliente;
import org.example.service.ClienteService;
import org.example.service.EnderecoService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService service;
    private final EnderecoService enderecoService;

    public ClienteController(ClienteService service, EnderecoService enderecoService) {
        this.service = service;
        this.enderecoService = enderecoService;
    }

    private void opcoes(Model model) {
        model.addAttribute("enderecos", enderecoService.findAll());
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("lista", service.findAll());
        return "clientes";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("cliente", new Cliente());
        opcoes(model);
        return "clienteForm";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        var obj = service.findOne(id);
        if (obj.isEmpty()) {
            return "redirect:/clientes";
        }
        model.addAttribute("cliente", obj.get());
        opcoes(model);
        return "clienteForm";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute("cliente") Cliente obj, BindingResult result, Model model, RedirectAttributes ra) {
        if (result.hasErrors()) {
            opcoes(model);
            return "clienteForm";
        }
        try {
            service.save(obj);
        } catch (RegraNegocioException ex) {
            model.addAttribute("erro", ex.getMessage());
            opcoes(model);
            return "clienteForm";
        }
        ra.addFlashAttribute("sucesso", "Registro salvo com sucesso.");
        return "redirect:/clientes";
    }

    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("sucesso", "Registro excluído com sucesso.");
        } catch (RegraNegocioException ex) {
            ra.addFlashAttribute("erro", ex.getMessage());
        } catch (DataIntegrityViolationException ex) {
            ra.addFlashAttribute("erro", "Não é possível excluir: o registro está vinculado a outros cadastros.");
        }
        return "redirect:/clientes";
    }
}
