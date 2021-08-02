package br.ufrn.imd.ecommerce.interfaces;

import br.ufrn.imd.ecommerce.abstracts.AbstractVendorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface ControllerWriteProtectedInterface<
        E extends AbstractVendorEntity,
        R extends JpaRepository<E, Long>,
        S extends ServiceInterface<E, R>> extends
        ControllerSimpleInterface<E, R, S> {

    @Override
    @PostMapping
    @Secured(value = {"2", "VENDOR"})
    default E post(@RequestBody E entity){
        return this.getService().post(entity);
    }

    @Override
    @PutMapping
    @Secured(value = {"2", "VENDOR"})
    default E put(@RequestBody E entity){
        return this.getService().put(entity);
    }
}
