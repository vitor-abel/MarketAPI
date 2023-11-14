package com.darksun.service;

import com.darksun.model.Produto;
import com.darksun.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ProdutoService {
    @Autowired
    ProdutoRepository repository;

    public Produto criar(Produto produto){
        if(repository.findById(produto.getCodigoDeBarras()).isPresent()){
            throw new IllegalArgumentException("Produto com código de barras já cadastrado");
        }
        if(produto.getPrecoCompra() == null || produto.getPrecoCompra()<=0.){
            throw new IllegalArgumentException("Preço de compra inválido");
        }
        if(produto.getPrecoVenda() == null || produto.getPrecoVenda()<=0.){
            throw new IllegalArgumentException("Preço de venda inválido");
        }
        if(produto.getQtdEstoque() == null || produto.getQtdEstoque()<0){
            throw new IllegalArgumentException("Estoque não pode ser negativo ou nulo");
        }
        return repository.save(produto);
    }

    public List<Produto> buscar(){
        return repository.findAll();
    }

    public Produto buscarPorCodigoDeBarras(Long codigoDeBarras){
        return repository.findById(codigoDeBarras).orElseThrow(()->new EntityNotFoundException("Código de barras não encontrado"));
    }

    public Produto atualizar(Produto produto){
        buscarPorCodigoDeBarras(produto.getCodigoDeBarras());
        return repository.save(produto);
    }

    public void deletar(Long codigoDeBarras){
        repository.deleteById(codigoDeBarras);
    }
}
