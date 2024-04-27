package com.exercise.bankaccount.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.exercise.bankaccount.req.BankTransferReq;
import com.exercise.bankaccount.res.BalanceRes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class FabrickAPIComponent {

	private final String BASE_URL = "https://sandbox.platfr.io";
	private final String API_KEY = "FXOVVXXHVCPVPBZXIJOBGUGSKHDNFRRQJP";

	private @Autowired RestTemplate restTemplate;

	public BalanceRes getBankAccountBalance(String aAccountId) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Auth-Schema", "S2S");
		headers.set("Api-Key", API_KEY);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<BalanceRes> requestEntity = new HttpEntity<>(headers);

		ResponseEntity<BalanceRes> responseEntity = restTemplate.exchange(
				BASE_URL + "/api/gbs/banking/v4.0/accounts/" + aAccountId + "/balance", HttpMethod.GET, requestEntity,
				BalanceRes.class);

		return responseEntity.getBody();
	}

	public String getBankTransactions(String aAccountId, String aFromAccountingDate, String aToAccountingDate)
			throws Exception {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set("Auth-Schema", "S2S");
			headers.set("Api-Key", API_KEY);
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("X-Time-Zone", "Europe/Rome");

			HttpEntity<String> requestEntity = new HttpEntity<>(headers);

			StringBuilder queryParam = new StringBuilder();
			if (aFromAccountingDate != null && aToAccountingDate != null) {
				queryParam.append("fromAccountingDate=").append(aFromAccountingDate).append("&toAccountingDate=")
						.append(aToAccountingDate);
			}

			ResponseEntity<String> responseEntity = restTemplate.exchange(BASE_URL + "/api/gbs/banking/v4.0/accounts/"
					+ aAccountId + "/transactions?" + queryParam.toString(), HttpMethod.GET, requestEntity,
					String.class);

			return responseEntity.getBody();
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			return e.getResponseBodyAsString();
		}
	}

	public String postBankTransfer(String aAccountId, BankTransferReq aBankTrasferReq)
			throws JsonMappingException, JsonProcessingException {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set("Auth-Schema", "S2S");
			headers.set("Api-Key", API_KEY);
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("X-Time-Zone", "Europe/Rome");

			String requestBody = aBankTrasferReq.getJson();
			HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

			ResponseEntity<String> responseEntity = restTemplate.exchange(
					BASE_URL + "/api/gbs/banking/v4.0/accounts/" + aAccountId + "/payments/money-transfers",
					HttpMethod.POST, requestEntity, String.class);

			return responseEntity.getBody();
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(e.getResponseBodyAsString());
			return "\"code\": " + jsonNode.get("errors").get(0).get("code").asText() + "\n\"description\": "
					+ jsonNode.get("errors").get(0).get("description").asText();
		} catch (Exception e) {
			return "Errore tecnico!";
		}
	}
}
