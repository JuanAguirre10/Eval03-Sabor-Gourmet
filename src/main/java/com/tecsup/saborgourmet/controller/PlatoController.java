package com.tecsup.saborgourmet.controller;

import com.tecsup.saborgourmet.model.Plato;
import com.tecsup.saborgourmet.service.PlatoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/platos")
@RequiredArgsConstructor
@Slf4j
public class PlatoController {

    private final PlatoService platoService;

    @GetMapping
    public String listarPlatos(Model model) {
        model.addAttribute("platos", platoService.findAllActivos());
        return "platos/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("plato", new Plato());
        return "platos/formulario";
    }

    @PostMapping
    public String guardarPlato(@Valid @ModelAttribute Plato plato,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "platos/formulario";
        }

        try {
            platoService.createPlato(plato);
            redirectAttributes.addFlashAttribute("success",
                    "Plato creado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/platos/nuevo";
        }

        return "redirect:/platos";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("plato", platoService.findById(id));
            return "platos/formulario";
        } catch (Exception e) {
            return "redirect:/platos";
        }
    }

    @PostMapping("/editar/{id}")
    public String actualizarPlato(@PathVariable Long id,
                                  @Valid @ModelAttribute Plato plato,
                                  BindingResult result,
                                  RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "platos/formulario";
        }

        try {
            platoService.updatePlato(id, plato);
            redirectAttributes.addFlashAttribute("success",
                    "Plato actualizado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/platos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarPlato(@PathVariable Long id,
                                RedirectAttributes redirectAttributes) {
        try {
            platoService.deletePlato(id);
            redirectAttributes.addFlashAttribute("success",
                    "Plato eliminado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/platos";
    }
}