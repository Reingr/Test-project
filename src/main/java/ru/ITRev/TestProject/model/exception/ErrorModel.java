package ru.ITRev.TestProject.model.exception;

import lombok.Data;

@Data
public class ErrorModel {
    private int errorCode;
    private String message;
}
