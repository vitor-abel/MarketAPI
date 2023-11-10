package com.darksun.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Produto implements Serializable {
    @Serial
    private static final long serialVersionUID = -5230914050939563482L;

    @Id
    private Long codigoDeBarras;
    private String nome;
    private Boolean produtoAtivo;
    private Double precoCompra;
    private Double precoVenda;
    private Integer qtdEstoque;
}
