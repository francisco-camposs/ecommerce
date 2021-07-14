package br.ufrn.imd.ecommerce.models;

import br.ufrn.imd.ecommerce.enums.UserRole;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Builder
@Entity
@Table(name = AppUser.SNAKE_NAME)
@AllArgsConstructor
@NoArgsConstructor
public class AppUser implements UserDetails {

    public static final String SNAKE_NAME = "app_user";
    public static final String CAMEL_NAME = "appUser";

    public static final String GENERATOR_NAME = SNAKE_NAME + "_sequence";
    public static final String ID_COLUMN_NAME = SNAKE_NAME + "_id";
    public static final String API_ATTRIBUTE_NAME = CAMEL_NAME + "Id";

    @Id
    @SequenceGenerator(
        name = AppUser.GENERATOR_NAME,
        sequenceName = AppUser.GENERATOR_NAME,
        allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = AppUser.GENERATOR_NAME
    )
    @Column(name = AppUser.ID_COLUMN_NAME, nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true, updatable = false)
    private String email;

    @Column(nullable = false, length = 128, unique = true, updatable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole appUserRole;

    private Boolean locked = false;

    private Boolean enabled = false;

    public AppUser(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.appUserRole = UserRole.COSTUMER;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(appUserRole.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
