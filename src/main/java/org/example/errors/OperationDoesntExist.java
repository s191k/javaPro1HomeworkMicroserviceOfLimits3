package org.example.errors;

import lombok.Getter;

import java.util.UUID;

@Getter
public class OperationDoesntExist extends RuntimeException {

    private final UUID operationId;

    public OperationDoesntExist(String message, UUID operationId) {
        super(message);
        this.operationId = operationId;
    }

}
