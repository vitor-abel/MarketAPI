package com.darksun.service;

import com.darksun.model.Produto;
import com.darksun.repository.ProdutoRepository;
import com.darksun.util.TesteUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService service;

    @Mock
    private ProdutoRepository repository;

    List<Produto> produtos;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        TesteUtils testeUtils = new TesteUtils();
        produtos = testeUtils.getProdutos();
    }

    @Test
    void criar_Sucesso() {
        Produto produto = new Produto(123L,"batata",true,4.00,2.50,20);
        when(repository.findById(any())).thenReturn(Optional.empty());
        when(repository.save(produto)).thenReturn(produtos.get(0));
        Produto response = service.criar(produto);
        Assertions.assertEquals(123L, response.getCodigoDeBarras());
        verify(repository, times(1)).save(any());
    }

    @Test
    void criar_FalhaCodigoJaCadastrado(){
        Produto produto = new Produto(123L,"batata",true,4.00,2.50,20);
        when(repository.findById(any())).thenReturn(Optional.of(produto));
        Boolean errorThrown = false;
        try{
            service.criar(produto);
        } catch(IllegalArgumentException ex){
            errorThrown = true;
        }
        Assertions.assertTrue(errorThrown);
        verify(repository, times(1)).findById(any());
    }

    @Test
    void criar_FalhaPrecoCompraNegativo(){
        Produto produto = new Produto(123L,"batata",true,-1.00,2.50,20);
        when(repository.findById(any())).thenReturn(Optional.empty());
        Boolean errorThrown = false;
        try{
            service.criar(produto);
        } catch(IllegalArgumentException ex){
            errorThrown = true;
        }
        Assertions.assertTrue(errorThrown);
        verify(repository, times(1)).findById(any());
    }

    @Test
    void criar_FalhaPrecoCompraZero(){
        Produto produto = new Produto(123L,"batata",true,0.00,2.50,20);
        when(repository.findById(any())).thenReturn(Optional.empty());
        Boolean errorThrown = false;
        try{
            service.criar(produto);
        } catch(IllegalArgumentException ex){
            errorThrown = true;
        }
        Assertions.assertTrue(errorThrown);
        verify(repository, times(1)).findById(any());
    }

    @Test
    void criar_FalhaPrecoCompraNulo(){
        Produto produto = new Produto(123L,"batata",true,null,2.50,20);
        when(repository.findById(any())).thenReturn(Optional.empty());
        Boolean errorThrown = false;
        try{
            service.criar(produto);
        } catch(IllegalArgumentException ex){
            errorThrown = true;
        }
        Assertions.assertTrue(errorThrown);
        verify(repository, times(1)).findById(any());
    }

    @Test
    void criar_FalhaPrecoVendaNegativo(){
        Produto produto = new Produto(123L,"batata",true,4.00,-2.50,20);
        when(repository.findById(any())).thenReturn(Optional.empty());
        Boolean errorThrown = false;
        try{
            service.criar(produto);
        } catch(IllegalArgumentException ex){
            errorThrown = true;
        }
        Assertions.assertTrue(errorThrown);
        verify(repository, times(1)).findById(any());
    }

    @Test
    void criar_FalhaPrecoVendaZero(){
        Produto produto = new Produto(123L,"batata",true,4.00,0.00,20);
        when(repository.findById(any())).thenReturn(Optional.empty());
        Boolean errorThrown = false;
        try{
            service.criar(produto);
        } catch(IllegalArgumentException ex){
            errorThrown = true;
        }
        Assertions.assertTrue(errorThrown);
        verify(repository, times(1)).findById(any());
    }

    @Test
    void criar_FalhaPrecoVendaNulo(){
        Produto produto = new Produto(123L,"batata",true,4.00,null,20);
        when(repository.findById(any())).thenReturn(Optional.empty());
        Boolean errorThrown = false;
        try{
            service.criar(produto);
        } catch(IllegalArgumentException ex){
            errorThrown = true;
        }
        Assertions.assertTrue(errorThrown);
        verify(repository, times(1)).findById(any());
    }

    @Test
    void criar_FalhaEstoqueNegativo(){
        Produto produto = new Produto(123L,"batata",true,4.00,2.50,-20);
        when(repository.findById(any())).thenReturn(Optional.empty());
        Boolean errorThrown = false;
        try{
            service.criar(produto);
        } catch(IllegalArgumentException ex){
            errorThrown = true;
        }
        Assertions.assertTrue(errorThrown);
        verify(repository, times(1)).findById(any());
    }

    @Test
    void criar_SucessoEstoqueZero(){
        Produto produto = new Produto(123L,"batata",true,4.00,2.50,0);
        when(repository.findById(any())).thenReturn(Optional.empty());
        Boolean errorThrown = false;
        try{
            service.criar(produto);
        } catch(IllegalArgumentException ex){
            errorThrown = true;
        }
        Assertions.assertFalse(errorThrown);
        verify(repository, times(1)).findById(any());
    }

    @Test
    void criar_FalhaEstoqueNulo(){
        Produto produto = new Produto(123L,"batata",true,4.00,2.50,-20);
        when(repository.findById(any())).thenReturn(Optional.empty());
        Boolean errorThrown = false;
        try{
            service.criar(produto);
        } catch(IllegalArgumentException ex){
            errorThrown = true;
        }
        Assertions.assertTrue(errorThrown);
        verify(repository, times(1)).findById(any());
    }

    @Test
    void buscar() {
        when(repository.findAll()).thenReturn(produtos);
        List<Produto> listaProdutos = service.buscar();
        Assertions.assertEquals(listaProdutos, produtos);
        verify(repository, times(1)).findAll();
    }

    @Test
    void buscarPorCodigoDeBarras() {
        Produto produto = produtos.get(0);
        when(repository.findById(any())).thenReturn(Optional.of(produto));
        Produto response = service.buscarPorCodigoDeBarras(123L);
        Assertions.assertEquals(produto, response);
        verify(repository, times(1)).findById(any());
    }

    @Test
    void atualizar() {
        Produto produto = produtos.get(0);
        Integer estoque = 100;
        produto.setQtdEstoque(estoque);
        when(repository.save(produto)).thenReturn(produto);
        when(repository.findById(any())).thenReturn(Optional.of(produto));
        Produto response = service.atualizar(produto);
        Assertions.assertEquals(response.getQtdEstoque(), estoque);
        verify(repository, times(1)).save(any());
    }

    @Test
    void deletar_Sucesso() {
        doNothing().when(repository).deleteById(any());
        service.deletar(123L);
        verify(repository, times(1)).deleteById(any());
    }

    @Test
    void deletar_Falha() {
        doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(any());
        Boolean errorThrown = false;
        try {
            service.deletar(3L);
        } catch (EmptyResultDataAccessException ex) {
            errorThrown = true;
        }
        Assertions.assertTrue(errorThrown);
        verify(repository, times(1)).deleteById(any());
    }
}