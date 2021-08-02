package br.ufrn.imd.ecommerce.abstracts;

import br.ufrn.imd.ecommerce.models.CostumerUser;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractCostumerEntity extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "created_by", updatable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected CostumerUser createdByCostumer;

    @ManyToOne
    @JoinColumn(name = "edited_by")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected CostumerUser editedByCostumer;

    @PrePersist
    protected void prePersist(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CostumerUser user = (CostumerUser) authentication.getPrincipal();
        this.setCreatedByCostumer(user);
        this.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    protected void preUpdate(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CostumerUser user = (CostumerUser) authentication.getPrincipal();
        this.setEditedByCostumer(user);
        this.setEditedAt(LocalDateTime.now());
    }

}
