package com.exercise.bankaccount.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exercise.bankaccount.component.FabrickAPIComponent;
import com.exercise.bankaccount.req.BankTransferReq;
import com.exercise.bankaccount.res.BalanceRes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Service
public class BankAccountService {

	private @Autowired FabrickAPIComponent fabrickApiComp;

	public String getBalance(String aAccountId) throws Exception {
		BalanceRes res = fabrickApiComp.getBankAccountBalance(aAccountId);
		return "Available balance: " + res.getAvailableBalance();
	}

	public String getTransactions(String aAccountId, String aFromAccountingDate, String aToAccountingDate)
			throws Exception {
		return fabrickApiComp.getBankTransactions(aAccountId, aFromAccountingDate, aToAccountingDate);
	}

	public String postBankTransfer(String aAccountId, BankTransferReq aBankTransferReq)
			throws JsonMappingException, JsonProcessingException {
		return fabrickApiComp.postBankTransfer(aAccountId, aBankTransferReq);
	}

}