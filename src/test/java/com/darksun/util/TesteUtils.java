package com.darksun.util;

import com.darksun.model.Produto;
import com.darksun.model.Venda;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class TesteUtils {

    private final List<Produto> produtos = new ArrayList<>();
    private final List<Venda> vendas = new ArrayList<>();

    public TesteUtils() {
        Produto batata = new Produto(123L, "batata", true, 3.00, 5.00, 100);
        produtos.add(batata);
        Produto cenoura = new Produto(234L, "cenoura", true, 2.00, 4.00, 50);
        produtos.add(cenoura);
        Produto aipim = new Produto(345L, "aipim", false, 3.00, 4.00, 20);
        produtos.add(aipim);
        Produto tomate = new Produto(456L, "tomate", true, 2.00, 1.00, 0);
        produtos.add(tomate);

        List<Produto> carrinho1 = new ArrayList<>();
        carrinho1.add(batata);
        carrinho1.add(cenoura);
        Venda venda1 = new Venda(1L, LocalDateTime.now(), LocalDateTime.now().minusDays(1).plusMinutes(5), carrinho1);
        vendas.add(venda1);

        List<Produto> carrinho2 = new ArrayList<>();
        carrinho2.add(batata);
        carrinho2.add(batata);
        carrinho2.add(batata);
        Venda venda2 = new Venda(2L, LocalDateTime.now().minusMinutes(20), null, carrinho2);
        vendas.add(venda2);

        Venda venda3 = new Venda(3L, LocalDateTime.now().minusDays(1), LocalDateTime.now().minusDays(1).plusMinutes(5), null);
        vendas.add(venda3);

        List<Produto> carrinho3 = new ArrayList<>();
        carrinho3.add(batata);
        carrinho3.add(tomate);
        Venda venda4 = new Venda(4L, LocalDateTime.now().minusDays(1), null, carrinho3);
        vendas.add(venda4);
    }

}
