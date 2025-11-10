package com.tecsup.saborgourmet.controller;

import com.tecsup.saborgourmet.model.Compra;
import com.tecsup.saborgourmet.service.CompraService;
import com.tecsup.saborgourmet.service.InsumoService;
import com.tecsup.saborgourmet.service.ProveedorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/compras")
@RequiredArgsConstructor
@Slf4j
public class CompraController {

    private final CompraService compraService;
    private final ProveedorService proveedorService;
    private final InsumoService insumoService;

    @GetMapping
    public String listarCompras(Model model) {
        model.addAttribute("compras", compraService.findAll());
        return "compras/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("compra", new Compra());
        model.addAttribute("proveedores", proveedorService.findAllActivos());
        model.addAttribute("insumos", insumoService.findAllActivos());
        return "compras/formulario";
    }

    @PostMapping
    public String guardarCompra(@Valid @ModelAttribute Compra compra,
                                BindingResult result,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "compras/formulario";
        }

        try {
            compraService.createCompra(compra);
            redirectAttributes.addFlashAttribute("success",
                    "Compra registrada exitosamente. Stock actualizado.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/compras/nuevo";
        }

        return "redirect:/compras";
    }

    @GetMapping("/ver/{id}")
    public String verCompra(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("compra", compraService.findById(id));
            return "compras/detalle";
        } catch (Exception e) {
            return "redirect:/compras";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCompra(@PathVariable Long id,
                                 RedirectAttributes redirectAttributes) {
        try {
            compraService.deleteCompra(id);
            redirectAttributes.addFlashAttribute("success",
                    "Compra eliminada. Stock ajustado.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/compras";
    }
}
