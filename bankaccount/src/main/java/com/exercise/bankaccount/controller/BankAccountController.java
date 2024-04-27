package com.exercise.bankaccount.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exercise.bankaccount.req.BankTransferReq;
import com.exercise.bankaccount.service.BankAccountService;

@RestController
@RequestMapping("/bankaccount")
public class BankAccountController {

	private @Autowired BankAccountService bankAccountService;

	@GetMapping("getBalance/{accountId}")
	public String getBalances(@PathVariable("accountId") String aAccountId) throws Exception {
		return bankAccountService.getBalance(aAccountId);

	}

	@GetMapping("getTransactions/{accountId}")
	public String getTransactions(@PathVariable("accountId") String aAccountId,
			@RequestParam("fromAccountingDate") String aFromAccountingDate,
			@RequestParam("toAccountingDate") String aToAccountingDate) throws Exception {
		return bankAccountService.getTransactions(aAccountId, aFromAccountingDate, aToAccountingDate);
	}

	@PostMapping("postBankTransfer/{accountId}")
	public String postBankTransfer(@PathVariable("accountId") String aAccountId,
			@RequestBody BankTransferReq aBankTransferReq) throws Exception {
		return bankAccountService.postBankTransfer(aAccountId, aBankTransferReq);
	}
}
