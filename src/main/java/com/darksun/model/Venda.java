package com.darksun.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Venda implements Serializable {
    @Serial
    private static final long serialVersionUID = -6115333943978480121L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "venda_produto", joinColumns = {
            @JoinColumn(name = "venda_id", nullable = false, updatable = false)}, inverseJoinColumns = {
            @JoinColumn(name = "codigo_de_barras", nullable = false, updatable = false)})
    private List<Produto> produtos;
}
