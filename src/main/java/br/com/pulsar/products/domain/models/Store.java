package br.com.pulsar.products.domain.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "T_STORES")
public class Store {

    @Id
    @Column(name = "PK_STORE", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;

    @Column(name = "CD_STORE", unique = true, nullable = false)
    private UUID id = UUID.randomUUID();

    @Column(name = "NM_STORE", nullable = false)
    private String name;

    @Column(name = "ST_STORE", nullable = false)
    private Boolean active = true;

    @OneToMany(mappedBy = "store", orphanRemoval = true)
    private List<Product> products = new ArrayList<>();
}
