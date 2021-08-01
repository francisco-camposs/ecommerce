package br.ufrn.imd.ecommerce.models;

import br.ufrn.imd.ecommerce.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@Builder
@EqualsAndHashCode()
@Entity
@Table(name = VendorUser.SNAKE_NAME)
@AllArgsConstructor
@NoArgsConstructor
public class VendorUser implements UserDetails {

    public static final String SNAKE_NAME = "vendor_user";
    public static final String CAMEL_NAME = "vendorUser";

    public static final String GENERATOR_NAME = SNAKE_NAME + "_sequence";
    public static final String ID_COLUMN_NAME = SNAKE_NAME + "_id";
    public static final String API_ATTRIBUTE_NAME = CAMEL_NAME + "Id";

    @Id
    @SequenceGenerator(
            name = VendorUser.GENERATOR_NAME,
            sequenceName = VendorUser.GENERATOR_NAME,
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = VendorUser.GENERATOR_NAME
    )
    @Column(name = VendorUser.ID_COLUMN_NAME, nullable = false, updatable = false)
    private Long id;

    @Column(length = 256)
    private String name;

    @Column(nullable = false, unique = true, updatable = false)
    private String email;

    @Column(nullable = false, length = 128, unique = true, updatable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Builder.Default
    private Boolean locked = false;

    @Builder.Default
    private Boolean enabled = true;

    @Column(nullable = false)
    private String imgLink;

    @Column(nullable = false, length = 14)
    private String cnpj;

    @Column
    private Long postalCode;

    @Column(length = 512)
    private String street;

    @Column
    private Long number;

    @Column(length = 512)
    private String complement;

    @Builder.Default
    private Boolean expired = false;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRole.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !expired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

}
