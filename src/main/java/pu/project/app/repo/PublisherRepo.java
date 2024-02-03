package pu.project.app.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pu.project.app.models.Publisher;

@Repository
public interface PublisherRepo extends JpaRepository<Publisher, Long> {
}