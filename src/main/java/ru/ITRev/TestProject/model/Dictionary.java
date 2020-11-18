package ru.ITRev.TestProject.model;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public class Dictionary {
    @Id
    private Long id;
    private String value;
}
