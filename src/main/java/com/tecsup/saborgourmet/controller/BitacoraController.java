package com.tecsup.saborgourmet.controller;

import com.tecsup.saborgourmet.service.BitacoraService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/bitacora")
@RequiredArgsConstructor
@Slf4j
public class BitacoraController {

    private final BitacoraService bitacoraService;

    @GetMapping
    public String listarBitacora(@RequestParam(required = false) String modulo,
                                 @RequestParam(required = false) String fechaInicio,
                                 @RequestParam(required = false) String fechaFin,
                                 Model model) {
        if (modulo != null && !modulo.isEmpty()) {
            model.addAttribute("registros", bitacoraService.findByModulo(modulo));
        } else if (fechaInicio != null && fechaFin != null) {
            LocalDateTime inicio = LocalDate.parse(fechaInicio).atStartOfDay();
            LocalDateTime fin = LocalDate.parse(fechaFin).atTime(23, 59, 59);
            model.addAttribute("registros", bitacoraService.findByFechaHoraBetween(inicio, fin));
        } else {
            model.addAttribute("registros", bitacoraService.findTop100());
        }

        return "bitacora/lista";
    }
}