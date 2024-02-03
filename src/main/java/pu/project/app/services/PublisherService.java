package pu.project.app.services;

import java.util.stream.Stream;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import pu.project.app.models.Publisher;
import pu.project.app.repo.PublisherRepo;

@AllArgsConstructor
@Service
public class PublisherService extends BaseService<Publisher> {

    PublisherRepo publisherRepo;

    @Override
    protected JpaRepository<Publisher, Long> getRepo() {
        return publisherRepo;
    }

    public Stream<Publisher> list(PageRequest of) {
        return publisherRepo.findAll(of).stream();
    }
}