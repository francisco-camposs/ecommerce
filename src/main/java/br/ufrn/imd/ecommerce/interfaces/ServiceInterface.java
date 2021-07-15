package br.ufrn.imd.ecommerce.interfaces;

import br.ufrn.imd.ecommerce.abstracts.AbstractEntity;
import br.ufrn.imd.ecommerce.models.AppUser;
import br.ufrn.imd.ecommerce.services.AppUserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface ServiceInterface <
        E extends AbstractEntity,
        R extends JpaRepository<E, Long> > {

    AppUserService getAppUserService();

    R getRepository();


    @Transactional
    default E post(E entity){
        this.prePost(entity);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        AppUser user = (AppUser) getAppUserService().loadUserByUsername(currentPrincipalName);
        entity.setCreatedBy(user);
        entity.setCreatedAt(LocalDateTime.now());
        entity = this.getRepository().save(entity);
        this.posPost(entity);
        return entity;
    }

    @Transactional
    default E put(E entity){
        if (entity.getId() == null)
            throw new IllegalStateException("This entity doesn't exists");
        this.prePut(entity);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        AppUser user = (AppUser) getAppUserService().loadUserByUsername(currentPrincipalName);
        entity.setEditedBy(user);
        entity.setEditedAt(LocalDateTime.now());
        entity = this.getRepository().save(entity);
        this.posPut(entity);
        return entity;
    }

    void posPost(E entity);
    void prePost(E entity);

    void posPut(E entity);
    void prePut(E entity);


    default E findById(Long id){
        return this.getRepository().findById(id).orElseThrow(() -> new IllegalStateException("This id not exists"));
    }

    default List<E> findAll(){
        return this.getRepository().findAll();
    }


    @Transactional
    default void delete(Long id){
        E entity = this.getRepository().findById(id).orElseThrow(() -> new IllegalStateException("This id not exists"));
        this.getRepository().delete(entity);
    }

}
