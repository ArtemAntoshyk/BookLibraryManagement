package org.library.manager.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Reader {
    private int id;
    @NotEmpty(message = "Name field should not be empty")
    @Size(min=2, max = 15, message = "Name should be between 2 and 15 characters")
    private String fullName;
    @NotEmpty(message = "Phone number field should not be empty")
    @Size(min=9, max = 15, message = "Ph. number should be between 9 and 15 characters")
    private String phoneNumber;
    @Email(message = "This field must contains email")
    private String email;
    @NotEmpty(message = "Address field should not be empty")
    private String address;
}
