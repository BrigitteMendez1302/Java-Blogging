package com.acme.blogging.domain.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

//mapped super class no toma como herencia propiamente
//en la db no va a haber una tabla Audit Model
//lo que hace es que los atributos que declaran en audit model
//van a fusionarse con cada uno de sus hijos
//la tabla Post va a tener las columnas createdAt y updatedAt
@Entity
@Table(name = "posts")
public class Post extends AuditModel {

    @Id
    //se va a generar como id, el identity aprovecha eso
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String title;

    @NotNull
    private String description;

    @NotNull
    @Lob //large object, content podria ser un texto muy largo
    //crea una columna que seporte texto grande
    private String content;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    //como le afectan las operaciones en cascada
    @JoinTable(name="post_tags", joinColumns = {@JoinColumn(name="post_id")},
    inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    private List<Tag> tags;

    public Post( @NotNull String title, @NotNull String description, @NotNull String content) {
        this.title = title;
        this.description = description;
        this.content = content;
    }

    public Post() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public boolean isTaggedWith(Tag tag){
        return this.getTags().contains(tag);
    }

    public Post tagWith(Tag tag){
        if(!this.isTaggedWith(tag))
            this.getTags().add(tag);
        return this;
    }

    public Post unTagWith(Tag tag){
        if(this.isTaggedWith(tag))
            this.getTags().remove(tag);
        return this;
    }
}