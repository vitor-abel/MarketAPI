package com.darksun.service;

import com.darksun.model.Produto;
import com.darksun.model.Venda;
import com.darksun.repository.VendaRepository;
import com.darksun.util.TesteUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class VendaServiceTest {
    @InjectMocks
    private VendaService service;

    @Mock
    private VendaRepository repository;

    @Mock
    private ProdutoService produtoService;

    List<Venda> vendas;
    List<Produto> produtos;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        TesteUtils testeUtils = new TesteUtils();
        vendas = testeUtils.getVendas();
        produtos = testeUtils.getProdutos();
    }

    @Test
    void iniciarVenda() {
        when(repository.save(any())).thenReturn(new Venda(1L, LocalDateTime.now(), null, new ArrayList<>()));
        Venda venda = service.iniciarVenda();
        Assertions.assertEquals(1L, venda.getId());
        Assertions.assertTrue(LocalDateTime.now().plusSeconds(1).isAfter(venda.getDataHoraInicio()));
        Assertions.assertNull(venda.getDataHoraFim());
        verify(repository, times(1)).save(any());
    }

    @Test
    void buscar() {
        when(repository.findAll()).thenReturn(vendas);
        List<Venda> listaVendas = service.buscar();
        Assertions.assertIterableEquals(listaVendas, vendas);
        verify(repository, times(1)).findAll();
    }

    @Test
    void buscarPorId() {
        Venda venda = vendas.get(0);
        when(repository.findById(any())).thenReturn(Optional.of(venda));
        Venda response = service.buscarPorId(1L);
        Assertions.assertEquals(venda, response);
        verify(repository, times(1)).findById(any());
    }

    @Test
    void adicionarProduto_Sucesso() {
        Produto produto = produtos.get(0);
        when(repository.findById(any())).thenReturn(Optional.of(vendas.get(1)));
        when(repository.save(any())).thenReturn(vendas.get(1));
        when(produtoService.buscarPorCodigoDeBarras(any())).thenReturn(produto);
        Venda response = service.adicionarProduto(2L, 123L);
        Assertions.assertEquals(produto.getNome(), response.getProdutos().get(3).getNome());
        verify(repository, times(1)).findById(any());
        verify(repository, times(1)).save(any());
        verify(produtoService, times(1)).buscarPorCodigoDeBarras(any());
    }

    @Test
    void adicionarProduto_Falha_VendaFaturada() {
        Produto produto = produtos.get(0);
        when(repository.findById(any())).thenReturn(Optional.of(vendas.get(0)));
        when(repository.save(any())).thenReturn(vendas.get(0));
        when(produtoService.buscarPorCodigoDeBarras(any())).thenReturn(produto);

        Boolean erroLancado = false;
        try {
            service.adicionarProduto(1L, 123L);
        } catch (IllegalArgumentException ex) {
            erroLancado = true;
        }

        Assertions.assertTrue(erroLancado);
        verify(repository, times(1)).findById(any());
        verify(repository, never()).save(any());
        verify(produtoService, never()).buscarPorCodigoDeBarras(any());
    }

    @Test
    void adicionarProduto_Falha_EstoqueMenorQueUm() {
        Produto produto = produtos.get(3);
        when(repository.findById(any())).thenReturn(Optional.of(vendas.get(1)));
        when(repository.save(any())).thenReturn(vendas.get(1));
        when(produtoService.buscarPorCodigoDeBarras(any())).thenReturn(produto);

        Boolean erroLancado = false;
        try {
            service.adicionarProduto(2L, 456L);
        } catch (IllegalArgumentException e) {
            erroLancado = true;
        }

        Assertions.assertTrue(erroLancado);
        verify(repository, times(1)).findById(any());
        verify(repository, never()).save(any());
        verify(produtoService, times(1)).buscarPorCodigoDeBarras(any());
    }

    @Test
    void removerProduto_Sucesso_ProdutosIguais() {
        Venda venda = vendas.get(1);
        when(repository.findById(any())).thenReturn(Optional.of(vendas.get(1)));
        when(produtoService.buscarPorCodigoDeBarras(any())).thenReturn(produtos.get(0));
        when(repository.save(venda)).thenReturn(venda);

        service.removerProduto(2L, 123L);

        Assertions.assertEquals(2, venda.getProdutos().size());
        Assertions.assertEquals(123L, venda.getProdutos().get(0).getCodigoDeBarras());
        Assertions.assertEquals(123L, venda.getProdutos().get(1).getCodigoDeBarras());
        verify(repository, times(1)).findById(any());
        verify(produtoService, times(1)).buscarPorCodigoDeBarras(any());
        verify(repository, times(1)).save(any());
    }

    @Test
    void removerProduto_Sucesso_ProdutosDiferentes() {
        Venda venda = vendas.get(3);
        when(repository.findById(any())).thenReturn(Optional.of(vendas.get(3)));
        when(produtoService.buscarPorCodigoDeBarras(any())).thenReturn(produtos.get(0));
        when(repository.save(venda)).thenReturn(venda);

        service.removerProduto(4L, 123L);

        Assertions.assertEquals(1, venda.getProdutos().size());
        Assertions.assertEquals(456L, venda.getProdutos().get(0).getCodigoDeBarras());
        verify(repository, times(1)).findById(any());
        verify(produtoService, times(1)).buscarPorCodigoDeBarras(any());
        verify(repository, times(1)).save(any());
    }

    @Test
    void removerProduto_Falha_VendaFaturada() {
        Venda venda = vendas.get(0);
        when(repository.findById(any())).thenReturn(Optional.of(vendas.get(0)));
        when(produtoService.buscarPorCodigoDeBarras(any())).thenReturn(produtos.get(0));
        when(repository.save(venda)).thenReturn(venda);

        Boolean erroLancado = false;
        try {
            service.removerProduto(1L, 123L);
        } catch (IllegalArgumentException e) {
            erroLancado = true;
        }

        Assertions.assertTrue(erroLancado);
        Assertions.assertEquals(2, venda.getProdutos().size());
        Assertions.assertEquals(123L, venda.getProdutos().get(0).getCodigoDeBarras());
        Assertions.assertEquals(234L, venda.getProdutos().get(1).getCodigoDeBarras());
        verify(repository, times(1)).findById(any());
        verify(produtoService, never()).buscarPorCodigoDeBarras(any());
        verify(repository, never()).save(any());
    }

    @Test
    void removerProduto_Falha_EstoqueMenorQueUm() {
        Venda venda = vendas.get(3);
        when(repository.findById(any())).thenReturn(Optional.of(vendas.get(3)));
        when(repository.save(any())).thenReturn(vendas.get(3));
        when(produtoService.buscarPorCodigoDeBarras(any())).thenReturn(produtos.get(1));

        Boolean erroLancado = false;
        try {
            service.removerProduto(4L, 456L);
        } catch (IllegalArgumentException e) {
            erroLancado = true;
        }

        Assertions.assertTrue(erroLancado);
        Assertions.assertEquals(2, venda.getProdutos().size());
        Assertions.assertEquals(123L, venda.getProdutos().get(0).getCodigoDeBarras());
        Assertions.assertEquals(456L, venda.getProdutos().get(1).getCodigoDeBarras());
        verify(repository, times(1)).findById(any());
        verify(repository, never()).save(any());
        verify(produtoService, times(1)).buscarPorCodigoDeBarras(any());
    }

    @Test
    void faturar_Sucesso() {
        Venda venda = vendas.get(1);
        when(repository.findById(any())).thenReturn(Optional.of(vendas.get(1)));
        doNothing().when(produtoService).decrementarQuantidadeNoEstoque(any(Produto.class));
        when(repository.save(any())).thenReturn(vendas.get(1));

        service.faturar(2L);

        Assertions.assertTrue(vendas.get(1).getDataHoraFim().minusSeconds(1).isBefore(LocalDateTime.now()));
        Assertions.assertTrue(vendas.get(1).getDataHoraFim().plusSeconds(1).isAfter(LocalDateTime.now()));
        verify(repository, times(1)).findById(any());
        verify(produtoService, times(vendas.get(1).getProdutos().size())).decrementarQuantidadeNoEstoque(any(Produto.class));
        verify(repository, times(1)).save(any());
    }

    @Test
    void faturar_Falha_VendaJaFaturada() {
        Venda venda = vendas.get(0);
        when(repository.findById(any())).thenReturn(Optional.of(vendas.get(0)));
        doNothing().when(produtoService).decrementarQuantidadeNoEstoque(any(Produto.class));
        when(repository.save(any())).thenReturn(vendas.get(0));

        Boolean erroLancado = false;
        try {
            service.faturar(1L);
        } catch (IllegalArgumentException e) {
            erroLancado = true;
        }

        Assertions.assertTrue(erroLancado);
        verify(repository, times(1)).findById(any());
        verify(produtoService, never()).decrementarQuantidadeNoEstoque(any(Produto.class));
        verify(repository, never()).save(any());
    }
}