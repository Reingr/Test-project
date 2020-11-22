package ru.ITRev.TestProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ITRev.TestProject.model.IdList;
import ru.ITRev.TestProject.model.ModelFile;

import java.util.List;

@Repository
public interface ModelFileRepository extends JpaRepository<ModelFile, Integer> {

    @Query("SELECT f FROM ModelFile f WHERE (:ids is null or f.id in :ids)")
    List<ModelFile> findAllById(@Param("ids") IdList ids);
}
