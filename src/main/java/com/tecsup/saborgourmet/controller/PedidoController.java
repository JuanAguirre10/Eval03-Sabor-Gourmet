package com.tecsup.saborgourmet.controller;

import com.tecsup.saborgourmet.model.Pedido;
import com.tecsup.saborgourmet.service.ClienteService;
import com.tecsup.saborgourmet.service.MesaService;
import com.tecsup.saborgourmet.service.PedidoService;
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
@RequestMapping("/pedidos")
@RequiredArgsConstructor
@Slf4j
public class PedidoController {

    private final PedidoService pedidoService;
    private final MesaService mesaService;
    private final ClienteService clienteService;
    private final PlatoService platoService;

    @GetMapping
    public String listarPedidos(Model model) {
        model.addAttribute("pedidos", pedidoService.findPedidosActivos());
        return "pedidos/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("pedido", new Pedido());
        model.addAttribute("mesas", mesaService.findMesasDisponibles());
        model.addAttribute("clientes", clienteService.findAllActivos());
        model.addAttribute("platos", platoService.findAllActivos());
        return "pedidos/formulario";
    }

    @PostMapping
    public String guardarPedido(@Valid @ModelAttribute Pedido pedido,
                                BindingResult result,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "pedidos/formulario";
        }

        try {
            pedidoService.createPedido(pedido);
            redirectAttributes.addFlashAttribute("success",
                    "Pedido creado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/pedidos/nuevo";
        }

        return "redirect:/pedidos";
    }

    @GetMapping("/ver/{id}")
    public String verPedido(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("pedido", pedidoService.findById(id));
            return "pedidos/detalle";
        } catch (Exception e) {
            return "redirect:/pedidos";
        }
    }

    @GetMapping("/cambiar-estado/{id}")
    public String cambiarEstado(@PathVariable Long id,
                                @RequestParam String estado,
                                RedirectAttributes redirectAttributes) {
        try {
            pedidoService.cambiarEstado(id, estado);
            redirectAttributes.addFlashAttribute("success",
                    "Estado cambiado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/pedidos";
    }

    @GetMapping("/cerrar/{id}")
    public String cerrarPedido(@PathVariable Long id,
                               RedirectAttributes redirectAttributes) {
        try {
            pedidoService.cerrarPedido(id);
            redirectAttributes.addFlashAttribute("success",
                    "Pedido cerrado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/pedidos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarPedido(@PathVariable Long id,
                                 RedirectAttributes redirectAttributes) {
        try {
            pedidoService.deletePedido(id);
            redirectAttributes.addFlashAttribute("success",
                    "Pedido eliminado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/pedidos";
    }
}