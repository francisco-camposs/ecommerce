package br.ufrn.imd.ecommerce.abstracts;

import br.ufrn.imd.ecommerce.models.VendorUser;
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
public abstract class AbstractVendorEntity extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "created_by", updatable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected VendorUser createdByVendor;

    @ManyToOne
    @JoinColumn(name = "edited_by")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected VendorUser editedByVendor;

    @PrePersist
    public void prePersist(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        VendorUser user = (VendorUser) authentication.getPrincipal();
        this.setCreatedByVendor(user);
        this.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void preUpdate(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        VendorUser user = (VendorUser) authentication.getPrincipal();
        this.setEditedByVendor(user);
        this.setEditedAt(LocalDateTime.now());
    }

}
