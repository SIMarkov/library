package pu.project.app.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Book_Author")
public class Author extends BaseModel {
    @Column(length = 256, name = "NAME", nullable = false)
    @NotNull
    private String name;
    @Column(name = "BIRTHYEAR", nullable = false)
    @NotNull
    private Integer birthYear;
    @Transient
    private String temp;

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    private Set<Book> books;

    public boolean addBook(final Book book) {
        if(books ==null){
            books = new HashSet<>();
        }
        return books.add(book);
    }
}