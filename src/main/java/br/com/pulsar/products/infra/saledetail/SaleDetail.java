package br.com.pulsar.products.infra.saledetail;

import br.com.pulsar.products.infra.persistence.entities.ProductEntity;
import br.com.pulsar.products.infra.sale.Sale;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "T_SALEDETAILS")
public class SaleDetail {

    @Id
    @Column(name = "PK_PRODUCT", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;

    @Column(name = "CD_SALEDETAIL", unique = true, nullable = false)
    private UUID id = UUID.randomUUID();

    @Column(name = "QT_SALEDETAIL", nullable = false)
    private Integer quantity;

    @Column(name = "UP_SALEDETAIL", nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "TT_SALEDETAIL", nullable = false)
    private BigDecimal total;

    @ManyToOne
    @JoinColumn(name = "T_SALES_CD_SALE", nullable = false)
    private Sale sale;

    @ManyToOne
    @JoinColumn(name = "T_PRODUCTS_CD_PRODUCT", nullable = false)
    private ProductEntity product;
}
