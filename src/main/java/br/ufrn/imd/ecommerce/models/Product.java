package br.ufrn.imd.ecommerce.models;

import br.ufrn.imd.ecommerce.abstracts.AbstractEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = Product.SNAKE_NAME)
@EqualsAndHashCode(callSuper = false)
public class Product extends AbstractEntity {
    public static final String SNAKE_NAME = "product";
    public static final String CAMEL_NAME = "product";

    public static final String GENERATOR_NAME = SNAKE_NAME + "_sequence";
    public static final String ID_COLUMN_NAME = SNAKE_NAME + "_id";
    public static final String API_ATTRIBUTE_NAME = CAMEL_NAME + "Id";

    @Id
    @SequenceGenerator(
            name = Product.GENERATOR_NAME,
            sequenceName = Product.GENERATOR_NAME,
            allocationSize = 1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = Product.GENERATOR_NAME
    )
    @Column(name = Product.ID_COLUMN_NAME, nullable = false, updatable = false)
    private Long id;

    @Override
    public void setId() {
    }
    private String name;
//    private String imgLink;
//    private String description;
//    private double price;

}
