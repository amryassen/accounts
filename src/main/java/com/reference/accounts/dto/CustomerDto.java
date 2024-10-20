package com.reference.accounts.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CustomerDto {
    @NotEmpty(message = "Name can not be empty or null")
    @Size(min = 5, max = 30, message = "The length of the name should be between 5 and 30 character")
    private String name;

    @Email(message = "Email address should be valid value")
    @NotEmpty(message = "Email can not be empty or null")
    private String email;

    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    private AccountsDto accountsDto;
}
