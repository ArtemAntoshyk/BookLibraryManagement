package org.library.manager.entity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Book {
    private int id;
    @NotEmpty(message = "Fill this field")
    private String title;
    @NotEmpty(message = "Fill this field")
    private String author;
    @NotEmpty(message = "Fill this field")
    private String description;
    @NotEmpty(message = "Fill this field")
    private String genre;
    @NotEmpty(message = "Fill this field")
    private String publisher;
    @NotEmpty(message = "Fill this field")
    private String language;
    @Max(2024)
    private int year;
}
