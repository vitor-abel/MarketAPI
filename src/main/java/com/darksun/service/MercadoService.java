package com.darksun.service;

import com.darksun.model.Produto;
import com.darksun.model.Venda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class MercadoService {
    @Autowired
    ProdutoService produtoService;
    @Autowired
    VendaService vendaService;

    public Produto abastecerEstoque (Long codigoDeBarras, Integer quantidade){
        Produto produto = produtoService.buscarPorCodigoDeBarras(codigoDeBarras);
        produtoService.incrementarQuantidadeNoEstoque(produto, quantidade);
        return produto;
    }

    public Double calcularLucro(LocalDate dataInicio, LocalDate dataFim){
        double lucro = 0.;
        for(Venda venda : vendaService.buscar()){
            if(venda.getDataHoraInicio().isAfter(dataInicio.atStartOfDay()) && venda.getDataHoraFim()!=null && venda.getDataHoraFim().isBefore(dataFim.plusDays(1).atStartOfDay())){
                for (Produto produto : venda.getProdutos()){
                    lucro += produto.getPrecoVenda() - produto.getPrecoCompra();
                }
            }
        }
        return lucro;
    }
}
