package org.example.controller;

import jakarta.validation.Valid;
import org.example.exception.RegraNegocioException;
import org.example.model.TipoAcesso;
import org.example.service.TipoAcessoService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tipos-acesso")
public class TipoAcessoController {

    private final TipoAcessoService service;

    public TipoAcessoController(TipoAcessoService service) {
        this.service = service;
    }

    private void opcoes(Model model) {
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("lista", service.findAll());
        return "tiposAcesso";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("tipoAcesso", new TipoAcesso());
        opcoes(model);
        return "tipoAcessoForm";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        var obj = service.findOne(id);
        if (obj.isEmpty()) {
            return "redirect:/tipos-acesso";
        }
        model.addAttribute("tipoAcesso", obj.get());
        opcoes(model);
        return "tipoAcessoForm";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute("tipoAcesso") TipoAcesso obj, BindingResult result, Model model, RedirectAttributes ra) {
        if (result.hasErrors()) {
            opcoes(model);
            return "tipoAcessoForm";
        }
        try {
            service.save(obj);
        } catch (RegraNegocioException ex) {
            model.addAttribute("erro", ex.getMessage());
            opcoes(model);
            return "tipoAcessoForm";
        }
        ra.addFlashAttribute("sucesso", "Registro salvo com sucesso.");
        return "redirect:/tipos-acesso";
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
        return "redirect:/tipos-acesso";
    }
}
