package org.example.controller;

import jakarta.validation.Valid;
import org.example.exception.RegraNegocioException;
import org.example.model.Usuario;
import org.example.service.UsuarioService;
import org.example.service.TipoAcessoService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;
    private final TipoAcessoService tipoAcessoService;

    public UsuarioController(UsuarioService service, TipoAcessoService tipoAcessoService) {
        this.service = service;
        this.tipoAcessoService = tipoAcessoService;
    }

    private void opcoes(Model model) {
        model.addAttribute("tiposAcesso", tipoAcessoService.findAll());
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("lista", service.findAll());
        return "usuarios";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("usuario", new Usuario());
        opcoes(model);
        return "usuarioForm";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        var obj = service.findOne(id);
        if (obj.isEmpty()) {
            return "redirect:/usuarios";
        }
        model.addAttribute("usuario", obj.get());
        opcoes(model);
        return "usuarioForm";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute("usuario") Usuario obj, BindingResult result, Model model, RedirectAttributes ra) {
        if (result.hasErrors()) {
            opcoes(model);
            return "usuarioForm";
        }
        try {
            service.save(obj);
        } catch (RegraNegocioException ex) {
            model.addAttribute("erro", ex.getMessage());
            opcoes(model);
            return "usuarioForm";
        }
        ra.addFlashAttribute("sucesso", "Registro salvo com sucesso.");
        return "redirect:/usuarios";
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
        return "redirect:/usuarios";
    }
}
