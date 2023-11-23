package com.darksun.service;

import com.darksun.model.Produto;
import com.darksun.model.Venda;
import com.darksun.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class VendaService {

    @Autowired
    VendaRepository repository;

    @Autowired
    ProdutoService produtoService;

    public Venda iniciarVenda (){
        Venda venda = new Venda(0L, LocalDateTime.now(), null, new ArrayList<>());
        return repository.save(venda);
    }

    public List<Venda> buscar(){
        return repository.findAll();
    }

    public Venda buscarPorId(Long id){
        return repository.findById(id).orElseThrow(()->new EntityNotFoundException("Venda não encontrada"));
    }

    public Venda adicionarProduto(Long idVenda, Long codigoDeBarras){
        Venda venda = buscarPorId(idVenda);
        validarVendaAberta(venda);
        Produto produto = produtoService.buscarPorCodigoDeBarras(codigoDeBarras);
        venda.getProdutos().add(produto);
        return repository.save(venda);
    }

    public Venda removerProduto(Long idVenda, Long codigoDeBarras){
        Venda venda = buscarPorId(idVenda);
        validarVendaAberta(venda);
        Produto produto = produtoService.buscarPorCodigoDeBarras(codigoDeBarras);
        if (!venda.getProdutos().contains(produto)){
            throw new IllegalArgumentException("Produto não está na lista");
        }
        venda.getProdutos().remove(produto);
        return repository.save(venda);
    }

    public Venda faturar(Long idVenda){
        Venda venda = buscarPorId(idVenda);
        validarVendaAberta(venda);
        venda.setDataHoraFim(LocalDateTime.now());
        return repository.save(venda);
    }

    private void validarVendaAberta(Venda venda){
        if(venda.getDataHoraFim()!=null){
            throw new IllegalArgumentException("Venda já faturada");
        }
    }
}
