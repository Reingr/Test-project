package ru.Test.TestProject.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.util.List;

/**
 * Список id для загрузки нескольких файлов из бд в архив
 */
@Data
public class IdList {
    @JsonDeserialize
    private List<Integer> id;
}
