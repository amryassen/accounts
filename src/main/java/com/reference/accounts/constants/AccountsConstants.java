package com.reference.accounts.constants;

import lombok.Getter;

@Getter
public enum AccountsConstants {
    CREATED("Account Created Successfully"),
    OK("OK"),
    INTERNAL_SERVER_ERROR("An error occurred. Please try again or contact Dev team"),
    SAVINGS("Savings"),
    ADDRESS("9 Al-Alam, Ain Shams");

    private final String value;

    AccountsConstants(String value) {
        this.value = value;
    }

}
