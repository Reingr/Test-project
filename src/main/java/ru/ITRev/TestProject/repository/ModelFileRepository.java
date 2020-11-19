package ru.ITRev.TestProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ITRev.TestProject.model.ModelFile;

public interface ModelFileRepository extends JpaRepository<ModelFile, Integer> {
}
