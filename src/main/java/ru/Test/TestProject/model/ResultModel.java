package ru.Test.TestProject.model;

import lombok.Data;

/**
 * Модель для возвращения результата
 * также можно использовать WebMessage (web-common)
 */

@Data
public class ResultModel {
    private int code;
    private Object object;

    public ResultModel(Object object) {
        this.code = 200;
        this.object = object;
    }
}
