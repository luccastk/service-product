package br.com.pulsar.products.infra.persistence.entities;

import br.com.pulsar.products.domain.valueobjects.BatchId;
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
public class BatchEntity {

    @Id
    @Column(name = "PK_BATCH", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;

    @Column(name = "CD_BATCH", unique = true, nullable = false)
    private BatchId id;

    @Column(name = "QT_BATCH", nullable = false)
    private Integer quantity;

    @Column(name = "DT_BATCH")
    private LocalDate validity;

    @Column(name = "CT_BATCH", nullable = false)
    private LocalDateTime createTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_PRODUCT", nullable = false)
    private ProductEntity product;
}
