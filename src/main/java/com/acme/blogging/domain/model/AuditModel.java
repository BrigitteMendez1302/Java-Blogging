package com.acme.blogging.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

//Va a ser padre
//esta anotacion me dice que sus atributos
//pasan a formar parte de otras clases
@MappedSuperclass
//Decimos que esta clase va a tener acceso a las vistas de auditoria
// vamos a decirle qué vista va con qué atributo
// java framework las puede exportar a .json
// para lectura si normal en json pero que no pida como input asi tmb
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value={"createdAt", "updatedAt"}, allowGetters = true)
public abstract class AuditModel implements Serializable {
    @Temporal(TemporalType.TIMESTAMP)
    @Column (name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column (name = "updated_at", nullable = false, updatable = false)
    @LastModifiedDate
    private Date updatedAt;

    public Date getCreatedAt() {
        return createdAt;
    }

    public AuditModel setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
