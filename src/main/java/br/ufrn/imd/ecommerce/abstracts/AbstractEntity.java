package br.ufrn.imd.ecommerce.abstracts;

import br.ufrn.imd.ecommerce.models.AppUser;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EqualsAndHashCode(callSuper = false)
public abstract class AbstractEntity {

    @Column(nullable = false)
    protected Boolean active = true;

    @Column(name = "created_at",updatable = false, nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss x")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected LocalDateTime createdAt;

    @Column(name = "edited_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss x")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected LocalDateTime editedAt;

    @ManyToOne
    @JoinColumn(name = "created_by")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected AppUser createdBy;

    @ManyToOne
    @JoinColumn(name = "edited_by")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected AppUser editedBy;


    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
        this.createdBy = AppUser.builder().id(0L).build();
    }

    @PreUpdate
    public void preUpdate(){
        this.editedAt = LocalDateTime.now();
    }

    public abstract Long getId();

    public abstract Long setId();
}
