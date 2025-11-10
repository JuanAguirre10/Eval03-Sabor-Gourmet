package com.tecsup.saborgourmet.controller;

import com.tecsup.saborgourmet.service.MesaService;
import com.tecsup.saborgourmet.service.PedidoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final MesaService mesaService;
    private final PedidoService pedidoService;

    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        model.addAttribute("usuario", authentication.getName());
        model.addAttribute("mesasDisponibles", mesaService.contarMesasDisponibles());
        model.addAttribute("pedidosActivos", pedidoService.findPedidosActivos().size());
        model.addAttribute("ventasDiarias", pedidoService.calcularVentasDiarias());

        return "dashboard";
    }

    @GetMapping("/error/403")
    public String accessDenied() {
        return "error/403";
    }
}