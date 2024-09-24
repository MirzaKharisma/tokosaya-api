package com.enigma.TokoSayaApi.constant;

import lombok.Getter;

@Getter
public enum ETransactionStatus {
    ORDERED("ordered", "Ordered"),
    PENDING("pending", "Pending"),
    SETTLEMENT("settlement", "Settlement"),
    CANCEL("cancel", "Cancel"),
    DENY("deny", "Deny"),
    EXPIRED("expired", "Expired"),
    FAILURE("failure", "Failure");

    private final String name;
    private final String description;

    ETransactionStatus(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
