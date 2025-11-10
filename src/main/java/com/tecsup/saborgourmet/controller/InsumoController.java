package com.tecsup.saborgourmet.controller;

import com.tecsup.saborgourmet.model.Insumo;
import com.tecsup.saborgourmet.service.InsumoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/inventario/insumos")
@RequiredArgsConstructor
@Slf4j
public class InsumoController {

    private final InsumoService insumoService;

    @GetMapping
    public String listarInsumos(Model model) {
        model.addAttribute("insumos", insumoService.findAllActivos());
        model.addAttribute("alertas", insumoService.findInsumosConStockBajo());
        return "inventario/insumos/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("insumo", new Insumo());
        return "inventario/insumos/formulario";
    }

    @PostMapping
    public String guardarInsumo(@Valid @ModelAttribute Insumo insumo,
                                BindingResult result,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "inventario/insumos/formulario";
        }

        try {
            insumoService.createInsumo(insumo);
            redirectAttributes.addFlashAttribute("success",
                    "Insumo creado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/inventario/insumos/nuevo";
        }

        return "redirect:/inventario/insumos";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("insumo", insumoService.findById(id));
            return "inventario/insumos/formulario";
        } catch (Exception e) {
            return "redirect:/inventario/insumos";
        }
    }

    @PostMapping("/editar/{id}")
    public String actualizarInsumo(@PathVariable Long id,
                                   @Valid @ModelAttribute Insumo insumo,
                                   BindingResult result,
                                   RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "inventario/insumos/formulario";
        }

        try {
            insumoService.updateInsumo(id, insumo);
            redirectAttributes.addFlashAttribute("success",
                    "Insumo actualizado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/inventario/insumos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarInsumo(@PathVariable Long id,
                                 RedirectAttributes redirectAttributes) {
        try {
            insumoService.deleteInsumo(id);
            redirectAttributes.addFlashAttribute("success",
                    "Insumo eliminado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/inventario/insumos";
    }

    @GetMapping("/alertas")
    public String verAlertas(Model model) {
        model.addAttribute("insumos", insumoService.findInsumosConStockBajo());
        return "inventario/insumos/alertas";
    }
}