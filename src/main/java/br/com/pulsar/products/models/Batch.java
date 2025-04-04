package br.com.pulsar.products.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "T_BATCHES")
public class Batch {

    @Id
    @Column(name = "PK_BATCH", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;

    @Column(name = "CD_BATCH", unique = true, nullable = false)
    private UUID id = UUID.randomUUID();

    @Column(name = "QT_BATCH", nullable = false)
    private Integer quantity;

    @Column(name = "DT_BATCH", nullable = false)
    private LocalDate validity;

    @Column(name = "CT_BATCH", nullable = false)
    private LocalDateTime createTime = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "T_STOCKS_PK_STOCK", nullable = false)
    private Stock stock;
}
