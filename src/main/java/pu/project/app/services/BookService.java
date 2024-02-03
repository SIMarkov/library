package pu.project.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import pu.project.app.models.Book;
import pu.project.app.repo.BookRepo;

@Service
public class BookService extends BaseService<Book>{

    @Autowired
    private BookRepo bookRepo;
    @Override
    protected JpaRepository<Book, Long> getRepo() {
        return bookRepo;
    }
            public Page<Book> list(Pageable pageable) {
        return bookRepo.findAll(pageable);
    }
    public void delete(Long id) {
        bookRepo.deleteById(id);
    }
    public Page<Book> list(Pageable pageable, Specification<Book> filter) {
        return bookRepo.findAll(pageable);
    }
    public Page<Book> list(String title, String author, Pageable pageable) {
        return bookRepo.findByTitleAndAuthor(title.toLowerCase(), author.toLowerCase(), pageable);
    }
}