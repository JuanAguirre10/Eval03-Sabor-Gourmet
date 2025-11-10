package com.tecsup.saborgourmet.controller;

import com.tecsup.saborgourmet.model.Mesa;
import com.tecsup.saborgourmet.service.MesaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/mesas")
@RequiredArgsConstructor
@Slf4j
public class MesaController {

    private final MesaService mesaService;

    @GetMapping
    public String listarMesas(Model model) {
        model.addAttribute("mesas", mesaService.findAll());
        model.addAttribute("disponibles", mesaService.findMesasDisponibles().size());
        return "mesas/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("mesa", new Mesa());
        return "mesas/formulario";
    }

    @PostMapping
    public String guardarMesa(@Valid @ModelAttribute Mesa mesa,
                              BindingResult result,
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "mesas/formulario";
        }

        try {
            mesaService.createMesa(mesa);
            redirectAttributes.addFlashAttribute("success",
                    "Mesa creada exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/mesas/nuevo";
        }

        return "redirect:/mesas";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("mesa", mesaService.findById(id));
            return "mesas/formulario";
        } catch (Exception e) {
            return "redirect:/mesas";
        }
    }

    @PostMapping("/editar/{id}")
    public String actualizarMesa(@PathVariable Long id,
                                 @Valid @ModelAttribute Mesa mesa,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "mesas/formulario";
        }

        try {
            mesaService.updateMesa(id, mesa);
            redirectAttributes.addFlashAttribute("success",
                    "Mesa actualizada exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/mesas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarMesa(@PathVariable Long id,
                               RedirectAttributes redirectAttributes) {
        try {
            mesaService.deleteMesa(id);
            redirectAttributes.addFlashAttribute("success",
                    "Mesa eliminada exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/mesas";
    }

    @GetMapping("/ocupar/{id}")
    public String ocuparMesa(@PathVariable Long id,
                             RedirectAttributes redirectAttributes) {
        try {
            mesaService.ocuparMesa(id);
            redirectAttributes.addFlashAttribute("success",
                    "Mesa ocupada exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/mesas";
    }

    @GetMapping("/liberar/{id}")
    public String liberarMesa(@PathVariable Long id,
                              RedirectAttributes redirectAttributes) {
        try {
            mesaService.liberarMesa(id);
            redirectAttributes.addFlashAttribute("success",
                    "Mesa liberada exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/mesas";
    }
}