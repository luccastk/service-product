package br.com.pulsar.products.infra.sale;

import br.com.pulsar.products.infra.payment.Payment;
import br.com.pulsar.products.infra.saledetail.SaleDetail;
import br.com.pulsar.products.infra.persistence.entities.StoreEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "T_SALES")
public class Sale {

    @Id
    @Column(name = "PK_SALE", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;

    @Column(name = "CD_SALE", unique = true, nullable = false)
    private UUID id = UUID.randomUUID();

    @Column(name = "CD_EMPLOYEE", nullable = false)
    private String employeeId;

    @Column(name = "TT_SALE", nullable = false)
    private BigDecimal total;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payment;

    @Column(nullable = false)
    private LocalDateTime timeStampSale = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "T_STORES_CD_STORE")
    private StoreEntity store;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SaleDetail> saleDetails;
}
