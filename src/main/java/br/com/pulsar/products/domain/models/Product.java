package br.com.pulsar.products.domain.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "T_PRODUCTS")
public class Product {

    @Id
    @Column(name = "PK_PRODUCT", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;

    @Column(name = "CD_PRODUCT", unique = true, nullable = false)
    private UUID id = UUID.randomUUID();

    @Column(name = "NM_PRODUCT", nullable = false)
    private String name;

    @Column(name = "ST_ACTIVE", nullable = false)
    private Boolean active = true;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Stock stock;

    @ManyToOne
    @JoinColumn(name = "T_STORES_PK_STORE", nullable = false)
    private Store store;
}
