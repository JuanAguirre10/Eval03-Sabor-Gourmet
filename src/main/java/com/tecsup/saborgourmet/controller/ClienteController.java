package com.tecsup.saborgourmet.controller;

import com.tecsup.saborgourmet.model.Cliente;
import com.tecsup.saborgourmet.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/clientes")
@RequiredArgsConstructor
@Slf4j
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    public String listarClientes(Model model) {
        model.addAttribute("clientes", clienteService.findAllActivos());
        return "clientes/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "clientes/formulario";
    }

    @PostMapping
    public String guardarCliente(@Valid @ModelAttribute Cliente cliente,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "clientes/formulario";
        }

        try {
            clienteService.createCliente(cliente);
            redirectAttributes.addFlashAttribute("success",
                    "Cliente creado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/clientes/nuevo";
        }

        return "redirect:/clientes";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("cliente", clienteService.findById(id));
            return "clientes/formulario";
        } catch (Exception e) {
            return "redirect:/clientes";
        }
    }

    @PostMapping("/editar/{id}")
    public String actualizarCliente(@PathVariable Long id,
                                    @Valid @ModelAttribute Cliente cliente,
                                    BindingResult result,
                                    RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "clientes/formulario";
        }

        try {
            clienteService.updateCliente(id, cliente);
            redirectAttributes.addFlashAttribute("success",
                    "Cliente actualizado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/clientes";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable Long id,
                                  RedirectAttributes redirectAttributes) {
        try {
            clienteService.deleteCliente(id);
            redirectAttributes.addFlashAttribute("success",
                    "Cliente eliminado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/clientes";
    }

    @GetMapping("/buscar")
    public String buscarClientes(@RequestParam String query, Model model) {
        model.addAttribute("clientes", clienteService.buscarPorNombre(query));
        model.addAttribute("query", query);
        return "clientes/lista";
    }
}