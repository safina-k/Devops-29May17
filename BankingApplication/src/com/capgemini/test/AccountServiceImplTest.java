package com.capgemini.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.capgemini.beans.Account;
import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientInitialAmountException;
import com.capgemini.exceptions.InvalidAccountNumberException;
import com.capgemini.repository.AccountRepository;
import com.capgemini.service.AccountService;
import com.capgemini.service.AccountServiceImpl;

import static org.mockito.Mockito.when;


public class AccountServiceImplTest {

	
	@Mock
	AccountRepository accountRepository;
	
	AccountService accountService;
	
	
	@Before
	public void setup(){
	
		MockitoAnnotations.initMocks(this);
		accountService = new AccountServiceImpl();
	}
	
	
	@Test(expected=com.capgemini.exceptions.InsufficientInitialAmountException.class)
	public void whenAmountLessThanFiveHundred() throws InsufficientInitialAmountException
	{
		accountService.createAccount(123, 400);			
	}
	
	
	@Test
	public void createAccountSuccess() throws InsufficientInitialAmountException
	{
		Account account = new Account();
		account.setAmount(1000);
		account.setNumber(101);
		
		when(accountRepository.save(account)).thenReturn(true);
		assertEquals(account, accountService.createAccount(101, 1000));		
		
	}
	
	
	@Test
	public void depositAmountSuccess() throws InvalidAccountNumberException, InsufficientInitialAmountException
	{
		Account account = new Account();
		account.setAmount(1000);
		account.setNumber(101);
		
		when(accountRepository.save(account)).thenReturn(true);
		accountService.createAccount(101, 1000);
		
		when(accountRepository.searchAccount(101)).thenReturn(account);
		assertEquals(2000, accountService.depositAmount(101, 1000));
	}
	
	
	@Test(expected=com.capgemini.exceptions.InvalidAccountNumberException.class)
	public void depositAmountInvalidAccountNumber() throws InvalidAccountNumberException, InsufficientInitialAmountException
	{
		Account account = new Account();
		account.setAmount(1000);
		account.setNumber(101);
		
		when(accountRepository.save(account)).thenReturn(true);
		accountService.createAccount(101, 1000);
		
		
		accountService.depositAmount(111, 1000);
	}
	
	@Test
	public void withdrawAmountSuccess() throws InsufficientBalanceException, InvalidAccountNumberException, InsufficientInitialAmountException
	{
		Account account = new Account();
		account.setAmount(1000);
		account.setNumber(101);
		
		when(accountRepository.save(account)).thenReturn(true);
		accountService.createAccount(101, 1000);
		
		assertEquals(500, accountService.withdrawAmount(101, 500));
	}
	
	@Test(expected=com.capgemini.exceptions.InvalidAccountNumberException.class)
	public void withdrawAmountInvalidAccountNumberException() throws InsufficientBalanceException, InvalidAccountNumberException, InsufficientInitialAmountException
	{
		Account account = new Account();
		account.setAmount(1000);
		account.setNumber(101);
		
		when(accountRepository.save(account)).thenReturn(true);
		accountService.createAccount(101, 1000);
		
		accountService.withdrawAmount(111, 500);
	}
	
	@Test(expected=com.capgemini.exceptions.InsufficientBalanceException.class)
	public void withdrawAmountInsufficientBalanceException() throws InsufficientBalanceException, InvalidAccountNumberException, InsufficientInitialAmountException
	{
		Account account = new Account();
		account.setAmount(1000);
		account.setNumber(101);
		
		when(accountRepository.save(account)).thenReturn(true);
		accountService.createAccount(101, 1000);
		
		accountService.withdrawAmount(101, 1500);
	}

}
