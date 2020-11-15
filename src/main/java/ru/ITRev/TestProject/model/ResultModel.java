package ru.ITRev.TestProject.model;

import lombok.Data;

@Data
public class ResultModel {
    private int code;
    private Object object;

    public ResultModel(Object object) {
        this.code = 200;
        this.object = object;
    }
}
