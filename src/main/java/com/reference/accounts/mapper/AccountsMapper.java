package com.reference.accounts.mapper;


import com.reference.accounts.dto.AccountsDto;
import com.reference.accounts.entity.Accounts;

// let the mapping to be static
// NOTE : don't put dependencies in DTOs like IDs or anything that maybe cause Circular Dependency
public class AccountsMapper {


    public static AccountsDto mapToAccountsDto(Accounts accounts) {
        return AccountsDto.builder()
                .accountNumber(accounts.getAccountNumber())
                .accountType(accounts.getAccountType())
                .branchAddress(accounts.getBranchAddress()).build();
    }


    public static void mapToAccounts(AccountsDto accountsDto, Accounts accounts) {
        accounts.setAccountNumber(accountsDto.getAccountNumber());
        accounts.setAccountType(accountsDto.getAccountType());
        accounts.setBranchAddress(accountsDto.getBranchAddress());
    }
}
