package br.ufrn.imd.ecommerce.interfaces;

import br.ufrn.imd.ecommerce.abstracts.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ControllerInterface <
        E extends AbstractEntity,
        R extends JpaRepository<E, Long>,
        S extends ServiceInterface<E, R>>{

    S getService();

    @GetMapping
    default E get(@RequestParam Long id){
        return this.getService().findById(id);
    }

    @GetMapping
    default List<E> get(){
        return this.getService().findAll();
    }

    @PostMapping
    default E post(@RequestBody E entity){
       return this.getService().post(entity);
    }

    @PutMapping
    default E put(@RequestBody E entity){
        return this.getService().put(entity);
    }

    @DeleteMapping
    default void delete(@RequestParam Long id){
        this.getService().delete(id);
    }

}
