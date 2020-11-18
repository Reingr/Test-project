package ru.ITRev.TestProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ITRev.TestProject.model.FormatFileEntity;

@Repository
public interface FormatFileRepository extends JpaRepository<FormatFileEntity, Long> {
}
