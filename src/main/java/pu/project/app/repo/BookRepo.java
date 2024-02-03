package pu.project.app.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pu.project.app.dto.BookDTO;
import pu.project.app.models.Book;

public interface BookRepo extends JpaRepository<Book, Long>, JpaSpecificationExecutor<BookDTO> {

    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE %:title% AND LOWER(b.author.name) LIKE %:authorName%")
    Page<Book> findByTitleAndAuthor(@Param("title") String title, @Param("authorName") String authorName, Pageable pageable);
}