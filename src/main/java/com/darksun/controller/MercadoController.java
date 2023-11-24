package com.darksun.controller;

import com.darksun.model.Produto;
import com.darksun.service.MercadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping
public class MercadoController {

    @Autowired
    MercadoService service;

    @PutMapping("/abastecerEstoque/{codigoDeBarras}/{quantidade}")
    public ResponseEntity<Produto> abastecerEstoque (@PathVariable Long codigoDeBarras, @PathVariable Integer quantidade){
        return new ResponseEntity<>(service.abastecerEstoque(codigoDeBarras, quantidade), HttpStatus.OK);
    }

    @GetMapping("/calcularLucro/{dataInicio}/{dataFim}")
    public ResponseEntity<Double> calcularLucro (@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataInicio, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataFim){
        return new ResponseEntity<>(service.calcularLucro(dataInicio, dataFim), HttpStatus.OK);
    }
}
