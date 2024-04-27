package com.exercise.bankaccount.req;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import jakarta.annotation.Nonnull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankTransferReq {
	private @Nonnull String creditorName;
	private @Nonnull String accountCode;
	private @Nonnull String bicCode;
	private @Nonnull String description;
	private @Nonnull String currency;
	private @Nonnull String amount;
	private @Nonnull String executionDate;

	public String getJson() throws Exception {
		ObjectMapper mapper = new ObjectMapper();

		ObjectNode rootNode = mapper.createObjectNode();

		rootNode.put("executionDate", this.executionDate);
		rootNode.put("description", this.description);
		rootNode.put("amount", this.amount);
		rootNode.put("currency", this.currency);

		ObjectNode creditorNode = mapper.createObjectNode();
		creditorNode.put("name", this.creditorName);

		ObjectNode accountNode = mapper.createObjectNode();
		accountNode.put("accountCode", this.accountCode);
		accountNode.put("bicCode", this.bicCode);

		creditorNode.set("account", accountNode);
		rootNode.set("creditor", creditorNode);

		String jsonString = mapper.writeValueAsString(rootNode);

		return jsonString;
	}

}
