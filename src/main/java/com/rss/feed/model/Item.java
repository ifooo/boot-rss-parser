package com.rss.feed.model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "item")
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"title", "link"})}, name = "item")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "title")
    @NotNull
    private String title;

    @Column(name = "link")
    @NotNull
    private String link;

    @Column(name = "publication_date")
    @NotNull
    private Date publicationDate;
}
