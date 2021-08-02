package br.ufrn.imd.ecommerce.services;

import br.ufrn.imd.ecommerce.enums.UserRole;
import br.ufrn.imd.ecommerce.exception.InvalidInputException;
import br.ufrn.imd.ecommerce.exception.UnauthorizedAccessException;
import br.ufrn.imd.ecommerce.interfaces.ServiceInterface;
import br.ufrn.imd.ecommerce.models.CostumerUser;
import br.ufrn.imd.ecommerce.models.Product;
import br.ufrn.imd.ecommerce.repositories.ProductRepository;
import br.ufrn.imd.ecommerce.validators.ProductValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService implements ServiceInterface<Product, ProductRepository> {

    @Getter
    private final ProductRepository repository;

    @Getter
    private final AppUserService appUserService;

    @Override
    public void prePost(Product entity) { validateProduct(entity); }

    @Override
    public void prePut(Product entity) { validateProduct(entity); }

    private void validateProduct(Product entity) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if ( !(authentication.getAuthorities().contains(new SimpleGrantedAuthority(UserRole.VENDOR.name())) || authentication.getAuthorities().contains(new SimpleGrantedAuthority(UserRole.ADMIN.name())) ))
            throw new UnauthorizedAccessException("Unauthorized");

        String errors = "";

        if (!ProductValidator.isValidName(entity.getName()))
            errors = errors.concat(" Invalid name, Character limit reached or product name shouldn't just numbers  ");

        if (!ProductValidator.isValidImgLink(entity.getImgLink()))
            errors = errors.concat(" Invalid Link, Character limit reached ");

        if (!ProductValidator.isValidDescription(entity.getDescription()))
            errors = errors.concat(" Invalid description, Character limit reached or description shouldn't just numbers ");

        if (!ProductValidator.isValidPrice(entity.getPrice()))
            errors = errors.concat(" Invalid price, Number of price is zero or negative ");

        if ( !errors.isBlank() )
            throw new InvalidInputException(errors);
    }
}
