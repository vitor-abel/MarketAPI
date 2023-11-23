package com.darksun.controller;

import com.darksun.model.Venda;
import com.darksun.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendas")
public class VendaController {

    @Autowired
    VendaService service;

    @PostMapping
    public ResponseEntity<Venda> iniciarVenda(){
        return new ResponseEntity<>(service.iniciarVenda(), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Venda>> buscar(){
        return new ResponseEntity<>(service.buscar(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venda> buscarPorId(@PathVariable Long id){
        return new ResponseEntity<>(service.buscarPorId(id), HttpStatus.OK);
    }

    @PutMapping("/{id}/add/{codigoDeBarras}")
    public ResponseEntity<Venda> adicionarProduto(@PathVariable Long id, @PathVariable Long codigoDeBarras){
        return new ResponseEntity<>(service.adicionarProduto(id,codigoDeBarras), HttpStatus.OK);
    }

    @PutMapping("/{id}/remove/{codigoDeBarras}")
    public ResponseEntity<Venda> removerProduto(@PathVariable Long id, @PathVariable Long codigoDeBarras){
        return new ResponseEntity<>(service.removerProduto(id,codigoDeBarras), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Venda> faturar(@PathVariable Long id){
        return new ResponseEntity<>(service.faturar(id), HttpStatus.OK);
    }
}
