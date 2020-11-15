package ru.ITRev.TestProject.model;

import lombok.Data;

@Data
public class ResultModel {
    private int errorCode;
    private Object object;

    public ResultModel(Object object) {
        this.errorCode = 200;
        this.object = object;
    }
}
