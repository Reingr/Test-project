package ru.Test.TestProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.Test.TestProject.model.FormatFileEntity;

@Repository
public interface FormatFileRepository extends JpaRepository<FormatFileEntity, Integer> {
}
