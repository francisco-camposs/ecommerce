package br.ufrn.imd.ecommerce.models;

import br.ufrn.imd.ecommerce.abstracts.AbstractEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = ComentsProduct.SNAKE_NAME)
@EqualsAndHashCode(callSuper = false)
public class ComentsProduct extends AbstractEntity {

    public static final String SNAKE_NAME = "coments_product";
    public static final String CAMEL_NAME = "comentsProduct";

    public static final String GENERATOR_NAME = SNAKE_NAME + "_sequence";
    public static final String ID_COLUMN_NAME = SNAKE_NAME + "_id" ;
    public static final String API_ATTRIBUTE_NAME = CAMEL_NAME + "Id";

    @Id
    @SequenceGenerator(
            name = ComentsProduct.GENERATOR_NAME,
            sequenceName = ComentsProduct.GENERATOR_NAME,
            allocationSize = 1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = ComentsProduct.GENERATOR_NAME
    )

    @Column(name = ComentsProduct.ID_COLUMN_NAME, nullable = false, updatable = false)
    @Getter
    private Long id;

    @Column(nullable = false)
    private int starRate;

    @Column(nullable = false)
    private String coment;

    @ManyToOne
    @JoinColumn(nullable = false, name = "product_ID")
    private Product product;

    @Override
    public void setId() {

    }
}
