package com.accolite.alertMessenger.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @NotBlank(message = "User ID Can not be empty or null")
    @NotNull(message = "User ID Can not be empty or null")
    private String userId;

    @NotNull(message = "User Password Can not be empty or null")
    @NotBlank(message = "User ID Can not be empty or null")
    private String password;

    @NotNull( message = "Enter valid role as ADMIN or USER only ")
    @NotBlank( message = "Enter valid role as ADMIN or USER only ")
    @Pattern(regexp = "^ADMIN|USER$" , message = "Enter valid role as ADMIN or USER only ")
    private String role;

}




