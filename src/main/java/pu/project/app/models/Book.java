package pu.project.app.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Books")
public class Book extends BaseModel{

    @Column(length = 256, name = "TITLE", nullable = false)
    @NotNull
    private String title;
    @Column(nullable = false)
    private String isbn;
    @Column(nullable = false)
    private Integer publishingYear;
    @Transient
    private String temp;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "AUTHOR_ID")
    @NotNull(message = "Изберете автор")
    private Author author;

    @ManyToOne(optional = false)
    @JoinColumn(name = "PUBLISHER_ID")
    @NotNull(message = "Изберете издател")
    private Publisher publisher;
}