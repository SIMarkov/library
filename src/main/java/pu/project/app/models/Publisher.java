package pu.project.app.models;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
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
@Table(name = "Book_Publisher")
public class Publisher extends BaseModel {
    @Column(name = "NAME", nullable = false)
    @NotNull
    private String name;
    @Column(name = "CITY", nullable = false)
    @NotNull
    private String city;
    @Transient
    private String temp;

    @OneToMany(mappedBy = "publisher", fetch = FetchType.EAGER)
    private Set<Book> books;

    public boolean addBook(final Book book) {
        if(books == null) {
            books = new HashSet<>();
        }
        return books.add(book);
    }
}