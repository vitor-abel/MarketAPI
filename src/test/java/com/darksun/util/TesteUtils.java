package com.darksun.util;

import com.darksun.model.Produto;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class TesteUtils {

    private final List<Produto> produtos = new ArrayList<>();

    public TesteUtils(){
        Produto batata = new Produto(123L,"batata",true,3.00,5.00,100);
        produtos.add(batata);
        Produto cenoura = new Produto(234L,"cenoura",true,2.00,4.00,50);
        produtos.add(cenoura);
        Produto aipim = new Produto(345L,"aipim",false,3.00,4.00,20);
        produtos.add(aipim);
        Produto tomate = new Produto(456L,"tomate",true,2.00,1.00,0);
        produtos.add(tomate);
    }

}
