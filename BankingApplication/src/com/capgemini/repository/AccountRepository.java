package com.capgemini.repository;

import com.capgemini.beans.Account;
import com.capgemini.exceptions.InvalidAccountNumberException;

public interface AccountRepository {

	boolean save(Account account);
	Account searchAccount(int acctNumber) throws InvalidAccountNumberException;
	
}
