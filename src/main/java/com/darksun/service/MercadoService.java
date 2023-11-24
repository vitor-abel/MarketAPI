package com.darksun.service;

import com.darksun.model.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MercadoService {
    @Autowired
    ProdutoService produtoService;

    public Produto abastecerEstoque (Long codigoDeBarras, Integer quantidade){
        Produto produto = produtoService.buscarPorCodigoDeBarras(codigoDeBarras);
        produtoService.incrementarQuantidadeNoEstoque(produto, quantidade);
        return produto;
    }
}
