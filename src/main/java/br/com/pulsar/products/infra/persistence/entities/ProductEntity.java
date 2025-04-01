package br.com.pulsar.products.infra.persistence.entities;


import br.com.pulsar.products.domain.valueobjects.ProductId;
import br.com.pulsar.products.domain.valueobjects.StoreId;
import br.com.pulsar.products.infra.saledetail.SaleDetail;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "T_PRODUCTS")
public class ProductEntity {

    @Id
    @Column(name = "PK_PRODUCT", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;

    @Column(name = "CD_PRODUCT", unique = true, nullable = false)
    private ProductId id;

    @Column(name = "NM_PRODUCT", nullable = false)
    private String name;

    @Column(name = "VL_DEFAULT_PRICE", nullable = false)
    private BigDecimal defaultPrice;

    @Column(name = "ST_ACTIVE")
    private Boolean active = true;

    @Column(name = "CD_STORE", nullable = false)
    private StoreId store;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BatchEntity> batches = new ArrayList<>();
}
