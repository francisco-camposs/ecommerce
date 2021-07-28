package br.ufrn.imd.ecommerce.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = ConfirmationToken.SNAKE_NAME)
public class ConfirmationToken {

    public static final String SNAKE_NAME = "confirmation_token";
    public static final String CAMEL_NAME = "confirmationToken";

    public static final String GENERATOR_NAME = SNAKE_NAME + "_sequence";
    public static final String ID_COLUMN_NAME = SNAKE_NAME + "_id" ;
    public static final String API_ATTRIBUTE_NAME = CAMEL_NAME + "Id";

    @Id
    @SequenceGenerator(
            name = ConfirmationToken.GENERATOR_NAME,
            sequenceName = ConfirmationToken.GENERATOR_NAME,
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = ConfirmationToken.GENERATOR_NAME
    )
    @Column(name = ConfirmationToken.ID_COLUMN_NAME, nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(nullable = false, name = CostumerUser.ID_COLUMN_NAME)
    private CostumerUser costumerUser;

    public ConfirmationToken(String token,
                             LocalDateTime createdAt,
                             LocalDateTime expiresAt,
                             CostumerUser costumerUser) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.costumerUser = costumerUser;
    }
}
