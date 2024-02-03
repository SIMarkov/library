package pu.project.app.services;

import java.util.stream.Stream;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import pu.project.app.models.Author;
import pu.project.app.repo.AuthorRepo;

@AllArgsConstructor
@Service
public class AuthorService extends BaseService<Author> {

    AuthorRepo authorRepo;

    @Override
    protected JpaRepository<Author, Long> getRepo() {
        return authorRepo;
    }

    public Stream<Author> list(PageRequest of) {
        return authorRepo.findAll(of).stream();
    }
}