package br.com.pulsar.products.infra.persistence.entities;

import br.com.pulsar.products.domain.valueobjects.ProductId;
import br.com.pulsar.products.domain.valueobjects.StoreId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "T_STORES")
public class StoreEntity {

    @Id
    @Column(name = "PK_STORE", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;

    @Column(name = "CD_STORE", unique = true, nullable = false)
    private StoreId id;

    @Column(name = "NM_STORE", nullable = false)
    private String name;

    @Column(name = "ST_ACTIVE", nullable = false)
    private Boolean active = true;

    @ElementCollection
    @CollectionTable(name = "PRODUCT_IDS", joinColumns = @JoinColumn(name = "entity_id"))
    @Column(name = "ID_PRODUCTS")
    private List<ProductId> products;
}
