package com.vkuzmenko.pizza.information.domain;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "blog")
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long postId;

    @NotBlank
    private String title;

    @Column(length = 10000)
    @NotBlank
    private String description;

    @Column(length = 500)
    @NotBlank
    private String shortDescription;
    private String image;

    @NotBlank
    private String createdAt;

    @OneToMany(
            mappedBy = "blog",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Fetch(FetchMode.SUBSELECT)
    private List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setBlog(this);
    }

    @Transient
    private int commentCount;

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Blog blog = (Blog) o;
        return postId == blog.postId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId);
    }
}
