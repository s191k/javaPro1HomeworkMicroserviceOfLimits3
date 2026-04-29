package org.example.errors;

import java.util.UUID;

public class OperationDoesntExist extends RuntimeException {

    private final UUID operationId;

    public OperationDoesntExist(String message, UUID operationId) {
        super(message);
        this.operationId = operationId;
    }

    public UUID getOperationId() {
        return operationId;
    }

}
