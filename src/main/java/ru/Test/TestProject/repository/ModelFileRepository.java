package ru.Test.TestProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.Test.TestProject.model.ModelFile;

import java.util.List;

@Repository
public interface ModelFileRepository extends JpaRepository<ModelFile, Integer> {

    @Query("SELECT f.name FROM ModelFile f")
    List<String> findAllNames();

    @Query("SELECT f FROM ModelFile f WHERE (:name is null or LOCATE(:name, f.name) != 0)")
    List<ModelFile> findByParams(String name);
}
