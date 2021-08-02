package br.ufrn.imd.ecommerce.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationClientDTO {

    private final String firstName;

    private final String lastName;

    private final String email;

    private final String password;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate birthdate;

}
