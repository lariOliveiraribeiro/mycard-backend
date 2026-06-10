package com.mycard.controller;

import com.mycard.model.GastoDTO;
import com.mycard.service.GastoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gastos")
@CrossOrigin(origins = "http://localhost:5173")
public class GastoController {

    private final GastoService service;

    public GastoController(GastoService service) {
        this.service = service;
    }

    @PostMapping
    public GastoDTO criar(@RequestBody GastoDTO dto) {
        return service.salvar(dto);
    }

    @GetMapping
    public List<GastoDTO> listar() {
        return service.listar();
    }
}