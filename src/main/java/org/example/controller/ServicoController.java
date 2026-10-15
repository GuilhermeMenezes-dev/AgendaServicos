package org.example.controller;

import jakarta.validation.Valid;
import org.example.exception.RegraNegocioException;
import org.example.model.Servico;
import org.example.service.ServicoService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/servicos")
public class ServicoController {

    private final ServicoService service;

    public ServicoController(ServicoService service) {
        this.service = service;
    }

    private void opcoes(Model model) {
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("lista", service.findAll());
        return "servicos";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("servico", new Servico());
        opcoes(model);
        return "servicoForm";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        var obj = service.findOne(id);
        if (obj.isEmpty()) {
            return "redirect:/servicos";
        }
        model.addAttribute("servico", obj.get());
        opcoes(model);
        return "servicoForm";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute("servico") Servico obj, BindingResult result, Model model, RedirectAttributes ra) {
        if (result.hasErrors()) {
            opcoes(model);
            return "servicoForm";
        }
        try {
            service.save(obj);
        } catch (RegraNegocioException ex) {
            model.addAttribute("erro", ex.getMessage());
            opcoes(model);
            return "servicoForm";
        }
        ra.addFlashAttribute("sucesso", "Registro salvo com sucesso.");
        return "redirect:/servicos";
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
        return "redirect:/servicos";
    }
}
