package pu.project.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pu.project.app.models.Author;

@Repository
public interface AuthorRepo extends JpaRepository<Author, Long> {
}