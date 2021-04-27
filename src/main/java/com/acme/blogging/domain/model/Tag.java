package com.acme.blogging.domain.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name="tags")
public class Tag extends AuditModel{
    @Id
    private Long id;
    @NotNull
    @Size(max=100)
    //el nombre va a ser como una llave alterna
    @NaturalId()
    private String name;

    //en el lado origen solo ponemos ese join
    //tags es el inverso
    //lo de lazy esta bien, y cascade tmb, si cambia algo en tag,
    //que cambie en posts
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE},
    mappedBy = "tags")
    private List<Post> posts;

    public Tag() {
    }

    public Tag(@NotNull @Size(max = 100) String name) {
        this.name = name;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Post> getPosts() {
        return posts;
    }
}