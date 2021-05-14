package com.acme.blogging.resource;

import com.acme.blogging.domain.model.AuditModel;

public class CommentResource extends AuditModel {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    private Long id;
    private String text;

}
