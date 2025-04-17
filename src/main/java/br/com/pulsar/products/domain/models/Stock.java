package br.com.pulsar.products.domain.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="T_STOCKS", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"T_STORES_PK_STORE", "T_PRODUCTS_PK_PRODUCT"})
})
public class Stock {

    @Id
    @Column(name = "PK_STOCK", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;

    @Column(name = "QT_STOCK", nullable = false)
    private Integer quantity = 0;

    @Column(name = "VL_BATCH", nullable = false)
    private BigDecimal price;

    @OneToOne
    @JoinColumn(name = "T_PRODUCTS_PK_PRODUCT", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "T_STORES_PK_STORE", nullable = false)
    private Store store;

    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Batch> batches;
}
