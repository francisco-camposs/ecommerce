package br.ufrn.imd.ecommerce.interfaces;

import br.ufrn.imd.ecommerce.abstracts.AbstractEntity;
import br.ufrn.imd.ecommerce.models.CostumerUser;
import br.ufrn.imd.ecommerce.services.AppUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        CostumerUser user = (CostumerUser) getAppUserService().loadUserByUsername(currentPrincipalName);
        entity.setCreatedBy(user);
        entity.setCreatedAt(LocalDateTime.now());
        entity = this.getRepository().save(entity);
        return entity;
    }

    @Transactional
    default E put(E entity){
        if (entity.getId() == null)
            throw new IllegalStateException("This entity doesn't exists");
        this.prePut(entity);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        CostumerUser user = (CostumerUser) getAppUserService().loadUserByUsername(currentPrincipalName);
        entity.setEditedBy(user);
        entity.setEditedAt(LocalDateTime.now());
        entity = this.getRepository().save(entity);
        return entity;
    }

    void prePost(E entity);

    void prePut(E entity);



    default E findById(Long id){
        return this.getRepository().findById(id).orElseThrow(() -> new IllegalStateException("This id not exists"));
    }

    default List<E> findAll(){
        return this.getRepository().findAll();
    }

    default List<E> findPagedContent(Long id){
        Pageable firstPageWithTenElements = PageRequest.of(Math.toIntExact(id), 5);
        Page<E> allContent = getRepository().findAll(firstPageWithTenElements);
        return allContent.getContent();
    }


    @Transactional
    default void delete(Long id){
        E entity = this.getRepository().findById(id).orElseThrow(() -> new IllegalStateException("This id not exists"));
        this.getRepository().delete(entity);
    }

}
