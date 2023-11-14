package com.darksun.controller;

import com.darksun.model.Produto;
import com.darksun.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    ProdutoService service;

    @PostMapping
    public ResponseEntity<Produto> criar(@RequestBody Produto produto){
        return new ResponseEntity<>(service.criar(produto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Produto>> buscar(){
        return new ResponseEntity<>(service.buscar(), HttpStatus.OK);
    }

    @GetMapping("/{codigoDeBarras}")
    public ResponseEntity<Produto> buscarPorCodigoDeBarras(@PathVariable Long codigoDeBarras){
        return new ResponseEntity<>(service.buscarPorCodigoDeBarras(codigoDeBarras), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Produto> atualizar(@RequestBody Produto produto){
        return new ResponseEntity<>(service.atualizar(produto), HttpStatus.OK);
    }

    @DeleteMapping("/{codigoDeBarras}")
    public ResponseEntity<Void> deletar(@PathVariable Long codigoDeBarras){
        service.deletar(codigoDeBarras);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
