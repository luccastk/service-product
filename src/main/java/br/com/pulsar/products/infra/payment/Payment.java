package br.com.pulsar.products.infra.payment;

import br.com.pulsar.products.infra.sale.Sale;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "T_PAYMENTS")
public class Payment {

    @Id
    @Column(name = "PK_PRODUCT", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;

    @Column(name = "CD_PAYMENT", unique = true, nullable = false)
    private UUID id = UUID.randomUUID();

    @Column(name = "VL_PAYMENT", nullable = false)
    private BigDecimal amount;

    @Column(name = "TS_PAYMENT", nullable = false)
    private LocalDateTime datePayment = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "DS_PAYMENT_STATUS", nullable = false)
    private PaymentStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "DS_PAYMENT_METHOD", nullable = false)
    private PaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(name = "T_SALES_CD_SALE", nullable = false)
    private Sale sale;
}
