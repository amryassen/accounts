package com.reference.accounts.controller;

import com.reference.accounts.constants.AccountsConstants;
import com.reference.accounts.dto.CustomerDto;
import com.reference.accounts.dto.ResponseDto;
import com.reference.accounts.services.IAccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "CRUD Rest APIs for Accounts microservice",
        description = "CRUD Rest APIs for Accounts microservice (CREATE,UPDATE,FETCH AND DELETE) developed my Amr Yassen"
)
@RestController
@RequestMapping(path = "/api")
@RequiredArgsConstructor
@Validated
@Transactional
public class AccountsController {

    private final IAccountsService iAccountsService;

    @Operation(
            summary = "Create Account REST API",
            description = "REST API to create new Customer & Account"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP status CREATED"
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
        iAccountsService.createAccount(customerDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(HttpStatus.CREATED, AccountsConstants.CREATED.getValue()));
    }

    @Operation(
            summary = "Fetch Account Details REST API",
            description = "REST API to fetch Customer & Account details based on mobile number"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP status OK"
    )
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam
                                                           @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                                                           String mobileNumber) {
        CustomerDto customerDto = iAccountsService.getAccountDetails(mobileNumber);

        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }


    @Operation(
            summary = "Update Account Details REST API",
            description = "REST API to Update Customer & Account base on mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP status INTERNAL_SERVER_ERROR"
            )
    }
    )
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto) {
        boolean status = iAccountsService.updateAccount(customerDto);
        return (status
                ? ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(HttpStatus.OK, AccountsConstants.OK.getValue()))
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR,
                AccountsConstants.INTERNAL_SERVER_ERROR.getValue()))
        );

    }

    @Operation(
            summary = "Delete Account & Customer Details REST API",
            description = "REST API to Delete Customer & Account base on mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP status INTERNAL_SERVER_ERROR"
            )
    }
    )
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam
                                                            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
                                                            String mobileNumber) {
        boolean status = iAccountsService.deleteAccount(mobileNumber);
        return (status
                ? ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(HttpStatus.OK, AccountsConstants.OK.getValue()))
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR,
                AccountsConstants.INTERNAL_SERVER_ERROR.getValue()))
        );

    }
}
