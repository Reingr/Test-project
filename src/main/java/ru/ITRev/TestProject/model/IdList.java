package ru.ITRev.TestProject.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.util.List;

@Data
public class IdList {
    @JsonDeserialize
    private List<Long> id;
}
